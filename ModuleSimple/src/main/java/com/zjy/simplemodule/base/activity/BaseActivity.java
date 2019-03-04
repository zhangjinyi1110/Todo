package com.zjy.simplemodule.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zjy.simplemodule.R;
import com.zjy.simplemodule.base.IActivity;
import com.zjy.simplemodule.utils.ActivityManager;

public abstract class BaseActivity extends AppCompatActivity implements IActivity {

    protected final String TAG = getClass().getSimpleName();
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        if (isSupportBinding()) {
            initBinding();
        } else {
            setContentView(layoutId());
        }
        if (isSupportViewModel()) {
            initViewModel();
        }
        toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                initToolbar(getSupportActionBar());
        }
        init(savedInstanceState);
        initEvent();
        initData();
    }

    private void initToolbar(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    private Toolbar getToolbar() {
        return null;
    }

    @Override
    public boolean isSupportBinding() {
        return false;
    }

    @Override
    public boolean isSupportViewModel() {
        return false;
    }

    @Override
    public void initBinding() {

    }

    @Override
    public void initViewModel() {

    }

    protected BaseActivity getSelf() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return onHomeClick();
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean onHomeClick() {
        finish();
        return true;
    }

}
