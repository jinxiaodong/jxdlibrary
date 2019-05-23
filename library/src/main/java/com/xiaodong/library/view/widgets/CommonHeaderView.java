package com.xiaodong.library.view.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaodong.library.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：
 */

public class CommonHeaderView extends FrameLayout {

    /*默认除title外的所有view都是隐藏的*/

    private LinearLayout titleBarGroup;
    private TextView titleBarGuider;        //1dp，用于沉浸式
    private RelativeLayout titleBarTitle;   //header的布局
    private ImageView titleBarBack;         //返回按钮
    private TextView titleBarTitleText;     //title标题
    private TextView titleBarOk;            //完成 ok
    private ImageView ivShareInformation;   //分享按钮
    private ImageView ivButtonRight;        //右边按钮
    private LinearLayout nativeTitlePanxu;  //排序
    private TextView nativeTitleTv;         //排序标题
    private ImageView nativeTitleIamge;     //排序的图标


    private Context mContext;


    /*返回按钮点击*/
    private onBackClickListener mOnBackClickListener;
    /*完成按钮点击*/
    private onTitleBarOkClickListener mOnTitleBarOkClickListener;
    /*分享按钮的点击*/
    private onIvShareClickListener mOnIvShareClickListener;
    /*右边按钮的点击*/
    private onButtonRightClickListener mOnButtonRightClickListener;
    /*右边排序点击*/
    private onPaixuClickListener mOnPaixuClickListener;

