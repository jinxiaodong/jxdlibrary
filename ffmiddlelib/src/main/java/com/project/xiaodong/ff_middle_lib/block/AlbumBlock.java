package com.project.xiaodong.ff_middle_lib.block;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;

import com.project.xiaodong.ff_middle_lib.block.base.BaseBlock;
import com.project.xiaodong.ff_middle_lib.constants.GlobalConstants;
import com.project.xiaodong.ff_middle_lib.utils.GetImagePathUtils;
import com.project.xiaodong.ff_middle_lib.utils.PreferencesUtils;
import com.project.xiaodong.ff_middle_lib.utils.ToastUtil;
import com.project.xiaodong.fflibrary.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by xiaodong.jin on 2018/2/6.
 * description：相机相册模块
 */

public class AlbumBlock extends BaseBlock {
    /*******************************************************************************
     *	Global
     *******************************************************************************/

    /*打开相机*/
    public final static int REQUEST_CODE_ACTION_IMAGE_CAPTURE = 1001;

    /*打开相册*/
    public final static int REQUEST_CODE_ACTION_PICK = 1002;

    /*剪切*/
    public final static int REQUEST_CODE_ACTION_CROP = 1003;


    /*******************************************************************************
     *	Public/Protected Variables
     *******************************************************************************/

    /*******************************************************************************
     *	Private Variables
     *******************************************************************************/

    /*******************************************************************************
     *	Overrides From Base
     *******************************************************************************/
    public AlbumBlock(Context context) {
        super(context);
    }

    /*******************************************************************************
     *	Public/Protected Methods
     *******************************************************************************/

    /*打开相机*/
    public static void doActionImageCapture(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = getOutputMediaFile(activity, false);

        if (file == null) {
            ToastUtil.makeToast(activity, "无法保存图片");
            return;
        }

        Uri imgUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            imgUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
            //将存储图片的Uri读写权限授权给拍照工具应用
            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, imgUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } else {
            imgUri = Uri.fromFile(file);
        }
        if (imgUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            activity.startActivityForResult(intent, REQUEST_CODE_ACTION_IMAGE_CAPTURE);
        } else {
            ToastUtil.makeToast(activity, "SD卡不可用，相机照片无法存储!");
        }

    }

    /**
     * 打开相机
     */
    public static void doActionImageCapture(Fragment fragment) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getOutputMediaFile(fragment.getActivity(), false);
        if (file == null) {
            ToastUtil.makeToast(fragment.getActivity(), "无法保存图片");
            return;
        }
        FragmentActivity activity = fragment.getActivity();
        Uri imgUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            imgUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
            //将存储图片的uri读写权限授权给拍照工具应用
            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, imgUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } else {
            imgUri = Uri.fromFile(file);
        }
        if (imgUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            fragment.startActivityForResult(intent, REQUEST_CODE_ACTION_IMAGE_CAPTURE);
        } else {
            ToastUtil.makeToast(fragment.getActivity(), "SD卡不可用，相机照片无法存储!");
        }
    }

    /**
     * 打开相册
     */
    public static void doActionPick(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, REQUEST_CODE_ACTION_PICK);
        } catch (Exception e) {
            ToastUtil.makeToast(activity, "您的系统未安装相册应用");
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void doActionCrop(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
//        intent.putExtra("return-data", true);


        intent.putExtra("return-data", false);

//        intent.putExtra("outputFormat",
//                Bitmap.CompressFormat.JPEG.toString());

        File file = getOutputMediaFile(activity, true);
        if (file == null) {
//            ToastUtil.makeToast(activity, "无法保存图片");
            return;
        }
        Uri imgUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        try {
            activity.startActivityForResult(intent, REQUEST_CODE_ACTION_CROP);
        } catch (Exception e) {
            ToastUtil.makeToast(activity, "裁剪图片失败");
        }
    }


    /**
     * 获得图片路径
     *
     * @param requestCode
     * @param data
     * @return
     */
    public String getImgPath(Context context, int requestCode, Intent data) {
        Uri uri = null;
        String imgPath = "";
        if (REQUEST_CODE_ACTION_IMAGE_CAPTURE == requestCode) {
            imgPath = getPhotoPath(context);
        } else if (REQUEST_CODE_ACTION_PICK == requestCode) {
            uri = data.getData();
////            uri = getUri(context,data);
//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
//            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            imgPath = cursor.getString(columnIndex);
//            cursor.close();
            if (uri != null) {
                imgPath = GetImagePathUtils.getPath(context, uri);
            }
        }
        return imgPath;
    }

    public static String getPhotoPath(Context context) {
        String photoPath = PreferencesUtils.getString(context, PreferencesUtils.CAPTURE_PHOTO_PATH);
        return photoPath;
    }

    /*******************************************************************************
     *	Private Methods
     *******************************************************************************/
    private static File getOutputMediaFile(Context context, boolean isCorp) {
        File mediaStorageDir = null;
        mediaStorageDir = FileUtils.getDirectory(GlobalConstants.CACHE_IMG);
        if (mediaStorageDir == null || !checkSDCard()) {
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US)
                .format(new Date());
        File mediaFile;
        String photoPath;
        if (isCorp) {
            photoPath = mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                    + "_CROP" + ".jpg";
            mediaFile = new File(photoPath);
        } else {
            photoPath = mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                    + ".jpg";
            mediaFile = new File(photoPath);
        }

        PreferencesUtils.putString(context, PreferencesUtils.CAPTURE_PHOTO_PATH, photoPath);

        return mediaFile;
    }

    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


    /**
     * 判断图片被旋转了多少度
     *
     * @param path 要判断的图片
     * @return 图片旋转度数
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 让图片旋转需要的角度
     *
     * @param bm                要旋转的图片
     * @param orientationDegree 旋转的角度
     * @return 旋转完的图片
     */
    public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

        try {
            Matrix m = new Matrix();
            m.setRotate(orientationDegree, (float) bm.getWidth() / 2,
                    (float) bm.getHeight() / 2);
            float targetX, targetY;
            if (orientationDegree == 90) {
                targetX = bm.getHeight();
                targetY = 0;
            } else {
                targetX = bm.getHeight();
                targetY = bm.getWidth();
            }

            final float[] values = new float[9];
            m.getValues(values);

            float x1 = values[Matrix.MTRANS_X];
            float y1 = values[Matrix.MTRANS_Y];

            m.postTranslate(targetX - x1, targetY - y1);

            Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(),
                    Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            Canvas canvas = new Canvas(bm1);
            canvas.drawBitmap(bm, m, paint);
            return bm1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent
     * @return
     */
    public static Uri getUri(Context context, Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /*******************************************************************************
     *	Internal Class,Interface
     *******************************************************************************/
}
