package com.rainy.rainymusic.entity;

/**
 * create by Rainy on 2020/12/16.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:  请求音乐网络魔声接口的 状态码 和信息
 */
public class BaseMSCode {

    public int code;

    private String msg;


    public String getMsg(){

        if (msg != null){
            String m = msg.split("：")[0];
            if (m != null){
                return m;
            }
            return msg;
        }

        return null;
    }


}
