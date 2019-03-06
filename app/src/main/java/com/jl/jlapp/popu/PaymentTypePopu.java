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
 * Created by fyf on 2018/1/16.
 */

public class PaymentTypePopu extends PopupWindow {

    private TextView tvKnow;
    private View view;

    public PaymentTypePopu(Context context) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.payment_type_popu, null);
        tvKnow = view.findViewById(R.id.tv_know);

        tvKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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
        ColorDrawable cd=new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(cd);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                CardView cardView=view.findViewById(R.id.layout);
                int top=cardView.getTop();
                int left=cardView.getLeft();

                int height=cardView.getHeight();
                int width=cardView.getWidth();

                int y=(int) event.getY();
                int x=(int) event.getX();

                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (y<top||y>(top+height)||x<left||x>(left+width)) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }


}
