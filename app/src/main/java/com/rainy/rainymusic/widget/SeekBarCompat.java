package com.rainy.rainymusic.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.core.content.ContextCompat;

import com.rainy.rainymusic.R;
import com.rainy.rainymusic.util.UIHelper;

import java.lang.reflect.Field;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * create by Rainy on 2020/12/14.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class SeekBarCompat  extends RelativeLayout {
    private Context context;

    //缓冲进度条

    private SeekBar bufferBar;

    //播放进度条

    private SeekBar musicBar;

    //资源长度  秒

    private int resLength = 300;



    public SeekBarCompat(Context context) {
        super(context);
        init(context,null);
    }

    public SeekBarCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.context = context;
        bufferBar = new SeekBar(context);
        bufferBar.setProgress(0);
        //禁用拖动
        bufferBar.setMax(100);
        bufferBar.setThumb(null);
        bufferBar.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.seekbar_buffer_bg));

        LayoutParams bufferP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        bufferBar.setLayoutParams(bufferP);
        addView(bufferBar);

        musicBar = new SeekBar(context);
        musicBar.setProgress(0);
        musicBar.setMax(resLength);
        musicBar.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.seek_musicbar_bg));
        musicBar.setThumb(ContextCompat.getDrawable(context, R.drawable.musicseek_thumb));
        LayoutParams musicBarP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        musicBar.setLayoutParams(musicBarP);
        addView(musicBar);

        bufferBar.setPadding(0,0,0,-UIHelper.dipToPx(5));
        musicBar.setPadding(0,0,0,-UIHelper.dipToPx(5));
        // 加载xml 资源
         if (attrs != null){

         }
    }


    //设置缓冲进度条

    public void setBufferProgress(int progress){
        if (bufferBar != null){
            bufferBar.setVisibility(VISIBLE);
            bufferBar.setAlpha(1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                bufferBar.setProgress(progress,true);
                return;
            }

            bufferBar.setProgress(progress);
        }
    }

    //设置音乐播放进度条

     public void setMusicPlayProgress(int progress){
        if (musicBar != null){
            musicBar.setProgress(progress);
        }
    }

    //设置拖动音乐进度条监听

    public  void setMusicBarTouchChangeListener(SeekBar.OnSeekBarChangeListener listener){
        if (musicBar != null &&
                listener != null){
            musicBar.setOnSeekBarChangeListener(listener);
        }
    }

    public void setResLength(int resLength) {
        this.resLength = resLength;
        this.musicBar.setMax(resLength);
    }

    public int getResLength(){
        return this.resLength;
    }

    public void setBufferGone(){
        if (bufferBar.getVisibility() == GONE){
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                    bufferBar.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bufferBar.setVisibility(GONE);
            }
        });
        valueAnimator.start();
    }
}
