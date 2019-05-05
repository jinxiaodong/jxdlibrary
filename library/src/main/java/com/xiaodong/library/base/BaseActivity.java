package com.xiaodong.library.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xiaodong.library.R;
import com.xiaodong.library.commons.mvp.BaseView;
import com.xiaodong.library.utils.LoadingUtils;
import com.xiaodong.library.utils.NetUtils;
import com.xiaodong.library.view.swipebacklayout.SwipeBackActivityHelper;
import com.xiaodong.library.view.swipebacklayout.SwipeBackLayout;
import com.xiaodong.library.view.widgets.CommonHeaderView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView, View.OnClickListener {


    public static final int DEFAULT_HEADER = 0;
    public FrameLayout rootDecodeView;

    public View netWork;

    protected LayoutInflater mLayoutInflater;

    protected Context mContext;

    private View mHeaderView;

    private View mContentView;

    /*swipebacklayout*/
    private SwipeBackActivityHelper swipeBackActivityHelper;

    private boolean isFirst = true;

    private Dialog mLoadingDialog = null;

    /*header 标题栏 */
    private CommonHeaderView mCommonHeaderView;

    /*用于在异步请求中更新ui*/
    private Handler mHandler;

    /**
     * header layout id
     * :-1:表示不使用头部布局，0：表示使用默认头部，其他：表示使用自己提供的头部布局
     * 默认值为0
     */
    protected abstract int getHeaderLayoutId();

    /**
     * content layout id
     */
    protected abstract int getContentLayoutId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.lib_base_activity_layout);
        mLayoutInflater = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        rootDecodeView = findViewById(R.id.rootDecodeView);
        LinearLayout layout = findViewById(R.id.content);
        if (getHeaderLayoutId() == DEFAULT_HEADER) {
            mHeaderView = mLayoutInflater.inflate(R.layout.lib_base_header, null);
            mCommonHeaderView = mHeaderView.findViewById(R.id.common_header_view);
            layout.addView(mHeaderView, LinearLayout.LayoutParams.MATCH_PARENT);
        } else if (getHeaderLayoutId() > -1) {
            mHeaderView = mLayoutInflater.inflate(getHeaderLayoutId(), null);
            layout.addView(mHeaderView, LinearLayout.LayoutParams.MATCH_PARENT);
        }
        if (getContentLayoutId() > -1) {
            mContentView = mLayoutInflater.inflate(getContentLayoutId(), null);
            layout.addView(mHeaderView, LinearLayout.LayoutParams.MATCH_PARENT);
        }
        if (isUseNetWorkListener()) {
            try {
                netWork = mLayoutInflater.inflate(R.layout.lib_item_networkerror, rootDecodeView, false);
                netWork.setOnClickListener(this);
                rootDecodeView.addView(layout);
                rootDecodeView.addView(netWork);
                boolean netConnected = NetUtils.isNetConnected();
                netWork.setVisibility(netConnected ? View.GONE : View.VISIBLE);
                mContext = this;
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            rootDecodeView.addView(layout);
        }

        initValue(savedInstanceState);
        initWidget(savedInstanceState);
        initListener(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFirst) {
            initData();
            isFirst = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUseNetWorkListener()) {
            boolean netConnected = NetUtils.isNetConnected();
            netWork.setVisibility(netConnected ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 初始化var
     */
    protected void initValue(Bundle onSavedInstance) {
    }

    /**
     * 初始化widget
     */
    protected void initWidget(Bundle onSavedInstance) {
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        swipeBackActivityHelper.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackActivityHelper.onPostCreate();
    }

    /**
     * init listener
     */
    protected void initListener(Bundle onSavedInstance) {
    }

    /**
     * 初始化data
     */
    protected void initData() {

    }

    public View getHeaderView() {

        return mHeaderView;
    }


    /*是否使用网络监听*/
    public boolean isUseNetWorkListener() {
        return true;
    }

    public void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    public void showActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.show();
        }
    }

    public SwipeBackLayout getSwipeBackLayout() {
        if (swipeBackActivityHelper != null) {
            return swipeBackActivityHelper.getSwipeBackLayout();
        }
        return null;
    }

    public void setEnableBackLayout(boolean enable) {
        if (getSwipeBackLayout() != null) {
            getSwipeBackLayout().setEnableGesture(enable);
        }
    }

    /*显示载入框*/
    public void showLoading() {
        showLoading("");
    }

    public void showLoading(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingUtils.createLoadingDialog(mContext, msg);
        }
        if (!this.isFinishing() && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }


    /**
     * 取消载入框
     */
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /*如果使用默认头部，可以从此方法获取，配置自己的属性*/
    public CommonHeaderView getCommonHeader() {
        if (mCommonHeaderView == null) {
            return mCommonHeaderView = new CommonHeaderView(mContext);
        }
        return mCommonHeaderView;
    }

    public void setTitle(String title) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.setTitlText(title);
            mCommonHeaderView.showOrHideTitle(true);
            mCommonHeaderView.showOrHideBar(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*设置标题*/
    public void setTitle(int strResId) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.setTitlText(getResString(strResId));
            mCommonHeaderView.showOrHideTitle(true);
            mCommonHeaderView.showOrHideBar(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*隐藏标题*/
    public void hideTitle() {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.showOrHideTitle(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shwoOrHideHeader(int viewState) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.setVisibility(viewState);
        } catch (Exception e) {
        }
    }

    /*显示返回按钮*/
    public void showBack(boolean isShow) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.showOrHideBack(true);
        } catch (Exception e) {
        }
    }

    /*隐藏返回按钮*/
    public void hideBack(boolean isShow) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.showOrHideBack(false);
        } catch (Exception e) {
        }
    }

    public String getResString(int res) {
        return getResources().getString(res) + "";
    }

    @Override
    public void onNetError(String tag, String errorMsg) {
        hideLoading();
    }

    @Override
    public void onNetStart(String tag, String startMsg) {
        showLoading();
    }

    @Override
    public void onNetFinish(String tag, String startMsg) {

    }


    @Override
    public void onClick(View view) {
        if (view == netWork) {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }
}
