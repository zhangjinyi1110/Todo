package com.zjy.simplemodule.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zjy.simplemodule.R;

import java.util.List;

public abstract class SimpleActivity extends BaseActivity {

    private FragmentManager manager;
    private Fragment currFragment;

    @Override
    public void init(Bundle savedInstanceState) {
        manager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            for (Fragment f : manager.getFragments()) {
                manager.beginTransaction().remove(f).commit();
            }
            manager.getFragments().clear();
        }
        currFragment = getFragment();
        if (currFragment != null) {
            manager.beginTransaction()
                    .add(getContentId(), currFragment, currFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void initData() {
    }

    @Override
    public int layoutId() {
        return R.layout.activity_simple;
    }

    protected void changeFragment(Fragment fragment) {
        if (currFragment != fragment) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(currFragment);
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(getContentId(), fragment, fragment.getClass().getSimpleName());
            }
            currFragment = fragment;
            transaction.commit();
        }
    }

    protected void showFragment(List<Fragment> fragments, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        for (Fragment f : fragments) {
            transaction.hide(f);
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(getContentId(), fragment, fragment.getClass().getSimpleName());
        }
        currFragment = fragment;
        transaction.commit();
    }

    protected void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(currFragment);
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(getContentId(), fragment, fragment.getClass().getSimpleName());
        }
        transaction.commit();
        currFragment = fragment;
    }

    protected int getContentId() {
        return R.id.simple_container;
    }

    protected abstract Fragment getFragment();

}
