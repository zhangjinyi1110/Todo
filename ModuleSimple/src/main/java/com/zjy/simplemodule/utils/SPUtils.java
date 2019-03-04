package com.zjy.simplemodule.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zjy.simplemodule.base.BaseApplication;

public class SPUtils {

    private static SPUtils utils;

    private final String FILE_NAME = "share_file";

    public static SPUtils getInstance() {
        if (utils == null) {
            synchronized (SPUtils.class) {
                if (utils == null) {
                    utils = new SPUtils();
                }
            }
        }
        return utils;
    }

    public void putString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return get().getString(key, defValue);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    private SharedPreferences get() {
        return BaseApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return get().edit();
    }

}
