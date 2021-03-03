package com.smartosc.fintech.lms.controller.handler;

import com.smartosc.fintech.lms.common.constant.MessageKey;
import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_CONSTRAINT_VIOLATION;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_DATA_INTEGRITY_VIOLATION;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_HTTP_MEDIA_TYPE_NOT_SUPPORTED;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_HTTP_MESSAGE_NOT_READABLE;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_HTTP_MESSAGE_NOT_WRITABLE;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_METHOD_ARGUMENT_NOT_VALID;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_METHOD_ARGUMENT_TYPE_MISMATCH;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_MISSING_SERVLET_REQUEST_PARAMETER;
import static com.smartosc.fintech.lms.common.constant.MessageKey.HANDLE_NOT_FOUND_EXCEPTION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  /**
   * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
   *
   * @param ex      MissingServletRequestParameterException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
    String message = String.format(ResourceUtil.getMessage(HANDLE_MISSING_SERVLET_REQUEST_PARAMETER), ex.getParameterName());
    return buildResponseEntity(BAD_REQUEST, new ApiError(message, BAD_REQUEST.value()));
  }

  /**
   * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
   *
   * @param ex      HttpMediaTypeNotSupportedException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(ResourceUtil.getMessage(HANDLE_HTTP_MEDIA_TYPE_NOT_SUPPORTED));
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    return buildResponseEntity(UNSUPPORTED_MEDIA_TYPE,
            new ApiError(builder.substring(0, builder.length() - 2), UNSUPPORTED_MEDIA_TYPE.value()));
  }

  /**
   * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
   *
   * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {

    ApiError apiError = new ApiError(ResourceUtil.getMessage(HANDLE_METHOD_ARGUMENT_NOT_VALID), BAD_REQUEST.value());
    apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
    apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                      HttpHeaders headers,
                                                      HttpStatus status,
                                                      WebRequest request) {
    ApiError error = new ApiError(ex.getMessage(), BAD_REQUEST.value());
    return buildResponseEntity(BAD_REQUEST, error);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ApiError apiError = new ApiError(ResourceUtil.getMessage(HANDLE_METHOD_ARGUMENT_NOT_VALID), BAD_REQUEST.value());
    apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
    apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  /**
   * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
   *
   * @param ex the ConstraintViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(
      javax.validation.ConstraintViolationException ex) {
    ApiError apiError = new ApiError(ResourceUtil.getMessage(HANDLE_CONSTRAINT_VIOLATION), BAD_REQUEST.value());
    apiError.addValidationErrors(ex.getConstraintViolations());
    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  /**
   * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
   *
   * @param ex the EntityNotFoundException
   * @return the ApiError object
   */
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
    ApiError error = new ApiError(ex.getMessage(), NOT_FOUND.value());
    return buildResponseEntity(NOT_FOUND, error);
  }

  /**
   * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
   *
   * @param ex      HttpMessageNotReadableException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      @NonNull HttpMessageNotReadableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
    String message = ResourceUtil.getMessage(HANDLE_HTTP_MESSAGE_NOT_READABLE);
    return buildResponseEntity(BAD_REQUEST, new ApiError(message, BAD_REQUEST.value()));
  }

  /**
   * Handle HttpMessageNotWritableException.
   *
   * @param ex      HttpMessageNotWritableException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      @NonNull HttpMessageNotWritableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    String message = ResourceUtil.getMessage(HANDLE_HTTP_MESSAGE_NOT_WRITABLE);
    return buildResponseEntity(INTERNAL_SERVER_ERROR, new ApiError(message, INTERNAL_SERVER_ERROR.value()));
  }

  /**
   * Handle NoHandlerFoundException.
   *
   * @param ex      NoHandlerFoundException
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiError object
   */
  @Override
  @NonNull
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      @NonNull NoHandlerFoundException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    String message = String.format(ResourceUtil.getMessage(HANDLE_NOT_FOUND_EXCEPTION), ex.getHttpMethod(), ex.getRequestURL());
    ApiError error = new ApiError(message, BAD_REQUEST.value());
    return buildResponseEntity(BAD_REQUEST, error);
  }

  /**
   * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
   *
   * @param ex the DataIntegrityViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      return buildResponseEntity(CONFLICT, new ApiError(ResourceUtil.getMessage(HANDLE_DATA_INTEGRITY_VIOLATION), CONFLICT.value()));
    }
    return buildResponseEntity(INTERNAL_SERVER_ERROR, new ApiError(ResourceUtil.getMessage(MessageKey.INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR.value()));
  }

  /**
   * Handle Exception, handle generic Exception.class
   *
   * @param ex the Exception
   * @return the ApiError object
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String message = String.format(ResourceUtil.getMessage(HANDLE_METHOD_ARGUMENT_TYPE_MISMATCH),
            ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
    ApiError error = new ApiError(message, BAD_REQUEST.value());
    return buildResponseEntity(BAD_REQUEST, error);
  }

  @ExceptionHandler(BusinessServiceException.class)
  protected ResponseEntity<Object> handleBusinessService(BusinessServiceException ex) {
    ApiError error = new ApiError(ex.getMessage(), ex.getCode());
    return buildResponseEntity(CONFLICT, error);
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleInternalException(Exception ex) {
    ApiError error = new ApiError(ex.getMessage(), INTERNAL_SERVER_ERROR.value());
    return buildResponseEntity(INTERNAL_SERVER_ERROR, error);
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, ApiError error) {
    return new ResponseEntity<>(Response.fail(error), httpStatus);
  }
}
