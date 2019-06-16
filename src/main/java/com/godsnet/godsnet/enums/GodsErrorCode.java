package com.godsnet.godsnet.enums;

public enum  GodsErrorCode {
    SYSTEM_ERROR("29999"),
    PARAM_MISSING("20000");
    private String code;

    GodsErrorCode(String code) {
        this.code = code;
    }
}
