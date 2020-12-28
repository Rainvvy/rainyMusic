package com.rainy.rainymusic.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * create by Rainy on 2020/12/9.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class MediaPlayerManager {

    private static final String TAG = "MediaPlayerManager";
    public static final String HTTP = "http";
    public static final String LOCAL_FILE = "file:///";


    private float mLeftVolume;
    private float mRightVolume;
    private Context mContext;
    private MediaPlayer mBackgroundMediaPlayer;
    private boolean mIsPaused;
    private String mCurrentPath;

    private onProgressListener onProgressListener;
    private onCompleteListener completeListener;

    private Handler handler = new Handler(Looper.getMainLooper());

    private Runnable progressRunable = new Runnable() {
        @Override
        public void run() {
            if (mBackgroundMediaPlayer != null){
                if (onProgressListener != null){
                    if (mBackgroundMediaPlayer.isPlaying()){
                        float bf  = (float) mBackgroundMediaPlayer.getCurrentPosition() / (float) mBackgroundMediaPlayer.getDuration();

                        Log.d(TAG, "run: cuu:" + mBackgroundMediaPlayer.getCurrentPosition() +"  total : " +  mBackgroundMediaPlayer.getDuration() );
                        onProgressListener.onProgress(bf);
                    }
                }
            }
            handler.postDelayed(this,1000);
        }
    };
    public MediaPlayerManager(Context context) {
        this.mContext = context;
        initData();
    }

    //初始化一些数据
    private void initData() {
        mLeftVolume = 0.6f;
        mRightVolume = 0.6f;
        mBackgroundMediaPlayer = null;
        mIsPaused = false;
        mCurrentPath = null;
    }

    /**
     * 根据path路径播放背景音乐
     *
     * @param path :assets中的音频路径
     * @param isLoop :是否循环播放
     */
    private void playForAssets(String path, boolean isLoop) {
        if (mCurrentPath == null) {
            //这是第一次播放背景音乐--- it is the first time to play background music
            //或者是执行end()方法后，重新被叫---or end() was called
            mBackgroundMediaPlayer = createMediaPlayerFromAssets(path);
            mCurrentPath = path;
        } else {
            if (!mCurrentPath.equals(path)) {
                //播放一个新的背景音乐--- play new background music
                //释放旧的资源并生成一个新的----release old resource and create a new one
                if (mBackgroundMediaPlayer != null) {
                    mBackgroundMediaPlayer.release();
                }
                mBackgroundMediaPlayer = createMediaPlayerFromAssets(path);
                //记录这个路径---record the path
                mCurrentPath = path;
            }
        }

        if (mBackgroundMediaPlayer == null) {
            mBackgroundMediaPlayer = createMediaPlayerFromAssets(path);
            Log.e(TAG, "playBackgroundMusic: background media player is null");
        } else {
            // 若果音乐正在播放或已近中断，停止它---if the music is playing or paused, stop it
            mBackgroundMediaPlayer.stop();
            mBackgroundMediaPlayer.setLooping(isLoop);
            mBackgroundMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    end();

                    if (completeListener != null){
                        completeListener.complete(mp);
                    }
                }
            });
            try {
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.start();
            } catch (Exception e) {
                Log.e(TAG, "playBackgroundMusic: error state");
            }
        }
    }

    public void playMusic(String path, boolean isLoop){
            end();

            //文件或网络加载
            if (path.contains(HTTP) ||
                    path.contains(LOCAL_FILE)){

                playForNetAndFile(path,isLoop);
                return;
            }

             playForAssets(path,isLoop);
    }

     /**
     * 根据path路径播放背景音乐
     *
     * @param path :assets中的音频路径
     * @param isLoop :是否循环播放
     */
    private void playForNetAndFile(String path, boolean isLoop) {
        if (mCurrentPath == null) {
            //这是第一次播放背景音乐--- it is the first time to play background music
            //或者是执行end()方法后，重新被叫---or end() was called
            mBackgroundMediaPlayer = createPlayerForNetAndFile(path);
            mCurrentPath = path;
        } else {
            if (!mCurrentPath.equals(path)) {
                //播放一个新的背景音乐--- play new background music
                //释放旧的资源并生成一个新的----release old resource and create a new one
                if (mBackgroundMediaPlayer != null) {
                    mBackgroundMediaPlayer.release();
                }
                mBackgroundMediaPlayer = createPlayerForNetAndFile(path);
                //记录这个路径---record the path
                mCurrentPath = path;
            }
        }

        if (mBackgroundMediaPlayer == null) {
            mBackgroundMediaPlayer = createPlayerForNetAndFile(path);

            Log.e(TAG, "playBackgroundMusic: background media player is null");
        } else {
            mBackgroundMediaPlayer.setLooping(isLoop);
            mBackgroundMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    end();

                    if (completeListener != null){
                        completeListener.complete(mp);
                    }
                }
            });
            mBackgroundMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mBackgroundMediaPlayer.seekTo(0);
                    mBackgroundMediaPlayer.start();
                    MediaPlayerManager.this.mIsPaused = false;
                }
            });
            try {

                mBackgroundMediaPlayer.prepareAsync();

            } catch (Exception e) {
                Log.e(TAG, "playBackgroundMusic: error state");
            }
        }
    }



    /**
     * 从 assets 中创建音乐播放器
     */
    private MediaPlayer createMediaPlayerFromAssets(String path) {
        MediaPlayer mediaPlayer;
        try {
            AssetFileDescriptor assetFileDescriptor = mContext.getAssets().openFd(path);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setVolume(mLeftVolume, mRightVolume);
        } catch (Exception e) {
            mediaPlayer = null;

            Log.e(TAG, "create mediaPlayer error: " + e.getMessage());
        }
        return mediaPlayer;
    }
      /**
     * 从 网络 中创建音乐播放器
     */
    private MediaPlayer createPlayerForNetAndFile(String path) {
        MediaPlayer mediaPlayer;
        try {

            mediaPlayer = new MediaPlayer();

            mediaPlayer.setDataSource(path);
            mediaPlayer.setVolume(mLeftVolume, mRightVolume);
//            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            mediaPlayer = null;
            Log.e(TAG, "create mediaPlayer error: " + e.getMessage());
        }
        return mediaPlayer;
    }


    /**
     * 是否 有path 歌曲路径
     */

    public boolean isHavePath(){
        return mCurrentPath != null;
    }
    /**
     * 停止播放背景音乐
     */
    public void stopBackgroundMusic() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            // should set the state, if not , the following sequence will be error
            // play -> pause -> stop -> resume
            this.mIsPaused = false;
        }
    }

    /**
     * 暂停播放背景音乐
     */
    public void pauseBackgroundMusic() {
        if (mBackgroundMediaPlayer != null && mBackgroundMediaPlayer.isPlaying()) {
            mBackgroundMediaPlayer.pause();
            this.mIsPaused = true;
        }
    }

    /**
     * 继续播放背景音乐
     */
    public void resumeBackgroundMusic() {
        if (mBackgroundMediaPlayer != null && this.mIsPaused) {
            mBackgroundMediaPlayer.start();
            this.mIsPaused = false;
        }
    }

    /**
     * 重新播放背景音乐
     */
    public void rewindBackgroundMusic() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.start();
                this.mIsPaused = false;
            } catch (Exception e) {
                Log.e(TAG, "rewindBackgroundMusic: error state");
            }
        }
    }

    /**
     * 判断背景音乐是否正在播放
     */
    public boolean isPlaying() {
        boolean ret;
        if (mBackgroundMediaPlayer == null) {
            ret = false;
        } else {
            ret = mBackgroundMediaPlayer.isPlaying();
        }
        return ret;
    }

    /**
     * 结束背景音乐，并释放资源
     */
    public void end() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            mBackgroundMediaPlayer.release();
        }
        //重新“初始化数据”
        initData();
    }

    /**
     * 得到背景音乐的“音量”
     */
    public float getBackgroundVolume() {
        if (this.mBackgroundMediaPlayer != null) {
            return (this.mLeftVolume + this.mRightVolume) / 2;
        } else {
            return 0.0f;
        }
    }

    /**
     * 设置背景音乐的音量
     */
    public void setBackgroundVolume(float volume) {
        this.mLeftVolume = this.mRightVolume = volume;
        if (this.mBackgroundMediaPlayer != null) {
            this.mBackgroundMediaPlayer.setVolume(this.mLeftVolume, this.mRightVolume);
        }
    }

    public void seekTo(float bfb){
        if (this.mBackgroundMediaPlayer != null){
            this.mBackgroundMediaPlayer.seekTo((int) (bfb * this.mBackgroundMediaPlayer.getDuration()));
        }
    }


    public void onDestroy(){

        if (mBackgroundMediaPlayer != null) {
             end();
        }

        if (handler != null){
            handler.removeCallbacks(progressRunable);
        }
    }

    public void setPlayCompleteListener(onCompleteListener completeListener){
        if (this.completeListener == null){
            this.completeListener = completeListener;
        }
    }


    public void setProgressListener(onProgressListener progressListener){
        if (this.onProgressListener == null) {
            this.onProgressListener = progressListener;
            handler.post(progressRunable);
        }
    }


    //实时监听音乐播放进度
    public interface onProgressListener{

        void onProgress(float progress);
    }

     //监听音乐播放完成

    public interface onCompleteListener{

        void complete(MediaPlayer md);
    }


}
