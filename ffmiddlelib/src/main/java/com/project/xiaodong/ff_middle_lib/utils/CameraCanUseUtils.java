package com.project.xiaodong.ff_middle_lib.utils;

import android.hardware.Camera;

/**
 * Created by xiaodong.jin on 2018/1/31.
 */

public class CameraCanUseUtils {
    /**
     * 测试当前摄像头能否被使用
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        //Timber.v("isCameraCanuse="+canUse);
        return canUse;
    }
}
