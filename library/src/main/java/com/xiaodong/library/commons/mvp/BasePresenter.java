package com.xiaodong.library.commons.mvp;

import com.xiaodong.library.net.rxjava.RxManager;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：mvp Presenter层基类
 */

public abstract  class BasePresenter<E,T> {
    public E mModel;
    public T mView;
    private RxManager rxManager;

    public void setMV(E model, T view) {
        this.mModel = model;
        this.mView = view;
    }

    public RxManager getRxManager() {
        if (rxManager == null) {
            synchronized (BasePresenter.class) {
                if (rxManager == null) {
                    rxManager = new RxManager();
                }
            }
        }
        return rxManager;
    }

    public void onDestroy() {
//        getRxManager().clear();
        if (rxManager != null) {
            rxManager = null;
        }
    }

}
