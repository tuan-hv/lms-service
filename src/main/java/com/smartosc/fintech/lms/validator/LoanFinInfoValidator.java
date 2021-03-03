package com.smartosc.fintech.lms.validator;

import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.ParamsConstant;
import com.smartosc.fintech.lms.common.constant.TableAndFieldName;
import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.smartosc.fintech.lms.common.constant.MessageKey.*;

@Slf4j
@Component
public class LoanFinInfoValidator {

    public void checkSimilarWithLast(LoanJobInformationDto last, LoanFinInfoRequest request) {
        if (isSimilarWithLast(last, request)) {
            throw new BusinessServiceException(ResourceUtil.getMessage(FIN_INFO_VALIDATOR_SIMILAR), ErrorCode.NOT_ALLOWED);
        }
    }

    public void validateInputType(LoanFinInfoRequest request) {
        try {
            long expense = Long.parseLong(request.getExpense());
            long preTaxIncome = Long.parseLong(request.getPreTaxIncome());
            StringBuilder errorMessage = new StringBuilder();
            if (expense > ParamsConstant.ALLOWED_MAX_NUMBER || expense < 0) {
                errorMessage.append(String.format(ResourceUtil.getMessage(FIN_INFO_VALIDATOR_OVER_ALLOWED_VALUE), TableAndFieldName.EXPENSE.toString()));
            }
            if (preTaxIncome > ParamsConstant.ALLOWED_MAX_NUMBER || preTaxIncome < 0) {
                if (errorMessage.length() > 0) {
                    errorMessage.append("|" + String.format(ResourceUtil.getMessage(FIN_INFO_VALIDATOR_OVER_ALLOWED_VALUE), TableAndFieldName.PRETAX_INCOME.toString()));
                } else {
                    errorMessage.append(String.format(ResourceUtil.getMessage(FIN_INFO_VALIDATOR_OVER_ALLOWED_VALUE), TableAndFieldName.PRETAX_INCOME.toString()));
                }
            }
            if (errorMessage.length() > 0) {
                throw new BusinessServiceException(errorMessage.toString(), ErrorCode.NOT_ALLOWED);
            }
        } catch (NumberFormatException e) {
            throw new BusinessServiceException(ResourceUtil.getMessage(FIN_INFO_VALIDATOR_INPUT), HttpStatus.SC_UNPROCESSABLE_ENTITY);
        }
    }

    public boolean isSimilarWithLast(LoanJobInformationDto last, LoanFinInfoRequest request) {
        long expense = Long.parseLong(request.getExpense());
        long preTaxIncome = Long.parseLong(request.getPreTaxIncome());
        return (ObjectUtils.isNotEmpty(last)
                && ObjectUtils.isNotEmpty(last.getExpense())
                && ObjectUtils.isNotEmpty(last.getPreTaxIncome())
                && last.getCompanyAddress() != null
                && last.getCompanyName() != null
                && last.getCompanyAddress().equals(request.getCompanyAddress())
                && last.getCompanyName().equals(request.getCompanyName())
                && expense == last.getExpense().longValue()
                && preTaxIncome == last.getPreTaxIncome().longValue())
                ||
                (ObjectUtils.isNotEmpty(last)
                        && ObjectUtils.isNotEmpty(last.getExpense())
                        && ObjectUtils.isNotEmpty(last.getPreTaxIncome())
                        && request.getCompanyAddress() != null
                        && request.getCompanyName() != null
                        && request.getCompanyAddress().equals(last.getCompanyAddress())
                        && request.getCompanyName().equals(last.getCompanyName())
                        && expense == last.getExpense().longValue()
                        && preTaxIncome == last.getPreTaxIncome().longValue())
                ||
                (ObjectUtils.isNotEmpty(last)
                        && ObjectUtils.isNotEmpty(last.getExpense())
                        && ObjectUtils.isNotEmpty(last.getPreTaxIncome())
                        && ObjectUtils.isEmpty(request.getCompanyAddress())
                        && ObjectUtils.isEmpty(request.getCompanyName())
                        && ObjectUtils.isEmpty(last.getCompanyAddress())
                        && ObjectUtils.isEmpty(last.getCompanyName())
                        && expense == last.getExpense().longValue()
                        && preTaxIncome == last.getPreTaxIncome().longValue());
    }

}
