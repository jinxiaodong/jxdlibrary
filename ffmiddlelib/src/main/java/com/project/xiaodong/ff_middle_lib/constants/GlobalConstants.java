package com.project.xiaodong.ff_middle_lib.constants;

import com.project.xiaodong.fflibrary.ApplicationHelper;
import com.project.xiaodong.fflibrary.utils.FileUtils;

/**
 * Created by xiaodong.jin on 2018/2/6.
 * description：
 */

public class GlobalConstants {
    /*******************************************************************************
     *	Public/Protected Variables
     *******************************************************************************/
    /**
     * 服务器环境
     */
    public enum ServerType {
        RELEASE,//正式
        STG,//STG
        TEST,//测试
        UAT1,//测试
        DEV,//开发
    }

    public static ServerType gServer = ServerType.RELEASE;
       /* 应用包名*/
    public final static String PACKAGE_NAME = ApplicationHelper.getApplication().getPackageName();
    /**
     * 应用版本code
     */
    public static int APP_VERSION_CODE = 1;
    /**
     * 应用版本号
     */
    public static String APP_VERSION_NAME = "1.0";

    /* 缓存根目录 */
    public final static String CACHE_ROOT = FileUtils.getDataPath() + "cache/";
    /* 数据缓存目录 */
    public final static String CACHE_DATA = CACHE_ROOT + "data/";
    /* 图片缓存目录 */
    public final static String CACHE_IMG = CACHE_ROOT + "images/";
    /* 音频缓存目录 */
    public final static String CACHE_AUDIO = CACHE_ROOT + "audio/";
    /*聊天目录 */
    public final static String CACHE_CHAT = CACHE_ROOT + "chat/";
    /**
     * 错误日志目录
     */
    public final static String CACHE_ERROR = CACHE_ROOT + "error/";
    /* webview缓存路径 * */
    public static final String WEBVIEW_CACHE_PATH = "/webview";

    /**
     * 城市缓存时间 5天
     */
    public static final long CITY_INFO_CACHETIME = 1000 * 60 * 60 * 24 * 5;
    /*城市数据存储路径*/
    public static final String CITY_FILE_PATH = GlobalConstants.CACHE_DATA + "cityinfo";

    /**
     * 成功相应code码
     */
    public static final String REQUEST_SUCCESS = "200";
    /**
     * 短信倒计时时间（单位：毫秒）
     */
    public static final long TIMING_VALUE = 60000;

    //多选图片（最可选数量）
    public static final int MAX_IMAG_NUMBER = 9;


    /***-------------------当前链接的网络类型-----------------------------*/
    public static int NO_NETWORK = -1;//无网络服务
    public static final int NETWORK_WIFI = 0;//wifi链接
    public static final int NETWORK_2G = 1;//2G网络
    public static final int NETWORK_3G = 2;//3G网络
    public static final int NETWORK_4G = 3;//4G网络
    public static final int NETWORK_UNKNOWN = 4;//未知网络
    public static int CURRENT_NETWORK_TYPE = NETWORK_UNKNOWN;//默认未知网络
    /***-------------------当前链接的网络类型-----------------------------*/

    /***app如果有更新 1天提示一次*/
    public static final long UPDATE_CHANNEL_TIME = 1000 * 60 * 60 * 24 * 1;

    /*******************************************************************************
     *	Private Variables
     *******************************************************************************/

    /*******************************************************************************
     *	Overrides From Base
     *******************************************************************************/

    /*******************************************************************************
     *	Public/Protected Methods
     *******************************************************************************/

    /*******************************************************************************
     *	Private Methods
     *******************************************************************************/

    /*******************************************************************************
     *	Internal Class,Interface
     *******************************************************************************/
}
