package com.rainy.rainymusic.util;

import android.content.Context;
import android.util.Log;

import com.rainy.rainymusic.Constant;
import com.rainy.rainymusic.entity.MusicEntity;
import com.rainy.rainymusic.http.HttpUtil;
import com.rainy.rainymusic.widget.LyricView;

import org.json.JSONObject;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * create by Rainy on 2020/12/19.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class MusicPlayManger {

     private static MusicPlayManger musicPlayManger;

     private Context context;




     public  static MusicPlayManger getInstance(Context context){
            if (musicPlayManger == null){
                musicPlayManger = new MusicPlayManger(context);
            }
            return musicPlayManger;
     }

     private MusicPlayManger(Context context){
         this.context = context;

     }





     public void loadLyric(MusicEntity entity, LyricView lyricView){
         if (entity.getMusicTypeUrls() == null ){
             TipUtils.showShortToast("未获取到歌曲信息");
             return;
         }
         String path = Constant.getLyricDir(context) +  File.separator + entity.getSong().getSongname() + ".lrc";
         if (entity.getLyric() == null){
             File lyricFile = new File(path);
             if (lyricFile.exists()){
                 entity.setLyric(MusicUtil.resolveLyric(path));
                 lyricView.setLrcRows(entity.getLyric());
                 return;
             }
             if (lyricFile.getParent() == null){
                 new File(lyricFile.getParent()).mkdirs();
             }

             HttpUtil.getHttpUtilInstance().getServer().getMusicLyric(entity.getMusicTypeUrls().getUrl().getLrc())
                     .subscribeOn(Schedulers.newThread())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(new Consumer<ResponseBody>() {
                         @Override
                         public void accept(ResponseBody responseBody) throws Exception {
                             String body = responseBody.string();
                             if (body.contains("code")){
                                 JSONObject jsonObject = new JSONObject(responseBody.string());
                                 int code = jsonObject.getInt("code");
                                 if (code == 1){
                                     TipUtils.showShortToast("请重试！");
                                     loadLyric(entity,lyricView);
                                     return;
                                 }
                             }


                             //写入文件
//                                FileUtil.writeObjToFile(path,responseBody.string());
                             FileUtil.writeCache(body,path);

                             entity.setLyric(MusicUtil.resolveLyric(path));
                             lyricView.setLrcRows(entity.getLyric());

                         }
                     });

            return;
         }


         lyricView.setLrcRows(entity.getLyric());


     }



}
