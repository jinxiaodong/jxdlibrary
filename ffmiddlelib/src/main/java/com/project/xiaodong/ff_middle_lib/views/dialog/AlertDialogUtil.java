package com.project.xiaodong.ff_middle_lib.views.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.xiaodong.ff_middle_lib.R;
import com.project.xiaodong.ff_middle_lib.utils.FileUtil;

import java.lang.ref.WeakReference;

/**
 * 警告框
 */

public class AlertDialogUtil {

    private Activity mActivity;
    public Dialog dialog;
    private View view;
    private View splitLine;
    private TextView titleTV, messageTV;
    private Button negativeButton, positiveButton;
    private LinearLayout contentLayout;
    private boolean isHasNegativeButton, isPositiveButton;

    //下载进度
    private ProgressBar mProgressBar;
    private TextView mTvSize;
    private TextView mTvProgress;


    public AlertDialogUtil(Activity activity) {
        WeakReference<Activity> activityWeakRef = new WeakReference<Activity>(activity);
        if (activityWeakRef.get() == null || activityWeakRef.get().isFinishing()) {
            return;
        }
        mActivity = activityWeakRef.get();
        while (mActivity.getParent() != null) {
            mActivity = mActivity.getParent();
        }
        dialog = new Dialog(mActivity, R.style.alertDialog_style);
        view = View.inflate(mActivity, R.layout.layout_alertdialog_view, null);
        titleTV = (TextView) view.findViewById(R.id.title);
        messageTV = (TextView) view.findViewById(R.id.message);
        contentLayout = (LinearLayout) view.findViewById(R.id.content);
        //取消按钮
        negativeButton = (Button) view.findViewById(R.id.NO);
        //确定按钮
        positiveButton = (Button) view.findViewById(R.id.YES);
        //分割线
        splitLine = view.findViewById(R.id.view);
        // 隐藏标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();
        int mWith = displayMetrics.widthPixels;
        dialog.setContentView(view,
                new LinearLayout.LayoutParams((int) (mWith * 0.7), LinearLayout.LayoutParams.WRAP_CONTENT));
    }


    public void show() {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        //判断按钮显示的数量
        if (isHasNegativeButton && isPositiveButton)
            splitLine.setVisibility(View.VISIBLE);
        else {
            splitLine.setVisibility(View.GONE);
            if (isHasNegativeButton)
                negativeButton.setBackgroundResource(R.drawable.selector_alertdialog_btn_bg);
            if (isPositiveButton)
                positiveButton.setBackgroundResource(R.drawable.selector_alertdialog_btn_bg);
        }
        dialog.show();
    }

    public AlertDialogUtil createProgress() {
        View view = View.inflate(mActivity, R.layout.dialog_download_view, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.my_profile_tracker);
        mTvSize = (TextView) view.findViewById(R.id.tv_file_size);
        mTvProgress = (TextView) view.findViewById(R.id.tv_progress_percent);
        addView(view);
        return this;
    }

    public AlertDialogUtil addView(View view) {
        if (view != null && contentLayout != null) {
            contentLayout.removeAllViews();
            contentLayout.addView(view);
        }
        return this;
    }

    public AlertDialogUtil addView(int redId) {
        View view = null;
        if (redId > 0 && mActivity != null) {
            view = View.inflate(mActivity, redId, null);
        }
        return addView(view);
    }

    /***
     * 更新进度
     */
    public void updateProgress(long count, long current) {
        if (mProgressBar != null && mTvSize != null && mTvProgress != null) {
            if (count > 0) {
                int currentProgress = (int) (100 * current / count);
                mProgressBar.setProgress((int) currentProgress);
                mTvProgress.setText(String.format("$s%%", String.valueOf(currentProgress)));
                mTvSize.setText(String.format("%1$s/%2$s", FileUtil.formatFileSize(current), FileUtil.formatFileSize(count)));
            }
        }
    }

    /**
     * 按钮监听
     */

    public interface OnClickListener {
        void onClick(DialogInterface dialog);
    }

    public AlertDialogUtil setTitle(String title) {
        if (titleTV != null && !TextUtils.isEmpty(title)) {
            titleTV.setText(title);
            titleTV.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public AlertDialogUtil setTitle(int redId) {
        String title = "";
        if (mActivity != null && redId > 0) {
            title = mActivity.getString(redId);
        }
        return setTitle(title);
    }

    public AlertDialogUtil setMessage(String message) {
        if (messageTV != null && !TextUtils.isEmpty(message)) {
            messageTV.setText(message);
            messageTV.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public AlertDialogUtil setMessage(int redId) {
        String message = "";
        if (mActivity != null){
            if (redId > 0) {
                message = mActivity.getString(redId);
            }
        }
        return setMessage(message);
    }

    public AlertDialogUtil setNegativeButton(String text, final OnClickListener listener) {
        if (negativeButton != null){
            isHasNegativeButton = true;
            if (!TextUtils.isEmpty(text)){
                negativeButton.setText(text);
            }
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener != null)
                        listener.onClick(dialog);
                }
            });
        }
        return this;
    }

    public AlertDialogUtil setNegativeButton(int redId, final OnClickListener listener) {
        if (redId > 0 && mActivity != null) {
            return setNegativeButton(mActivity.getString(redId), listener);
        } else {
            return setNegativeButton("", listener);
        }
    }

    public AlertDialogUtil setPositiveButton(String text, final OnClickListener listener) {
        if (positiveButton != null){
            isPositiveButton = true;
            if (!TextUtils.isEmpty(text))
                positiveButton.setText(text);
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener != null)
                        listener.onClick(dialog);
                }
            });
        }
        return this;
    }

    public AlertDialogUtil setPositiveButton(int redId, final OnClickListener listener) {
        if (redId > 0 && mActivity != null) {
            return setPositiveButton(mActivity.getString(redId), listener);
        } else {
            return setPositiveButton("", listener);
        }
    }


    public boolean isShowing() {
        if (dialog != null)
            return dialog.isShowing();
        else
            return false;
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public AlertDialogUtil setCanceledOnTouchOutside(boolean cancel) {
        if (dialog != null){
            dialog.setCanceledOnTouchOutside(cancel);
        }
        return this;
    }

    public AlertDialogUtil setCancelable(boolean flag) {
        if (dialog != null){
            dialog.setCancelable(flag);
        }
        return this;
    }

    public TextView getMssageTextView() {
        return messageTV;
    }

    public void setNegativeButtonTextColor(int colorId) {
        if (mActivity != null && negativeButton != null){
            negativeButton.setTextColor(mActivity.getResources().getColor(colorId));
        }
    }

    public void setPositiveButtonTextColor(int colorId) {
        if (mActivity != null && negativeButton != null){
            positiveButton.setTextColor(mActivity.getResources().getColor(colorId));
        }
    }

}
