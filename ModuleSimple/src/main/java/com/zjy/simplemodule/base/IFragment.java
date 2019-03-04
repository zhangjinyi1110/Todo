package com.zjy.simplemodule.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IFragment extends IContarct {
    View initBinding(LayoutInflater inflater, ViewGroup container);
}
