package com.jl.jlapp.nets;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jl.jlapp.BuildConfig;
import com.jl.jlapp.mvp.base.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fyf on 2017/10/27.
 */

public class Net {

    private static Net instance;
    private Retrofit retrofit;

    public Net() {
        if (retrofit == null) {
            CommonInterceptor log = new CommonInterceptor();
            if (BuildConfig.DEBUG) {
                log.setLevel(CommonInterceptor.Level.BODY);
            } else {
                log.setLevel(CommonInterceptor.Level.NONE);
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppFinal.BASEURL)
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(log)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }
    }

    public Net(String baseUrl) {
        if (retrofit == null) {
            CommonInterceptor log = new CommonInterceptor();
            if (BuildConfig.DEBUG) {
                log.setLevel(CommonInterceptor.Level.BODY);
            } else {
                log.setLevel(CommonInterceptor.Level.NONE);
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .addInterceptor(log)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }
    }

    public static Api get() {
        if (instance == null) {
            synchronized (Net.class) {
                if (instance == null) {
                    instance = new Net();
                }
            }
        }

        return instance.service();
    }

    public static Api get(String url) {
        Log.d("aaaaaaa","Net有参数baseurl："+url);
        Net instance = null;
        synchronized (Net.class) {
            instance = new Net(url);
        }
        return instance.service();
    }


    public Api service() {
        return retrofit.create(Api.class);
    }

    private static final long MAX_CACHE_SIZE = 10 * 1024 * 1024;
    private static final int TIMEOUT = 30;
    private static final OkHttpClient OK_HTTP_CLIENT;
    private static final GsonBuilder GSON_BUILDER;

    static {
        GSON_BUILDER = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting();
    }

    public static Gson getGson() {
        return GSON_BUILDER.create();
    }

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(new Cache(new File(MyApplication.getSmartCacheDir(MyApplication.getInstance().getApplicationContext()), "retrofit"), MAX_CACHE_SIZE))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }


        OK_HTTP_CLIENT = builder.build();
    }
}
