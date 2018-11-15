package com.xiaodong.library.commons.mvp;

/**
 * Created by xiaodong.jin on 2018/11/15.
 * description：mvp view层接口
 */
public interface BaseView {
    void onNetError(String tag,String errorMsg);
    void onNetStart(String tag,String startMsg);
    void onNetFinish(String tag,String startMsg);
}
