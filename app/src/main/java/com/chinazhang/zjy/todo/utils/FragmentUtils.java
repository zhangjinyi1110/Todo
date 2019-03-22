package com.chinazhang.zjy.todo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtils {

    public static void add(FragmentManager manager, int layoutId, Fragment fragment) {
        manager.beginTransaction()
                .add(layoutId, fragment)
                .commit();
    }

    public static void replace(FragmentManager manager, int layoutId, Fragment fragment) {
        manager.beginTransaction()
                .replace(layoutId, fragment)
                .commit();
    }

    public static void remove(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction()
                .remove(fragment)
                .commit();
    }

    public static void show(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction()
                .show(fragment)
                .commit();
    }

    public static void hide(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction()
                .hide(fragment)
                .commit();
    }

    public static Fragment showOrHide(FragmentManager manager, int layoutId, Fragment to, Fragment form) {
        if (form != to) {
            FragmentTransaction transaction = manager.beginTransaction().hide(form);
            if (to.isAdded()) {
                transaction.show(to);
            } else {
                transaction.add(layoutId, to);
            }
            transaction.commit();
            form = to;
        }
        return form;
    }

}
