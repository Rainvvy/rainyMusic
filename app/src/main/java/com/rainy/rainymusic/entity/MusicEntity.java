package com.rainy.rainymusic.entity;


import java.util.List;

/**
 * Created by xmuSistone on 2017/5/12.
 */

public class MusicEntity {

    //音乐  url 地址

    private MusicUrl musicTypeUrls;

    //歌名，歌手

    private SearchMusic.Song song;

    //音乐时长

    private int length = 0;

    //歌词

    private List<LrcRow> lyrics ;

    //评论

    private Comment comment;

    //标准url 就是 64MP3 类型

    private String normalUrl;


    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public MusicUrl getMusicTypeUrls() {
        return musicTypeUrls;
    }

    public void setMusicTypeUrls(MusicUrl musicTypeUrls) {
        this.musicTypeUrls = musicTypeUrls;
    }

    public SearchMusic.Song getSong() {
        return song;
    }

    public void setSong(SearchMusic.Song song) {
        this.song = song;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<LrcRow> getLyric() {
        return lyrics;
    }

    public void setLyric(List<LrcRow> lyric) {
        this.lyrics = lyric;
    }

    public String getNormalUrl() {
        return normalUrl;
    }

    public void setNormalUrl(String normalUrl) {
        this.normalUrl = normalUrl;
    }
//    private String country;
//    private String temperature;
//    private String coverImageUrl;
//    private String address;
//    private String description;
//    private String time;
//    private String mapImageUrl;

}
