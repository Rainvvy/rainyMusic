package com.rainy.rainymusic.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rainy.rainymusic.service.MusicService;

/**
 * create by Rainy on 2020/12/9.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected MusicService musicPlayService;


    /**
     * 绑定后台服务的借口
     */
    private ServiceConnection backServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicPlayService = ((MusicService.MyBinder) service).getService();
            Log.d("test","服务已连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicPlayService = null;
            Log.d("test","服务已断开");

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        init();
        bindMediaService();
    }

    //返回layout id

    protected abstract int layoutId();

    protected abstract void init();

    private void bindMediaService(){
        Log.d(getClass().getSimpleName()  ,(musicPlayService == null) + "---- 是否会重新初始化service ");
        if (musicPlayService == null){
            Intent intentService = new Intent(this, MusicService.class);
//            startService(intentService);
            bindService(intentService, backServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(backServiceConnection);

    }
}
