package com.project.xiaodong.fflibrary.constants;

import com.project.xiaodong.fflibrary.ApplicationHelper;

/**
 * Created by xiaodong.jin on 2018/2/6.
 * description：常量
 */

public class BaseConstants {

    /*Debug 版本标志*/
    public static boolean isTest = false;
    /*应用包名*/
    public final static String PACKAGE_NAME = ApplicationHelper.getApplication().getPackageName();
    /*应用版本code*/
    public static int APP_VERSION_CODE = 1;
    /** version name */
    public static String APP_VERSION_NAME = "1.0";
}
