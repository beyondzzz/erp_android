package com.jl.jlapp.popu;

import android.content.Context;
import android.content.Intent;
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
import com.jl.jlapp.adapter.ChooseSendToAddressAdapter;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.mvp.activity.MyAddressActivity;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 柳亚婷 on 2018/1/16.
 */

public class ChooseSendToAddressPopu extends PopupWindow {

    private TextView shadowPart;
    private View view;
    ImageView closeBtn;
    ChooseSendToAddressAdapter chooseSendToAddressAdapter;
    RecyclerView chooseSendToAddressRecyclerView;
    RelativeLayout chooseAddressPopup;
    List<String> address;
    TextView chooseAddressBottomBtn;
    String saveChooseAddress="";//保存选择的地址
    private setOnClickBottomOKBtn setOnClickBottomOKBtn;
    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setSetOnClickBottomOKBtn(ChooseSendToAddressPopu.setOnClickBottomOKBtn setOnClickBottomOKBtn) {
        this.setOnClickBottomOKBtn = setOnClickBottomOKBtn;
    }

    public ChooseSendToAddressPopu(Context context,String defaultChooseAddress,List<ShipAddressModel.ResultDataBean> shipAddressResultDataBeans) {
        super(context);
        saveChooseAddress=defaultChooseAddress;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.choose_send_to_address_pop, null);
        shadowPart = view.findViewById(R.id.shadow_part);
        chooseSendToAddressRecyclerView=view.findViewById(R.id.choose_send_to_address_recycler_view);
        chooseAddressPopup=view.findViewById(R.id.choose_address_popup);
        chooseAddressBottomBtn=view.findViewById(R.id.choose_address_bottom_btn);
        closeBtn=view.findViewById(R.id.close_btn);
        //getData();

        chooseSendToAddressRecyclerView.setLayoutManager(new CustomLanearLayoutManager(context,false));
        chooseSendToAddressAdapter=new ChooseSendToAddressAdapter(R.layout.choose_send_to_address_item,shipAddressResultDataBeans);
        chooseSendToAddressAdapter.setChooseAddress(saveChooseAddress);
        chooseSendToAddressRecyclerView.setAdapter(chooseSendToAddressAdapter);

        chooseSendToAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView chooseAddressLocation=view.findViewById(R.id.choose_address_location);
                if ("".equals(saveChooseAddress)||!saveChooseAddress.equals(chooseAddressLocation.getText().toString().trim())){
                    saveChooseAddress=chooseAddressLocation.getText().toString().trim();
                    chooseSendToAddressAdapter.setChooseAddress(saveChooseAddress);
                    chooseSendToAddressAdapter.notifyDataSetChanged();
                }
                if (onClickItem!=null) {
                    onClickItem.onClickItem(saveChooseAddress);
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

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {

                //获取控件距离手机顶部的高度和距离左边的距离
                int top=chooseAddressPopup.getTop();

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

        chooseAddressBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnClickBottomOKBtn!=null){
                    setOnClickBottomOKBtn.onClickBottomOkBtn();
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

   /* public void getData(){
        address=new ArrayList<>();
        address.add("北京市海淀区三环到四环之间学院路35号世宁大厦15层");
        address.add("北京市海淀区三环到四环之间");
        address.add("北京市学院路35号世宁大厦15");
    }*/

    public interface OnClickItem{
        void onClickItem(String address);
    }

    public interface setOnClickBottomOKBtn{
        void onClickBottomOkBtn();
    }

}
