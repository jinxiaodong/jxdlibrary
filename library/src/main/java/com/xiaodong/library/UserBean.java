package com.xiaodong.library;

import java.io.Serializable;

/**
 * Creator : Created by Kevin.tian
 * Time : 2018/6/25
 * Description :
 */
public class UserBean implements Serializable {
    private String ut;
    private String loginUserId;
    private String loginPhone;
    private boolean loginState;
    public String nickname;
    public String headPicUrl;
    public String sex;
    public boolean isInEmployee; //内部员工
    public boolean isShowNewBalance; //是否展示来伊份支付余额
    public String memberId;   //用户memberId;



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public boolean isLoginState() {
        return loginState;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "ut='" + ut + '\'' +
                ", loginUserId='" + loginUserId + '\'' +
                ", loginPhone='" + loginPhone + '\'' +
                ", loginState=" + loginState +
                ", nickname='" + nickname + '\'' +
                ", headPicUrl='" + headPicUrl + '\'' +
                ", sex='" + sex + '\'' +
                ", isInEmployee=" + isInEmployee +
                ", isShowNewBalance=" + isShowNewBalance +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
