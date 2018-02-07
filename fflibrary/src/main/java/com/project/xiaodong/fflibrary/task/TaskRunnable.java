package com.project.xiaodong.fflibrary.task;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.Serializable;

/**
 * Task线程
 */
public class TaskRunnable implements Runnable {
	
	private static TaskRunnable mTaskRunnable = null;
	private Handler mHandler;

	private TaskRunnable(Handler mHandler) {
		super();
		this.mHandler = mHandler;
	}

	public synchronized static TaskRunnable getInstance(Handler mHandler) {
		if (mTaskRunnable == null)
			mTaskRunnable = new TaskRunnable(mHandler);
		return mTaskRunnable;
	}
	
	public synchronized static TaskRunnable getInstance() {
		return mTaskRunnable;
	}
	
	@Override
	public void run() {
		ITask task = TaskManager.getInstance().getTask();
		if (mHandler == null || task == null)
			return;
		//task的具体执行代码是在自己定义的task里通过抽象方法实现的
        //注意:是在子thread里执行的
		Object result = task.doTask();
		Bundle bundle = new Bundle();
		bundle.putInt(TaskHelper.INTENT_KEY_TASK_ID, task.getTaskId());
		bundle.putString(TaskHelper.INTENT_KEY_IDENTIFICATION, task.getmIdentification());
		bundle.putSerializable(TaskHelper.INTENT_KEY_RESULT, (Serializable) result);
		
		Message msg = mHandler.obtainMessage();
		msg.setData(bundle);
		msg.sendToTarget();
	}
	
}
