package com.rainy.rainymusic.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.rainy.rainymusic.http.gsonadapter.DoubleDefault0Adapter;
import com.rainy.rainymusic.http.gsonadapter.IntegerDefault0Adapter;
import com.rainy.rainymusic.http.gsonadapter.ListJsonDeserializer;
import com.rainy.rainymusic.http.gsonadapter.LongDefault0Adapter;
import com.rainy.rainymusic.http.interceptor.HeaderInterceptor;
import com.rainy.rainymusic.http.interceptor.ResponseInterceptor;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;


import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by AaronYang on 2018/5/31.
 * email: 1390939057@qq.com
 * github: AaronYang23
 * describe: 网络请求的工具类
 */
public class HttpUtil {
    private static HttpUtil httpUtil;
    public static final long CONNECT_TIMEOUT = 20;
    public static final long READ_TIMEOUT = 40;
    public static final long WRITE_TIMEOUT = 40;
    public static final long SUCCECESSCODE = 2000000;

    //域名地址   线上

    public static final String URL_BASE = "http://music.moresound.tk/";






    private Retrofit retrofit;
    private RequestServer server;
    private Gson gson;



    private HttpUtil(boolean isResponseIntrceptor) {
        initRetrofit(isResponseIntrceptor);
    }


    private HttpUtil(boolean isResponseIntrceptor,String baseUrl) {
        initRetrofit(isResponseIntrceptor,baseUrl);
    }

    public static HttpUtil getHttpUtilInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpUtil(true);
        }
        return httpUtil;
    }

    //选择是否需要 请求后的拦截器
    public static HttpUtil getHttpUtilInstance(boolean isResponseIntrceptor) {

        return new HttpUtil(isResponseIntrceptor);
    }

     public static HttpUtil getHttpUtilInstance(String urlBase) {

        return new HttpUtil(true,urlBase);
    }


    /**
     * desc: Retrofit配置
     */
    private void initRetrofit(boolean isResponseIntrceptor) {
        OkHttpClient.Builder okClient = new OkHttpClient.Builder();

        okClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//写入超时设置，
                .readTimeout(SUCCECESSCODE, TimeUnit.SECONDS);//读取超时设置;
        //添加拦截器
        //请求前
        okClient.addInterceptor(new HeaderInterceptor());

//        if (LogUtil.FLAG) {//添加日志打印拦截器
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            okClient.addInterceptor(loggingInterceptor);
//        }

        //响应后
        if (isResponseIntrceptor) {
            okClient.addInterceptor(new ResponseInterceptor());
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(okClient.build())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        server = retrofit.create(RequestServer.class);

    }

    private void initRetrofit(boolean isResponseIntrceptor,String urlBase) {
        OkHttpClient.Builder okClient = new OkHttpClient.Builder();

        okClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//写入超时设置，
                .readTimeout(SUCCECESSCODE, TimeUnit.SECONDS);//读取超时设置;
        //添加拦截器
        //请求前
        okClient.addInterceptor(new HeaderInterceptor());

//        if (LogUtil.FLAG) {//添加日志打印拦截器
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            okClient.addInterceptor(loggingInterceptor);
//        }

        //响应后
        if (isResponseIntrceptor) {
            okClient.addInterceptor(new ResponseInterceptor());
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .client(okClient.build())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        server = retrofit.create(RequestServer.class);

    }

    public Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .registerTypeHierarchyAdapter(List.class, new ListJsonDeserializer())
                    .create();
        }
        return gson;
    }


    public RequestServer getServer() {
        return server;
    }

    public void writeFile2Disk(Response<ResponseBody> response, File file) {
        long currentLength = 0;
        OutputStream os = null;
        // 要跳到那里去改好bug再市场更新app 除了分支，因为版本多了分支会多 最好的办法是什么，对于版本回退的操作
        //ps ：分支要建立一个当前市场最新的版本app 方便改bug  就是说新的开发都要建分支开发
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
//                LogUtil.d("vivi","当前进度:"+currentLength);
//                httpCallBack.onLoading(currentLength,totalLength);
            }
            // httpCallBack.onLoading(currentLength,totalLength,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
