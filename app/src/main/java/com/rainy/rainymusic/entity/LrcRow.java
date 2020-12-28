package com.rainy.rainymusic.entity;

import android.text.TextUtils;
import android.util.Log;

import com.rainy.rainymusic.util.MusicTimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Rainy on 2020/12/18.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class LrcRow   implements Comparable<LrcRow>{

    private String content;
    private int time;
    private int totalTime;

    public LrcRow( int time, String content) {
        this.time = time;
        this.content = content;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }


    public String getTimeStr() {

        return MusicTimeUtil.getMusicProgress(time/1000);
    }

    /**
     * 将歌词文件中的某一行 解析成一个List<LrcRow>
     * 因为一行中可能包含了多个LrcRow对象
     * 比如  [03:33.02][00:36.37]当鸽子不再象征和平  ，就包含了2个对象
     *
     * @param lrcLine
     * @return
     */
    public static final List<LrcRow> createRows(String lrcLine) {
        if (!lrcLine.startsWith("[")) {
            return null;
        }
        //最后一个"]"
        int lastIndexOfRightBracket = lrcLine.lastIndexOf("]");
        //歌词内容
        String content = lrcLine.substring(lastIndexOfRightBracket + 1, lrcLine.length());
        //截取出歌词时间，并将"[" 和"]" 替换为"-"   [offset:0]
        Log.e("歌词","lrcLine=" + lrcLine);
        // -03:33.02--00:36.37-
        String times = lrcLine.substring(0, lastIndexOfRightBracket + 1).replace("[", "-").replace("]", "-");
        String[] timesArray = times.split("-");
        List<LrcRow> lrcRows = new ArrayList<LrcRow>();
        for (String tem : timesArray) {
            if (TextUtils.isEmpty(tem.trim())) {
                continue;
            }
            //
            try {
                LrcRow lrcRow = new LrcRow(formatTime(tem), content);
                lrcRows.add(lrcRow);
            } catch (Exception e) {
                Log.w("LrcRow", e.getMessage());
            }
        }
        return lrcRows;
    }

    /****
     * 把歌词时间转换为毫秒值  如 将00:10.00  转为10000
     *
     * @param timeStr
     * @return
     */
    private static int formatTime(String timeStr) {
        timeStr = timeStr.replace('.', ':');
        String[] times = timeStr.split(":");

        return Integer.parseInt(times[0]) * 60 * 1000
                + Integer.parseInt(times[1]) * 1000
                + Integer.parseInt(times[2]);
    }


    @Override
    public int compareTo(LrcRow o) {
        return (int) (this.time - o.time);
    }
}
