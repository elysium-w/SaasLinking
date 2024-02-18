package org.saas.admin.common.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.saas.admin.common.conversion.errorcode.BaseErrorCode;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.common.conversion.result.Results;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 全局异常拦截器
 */
@RestControllerAdvice
@Slf4j
@Component
public class GlobalExceptionHandler {
//    /**
//     *  拦截参数异常
//     */
//    public Result validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException exception){
//        BindingResult bindingResult = exception.getBindingResult();
//        FieldError firstFieldError = CollectionUtil.getFirst(bindingResult.getFieldErrors());
//        String exceptionStr = Optional.ofNullable(firstFieldError);
//    }
    /**
     * 拦截参数验证异常
     */
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError firstFieldError = CollectionUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), exceptionStr);
        return Results.failure(BaseErrorCode.CLIENT_ERROR.code(), exceptionStr);
    }

}
