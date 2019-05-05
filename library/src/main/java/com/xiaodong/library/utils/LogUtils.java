package com.xiaodong.library.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

/**
 * Created by wisn on 2017/8/22.
 */

public class LogUtils {

    private static boolean isLogEnable = true;

    private static String tag = "";

    public static void debug(boolean isEnable) {
        debug(tag, isEnable);
    }

    public static void debug(String logTag, boolean isEnable) {
        tag = logTag;
        isLogEnable = isEnable;
    }

    public static void v(String msg) {
        v(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isLogEnable && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) Logger.v(tag, msg);
    }

    public static void d(String msg) {
        d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isLogEnable && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) Logger.d(tag, msg);
    }

    public static void i(String msg) {
        i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isLogEnable && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) Logger.i(tag, msg);
    }

    public static void w(String msg) {
        w(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isLogEnable && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) Logger.w(tag, msg);
    }

    public static void e(String msg) {
        e(tag, msg);
    }

    public static void e(String tag, String msg) {

        if (isLogEnable && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Logger.e(tag, msg);
        }
    }

    public static void printStackTrace(Throwable t) {
        if (isLogEnable && t != null) t.printStackTrace();
    }

}
