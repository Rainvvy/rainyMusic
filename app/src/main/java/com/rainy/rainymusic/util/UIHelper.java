package com.rainy.rainymusic.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.rainy.rainymusic.R;
import com.rainy.rainymusic.RainyMusicApplication;

import java.lang.reflect.Method;

public class UIHelper {
    private static float density;
    private static long lastTime = 0;
    public static int WEIGHT = 0;
    public static int HEIGHT = 1;

    /**
     * dip转px
     */
    public static int dipToPx(int dip) {
        if (density <= 0) {
            density = RainyMusicApplication.getInstance().getResources().getDisplayMetrics().density;
//            LogUtil.i("=========density" + density);
        }
        return (int) (dip * density + 0.5f);
    }

    //sp转px
    public static float spToPx(int sp) {
        if (density <= 0) {
            density = RainyMusicApplication.getInstance().getResources().getDisplayMetrics().density;
//            LogUtil.i("=========density" + density);
        }
        return (float) (sp * density);
    }


    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }



    /**
     * desc: 获取根据屏幕指定的百分比
     */
    public static int getPercentOfScreen(Context context, int type, float percent) {
        if (type == WEIGHT) {
            return (int) (getScreenPixWidth(context) * percent);
        } else if (type == HEIGHT) {
            return (int) (getScreenPixHeight(context) * percent);
        } else {
            return 0;
        }
    }


    /**
     * desc: density
     */
    public static float getDensity(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().density;
    }


    /**
     * desc: 屏幕高像素
     */
    public static int getScreenPixHeight(Context paramContext) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        if (!(paramContext instanceof Activity))
            return paramContext.getResources().getDisplayMetrics().heightPixels;
        WindowManager localWindowManager = ((Activity) paramContext).getWindowManager();
        if (localWindowManager == null)
            return paramContext.getResources().getDisplayMetrics().heightPixels;
        localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels;
    }


    /**
     * desc: 屏幕宽像素
     */
    public static int getScreenPixWidth(Context paramContext) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        if (!(paramContext instanceof Activity))
            return paramContext.getResources().getDisplayMetrics().widthPixels;
        WindowManager localWindowManager = ((Activity) paramContext).getWindowManager();
        if (localWindowManager == null)
            return paramContext.getResources().getDisplayMetrics().widthPixels;
        localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }

    /**
     * desc: 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * desc: 状态栏高度 方式二
     */
    public static int getStatusBarHeight2(Context paramContext) {
        try {
            Class<?> localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
            int j = paramContext.getResources().getDimensionPixelSize(i);
            return j;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }


    /**
     * desc:  获得toolbar以下的高度
     */
    public static int getToolbarUnderHeight(Context context) {
        return (int) (getScreenPixHeight(context) - getStatusBarHeight(context) - dipToPx(44));
    }

    /**
     * 获取是否存在NavigationBar，是否有虚拟按钮
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
    /**
     * 获取虚拟按钮ActionBar的高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }


//
//    public static void setFullScreen(Activity paramActivity) {
//        paramActivity.getWindow().setFlags(1024, 1024);
//    }
//
//    public static void setNoActionbar(Activity paramActivity) {
//        paramActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
//    }
//
//    public static void showToast(Activity activity, String paramString) {
//        if (System.currentTimeMillis() - lastTime > 2000) {
//            showToast(activity, paramString, Toast.LENGTH_SHORT);
//            lastTime = System.currentTimeMillis();
//        }
//    }
//
//    public static void showToast(final Activity activity, final String paramString, final int paramInt) {
//        activity.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                Toast.makeText(activity, paramString, paramInt).show();
//            }
//        });
//    }
//
//    // 去掉进度条
//    public static void cancleProgressDialog(Dialog dialog) {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//            dialog.cancel();
//        }
//    }
//
//    public static String getUnEmptyString(String content) {
//        String result = content == null ? StringUtil.EMPTY : content;
//        result = result.replaceAll("\n", StringUtil.EMPTY);
//        return result;
//    }
//
//    public static void setText(TextView textView, String content) {
//        if (textView != null) {
//            textView.setText(getUnEmptyString(content));
//        }
//    }
//
//    public static void setText(TextView textView, String content, String defaultContent) {
//        if (textView != null) {
//            if (StringUtil.isEmpty(getUnEmptyString(content))) {
//                textView.setText(defaultContent);
//            } else {
//                textView.setText(getUnEmptyString(content));
//            }
//        }
//    }
//
//
//    public interface OnDialogClickListener {
//        void onClick();
//    }
//
//
//    public static int getStatusHeight(Activity activity) {
//        Class<?> c = null;
//        Object obj = null;
//        Field field = null;
//        int x = 0, sbar = 0;
//        try {
//            c = Class.forName("com.android.internal.R$dimen");
//            obj = c.newInstance();
//            field = c.getField("status_bar_height");
//            x = Integer.parseInt(field.get(obj).toString());
//            sbar = activity.getResources().getDimensionPixelSize(x);
//            return sbar;
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            return UIHelper.dipToPx(25);
//        }
//    }
//
//
//    // 获取当前系统亮度
//    public static float getScreenBrightness(Context context) {
//        int screenBrightness = 255;
//        try {
//            screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
//        } catch (Exception localException) {
//            return 1f;
//        }
//        float screenBrightnessPre = (float) screenBrightness / 255;
//        return screenBrightnessPre;
//    }

}