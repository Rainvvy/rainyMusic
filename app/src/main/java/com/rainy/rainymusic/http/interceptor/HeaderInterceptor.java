package com.rainy.rainymusic.http.interceptor;




import com.rainy.rainymusic.Constant;
import com.rainy.rainymusic.MainActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * create by AaronYang on 2018/6/2.
 * email: 1390939057@qq.com
 * github: AaronYang23
 * describe: 头部拦截器
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
//        Request.Builder builder = oldRequest.newBuilder();

//        //添加header
//        builder.header("sessionId", SeekNatureApplication.getAppInstance().getToken());
//        request.header(SeekNatureApplication.getAppInstance().getToken());//添加header token

        String newBodyString = "";
        Request.Builder newRequestBuild = oldRequest.newBuilder();
        newRequestBuild.addHeader("Cookie", Constant.cookie);
        //添加公参
        if (oldRequest.method().equals("POST")) {
            if (oldRequest.url().toString().contains("updateUserImg")) {
//                LogUtil.i("addheader:头像更新不编码");
            } else {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();


                RequestBody formBody = formBodyBuilder.build();
                newBodyString = bodyToString(oldRequest.body());
                newBodyString += ((newBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
                newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), newBodyString));
            }


        } else if (oldRequest.method().equals("GET")) {
//            LogUtil.i("==========");
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("deviceId", "123456");
        }

//        Request newRequest = newRequestBuild.header("Cookie","XLA_CI=a036f12da9b9abdd5e94c484808a7c97; XLA_CI=''; IP=XQMB4qANE%252F%252FSDNNyEZCn1FNWMRVN0M9LMNA61mE80to%253D; QQMUSIC_SIP=%5B%22http%3A//183.232.254.151/amobile.music.tc.qq.com/%22%2C%22http%3A//183.232.254.152/amobile.music.tc.qq.com/%22%2C%22http%3A//183.232.254.153/amobile.music.tc.qq.com/%22%5D; QQ=")////添加sessionId header
//                .build();

//        //打印url和参数
//        if(LogUtil.FLAG){
//            LogUtil.i("HeaderInterceptor-url:" + newRequest.url());
//            StringBuilder sb = new StringBuilder();
//            if (newRequest.body() instanceof FormBody) {
//                FormBody body = (FormBody) newRequest.body();
//                for (int i = 0; i < body.size(); i++) {
//                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
//                }
//                sb.delete(sb.length() - 1, sb.length());
//                LogUtil.i("| RequestParams:{" + sb.toString() + "}");
//            }
//        }

        return chain.proceed(newRequestBuild.build());
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
