package com.xiaodong.library.base;

/**
 * Created by xiaodong.jin on 2019/5/5.
 * description：
 */
public class BaseResponse<T> {
    public String message;
    public String msg;
    public String errMsg;
    public String trace;
    public String code;
    public T data;
}
