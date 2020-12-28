package com.rainy.rainymusic;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;




/**
 * create by Rainy on 2020/12/11.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class RainyMusicApplication extends Application {

    private static RainyMusicApplication instance;

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        RainyMusicApplication app = (RainyMusicApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                //缓存路径
                .cacheDirectory(Constant.getCacheFile(this))
                //最大缓存文件数量
                .maxCacheFilesCount(Constant.MAX_CACHE_COUNT)
                //最大缓存大小
                .maxCacheSize(Constant.MAX_CACHE_SIZE)
                .build();
    }


    public static RainyMusicApplication getInstance(){
        return  instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }



}
