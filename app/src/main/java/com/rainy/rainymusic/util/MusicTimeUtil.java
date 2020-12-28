package com.rainy.rainymusic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by Rainy on 2020/12/16.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class MusicTimeUtil {

    /**
     *
     * @param progress  300 秒
     * @return   05:00
     */
    public static String getMusicProgress(int progress){
         if (progress == -1){
             return  "-1";
         }
         StringBuilder  timeBuilder =  new StringBuilder();
         int hour = progress / 3600;
         int min = progress / 60;
         int millon = progress - (min * 60) - (hour * 60);
         if (hour != 0){
             if (hour < 10){
                 timeBuilder.append("0");
             }
             timeBuilder.append(hour);
             timeBuilder.append(":");
         }

         if (min != 0){
             if (min < 10){
                 timeBuilder.append("0");
             }
             timeBuilder.append(min);
             timeBuilder.append(":");
         }else {
             if (hour != 0){
                 timeBuilder.append("00");
             }
         }

          if (millon != 0){
              if (min == 0){
                  timeBuilder.append("00:");
              }
             if (millon < 10){
                 timeBuilder.append("0");
             }
             timeBuilder.append(millon);
         }else {
              if (hour != 0 || min != 0){
                  timeBuilder.append("00");
              }
          }

         return timeBuilder.length() == 0 ? "00:00"
                 : timeBuilder.toString();
    }

    /**
     *
     *
     * @param time  00:30:50   时间
     * @return
     */

    public static int getProgress(String time){
        if (time != null){
            int progress = 0;
            int base = 1;
            String[] times = time.split(":");

            if (times == null){
                return 0;
            }

            for (int i = times.length -1 ; i >= 0; i--){
               progress += (Integer.valueOf(times[i]) * base);
               base *=60;
            }

            return progress;
        }

        return 0;
    }



    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long time){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }
}
