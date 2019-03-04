package com.zjy.simplemodule.retrofit;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class HttpResultFunction<T> implements Function<HttpResult<T>, Publisher<T>> {

    @Override
    public Publisher<T> apply(HttpResult<T> tHttpResult) throws Exception {
        if (RetrofitConfig.successCode != tHttpResult.getErrorCode()) {
            return Flowable.error(new HttpFailureException(tHttpResult.getErrorCode(), tHttpResult.getErrorMsg()));
        }
        return Flowable.just(tHttpResult.getData());
    }
}
