package org.saas.admin.common.conversion.result;

import org.saas.admin.common.conversion.errorcode.BaseErrorCode;
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

    }

    /**
     * 构建服务端失败响应
     */

    /**
     * 通过 {@link AbstractException} 构建失败响应
     */

    /**
     * 通过 errorCode、errorMessage 构建失败响应
     */

}