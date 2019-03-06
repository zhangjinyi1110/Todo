package com.chinazhang.zjy.todo.utils;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DBFlowableUtils {

    public static <T, V, C extends DBSubscribe<T, V>> void getResult(T t, final C c) {
        Flowable.just(t)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<T, Publisher<V>>() {
                    @Override
                    public Publisher<V> apply(T t) throws Exception {
                        return Flowable.just(c.value(t));
                    }
                })
                .subscribe(c);
    }

    public static <T> void query(Flowable<T> flowable, Subscriber<T> subscriber) {
        flowable.subscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

}
