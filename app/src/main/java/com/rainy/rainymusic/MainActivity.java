package com.rainy.rainymusic;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rainy.rainymusic.activity.BaseActivity;
import com.rainy.rainymusic.entity.Comment;
import com.rainy.rainymusic.entity.MusicEntity;
import com.rainy.rainymusic.entity.MusicUrl;
import com.rainy.rainymusic.entity.SearchMusic;
import com.rainy.rainymusic.http.HttpUtil;
import com.rainy.rainymusic.util.MediaPlayerManager;
import com.rainy.rainymusic.util.MuiscCacheUtil;
import com.rainy.rainymusic.util.MusicPlayManger;
import com.rainy.rainymusic.util.MusicTimeUtil;
import com.rainy.rainymusic.util.TipUtils;
import com.rainy.rainymusic.util.Utils;
import com.rainy.rainymusic.widget.FadeTransitionImageView;
import com.rainy.rainymusic.widget.HorizontalTransitionLayout;
import com.rainy.rainymusic.widget.LyricView;
import com.rainy.rainymusic.widget.PileLayout;
import com.rainy.rainymusic.widget.PlayPauseView;
import com.rainy.rainymusic.widget.SeekBarCompat;
import com.rainy.rainymusic.widget.VerticalTransitionLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity {

    private View positionView;
    private PileLayout pileLayout;
    private List<MusicEntity> dataList;

    private int lastDisplay = -1;


    private ObjectAnimator transitionAnimator;
    private float transitionValue;
    private HorizontalTransitionLayout musicName, temperatureView;
    private VerticalTransitionLayout comment, timeView;
    private FadeTransitionImageView bottomView;
    private Animator.AnimatorListener animatorListener;
    private TextView commentContent;

    private LyricView lyricView ;
    private MediaPlayerManager musicManager;

    private TextView commentLikeConut;
    private TextView commentUserNickName;
     private TextView commentTime;
    private RoundedImageView commentHeadImage;


    private HashMap<Integer, ViewHolder> viewHolders = new HashMap<>();

    private int playingIndex = -1;
   @Override
   protected void init(){
       positionView = findViewById(R.id.positionView);
       musicName = (HorizontalTransitionLayout) findViewById(R.id.musicName);
       temperatureView = (HorizontalTransitionLayout) findViewById(R.id.temperatureView);
       pileLayout = (PileLayout) findViewById(R.id.pileLayout);
       comment = (VerticalTransitionLayout) findViewById(R.id.comment);
       commentContent = (TextView) findViewById(R.id.comment_content);
       commentLikeConut = findViewById(R.id.comment_like);
       commentUserNickName = findViewById(R.id.comment_nickName);
       commentTime = findViewById(R.id.comment_time);
       commentHeadImage = findViewById(R.id.headImg);
       timeView = (VerticalTransitionLayout) findViewById(R.id.timeView);
       bottomView = (FadeTransitionImageView) findViewById(R.id.bottomImageView);
       lyricView = findViewById(R.id.lyricView);




       // 1. 状态栏侵入
       boolean adjustStatusHeight = false;
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           adjustStatusHeight = true;
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
           } else {
               getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           }
       }

       // 2. 状态栏占位View的高度调整
       String brand = Build.BRAND;
       if (brand.contains("Xiaomi")) {
           Utils.setXiaomiDarkMode(this);
       } else if (brand.contains("Meizu")) {
           Utils.setMeizuDarkMode(this);
       } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           View decor = getWindow().getDecorView();
           decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
           adjustStatusHeight = false;
       }
       if (adjustStatusHeight) {
           adjustStatusBarHeight(); // 调整状态栏高度
       }

       animatorListener = new Animator.AnimatorListener() {
           @Override
           public void onAnimationStart(Animator animation) {

           }

           @Override
           public void onAnimationEnd(Animator animation) {
               musicName.onAnimationEnd();
//               temperatureView.onAnimationEnd();
//               addressView.onAnimationEnd();
//               bottomView.onAnimationEnd();
//               timeView.onAnimationEnd();
           }

           @Override
           public void onAnimationCancel(Animator animation) {

           }

           @Override
           public void onAnimationRepeat(Animator animation) {

           }
       };


       // 3. PileLayout绑定Adapter
       initDataList();
       pileLayout.setAdapter(new PileLayout.Adapter() {
           @Override
           public int getLayoutId() {
               return R.layout.item_layout;
           }

           @Override
           public void bindView(View view, int position) {
               ViewHolder viewHolder = (ViewHolder) view.getTag();
               Log.d("asdas", "bindView: " + position);
               if (viewHolder == null) {
                   viewHolder = new ViewHolder();
                   viewHolder.imageView =  view.findViewById(R.id.imageView);
                   viewHolder.musicPlay =  view.findViewById(R.id.music_play);
                   viewHolder.musicLength =  view.findViewById(R.id.music_length);
                   viewHolder.playingTime =  view.findViewById(R.id.music_playing_time);
                   viewHolder.seekBarCompat = view.findViewById(R.id.music_seekbarCompt);



                   view.setTag(viewHolder);
               }
               viewHolders.put(position,viewHolder);

               if (dataList.get(position).getMusicTypeUrls() != null){
                   Glide.with(MainActivity.this).load(dataList.get(position).getMusicTypeUrls().getUrl().getAblum_pic()).into(viewHolder.imageView);
                   viewHolder.musicLength.setText(MusicTimeUtil.getMusicProgress(MusicTimeUtil.getProgress(dataList.get(position).getMusicTypeUrls().getInterval())));
                   viewHolder.seekBarCompat.setResLength(MusicTimeUtil.getProgress(dataList.get(position).getMusicTypeUrls().getInterval()));
               }else {
                   //加载网络专辑图片
                   loadPileCoverImage(position, viewHolder);
               }

               ViewHolder tempViewHolder = viewHolder;
               //监听播放时的进度回调
               viewHolder.seekBarCompat.setMusicBarTouchChangeListener(new SeekBar.OnSeekBarChangeListener() {
                   @Override
                   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                       tempViewHolder.playingTime.setText(MusicTimeUtil.
                               getMusicProgress(seekBar.getProgress()));
                   }

                   @Override
                   public void onStartTrackingTouch(SeekBar seekBar) {

                   }

                   @Override
                   public void onStopTrackingTouch(SeekBar seekBar) {
                       //停止拖动时
                       float bfb = (float) seekBar.getProgress() / (float) seekBar.getMax();
                       musicPlayService.getMediaManger().seekTo(bfb);

                   }
               });
               musicPlayService.getMediaManger().setPlayCompleteListener(mp -> {
                   Log.d("asdasdas", "bindView:  播放完成" );

                       ViewHolder holder  = viewHolders.get(playingIndex);
                       holder.playingTime.setText(MusicTimeUtil.
                               getMusicProgress(holder.seekBarCompat.getResLength()));

                       holder.seekBarCompat.setMusicPlayProgress(holder.seekBarCompat.getResLength());

                        pileLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.seekBarCompat.setMusicPlayProgress(0);
                                //下一曲
                                pileLayout.next();
                            }
                        },1000);



               });
                musicPlayService.getMediaManger().setProgressListener(progress -> {

                       //设置进度条
                       ViewHolder holder = viewHolders.get(playingIndex);
                       int pro  = (int) (holder.seekBarCompat.getResLength() * progress);
                       holder.seekBarCompat.setMusicPlayProgress(pro);
                       lyricView.setProgress(pro,false);
                       Log.d("asdasdas", "bindView: " + progress);
                   });

               //点击播放按钮
               viewHolder.musicPlay.setPlayPauseListener(new PlayPauseView.PlayPauseListener() {
                   @Override
                   public void play() {
                       musicManager = musicPlayService.getMediaManger();
                       if (musicManager.isHavePath()) {
                           musicManager.resumeBackgroundMusic();
                           return;
                       }

                       playNewMusic(position, tempViewHolder);
                   }

                   @Override
                   public void pause() {
                       musicManager = musicPlayService.getMediaManger();
                       if (musicManager.isHavePath()) {
                           if (musicManager.isPlaying()) {
                               musicManager.pauseBackgroundMusic();
                               return;
                           }
                       }

                   }
               });



           }

           @Override
           public int getItemCount() {
               return dataList.size();
           }

           @Override
           public void displaying(int position) {
               musicPlayService.getMediaManger().end();
               //加载歌词

               //初始化
               ViewHolder holder = viewHolders.get(position);
               holder.seekBarCompat.setMusicPlayProgress(0);
               holder.seekBarCompat.setBufferProgress(0);
                holder.musicPlay.pause(false);
               lyricView.showLoading();
               if (playingIndex != -1){
                   playNewMusic(position, holder);
               }
               playingIndex = position;
               //播放新音乐


               lyricView.setOnPlayClickListener(new LyricView.OnPlayClickListener() {
                   @Override
                   public void onClick(int progress) {
                       //点击歌词
                       float bfb = (float) (progress/1000) / (float) MusicTimeUtil.
                               getProgress(dataList.get(position).getMusicTypeUrls().getInterval());

                       musicPlayService.getMediaManger().seekTo(bfb);
                   }
               });

               comment.saveNextPosition(playingIndex, "hot comments");
               changeComment(dataList.get(playingIndex));
               if (lastDisplay < 0) {
                   initSecene(position);
                   lastDisplay = 0;
               } else if (lastDisplay != position) {
                   transitionSecene(position);
                   lastDisplay = position;
               }
           }

       });

