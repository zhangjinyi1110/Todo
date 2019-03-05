package com.chinazhang.zjy.todo;

public abstract class Callback<T, V> {

    abstract V value(T t);

    public void onNext(V v) {

    }

    public void onError(Throwable throwable) {

    }

    public void onComplete() {

    }

}
