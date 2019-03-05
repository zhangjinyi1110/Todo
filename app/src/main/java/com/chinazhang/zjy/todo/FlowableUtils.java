package com.chinazhang.zjy.todo;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FlowableUtils {

    public static <T, V, C extends Callback<T, V>> void add(T t, final C c) {
        Flowable.just(t)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<T, Publisher<V>>() {
                    @Override
                    public Publisher<V> apply(T t) throws Exception {
                        return Flowable.just(c.value(t));
                    }
                })
                .subscribe(new Subscriber<V>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(V v1) {
                        c.onNext(v1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        c.onError(t);
                    }

                    @Override
                    public void onComplete() {
                        c.onComplete();
                    }
                });
    }

}
