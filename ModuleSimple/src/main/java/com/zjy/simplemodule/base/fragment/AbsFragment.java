package com.zjy.simplemodule.base.fragment;

import android.arch.lifecycle.ViewModelProviders;

import com.zjy.simplemodule.base.BaseViewModel;
import com.zjy.simplemodule.utils.GenericityUtils;

public abstract class AbsFragment<VM extends BaseViewModel> extends BaseFragment {

    protected VM viewModel;

    @Override
    public boolean isSupportViewModel() {
        return true;
    }

    @Override
    public void initViewModel() {
        viewModel = getViewModel();
        observe();
    }

    private VM getViewModel() {
        try {
            return ViewModelProviders.of(this).get(GenericityUtils.<VM>getGenericity(getClass()));
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract void observe();

}
