package com.chinazhang.zjy.todo.utils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class DBSimpleSubscribe<V> implements Subscriber<V> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

}
