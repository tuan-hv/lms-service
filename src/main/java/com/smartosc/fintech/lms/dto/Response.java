package com.smartosc.fintech.lms.dto;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.controller.handler.ApiError;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import static com.smartosc.fintech.lms.common.constant.MessageKey.APP_RESPONSE_OK;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Response<T> {
    @ApiModelProperty(notes = "error code")
    private ApiError status;
    @ApiModelProperty(notes = "data of response")
    private T data;

    public static <T> Response<T> ok(T data) {
        ApiError status = new ApiError(ResourceUtil.getMessage(APP_RESPONSE_OK), HttpStatus.OK.value());
        return Response.<T>builder().status(status).data(data).build();
    }

    public static <T> Response<T> fail(String message, int code) {
        ApiError status = new ApiError(message, code);
        return Response.<T>builder().status(status).build();
    }

    public static <T> Response<T> fail(ApiError status) {
        return Response.<T>builder().status(status).build();
    }
}
