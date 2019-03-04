package com.zjy.simplemodule;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.zjy.simplemodule.base.activity.BaseActivity;
import com.zjy.simplemodule.retrofit.AutoDisposeUtils;
import com.zjy.simplemodule.retrofit.BaseSubscriber;
import com.zjy.simplemodule.retrofit.HttpResultTransformer;
import com.zjy.simplemodule.retrofit.RetrofitConfig;
import com.zjy.simplemodule.retrofit.RetrofitUtils;

import java.util.List;

public class MainActivity extends BaseActivity {


    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
}