//       new Thread(new Runnable() {
//           @Override
//           public void run() {
//               try {
//                   Constant.cookie =   Constant.getUrlCookieAsync(URL_BASE);
//               } catch (IOException e) {
//                   e.printStackTrace();
//               }
//           }
//       }).start();
   }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }



    private void loadPileCoverImage(int index, ViewHolder viewHolder){


            if (index < dataList.size()){
                //刷新
                MusicEntity entity = dataList.get(index);
                if (entity.getMusicTypeUrls() == null){
                    HttpUtil.getHttpUtilInstance().getServer().getMusicUrl(entity.getSong().getSongmid())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<MusicUrl>() {
                                @Override
                                public void accept(MusicUrl musicUrl) throws Exception {
                                    if (musicUrl.code == 0){
                                        entity.setMusicTypeUrls(musicUrl);
                                        Glide.with(MainActivity.this).load(entity.getMusicTypeUrls().getUrl().getAblum_pic()).into(viewHolder.imageView);
                                        viewHolder.musicLength.setText(MusicTimeUtil.getMusicProgress(MusicTimeUtil.getProgress(entity.getMusicTypeUrls().getInterval())));
                                        viewHolder.seekBarCompat.setResLength(MusicTimeUtil.getProgress(entity.getMusicTypeUrls().getInterval()));
                                        return;
                                    }


                                    //出错
                                    TipUtils.showShortToast( musicUrl.getMsg());
                                    viewHolder.musicLength.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadPileCoverImage(index,viewHolder);
                                        }
                                    },1000);

                                }
                            });
                }

        }
    }

    //播放新的音乐
    private void playNewMusic(int position,ViewHolder viewHolder){

        //没有路径 就 播放 当前 path  音乐
        MusicEntity entity = dataList.get(position);
        if (entity.getNormalUrl() != null){
            if (MuiscCacheUtil.isCache(MainActivity.this,entity.getNormalUrl())){

                musicManager.playMusic(MuiscCacheUtil.
                        proxyCacheUrl(MainActivity.this,entity.getNormalUrl()),false);

                if (!viewHolder.musicPlay.isPlaying()){
                    viewHolder.musicPlay.play();
                }
                //加载歌词
                MusicEntity musicEntity = dataList.get(position);
                MusicPlayManger.getInstance(MainActivity.this)
                        .loadLyric(musicEntity,lyricView);
                if (musicEntity.getLyric() != null) {
                    lyricView.setLrcRows(dataList.get(position).getLyric());
                }
            }
            return;
        }

        if (entity.getMusicTypeUrls() == null){
            TipUtils.showShortToast("数据加载失败！");
            return;
        }

        //加载歌词
        MusicEntity musicEntity = dataList.get(position);
        MusicPlayManger.getInstance(MainActivity.this)
                .loadLyric(musicEntity,lyricView);
        if (musicEntity.getLyric() != null) {
            lyricView.setLrcRows(dataList.get(position).getLyric());
        }

        //去加载网络真实音乐地址
        HttpUtil.getHttpUtilInstance().getServer().getMusicTypeUrl(entity.getMusicTypeUrls().getUrl().getMp3_64())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        String url = jsonObject.get("url").toString();
                        if (url == null) {
                            return;
                        }
                        if (url.length() < 10) {
                            playNewMusic(position,viewHolder);
                            return;
                        }
                        String[] urls = url.split("\\?");
                        if (urls != null && urls.length > 0){
                            url = urls[0];
                        }

                        musicManager =  musicPlayService.getMediaManger();
//                        url = "http://freetyst.nf.migu.cn/public/product9th/product42/2020/11/1710/2020%E5%B9%B409%E6%9C%8815%E6%97%A515%E7%82%B935%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E5%8D%8E%E7%BA%B31%E9%A6%96/%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC/Mp3_64_22_16/6005759Z0Q0102623.mp3";
                        if (MuiscCacheUtil.isCache(MainActivity.this , url)){

                            musicManager.playMusic(MuiscCacheUtil.
                                    proxyCacheUrl(MainActivity.this,url),false);
                            if (!viewHolder.musicPlay.isPlaying()){
                                viewHolder.musicPlay.play();
                            }


                            return;
                        }
                        //没有缓存  需要网络缓存

                        musicManager.playMusic(MuiscCacheUtil.proxyCacheUrl(MainActivity.this,url,
                                (cacheFile, url1, percentsAvailable) -> {
                                    //设置缓存进度

                                    viewHolder.seekBarCompat.setBufferProgress(percentsAvailable);
                                    if (percentsAvailable == 100){
                                        viewHolder.seekBarCompat.setBufferGone();
                                        entity.setNormalUrl(url1);
                                    }


                                }),false);
                        if (!viewHolder.musicPlay.isPlaying()){
                            viewHolder.musicPlay.play();
                        }

                    }
                });
    }
    private void initSecene(int position) {
        musicName.firstInit(dataList.get(position).getSong().getSongname());
//        temperatureView.firstInit(dataList.get(position).getTemperature());
            comment.saveNextPosition(position, "hot comments");
//        bottomView.firstInit(dataList.get(position).getMapImageUrl());
//        timeView.firstInit(dataList.get(position).getTime());
    }

    private void transitionSecene(int position) {
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }

        musicName.saveNextPosition(position, dataList.get(position).getSong().getSongname());
