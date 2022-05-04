package com.metaverse.hillside.core.advice;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.metaverse.hillside.common.exception.BusinessException;
import com.metaverse.hillside.common.exception.ValidatedException;
import com.metaverse.hillside.common.restful.response.ApiResult;
import com.metaverse.hillside.common.restful.response.IResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        String message = e.getMessage();

        log.info("Method:[{}],requestURI:[{}],Exception:[{}]",
                request.getMethod(),
                request.getRequestURI(),
                message,
                e
        );

        return ApiResult.failed(message);
    }

    @ExceptionHandler({
            BusinessException.class,
            ValidatedException.class
    })
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, BusinessException e) {
        String message = e.getMessage();

        log.info("Method:[{}],requestURI:[{}],Exception:[{}]",
                request.getMethod(),
                request.getRequestURI(),
                message,
                e
        );

        return ApiResult.failed(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.info("Method:[{}],requestURI:[{}],Exception:[{}]",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage(),
                e
        );

        // 打印待整理
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        String fieldMessage = fieldError.getDefaultMessage();
        return ApiResult.failed(fieldMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, DataIntegrityViolationException e) {
        log.info("Method:[{}],requestURI:[{}],Exception:[{}]",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage(),
                e
        );

        Matcher matcher = Pattern.compile("(?<=Duplicate entry ').*(?=' for key)").matcher(e.getRootCause().getMessage());
        if (matcher.find()) {
            String filedName = matcher.group();
            return ApiResult.failed(String.format("事务错误，某字段值【%s】，违反了惟一性限制", filedName));
        }
        return ApiResult.failed("事务错误");
    }

//    @ExceptionHandler({
//            SignatureVerificationException.class,
//            TokenExpiredException.class,
//            AlgorithmMismatchException.class
//    })
//    @ResponseStatus(HttpStatus.OK)
//    public IResult<String> handle(HttpServletRequest request, TokenExpiredException e) {
//        String message = e.getMessage();
//
//        log.info("Method:[{}],requestURI:[{}],Exception:[{}]",
//                request.getMethod(),
//                request.getRequestURI(),
//                message,
//                e
//        );
//
//        return ApiResult.failed(message);
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public IResult<String> handle(HttpServletRequest request, EntityNotFoundException e) {
        String message = e.getMessage();

        log.info("Method:[{}],requestURI:[{}],Exception:[{}]",
                request.getMethod(),
                request.getRequestURI(),
                message,
                e
        );

        return ApiResult.failed(message);
    }

}