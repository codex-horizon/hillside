package com.metaverse.hillside.core.advice;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.metaverse.hillside.common.exception.BusinessException;
import com.metaverse.hillside.common.exception.ValidatedException;
import com.metaverse.hillside.common.restful.response.ApiResult;
import com.metaverse.hillside.common.restful.response.IResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, Exception e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return ApiResult.failed(e.getMessage());
    }

    @ExceptionHandler({
            BusinessException.class,
            ValidatedException.class
    })
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, BusinessException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return ApiResult.failed(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        // 打印待整理
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        String fieldMessage = fieldError.getDefaultMessage();
        return ApiResult.failed(fieldMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, DataIntegrityViolationException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        Matcher matcher = Pattern.compile("(?<=Duplicate entry ').*(?=' for key)").matcher(e.getRootCause().getMessage());
        if (matcher.find()) {
            String filedName = matcher.group();
            return ApiResult.failed(String.format("事务错误，某字段值【%s】，违反了惟一性限制", filedName));
        }
        return ApiResult.failed("事务错误");
    }

    @ExceptionHandler({
            SignatureVerificationException.class,
            TokenExpiredException.class,
            AlgorithmMismatchException.class
    })
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, JWTVerificationException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return ApiResult.failed(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, EntityNotFoundException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return ApiResult.failed(e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, MissingServletRequestParameterException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        String message = String.format("参数[%s],类型[%s] 有误", e.getParameterName(), e.getParameterType());
        return ApiResult.failed(message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return ApiResult.failed(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, HttpMessageNotReadableException e) {
        log.error("方法:[{}],请求url:[{}],异常信息:[{}]", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        return ApiResult.failed("JSON解析错误");
    }
}