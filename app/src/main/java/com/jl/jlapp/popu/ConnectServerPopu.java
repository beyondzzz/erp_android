package com.jl.jlapp.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jl.jlapp.R;


/**
 * Created by 柳亚婷 on 2018/1/19.
 */

public class ConnectServerPopu extends PopupWindow {

    private TextView tvKnow;
    private View view;
    CardView cardView;
    TextView cancelBtn, okBtn;

    public ConnectServerPopu(Context context, View.OnClickListener itemclick) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.connect_server_popu, null);
        cardView = view.findViewById(R.id.layout);
        cancelBtn = view.findViewById(R.id.clear_history_bottom_cancel_btn);
        cancelBtn.setVisibility(View.VISIBLE);
        okBtn = view.findViewById(R.id.clear_history_bottom_ok_btn);


        
        okBtn.setOnClickListener(itemclick);

        //tvKnow = view.findViewById(R.id.tv_know);

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable cd = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(cd);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {

                //获取控件距离手机顶部的高度和距离左边的距离
                int top = cardView.getTop();
                int left = cardView.getLeft();
                //获取控件的高度和宽度
                int width = cardView.getWidth();
                int height = cardView.getHeight();
                //获取手指触摸屏幕的坐标
                int touchY = (int) event.getY();
                int touchX = (int) event.getX();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (touchY < top || touchX < left || touchY > (top + height) || touchX > (left + width)) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }


}
