package com.project.xiaodong.fflibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiaodong.jin on 2018/2/6.
 */
@SuppressLint("SdCardPath")
public class FileUtils {

    /*SD卡路径*/
    public static String SDCard = Environment.getExternalStorageDirectory().getAbsolutePath();


    /**
     * 根据路径返回InputStream
     *
     * @param rootPath    根路径
     * @param path        路径
     *
     * @return
     */
    public static InputStream getInputStream(Context mContext,String rootPath,String path)
            throws IOException {
        File file = new File(getDataPath() + rootPath + path);
        if(file.exists()) {
            return new FileInputStream(file);
        }else {
            return mContext.getAssets().open(path);
        }
    }


    /**
     * 返回数据目录
     * @return
     */
    private static String getDataPath() {

        return null;
    }
}
