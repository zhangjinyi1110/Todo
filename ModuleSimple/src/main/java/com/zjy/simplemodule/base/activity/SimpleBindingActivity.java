package com.zjy.simplemodule.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

public abstract class SimpleBindingActivity<B extends ViewDataBinding> extends SimpleActivity {

    protected B binding;

    @Override
    public boolean isSupportBinding() {
        return true;
    }

    @Override
    public void initBinding() {
        super.initBinding();
        binding = DataBindingUtil.setContentView(this, layoutId());
    }
}
