package com.project.xiaodong.ff_middle_lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * PreferencesUtils, easy to get or put data
 * <ul>
 * <strong>Preference Name</strong>
 * <li>you can change preference name by {@link #PREFERENCE_NAME}</li>
 * </ul>
 * <ul>
 * <strong>Put Value</strong>
 * <li>put string {@link #putString(Context, String, String)}</li>
 * <li>put int {@link #putInt(Context, String, int)}</li>
 * <li>put long {@link #putLong(Context, String, long)}</li>
 * <li>put float {@link #putFloat(Context, String, float)}</li>
 * <li>put boolean {@link #putBoolean(Context, String, boolean)}</li>
 * </ul>
 * <ul>
 * <strong>Get Value</strong>
 * <li>get string {@link #getString(Context, String)}, {@link #getString(Context, String, String)}</li>
 * <li>get int {@link #getInt(Context, String)}, {@link #getInt(Context, String, int)}</li>
 * <li>get long {@link #getLong(Context, String)}, {@link #getLong(Context, String, long)}</li>
 * <li>get float {@link #getFloat(Context, String)}, {@link #getFloat(Context, String, float)}</li>
 * <li>get boolean {@link #getBoolean(Context, String)}, {@link #getBoolean(Context, String, boolean)}</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-3-6
 */
public class PreferencesUtils {
    /** 文件名 */
    public static String PREFERENCE_NAME = "FF_BUSINESS_APP";
    /** 第一次启动app标识 */
    public static String FIRST_APP = "FIRST_APP";
	/** 阿里方式生成的唯一设备标识 */
	public static String DEVICE_ALI_UTDID = "ALI_UTDID";
    /**用户登录token*/
    public static String USER_TOKEN = "usertoken";
    /**用户地区code码*/
    public static String LOCATION_CODE = "mLocationCode";
    /**用户地区名称*/
    public static String LOCATION_NAME = "mLocationName";
    /**用户登录信息*/
    public static String USER_INFO_PROFILE = "USER_INFO_PROFILE";
    /**用户登录信息*/
    public static String USER_INFO = "USER_INFO";
    /**相机拍照文件*/
    public static String CAPTURE_PHOTO_PATH = "CAPTURE_PHOTO_PATH";
    /**用户资料*/
    public static String USER_PROFILE = "USER_PROFILE";
    /**即时通讯用户资料*/
    public static String LOGIN_IM = "LOGIN_IM";
    /**定位信息*/
    public static String LOCATION = "LOCATION";
    /**用户手选定位*/
    public static String USER_LOCATION = "USER_LOCATION";
    /**APPICON信息*/
    public static String APPICON="APPICON";
    /**存储openinstall渠道 号*/
    public static String OPEN_INSTALL_CHANNEL= "open_install_channel";
    /**记录openinstall渠道是否上传成功*/
    public static String OPEN_INSTALL_UPLOAD="OPEN_INSTALL_UPLOAD";
    /**记录openinstall渠道设备信息是否上传成功*/
    public static String OPEN_UPLOAD_DEVICE_INFO="OPEN_UPLOAD_DEVICE_INFO";
    /***渠道对应的商场信息**/
    public static final String CHANNEL_MAPPING_MALL="channel_mapping_mall";
    /**app更新提示时间记录*/
    public static final String UPDATE_CHANNEL_TIME= "update_channel_time";

    private PreferencesUtils() {
        throw new AssertionError();
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     *         name that is not a string
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     *         name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     *         name that is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     *         this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 保存纬度
     */
    public static boolean putLatitude(Context context, String key, String value){
    	SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sp.edit();
    	editor.putString(key, value);
    	return editor.commit();
    }
    /**
     * 保存经度
     */
    public static boolean putLongitude(Context context, String key, String value){
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sp.edit();
    	editor.putString(key, value);
    	return editor.commit();
    }
    /**获取纬度*/
	public static String getLatitude(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, value);
	}
	/**获取经度*/
	public static String getLongitude(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, value);
	}
	 /**
     * 保存服务热线
     */
    public static boolean putHotline(Context context, String key, String value){
    	SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = sp.edit();
    	editor.putString(key, value);
    	return editor.commit();
    }
    /**获取服务热线*/
	public static String getHotline(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, value);
	}



    public static final String COOKIES = "COOKIES";
    public static boolean putStringSet(Context context, HashSet<String> cookies) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(COOKIES, cookies);
        return editor.commit();
    }

    public static Set<String> getStringSet(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getStringSet(COOKIES,new HashSet<String>());
    }
}
