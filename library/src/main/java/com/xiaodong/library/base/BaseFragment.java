package com.xiaodong.library.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xiaodong.library.R;
import com.xiaodong.library.utils.LoadingUtils;
import com.xiaodong.library.view.widgets.CommonHeaderView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by xiaodong.jin on 2019/05/23.
 * description：
 */

// TODO: 2019/05/23 加载出错页和空白页
public abstract  class BaseFragment extends Fragment {

    public static final int DEFAULT_HEADER = 0;
    public static final int CUSTOMER_HEADER = -1;

    /**
     * layout 加载器
     */
    protected LayoutInflater mLayoutInflater;

    /**
     * fragmentActivity context
     */
    public Context mContext;

    /**
     * 页面内容装载器,加载页面的标题（如果设置）和主界面
     */
    protected LinearLayout mRootView;

    /*header 标题栏 */
    private View mHeaderView;

    /*content 内容View */
    private View mContentView;

    /*header 自定义标题栏 */
    private CommonHeaderView mCommonHeaderView;

    /*加载框*/
    private Dialog mLoadingDialog = null;


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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        if(mContext == null) {
            mContext = getActivity();
        }

        if(mRootView == null) {
            mRootView = (LinearLayout) inflater.inflate(R.layout.lib_base_fragment_layout,null);

            if (getHeaderLayoutId() == DEFAULT_HEADER) {
                mHeaderView = mLayoutInflater.inflate(R.layout.lib_base_header, null);
                mCommonHeaderView = mHeaderView.findViewById(R.id.common_header_view);
                mRootView.addView(mHeaderView, LinearLayout.LayoutParams.MATCH_PARENT);
            } else if (getHeaderLayoutId() > CUSTOMER_HEADER) {
                mHeaderView = mLayoutInflater.inflate(getHeaderLayoutId(), null);
                mRootView.addView(mHeaderView, LinearLayout.LayoutParams.MATCH_PARENT);
            }

            if (getContentLayoutId() > -1) {
                mContentView = mLayoutInflater.inflate(getContentLayoutId(), null);
                mRootView.addView(mHeaderView, LinearLayout.LayoutParams.MATCH_PARENT);
            }

        }
        ViewGroup mViewRoot = (ViewGroup) mRootView.getParent();
        if (mViewRoot != null) {
            mViewRoot.removeView(mRootView);
        }
        initValue(savedInstanceState);
        initWidget(savedInstanceState);
        initListener(savedInstanceState);
        initData(savedInstanceState);
        return mRootView;
    }


    /**
     * 初始化变量.
     */
    protected abstract  void initValue(Bundle savedInstanceState);
    /**
     * 初始化控件.
     */
    protected abstract void initWidget(Bundle savedInstanceState);

    /**
     * init listener
     */
    protected abstract void initListener(Bundle savedInstanceState);

    /**
     * 初始化数据.
     */
    protected abstract void initData(Bundle savedInstanceState) ;



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


    /**
     * 用于网络请求回来的时候要判断fragemnt是Attached 到activity
     * 返回true 的时候可以操作ui
     *
     * @return
     */
    public boolean isAttachedToActivity() {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            return true;
        }
        return false;
    }



    /*显示载入框*/
    public void showLoading() {
        showLoading("");
    }

    public void showLoading(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingUtils.createLoadingDialog(mContext, msg);
        }
        if (isAttachedToActivity() && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
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
     * 取消载入框
     */
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 根据id查找view
     *
     * @param res
     * @return
     */
    public View findViewById(int res) {
        try {
            if (mRootView != null) {
                View view = mRootView.findViewById(res);
                return view;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
