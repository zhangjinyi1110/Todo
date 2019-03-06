package com.chinazhang.zjy.todo.utils;

public abstract class DBSubscribe<T, V> extends DBSimpleSubscribe<V> {

    public abstract V value(T t);

    @Override
    public void onNext(V v) {

    }

}
