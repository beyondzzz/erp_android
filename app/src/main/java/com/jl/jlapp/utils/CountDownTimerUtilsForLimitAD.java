package com.jl.jlapp.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 景雅倩 on 2018-07-20.
 */

public class CountDownTimerUtilsForLimitAD extends CountDownTimer {
    private TextView mTextView;
    RelativeLayout mRelativeLayout;

    /**
     * @param textView          The TextView
     *
     *
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receiver
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtilsForLimitAD(RelativeLayout relativeLayout, TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.mRelativeLayout = relativeLayout;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        long second = millisUntilFinished/1000;
        long hour = second/3600;//小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        String hourStr = hour+"";
        if(hour<10){
            hourStr = "0"+hour;
        }
        String min = minutes+"";
        if(minutes<10){
            min = "0"+minutes;
        }
        String sec = second+"";
        if(second<10){
            sec = "0"+second;
        }

        mTextView.setText(hourStr+":"+min+":"+sec);
    }

    @Override
    public void onFinish() {
       mRelativeLayout.setVisibility(View.GONE);
    }
}