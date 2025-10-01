package com.phmxnnam.prj_banking.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error."),
    USER_NOT_EXISTS(1000 ,"user not exist."),
    USER_EXISTED(1001,"user existed."),
    NAME_INVALID(1002,"must be at least 10 characters."),
    USERNAME_INVALID(1003, "must be at least 8 characters."),
    PASSWORD_INVALID(1004, "must be at least 8 characters."),
    EMAIL_INVALID(1005, "email invalid."),
    PHONE_INVALID(1006,"must be 10 numbers."),
    DOB_INVALID(1007, "must be at least 18."),
    ROLE_EXISTED(1008, "role existed."),
    ROLE_NOT_EXIST(1009,"role not exist."),
    CUSTOMER_NOT_EXISTS(1010,"customer not exist."),
    PHONE_NUMBER_EXISTED(1011,"phone number already in use."),
    ACCOUNT_EXISTED(1012,"account number existed."),
    ACCOUNT_NOT_EXISTS(1013,"account number not exists."),
    ACCOUNT_INACTIVE(1016, "account inactive."),
    PERMISSION_NOT_EXISTS(1014,"permission not exists."),
    PERMISSION_EXISTED(1014,"permission existed."),
    INSUFFICIENT_BALANCE(1015, "insufficient balance."),
    MIN_AMOUNT(1017, "at least 50 thousand VND."),
    TOKEN_INVALID(1018,"token invalid"),
    APPEND_ONLY(1019, "append-only"),
    SYSTEM_ERROR(1020, "system error")

    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    int code;
    String message;
}
