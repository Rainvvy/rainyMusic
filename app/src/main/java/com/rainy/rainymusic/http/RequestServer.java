package com.rainy.rainymusic.http;



import com.rainy.rainymusic.entity.Comment;
import com.rainy.rainymusic.entity.MusicEntity;
import com.rainy.rainymusic.entity.MusicUrl;
import com.rainy.rainymusic.entity.SearchMusic;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;


/**
 * create by Rainy on 2018/5/31.
 * email: im.wyu@qq.com
 * github: Rainvvy
 * describe: 请求服务接口
 */
public interface RequestServer {



    @FormUrlEncoded
    @POST("api.php?search=mg")
    Observable<SearchMusic> serarchMusic(@Field("w") String w , @Field("p") int p, @Field("n") int n);

    @FormUrlEncoded
    @POST("api.php?get_song=mg")
    Observable<MusicUrl> getMusicUrl(@Field("mid") String mid );

    //获取所有的Url

    @GET
    Observable<ResponseBody> getMusicTypeUrl(@Url String url);

    //获取歌词的数据 写入文件

    @GET
    Observable<ResponseBody> getMusicLyric(@Url String url);


    //获取歌曲评论

    @POST
    Observable<Comment> getMusicComment(@Url String url);
}
