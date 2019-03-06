package com.jl.jlapp.popu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.ChoosGoodsActivityAdapter;
import com.jl.jlapp.adapter.ChooseSendToAddressAdapter;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import java.util.List;


/**
 * Created by 柳亚婷 on 2018/1/16.
 */

public class ChooseGoodsActivityPopu extends PopupWindow {

    private TextView shadowPart;
    private View view;
    ChoosGoodsActivityAdapter choosGoodsActivityAdapter;
    RecyclerView chooseGoodsActivityRecycler_view;
    RelativeLayout chooseGoodsActivityPopup;
    ImageView closeBtn;

    public ChooseGoodsActivityPopu(Context context, List<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX> goodsActivitysBeanXES) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.choose_goods_activity_pop, null);
        closeBtn=view.findViewById(R.id.close_btn);
        shadowPart = view.findViewById(R.id.shadow_part);
        chooseGoodsActivityRecycler_view=view.findViewById(R.id.choose_goods_activity_recycler_view);
        chooseGoodsActivityPopup=view.findViewById(R.id.choose_goods_activity_popup);


        chooseGoodsActivityRecycler_view.setLayoutManager(new CustomLanearLayoutManager(context,false));
        choosGoodsActivityAdapter=new ChoosGoodsActivityAdapter(R.layout.choose_goods_activity_item,goodsActivitysBeanXES);

        chooseGoodsActivityRecycler_view.setAdapter(choosGoodsActivityAdapter);


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

        closeBtn.setOnClickListener(new View.OnClickListener() {
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
                int top=chooseGoodsActivityPopup.getTop();

                //获取手指触摸屏幕的坐标
                int touchY=(int) event.getY();

                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (touchY<top) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

}
