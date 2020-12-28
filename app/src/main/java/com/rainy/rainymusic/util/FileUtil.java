package com.rainy.rainymusic.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * create by AaronYang on 2018/8/9.
 * email: 1390939057@qq.com
 * github: AaronYang23
 * describe:
 */
public class FileUtil {
    private static final String TAG = "FileUtil";


//    /**
//     * 写入文件
//     *
//     * @throws IOException
//     */
//    public static void writeCache(ResponseBody responseBody, String path) {
//        File file = new File(path);
//        //设置总长度
//
//        long allLength = responseBody.contentLength();
//
//        //判断文件是否存在
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        RandomAccessFile   randomAccessFile = null;
//        try {
//            randomAccessFile = new RandomAccessFile(file, "rwd");
//            //Chanel NIO中的用法，由于RandomAccessFile没有使用缓存策略，直接使用会使得下载速度变慢，亲测缓存下载3.3秒的文件，用普通的RandomAccessFile需要20多秒。
//            randomAccessFile.seek(allLength);
//            randomAccessFile.write(responseBody.string().getBytes());
//            randomAccessFile.close();
//
//        } catch (Exception e) {
//            Log.e("异常:", e.toString());
//            e.printStackTrace();
//        } finally {
//
//                if (randomAccessFile != null) {
//                    try {
//                        randomAccessFile.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//        }
//    }

    public static void writeCache(String val, String path) {
        File file = new File(path);
        //设置总长度

        //判断文件是否存在
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //设置总长度
        try {
            FileOutputStream stream = new FileOutputStream(file);
            byte[] bytes = val.getBytes();

            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * desc: 修改文件名,不包含文件夹
     */
    public  static void renameFile(String file, String toFile) {

        File renameFile = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!renameFile.exists() || renameFile.isDirectory()) {
            System.out.println("File does not exist: " + file);
            return;
        }
        File newFile = new File(toFile);

        //修改文件名
        if (renameFile.renameTo(newFile)) {
            Log.i(TAG,"重命名File has been renamed.");
        } else {
            Log.i(TAG,"Error renmaing file");
        }

    }

    /**
     * 读文件
     *
     * @param fileName
     * @return
     */
    public static String readFile(String fileName) {
        if (fileName == null) {
            return null;
        }
        String fileString = null;
        String filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = fileName;
            Log.e("Tag", "filepath" + filePath);
        } else {
            TipUtils.showShortToast("文件系统有问题");
        }
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(filePath);
        if (file.isDirectory()) {
            Log.e("Tag", filePath + " is directory");
            return null;
        }
        if (!file.exists()) {
            TipUtils.showShortToast("文件不存在");

        } else {
            try {
                InputStream is = new FileInputStream(file);
                if (is != null) {
                    InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                    BufferedReader br = new BufferedReader(isr);

                    String line;
                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line);
                        Log.e("Tag", line);
                    }
                    fileString = stringBuilder.toString();

                }
            } catch (FileNotFoundException e) {
                Log.e("Tag", filePath + " doesn't found!");
            } catch (IOException e) {
                Log.e("Tag", filePath + " read exception, " + e.getMessage());
            }
        }

        return fileString;
    }



}
