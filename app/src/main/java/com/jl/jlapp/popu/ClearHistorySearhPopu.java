package com.jl.jlapp.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jl.jlapp.R;


/**
 * Created by 柳亚婷 on 2018/1/19.
 */

public class ClearHistorySearhPopu extends PopupWindow {

    private TextView tvKnow;
    private View view;
    CardView cardView;
    TextView cancelBtn, okBtn, popuContent;

    public ClearHistorySearhPopu(Context context, View.OnClickListener itemclick, Integer isFromWhichPage) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.clear_history_search_popu, null);
        cardView = view.findViewById(R.id.layout);
        cancelBtn = view.findViewById(R.id.clear_history_bottom_cancel_btn);
        cancelBtn.setVisibility(View.VISIBLE);
        okBtn = view.findViewById(R.id.clear_history_bottom_ok_btn);
        popuContent = view.findViewById(R.id.popu_content);
        //说明从清空历史搜索记录的页面跳转而来
        if (isFromWhichPage == 0) {
            popuContent.setText(R.string.sure_to_empty_search_history);
        }
        //从购物车页面跳转来
        else if (isFromWhichPage == 1) {
            popuContent.setText(R.string.sure_to_delete);
        }
        //取消订单  未支付
        else if (isFromWhichPage == 2) {
            popuContent.setText(R.string.sure_to_cancel_order);
        }

        //申请售后提交成功
        else if (isFromWhichPage == 3) {
            cancelBtn.setVisibility(View.GONE);
            popuContent.setText(R.string.apply_success);
        }

        //删除订单
        else if (isFromWhichPage == 4) {
            popuContent.setText(R.string.sure_to_delete_order);
        }
        //删除订单
        else if (isFromWhichPage == 5) {
            popuContent.setText("确定收货吗？");
        }
        //清空缓存
        else if (isFromWhichPage == 6) {
            popuContent.setText("确定清空缓存吗？");
        }
        //退出登录
        else if (isFromWhichPage == 7) {
            popuContent.setText("确定退出登录吗？");
        }
        //删除普票信息
        else if (isFromWhichPage == 8) {
            popuContent.setText("确定删除该公司吗？");
        }
        //未保存编辑就退出
        else if (isFromWhichPage == 9) {
            popuContent.setText("您的编辑尚未保存，是否确认离开此页面？");
        }

        //删除增票信息
        else if (isFromWhichPage == 10) {
            popuContent.setText("确定删除该增票资质吗？");
        }
        //清空全部消息
        else if (isFromWhichPage == 11) {
            popuContent.setText("确定要清空全部消息吗？");
        }
        //订单未支付
        else if (isFromWhichPage == 12) {
            popuContent.setText("您的订单尚未支付,是否确认离开此页面？");
        }

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
