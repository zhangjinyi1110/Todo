package com.zjy.simplemodule.retrofit;

public class HttpFailureException extends Exception {

    private int errorCode;
    private String errorMessage;

    public HttpFailureException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
