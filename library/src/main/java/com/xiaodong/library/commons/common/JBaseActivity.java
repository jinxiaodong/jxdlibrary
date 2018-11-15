package com.xiaodong.library.commons.common;

import android.os.Bundle;
import android.view.View;

import com.xiaodong.library.base.BaseActivity;
import com.xiaodong.library.commons.event.NetWorkChangeEvent;
import com.xiaodong.library.commons.mvp.BaseModel;
import com.xiaodong.library.commons.mvp.BasePresenter;
import com.xiaodong.library.utils.ObjectGetByClassUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public abstract class JBaseActivity<T extends BaseModel, E extends BasePresenter> extends BaseActivity {
    public T mModel;
    public E mPresenter;
    protected boolean bActive = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mModel == null) mModel = ObjectGetByClassUtils.getClass(this, 0);
        if (mPresenter == null) mPresenter = ObjectGetByClassUtils.getClass(this, 1);
        if (mModel != null && mPresenter != null) {
            mPresenter.setMV(mModel, this);
        }
        super.onCreate(savedInstanceState);
        bActive = true;
    }

    @Override
    protected void onResume() {
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
        if (!isFinishing()) {
            if (event.CurrentNetWorkStatus == NetWorkChangeEvent.NetWorkAvailable) {
                if (netWork != null) netWork.setVisibility(View.GONE);
                netWorkChange(true);
            } else if (event.CurrentNetWorkStatus == NetWorkChangeEvent.NetWorkDisconnect) {
                if (netWork != null) netWork.setVisibility(View.VISIBLE);
                netWorkChange(false);
            }
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
        bActive = false;
    }

}
