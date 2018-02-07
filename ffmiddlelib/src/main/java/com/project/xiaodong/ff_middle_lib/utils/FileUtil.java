package com.project.xiaodong.ff_middle_lib.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 文件转换工具类
 */

public class FileUtil {

    /***
     * unity 3d 资源目录名
     */
    public final static String UNITY_3D_RES = "3DModel";

    /***
     * 是否含有外置存储卡
     *
     * @return
     */
    public static boolean isHasExternalStorage() {
        return Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable();
    }

    public static File getExternalFilesDir(Context context) {
        File sdDir = null;
        if (isHasExternalStorage())
            sdDir = context.getExternalFilesDir(null);
        return sdDir;
    }

    /***
     * 获取Unity 3d资源文件根目录
     *
     * @param context
     * @return
     */
    public static File getUnityRootDir(Context context) {
        File sdDir = null;
        if (isHasExternalStorage()) {
            sdDir = new File(getExternalFilesDir(context), UNITY_3D_RES);
        }
        return sdDir;
    }

    /***
     * 获取Unity 3d资源文件指定spu目录
     *
     * @param context
     * @param spu
     * @return
     */
    public static File getUnityResFilesRootDir(Context context, String spu) {
        File sdDir = null;
        if (isHasExternalStorage()) {
            sdDir = new File(getUnityRootDir(context), spu);
        }
        return sdDir;
    }

    /***
     * 获取指定spu商品的Unity 3d资源文件
     *
     * @param context
     * @param spu     商品的spu
     * @param version 资源的版本号
     * @return
     */
    public static File getUnityResFile(Context context, String spu, String version) {
        if (TextUtils.isEmpty(spu)) {
            return null;
        }
        File file = null;
        if (isHasExternalStorage()) {
            File rootDir = getUnityRootDir(context);
            if (rootDir != null) {
                if (!TextUtils.isEmpty(version)) {
                    version = "_" + version;
                }
                file = new File(rootDir, spu + version + ".zip");
            }
        }
        return file;
    }

    /***
     * 判断本地是否有unity缓存
     *
     * @param context
     * @param spu
     * @return
     */
    public static boolean isHasUnityCache(Context context, String spu) {
        File resDir = getUnityResFilesRootDir(context, spu);
        if (resDir != null){
            if (resDir.isDirectory() && resDir.exists()) {
                File[] files = resDir.listFiles();
                if (files.length > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * 删除文件夹
     *
     * @param context
     * @param spu
     */
    public static void deleteUnityCache(Context context, String spu) {
        File resDir = getUnityResFilesRootDir(context, spu);
        if (resDir != null){
            if (resDir.isDirectory() && resDir.exists()) {
                resDir.delete();
            }
        }
    }

    public static String getFileCacheFolder(Context context) {
        String sdDir = "";
        if (isHasExternalStorage())
            sdDir = context.getExternalFilesDir(null).getPath();
        else
            sdDir = context.getFilesDir().getPath();
        return sdDir;
    }

    public static String formatFileSize(long fileS) {// 转换文件大小
        return formatFileSize(fileS, "#0.00");
    }

    /***
     * 转换文件大小
     *
     * @param fileS
     * @return 显示格式
     */
    public static String formatFileSize(long fileS, String pattern) {//
        DecimalFormat df = new DecimalFormat(pattern);
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
