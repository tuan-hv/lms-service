package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.controller.BeneficiaryInfoController;
import com.smartosc.fintech.lms.dto.BeneficiaryInfoDto;
import com.smartosc.fintech.lms.dto.BeneficiaryInfoRequest;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.BeneficiaryInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class BeneficiaryInfoControllerImpl implements BeneficiaryInfoController {
    private final BeneficiaryInfoService beneficiaryInfoService;

    @Override
    public Response<BeneficiaryInfoDto> getBeneficiaryInfoByLoanUUID(BeneficiaryInfoRequest request) {
        BeneficiaryInfoDto response = beneficiaryInfoService.getBeneficiaryInfoByLoanUUID(request.getUuid());
        return Response.ok(response);
    }
}
