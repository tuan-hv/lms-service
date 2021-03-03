package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.RepaymentState;
import com.smartosc.fintech.lms.common.util.CurrencyUtil;
import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.BaseRepaymentDto;
import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.dto.DisbursementInfoDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.LoanOfLenderDto;
import com.smartosc.fintech.lms.dto.PageableData;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.dto.PaymentAmountDto;
import com.smartosc.fintech.lms.dto.RepaymentAmountDto;
import com.smartosc.fintech.lms.dto.SearchLoanDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanDisbursementMethodEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanProductRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.LoanApplicationService;
import com.smartosc.fintech.lms.service.RepaymentService;
import com.smartosc.fintech.lms.service.mapper.BaseRepaymentMapper;
import com.smartosc.fintech.lms.service.mapper.BigDecimalMapper;
import com.smartosc.fintech.lms.service.mapper.BriefLoanMapper;
import com.smartosc.fintech.lms.service.mapper.LoanApplicationMapper;
import com.smartosc.fintech.lms.service.mapper.PaymentAmountMapper;
import com.smartosc.fintech.lms.service.mapper.RepaymentLatelyPrincipalMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.smartosc.fintech.lms.common.constant.CurrencyCode.VND;
import static com.smartosc.fintech.lms.common.constant.DisbursementMethod.valueOf;
import static com.smartosc.fintech.lms.common.constant.ErrorCode.SEARCH_LOAN_INPUT_DATE_ERROR;
import static com.smartosc.fintech.lms.common.constant.LoanApplicationStatus.ACTIVE;
import static com.smartosc.fintech.lms.common.constant.LoanTransactionType.DISBURSEMENT;
import static com.smartosc.fintech.lms.common.constant.MessageKey.APPLICATION_MANY_RECORD;
import static com.smartosc.fintech.lms.common.constant.MessageKey.APPLICATION_NOT_FOUND;
import static com.smartosc.fintech.lms.common.constant.MessageKey.SEARCH_LOAN_INPUT_DATE_INVALID;
import static com.smartosc.fintech.lms.common.constant.ParamsConstant.DAY_OF_YEAR;
import static com.smartosc.fintech.lms.common.constant.ParamsConstant.LEAD_DAY;
import static com.smartosc.fintech.lms.common.constant.ParamsConstant.ZERO_2SCALE;
import static com.smartosc.fintech.lms.common.constant.RepaymentState.PARTIALLY_PAID;
import static com.smartosc.fintech.lms.common.constant.RepaymentState.PENDING;
import static com.smartosc.fintech.lms.common.util.DateTimeUtil.getFormatTimestamp;
import static com.smartosc.fintech.lms.repository.specification.BaseSpecification.where;
import static com.smartosc.fintech.lms.repository.specification.LoanApplicationSpecification.whereAccountNumberEqual;
import static com.smartosc.fintech.lms.repository.specification.LoanApplicationSpecification.whereCreateDateGreaterThanOrEqualTo;
import static com.smartosc.fintech.lms.repository.specification.LoanApplicationSpecification.whereCreateDateLessThanOrEqualTo;
import static com.smartosc.fintech.lms.repository.specification.LoanApplicationSpecification.whereCustomerCardEqual;
import static com.smartosc.fintech.lms.repository.specification.LoanApplicationSpecification.whereCustomerNameEqual;
import static com.smartosc.fintech.lms.repository.specification.LoanApplicationSpecification.whereLoanTypeEqual;
import static com.smartosc.fintech.lms.repository.specification.LoanApplicationSpecification.whereStatusEqual;

