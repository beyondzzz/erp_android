package com.jl.jlapp.utils;

import android.content.Context;
import android.support.v4.content.FileProvider;

/**
 * Created by THINK on 2018-03-13.
 */

public class MyFileProvider extends FileProvider {
    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".publish.MyFileProvider";
    }
}
