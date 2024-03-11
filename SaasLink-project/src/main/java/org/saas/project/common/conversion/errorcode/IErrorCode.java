package org.saas.project.common.conversion.errorcode;

/**
 * 创建全局异常码
 */
public interface IErrorCode {
    /**
     * 错误码
     * @return
     */
    String code();

    /**
     * 错误信息
     * @return
     */
    String message();
}