//        temperatureView.saveNextPosition(position, dataList.get(position).getTemperature());
         comment.saveNextPosition(position, "hot comments");
//        bottomView.saveNextPosition(position, dataList.get(position).getMapImageUrl());
//        timeView.saveNextPosition(position, dataList.get(position).getTime());

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);

    }

    /**
     * 调整沉浸状态栏
     */
    private void adjustStatusBarHeight() {
        int statusBarHeight = Utils.getStatusBarHeight(this);
        ViewGroup.LayoutParams lp = positionView.getLayoutParams();
        lp.height = statusBarHeight;
        positionView.setLayoutParams(lp);
    }

    /**
     * 修改评论
     * @param musicEntity
     */
    private void changeComment(MusicEntity musicEntity){

        if (musicEntity != null){
            if (musicEntity.getComment() == null){
                //网络请求评论
                //去加载网络真实音乐地址
                HttpUtil.getHttpUtilInstance("http://music.163.com/api/v1/resource/comments/")
                        .getServer().getMusicComment("R_SO_4_1479003964" )
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Comment>() {
                            @Override
                            public void accept(Comment comment) throws Exception {

                                if (comment != null) {
                                    musicEntity.setComment(comment);
                                    changeComment(musicEntity);
                                }
                            }
                        });

                return;
            }


            Comment comment = musicEntity.getComment();
            List<Comment.HotComment> comments = comment.getHotComments();
            if (comments.size() > 0){
                Comment.HotComment pl = comments.get(0);
                commentContent.setText(pl.getContent());
                commentUserNickName.setText(pl.getUser().getNickname());
                Glide.with(MainActivity.this).load(pl.getUser().getAvatarurl())
                        .into(commentHeadImage);
                commentTime.setText(MusicTimeUtil.stampToDate(pl.getTime()));
                commentLikeConut.setText(""+pl.getLikedCount());
            }


        }


    }
    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    /**
     * 从asset读取文件json数据
     */
    private void initDataList() {

        dataList = new ArrayList<>();

        pileLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                SearchMusic searchMusic = gson.fromJson(Constant.json, SearchMusic.class);

                if (searchMusic.code == 0){
                    for (SearchMusic.Song song  : searchMusic.getSongList()){
                        MusicEntity music = new MusicEntity();
                        music.setSong(song);
                        dataList.add(music);
                    }
                    pileLayout.notifyDataSetChanged();

                }

            }
        },1000);

