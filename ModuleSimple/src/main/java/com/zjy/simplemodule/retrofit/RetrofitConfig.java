package com.zjy.simplemodule.retrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

public class RetrofitConfig {

    static TimeUnit timeUnit = TimeUnit.SECONDS;
    static long writeTimeout = 5000;
    static long readTimeout = 5000;
    static long connectTimeout = 5000;
    static List<Interceptor> interceptors;
    static String baseUrl;
    static int successCode = 0;
    static int retryCount = 5;
    static long retryTime = 3000;
    private static RetrofitConfig config;

    public static RetrofitConfig newInstance() {
        if (config == null) {
            synchronized (RetrofitConfig.class) {
                if (config == null) {
                    config = new RetrofitConfig();
                }
            }
        }
        return config;
    }

    private RetrofitConfig() {
        interceptors = new ArrayList<>();
    }

    public RetrofitConfig setTimeUnit(TimeUnit timeUnit) {
        RetrofitConfig.timeUnit = timeUnit;
        return config;
    }

    public RetrofitConfig setConnectTimeout(long connectTimeout) {
        RetrofitConfig.connectTimeout = connectTimeout;
        return config;
    }

    public RetrofitConfig setReadTimeout(long readTimeout) {
        RetrofitConfig.readTimeout = readTimeout;
        return config;
    }

    public RetrofitConfig setWriteTimeout(long writeTimeout) {
        RetrofitConfig.writeTimeout = writeTimeout;
        return config;
    }

    public RetrofitConfig addInterceptors(List<Interceptor> interceptors) {
        RetrofitConfig.interceptors.addAll(interceptors);
        return config;
    }

    public RetrofitConfig addInterceptor(Interceptor interceptor) {
        RetrofitConfig.interceptors.add(interceptor);
        return config;
    }

    public RetrofitConfig setBaseUrl(String baseUrl) {
        RetrofitConfig.baseUrl = baseUrl;
        return config;
    }

    public RetrofitConfig setSuccessCode(int successCode) {
        RetrofitConfig.successCode = successCode;
        return config;
    }

    public RetrofitConfig setRetryCount(int retryCount) {
        RetrofitConfig.retryCount = retryCount;
        return config;
    }

    public RetrofitConfig setRetryTime(long retryTime) {
        RetrofitConfig.retryTime = retryTime;
        return config;
    }
}
