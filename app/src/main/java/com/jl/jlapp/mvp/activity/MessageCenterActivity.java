package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.eneity.MessageNoReadNumModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessageCenterActivity extends Activity {

    @BindView(R.id.service_message_no_read_num)
    TextView serviceMessageNoReadNumTv;
    @BindView(R.id.order_message_no_read_num)
    TextView orderMessageNoReadNumTv;
    @BindView(R.id.activity_message_no_read_num)
    TextView activityMessageNoReadNumTv;
   /* @BindView(R.id.service_last_time)
    TextView serviceLastTimeTv;*/
    @BindView(R.id.order_last_time)
    TextView orderLastTimeTv;
    @BindView(R.id.activity_last_time)
    TextView activityLastTimeTv;
    @BindView(R.id.last_activity_name)
    TextView lastActivityNameTv;
    @BindView(R.id.last_order_no)
    TextView lastOrderNoTv;
    @BindView(R.id.service_point)
    TextView servicePointTv;
    @BindView(R.id.order_no_message)
    TextView orderNoMessage;
    @BindView(R.id.activity_no_message)
    TextView activityNoMessage;
    @BindView(R.id.order_has_message_linear)
    LinearLayout orderHasMessageLinear;

    //加载框
    private ProgressDialog progressDialog;
    int userId = -1;//获取App中存储的用户id

    int serviceNoReadNum = 0;
    int orderMessageNoReadNum = 0;
    int activityMessageNum = 0;

    long serviceLastTime = 0;
    long orderMessageLastTime = 0;
    long activityMessageLastTime = 0;

    String orderMessageLastOrderNo = "";
    String activityMessageLastActivityName = "";
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);

        simpleDateFormat = new SimpleDateFormat("M月d日");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        //开启加载框
        buildProgressDialog();
        if (userId > 0) {
            getMessageNum(userId);
        } else {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick({R.id.icon_return, R.id.message_setting_img, R.id.order_message_rela, R.id.user_service_rela, R.id.activity_message_rela})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_return:
                finish();
                break;
            case R.id.message_setting_img:
                Intent intent = new Intent(MessageCenterActivity.this, MessageSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.order_message_rela:
                //点开到列表时修改所有订单消息为已读
                updateOrderMSGStauts(userId);
                break;
            case R.id.user_service_rela:
                Intent intent3 = new Intent(MessageCenterActivity.this, CustomerServiceActivity.class);
                startActivity(intent3);
                break;
            case R.id.activity_message_rela:
                updateActivityMSGStauts(userId);
                break;
        }
    }

    /**
     * 加载框
     */
    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("加载中");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * @Description: TODO 取消加载框
     */
    public void cancelProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }

    //设置未读消息数
    private void setNoReadMessageNum(){
        serviceMessageNoReadNumTv.setVisibility(View.GONE);
        /*if (serviceNoReadNum > 0) {
            serviceMessageNoReadNumTv.setVisibility(View.VISIBLE);
            serviceMessageNoReadNumTv.setText(serviceNoReadNum + "");
        } else {
            serviceMessageNoReadNumTv.setVisibility(View.GONE);
        }*/
        if (orderMessageNoReadNum > 0) {
            orderMessageNoReadNumTv.setText(orderMessageNoReadNum + "");
            orderMessageNoReadNumTv.setVisibility(View.VISIBLE);
        } else {
            orderMessageNoReadNumTv.setVisibility(View.GONE);
        }
        if (activityMessageNum > 0) {
            activityMessageNoReadNumTv.setText(activityMessageNum + "");
            activityMessageNoReadNumTv.setVisibility(View.VISIBLE);
        } else {
            activityMessageNoReadNumTv.setVisibility(View.GONE);
        }
    }

    //设置页面里各个消息的时间以及最后一条消息的内容
    private void setMessage(MessageNoReadNumModel messageNoReadNumModel){
        Log.d("aaaaaaaaaaaa", messageNoReadNumModel.getResultData().getOrderTime() + "  getOrderTime");
        Log.d("aaaaaaaaaaaa", messageNoReadNumModel.getResultData().getActivityTime() + "  getActivityTime");
//        if (messageNoReadNumModel.getResultData().getUserMessageTime() > 0) {
//            serviceLastTimeTv.setText(simpleDateFormat.format(messageNoReadNumModel.getResultData().getUserMessageTime()));
//            servicePointTv.setText("点击查看您与客服的沟通记录");
//            if(serviceNoReadNum > 0){
//                serviceLastTimeTv.setVisibility(View.VISIBLE);
//            }
//            else{
//                serviceLastTimeTv.setVisibility(View.GONE);
//            }
//        }
//        else{
//            servicePointTv.setText("暂无聊天记录");
//            serviceLastTimeTv.setVisibility(View.GONE);
//        }
        if(messageNoReadNumModel.getResultData().getOrderTime()>0){
            Log.d("aaaaaaaaaaaa", "if");
            orderNoMessage.setVisibility(View.GONE);
            orderHasMessageLinear.setVisibility(View.VISIBLE);
            orderLastTimeTv.setText(simpleDateFormat.format(messageNoReadNumModel.getResultData().getOrderTime()));
            lastOrderNoTv.setText(messageNoReadNumModel.getResultData().getOrderNO());
            orderLastTimeTv.setVisibility(View.VISIBLE);
        }
        else{
            Log.d("aaaaaaaaaaaa", "else");
            orderHasMessageLinear.setVisibility(View.GONE);
            orderNoMessage.setVisibility(View.VISIBLE);
            orderLastTimeTv.setVisibility(View.GONE);
        }
        if(messageNoReadNumModel.getResultData().getActivityTime()>0){
            activityNoMessage.setVisibility(View.GONE);
            lastActivityNameTv.setVisibility(View.VISIBLE);
            lastActivityNameTv.setText(messageNoReadNumModel.getResultData().getActivityName());
            activityLastTimeTv.setText(simpleDateFormat.format(messageNoReadNumModel.getResultData().getActivityTime()));
            activityLastTimeTv.setVisibility(View.VISIBLE);
        }
        else{
            activityNoMessage.setVisibility(View.VISIBLE);
            lastActivityNameTv.setVisibility(View.GONE);
            activityLastTimeTv.setVisibility(View.GONE);
        }
    }

    //根据用户id获取消息未读数
    public void getMessageNum(Integer userId) {
        Net.get().getMessageNum(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageNoReadNumModel -> {
                    cancelProgressDialog();
                    if (messageNoReadNumModel.getCode() == 200) {
                        serviceNoReadNum = messageNoReadNumModel.getResultData().getUserMessageNum();
                        orderMessageNoReadNum = messageNoReadNumModel.getResultData().getOrderNum();
                        activityMessageNum = messageNoReadNumModel.getResultData().getActivityNum();
                        setNoReadMessageNum();
                        setMessage(messageNoReadNumModel);

                    } else {
                        Toast.makeText(this, messageNoReadNumModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //点开到列表时修改所有订单消息为已读
    public void updateOrderMSGStauts(Integer userId) {
        Net.get().updateOrderMSGStauts(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() == 200) {
                        Intent intent2 = new Intent(MessageCenterActivity.this, OrderMessageListActivity.class);
                        startActivity(intent2);
                    } else {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //点开到列表时修改所有活动消息为已读
    public void updateActivityMSGStauts(Integer userId) {
        Net.get().updateActivityMSGStauts(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() == 200) {
                        Intent intent4 = new Intent(MessageCenterActivity.this, ActivityMessageListActivity.class);
                        startActivity(intent4);
                    } else {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
}