    public CommonHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public CommonHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.lib_base_common_header, this);

        titleBarGroup = (LinearLayout) view.findViewById(R.id.title_bar_group);
        titleBarGuider = (TextView) view.findViewById(R.id.title_bar_guider);
        titleBarTitle = (RelativeLayout) view.findViewById(R.id.title_bar_title);
        titleBarBack = (ImageView) view.findViewById(R.id.title_bar_back);
        titleBarTitleText = (TextView) view.findViewById(R.id.title_bar_title_text);
        titleBarOk = (TextView) view.findViewById(R.id.title_bar_ok);
        ivShareInformation = (ImageView) view.findViewById(R.id.iv_share_information);
        ivButtonRight = (ImageView) view.findViewById(R.id.iv_right);
        nativeTitlePanxu = (LinearLayout) view.findViewById(R.id.native_title_panxu);
        nativeTitleTv = (TextView) view.findViewById(R.id.native_title_tv);
        nativeTitleIamge = (ImageView) view.findViewById(R.id.native_title_iamge);


        titleBarBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBackClickListener != null) {
                    mOnBackClickListener.onTitleBarBackClick(v);
                }
            }
        });

        titleBarOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTitleBarOkClickListener != null) {
                    mOnTitleBarOkClickListener.onTitleBarOkClick(v);
                }
            }
        });

        ivShareInformation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnIvShareClickListener != null) {
                    mOnIvShareClickListener.onIvShareClick(v);
                }
            }
        });
        ivButtonRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonRightClickListener != null) {
                    mOnButtonRightClickListener.onButtonRightClick(v);
                }
            }
        });

        nativeTitlePanxu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPaixuClickListener != null) {
                    mOnPaixuClickListener.onPaixuClick(v);
                }
            }
        });
    }


    /*设置Title标题内容*/
    public void setTitlText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.titleBarTitleText.setText(text);
        }
    }

    /*设置完成按钮内容*/
    public void setTitleBarOkText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.titleBarOk.setText(text);
        }
    }

    /*沉浸式时设置的状态栏高度*/
    public void setTitleBarGuiderHeight(int height) {
        titleBarGuider.setHeight(height);
    }

    /*设置右边按钮图标*/
    public void setIvButtonRightResourse(int rsd) {
        this.ivButtonRight.setBackgroundResource(rsd);
    }


    /*设置排序标题*/
    public void setNativeTitleTv(String text) {
        if (TextUtils.isEmpty(text)) {
            this.nativeTitleTv.setText(text);
        }
    }


    /*设置背景颜色*/
    public void setBackgroundColor(int color) {
        if (titleBarGroup != null) {
            titleBarGroup.setBackgroundColor(color);
        }
    }

    public void setBackResource(int resId) {
        this.titleBarBack.setImageResource(resId);
    }

    public void setButtonRightResource(int resId) {
        this.ivButtonRight.setImageResource(resId);
    }

    /*******************显示或隐藏图标****************************/

       /*title的显示和隐藏*/
    public void showOrHideTitle(boolean isShow) {
        if (isShow) {
            titleBarTitleText.setVisibility(VISIBLE);
        } else {
            titleBarTitleText.setVisibility(GONE);
        }
    }

    /*整个bar的显示和隐藏*/
    public void showOrHideBar(boolean isShow) {
        if (isShow) {
            titleBarGroup.setVisibility(VISIBLE);
        } else {
            titleBarGroup.setVisibility(GONE);
        }
    }

    /*显示隐藏返回按钮*/
    public void showOrHideBack(boolean isShow) {
        if (isShow) {
            titleBarBack.setVisibility(VISIBLE);
        } else {
            titleBarBack.setVisibility(GONE);
        }
    }

    /*显示隐藏完成按钮*/
    public void showOrHideTitleBarOk(boolean isShow) {
        if (isShow) {
            titleBarOk.setVisibility(VISIBLE);
        } else {
            titleBarOk.setVisibility(GONE);
        }
    }

    /*显示隐藏分享按钮*/
    public void showOrHideIvShare(boolean isShow) {
        if (isShow) {
            ivShareInformation.setVisibility(VISIBLE);
        } else {
            ivShareInformation.setVisibility(GONE);
        }
    }

    /*显示或隐藏右边按钮*/
    public void showOrHideButtonRight(boolean isShow) {
        if (isShow) {
            ivButtonRight.setVisibility(VISIBLE);
        } else {
            ivButtonRight.setVisibility(GONE);
        }
    }

    /*显示隐藏右边排序*/
    public void showOrHidePaixu(boolean isShow) {
        if (isShow) {
            nativeTitlePanxu.setVisibility(VISIBLE);
        } else {
            nativeTitlePanxu.setVisibility(GONE);
        }
    }

    /*显示或隐藏1dp的线*/
    public void showOrHideTitleBarGuider(boolean isShow) {
        if (isShow) {
            titleBarGuider.setVisibility(VISIBLE);
        } else {
            titleBarGuider.setVisibility(GONE);
        }
    }

    public View getGuider() {
        return titleBarGuider;
    }

    /*将Navigation改为灰色模式:如果没有灰色图标根据需求自己添加*/
    public void setBlackMode() {
        titleBarGroup.setBackgroundResource(R.color.lib_color_gray_f9f9f9);
        titleBarBack.setImageResource(R.drawable.lib_icon_back_gray);
        titleBarTitleText.setTextColor(getResources().getColor(R.color.lib_color_gray_444444));
        titleBarOk.setTextColor(getResources().getColor(R.color.lib_color_gray_444444));
        ivShareInformation.setImageResource(R.drawable.lib_icon_share_gray);
        ivButtonRight.setImageResource(R.drawable.lib_icon_plus_gray);
    }

    /******************点击监听接口************************/
    public interface onBackClickListener {
        void onTitleBarBackClick(View v);
    }

    public interface onTitleBarOkClickListener {
        void onTitleBarOkClick(View v);
    }

    public interface onIvShareClickListener {
        void onIvShareClick(View v);
    }

    public interface onButtonRightClickListener {
        void onButtonRightClick(View v);
    }

    public interface onPaixuClickListener {
        void onPaixuClick(View v);
    }

    public void setOnRightPaixuClickListener(onPaixuClickListener onPaixuClickListener) {
        mOnPaixuClickListener = onPaixuClickListener;
    }

    public void setOnButtonRightClickListener(onButtonRightClickListener onButtonRightClickListener) {
        mOnButtonRightClickListener = onButtonRightClickListener;
    }

    public void setOnIvShareClickListener(onIvShareClickListener onIvShareClickListener) {
        mOnIvShareClickListener = onIvShareClickListener;
    }

    public void setOnTitleBarOkClickListener(onTitleBarOkClickListener onTitleBarOkClickListener) {
        mOnTitleBarOkClickListener = onTitleBarOkClickListener;
    }

    public void setOnBackClickListener(onBackClickListener onBackClickListener) {
        mOnBackClickListener = onBackClickListener;
    }
}
