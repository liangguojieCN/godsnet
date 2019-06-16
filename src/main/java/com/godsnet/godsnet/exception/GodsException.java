package com.godsnet.godsnet.exception;

import com.godsnet.godsnet.enums.GodsErrorCode;

import java.io.IOException;

public class GodsException extends IOException {
    private GodsErrorCode code;
    private String message;

    public GodsException(GodsErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public GodsErrorCode getCode() {
        return code;
    }

    public void setCode(GodsErrorCode code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
