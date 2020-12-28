package com.rainy.rainymusic.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * create by Rainy on 2020/12/22.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class Comment {

    private boolean isMusician;

    private int userId;


    private boolean moreHot;

    private List<HotComment> hotComments ;

    private String commentBanner;

    private int code;


    private int total;

    private boolean more;

    public void setIsMusician(boolean isMusician){
        this.isMusician = isMusician;
    }
    public boolean getIsMusician(){
        return this.isMusician;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }

    public void setMoreHot(boolean moreHot){
        this.moreHot = moreHot;
    }
    public boolean getMoreHot(){
        return this.moreHot;
    }
    public void setHotComments(List<HotComment> hotComments){
        this.hotComments = hotComments;
    }
    public List<HotComment> getHotComments(){
        return this.hotComments;
    }
    public void setCommentBanner(String commentBanner){
        this.commentBanner = commentBanner;
    }
    public String getCommentBanner(){
        return this.commentBanner;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setMore(boolean more){
        this.more = more;
    }
    public boolean getMore(){
        return this.more;
    }


    public static class HotComment {
        private User user;



//        private String pendantData;
//
//        private String showFloorComment;
//
//        private int status;
//
//        private int commentId;

        private String content;

        private int time;

        private int likedCount;

//        private String expressionUrl;
//
//        private int commentLocationType;
//
//        private int parentCommentId;
//
//        private String decoration;
//
//        private String repliedMark;
////
//        private boolean liked;

        public void setUser(User user){
            this.user = user;
        }
        public User getUser(){
            return this.user;
        }
//        public void setPendantData(String pendantData){
//            this.pendantData = pendantData;
//        }
//        public String getPendantData(){
//            return this.pendantData;
//        }
//        public void setShowFloorComment(String showFloorComment){
//            this.showFloorComment = showFloorComment;
//        }
//        public String getShowFloorComment(){
//            return this.showFloorComment;
//        }
//        public void setStatus(int status){
//            this.status = status;
//        }
//        public int getStatus(){
//            return this.status;
//        }
//        public void setCommentId(int commentId){
//            this.commentId = commentId;
//        }
//        public int getCommentId(){
//            return this.commentId;
//        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setTime(int time){
            this.time = time;
        }
        public int getTime(){
            return this.time;
        }
        public void setLikedCount(int likedCount){
            this.likedCount = likedCount;
        }
        public int getLikedCount(){
            return this.likedCount;
        }
//        public void setExpressionUrl(String expressionUrl){
//            this.expressionUrl = expressionUrl;
//        }
//        public String getExpressionUrl(){
//            return this.expressionUrl;
//        }
//        public void setCommentLocationType(int commentLocationType){
//            this.commentLocationType = commentLocationType;
//        }
//        public int getCommentLocationType(){
//            return this.commentLocationType;
//        }
//        public void setParentCommentId(int parentCommentId){
//            this.parentCommentId = parentCommentId;
//        }
//        public int getParentCommentId(){
//            return this.parentCommentId;
//        }
//        public void setDecoration(String decoration){
//            this.decoration = decoration;
//        }
//        public String getDecoration(){
//            return this.decoration;
//        }
//        public void setRepliedMark(String repliedMark){
//            this.repliedMark = repliedMark;
//        }
//        public String getRepliedMark(){
//            return this.repliedMark;
//        }
//        public void setLiked(boolean liked){
//            this.liked = liked;
//        }
//        public boolean getLiked(){
//            return this.liked;
//        }

    }


    public static class User{

//        @SerializedName("locationInfo")
//        private String locationinfo;
//        @SerializedName("liveInfo")
//        private String liveinfo;
//        private int anonym;
//        @SerializedName("userId")
//        private int userid;
//        @SerializedName("avatarDetail")
//        private String avatardetail;
//        @SerializedName("userType")
//        private int usertype;
//        @SerializedName("remarkName")
//        private String remarkname;
//        @SerializedName("vipRights")
//        private String viprights;
        private String nickname;
        @SerializedName("avatarUrl")
        private String avatarurl;
//        @SerializedName("authStatus")
//        private int authstatus;
//        @SerializedName("expertTags")
//        private String experttags;
//        private String experts;
//        @SerializedName("vipType")
//        private int viptype;
//        public void setLocationinfo(String locationinfo) {
//            this.locationinfo = locationinfo;
//        }
//        public String getLocationinfo() {
//            return locationinfo;
//        }
//
//        public void setLiveinfo(String liveinfo) {
//            this.liveinfo = liveinfo;
//        }
//        public String getLiveinfo() {
//            return liveinfo;
//        }
//
//        public void setAnonym(int anonym) {
//            this.anonym = anonym;
//        }
//        public int getAnonym() {
//            return anonym;
//        }
//
//        public void setUserid(int userid) {
//            this.userid = userid;
//        }
//        public int getUserid() {
//            return userid;
//        }
//
//        public void setAvatardetail(String avatardetail) {
//            this.avatardetail = avatardetail;
//        }
//        public String getAvatardetail() {
//            return avatardetail;
//        }
//
//        public void setUsertype(int usertype) {
//            this.usertype = usertype;
//        }
//        public int getUsertype() {
//            return usertype;
//        }
//
//        public void setRemarkname(String remarkname) {
//            this.remarkname = remarkname;
//        }
//        public String getRemarkname() {
//            return remarkname;
//        }
//
//        public void setViprights(String viprights) {
//            this.viprights = viprights;
//        }
//        public String getViprights() {
//            return viprights;
//        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public String getNickname() {
            return nickname;
        }

        public void setAvatarurl(String avatarurl) {
            this.avatarurl = avatarurl;
        }
        public String getAvatarurl() {
            return avatarurl;
        }

//        public void setAuthstatus(int authstatus) {
//            this.authstatus = authstatus;
//        }
//        public int getAuthstatus() {
//            return authstatus;
//        }
//
//        public void setExperttags(String experttags) {
//            this.experttags = experttags;
//        }
//        public String getExperttags() {
//            return experttags;
//        }
//
//        public void setExperts(String experts) {
//            this.experts = experts;
//        }
//        public String getExperts() {
//            return experts;
//        }
//
//        public void setViptype(int viptype) {
//            this.viptype = viptype;
//        }
//        public int getViptype() {
//            return viptype;
//        }
    }
}
