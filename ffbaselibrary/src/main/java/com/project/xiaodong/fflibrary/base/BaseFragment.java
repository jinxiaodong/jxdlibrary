package com.project.xiaodong.fflibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.project.xiaodong.fflibrary.R;
import com.project.xiaodong.fflibrary.task.ITask;
import com.project.xiaodong.fflibrary.task.IUIController;
import com.project.xiaodong.fflibrary.task.MSG;
import com.project.xiaodong.fflibrary.task.TaskManager;

/**
 * Created by xiaodong.jin on 2018/2/6.
 * description：
 */

public abstract class BaseFragment extends Fragment implements IUIController {
    /*******************************************************************************
     *	Public/Protected Variables
     *******************************************************************************/

    /*******************************************************************************
     *	Private Variables
     *******************************************************************************/
    /**
     * 页面内容装载器,加载页面的标题（如果设置）和主界面
     */
    protected LinearLayout mRootView;
    /**
     * 内容布局
     */
    private View mContentView;
    /**
     * 标题,可能为空
     */
    private View mTitleView;

    /**
     * Layout inflater
     */
    protected LayoutInflater inflater;

    /**
     * fragmentActivity context
     */
    public Context context;
    /*线程池管理*/
    private TaskManager mTaskManager = null;

    /*******************************************************************************
     *	Overrides From Base
     *******************************************************************************/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获得任务管理类单例
        mTaskManager = TaskManager.getInstance();
        mTaskManager.registerUIController(this);
    }

    @Override
    final public View onCreateView(LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState) {
        this.inflater = inflater;
        if (context == null) {
            context = getActivity();
        }

        if (mRootView == null) {
            mRootView = (LinearLayout) inflater.inflate(R.layout.fragment_lib_base, null);
            if (getHeaderLayoutId() != -1) {
                // 加载页面的标题,可以使用自定义的页面标题布局
                mTitleView = inflater.inflate(getHeaderLayoutId(), null);
                mRootView.addView(mTitleView,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            // 加载主界面资源（标题除外）
            if (getContentLayoutId() != -1) {
                mContentView = (ViewGroup) inflater.inflate(getContentLayoutId(), null);
                mRootView.addView(mContentView,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTaskManager.unRegisterUIController(this);
    }

    @Override
    public void refreshUI(int id, MSG msg) {
    }

    /**
     * for task, to identifier activity or fragment
     * 尽量不要修改
     */
    @Override
    public String getIdentification() {
        return getClass().toString() + this;
    }
    /*******************************************************************************
     *	Public/Protected Methods
     *******************************************************************************/
    /**
     * 获得内容view
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 获得标题view,可能为空
     */
    public View getHeaderView() {
        return mTitleView;
    }

    /**
     * 初始化变量.
     */
    protected void initValue(Bundle savedInstanceState) {
    }

    /**
     * 初始化控件.
     */
    protected void initWidget(Bundle savedInstanceState) {
    }

    /**
     * init listener
     */
    protected void initListener(Bundle savedInstanceState) {

    }

    /**
     * 初始化数据.
     */
    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 每个页面需要实现该方法返回一个该页面所对应的资源ID
     *
     * @return 页面资源ID
     */
    protected abstract int getContentLayoutId();

    /**
     * 如果需要自定义页面标题，则需要重载该方法
     *
     * @return
     */
    protected int getHeaderLayoutId() {
        return -1;
    }

    /**
     * 执行Task任务
     *
     * @param task
     */
    protected void execuTask(ITask task) {
        if (task == null)
            return;
        task.setContext(getActivity());
        task.setmIdentification(getIdentification());
        mTaskManager.addTask(task);
    }
    /*******************************************************************************
     *	Private Methods
     *******************************************************************************/

    /*******************************************************************************
     *	Internal Class,Interface
     *******************************************************************************/
}
