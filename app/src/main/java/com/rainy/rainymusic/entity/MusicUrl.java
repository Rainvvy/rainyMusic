package com.rainy.rainymusic.entity;

import com.google.gson.annotations.SerializedName;
import com.rainy.rainymusic.http.HttpUtil;

/**
 * create by Rainy on 2020/12/10.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class MusicUrl extends BaseMSCode{

    private String singer;

    private String song;

    private String album;

    private String albumid;

    private String interval;

    private String id;

    private TypeUrl url;


    public void setSinger(String singer){
        this.singer = singer;
    }
    public String getSinger(){
        return this.singer;
    }
    public void setSong(String song){
        this.song = song;
    }
    public String getSong(){
        return this.song;
    }
    public void setAlbum(String album){
        this.album = album;
    }
    public String getAlbum(){
        return this.album;
    }
    public void setAlbumid(String albumid){
        this.albumid = albumid;
    }
    public String getAlbumid(){
        return this.albumid;
    }
    public void setInterval(String interval){
        this.interval = interval;
    }
    public String getInterval(){
        return this.interval;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setUrl(TypeUrl url){
        this.url = url;
    }
    public TypeUrl getUrl(){
        return this.url;
    }
    public static class TypeUrl {
        private String MV;

        @SerializedName("24Bit_M4A")
        private String bit_M4A_24;

        @SerializedName("24Bit_FLAC")
        private String bit_FLAC_24;

        private String SQ_M4A;

        private String FLAC;

        @SerializedName("320MP3")
        private String mp3_320;
        @SerializedName("128MP3")
        private String mp3_128;

        @SerializedName("64MP3")
        private String mp3_64;

        @SerializedName("专辑封面")
        private String ablum_pic;

        private String lrc;

        public void setMV(String MV){
            this.MV = MV;
        }
        public String getMV(){
            return this.MV;
        }

        public String getBit_M4A_24() {
            return bit_M4A_24;
        }

        public void setBit_M4A_24(String bit_M4A_24) {
            this.bit_M4A_24 = bit_M4A_24;
        }

        public String getBit_FLAC_24() {
            return bit_FLAC_24;
        }

        public void setBit_FLAC_24(String bit_FLAC_24) {
            this.bit_FLAC_24 = bit_FLAC_24;
        }

        public String getSQ_M4A() {
            return SQ_M4A;
        }

        public void setSQ_M4A(String SQ_M4A) {
            this.SQ_M4A = SQ_M4A;
        }

        public String getFLAC() {
            return FLAC;
        }

        public void setFLAC(String FLAC) {
            this.FLAC = FLAC;
        }

        public String getMp3_320() {
            return mp3_320;
        }

        public void setMp3_320(String mp3_320) {
            this.mp3_320 = mp3_320;
        }

        public String getMp3_128() {
            return mp3_128;
        }

        public void setMp3_128(String mp3_128) {
            this.mp3_128 = mp3_128;
        }

        public String getMp3_64() {
            return mp3_64;
        }

        public void setMp3_64(String mp3_64) {
            this.mp3_64 = mp3_64;
        }

        public String getAblum_pic() {
            return "http:" + ablum_pic;
        }

        public void setAblum_pic(String ablum_pic) {
            this.ablum_pic = ablum_pic;
        }

        public String getLrc() {
            return HttpUtil.URL_BASE + lrc;
        }

        public void setLrc(String lrc) {
            this.lrc = lrc;
        }
    }
}
