package com.xiaodong.library.commons.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaodong.library.base.BaseFragment;
import com.xiaodong.library.commons.event.NetWorkChangeEvent;
import com.xiaodong.library.commons.mvp.BaseModel;
import com.xiaodong.library.commons.mvp.BasePresenter;
import com.xiaodong.library.utils.ObjectGetByClassUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xiaodong.jin on 2019/05/23.
 * description：
 */
public abstract class CommonFragment<T extends BaseModel,E extends BasePresenter> extends BaseFragment {
    public T mModel;
    public E mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mModel == null) mModel = ObjectGetByClassUtils.getClass(this, 0);
        if (mPresenter == null) mPresenter = ObjectGetByClassUtils.getClass(this, 1);
        if (mModel != null && mPresenter != null) {
            mPresenter.setMV(mModel, this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    /**
     * 网络变化Event
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void netWorkChangeEvent(NetWorkChangeEvent event) {
        if (event.CurrentNetWorkStatus == NetWorkChangeEvent.NetWorkAvailable) {
            netWorkChange(true);
        } else if (event.CurrentNetWorkStatus == NetWorkChangeEvent.NetWorkDisconnect) {
            netWorkChange(false);
        }
    }

    public void netWorkChange(boolean isConnect) {
//        LogUtilsLib.d("netWorkChange", this.getClass().getSimpleName()+"连接状态" + isConnect);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }
}
