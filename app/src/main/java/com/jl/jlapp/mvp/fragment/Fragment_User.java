package com.jl.jlapp.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.OrderListModel;
import com.jl.jlapp.mvp.activity.AfterSaleApplyActivity;
import com.jl.jlapp.mvp.activity.CenterActivity;
import com.jl.jlapp.mvp.activity.CouponCenterActivity;
import com.jl.jlapp.mvp.activity.CustomerServiceActivity;
import com.jl.jlapp.mvp.activity.InvoiceActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.MessageCenterActivity;
import com.jl.jlapp.mvp.activity.MyAddressActivity;
import com.jl.jlapp.mvp.activity.MyOrderListActivity;
import com.jl.jlapp.mvp.activity.SettingsActivity;
import com.jl.jlapp.mvp.activity.UserCouponActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-17.
 */

public class Fragment_User extends Fragment implements View.OnClickListener {
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.message_icon)
    ImageView message_icon;

    @BindView(R.id.my_address)
    ImageView my_address;
    @BindView(R.id.invoice)
    ImageView invoice;
    @BindView(R.id.coupon)
    ImageView coupon;
    @BindView(R.id.server_and_help)
    ImageView server_and_help;
    @BindView(R.id.my_setting)
    ImageView my_setting;
    @BindView(R.id.coupon_center)
    ImageView coupon_center;

    @BindView(R.id.wait_pay)
    LinearLayout wait_pay;
    @BindView(R.id.wait_receive)
    LinearLayout wait_receive;
    @BindView(R.id.wait_evqaluate)
    LinearLayout wait_evqaluate;
    @BindView(R.id.after_sale)
    LinearLayout after_sale_img;
    @BindView(R.id.all_order)
    LinearLayout all_order;
    @BindView(R.id.wait_pay_num)
    TextView waitPayNumTv;
    @BindView(R.id.wait_receive_num)
    TextView waitReceiveNumTv;
    @BindView(R.id.wait_evqaluate_num)
    TextView waitEvqaluateNumTv;
    @BindView(R.id.after_sale_num)
    TextView afterSaleNumTv;


    int userId = -1;
    String userPicUrl = "";
    String userPhone = "";
    String userName = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int waitPayNum = 0;//待支付订单的数量
    int waitReceiveNum = 0;//待收货订单的数量
    int waitEvqaluateNum = 0;//待评价订单的数量
    int afterSaleNum = 0;//售后订单的数量


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, null, false);

        //控制注解
        ButterKnife.bind(this, view);
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();//获取编辑器
        }
        //        sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        //        editor = sharedPreferences.edit();//获取编辑器
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        userPicUrl = sharedPreferences.getString(AppFinal.USERPICURL, "");
        userPhone = sharedPreferences.getString(AppFinal.USERPHONE, "");
        userName = sharedPreferences.getString(AppFinal.USERNAME, "");

        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();//获取编辑器
        }

        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        userPicUrl = sharedPreferences.getString(AppFinal.USERPICURL, "");
        userPhone = sharedPreferences.getString(AppFinal.USERPHONE, "");
        userName = sharedPreferences.getString(AppFinal.USERNAME, "");


        if (userId != -1) {//用户已登录
            //logout.setVisibility(View.VISIBLE);
            login.setText(userName);
            if (userPicUrl == "") {
                Glide
                        .with(getActivity())
                        .load(R.drawable.img_person_defult)
                        .into(user_img);
            } else {
                Tools.setCircleIcon(AppFinal.BASEURL + userPicUrl, user_img);

            }

            //判断是否有未读的消息
            getMessageNum(userId);
            getOrderList(userId);

        } else {

            // logout.setVisibility(View.GONE);
            login.setText("登录/注册");
            login.setClickable(true);
            Glide
                    .with(getActivity())
                    .load(R.drawable.img_person_defult)
                    .into(user_img);
            message_icon.setImageResource(R.drawable.icon_message);
            //设置订单数量控件隐藏
            afterSaleNum = 0;
            afterSaleNumTv.setVisibility(View.GONE);
            waitPayNum = 0;
            waitPayNumTv.setVisibility(View.GONE);
            waitReceiveNum = 0;
            waitReceiveNumTv.setVisibility(View.GONE);
            waitEvqaluateNum = 0;
            waitEvqaluateNumTv.setVisibility(View.GONE);
        }
    }

    public void init() {
        setOnClickEvent();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.user_img:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), SettingsActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.login:
                if (userId > 0) {//用户已登录
                    intent = new Intent(getContext(), SettingsActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.logout:
                if (sharedPreferences == null) {
                    sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();//获取编辑器
                }
                editor.clear();
                editor.commit();
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.message_icon:
                //Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent3 = new Intent(getActivity(), MessageCenterActivity.class);
                    startActivity(intent3);
                }
                break;
            case R.id.my_address:
                //Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(getActivity(), MyAddressActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.invoice:
                //                Log.d("aaaaaaaaaaaaaaaaa", "user: "+userId);
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), InvoiceActivity.class);
                    startActivity(intent);
                }
                //                Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.coupon:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent2 = new Intent(getContext(), UserCouponActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.server_and_help:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent2 = new Intent(getContext(), CustomerServiceActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.my_setting:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), SettingsActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.coupon_center:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(getActivity(), CouponCenterActivity.class);
                    startActivity(intent1);
                }
                break;


            //默认-1为查看全部  0：待支付 2：待收货 3：已完成 6：售后中
            case R.id.wait_pay:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), MyOrderListActivity.class);
                    intent.putExtra("orderState", 0);
                    startActivity(intent);
                }

                break;
            case R.id.wait_receive:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), MyOrderListActivity.class);
                    intent.putExtra("orderState", 2);
                    startActivity(intent);
                }

                break;
            case R.id.wait_evqaluate:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), CenterActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.after_sale:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), MyOrderListActivity.class);
                    intent.putExtra("orderState", 6);
                    startActivity(intent);
                }

                break;
            case R.id.all_order:
                if (userId == -1) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), MyOrderListActivity.class);
                    startActivity(intent);
                }

                break;

        }
    }

    public void setOnClickEvent() {
        user_img.setOnClickListener(this);
        login.setOnClickListener(this);//登录
        logout.setOnClickListener(this);//注销
        message_icon.setOnClickListener(this);


        my_address.setOnClickListener(this);
        invoice.setOnClickListener(this);
        coupon.setOnClickListener(this);
        server_and_help.setOnClickListener(this);
        my_setting.setOnClickListener(this);
        coupon_center.setOnClickListener(this);


        wait_pay.setOnClickListener(this);
        wait_receive.setOnClickListener(this);
        wait_evqaluate.setOnClickListener(this);
        after_sale_img.setOnClickListener(this);
        all_order.setOnClickListener(this);//全部订单
    }

    //根据用户id获取消息未读数
    public void getMessageNum(Integer userId) {
        Net.get().getMessageNum(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageNoReadNumModel -> {
                    if (messageNoReadNumModel.getCode() == 200) {
                        if (messageNoReadNumModel.getResultData().getActivityNum() > 0 || messageNoReadNumModel.getResultData().getOrderNum() > 0 || messageNoReadNumModel.getResultData().getUserMessageNum() > 0) {
                            message_icon.setImageResource(R.drawable.icon_message_many);
                        } else {
                            message_icon.setImageResource(R.drawable.icon_message);
                        }
                    } else {
                        Toast.makeText(getContext(), messageNoReadNumModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //获取用户的订单列表
    public void getOrderList(Integer userId) {
        Net.get().getOrderList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderListModel -> {
                    if (orderListModel.getCode() == 200) {
                        if (orderListModel.getResultData().size() > 0) {
                            afterSaleNum = 0;
                            waitPayNum = 0;
                            waitReceiveNum = 0;
                            waitEvqaluateNum = 0;
                            Log.d("aaaaaaaaaaaa","data.size:"+orderListModel.getResultData().size());
                            getShouldShowDataListByOrderState(orderListModel.getResultData());

                        } else {
                            afterSaleNum = 0;
                            waitPayNum = 0;
                            waitReceiveNum = 0;
                            waitEvqaluateNum = 0;
                        }

                    } else {
                        Toast.makeText(getContext(), orderListModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //从结果集中获取对应状态列表的数量
    public void getShouldShowDataListByOrderState(List<OrderListModel.ResultDataBean> orderListDataBeans) {
        Log.d("aaaaaaaaaaaa","orderListDataBeans.size:"+orderListDataBeans.size());
        for (int i = 0; i < orderListDataBeans.size(); i++) {
            Log.d("aaaaaaaaaaaa","state:"+orderListDataBeans.get(i).getOrderState());
            //售后
            if (orderListDataBeans.get(i).getOrderState() == 6) {
                afterSaleNum++;
            }
            //待支付
            else if (orderListDataBeans.get(i).getOrderState() == 0) {
                waitPayNum++;
            }
            //待收货
            else if (orderListDataBeans.get(i).getOrderState() == 2) {
                waitReceiveNum++;
            }
            //待评价
            else if (orderListDataBeans.get(i).getOrderState() == 3 || orderListDataBeans.get(i).getOrderState() == 8 || orderListDataBeans.get(i).getOrderState() == 10) {
                if (orderListDataBeans.get(i).getIsHasEvaluation() == 0) {
                    waitEvqaluateNum++;
                }
            }

        }

        Log.d("aaaaaaaaaaaa","afterSaleNum:"+afterSaleNum+" waitPayNum:"+waitPayNum+" waitReceiveNum:"+waitReceiveNum+" waitEvqaluateNum:"+waitEvqaluateNum);
        //售后
        if(afterSaleNum>0){
            afterSaleNumTv.setVisibility(View.VISIBLE);
            afterSaleNumTv.setText(afterSaleNum+"");
        }
        else{
            afterSaleNumTv.setVisibility(View.GONE);
        }

        //待支付
        if(waitPayNum>0){
            waitPayNumTv.setVisibility(View.VISIBLE);
            waitPayNumTv.setText(waitPayNum+"");
        }
        else{
            waitPayNumTv.setVisibility(View.GONE);
        }

        //待收货
        if(waitReceiveNum>0){
            waitReceiveNumTv.setVisibility(View.VISIBLE);
            waitReceiveNumTv.setText(waitReceiveNum+"");
        }
        else{
            waitReceiveNumTv.setVisibility(View.GONE);
        }

        //待评价
        if(waitEvqaluateNum>0){
            waitEvqaluateNumTv.setVisibility(View.VISIBLE);
            waitEvqaluateNumTv.setText(waitEvqaluateNum+"");
        }
        else{
            waitEvqaluateNumTv.setVisibility(View.GONE);
        }
    }

}
