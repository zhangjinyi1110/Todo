package com.zjy.simplemodule.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zjy.simplemodule.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static RetrofitUtils utils;
    private OkHttpClient client;

    public static RetrofitUtils getInstance() {
        if (utils == null) {
            synchronized (RetrofitUtils.class) {
                if (utils == null) {
                    utils = new RetrofitUtils();
                }
            }
        }
        return utils;
    }

    public <S> S createService(Class<S> sClass) {
        return createService(sClass, RetrofitConfig.baseUrl);
    }

    public <S> S createService(Class<S> sClass, String url) {
        return createService(sClass, url, getClient());
    }

    public <S> S createService(Class<S> sClass, String url, OkHttpClient client) {
        return getRetrofit(url, client).create(sClass);
    }

    private Retrofit getRetrofit(String url, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .writeTimeout(RetrofitConfig.writeTimeout, RetrofitConfig.timeUnit)
                    .readTimeout(RetrofitConfig.readTimeout, RetrofitConfig.timeUnit)
                    .connectTimeout(RetrofitConfig.connectTimeout, RetrofitConfig.timeUnit);
            for (Interceptor interceptor : RetrofitConfig.interceptors) {
                builder.addInterceptor(interceptor);
            }
            if (BuildConfig.BUILD_TYPE.equals("debug")) {
                builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NonNull String message) {
                        Log.e("okHttp3", "log: " + message);
                    }
                }));
            }
            client = builder.build();
        }
        return client;
    }

}
