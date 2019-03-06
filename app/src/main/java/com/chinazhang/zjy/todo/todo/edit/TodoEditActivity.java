package com.chinazhang.zjy.todo.todo.edit;

import android.support.v4.app.Fragment;

import com.zjy.simplemodule.base.activity.SimpleActivity;

public class TodoEditActivity extends SimpleActivity {
    @Override
    protected Fragment getFragment() {
        return TodoEditFragment.newInstance();
    }
}
