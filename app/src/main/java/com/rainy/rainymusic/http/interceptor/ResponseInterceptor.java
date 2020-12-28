package com.rainy.rainymusic.http.interceptor;



import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * create by AaronYang on 2018/6/2.
 * email: 1390939057@qq.com
 * github: AaronYang23
 * describe: 回应拦截器：添加返回体对code的处理
 */
public class ResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

//        try {
//            Request request = chain.request();
//
//            Response response = chain.proceed(request);
//
//            String reponseString = response.body().string();
//
//e
//            LogUtil.e("response:" + reponseString);
////
//            //服务器非正常状态码处理
//            ApiResponse result = new Gson().fromJson(reponseString, ApiResponse.class);
//
//            LogUtil.e("resultcode转化:" + result.toString());
//
//            if (result.getStatus() != HttpUtil.SUCCECESSCODE) {
//                Log.i("=============", "抛出服务器状态码异常");
//                throw new ServerResultCodeException(result.getStatus(), result.getMessage());
//            }
//            LogUtil.i("ResponseInterceptor");
//
//            return chain.proceed(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//        https://www.jianshu.com/p/230e2e2988e0
        Response response = chain.proceed(chain.request());

        if (response.body() != null && response.body().contentType() != null) {
            MediaType mediaType = response.body().contentType();
            String string = response.body().string();
//            LogUtil.e("response:" + string);
//            //服务器非正常状态码处理
//            ApiResponse result = new Gson().fromJson(string, ApiResponse.class);
//            LogUtil.e("resultcode转化:" + result.toString());
//
//            if (result.getStatus() != HttpUtil.SUCCECESSCODE) {
//                Log.i("=============", "抛出服务器状态码异常");
//                throw new ServerResultCodeException(result.getStatus(), result.getMessage());
//            }
            Log.d("testttt", "intercept: " + string);
            ResponseBody responseBody = ResponseBody.create(mediaType, string);
            return response.newBuilder().body(responseBody).build();
        } else {
            return response;
        }


    }

}
