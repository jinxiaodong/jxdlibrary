package com.project.xiaodong.fflibrary.task;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import java.util.concurrent.Future;

/**
 * 任务抽象类
 */
public abstract class ITask {

	/**
	 *  任务Id
	 */
	protected int mTaskId;
	/**
	 *  指定任务所属的IUIController的标识
	 */
	protected String mIdentification;
	/**
	 * 上下文
	 */
	protected Activity mContext;
    /**
     * 用来控制runnable
     * */
	private Future<?> mFuture;

	/**
	 * 
	 * @param taskId 任务ID
	 */
	public ITask(int taskId) {
		this.mTaskId = taskId;
	}

	/**
	 * 处理任务,并返回结果
	 * @return
	 */
	public abstract MSG doTask();
	
	/**
	 * 处理任务,并通过Handler返回结果
	 * @return MSG
	 */
	public MSG doTask(Handler handler){
		return null;
	}
	
	/**
	 * 中断取消任务
	 * @return
	 */
	public boolean cancel(){
		if(mFuture != null && !mFuture.isCancelled() && !mFuture.isDone()){
			return mFuture.cancel(true);
		}
		return false;
	}

	/**
     * 任务是否被取消
     * */
	public boolean isCancled(){
        if(mFuture != null){
            return mFuture.isCancelled();
        }
        return true;
    }

    /**
     * 任务是否执行完毕
     * */
    public boolean isDown(){
        if (mFuture != null){
            return mFuture.isDone();
        }
        return true;
    }

	public int getTaskId() {
		return mTaskId;
	}
	public void setmIdentification(String mIdentification) {
		this.mIdentification = mIdentification;
	}
	public String getmIdentification() {
		return mIdentification;
	}
	public void setContext(Activity context) {
		this.mContext = context;
	}
	public Context getContext() {
		return mContext;
	}
	public void setFuture(Future<?> future) {
		this.mFuture = future;
	}
	
}
