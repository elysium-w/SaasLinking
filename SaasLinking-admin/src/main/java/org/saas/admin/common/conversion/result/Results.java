package org.saas.admin.common.conversion.result;

import org.saas.admin.common.conversion.errorcode.BaseErrorCode;
import org.saas.admin.common.conversion.errorcode.IErrorCode;
import org.saas.admin.common.conversion.exception.AbstractException;

import java.util.Optional;

/**
 * 全局返回对象构造器
 */
public final class Results {

    /**
     * 构造成功响应
     */
    public static Result<Void> success(){
        return new Result<Void>()
                .setCode(Result.SUCCESS_CODE);
    }
    /**
     * 构造带返回数据的成功响应
     */
    public static <T> Result<T> success(T data){
        return new Result<T>()
                .setCode(Result.SUCCESS_CODE)
                .setData(data);
    }

    /**
     * 构建服务端失败响应
     */
    public static Result<Void> failure(){
        return new Result<Void>()
                .setCode(BaseErrorCode.SERVICE_ERROR.code())
                .setMessage(BaseErrorCode.SERVICE_ERROR.message());
    }
    /**
     * 通过 {@link AbstractException} 构建失败响应
     */
    public static Result<Void> failure(AbstractException exception){
        String errorCode = Optional.ofNullable(exception.getErrorCode())
                .orElse(BaseErrorCode.SERVICE_ERROR.code());
        String errorMessage = Optional.ofNullable(exception.getErrorMessage())
                .orElse(BaseErrorCode.SERVICE_ERROR.message());
        return new Result<Void>()
                .setCode(BaseErrorCode.SERVICE_ERROR.code())
                ;
    }
    /**
     * 通过 errorCode、errorMessage 构建失败响应
     */
    public static Result<Void> failure(IErrorCode errorCode){
        return new Result<Void>()
                .setCode(errorCode.code())
                .setMessage(errorCode.message());
    }

}