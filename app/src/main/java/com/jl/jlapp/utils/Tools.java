package com.jl.jlapp.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.mvp.base.MyApplication;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fyf on 2018/1/19.
 */

public class Tools {
    private static List<Activity> list;

    /**
     * 1.0
     * 跳转界面
     *
     * @param aClass
     */
    public static void startActivity(Context context, Class aClass) {
        context.startActivity(new Intent(context, aClass));
    }

    /**
     * 1.1
     * 跳转界面
     *
     * @param activity
     */
    public static void startActivity(Context context, Activity activity) {
        context.startActivity(new Intent(context, activity.getClass()));
    }

    /**
     * 2
     * 添加activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (list == null)
            list = new ArrayList<>();
        if (list.contains(activity)) return;
        list.add(activity);
    }


    /**
     * 3
     * 结束所有activity
     */
    public static void finishAll() {
        if (list == null) return;
        if (list.size() == 0) return;
        for (Activity a : list) {
            a.finish();
        }
    }

    /**
     * 评分条
     *
     * @param rb
     * @param score 0.0-5.0
     */
    public static void initRatingBar(RatingBar rb, String score) {
        if (score == null) {
            rb.setProgress(rb.getMax());
            return;
        }

        if (score == "") {
            rb.setProgress(rb.getMax());
            return;
        }
        Double i = Double.valueOf(score);
        rb.setProgress((int) (i * 20));
    }

    /**
     * 5
     * 手机号加密显示
     *
     * @param phone
     * @return
     * @Description: 将手机号中间4位，显示成*
     * @Author: fyf
     * @Since: 2017年1月6日下午17:04:15
     */
    public static String hidePhone(String phone) {
        if (!StringUtils.isEmpty(phone)) {
            phone = phone.substring(0, phone.length() - (phone.substring(3)).length()) + "****" + phone.substring(7);
        }
        return phone;
    }

    /**
     * 7
     * 防止快速点击
     *
     * @return
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick(int fastTime) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < fastTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * dp转换px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 设置圆形头像
     *
     * @param imgUrl
     * @param iv
     */
    public static void setCircleIcon(String imgUrl, ImageView iv) {
        Log.d("useraaaaaaaaaaa","我执行了设置头像:"+imgUrl);
        Glide.with(MyApplication.getInstance()).asBitmap().apply(RequestOptions.bitmapTransform(new CircleCrop()).
                error(R.drawable.icon_user_img).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.icon_user_img)).load(imgUrl).into(iv).onStart();
    }


    /**
     * @param s 判断是否有空值
     * @return
     */
    public static boolean isNull(String... s) {
        for (String value : s) {
            if ("".equals(value)) return true;
        }
        return false;
    }


    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void setInputMethod(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersion() {
        try {
            PackageManager manager = MyApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取版本名
     *
     * @return当前应用的版本名
     */
    public static String getVersionName() {
        try {
            PackageManager manager = MyApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 四舍五入保留两位
     *
     * @param money
     * @return
     */
    public static String totalMoney(double money) {
        java.math.BigDecimal bigDec = new java.math.BigDecimal(money);
        double total = bigDec.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }

    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 判断是否安装了支付包客户端
     *
     * @return
     */
    public static boolean checkAliPayInstalled() {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(MyApplication.getInstance().getPackageManager());
        return componentName != null;
    }

    /**
     * 好评率
     *
     * @param receiver 好评度
     * @param tvReceiver textview
     */
    public static void setRate(String receiver, TextView tvReceiver) {
        String szReceiver = MyApplication.getInstance().getString(R.string.text_good_rate, receiver);
        SpannableStringBuilder ssb = new SpannableStringBuilder(szReceiver);
        ssb.setSpan(new ForegroundColorSpan(MyApplication.getInstance().getResources().getColor(R.color.light_blue)), ssb.length() - receiver.length(), ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if (!TextUtils.isEmpty(receiver)) {
            tvReceiver.setText(ssb);
        } else {
            tvReceiver.setText(szReceiver);
        }
    }


}
