package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.PersonalInformationController;
import com.smartosc.fintech.lms.dto.InputPersonalInformationDto;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PersonalInformationControllerImpl implements PersonalInformationController {

    private final PersonalInformationService personalInformationService;

    @Override
    public Response<LoanPersonalInformationDto> getPersonalInformation(String uuid) {
        return Response.ok(personalInformationService.getLoanPersonalInformation(uuid));
    }

    @Override
    @SMFLogger
    public Response<List<LoanPersonalInformationDto>>
        updateLoanPersonalInformation(String uuid, InputPersonalInformationDto inputPersonalInformationDto) {
        List<LoanPersonalInformationDto> result = personalInformationService.updateLoanPersonalInformation(uuid, inputPersonalInformationDto);
        return Response.ok(result);
    }

}
