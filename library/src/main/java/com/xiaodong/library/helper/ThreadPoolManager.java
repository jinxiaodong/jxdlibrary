package com.xiaodong.library.helper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaodong.jin on 2019/5/5 .
 * 功能描述：顺序执行Runnable task
 */
public class ThreadPoolManager {
    private ExecutorService service;
    private final SerialExecutor serialExecutor;
    private static final ThreadPoolManager manager = new ThreadPoolManager();

    private ThreadPoolManager() {
        int num = Runtime.getRuntime().availableProcessors() * 20;
        service = Executors.newFixedThreadPool(num);
        serialExecutor = new SerialExecutor(service);
    }

    public static ThreadPoolManager getInstance() {
        return manager;
    }

    /**
     * 顺序执行一个任务
     *
     * @param runnable              任务
     * @param isSequentialExecution 是否顺序执行
     */
    public void executeTask(Runnable runnable, boolean isSequentialExecution) {
        if (isSequentialExecution) {
            serialExecutor.execute(runnable);
        } else {
            service.execute(runnable);
        }
    }

    /**
     * 执行一个任务
     *
     * @param runnable
     */
    public void executeTask(Runnable runnable) {
        executeTask(runnable, false);
    }

    public void executeTasks(ArrayList<Runnable> list, boolean isSequentialExecution) {
        for (Runnable runnable : list) {
            executeTask(runnable, isSequentialExecution);
        }
    }

    /**
     * 执行AsyncTask
     *
     * @param task
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("unchecked")
    public void execAsync(AsyncTask<?, ?, ?> task) {
        if (Build.VERSION.SDK_INT >= 11) {
            //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            task.executeOnExecutor(Executors.newCachedThreadPool());
        } else {
            task.execute();
        }

    }

}