@Service
@AllArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    private RepaymentRepository repaymentRepository;

    private RepaymentService repaymentService;

    private LoanTransactionRepository loanTransactionRepository;

    private LoanProductRepository loanProductRepository;


    @Override
    @SMFLogger
    public LoanApplicationDto findLoanApplicationEntityByUuid(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid)));
        LoanApplicationDto loanApplicationDto = LoanApplicationMapper.getInstance().mapToDto(loanApplicationEntity);

        /*set outstandingBalance*/
        BigDecimal outstandingBalance = BigDecimal.ZERO;
        if (ACTIVE.getValue() == (loanApplicationEntity.getStatus())) {
            outstandingBalance = loanApplicationEntity.getPrincipalPaid() != null
                    ? loanApplicationEntity.getLoanAmount().subtract(loanApplicationEntity.getPrincipalPaid())
                    : loanApplicationEntity.getLoanAmount();
        }
        loanApplicationDto.setOutstandingBalance(BigDecimalMapper.mapToScale(outstandingBalance));
        loanApplicationDto.setLoanType(loanApplicationEntity.getLoanProduct().getName());

        /*set expire date, disburse date*/
        LoanTransactionEntity loanTransactionEntity =
                loanTransactionRepository.findDistinctFirstByLoanApplicationUuidAndType(uuid, DISBURSEMENT.name());
        LocalDateTime expireDate = null;
        if (loanTransactionEntity != null) {
            Period period = Period.ofDays(Integer.parseInt(loanApplicationEntity.getLoanTerm()));
            LocalDateTime entryDate = loanTransactionEntity.getEntryDate().toLocalDateTime().toLocalDate().atTime(LocalTime.MAX);
            expireDate = entryDate.plus(period);
            loanApplicationDto.setExpireDate(getFormatTimestamp(Timestamp.valueOf(expireDate)));
            loanApplicationDto.setDisburseDate(getFormatTimestamp(loanTransactionEntity.getEntryDate()));
        }

        /*set disbursement info*/
        DisbursementInfoDto disbursementInfoDto = new DisbursementInfoDto();
        if (!Objects.isNull(loanTransactionEntity)) {
            disbursementInfoDto.setDisbursedAmount(loanTransactionEntity.getAmount());
        }
        if (!CollectionUtils.isEmpty(loanApplicationEntity.getLoanDisbursementMethods())) {
            LoanDisbursementMethodEntity loanDisbursementMethodEntity = loanApplicationEntity.getLoanDisbursementMethods()
                    .stream()
                    .findFirst()
                    .orElse(new LoanDisbursementMethodEntity());
            disbursementInfoDto.setDisbursedMethod(valueOf(loanDisbursementMethodEntity.getDisbursementMethod()).getLabel());
        }
        loanApplicationDto.setDisbursedInfo(disbursementInfoDto);

        if (ACTIVE.getValue() == loanApplicationEntity.getStatus() && loanTransactionEntity != null) {
            BigDecimal interestAccrued = repaymentService.calculateAccruedInterest(loanApplicationEntity, loanTransactionEntity.getEntryDate());
            loanApplicationDto.setInterestAccrued(BigDecimalMapper.mapToScale(interestAccrued));
        }

        /*set payment amount*/
        List<RepaymentEntity> repaymentEntities =
                repaymentRepository.findByLoanApplicationUuidAndStateNotOrderByDueDateAsc(uuid, RepaymentState.PAID.name());
        if (!repaymentEntities.isEmpty()) {
            LocalDateTime currentDate = LocalDateTime.now();
            Period periodLead = Period.ofDays(LEAD_DAY);
            if (expireDate != null && currentDate.plus(periodLead).compareTo(expireDate.toLocalDate().atStartOfDay()) >= 0) {
                RepaymentEntity latestPayment = calculateInterest(LocalDate.now(), repaymentEntities.get(0), loanApplicationDto.getInterestRate());
                PaymentAmountDto paymentAmountDto = PaymentAmountMapper.getInstance().entityToDto(latestPayment);
                loanApplicationDto.setPaymentAmount(paymentAmountDto);
            }
        }
        return loanApplicationDto;
    }

    @Override
    @SMFLogger
    public List<BriefLoanDto> findLoanApplicationByUser(long id) {
        List<LoanApplicationEntity> loanApplicationEntities = loanApplicationRepository.findLoanApplicationEntityByUserIdAndStatusNotDrop(id);
        List<BriefLoanDto> briefLoanDtos = new ArrayList<>();
        for (LoanApplicationEntity loanApplicationEntity : loanApplicationEntities) {
            BriefLoanDto briefLoanDto = BriefLoanMapper.getInstance().mapToDto(loanApplicationEntity);
            BigDecimal outstandingBalance = BigDecimal.ZERO;
            if (ACTIVE.getValue() == (loanApplicationEntity.getStatus())) {
                outstandingBalance = loanApplicationEntity.getPrincipalPaid() != null
                        ? loanApplicationEntity.getLoanAmount().subtract(loanApplicationEntity.getPrincipalPaid())
                        : loanApplicationEntity.getLoanAmount();
            }
            briefLoanDto.setOutstandingBalance(BigDecimalMapper.mapToScale(outstandingBalance));
            briefLoanDtos.add(briefLoanDto);
        }
        return briefLoanDtos;
    }

    @Override
    @SMFLogger
    public PagingResponse<LoanOfLenderDto> searchListLoanOfLender(SearchLoanDto searchLoanDto) {

        if (Objects.nonNull(searchLoanDto.getCreateFrom()) && Objects.nonNull(searchLoanDto.getCreateTo()) &&
                searchLoanDto.getCreateFrom().compareTo(searchLoanDto.getCreateTo()) > 0) {
            throw new BusinessServiceException(ResourceUtil.getMessage(SEARCH_LOAN_INPUT_DATE_INVALID), SEARCH_LOAN_INPUT_DATE_ERROR);
        }

        Pageable pageable = PageRequest.of(searchLoanDto.getPageNumber(), searchLoanDto.getPageSize(), Sort.by("createdDate").descending());
        Specification<LoanApplicationEntity> specification = null;
        specification = where(specification, whereStatusEqual(searchLoanDto.getStatus()));
        specification = where(specification, whereLoanTypeEqual(searchLoanDto.getLoanType()));
        specification = where(specification, whereAccountNumberEqual(searchLoanDto.getLoanAccount()));
        specification = where(specification, whereCustomerNameEqual(searchLoanDto.getCustomerName()));
        specification = where(specification, whereCustomerCardEqual(searchLoanDto.getCardNumber()));
        specification = where(specification, whereCreateDateGreaterThanOrEqualTo(searchLoanDto.getCreateFrom()));
        specification = where(specification, whereCreateDateLessThanOrEqualTo(searchLoanDto.getCreateTo()));

        Page<LoanApplicationEntity> pageLoan = loanApplicationRepository.findAll(specification, pageable);
        Page<LoanOfLenderDto> pageLoanDto = pageLoan.map(LoanApplicationMapper.getInstance()::mapToLoanOfLenderDto);

        PageableData paging = new PageableData();
        paging.setPageNumber(pageLoan.getNumber());
        paging.setPageSize(pageLoan.getSize());
        paging.setTotalPage(pageLoan.getTotalPages());
        paging.setTotalRecord(pageLoan.getTotalElements());

        PagingResponse<LoanOfLenderDto> response = new PagingResponse<>();
        response.setContents(pageLoanDto.getContent());
        response.setPaging(paging);

        return response;
    }


    public RepaymentEntity calculateInterest(LocalDate currentDate, RepaymentEntity latestPayment, BigDecimal interestRate) {
        LocalDate dueDate = latestPayment.getDueDate().toLocalDateTime().toLocalDate();
        if (currentDate.isBefore(dueDate)) {
            return latestPayment;
        }

        long diffDays = ChronoUnit.DAYS.between(dueDate, currentDate);
        BigDecimal expiredInterest = latestPayment.getPrincipalDue()
                .multiply(BigDecimal.valueOf(diffDays))
                .multiply(interestRate)
                .divide(DAY_OF_YEAR, RoundingMode.CEILING
                );
        latestPayment.setInterestDue(latestPayment.getInterestDue().add(expiredInterest));
        return latestPayment;
    }

    @Override
    public Integer getStatusOnly(String uuid) {
        List<Integer> statusObject = loanApplicationRepository.getStatusOnly(uuid);
        if (ObjectUtils.isEmpty(statusObject)) {
            throw new BusinessServiceException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid), HttpStatus.SC_NOT_FOUND);
        } else if (statusObject.size() > 1) {
            throw new BusinessServiceException(String.format(ResourceUtil.getMessage(APPLICATION_MANY_RECORD), uuid), ErrorCode.EXPECTONE_GETTOOMANY);
        }

        return statusObject.get(0);

    }

    @Override
    public List<String> getLoanTypeList() {
        return loanProductRepository.getLoanTypeList();
    }

    @Override
    public RepaymentAmountDto getRepaymentInfo(String uuid) {
        String[] states = new String[]{PENDING.name(), PARTIALLY_PAID.name()};
        List<RepaymentEntity> list = repaymentRepository.findByStateInAndLoanApplicationUuidOrderByDueDateAsc(states, uuid);
        List<BaseRepaymentDto> listRepaymentLate = new ArrayList<>();
        BaseRepaymentMapper mapper = RepaymentLatelyPrincipalMapper.getInstance();

        LocalDateTime preDueDate = list.get(0).getDueDate().toLocalDateTime();
        for (int i = 0; i < list.size(); i++) {
            listRepaymentLate.add(mapper.mapToBaseRepaymentDto(list.get(i), preDueDate));
            preDueDate = list.get(i).getDueDate().toLocalDateTime();
        }
        return calculatePaymentInfo(listRepaymentLate);
    }

    private BigDecimal calculateRepaymentAmount(List<BaseRepaymentDto> list) {
        BigDecimal repaymentAmount = list.stream()
                .filter(x -> x.checkExpireDueDate() || x.checkInPeriod())
                .map(BaseRepaymentDto::paymentAmount)
                .reduce(ZERO_2SCALE, BigDecimal::add);
        repaymentAmount = repaymentAmount.setScale(CurrencyUtil.getMinorUnit(VND), BigDecimal.ROUND_HALF_UP);
        repaymentAmount = repaymentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        return repaymentAmount;
    }

    private BigDecimal calculatePayoffAmount(List<BaseRepaymentDto> list) {
        BigDecimal payOffAmount = list.stream()
                .map(BaseRepaymentDto::paymentAmount)
                .reduce(ZERO_2SCALE, BigDecimal::add);
        payOffAmount = payOffAmount.setScale(CurrencyUtil.getMinorUnit(VND), BigDecimal.ROUND_HALF_UP);
        payOffAmount = payOffAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        return payOffAmount;
    }

    private RepaymentAmountDto calculatePaymentInfo(List<BaseRepaymentDto> list) {
        RepaymentAmountDto repaymentAmountDto = new RepaymentAmountDto();
        repaymentAmountDto.setLoanAccount(list.get(0).getLoanAccount());
        repaymentAmountDto.setRepaymentAmount(calculateRepaymentAmount(list));
        repaymentAmountDto.setPayOffAmount(calculatePayoffAmount(list));
        return repaymentAmountDto;
    }

}
