package com.jl.jlapp.mvp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.AuthResult;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.eneity.PayResult;
import com.jl.jlapp.eneity.WXOrderInfo;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.Config;
import com.jl.jlapp.utils.SuperTextView;
import com.jl.jlapp.utils.Tools;
import com.jl.jlapp.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;

public class PayOrderActivity extends Activity implements View.OnClickListener {

    //加载框
    private ProgressDialog progressDialog;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.stv_wechat_pay)
    SuperTextView stv_wechat_pay;
    @BindView(R.id.stv_alipay)
    SuperTextView stv_alipay;
    @BindView(R.id.stv_union_pay)
    SuperTextView stv_union_pay;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.timer_pay)
    TextView timer_view;
    @BindView(R.id.pay_type_choose)
    RelativeLayout payTypeChoose;
    @BindView(R.id.pay_title)
    TextView payTitle;
    @BindView(R.id.out_of_time_tips)
    TextView outOfTimeTips;
    @BindView(R.id.overtime_pay_title)
    TextView overtimePayTitle;
    double payMoney = -1;
    int orderId = -1;
    String orderNo = "";
    int postagePayType = -1;
    int fromDetailPage = -1;

    private long countdownTime;//倒计时的总时间(单位:毫秒)
    private String timefromServer;//从服务器获取的订单生成时间
    private long chaoshitime;//从服务器获取订单有效时长(单位:毫秒)

    int userId = -1;//获取App中存储的用户id
    boolean isNotOvertime = true;//判断是否超时
    private static final int SDK_PAY_FLAG = 1;
    ClearHistorySearhPopu clearHistorySearhPopu;

    private IWXAPI api; //微信支付

    private boolean isChoosePayMode=false;//是否已点击选择付款方式  不让多次点击

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        Tools.finishAll();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);

        Intent intent = getIntent();
        payMoney = intent.getDoubleExtra("payMoney", -1);
        orderId = intent.getIntExtra("orderId", -1);
        orderNo = intent.getStringExtra("orderNo");
        postagePayType = intent.getIntExtra("postagePayType", -1);
        fromDetailPage = intent.getIntExtra("fromDetailPage", -1);//等于1说明从订单详情页跳转而来
        if (payMoney != -1 && orderId != -1 && orderNo != null && orderNo != "" && postagePayType != -1) {
            getOrderMsg();
            //修改页面展示
            tv_money.setText(payMoney + "");
            if (postagePayType == 1) {
                tips.setVisibility(View.VISIBLE);
            } else {
                tips.setVisibility(View.GONE);
            }
            setClickEvent();
        } else {
            Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(WXPayEntryActivity.resultCode==-1){
            isChoosePayMode=false;
            Log.d("aaaWXPayEntryActivity","-1");
           /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_tip);
            builder.setMessage("支付失败");
            builder.show();*/
            Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
            WXPayEntryActivity.resultCode=1;
        }
        else if(WXPayEntryActivity.resultCode==-2){
            isChoosePayMode=false;
            Log.d("aaaWXPayEntryActivity","-2");
           /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("您已经取消了支付");
            builder.show();
            AlertDialog alertDialog = builder.show();
            Window window = alertDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 0.5f;    // 设置透明度为0.5
            window.setAttributes(lp);*/
            Toast.makeText(this,"您已经取消了支付",Toast.LENGTH_SHORT).show();
            WXPayEntryActivity.resultCode=1;
        }
    }

    //type 1 支付宝  2  微信
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stv_wechat_pay:
                if(!isChoosePayMode){
                    isChoosePayMode=true;
                    Log.d("wxOrderInfo","我点击了微信");
                    if(payMoney<=0){
                        Tools.addActivity(this);
                        updateSatetIsOne(orderNo,payMoney,2);
                    }
                    else{
                        pay(2);//微信
                    }
                }else{
                    Toast.makeText(PayOrderActivity.this,"页面跳转中", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.stv_alipay:
                if(!isChoosePayMode){
                    isChoosePayMode=true;
                    if(payMoney<=0){
                        Tools.addActivity(this);
                        updateSatetIsOne(orderNo,payMoney,1);
                    }
                    else{
                        pay(1);//支付宝
                    }
                }
                else{
                    Toast.makeText(PayOrderActivity.this,"页面跳转中", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.stv_union_pay:
                if(!isChoosePayMode) {
                    isChoosePayMode = true;
                    if(payMoney<=0){
                        Tools.addActivity(this);
                        updateSatetIsOne(orderNo,payMoney,3);
                    }
                    else{
                        pay(3);//银联
                    }
                } else{
                    Toast.makeText(PayOrderActivity.this,"页面跳转中", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.back:
                if (isNotOvertime) {
                    clearHistorySearhPopu = new ClearHistorySearhPopu(this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (fromDetailPage != 1) {
                                Intent intent = new Intent(PayOrderActivity.this, OrderDetailActivity.class);
                                intent.putExtra("orderId", orderId);
                                startActivity(intent);

                            }
                            clearHistorySearhPopu.dismiss();
                            finish();
                        }
                    }, 12);
                    clearHistorySearhPopu.showAtLocation(findViewById(R.id.choose_pay_type_page), Gravity.CENTER, 0, 0);
                } else {
                    finish();
                }

                break;
        }
    }

    //监听手机屏幕上的按键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (isNotOvertime) {
                clearHistorySearhPopu = new ClearHistorySearhPopu(this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fromDetailPage != 1) {
                            Intent intent = new Intent(PayOrderActivity.this, OrderDetailActivity.class);
                            intent.putExtra("orderId", orderId);
                            startActivity(intent);

                        }
                        clearHistorySearhPopu.dismiss();
                        finish();
                    }
                }, 12);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.choose_pay_type_page), Gravity.CENTER, 0, 0);
                return true;
            } else {
                finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void setClickEvent() {
        stv_wechat_pay.setOnClickListener(this);
        stv_alipay.setOnClickListener(this);
        stv_union_pay.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    //type 1 支付宝  2  微信
    private void pay(int type) {
        Tools.addActivity(this);
        switch (type) {
            case 1: {
                Net.get().getOrderInfo(orderId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(authTask1 -> {
                            Runnable authRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(PayOrderActivity.this);
                                    Map<String, String> result = alipay.payV2(authTask1.getResultData(), true);
                                    Log.i("msp", result.toString());
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread authThread = new Thread(authRunnable);
                            authThread.start();
                        }, throwable -> {
                            Log.d("error:--A", "asdasdasd");
                        });
            }
            break;
            case 2:
                Log.d("wxOrderInfo","进来了");
                Net.get().getWXOrderInfo(orderId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wxOrderInfo -> {
                            if(wxOrderInfo.getCode()==200){
                                Log.d("wxOrderInfo", wxOrderInfo.getResultData().toString());
                                sendPayRequest(wxOrderInfo.getResultData());
                            }
                            else{
                                isChoosePayMode = false;
                                Log.d("wxOrderInf", "error");
                            }

                        }, throwable -> {
                            isChoosePayMode = false;
                            Log.d("wxOrderInf", "throwable");
                        });
                break;
            default: {

            }
        }


    }

    /*微信支付*/
    /**调用微信支付*/
    public void sendPayRequest(WXOrderInfo.ResultDataBean wxOrderInfo) {
        Log.d("wxOrderInf", "开始调用");
        PayReq req = new PayReq();
        Log.d("aaaaaaaaa","Appid:"+wxOrderInfo.getAppid());
        req.appId = wxOrderInfo.getAppid();
        req.partnerId = wxOrderInfo.getPartnerid();
        //预支付订单
        req.prepayId = wxOrderInfo.getPrepayid();
        req.nonceStr = wxOrderInfo.getNoncestr();
        req.timeStamp = wxOrderInfo.getTimestamp()+"";
        req.packageValue = wxOrderInfo.getPackageX();
        req.sign = wxOrderInfo.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        //3.调用微信支付sdk支付方法
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, wxOrderInfo.getAppid());
        //将应用的appid注册到微信
        api.registerApp(wxOrderInfo.getAppid());
        WXPayEntryActivity.orderId=orderId;
        WXPayEntryActivity.orderNo=orderNo;
        api.sendReq(req);

    }


    /*支付宝支付*/
    private void success() {
        Net.get().cancleOrder(orderId, userId, 1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cancelOrderModel -> {
                    Log.d("aaaaaaaaaaaaaaa", "支付订单发送请求并接收到数据了 code1:"+cancelOrderModel.getCode());
                    int code1 = cancelOrderModel.getCode();
                    if (code1 == 200) {
                        Log.d("aaaaaasuccess","200");
                        Toast.makeText(PayOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PayOrderActivity.this, PayResultActivity.class);
                        intent.putExtra("orderNo", orderNo);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("page", 0);
                        startActivity(intent);
                    } else {
                        isChoosePayMode = false;
                        Log.d("aaaaaasuccess","else");
                        Toast.makeText(PayOrderActivity.this, "" + cancelOrderModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    isChoosePayMode = false;
                    Toast.makeText(PayOrderActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Net.get().getOrderState(orderId)//查询是否成功
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(authTask1 -> {
                                    if (authTask1.getCode() == 200) {
                                       // success();
                                        Intent intent = new Intent(PayOrderActivity.this, PayResultActivity.class);
                                        intent.putExtra("orderNo", orderNo);
                                        intent.putExtra("orderId", orderId);
                                        intent.putExtra("page", 0);
                                        startActivity(intent);
                                    }
                                    else{
                                        isChoosePayMode = false;
                                    }
                                }, throwable -> {
                                    isChoosePayMode = false;
                                    Log.d("error:--A", "asdasdasd");
                                });
                        Toast.makeText(PayOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        isChoosePayMode = false;
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /*倒计时*/
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (countdownTime > 1000) {
                isNotOvertime = true;
                countdownTime -= 1000;//倒计时总时间减1
                long second = countdownTime / 1000;
                long hour = second / 3600;//小时
                second = second % 3600;                //剩余秒数
                long minutes = second / 60;            //转换分钟
                second = second % 60;                //剩余秒数
                String hourStr = hour + "";
                if (hour < 10) {
                    hourStr = "0" + hour;
                }
                String min = minutes + "";
                if (minutes < 10) {
                    min = "0" + minutes;
                }
                String sec = second + "";
                if (second < 10) {
                    sec = "0" + second;
                }

                timer_view.setText(hourStr + ":" + min + ":" + sec);
                handler.postDelayed(this, 1000);
            } else {//自动取消订单
                isNotOvertime = false;
                payTitle.setVisibility(View.GONE);
                timer_view.setVisibility(View.GONE);
                outOfTimeTips.setVisibility(View.GONE);
                overtimePayTitle.setVisibility(View.VISIBLE);

                payTypeChoose.setVisibility(View.GONE);

                handler.removeCallbacks(this::run);
            }
            //            SimpleDateFormat minforamt = new SimpleDateFormat("hh:mm:ss");
            //
            //            String hms = minforamt.format(countdownTime);//格式化倒计时的总时间


        }
    };

    //倒计时处理
    private void getTimeDuring() {
        chaoshitime = 4 * 60 * 60 * 1000;//应该从服务器获取 4小时
        if (postagePayType == 0) {
            chaoshitime = 4 * 60 * 60 * 1000;//应该从服务器获取 4小时
        } else if (postagePayType == 1) {
            chaoshitime = 24 * 60 * 60 * 1000;//应该从服务器获取 24小时
        }

        //        timefromServer = "2018-01-19 16:20:00";//应该从服务器获取
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date serverDate = null;
        try {
            serverDate = df.parse(timefromServer);
            long duringTime = new Date().getTime() - serverDate.getTime();//计算当前时间和从服务器获取的订单生成时间的时间差
            countdownTime = chaoshitime - duringTime;//计算倒计时的总时间

        } catch (ParseException e) {
            e.printStackTrace();
        }

        handler.postDelayed(runnable, 1000);


    }

    /*获取订单信息*/
    public void getOrderMsg() {
        Net.get().getOrderDetail(orderId, userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetail -> {
                    Log.d("aaaaaaaaaaaaaaa", "获取数据加载了");
                    int code1 = orderDetail.getCode();
                    if (code1 == 200) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String order_commit_time_str = sdf.format(orderDetail.getResultData().getPlaceOrderTime());
                        Log.d("aaaaaaaa", "timer: " + order_commit_time_str);
                        timefromServer = order_commit_time_str;
                        postagePayType = orderDetail.getResultData().getPostagePayType();
                        getTimeDuring();
                    } else {
                        Toast.makeText(this, "" + orderDetail.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    /*修改订单状态为待发货*/
    public void updateSatetIsOne(String orderNo,double totalFee,int payMode){
        Net.get().updateSatetIsOne(orderNo,totalFee,payMode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                   if(postModel.getCode()==200){
                      //支付成功，跳转至支付成功页面
                       Intent intent = new Intent(PayOrderActivity.this, PayResultActivity.class);
                       intent.putExtra("orderNo", orderNo);
                       intent.putExtra("orderId", orderId);
                       intent.putExtra("page", 0);
                       startActivity(intent);
                   }else {
                       isChoosePayMode = false;
                       Toast.makeText(this, "" + postModel.getMsg(), Toast.LENGTH_SHORT).show();
                   }
                }, throwable -> {
                    isChoosePayMode = false;
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

}
