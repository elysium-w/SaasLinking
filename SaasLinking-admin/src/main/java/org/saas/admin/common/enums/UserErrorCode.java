package org.saas.admin.common.enums;

import org.saas.admin.common.conversion.errorcode.IErrorCode;

public enum UserErrorCode implements IErrorCode {
    USER_NULL("B000200","用户记录不存在"),
    USER_EXIST("B000200","用户记录不存在");
    private final String code;

    private final String message;
    UserErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}