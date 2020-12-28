package com.rainy.rainymusic.util;

import android.widget.Toast;

import com.rainy.rainymusic.RainyMusicApplication;

/**
 * create by AaronYang on 2018/6/28.
 * email: 1390939057@qq.com
 * github: AaronYang23
 * describe:提示类工具
 */
public class TipUtils {

    public static Toast mToast = null;


    public static void showShortToast(String msg) {
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(RainyMusicApplication.getInstance().getApplicationContext(),   msg, Toast.LENGTH_LONG);

        mToast.show();
    }

    public static void showLongToast(String msg) {
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(RainyMusicApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG);

        mToast.show();
    }


}
