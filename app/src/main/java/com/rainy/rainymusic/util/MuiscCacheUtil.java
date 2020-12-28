package com.rainy.rainymusic.util;

import android.content.Context;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.rainy.rainymusic.RainyMusicApplication;

/**
 * create by Rainy on 2020/12/12.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class MuiscCacheUtil {

    public static  String proxyCacheUrl(Context context,String url){
        return RainyMusicApplication
                .getProxy(context)
                .getProxyUrl(url);
    }

    public static  String proxyCacheUrl(Context context, String url, CacheListener cacheListener){
        HttpProxyCacheServer proxy =  RainyMusicApplication.getProxy(context);
        proxy.registerCacheListener(cacheListener , url);


        return proxy.getProxyUrl(url);
    }


    public static boolean isCache(Context context, String url){

        return RainyMusicApplication
                .getProxy(context)
                .isCached(url);
    }
}
