package com.zjy.simplemodule.retrofit;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

public class HttpResultException extends Exception {

    private int errorType;
    private String errorMessage;
    private Throwable throwable;

    public final static int HTTP_ERROR = 0x00;
    public final static int CONNECT_ERROR = 0x01;
    public final static int JSON_ERROR = 0x02;
    public final static int UNKNOWN_ERROR = 0x03;

    public HttpResultException(Throwable throwable) {
        this.throwable = throwable;
        init();
    }

    private void init() {
        if (throwable instanceof HttpFailureException) {
            this.errorType = ((HttpFailureException) throwable).getErrorCode();
            this.errorMessage = ((HttpFailureException) throwable).getErrorMessage();
        } else if (throwable instanceof HttpException) {
            this.errorType =HTTP_ERROR;
            this.errorMessage = "网络错误";
        } else if (throwable instanceof ConnectException || throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
            this.errorType =CONNECT_ERROR;
            this.errorMessage = "连接错误";
        } else if (throwable instanceof JSONException || throwable instanceof ParseException || throwable instanceof JsonSyntaxException) {
            this.errorType =JSON_ERROR;
            this.errorMessage = "解析错误";
        } else {
            this.errorType =UNKNOWN_ERROR;
            this.errorMessage = "未知错误";
        }
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
