package com.xiaodong.library.net.Interceptor;

import android.os.Build;

import com.xiaodong.library.GlobalUser;
import com.xiaodong.library.utils.GsonUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by xiaodong.jin on 2019/05/06.
 * 功能描述： Http 请求公共参数,如果需要其他参数，可自行定义新的Interceptor
 */
public class CommonRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String ContentType = null;
        String token = GlobalUser.INSTANCE.getLoginUt();
        Request.Builder header = originalRequest.newBuilder()
                .addHeader("clientInfo", getValueEncoded(initBaseHeader()))
                .header("platformId", "0")//0:andorid 1:ios
                .header("Cookie", "ut=" + token)
                .header("Accept", "application/json, text/plain, */*")
                .addHeader("ut", token);
        Request requestWithHeader = header.build();
        if (originalRequest != null && originalRequest.body() != null && originalRequest.body().contentType() != null) {
            ContentType = originalRequest.body().contentType().toString();
        }
        if ("GET".equals(originalRequest.method())) {
            HttpUrl url = originalRequest.url();
            HttpUrl.Builder builder = url.newBuilder();
            builder.addQueryParameter("companyId", "30");
            builder.addQueryParameter("ut", token);
            builder.addQueryParameter("platformId", "0");
            builder.addQueryParameter("platform", "3");
            url = builder.build();
            Request requestWithGet = requestWithHeader.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(requestWithGet);
        } else if ("POST".equals(originalRequest.method()) && (
                ContentType == null ||
                        ContentType.toLowerCase().contains("x-www-form-urlencoded"))) {
            String postBodyString = bodyToString(originalRequest.body());
            FormBody.Builder builder = new FormBody.Builder();
            if (!postBodyString.contains("platformId")) builder.add("platformId", "0");
            if (!postBodyString.contains("companyId")) builder.add("companyId", "30");
//            if (!postBodyString.contains("platform")) builder.add("platform", "3");
            if (!postBodyString.contains("ut")) builder.add("ut", token);
            RequestBody formBody = builder.build();
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            Request requestWithPost = requestWithHeader.newBuilder()
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                            postBodyString))
                    .header("Content-Length", postBodyString.length() + "")
                    .build();
            return chain.proceed(requestWithPost);
        } else {
            return chain.proceed(requestWithHeader);
        }
    }

    private String initBaseHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("clientVersion", Build.VERSION.RELEASE);
        header.put("clientDeviceType", Build.MANUFACTURER);
        return GsonUtils.toJson(header);
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

    //由于okhttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符,所以这里
    //会首先替换 \n ,然后使用 okhttp 的校验方式,校验不通过的话,就返回 encode 后的字符串
    private static String getValueEncoded(String value) {
        if (value == null) return "null";
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                try {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return newValue;
    }
}
