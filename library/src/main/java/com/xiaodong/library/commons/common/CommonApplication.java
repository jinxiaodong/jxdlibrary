package com.xiaodong.library.commons.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;

import com.xiaodong.library.base.BaseApplication;
import com.xiaodong.library.commons.event.NetWorkChangeEvent;
import com.xiaodong.library.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xiaodong.jin on 2019/05/06.
 * description：
 */
public class CommonApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    // TODO: 2019/05/06  初始化路由
    public void  initRouter(){

    }

    // TODO: 2019/05/06 初始化参数
    public  void initConfig(){

    }

    // TODO: 2019/05/06  初始化配置



    /*网络状态监听*/
    public void netWorkChangeListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
                if (connectivityManager != null) {
                    connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                        /**
                         * 网络可用的回调
                         * */
                        @Override
                        public void onAvailable(Network network) {
                            super.onAvailable(network);
                            EventBus.getDefault().post(new NetWorkChangeEvent(NetWorkChangeEvent.NetWorkAvailable));
                        }

                        /**
                         * 网络丢失的回调
                         * */
                        @Override
                        public void onLost(Network network) {
                            super.onLost(network);
                            EventBus.getDefault().post(new NetWorkChangeEvent(NetWorkChangeEvent.NetWorkDisconnect));
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            BroadcastReceiver netWorkStateReceiver=new BroadcastReceiver(){

                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean netConnected = NetUtils.isNetConnected();
                    EventBus.getDefault().post(new NetWorkChangeEvent(netConnected?NetWorkChangeEvent.NetWorkAvailable:NetWorkChangeEvent.NetWorkDisconnect));
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateReceiver, filter);
        }
    }

}
