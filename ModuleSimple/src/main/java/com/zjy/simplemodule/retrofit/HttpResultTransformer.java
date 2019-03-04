package com.zjy.simplemodule.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.zjy.simplemodule.widget.LoadDialog;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HttpResultTransformer<T> implements FlowableTransformer<HttpResult<T>, T> {

    private Context context;
    private View loadingView;
    private LoadDialog dialog;
    private String loadText;

    public HttpResultTransformer() {
    }

    public HttpResultTransformer(Context context) {
        this.context = context;
    }

    public HttpResultTransformer(Context context, View loadingView) {
        this.context = context;
        this.loadingView = loadingView;
    }

    public HttpResultTransformer(Context context, String loadText) {
        this.context = context;
        this.loadText = loadText;
    }

    @Override
    public Publisher<T> apply(Flowable<HttpResult<T>> upstream) {
        return upstream.retryWhen(new RetryFunction())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .flatMap(new HttpResultFunction<T>())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        View view = loadingView;
                        if (context != null) {
                            if (view == null) {
                                if (!TextUtils.isEmpty(loadText))
                                    dialog = new LoadDialog(context, loadText);
                                else
                                    dialog = new LoadDialog(context);
                            } else {
                                dialog = new LoadDialog(context, view);
                            }
                            dialog.show();
                        }
                    }
                })
                .doOnCancel(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                });
    }
}
