package com.project.xiaodong.fflibrary;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * Created by xiaodong.jin on 2018/2/6.
 */

public class ApplicationHelper {
    public static Application getApplication() {
        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            Method method = clazz.getMethod("currentApplication");
            return (Application) method.invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
