package com.rainy.rainymusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rainy.rainymusic.util.MediaPlayerManager;

/**
 * create by Rainy on 2020/12/9.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class MusicService extends Service {

    private MediaPlayerManager playManager;



    @Override
    public void onCreate() {
        super.onCreate();

        playManager = new MediaPlayerManager(this);

        Log.d("MusicService", "onCreate:");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("MusicService", "onStartCommand: flag :"+ flags + "startId:　" + startId);
        return super.onStartCommand(intent, flags, startId);
    }


    public void play(String path){
        if (playManager != null){
            playManager.playMusic(path,false);
        }
    }

    public MediaPlayerManager getMediaManger(){
       return playManager;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playManager != null){
            playManager.onDestroy();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    public class MyBinder extends Binder {

        public MusicService getService() {
            return MusicService.this;
        }
    }

    private MyBinder myBinder = new MyBinder();

}
