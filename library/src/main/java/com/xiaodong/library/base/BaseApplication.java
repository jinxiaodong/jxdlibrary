package com.xiaodong.library.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.xiaodong.library.commons.constants.DeviceInfo;
import com.xiaodong.library.utils.LogUtils;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public class BaseApplication extends MultiDexApplication {
    private static  BaseApplication instance;
    protected static Context context;
    public static String SCHEME = "";

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        appInit();
    }

    private void appInit() {
        //屏幕信息\
        Resources resources = context.getResources();
        if (resources != null){
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LogUtils.i("Application", "============appInit, orientation:" + "横屏状态");
                DeviceInfo.WIDTHPIXELS = displayMetrics.heightPixels;
                DeviceInfo.HEIGHTPIXELS = displayMetrics.widthPixels;
            } else {
                LogUtils.i("Application", "============appInit, orientation:" + "竖屏状态");
                DeviceInfo.WIDTHPIXELS = displayMetrics.widthPixels;
                DeviceInfo.HEIGHTPIXELS = displayMetrics.heightPixels;
            }
            DeviceInfo.DENSITYDPI = displayMetrics.densityDpi;
            DeviceInfo.DENSITY = displayMetrics.density;
        }

    }
}
