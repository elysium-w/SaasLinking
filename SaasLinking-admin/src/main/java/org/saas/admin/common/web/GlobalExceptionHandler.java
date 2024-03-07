package org.saas.admin.common.web;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.saas.admin.common.conversion.errorcode.BaseErrorCode;
import org.saas.admin.common.conversion.exception.AbstractException;
import org.saas.admin.common.conversion.result.Result;
import org.saas.admin.common.conversion.result.Results;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Optional;

/**
 * 全局异常拦截器
 */
@RestControllerAdvice
@Slf4j
@Component
public class GlobalExceptionHandler {
    /**
     *  拦截参数异常
     */
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        FieldError firstFieldError = CollectionUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), exceptionStr);
        return Results.failure(BaseErrorCode.CLIENT_ERROR.code(), exceptionStr);
    }

    /**
     * 拦截应用内异常
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {AbstractException.class})
    public Result abstractException(HttpServletRequest request, AbstractException exception){
        if (exception.getCause() != null){
            log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), exception.toString(), exception.getCause());
            return Results.failure();
        }
        log.error("[{}] {} [exception] {}",request.getMethod(),request.getRequestURI().toString(),exception.toString());
        return Results.failure(exception);
    }

    /**
     * 拦截未捕获异常,Throwable处理其他位被方法捕获的异常
     * @param request
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public Result defaultErrorHandler(HttpServletRequest request,Throwable throwable){
        log.error("[{}] {} ",request.getMethod(),getUrl(request),throwable);
        return Results.failure();
    }

    /**
     * 将http请求转换成字符串
     * @param request
     * @return
     */
    private String getUrl(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getQueryString())){
            return request.getRequestURI().toString();
        }
        return request.getRequestURI().toString() + "?" + request.getQueryString();
    }
}
