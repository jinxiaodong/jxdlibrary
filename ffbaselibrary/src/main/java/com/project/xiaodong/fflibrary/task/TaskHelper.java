package com.project.xiaodong.fflibrary.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @file TaskHelper
 * @copyright (c) 2017 Macalline All Rights Reserved.
 * @date 2017/12/7
 */

public class TaskHelper {

    public static String INTENT_KEY_TASK_ID = "taskId";
    public static String INTENT_KEY_IDENTIFICATION = "identification";
    public static String INTENT_KEY_RESULT = "result";

    public static TaskHelper mInstance;
    /*thread pool*/
    private static ExecutorService pool;

    /**
     * default constructor
     * */
    private TaskHelper(){
    }
    /**
     * 获取单例
     * */
    public synchronized  static TaskHelper getInstance(){
        if (mInstance == null){
            mInstance = new TaskHelper();
        }
        return mInstance;
    }

    /**
     * 任务管理类初始化
     * */
    public void taskInit(){
        TaskManager.getInstance();
        TaskRunnable.getInstance(mHandler);
    }

    /**
     * sigle instance for thread pool
     * @return
     */
    public static ExecutorService getPoolInstance() {
        if(pool == null || (pool != null && pool.isShutdown())) {
            pool = Executors.newFixedThreadPool(10);
        }
        return pool;
    }

    /**
     * 清理
     */
    public static void clear(){
        if(pool != null && !pool.isShutdown()) {
            pool.shutdownNow();
        }
        pool = null;
    }

    /**
     * destroy task manager
     * */
    public void destroy(){
        clear();
        TaskManager.getInstance().destroy();
    }

    /**
     * 通知IUIController 刷新控件
     */
    protected static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            if (bundle == null)
                return;
            int id = bundle.getInt(INTENT_KEY_TASK_ID);
            String identification = bundle.getString(INTENT_KEY_IDENTIFICATION);
            MSG result = (MSG) bundle.get(INTENT_KEY_RESULT);
            IUIController controller = TaskManager.getInstance().getUIController(identification);
            if (controller == null)
                return;
            controller.refreshUI(id, result);
        }
    };
}
