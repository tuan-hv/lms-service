package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.dto.LoanTransactionRequest;
import com.smartosc.fintech.lms.dto.PageableData;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.service.LoanTransactionService;
import com.smartosc.fintech.lms.service.mapper.LoanTransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static com.smartosc.fintech.lms.common.constant.ErrorCode.TRANSACTION_INPUT_DATE_ERROR;
import static com.smartosc.fintech.lms.common.constant.MessageKey.TRANSACTION_INPUT_DATE_INVALID;
import static com.smartosc.fintech.lms.common.constant.MessageKey.TRANSACTION_NOT_FOUND;
import static com.smartosc.fintech.lms.repository.specification.LoanTransactionSpecification.*;

@Service
@AllArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {
    private final LoanTransactionRepository loanTransactionRepository;

    @Override
    public LoanTransactionDto getLoanTransaction(String uuid) {
        LoanTransactionEntity transaction = loanTransactionRepository.findByUuid(uuid).orElseThrow(() ->
                                                new EntityNotFoundException(ResourceUtil.getMessage(TRANSACTION_NOT_FOUND)));

        return LoanTransactionMapper.getInstance().mapToDto(transaction);
    }

    @Override
    public PagingResponse<LoanTransactionDto> getListLoanTransaction(LoanTransactionRequest request) {
        if (Objects.nonNull(request.getFromDate()) && Objects.nonNull(request.getToDate()) &&
                request.getFromDate().compareTo(request.getToDate()) > 0) {
            throw new BusinessServiceException(ResourceUtil.getMessage(TRANSACTION_INPUT_DATE_INVALID), TRANSACTION_INPUT_DATE_ERROR);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);

        Specification<LoanTransactionEntity> specification = Specification.where(loanApplicationKeyEq(request.getUuid()));
        specification = specification == null ? Specification.where(creationDateGte(request.getFromDate())) :
                                                specification.and(creationDateGte(request.getFromDate()));
        specification = specification == null ? Specification.where(creationDateLte(request.getToDate())) :
                                                specification.and(creationDateLte(request.getToDate()));
        Page<LoanTransactionEntity> transactions = loanTransactionRepository.findAll(specification, pageable);
        Page<LoanTransactionDto> pagingDto = transactions.map(LoanTransactionMapper.getInstance()::mapToDto);

        PageableData paging = new PageableData();
        paging.setPageNumber(request.getPageNumber());
        paging.setPageSize(request.getPageSize());
        paging.setTotalPage(pagingDto.getTotalPages());
        paging.setTotalRecord(pagingDto.getTotalElements());

        PagingResponse<LoanTransactionDto> response = new PagingResponse<>();
        response.setContents(pagingDto.getContent());
        response.setPaging(paging);

        return response;
    }
}
