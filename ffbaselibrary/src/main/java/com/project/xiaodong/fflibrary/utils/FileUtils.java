package com.project.xiaodong.fflibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.project.xiaodong.fflibrary.constants.BaseConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
     * @param rootPath 根路径
     * @param path     路径
     * @return
     */
    public static InputStream getInputStream(Context mContext, String rootPath, String path)
            throws IOException {
        File file = new File(getDataPath() + rootPath + path);
        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            return mContext.getAssets().open(path);
        }
    }


    /**
     * 返回数据目录
     *
     * @return
     */
    public static String getDataPath() {
        if (MemorySpaceCheckUtil.checkSDCard()) {
            return SDCard + "/Android/data/" + BaseConstants.PACKAGE_NAME + "/";
        }
        return "/data/data/" + BaseConstants.PACKAGE_NAME + "/";
    }


    /**
     * 返回file，如果没有就创建
     *
     * @param path
     * @return
     */
    public static File getDirectory(String path) {
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        return appDir;

    }

    /**
     * 删除文件夹：递归删除
     *
     * @param path
     * @return
     */
    public static boolean deleteDirectory(String path) {
        boolean flag = false;
        //如果path不以文件分隔符结尾，那么自动添加文件分隔符
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dir = new File(path);
        //如果dir对应的文件不存在，或者不是一个目录，，直接退出
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        flag = true;
        //存在，删除文件夹下的所有文件，包括子目录
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dir.delete()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 创建文件夹
     *
     * @param path
     * @return 1：创建成功，0：已存在 ,-1：创建失败
     */
    public static int createFloder(String path) {
        File file = new File(path);
        if (file.exists()) {
            return 0;
        } else if (file.mkdirs()) {
            return 1;
        } else return -1;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        boolean flag = false;
        File file = new File(path);
        //路径为文件并且不为空
        if (file.exists() && file.isFile()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 读取文件
     *
     * @param filePath
     * @return
     */
    public static String readerFile(String filePath) {
        StringBuffer stringBuffer = new StringBuffer();

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            int ch;
            while ((ch = br.read()) > -1) {
                stringBuffer.append(ch);
            }
            br.close();
        } catch (Exception e) {
            return null;
        }
        return stringBuffer.toString();
    }

    /**
     * 写入文件
     *
     * @param path
     * @param content
     * @return 1: 写入成功 0: 写入失败
     */
    public static int writeFile(String path, String content) {

        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            if (file.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.close();
            } else {
                return 0;
            }
        } catch (IOException e) {
            return 0;
        }
        return 1;
    }

    /**
     * 写入文件
     *
     * @param path
     * @param in
     * @return 1: 写入成功 0: 写入失败
     */
    public static int writeFile(String path, InputStream in) {
        try {
            if (in == null) {
                return 0;
            }
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }

            if (file.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int count = -1;
                while ((count = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                    fos.flush();
                }
                fos.close();
                in.close();
            } else {
                return 0;
            }
        } catch (IOException e) {
            return 0;
        }
        return 1;
    }

    /**
     * 复制文件
     *
     * @param is
     * @param os
     * @return 1: 写入成功 0: 写入失败
     * @throws IOException
     */
    public static int copyStream(InputStream is, OutputStream os) {
        try {
            final int buffer_size = 1024;
            byte[] bytes = new byte[buffer_size];
            while (true) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1) {
                    break;
                }
                os.write(bytes, 0, count);
            }
            return 1;
        } catch (IOException e) {
            return 0;
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
            }
        }
    }
    /**
     * 读取序列化对象
     *
     * @param filePath
     * @return
     */
    public static Object readerObject(String filePath) {
        Object oo;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fis);
            oo = objectIn.readObject();
            objectIn.close();
            fis.close();
        } catch (Exception e) {
            return null;
        }
        return oo;
    }


    /**
     * 写入序列化对象
     *
     * @param path
     * @param object
     * @return
     */
    public static int writeObject(String path, Object object) {
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            if (f.createNewFile()) {
                FileOutputStream utput = new FileOutputStream(f);
                ObjectOutputStream objOut = new ObjectOutputStream(utput);
                objOut.writeObject(object);
                objOut.close();
                utput.close();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * 解压
     *
     * @param rootPath
     *            解压的根目录
     * @param fileIn
     *            要解压的ZIP、rar文件流
     * @throws Exception
     */
    public static void unzip(String rootPath, InputStream fileIn) {

        try {
			/* 创建根文件夹 */
            File rootFile = new File(rootPath);
            rootFile.mkdir();

            rootFile = new File(rootPath + "resource/");
            rootFile.mkdir();

            ZipInputStream in = new ZipInputStream(new BufferedInputStream(
                    fileIn, 2048));

            ZipEntry entry = null;// 读取的压缩条目

			/* 解压缩开始 */
            while ((entry = in.getNextEntry()) != null) {
                decompression(entry, rootPath, in);// 解压
            }
            in.close();// 关闭输入流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：解压
     *
     * @param entry
     *            解压条目
     * @param in
     *            数量
     * @param rootPath
     *            根目录
     * @throws Exception
     */
    private static void decompression(ZipEntry entry, String rootPath,
                                      ZipInputStream in) throws Exception {
		/* 如果是文件夹 */
        if ((entry.isDirectory() || -1 == entry.getName().lastIndexOf("."))) {
            File file = new File(rootPath
                    + entry.getName()
                    .substring(0, entry.getName().length() - 1));
            file.mkdir();
        } else {
            File file = new File(rootPath + entry.getName());
            if (!file.exists())
                file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file), 2048);
            int b;
            while ((b = in.read()) != -1) {
                bos.write(b);
            }
            bos.close();
        }
    }

    /**
     * 获取文件扩展名
     *
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
