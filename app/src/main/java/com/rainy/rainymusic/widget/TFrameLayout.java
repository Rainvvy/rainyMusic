package com.rainy.rainymusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * create by Rainy on 2020/12/14.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class TFrameLayout  extends FrameLayout {



    private int scrollPX= 8;

    private ClickListener clickListener;

    public TFrameLayout(@NonNull Context context) {
        super(context);
    }

    public TFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    int downX  = 0;
    int downY  = 0;
    //首先 先将 返回回来的onTouchEvent 拦截下来 处理 判断是否为点击事件 如果为点击事件 自己处理。。 点击事件 返回给父布局处理

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 继续传递
        PileLayout p  = (PileLayout) getParent();
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                p.onInterceptTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                p.onInterceptTouchEvent(event);
                p.onTouchEvent(event);
                break;
             case MotionEvent.ACTION_UP:
                if (Math.abs(event.getX() - downX) < scrollPX
                        && Math.abs(event.getY() - downY) < scrollPX){
                    if (this.clickListener != null){
                        this.clickListener.onClick(this);
                    }
                    return true;
                }

                 p.onTouchEvent(event);
                 return true;

            default:return false;
        }
       return  true;
    }

    public void setListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    public interface ClickListener{
           void onClick(View v);
    }
}
