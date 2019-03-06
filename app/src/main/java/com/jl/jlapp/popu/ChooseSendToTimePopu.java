package com.jl.jlapp.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.ChooseSendToAddressAdapter;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;


/**
 * Created by 柳亚婷 on 2018/1/16.
 */

public class ChooseSendToTimePopu extends PopupWindow implements View.OnClickListener{

    Context context;
    private View view;
    ImageView closeBtn;
    RelativeLayout chooseTimePopup;
    LinearLayout timeLinear1,timeLinear2;
    TextView chooseTimeBottomBtn, sendTime1, sendTime2, sendTime3, sendTime4, sendTime5, sendTime6, sendTime7,presellSendTime,deliveryToName;
    String saveChooseTime = "";//保存选择的地址
    List<TextView> textViews;
    private OnClickBottomOKBtn onClickBottomOKBtn;

    Date date;
    SimpleDateFormat simpleDateFormat;

    public void setSetOnClickBottomOKBtn(ChooseSendToTimePopu.OnClickBottomOKBtn setOnClickBottomOKBtn) {
        this.onClickBottomOKBtn = setOnClickBottomOKBtn;
    }

    public ChooseSendToTimePopu(Context context, String defaultChooseAddress,Integer isPresell,String presellEndTime) {
        super(context);
        this.context=context;
        saveChooseTime = defaultChooseAddress;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.choose_send_to_time_pop, null);
        deliveryToName=view.findViewById(R.id.delivery_to_name);
        chooseTimePopup = view.findViewById(R.id.choose_time_popup);
        chooseTimeBottomBtn = view.findViewById(R.id.choose_time_bottom_btn);
        timeLinear1=view.findViewById(R.id.time_linear_1);
        timeLinear2=view.findViewById(R.id.time_linear_2);
        presellSendTime=view.findViewById(R.id.presell_send_time);
        closeBtn = view.findViewById(R.id.close_btn);
        sendTime1 = view.findViewById(R.id.send_time_1);
        sendTime2 = view.findViewById(R.id.send_time_2);
        sendTime3 = view.findViewById(R.id.send_time_3);
        sendTime4 = view.findViewById(R.id.send_time_4);
        sendTime5 = view.findViewById(R.id.send_time_5);
        sendTime6 = view.findViewById(R.id.send_time_6);
        sendTime7 = view.findViewById(R.id.send_time_7);
        textViews = new ArrayList<>();
        textViews.add(sendTime1);
        textViews.add(sendTime2);
        textViews.add(sendTime3);
        textViews.add(sendTime4);
        textViews.add(sendTime5);
        textViews.add(sendTime6);
        textViews.add(sendTime7);

        if(isPresell==2){
            timeLinear1.setVisibility(View.VISIBLE);
            timeLinear2.setVisibility(View.VISIBLE);
            presellSendTime.setVisibility(View.GONE);
            for (int i = 0; i < 7; i++) {
                textViews.get(i).setOnClickListener(this);
                textViews.get(i).setText(getDay(i+1));
                if (getDay(i+1).equals(defaultChooseAddress)) {
                    textViews.get(i).setBackgroundResource(R.drawable.send_to_time_green_border);
                    textViews.get(i).setTextColor(context.getResources().getColor(R.color.checkGreenColor));
                } else {
                    textViews.get(i).setBackgroundResource(R.drawable.send_to_time_gray_background);
                    textViews.get(i).setTextColor(context.getResources().getColor(R.color.trans_333333));
                }
            }
        }
        else{
            deliveryToName.setText("送货时间提示");
            timeLinear1.setVisibility(View.GONE);
            timeLinear2.setVisibility(View.GONE);
            presellSendTime.setVisibility(View.VISIBLE);
            presellSendTime.setText("送货时间为活动结束后7日内(结束时间:"+presellEndTime+")");
        }



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

                //获取控件距离手机顶部的高度和距离左边的距离
                int top = chooseTimePopup.getTop();

                //获取手指触摸屏幕的坐标
                int touchY = (int) event.getY();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (touchY < top) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        chooseTimeBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ChooseSendToTimePopuaa","我点击了确定");
                if(isPresell==2){
                    if("请选择".equals(saveChooseTime)){
                        Toast.makeText(context,"请选择配送时间",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (onClickBottomOKBtn != null) {
                            onClickBottomOKBtn.onClickBottomOkBtn(saveChooseTime);

                        }
                    }
                }
                else{
                    dismiss();
                }


            }
        });

        //关闭按钮的点击事件
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Log.d("ChooseSendToTimePopuaa","执行了点击事件");
        for (int i = 0; i < 7; i++) {
            if (view.getId() == textViews.get(i).getId()) {
                textViews.get(i).setBackgroundResource(R.drawable.send_to_time_green_border);
                textViews.get(i).setTextColor(context.getResources().getColor(R.color.checkGreenColor));
                simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                saveChooseTime=simpleDateFormat.format(date.getTime() + 1000 * 60 * 60 * 24 * (i+1));
            } else {
                textViews.get(i).setBackgroundResource(R.drawable.send_to_time_gray_background);
                textViews.get(i).setTextColor(context.getResources().getColor(R.color.trans_333333));
            }
        }
    }

    public interface OnClickBottomOKBtn {
        void onClickBottomOkBtn(String time);
    }

    private String getDay(int day) {
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("M月d日");
        return simpleDateFormat.format(date.getTime() + 1000 * 60 * 60 * 24 * day);
    }

}
