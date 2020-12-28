package com.rainy.rainymusic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class SharedPreferencesUtils {

    private static final String TAG = "SharedPreferencesUtils";
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "rainyMusic";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void setParam(Context context, String key, Object object) {
        try {
            String type = object.getClass().getSimpleName();
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sp.edit();

            if ("String".equals(type)) {
                editor.putString(key, (String) object);
            } else if ("Integer".equals(type)) {
                editor.putInt(key, (Integer) object);
            } else if ("Boolean".equals(type)) {
                editor.putBoolean(key, (Boolean) object);
            } else if ("Float".equals(type)) {
                editor.putFloat(key, (Float) object);
            } else if ("Long".equals(type)) {
                editor.putLong(key, (Long) object);
            }
//            都是提交，为什么用apply会出问题，而commit就可以成功处理呢？
//            apply没有返回值而commit返回boolean表明修改是否提交成功
//            apply是将修改数据原子提交到内存，而后异步真正提交到硬件磁盘；而commit是同步的提交到硬件磁盘，因此，在多个并发的提交commit的时候，他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。而apply只是原子的提交到内存，后面有调用apply的函数的将会直接覆盖前面的内存数据，这样从一定程度上提高了很多效率。
//            apply方法不会提示任何失败的提示
            editor.apply();
            Log.e(TAG,"SharedPreferences保存成功");
        } catch (Exception e) {
            Log.e(TAG, "写入SharedPreferences  On  Error");
            e.printStackTrace();
        }

    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object getParam(Context context, String key,
                                  Object defaultObject) {
        try {
            String type = defaultObject.getClass().getSimpleName();
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
            if ("String".equals(type)) {
                return sp.getString(key, (String) defaultObject);
            } else if ("Long".equals(type)) {
                return sp.getLong(key, (Long) defaultObject);
            } else if ("Integer".equals(type)) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if ("Boolean".equals(type)) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if ("Float".equals(type)) {
                return sp.getFloat(key, (Float) defaultObject);
            }
        } catch (Exception e) {
            Log.e(TAG, "取出SharedPreferences   on error");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存对象
     */
    public static void saveObject(Object object, Context context, String name) {
        try {
//            LogUtil.i("SharedPreferences:save");
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(object);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(name, json);
            edit.apply();
        } catch (Exception e) {
            Log.e(TAG,"saveObject: 取对象：存Exception" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 取对象
     */
    public static Object getObject(Context context, String name, Class<?> clazz) throws Exception {
        try {
//            LogUtil.i("SharedPreferences:get");
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            String result = sp.getString(name, "");


            if (result == "") {
                return null;
            }
            Gson gson = new Gson();
            return gson.fromJson(result, clazz);
        } catch (Exception e) {
            Log.e(TAG,"fragment2_pagerNum: 取对象：Exception" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取对象
     */
    public static String getArrays(Context context, String name) {
        try {
            Log.e(TAG,"SharedPreferences:get");
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            String result = sp.getString(name, "");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
