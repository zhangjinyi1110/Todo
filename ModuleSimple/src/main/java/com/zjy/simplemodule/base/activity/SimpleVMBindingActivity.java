package com.zjy.simplemodule.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import com.zjy.simplemodule.base.BaseViewModel;

public abstract class SimpleVMBindingActivity<VM extends BaseViewModel, B extends ViewDataBinding> extends SimpleVMActivity<VM> {

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
