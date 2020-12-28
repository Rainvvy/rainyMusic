package com.rainy.rainymusic.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * create by Rainy on 2020/12/10.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class SearchMusic  extends  BaseMSCode{


    private String page;
    private String num;
    private String totalnum;

    @SerializedName("song_list")
    private List<Song> songList;


    public void setPage(String page) {
        this.page = page;
    }
    public String getPage() {
        return page;
    }

    public void setNum(String num) {
        this.num = num;
    }
    public String getNum() {
        return num;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }
    public String getTotalnum() {
        return totalnum;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
    public List<Song> getSongList() {
        return songList;
    }


    public static class Singer {

        private String id;
        private String name;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

    }

    public  static class Song {

        private String albumid;
        private String albumname;
        private String songmid;
        private String songname;
        private List<Singer> singer;
        public void setAlbumid(String albumid) {
            this.albumid = albumid;
        }
        public String getAlbumid() {
            return albumid;
        }

        public void setAlbumname(String albumname) {
            this.albumname = albumname;
        }
        public String getAlbumname() {
            return albumname;
        }

        public void setSongmid(String songmid) {
            this.songmid = songmid;
        }
        public String getSongmid() {
            return songmid;
        }

        public void setSongname(String songname) {
            this.songname = songname;
        }
        public String getSongname() {
            return songname.split("<")[0];
        }

        public void setSinger(List<Singer> singer) {
            this.singer = singer;
        }
        public List<Singer> getSinger() {
            return singer;
        }

    }

    @Override
    public String toString() {
        return "SearchMusic{" +
                "code=" + code +
                ", page='" + page + '\'' +
                ", num='" + num + '\'' +
                ", totalnum='" + totalnum + '\'' +
                ", songList=" + songList +
                '}';
    }
}
