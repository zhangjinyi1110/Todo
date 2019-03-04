package com.chinazhang.zjy.todo;

import android.support.v4.app.Fragment;

import com.zjy.simplemodule.base.activity.SimpleActivity;

public class TodoListActivity extends SimpleActivity {

    @Override
    protected Fragment getFragment() {
        return TodoListFragment.newInstance();
    }
}
