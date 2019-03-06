package com.jl.jlapp.mvp.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;



/**
 * Created by fyf on 2017/10/19.
 */

public class MyApplication extends Application {

    public static MyApplication app;
    public static final String USERINFO_FILENAME = "userinfo";
    private boolean isDebug = true;


    public static Context getInstance() {
        return app.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;


    }

    public static File getSmartCacheDir(@NonNull Context context) {
        if (SDCardUtils.isSDCardEnable()) {
            return context.getApplicationContext().getExternalCacheDir();
        } else {
            return context.getApplicationContext().getCacheDir();
        }
    }


}
