package com.xiaodong.library.config;

/**
 * Created by xiaodong.jin on 2019/5/5 .
 * description：
 */
public class HttpConfig {
    public static final String BASE_URL = "";
    public static final String HttpLogTAG = "jxdlib";
    public static String URL_CACHE;
    public static String URL_CACHE_CHILD ="lib";
    //缓存最大的内存,默认为10M
    public static long MAX_MEMORY_SIZE = 10 * 1024 * 1024;
    //链接超时时间 10s
    public static final int connectTimeout = 300;
    //读取超时时间 10s
    public static final int readTimeout = 300;
    //写超时时间 10s
    public static final int writeTimeout = 300;
    public static boolean isDebug = true;
}

