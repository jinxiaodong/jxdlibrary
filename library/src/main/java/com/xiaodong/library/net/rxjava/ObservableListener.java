package com.xiaodong.library.net.rxjava;

import com.xiaodong.library.base.BaseResponse;

/**
 * Created by xiaodong.jin on 2019/5/5 .
 * description：
 */
public interface ObservableListener<T extends BaseResponse> {
    void onNetStart(String msg);

    void onNext(T result);

    void onComplete();

    void onNetError(NetWorkCodeException.ResponseThrowable responseThrowable);
}