//        HttpUtil.getHttpUtilInstance().getServer().serarchMusic("交换余生",1,20)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<SearchMusic>() {
//
//                    @Override
//                    public void accept(SearchMusic searchMusic) throws Exception {
//                        Log.d("successful", "accept: "+ searchMusic.toString());
//                        if (searchMusic.code == 0){
//                            for (SearchMusic.Song song  : searchMusic.getSongList()){
//                                MusicEntity music = new MusicEntity();
//                                music.setSong(song);
//                                dataList.add(music);
//                            }
//                            pileLayout.notifyDataSetChanged();
//                            return;
//                        }
//
//                        TipUtils.showShortToast(searchMusic.getMsg());
//
//                    }
//                });

    }

    /**
     * 属性动画  ValueAnimatior 设置的target :this  这个setTransitionValue 方法
     */
    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
        musicName.duringAnimation(transitionValue);
        temperatureView.duringAnimation(transitionValue);
        comment.duringAnimation(transitionValue);
        bottomView.duringAnimation(transitionValue);
        timeView.duringAnimation(transitionValue);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (musicPlayService != null) {
            musicPlayService.stopSelf();
        }
    }

    public float getTransitionValue() {
        return transitionValue;
    }


    class ViewHolder {
        //专辑图
        ImageView imageView;

        //播放 暂停按钮
        PlayPauseView musicPlay;

        //开始播放进度
        TextView playingTime;

        //音乐长度
        TextView musicLength;

        //音乐进度条 和缓冲
        SeekBarCompat seekBarCompat;


    }
}