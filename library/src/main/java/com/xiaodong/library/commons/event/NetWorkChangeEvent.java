package com.xiaodong.library.commons.event;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * 功能描述：
 */
public class NetWorkChangeEvent {
    //网络断开标志
    public static final int NetWorkDisconnect = 100;
    //网络连接状态标志
    public static final int NetWorkAvailable = 101;
    //当天网络状态
    public int CurrentNetWorkStatus;

    public NetWorkChangeEvent(int currentNetWorkStatus) {
        CurrentNetWorkStatus = currentNetWorkStatus;
    }
}
