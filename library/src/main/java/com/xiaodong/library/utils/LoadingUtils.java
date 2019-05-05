package com.xiaodong.library.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaodong.library.R;
import com.xiaodong.library.commons.constants.DeviceInfo;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public class LoadingUtils {

    public static Dialog createLoadingDialog(Context context, String msg, boolean canceled) {
        View v = LayoutInflater.from(context).inflate(R.layout.lib_loaddialog, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        if (!TextUtils.isEmpty(msg)) {
            TextView tvMsg = (TextView) layout.findViewById(R.id.tipTextView);
            tvMsg.setText(msg);
        }


        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCanceledOnTouchOutside(canceled);// 空白区域不可以点击
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                DeviceInfo.WIDTHPIXELS / 3 * 2,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局

        return loadingDialog;
    }


    public static Dialog createLoadingDialog(Context context, String msg) {
        return createLoadingDialog(context, msg, false);
    }
}
