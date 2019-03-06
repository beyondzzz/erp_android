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
import com.jl.jlapp.adapter.OrderChoosGoodsActivityAdapter;
import com.jl.jlapp.eneity.ActivitysAndCouponsByGoodsMsgModel;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import org.w3c.dom.Text;

import java.util.List;


/**
 * Created by 柳亚婷 on 2018/1/16.
 */

public class OrderChooseGoodsActivityPopu extends PopupWindow {

    private TextView shadowPart;
    private View view;
    OrderChoosGoodsActivityAdapter orderChoosGoodsActivityAdapter;
    RecyclerView chooseGoodsActivityRecycler_view;
    RelativeLayout chooseGoodsActivityPopup;
    ImageView closeBtn;
    int chooseActivityInformationId=0;
    private OnClickActivityItem onClickActivityItem;

    public void setOnClickActivityItem(OnClickActivityItem onClickActivityItem) {
        this.onClickActivityItem = onClickActivityItem;
    }

    public OrderChooseGoodsActivityPopu(Context context, List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.ActivitysBean> activitysBeans, int chooseAvtivityId) {
        super(context);
        chooseActivityInformationId=chooseAvtivityId;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.order_choose_goods_activity_pop, null);
        closeBtn=view.findViewById(R.id.close_btn);
        shadowPart = view.findViewById(R.id.shadow_part);
        chooseGoodsActivityRecycler_view=view.findViewById(R.id.choose_goods_activity_recycler_view);
        chooseGoodsActivityPopup=view.findViewById(R.id.choose_goods_activity_popup);


        chooseGoodsActivityRecycler_view.setLayoutManager(new CustomLanearLayoutManager(context,true));
        orderChoosGoodsActivityAdapter=new OrderChoosGoodsActivityAdapter(R.layout.order_choose_goods_activity_item,activitysBeans);
        orderChoosGoodsActivityAdapter.setChooseId(chooseActivityInformationId);
        chooseGoodsActivityRecycler_view.setAdapter(orderChoosGoodsActivityAdapter);

        orderChoosGoodsActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView activityInformationId=view.findViewById(R.id.activity_information_id);
                chooseActivityInformationId=Integer.parseInt(activityInformationId.getText().toString().trim());
                orderChoosGoodsActivityAdapter.setChooseId(chooseActivityInformationId);
                orderChoosGoodsActivityAdapter.notifyDataSetChanged();
                if(onClickActivityItem!=null){
                    onClickActivityItem.onClickActivityItem(chooseActivityInformationId);
                    dismiss();
                }
            }
        });


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

    public interface OnClickActivityItem{
        void onClickActivityItem(int activityInformationId);
    }

}
