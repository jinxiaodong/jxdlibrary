package com.project.xiaodong.fflibrary.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public abstract class BaseActivity extends FragmentActivity implements IUIController {

    /*******************************************************************************
     *	Public/Protected Variables
     *******************************************************************************/
    /*layout inflater*/
    protected LayoutInflater mLayoutInflater;
    /*******************************************************************************
     *	Private Variables
     *******************************************************************************/
    /*线程池manager*/
    private TaskManager mTaskManager = null;
    /*root view*/
    private LinearLayout mRootView;
    /*标题栏view*/
    private ViewGroup mHeaderView;
    /*内容块view */
    private ViewGroup mContentView;

    /*******************************************************************************
     *	Overrides From Base
     *******************************************************************************/
    @Override
    protected void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_lib_base);
        //获得任务manager class单例
        mTaskManager = TaskManager.getInstance();
        mTaskManager.registerUIController(this);

        mLayoutInflater = LayoutInflater.from(this);
        mRootView = (LinearLayout) findViewById(R.id.rootLayout);
        if (getHeaderLayoutId() > -1) {
            mHeaderView = (ViewGroup) mLayoutInflater.inflate(getHeaderLayoutId(), null);
            mRootView.addView(mHeaderView, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        if (getContentLayoutId() > -1) {
            mContentView = (ViewGroup) mLayoutInflater.inflate(getContentLayoutId(), null);
            mRootView.addView(mContentView, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
        }

        initValue(onSavedInstance);
        initWidget(onSavedInstance);
        initListener(onSavedInstance);
        initData(onSavedInstance);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
     * 初始化var
     */
    protected void initValue(Bundle onSavedInstance) {
    }

    /**
     * 初始化widget
     */
    protected void initWidget(Bundle onSavedInstance) {
    }

    /**
     * init listener
     */
    protected void initListener(Bundle onSavedInstance) {
    }

    /**
     * 初始化data
     */
    protected void initData(Bundle onSavedInstance) {

    }

    /**
     * header layout id
     */
    protected abstract int getHeaderLayoutId();

    /**
     * content layout id
     */
    protected abstract int getContentLayoutId();

    /**
     * 获得标题栏view
     */
    public ViewGroup getHeaderView() {
        return mHeaderView;
    }

    /**
     * show titlebar
     */
    public void showHeaderView() {
        if (mHeaderView != null) {
            mHeaderView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * hide titlebar
     */
    public void hideHeaderView() {
        if (mHeaderView != null) {
            mHeaderView.setVisibility(View.GONE);
        }
    }

    /**
     * 获得内容view
     */
    public ViewGroup getContentView() {
        return mContentView;
    }

    /**
     * 关闭activity
     */
    public void close() {
        finish();
    }

    /**
     * @param task
     * @return void
     * @description 执行Task任务
     */
    protected void execTask(ITask task) {
        if (task == null)
            return;
        task.setContext(this);
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
