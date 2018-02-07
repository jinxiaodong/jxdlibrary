/**
 * @file ToastUtil
 * @copyright (c) 2016 Macalline All Rights Reserved.
 * @author SongZheng
 * @date 2016/3/8
 */
package com.project.xiaodong.ff_middle_lib.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xiaodong.ff_middle_lib.R;


public class ToastUtil {
    /*******************************************************************************
     *	Public/Protected Variables
     *******************************************************************************/

    /*******************************************************************************
     * Private Variables
     *******************************************************************************/
    private static Toast mToast;
    /*******************************************************************************
     *	Overrides From Base
     *******************************************************************************/

    /*******************************************************************************
     *	Public/Protected Methods
     *******************************************************************************/
    /**
     * Toast显示
     *
     * @param context
     * @param text
     */
    public static void makeToast(Context context, String text) {
        if (!isMainThread()) {
            return;
        }

        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 带图标icon
     *
     * @param context
     * @param msgId
     */
    public static void makeIconToat(Context context, int msgId) {
        makeIconToat(context, R.mipmap.icon_comment_success, msgId);
    }

    public static void makeIconToat(Context context, int iconId, String msg) {
        if (context == null) return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_comment_toast, null);
        ImageView iv = (ImageView) v.findViewById(R.id.icon);
        TextView tv = (TextView) v.findViewById(R.id.message);
        iv.setImageResource(iconId);
        tv.setText(msg);
        makeToast(context, v);
    }

    /**
     * 图标icon
     *
     * @param context
     * @param iconId
     * @param msgId
     */
    public static void makeIconToat(Context context, int iconId, int msgId) {
        makeIconToat(context, iconId, context.getResources().getString(msgId));
    }

    /***
     * 自定义view toast
     *
     * @param context
     * @param layoutRes layout资源ID
     */
    public static void makeToast(Context context, int layoutRes) {
        if (layoutRes <= 0) return;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRes, null);
        makeToast(context, view);
    }

    /***
     * 自定义view toast
     *
     * @param context
     * @param view
     */
    public static void makeToast(Context context, View view) {
        if (!isMainThread()) {
            return;
        }
        if (view == null || context == null) return;
        if (toast == null) {
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.setView(view);
        toast.show();
    }

    static Toast toast;
    /*******************************************************************************
     *	Private Methods
     *******************************************************************************/

    /*******************************************************************************
     *	Internal Class,Interface
     *******************************************************************************/
    //#6829 bugly 解决非主线程运行问题
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
