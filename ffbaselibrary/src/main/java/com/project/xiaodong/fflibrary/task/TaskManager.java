package com.project.xiaodong.fflibrary.task;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Task任务管理器
 */
public class TaskManager {
	
	private static TaskManager mManager = null;
	/**
	 * 任务队列
	 */
	private Queue<ITask> mTaskQueue;
	/**
	 * 管理视图控制器
	 */
	private ArrayList<WeakReference<IUIController>> mControllerList;
	
	private TaskManager() {
		mTaskQueue = new LinkedList<ITask>();
		mControllerList = new ArrayList<WeakReference<IUIController>>();
	}

	public synchronized static TaskManager getInstance() {
		if (mManager == null)
			mManager = new TaskManager();
		return mManager;
	}

	/**
	 * 有新任务进来，唤醒线程
	 * @param task
	 */
	public void addTask(ITask task) {
		if (!mTaskQueue.contains(task)) {
			mTaskQueue.offer(task);
		}
		task.setFuture(TaskHelper.getPoolInstance().submit(TaskRunnable.getInstance()));
	}

	/**
	 * 取出队列的第一个
	 * @return
	 */
	public ITask getTask() {
		return mTaskQueue.poll();
	}

	/**
	 * 注册视图控制器
	 * @param con
	 */
	public void registerUIController(IUIController con) {
        if(con == null){
            return;
        }
		WeakReference<IUIController> weakRefer = new WeakReference<IUIController>(con);
        boolean isFind = false;
        for (WeakReference<IUIController> controller : mControllerList) {
            if (controller.get() == con) {
                isFind = true;
                break;
            }
        }
        if(!isFind){
            mControllerList.add(weakRefer);
        }
	}

	/**
	 * 移除视图控制器
	 * @param con
	 */
	public void unRegisterUIController(IUIController con) {
        boolean isFind = false;
        WeakReference<IUIController> targetController = null;
        for (WeakReference<IUIController> controller : mControllerList) {
            if (controller.get() == con) {
                targetController = controller;
                break;
            }
        }
		if (targetController != null) {
			mControllerList.remove(targetController);
		}
	}

	/**
	 * 根据task的标识获取对应的视图控制器
	 * @param identification
	 * @return
	 */
	public IUIController getUIController(String identification) {
		for (WeakReference<IUIController> controller : mControllerList) {
			if (controller.get() != null && controller.get().getIdentification().equals(identification)) {
				return controller.get();
			}
		}
		return null;
	}
	
	/**
	 * 清空任务队列及视图控制器集合
	 */
	public void destroy(){
		mTaskQueue.clear();
		mControllerList.clear();
	}
	
	/**
	 * 清空任务队列
	 */
	public void taskClear(){
		mTaskQueue.clear();
	}

}
