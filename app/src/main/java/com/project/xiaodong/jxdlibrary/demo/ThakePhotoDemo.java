package com.project.xiaodong.jxdlibrary.demo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.project.xiaodong.ff_middle_lib.block.AlbumBlock;
import com.project.xiaodong.ff_middle_lib.utils.CameraCanUseUtils;
import com.project.xiaodong.ff_middle_lib.utils.PermissionSettingUtils;
import com.project.xiaodong.ff_middle_lib.utils.ToastUtil;
import com.project.xiaodong.ff_middle_lib.views.dialog.AlertDialogUtil;
import com.project.xiaodong.fflibrary.base.BaseActivity;
import com.project.xiaodong.jxdlibrary.R;

public class ThakePhotoDemo extends BaseActivity {

    public static final int REQUEST_CAMERA = 101;
    private static final int REQUEST_CODE_SETTINGS = 102;

    @Override
    protected int getHeaderLayoutId() {
        return -1;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_thake_photo_demo;
    }

    public void onTakePhotos(View view) {
        requestPermission();
    }

    public void onOpenAlbum(View view) {
    }

    /*
     *  1.检查权限是否存在
     *  2.(1)若权限存在：直接打开app
     *    (2)若权限不存在，请求改权限
     *  3.重写onRequestPermissionsResult()方法，判断是权限返回结果，在做处理
     */
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            //有权限：打开相机
            if (CameraCanUseUtils.isCameraCanUse()) {
                AlbumBlock.doActionImageCapture(this);
            } else {
                showRequstPermissionDialog();
            }
        } else {
            //无权限:申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTINGS:
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String perm = permissions[i];
                    //依次判断权限
                    if (Manifest.permission.CAMERA.equals(perm)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            //打开相机
                            AlbumBlock.doActionImageCapture(this);
                        } else {
                            //拒绝
                            /*如果用户选择了拒绝，并且选择了不在提醒，那么这个方法会返回false，这样我们就可以做一些自己的
                            * 提醒，避免一些不好的体验。这个时候requestPermissions()是不起作用的，所以我们需要告诉用户怎么打开权限。
                            * */
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                                showRequstPermissionDialog();
                            }
                        }
                    }
                }
                break;
        }


    }


    private void showRequstPermissionDialog() {
        new AlertDialogUtil(this)
                .setTitle("相机权限未开启")
                .setMessage("请在设置中开启相机权限")
                .setNegativeButton("暂不", null)
                .setPositiveButton("去设置", new AlertDialogUtil.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog) {
                        try {
                            /*
                            * 当然在这里我们可以针对不同的手机品牌，跳转到不同设置界面。
                            * 后面会给出相应的工具类。
                            */
                            Intent permissionSettingIntent = PermissionSettingUtils.getPermissionSettingIntent(ThakePhotoDemo.this);
                            startActivity(permissionSettingIntent);

                        } catch (Exception e) {
                            try {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_CODE_SETTINGS);
                            } catch (Exception e1) {
                                ToastUtil.makeToast(ThakePhotoDemo.this, "打开设置失败");
                            }
                        }
                    }
                }).show();

    }


}
