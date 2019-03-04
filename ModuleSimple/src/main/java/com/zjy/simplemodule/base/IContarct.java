package com.zjy.simplemodule.base;

import android.os.Bundle;

public interface IContarct {

    int layoutId();
    void init(Bundle savedInstanceState);
    void initEvent();
    void initData();
    boolean isSupportBinding();
    boolean isSupportViewModel();
    void initViewModel();

}
