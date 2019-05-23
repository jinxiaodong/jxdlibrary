package com.xiaodong.library.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView, View.OnClickListener {


    public static final int DEFAULT_HEADER = 0;
    public static final int CUSTOMER_HEADER = -1;
    public FrameLayout rootDecodeView;

    public View netWork;

    /*LayoutInflater*/
    protected LayoutInflater mLayoutInflater;

    /*activity*/
    protected Context mContext;

    /*header 标题栏 */
    private View mHeaderView;

    /*content 内容View */
    private View mContentView;

    /*swipebacklayout*/
    private SwipeBackActivityHelper swipeBackActivityHelper;

    /*是否第一次加载*/
    private boolean isFirst = true;

    /*加载框*/
    private Dialog mLoadingDialog = null;

    /*header 标题栏 */
    private CommonHeaderView mCommonHeaderView;

    /*是否正在切换Fragment*/
    private boolean isSwitchFragmenting = false;

    /*用于在异步请求中更新ui*/
//    private Handler mHandler;

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
        } else if (getHeaderLayoutId() > CUSTOMER_HEADER) {
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
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


    /**
     * @param resLayId
     * @param fragment
     * @param isAddBackStack 是否加入返回栈
     * @description 替换Fragment (默认有动画效果)
     */
    protected void replaceFragment(int resLayId, Fragment fragment, boolean isAddBackStack) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.lib_slide_in_left,
                R.anim.lib_slide_out_right, R.anim.lib_slide_in_left,
                R.anim.lib_slide_out_right);
        fragmentTransaction.replace(resLayId, fragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        isSwitchFragmenting = false;
    }

    /**
     * @param resLayId       resid
     * @param fragment       fragment
     * @param isAddBackStack 是否加入返回栈
     * @param isAnimation    切换动画
     * @return
     * @description 替换Fragment
     */
    protected void replaceFragment(int resLayId, Fragment fragment,
                                   boolean isAddBackStack, boolean isAnimation) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.lib_slide_in_left,
                    R.anim.lib_slide_out_right, R.anim.lib_slide_in_left,
                    R.anim.lib_slide_out_right);
        }
        fragmentTransaction.replace(resLayId, fragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        isSwitchFragmenting = false;
    }

    /**
     * @param resLayId
     * @param showFragment
     * @param isAnimation
     * @param isAddBackStack
     * @param hideFragments  要隐藏的Fragment数组
     * @return
     * @description 添加Fragment
     */
    protected void addFragment(int resLayId, Fragment showFragment,
                               boolean isAnimation, boolean isAddBackStack,
                               Fragment... hideFragments) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (isAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.lib_slide_in_left,
                    R.anim.lib_slide_out_right, R.anim.lib_slide_in_left,
                    R.anim.lib_slide_out_right);
        }
        if (hideFragments != null) {
            for (Fragment hideFragment : hideFragments) {
                if (hideFragment != null)
                    fragmentTransaction.hide(hideFragment);
            }
        }
        fragmentTransaction.add(resLayId, showFragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        isSwitchFragmenting = false;
    }

    /**
     * @param showFragment   显示的fragment
     * @param hideFragments  要隐藏的Fragment数组
     * @param isAddBackStack 是否加入返回栈
     * @description 显示隐藏Fragment
     */
    protected void showHideFragment(Fragment showFragment, boolean isAnimation,
                                    boolean isAddBackStack, Fragment... hideFragments) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (isAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.lib_slide_in_left,
                    R.anim.lib_slide_out_right, R.anim.lib_slide_in_left,
                    R.anim.lib_slide_out_right);
        }
        if (hideFragments != null) {
            for (Fragment hideFragment : hideFragments) {
                if (hideFragment != null && hideFragment.isAdded())
                    fragmentTransaction.hide(hideFragment);
            }
        }
        if (showFragment != null) {
            fragmentTransaction.show(showFragment);
        }
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        //#5266 nSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
        //再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()
        fragmentTransaction.commitAllowingStateLoss();
        isSwitchFragmenting = false;
    }

    /*
     * todo 如果有双击需求：那么需要新增配置
     * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
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

    /**
     * 获取字符串资源
     *
     * @param res
     * @return
     */
    public String getResString(int res) {
        return getResources().getString(res) + "";
    }

    /**
     * 获取颜色资源
     *
     * @param colorId
     * @return
     */
    public int getResColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * 获取设置的头部布局
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }


    /*********************start CommonHeaderView 配置方法**************************/
    public CommonHeaderView getCommonHeader() {
        if (mCommonHeaderView == null) {
            return mCommonHeaderView = new CommonHeaderView(mContext);
        }
        return mCommonHeaderView;
    }

    /**
     * 设置默认头部标题
     *
     * @param title
     */
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

    /**
     * 设置默认头部标题
     */
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

    /**
     * 隐藏默认头部标题
     */
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

    /**
     * 显示或隐藏默认头部
     *
     * @param viewState
     */
    public void shwoOrHideHeader(int viewState) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.setVisibility(viewState);
        } catch (Exception e) {
        }
    }

    /**
     * 显示默认头部返回按钮
     */
    public void showBack() {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.showOrHideBack(true);
        } catch (Exception e) {
        }
    }

    /**
     * 隐藏默认头部返回按钮
     */
    public void hideBack() {
        if (getHeaderView() == null) {
            return;
        }
        try {
            mCommonHeaderView.showOrHideBack(false);
        } catch (Exception e) {
        }
    }

    /*********************end CommonHeaderView 配置方法**************************/


    private long lastClickTime;

    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= 300;
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
