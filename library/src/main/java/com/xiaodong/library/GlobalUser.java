package com.xiaodong.library;


import android.text.TextUtils;

/**
 * Creator : Created by Kevin.tian
 * Time : 2018/6/25
 * Description : 全局User对象，单例
 */
public enum GlobalUser {
    INSTANCE;
    private UserBean bean = null;

    public boolean isLogin() {
        if (bean != null && bean.isLoginState()&& !TextUtils.isEmpty(bean.getUt())) {
            return true;
        }
        return false;
    }

    public String getLoginUt() {
        return bean == null ? "" : bean.getUt();
    }

    public String getLoginMobile() {
        return bean == null ? "" : bean.getLoginPhone();

    }
    //用户id
    public String getMemberId() {
        return bean == null ? "" : bean.getMemberId();

    }

    public UserBean getUserInfo() {
        if (bean == null) return new UserBean();
        return bean;
    }

    public void updateUser(UserBean userBean) {
        this.bean = userBean;
    }

    public void loginOut() {
        this.bean = null;
    }
}
