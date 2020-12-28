package com.rainy.rainymusic;

import android.content.Context;
import android.util.Log;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

import java.io.File;
import java.io.IOException;

/**
 * create by Rainy on 2020/12/12.
 * email: im.wyu@qq.com
 * github: Rainvy
 * describe:
 */
public class Constant {


    public static String json = "{\"code\":0,\"page\":\"1\",\"num\":\"20\",\"totalnum\":\"251\",\"song_list\":[{\"albumid\":\"1135291776\",\"albumname\":\"交换余生\",\"songmid\":\"600908000005572453_6005759Z0Q0_24bit|600906000000326390\",\"songname\":\"交换余生<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《交换余生》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717286\",\"albumname\":\"Like You Do 如你\",\"songmid\":\"600908000005911379_6005759Z0R6_24bit\",\"songname\":\"Not Tonight (Tomorrow Sounds Good Steve Aoki Remix)<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《Like You Do 如你》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717286\",\"albumname\":\"Like You Do 如你\",\"songmid\":\"600908000005689935_6005759Z0QC_24bit\",\"songname\":\"While I Can<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《Like You Do 如你》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717285\",\"albumname\":\"幸存者 Drifter\",\"songmid\":\"600908000005572519_6005759Z0QB_24bit\",\"songname\":\"交换余生<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《幸存者 Drifter》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1002142415\",\"albumname\":\"新地球\",\"songmid\":\"600910000008055474_6005751KT2J_SQ\",\"songname\":\"可惜没如果 (电视剧《对我而言，可爱的她》片尾曲)<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《新地球》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717285\",\"albumname\":\"幸存者 Drifter\",\"songmid\":\"600908000005572489_6005759Z0Q6_24bit\",\"songname\":\"幸存者<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《幸存者 Drifter》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1115604540\",\"albumname\":\"白羽毛之恋\",\"songmid\":\"600913000008152688_6005752BNV9_SQ\",\"songname\":\"修炼爱情<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《白羽毛之恋》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717285\",\"albumname\":\"幸存者 Drifter\",\"songmid\":\"600908000005572459_6005759Z0Q1_24bit\",\"songname\":\"最好是<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《幸存者 Drifter》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1118705914\",\"albumname\":\"将故事写成我们\",\"songmid\":\"600908000005562716_6005759Z0PQ_SQ|600906000000273560\",\"songname\":\"将故事写成我们<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《将故事写成我们》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1001416291\",\"albumname\":\"他是…JJ林俊杰\",\"songmid\":\"600902000008745944_60058622891_SQ\",\"songname\":\"她说(电视剧《爱情睡醒了》插曲)<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《他是…JJ林俊杰》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717285\",\"albumname\":\"幸存者 Drifter\",\"songmid\":\"600908000005572507_6005759Z0Q9_24bit\",\"songname\":\"离开的那一些<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《幸存者 Drifter》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":0,\"albumname\":\"无\",\"songmid\":\"600913000008940388_6005752CGR6_LQ\",\"songname\":\"修炼爱情(3D Audio)\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717285\",\"albumname\":\"幸存者 Drifter\",\"songmid\":\"600908000005572471_6005759Z0Q3_24bit\",\"songname\":\"最向往的地方<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《幸存者 Drifter》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1134994539\",\"albumname\":\"无滤镜 (feat. 藤原浩)\",\"songmid\":\"600908000005562746_6005759Z0PV_24bit\",\"songname\":\"无滤镜 (feat. 藤原浩)<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《无滤镜 (feat. 藤原浩)》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135717285\",\"albumname\":\"幸存者 Drifter\",\"songmid\":\"600908000005572477_6005759Z0Q4_24bit\",\"songname\":\"暂时的记号<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FF0000;font-size:10px;color:#FF0000;\\\">24bit</sup><sup>《幸存者 Drifter》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"65730\",\"albumname\":\"学不会\",\"songmid\":\"600910000004570073_6005751FH33_SQ\",\"songname\":\"那些你很冒险的梦<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《学不会》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1135042872\",\"albumname\":\"Stay With You（英文版）\",\"songmid\":\"600908000005572429_6005759Z0PW_SQ\",\"songname\":\"Stay With You (英文版)<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《Stay With You（英文版）》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"},{\"id\":\"259\",\"name\":\"孙燕姿\"}]},{\"albumid\":\"1001416291\",\"albumname\":\"他是…JJ林俊杰\",\"songmid\":\"600902000009222909_60084600029_SQ\",\"songname\":\"江南<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《他是…JJ林俊杰》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1001416291\",\"albumname\":\"他是…JJ林俊杰\",\"songmid\":\"600902000007915995_60084600253_SQ\",\"songname\":\"江南(DJ版)<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《他是…JJ林俊杰》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]},{\"albumid\":\"1112933562\",\"albumname\":\"和自己对话 From M.E. To Myself\",\"songmid\":\"600908000008738861_6005759Z0SF_SQ\",\"songname\":\"不为谁而作的歌<sup style=\\\"margin-left:5px;border-radius:5px;padding:0px 3px;border:1px solid #FFA500;font-size:10px;color:#FFA500;\\\">SQ</sup><sup>《和自己对话 From M.E. To Myself》</sup>\",\"singer\":[{\"id\":\"266\",\"name\":\"林俊杰\"}]}]}";

    private static final String RAINY_MUSIC_CACHE_DIR = "cache/audio/";

    //歌词路径

    public static final String LYRIC = "cache/lyric";

    public static String cookie = "XLA_CI=c7c53a6e62456d85c016521a08dad157; IP=JRZfl%252B%252BTBhFIZjXPA1X4430QEY8OKmggiZn6AeUizSI%253D; QQMUSIC_SIP=%5B%22http%3A//183.66.104.113/amobile.music.tc.qq.com/%22%2C%22http%3A//183.66.104.170/amobile.music.tc.qq.com/%22%2C%22http%3A//183.66.104.171/amobile.music.tc.qq.com/%22%5D; QQ=";

    // 1G

    public static final int MAX_CACHE_SIZE  = 1024 * 1024 * 1024;

    public static final int MAX_CACHE_COUNT  = 100;

    //   /data/user/0/com.zhangyu.myfilepath/cache

    public  static File getCacheFile(Context context){

        Log.d("asdasd", "getCacheFile: dir -- " +  context.getExternalFilesDir(null).getParent() +  File.separator + RAINY_MUSIC_CACHE_DIR );
        return new File(context.getExternalFilesDir(null).getParent() +  File.separator + RAINY_MUSIC_CACHE_DIR ) ;
    }

    public static String getLyricDir(Context context){
        return context.getExternalFilesDir(null).getParent() +  File.separator + LYRIC;
    }

    //获取url  cookie

    public static String getUrlCookieAsync(String url) throws IOException{

            String cookie = "";
            //构造一个webClient 模拟Chrome 浏览器
            WebClient webClient = new WebClient(BrowserVersion.CHROME);

            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(2000);

            webClient.getPage(url);

            //设置一个运行JavaScript的时间
            webClient.waitForBackgroundJavaScript(100);


            cookie = "XLA_CI=" + webClient.getCookieManager().getCookie("XLA_CI").getValue()
                    + ";IP=" + webClient.getCookieManager().getCookie("IP").getValue()
                    + ";QQMUSIC_SIP="+webClient.getCookieManager().getCookie("QQMUSIC_SIP").getValue()
                    + ";QQ=";

//
            Log.d("cookieaaaaa", "init: " + cookie );


        return cookie;
    }
}
