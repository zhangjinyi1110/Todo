package com.zjy.simplemodule.retrofit;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class RetryFunction implements Function<Flowable<Throwable>, Publisher<?>> {

    private int currTryCount = 0;

    @Override
    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {
        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
            @Override
            public Publisher<?> apply(Throwable throwable) throws Exception {
                currTryCount++;
                if (currTryCount <= RetrofitConfig.retryCount) {
                    return Flowable.timer(RetrofitConfig.retryTime, TimeUnit.MILLISECONDS);
                }
                return Flowable.error(throwable);
            }
        });
    }

}
