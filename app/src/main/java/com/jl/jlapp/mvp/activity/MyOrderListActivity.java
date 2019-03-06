package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.mvp.fragment.MyOrderListFragment;
import com.jl.jlapp.mvp.fragment.NotFoundOrderListFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyOrderListActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener ,View.OnClickListener{

    @BindView(R.id.my_order_state_radiogroup)
    RadioGroup myOrderStateRadiogroup;
    @BindView(R.id.my_order_state_all)
    RadioButton rbAll;
    @BindView(R.id.my_order_state_for_the_payment)
    RadioButton rbForThePayment;
    @BindView(R.id.my_order_state_for_the_goods)
    RadioButton rbForTheGoods;
    @BindView(R.id.my_order_state_has_been_completed)
    RadioButton rbHasBeenBompleted;
    @BindView(R.id.my_order_state_in_the_after_sale)
    RadioButton rbInTheAfterSale;
    @BindView(R.id.message)
    ImageView message;

    ArrayList<Order> orderList;
    //加载碎片
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    /*碎片*/
    Fragment myOrderListFragment, notFoundOrderListFragment;

    int userId = 0;

    public int orderState = -1;//默认-1为查看全部  0：待支付 2：待收货 3：已完成 6：售后中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        //控制注解
        ButterKnife.bind(this);

        Intent intent=getIntent();
        orderState=intent.getIntExtra("orderState",-1);
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);

        //给控件添加数据
        setDateToControl();
        setClickListener();
        //根据用户id获取消息未读数
        getMessageNum(userId);

        fragmentManager = getSupportFragmentManager();
        Drawable drawableLift = getResources().getDrawable(
                R.drawable.checkeed_line);
        switch (orderState) {
            case -1:
                rbAll.setChecked(true);
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(-1);
                break;
            case 0:
                rbForThePayment.setChecked(true);
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(0);
                break;
            case 2:
                rbForTheGoods.setChecked(true);
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(2);
                break;
            case 3:
                rbHasBeenBompleted.setChecked(true);
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(3);
                break;
            case 6:
                rbInTheAfterSale.setChecked(true);
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                replaceFragmentByOrderState(6);
                break;

        }

    }

    public void replaceFragmentByOrderState(int orderState) {
        this.orderState=orderState;
        transaction = fragmentManager.beginTransaction();
        myOrderListFragment = new MyOrderListFragment();
        transaction.replace(R.id.my_order_list_fragment, myOrderListFragment);
        transaction.commit();

    }

    public void setDateToControl() {
        rbAll.setChecked(true);
    }

    public void setClickListener() {
        myOrderStateRadiogroup.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.order_list_return,R.id.message})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.order_list_return:
                finish();
                break;
            case R.id.message:
                //Toast.makeText(this,"正在开发中，敬请期待...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MessageCenterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Drawable drawableLift = getResources().getDrawable(
                R.drawable.checkeed_line);
        switch (checkedId) {
            case R.id.my_order_state_all:
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(-1);
                break;
            case R.id.my_order_state_for_the_payment:
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(0);
                break;
            case R.id.my_order_state_for_the_goods:
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(2);
                break;
            case R.id.my_order_state_has_been_completed:
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                replaceFragmentByOrderState(3);
                break;
            case R.id.my_order_state_in_the_after_sale:
                rbAll.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForTheGoods.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbForThePayment.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbHasBeenBompleted.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                rbInTheAfterSale.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLift);
                replaceFragmentByOrderState(6);
                break;

        }
    }

    public void replaceIfNoData() {
        transaction = fragmentManager.beginTransaction();
        if (notFoundOrderListFragment == null) {
            notFoundOrderListFragment = new NotFoundOrderListFragment();
        }
        transaction.replace(R.id.my_order_list_fragment, notFoundOrderListFragment);
        transaction.commit();
    }

    //根据用户id获取消息未读数
    public void getMessageNum(Integer userId) {
        Net.get().getMessageNum(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageNoReadNumModel -> {
                    if (messageNoReadNumModel.getCode() == 200) {
                        if (messageNoReadNumModel.getResultData().getActivityNum() > 0 || messageNoReadNumModel.getResultData().getOrderNum() > 0 || messageNoReadNumModel.getResultData().getUserMessageNum() > 0) {
                            message.setImageResource(R.drawable.icon_message_green_many);
                        } else {
                            message.setImageResource(R.drawable.icon_message_green_l);
                        }
                    } else {
                        Toast.makeText(this, messageNoReadNumModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
}
