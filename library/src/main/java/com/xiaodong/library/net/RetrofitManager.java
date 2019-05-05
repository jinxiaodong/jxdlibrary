package com.xiaodong.library.net;

import com.google.gson.Gson;
import com.xiaodong.library.config.HttpConfig;
import com.xiaodong.library.net.Interceptor.CommonRequestInterceptor;
import com.xiaodong.library.net.Interceptor.HttpLoggingInterceptor;
import com.xiaodong.library.net.http.HttpsUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaodong.jin on 2019/5/5 .
 * description：
 */
public class RetrofitManager {
    private static Retrofit mRetrofit;
    private static OkHttpClient.Builder okhttpClientBuilder;
    private static Gson gson;

    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            OkHttpClient okHttpClient = buildOkHttpClient(okhttpClientBuilder);
            mRetrofit = buildRetrofit(okHttpClient);
        }
        return mRetrofit;
    }

    private static OkHttpClient buildOkHttpClient(OkHttpClient.Builder builder) {
        try {
            if (builder == null) {
                builder = new OkHttpClient.Builder();
            }
            //如果不是在正式包，添加拦截 打印响应json
            builder.addInterceptor(new CommonRequestInterceptor());
//            if (HttpConfig.isDebug) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(HttpConfig.HttpLogTAG);
                httpLoggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
                httpLoggingInterceptor.setColorLevel(Level.INFO);
                builder.addNetworkInterceptor(httpLoggingInterceptor);
//            }
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            builder.connectTimeout(HttpConfig.connectTimeout, TimeUnit.SECONDS);
            builder.readTimeout(HttpConfig.readTimeout, TimeUnit.SECONDS);
            builder.writeTimeout(HttpConfig.writeTimeout, TimeUnit.SECONDS);
            builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
            //设置缓存
            File httpCacheDirectory = new File(HttpConfig.URL_CACHE,"lyfcache");
            if(!httpCacheDirectory.exists()){
                httpCacheDirectory.mkdirs();
            }
            builder.cache(new Cache(httpCacheDirectory, HttpConfig.MAX_MEMORY_SIZE));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return builder.build();
        }
    }

    private static Retrofit buildRetrofit(OkHttpClient okHttpClient) {
        return mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static Gson buildGson() {
        return null;
    }
}
