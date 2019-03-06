package com.jl.jlapp.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jl.jlapp.R;


/**
 * 修改头像的popu
 * Created by fyf on 2017/7/27.
 */

public class PhotoPicPopupWindow extends PopupWindow {

    Button photographButton, photoButton, remove;
    View view;


    public PhotoPicPopupWindow(Context context, View.OnClickListener itemclick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.photo_main_pop, null);
        photographButton = view.findViewById(R.id.photographButton);
        photoButton = view.findViewById(R.id.photoButton);
        remove = view.findViewById(R.id.remove);


        photographButton.setOnClickListener(itemclick);
        photoButton.setOnClickListener(itemclick);
        //取消，销毁页面
        remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();//销毁弹出框
            }
        });

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

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                int height = view.findViewById(R.id.layout).getTop();
                int ll = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
//					if (ll>height) {
                    dismiss();
//					}

                }


                return true;
            }
        });


    }


}
