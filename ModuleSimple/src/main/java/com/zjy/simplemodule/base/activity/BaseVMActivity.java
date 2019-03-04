package com.zjy.simplemodule.base.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;

import com.zjy.simplemodule.base.BaseViewModel;
import com.zjy.simplemodule.utils.GenericityUtils;

public abstract class BaseVMActivity<VM extends BaseViewModel> extends BaseActivity {

    protected VM viewModel;

    @Override
    public boolean isSupportViewModel() {
        return true;
    }

    @Override
    public void initViewModel() {
        super.initViewModel();
        viewModel = getViewModel();
    }

    private VM getViewModel() {
        try {
            return ViewModelProviders.of(this).get(GenericityUtils.<VM>getGenericity(this.getClass()));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getViewModel: " + e.toString());
            return null;
        }
    }
}
