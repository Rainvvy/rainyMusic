package com.rainy.rainymusic.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.rainy.rainymusic.entity.LrcRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create by Rainy on 2020/12/19.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class MusicUtil {




    /**
     * 读文件
     *
     * @param fileName
     * @return
     */
    public static  List<LrcRow> resolveLyric(String fileName) {
        List<LrcRow> lrcRows = new ArrayList<>();
        if (fileName == null) {
            return null;
        }
        String filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = fileName;
            Log.e("Tag", "filepath" + filePath);
        } else {
            TipUtils.showShortToast("文件系统有问题");
        }
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
                        List<LrcRow> rows = LrcRow.createRows(line);
                        if (rows != null && rows.size() > 0) {
                            lrcRows.addAll(rows);
                        }
                    }
                    Collections.sort(lrcRows);
                    int len = lrcRows.size();
                    for (int i = 0; i < len - 1; i++) {
                        lrcRows.get(i).setTotalTime(lrcRows.get(i + 1).getTime() - lrcRows.get(i).getTime());
                    }

                    if (len -1 >= 0) {
                        lrcRows.get(len - 1).setTotalTime(5000);
                    }


                }
            } catch (FileNotFoundException e) {
                Log.e("Tag", filePath + " doesn't found!");
            } catch (IOException e) {
                Log.e("Tag", filePath + " read exception, " + e.getMessage());
            }
        }

        return lrcRows;
    }

}
