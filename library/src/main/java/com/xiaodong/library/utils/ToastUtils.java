package com.xiaodong.library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by xiaodong.jin on 2019/5/5 .
 * 功能描述：
 */

public class ToastUtils {
    private static Toast toast;

    public static void show(String msg) {
       show(null,msg);
    }

    public static void show(Context context , String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        if (context != null){
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
            return ;
        }
        if (toast == null) {
            toast = Toast.makeText(Utils.getApp().getApplicationContext(),msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }



    public static void showGravity(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        if (toast == null) {
            toast = Toast.makeText(Utils.getApp().getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public static void show(int id) {
        show(Utils.getApp().getString(id));
    }


}
