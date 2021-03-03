package com.smartosc.fintech.lms.config;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class SMFLoggerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMFLoggerConfig.class);
    private static final String LOGGER_REQUEST_URL = "url not recognized";

    @Before("within(com.smartosc.fintech.lms..*) "
            + "&& @annotation(com.smartosc.fintech.lms.common.util.SMFLogger)")
    public void writeLogBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        String url = getRequestUrl();
        if (!LOGGER_REQUEST_URL.equals(url)) {
            LOGGER.info("API = {}", url);
        } else {
            if (StringUtils.isNotEmpty(this.getMessage(joinPoint))) {
                LOGGER.info("Start with message:  {}", this.getMessage(joinPoint));
            }
        }
    }

    @AfterReturning("within(com.smartosc.fintech.lms..*)"
            + " && @annotation(com.smartosc.fintech.lms.common.util.SMFLogger)")
    public void writeLogAfterReturn(JoinPoint joinPoint) throws NoSuchMethodException {
        if (StringUtils.isNotEmpty(this.getMessage(joinPoint))) {
            LOGGER.info("End with message: {}", this.getMessage(joinPoint));
        }
    }

    @AfterThrowing(value = "within(com.smartosc.fintech.lms..*) "
            + "&& @annotation(com.smartosc.fintech.lms.common.util.SMFLogger)", throwing = "e")
    public void writeLogAfterThrow(JoinPoint joinPoint, Exception e) throws NoSuchMethodException {
        LOGGER.error("Exception in process, detail:", e);
        if (StringUtils.isNotEmpty(this.getMessage(joinPoint))) {
            LOGGER.info("Failed message: {}", this.getMessage(joinPoint));
        }
    }

    private String getMessage(JoinPoint joinPoint) throws NoSuchMethodException {
        Method interfaceMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method implementationMethod = joinPoint.getTarget().getClass().getMethod(interfaceMethod.getName(),
                interfaceMethod.getParameterTypes());
        String message = "";
        if (implementationMethod.isAnnotationPresent(SMFLogger.class)) {
            SMFLogger logger = implementationMethod.getAnnotation(SMFLogger.class);
            message = logger.message();
        }
        return message;
    }

    private String getRequestUrl() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getRequestURI();
            if (!StringUtils.isEmpty(request.getQueryString())) {
                url += "?" + request.getQueryString();
            }
            return url;
        } catch (Exception e) {
            return LOGGER_REQUEST_URL;
        }
    }
}
