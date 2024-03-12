package org.saas.project.common.conversion.exception;

import lombok.Getter;
import org.saas.project.common.conversion.errorcode.IErrorCode;
import org.springframework.util.StringUtils;
import java.util.Optional;

/**
 * 全局抽象异常
 */
@Getter
public abstract class AbstractException extends RuntimeException{
    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
