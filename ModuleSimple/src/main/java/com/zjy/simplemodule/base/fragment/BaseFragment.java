package com.zjy.simplemodule.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjy.simplemodule.base.IFragment;

public abstract class BaseFragment extends Fragment implements IFragment {

    private FragmentActivity activity;
    private boolean isFirst = true;
    private boolean isLazy = true;
    protected final String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            activity = (FragmentActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSupportViewModel())
            initViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (isSupportBinding()) {
            view = initBinding(inflater, container);
        } else {
            view = inflater.inflate(layoutId(), container);
        }
        init(savedInstanceState);
        initEvent();
        if (!isLazy() || getUserVisibleHint()) {
            initData();
            isLazy(false);
        }
        isFirst = false;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFirst && isLazy()) {
            initData();
            isLazy(false);
        }
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
    public View initBinding(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initViewModel() {

    }

    public boolean isLazy() {
        return isLazy;
    }

    public void isLazy(boolean lazy) {
        isLazy = lazy;
    }

    public FragmentActivity getSelfActivity() {
        return activity;
    }
}
