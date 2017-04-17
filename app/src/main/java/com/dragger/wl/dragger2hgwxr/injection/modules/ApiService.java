package com.dragger.wl.dragger2hgwxr.injection.modules;

import com.dragger.wl.dragger2hgwxr.BuildConfig;
import com.dragger.wl.dragger2hgwxr.DemoApplication;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wl
 * @version :
 * @date 2017/4/14
 * @描述
 */

public class ApiService {

    public  static final String BASE_URL="http://www.tngou.net/";

        public static <S> S createService(Class<S> sClass) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(HttpUrl.parse(BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            builder.client(provideOkHttpClient());
            return (S) builder.build().create(sClass);
    }

   private   static   OkHttpClient provideOkHttpClient() {
        Cache cache = new Cache(DemoApplication.get().getCacheDir(), 10240 * 1024);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        builder.addNetworkInterceptor(new CacheInterceptor())
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        return builder.build();
    }

    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //cache for 30 days
                    .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                    .build();
        }
    }
}
