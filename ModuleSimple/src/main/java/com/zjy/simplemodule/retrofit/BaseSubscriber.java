package com.zjy.simplemodule.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.zjy.simplemodule.base.BaseApplication;
import com.zjy.simplemodule.utils.ActivityManager;
import com.zjy.simplemodule.utils.NetWorkUtils;
import com.zjy.simplemodule.utils.ToastUtils;
import com.zjy.simplemodule.widget.LoadDialog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class BaseSubscriber<T> implements Subscriber<T> {

    private Context context;
    private LoadDialog dialog;
    private View loadView;
    private String loadText;
    private boolean showLoad;

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    public BaseSubscriber(Context context, View loadView) {
        this.context = context;
        this.loadView = loadView;
    }

    public BaseSubscriber(Context context, String loadText) {
        this.context = context;
        this.loadText = loadText;
    }

    public BaseSubscriber(Context context, boolean showLoad) {
        this.context = context;
        this.showLoad = showLoad;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
        if (!NetWorkUtils.isNetworkAvailable(BaseApplication.getContext())) {
            ToastUtils.showToastShort(ActivityManager.getInstance().getCurrActivity(), "没有网络服务");
            s.cancel();
            return;
        }
        if (loadView != null) {
            dialog = new LoadDialog(context, loadView);
        } else if (!TextUtils.isEmpty(loadText)) {
            dialog = new LoadDialog(context, loadText);
        } else if (showLoad) {
            dialog = new LoadDialog(context);
        }
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        if (dialog != null) {
            dialog.dismiss();
        }
        onFailure(new HttpResultException(t));
    }

    @Override
    public void onComplete() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public abstract void onSuccess(T t);

    public void onFailure(HttpResultException exception) {
        if (context != null)
            ToastUtils.showToastShort(context, exception.getErrorMessage());
    }
}
