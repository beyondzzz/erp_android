package com.jl.jlapp.mvp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.OrderDetailGoodsListAdapter;
import com.jl.jlapp.adapter.TracingListAdapter;
import com.jl.jlapp.eneity.Order;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.eneity.OrderStateDetail;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
import com.jl.jlapp.utils.Tools;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-18.
 */

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.timer)
    TextView timer_view;
    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.order_tracing_detail)
    ImageView order_tracing_detail;
    @BindView(R.id.my_order_combined_goods_num)
    TextView my_order_combined_goods_num;
    @BindView(R.id.my_order_combined_num)
    TextView my_order_combined_num;
    @BindView(R.id.my_order_freight)
    TextView my_order_freight;
    @BindView(R.id.order_no)
    TextView order_no;
    @BindView(R.id.order_commit_time)
    TextView order_commit_time;
    @BindView(R.id.invoice_layout)
    LinearLayout invoice_layout;
    @BindView(R.id.invoice)
    TextView invoice;
    @BindView(R.id.pay_time_layout)
    LinearLayout pay_time_layout;
    @BindView(R.id.pay_type_layout)
    LinearLayout pay_type_layout;
    @BindView(R.id.pay_mode_layout)
    LinearLayout pay_mode_layout;
    @BindView(R.id.pay_time_view)
    TextView pay_time_view;
    @BindView(R.id.pay_type_view)
    TextView pay_type_view;
    @BindView(R.id.pay_mode_view)
    TextView pay_mode_view;
    @BindView(R.id.order_tracing_message)
    TextView order_tracing_message;
    @BindView(R.id.order_tracing_time)
    TextView order_tracing_time;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.icon_state)
    ImageView icon_state;
    @BindView(R.id.order_state)
    TextView order_state;
    @BindView(R.id.out_of_time_tips)
    TextView out_of_time_tips;
    @BindView(R.id.top_apply)
    TextView top_apply;

    @BindView(R.id.wait_pay_bottm)
    RelativeLayout wait_pay_bottom;
    @BindView(R.id.wait_pay_cancel_order)
    TextView wait_pay_cancel_order;
    @BindView(R.id.wait_pay_contact_customer_service)
    TextView wait_pay_contact_customer_service;
    @BindView(R.id.wait_pay_pay_order)
    TextView wait_pay_pay_order;





    @BindView(R.id.wait_receive_bottm)
    RelativeLayout wait_receive_bottom;
//    @BindView(R.id.wait_receive_cancel_order)
//    TextView wait_receive_cancel_order;
    @BindView(R.id.wait_receive_contact_customer_service)
    TextView wait_receive_contact_customer_service;
    @BindView(R.id.wait_receive_tracing_order)
    TextView wait_receive_tracing_order;




    @BindView(R.id.finish_bottm)
    RelativeLayout finish_bottom;
    @BindView(R.id.finish_delete_order)
    TextView finish_delete_order;
    @BindView(R.id.finish_contact_customer_service)
    TextView finish_contact_customer_service;
    @BindView(R.id.finish_evaluate_and_show)
    TextView finish_evaluate_and_show;
    @BindView(R.id.finish_buy_again)
    TextView finish_buy_again;



    @BindView(R.id.cancel_bottm)
    RelativeLayout cancel_bottom;
    @BindView(R.id.cancel_delete_order)
    TextView cancel_delete_order;
    @BindView(R.id.cancel_contact_customer_service)
    TextView cancel_contact_customer_service;
    @BindView(R.id.cancel_buy_again)
    TextView cancel_buy_again;



    @BindView(R.id.after_sale_bottm)
    RelativeLayout after_sale_bottom;
    @BindView(R.id.after_sale_contact_customer_service)
    TextView after_sale_contact_customer_service;
    @BindView(R.id.after_sale_see)
    TextView after_sale_see;




    private long countdownTime ;//倒计时的总时间(单位:毫秒)
    private String timefromServer;//从服务器获取的订单生成时间
    private long chaoshitime;//从服务器获取订单有效时长(单位:毫秒)
    int postage_pay_type = -1;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    OrderDetailGoodsListAdapter goodsListAdapter;
    ArrayList<Order> orderList;
    ArrayList<OrderDetail.ResultDataBean.OrderDetailsBean> orderDetailsBeanList = new ArrayList<>();
    //加载框
    private ProgressDialog progressDialog;
    ClearHistorySearhPopu clearHistorySearhPopu;

    int orderId = -1;
    int userId = -1;//获取App中存储的用户id
    String orderState = "";
    int orderStateInt;//订单状态

    double totalPayMoney = 0;
    String orderNo;
    int isHasEvaluation;
    DecimalFormat df;

    String phoneStr = "";
    String nameStr = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_activity);

        orderId = getIntent().getIntExtra("orderId",-1);
//        orderId = 30;
        //控制注解
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        //开启加载框
        buildProgressDialog();
        df = new DecimalFormat(".00");//保留两位小数

        init();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.back://订单跟踪图标
                finish();
                break;

            case R.id.order_tracing_detail://订单跟踪图标
                viewTracingOrderClick();
                break;

            case R.id.top_apply://申请售后
                intent=new Intent(OrderDetailActivity.this,AfterSaleApplyActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("name",nameStr);
                intent.putExtra("phone",phoneStr);
                startActivityForResult(intent,0);
//                startActivity(intent);
                break;

            //待支付
            case R.id.wait_pay_cancel_order://取消订单
                cancelOrderClick();
                break;
            case R.id.wait_pay_contact_customer_service://联系客服
                Intent intent2=new Intent(this, CustomerServiceActivity.class);
                startActivity(intent2);
                break;
            case R.id.wait_pay_pay_order://支付
                payOrderClick();

                break;

            //待收货
//            case R.id.wait_receive_cancel_order://取消订单
//                cancelOrderClick();
//                break;
            case R.id.wait_receive_contact_customer_service://联系客服
                Intent intent3=new Intent(this, CustomerServiceActivity.class);
                startActivity(intent3);
                break;
            case R.id.wait_receive_tracing_order://订单跟踪
                viewTracingOrderClick();
                break;

            //已完成
            case R.id.finish_delete_order://删除订单
                deleteOrderClick();
                break;
            case R.id.finish_contact_customer_service://联系客服
                Intent intent4=new Intent(this, CustomerServiceActivity.class);
                startActivity(intent4);
                break;
            case R.id.finish_evaluate_and_show://晒单评价
                if(isHasEvaluation == 0){//没有评价完  评价晒单
                    intent  = new Intent(OrderDetailActivity.this,ShouldEvaluationActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }else{//完全评价完成   评价详情
                    intent  = new Intent(OrderDetailActivity.this,EvaluationDetailActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }


                break;
            case R.id.finish_buy_again://再次购买
                buyAgainClick();

                break;

            //已取消
            case R.id.cancel_delete_order://删除订单
                deleteOrderClick();
                break;
            case R.id.cancel_contact_customer_service://联系客服
                Intent intent5=new Intent(this, CustomerServiceActivity.class);
                startActivity(intent5);
                break;
            case R.id.cancel_buy_again://再次购买
                buyAgainClick();
                break;

            //售后
            case R.id.after_sale_contact_customer_service://联系客服
                Intent intent6=new Intent(this, CustomerServiceActivity.class);
                startActivity(intent6);
                break;
            case R.id.after_sale_see://查看售后
                intent  = new Intent(OrderDetailActivity.this,AfterSaleDetailActivity.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                break;

        }
    }
    private void setClickEvent(){
        back.setOnClickListener(this);
        //订单跟踪
        order_tracing_detail.setOnClickListener(this);
        wait_receive_tracing_order.setOnClickListener(this);

        //申请售后
        top_apply.setOnClickListener(this);

        //查看售后
        after_sale_see.setOnClickListener(this);

        //取消订单
        wait_pay_cancel_order.setOnClickListener(this);
//        wait_receive_cancel_order.setOnClickListener(this);

        //删除订单
        finish_delete_order.setOnClickListener(this);
        cancel_delete_order.setOnClickListener(this);

        //再次购买
        finish_buy_again.setOnClickListener(this);
        cancel_buy_again.setOnClickListener(this);

        //支付
        wait_pay_pay_order.setOnClickListener(this);

        //联系客服
        wait_pay_contact_customer_service.setOnClickListener(this);
        wait_receive_contact_customer_service.setOnClickListener(this);
        finish_contact_customer_service.setOnClickListener(this);
        cancel_contact_customer_service.setOnClickListener(this);
        after_sale_contact_customer_service.setOnClickListener(this);

        //晒单评价
        finish_evaluate_and_show.setOnClickListener(this);




    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode){
            case 0://从申请售后页面返回
                int code = intent.getIntExtra("netCode",-1);
                if(code == 200){//申请售后成功
                    buildProgressDialog();
                    getNetData();
                }
                break;
        }
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {


            countdownTime -= 1000;//倒计时总时间减1

//            Log.d("qqqqqqqqqqqqqqq", "run: "+countdownTime);
            if(countdownTime<=0 &&  wait_pay_bottom.getVisibility()==View.VISIBLE){//自动取消订单


                //已取消
                orderState = "已取消";
                //头部状态栏图标和文字的处理
                timer_view.setVisibility(View.GONE);
                out_of_time_tips.setVisibility(View.GONE);

                icon_state.setImageResource(R.drawable.icon_cancel_white);
                order_state.setText("已取消");
                //底部按钮栏处理
                wait_pay_bottom.setVisibility(View.GONE);
                cancel_bottom.setVisibility(View.VISIBLE);


                handler.removeCallbacks(runnable);
            }
//            SimpleDateFormat minforamt = new SimpleDateFormat("hh:mm:ss");
//
//            String hms = minforamt.format(countdownTime);//格式化倒计时的总时间


            long second = countdownTime/1000;
            long hour = second/3600;//小时
            second = second % 3600;                //剩余秒数
            long minutes = second /60;            //转换分钟
            second = second % 60;                //剩余秒数
            String hourStr = hour+"";
            if(hour<10){
                hourStr = "0"+hour;
            }
            String min = minutes+"";
            if(minutes<10){
                min = "0"+minutes;
            }
            String sec = second+"";
            if(second<10){
                sec = "0"+second;
            }

            timer_view.setText(hourStr+":"+min+":"+sec);
            handler.postDelayed(this, 1000);
        }
    };

    //倒计时处理
    private void getTimeDuring() {
        chaoshitime = 4 * 60 * 60 * 1000;//应该从服务器获取 4小时
        if( postage_pay_type==0){
            chaoshitime = 4 * 60 * 60 * 1000;//应该从服务器获取 4小时
        }else  if( postage_pay_type==1){
            chaoshitime = 24 * 60 * 60 * 1000;//应该从服务器获取 24小时
        }

//        timefromServer = "2018-01-19 16:20:00";//应该从服务器获取
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date serverDate = null;
        try {
            serverDate = sdf1.parse(timefromServer);
            long duringTime = new Date().getTime() - serverDate.getTime();//计算当前时间和从服务器获取的订单生成时间的时间差
            countdownTime = chaoshitime - duringTime;//计算倒计时的总时间




        } catch (ParseException e) {
            e.printStackTrace();
        }

        handler.postDelayed(runnable, 1000);




    }

    private void init(){
        getNetData();
    }



    //查看订单跟踪点击事件方法体
    public void viewTracingOrderClick(){
        Intent intent=new Intent(OrderDetailActivity.this,OrderTracingActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("orderState",orderState);
        startActivity(intent);
    }

    //再次购买点击事件方法体
    public void buyAgainClick(){


            Net.get().buyAgain(orderId, userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(copyOrderShoppingCartModel -> {
                        int code = copyOrderShoppingCartModel.getCode();
                        if(code == 200){
                            Intent intent = new Intent(OrderDetailActivity.this,BaseActivity.class);
                            intent.putExtra("shouldReplaceFragment",2);
                            startActivity(intent);
                        }else{
                            Toast.makeText(OrderDetailActivity.this, ""+copyOrderShoppingCartModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }


                    }, throwable -> {
                        Toast.makeText(OrderDetailActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                    });

    }

    //支付点击事件方法体
    public void payOrderClick(){
        Intent intent = null;
        if(totalPayMoney>=0 && totalPayMoney<50000){
            intent = new Intent(OrderDetailActivity.this,PayOrderActivity.class);

        }else if(totalPayMoney>=50000){
            intent = new Intent(OrderDetailActivity.this,UnderlineActivity.class);

        }else{
            Toast.makeText(this, "订单异常", Toast.LENGTH_SHORT).show();
        }
        if(intent != null){
            intent.putExtra("payMoney", totalPayMoney);//支付金额
            intent.putExtra("orderId", orderId);//订单id
            intent.putExtra("orderNo",orderNo);//订单编号
            intent.putExtra("postagePayType",postage_pay_type);//邮费支付方式
            intent.putExtra("fromDetailPage",1);//从订单详情页跳转到支付页面
            startActivity(intent);
        }



    }


    //取消订单
    public void cancelOrderClick(){
        clearHistorySearhPopu = new ClearHistorySearhPopu(this, confirmCancelOrder,2);
        clearHistorySearhPopu.showAtLocation(findViewById(R.id.order_detail_page), Gravity.CENTER, 0, 0);

    }

    //确认取消按钮回调方法
    public View.OnClickListener confirmCancelOrder = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancelOrder(4,"订单取消成功");
            clearHistorySearhPopu.dismiss();
        }

    };

    //删除订单
    public void deleteOrderClick(){
        clearHistorySearhPopu = new ClearHistorySearhPopu(this, confirmDeleteOrder,4);
        clearHistorySearhPopu.showAtLocation(findViewById(R.id.order_detail_page), Gravity.CENTER, 0, 0);

    }
    //确认删除按钮回调方法
    public View.OnClickListener confirmDeleteOrder = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancelOrder(11,"订单删除成功");

            clearHistorySearhPopu.dismiss();
            Intent intent = new Intent(OrderDetailActivity.this,MyOrderListActivity.class);
            startActivity(intent);


        }

    };

    private void cancelOrder(int operation,String message){
        Net.get().cancleOrder(orderId,userId,operation).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cancelOrderModel -> {
                    Log.d("aaaaaaaaaaaaaaa", "取消订单发送请求并接收到数据了");
                    int code1 = cancelOrderModel.getCode();
                    if (code1 == 200) {
                        //开启加载框
                        buildProgressDialog();
                        getNetData();
                        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "" + cancelOrderModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                },throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
    }



    public void setAdapter(){
        //设置RecyclerView的布局方式
        CustomLanearLayoutManager clm = new CustomLanearLayoutManager(this);
        clm.setScrollEnabled(false);
        recyclerView.setLayoutManager(clm);
        //初始化适配器
        goodsListAdapter=new OrderDetailGoodsListAdapter(R.layout.order_detail_goods_list_item,orderDetailsBeanList);
        //设置适配器
        recyclerView.setAdapter(goodsListAdapter);
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

    public void getNetData(){
        if(orderId != -1 && userId != -1){

            Net.get().getOrderDetail(orderId,userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(orderDetail -> {
                        int code1 = orderDetail.getCode();
                        if (code1 == 200) {
                            isHasEvaluation = orderDetail.getResultData().getIsHasEvaluation();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //订单跟踪处理
                            List<OrderDetail.ResultDataBean.OrderStateDetailsBean> orderStateDetailList = orderDetail.getResultData().getOrderStateDetails();
                            String orderTracingMsgStr = "";
                            String orderTracingTimeStr = "";
                            int orderStateDetailListSize = orderStateDetailList.size();
                            if(orderStateDetailListSize>0){
                                orderTracingMsgStr = orderStateDetailList.get(orderStateDetailListSize-1).getOrderStateDetail();
                                if(orderStateDetailList.get(orderStateDetailListSize-1).getAddTime()!=null){
                                    orderTracingTimeStr = sdf.format(orderStateDetailList.get(orderStateDetailListSize-1).getAddTime());
                                }
                            }
                            order_tracing_message.setText(orderTracingMsgStr);
                            order_tracing_time.setText(orderTracingTimeStr);


                            //收货地址处理
                            nameStr = orderDetail.getResultData().getConsigneeName();
                            phoneStr = orderDetail.getResultData().getConsigneeTel();
                            String addressStr = orderDetail.getResultData().getConsigneeAddress();

                            name.setText(nameStr);
                            phone.setText(Tools.hidePhone(phoneStr));
                            address.setText(addressStr);

                            //订单商品列表
                            orderDetailsBeanList = orderDetail.getResultData().getOrderDetails();
                            if(orderDetailsBeanList.size()>0){
                                setAdapter();
                                int goods_num = 0;
                                double postageMoney = 0;
                                postageMoney = orderDetail.getResultData().getPostage();
                                totalPayMoney = postageMoney+orderDetail.getResultData().getOrderPresentPrice();
                                //共几件商品
                                for (int i = 0; i <orderDetailsBeanList.size() ; i++) {
                                    int goodsItemNum = orderDetailsBeanList.get(i).getGoodsQuantity();
                                    goods_num+=goodsItemNum;
                                }
                                my_order_combined_goods_num.setText(goods_num+"");
                                //订单应付金额
                                if(totalPayMoney>1){
                                    my_order_combined_num.setText(df.format(totalPayMoney)+"");
                                }else{
                                    my_order_combined_num.setText(Double.parseDouble(df.format(totalPayMoney))+"");
                                }

                                //邮费
                                my_order_freight.setText(postageMoney+"");
                                //订单编号
                                orderNo = orderDetail.getResultData().getOrderNo();
                                order_no.setText(orderNo);
                                //下单时间

                                String order_commit_time_str = sdf.format(orderDetail.getResultData().getPlaceOrderTime());
                                timefromServer = order_commit_time_str;
                                postage_pay_type = orderDetail.getResultData().getPostagePayType();
                                getTimeDuring();
                                order_commit_time.setText(order_commit_time_str);

                                //发票信息
                                if(orderDetail.getResultData().getInvoice() != null){
                                    invoice_layout.setVisibility(View.VISIBLE);
                                    //发票类型（0：普通发票，1：增值发票）
                                    int invoiceType = orderDetail.getResultData().getInvoice().getInvoiceType();
                                    String invoiceTypeStr="";
                                    switch (invoiceType){
                                        case 0://普通发票
                                            invoiceTypeStr = "普通发票";
                                            break;
                                        case 1://增值发票
                                            invoiceTypeStr = "增值发票";
                                            break;
                                    }
                                    //发票抬头类型（0：单位，1：个人）
                                    int invoiceLookedUpType = orderDetail.getResultData().getInvoice().getInvoiceLookedUpType();
                                    String invoiceLookedUpTypeStr="";
                                    switch (invoiceLookedUpType){
                                        case 0://单位
                                            invoiceLookedUpTypeStr = "单位";
                                            break;
                                        case 1://个人
                                            invoiceLookedUpTypeStr = "个人";
                                            break;
                                    }
                                    invoice.setText(invoiceTypeStr+"-"+invoiceLookedUpTypeStr);
                                }else{
                                    invoice_layout.setVisibility(View.GONE);
                                }


                                //支付时间
                                String payTimeStr = "";
                                if(orderDetail.getResultData().getPayTime()!=null){
                                    payTimeStr = sdf.format(orderDetail.getResultData().getPayTime());
                                    pay_mode_view.setTextColor(getResources().getColor(R.color.font_grey));
                                    pay_mode_view.setEnabled(false);
                                    if(orderDetail.getResultData().getPayMode()!=null){
                                        if(orderDetail.getResultData().getPayMode()==1){
                                            pay_mode_view.setText("支付宝");
                                        }
                                        else if(orderDetail.getResultData().getPayMode()==2){
                                            pay_mode_view.setText("微信");
                                        }
                                        else if(orderDetail.getResultData().getPayMode()==3){
                                            pay_mode_view.setText("银行卡");
                                        }
                                        else{
                                            pay_mode_view.setText("");
                                        }
                                    }

                                }
                                pay_time_view.setText(payTimeStr);
                                //支付方式   订单支付类型(0：快捷支付，1：线下支付)
                                String payTypeStr = "";
                                if(orderDetail.getResultData().getPayType()!=null){
                                    int payType =orderDetail.getResultData().getPayType();
                                    switch (payType){
                                        case 0://快捷支付
                                            payTypeStr = "快捷支付";
                                            pay_type_view.setTextColor(getResources().getColor(R.color.font_grey));
                                            pay_type_view.setEnabled(false);

                                            break;
                                        case 1://线下支付
                                            payTypeStr = "线下支付";
                                            pay_type_view.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                            pay_type_view.setEnabled(true);
                                            pay_type_view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
                                            pay_type_view.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent=new Intent(OrderDetailActivity.this,UnderlineDetailActivity.class);
                                                    intent.putExtra("payMoney",(orderDetail.getResultData().getOrderPresentPrice()+orderDetail.getResultData().getPostage()));
                                                    intent.putExtra("orderNo",orderDetail.getResultData().getOrderNo());
                                                    intent.putExtra("postageMoney",orderDetail.getResultData().getPostage());
                                                    intent.putExtra("payeeName",orderDetail.getResultData().getOfflinePayment().getPayeeName());
                                                    intent.putExtra("payeeAccount",orderDetail.getResultData().getOfflinePayment().getPayeeAccount());
                                                    intent.putExtra("payeeAccountDepositBank",orderDetail.getResultData().getOfflinePayment().getPayeeAccountDepositBank());
                                                    intent.putExtra("postagePayType",orderDetail.getResultData().getPostagePayType());
                                                    intent.putExtra("remitterName",orderDetail.getResultData().getOfflinePayment().getRemitterName());
                                                    intent.putExtra("remitterAccount",orderDetail.getResultData().getOfflinePayment().getRemitterAccount());
                                                    if (orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlOne()!=null&&orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlTwo()!=null&&!"".equals(orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlOne())&&!"".equals(orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlTwo())){
                                                        Log.d("aaaaaaaaaaaaaaaaaaa","picNum:"+2);
                                                        Log.d("aaaaaaaaaaaaaaaaaaa","pic1:"+orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlOne());
                                                        Log.d("aaaaaaaaaaaaaaaaaaa","pic2:"+orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlTwo());
                                                        intent.putExtra("picNum",2);
                                                        intent.putExtra("pic1",orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlOne());
                                                        intent.putExtra("pic2",orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlTwo());
                                                    }else if(!"".equals(orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlOne())&&orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlTwo()==null){
                                                        intent.putExtra("picNum",1);
                                                        intent.putExtra("pic1",orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlOne());
                                                        Log.d("aaaaaaaaaaaaaaaaaaa","picNum:"+1);
                                                        Log.d("aaaaaaaaaaaaaaaaaaa","pic1:"+orderDetail.getResultData().getOfflinePayment().getPaymentVoucherUrlOne());
                                                    }

                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                    }
                                }
                                pay_type_view.setText(payTypeStr);
                                //订单状态(0：待支付，1：待发货, 2：待收货，3：已完成，4：已取消，5：已关闭，6：售后中，7：已退货退款，8：已换货,9:已支付,10:关闭售后)
                                orderStateInt = orderDetail.getResultData().getOrderState();
                                //默认
                                if(orderDetail.getResultData().getPayTime()==null){
                                    pay_time_layout.setVisibility(View.GONE);
                                    pay_type_layout.setVisibility(View.GONE);
                                    pay_mode_layout.setVisibility(View.GONE);
                                }else if(orderDetail.getResultData().getPayTime()!=null && orderDetail.getResultData().getPayTime()!= ""){
                                    pay_time_layout.setVisibility(View.VISIBLE);
                                    pay_type_layout.setVisibility(View.VISIBLE);
                                    if(orderDetail.getResultData().getPayType()==0){
                                        pay_mode_layout.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        pay_mode_layout.setVisibility(View.GONE);
                                    }
                                }

                                top_apply.setVisibility(View.GONE);
                                timer_view.setVisibility(View.GONE);
                                out_of_time_tips.setVisibility(View.GONE);
                                wait_pay_bottom.setVisibility(View.GONE);
                                wait_receive_bottom.setVisibility(View.GONE);
                                finish_bottom.setVisibility(View.GONE);
                                cancel_bottom.setVisibility(View.GONE);
                                after_sale_bottom.setVisibility(View.GONE);


                                switch (orderStateInt){
                                    case 0://待支付
                                        orderState = "待支付";
                                    //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_card_white);
                                        order_state.setText("待支付");
                                        timer_view.setVisibility(View.VISIBLE);
                                        out_of_time_tips.setVisibility(View.VISIBLE);
                                    //不显示
                                        pay_time_layout.setVisibility(View.GONE);
                                        pay_type_layout.setVisibility(View.GONE);
                                        //底部按钮栏处理
                                        wait_pay_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 1://待发货
                                        orderState = "待发货";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_car_white);
                                        order_state.setText("待发货");
                                        //底部按钮栏处理
                                        wait_receive_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 2://待收货
                                        orderState = "待收货";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_car_white);
                                        order_state.setText("待收货");
                                        //底部按钮栏处理
                                        wait_receive_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 3://已完成
                                        orderState = "已完成";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_box_white);
                                        order_state.setText("已完成");
                                        top_apply.setVisibility(View.VISIBLE);
                                        //底部按钮栏处理
                                        finish_bottom.setVisibility(View.VISIBLE);
                                        if(isHasEvaluation == 0){//没有评价完
                                            finish_evaluate_and_show.setText("评价晒单");
                                        }else{//全部评价完成
                                            finish_evaluate_and_show.setText("评价详情");
                                        }

                                        break;
                                    case 4://已取消
                                        orderState = "已取消";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_cancel_white);
                                        order_state.setText("已取消");
                                        //底部按钮栏处理
                                        cancel_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 5://已关闭
                                        orderState = "已关闭";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_cancel_white);
                                        order_state.setText("已关闭");
                                        //底部按钮栏处理
                                        cancel_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 6://售后中
                                        orderState = "售后中";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_aftermark_white);
                                        order_state.setText("售后中");
                                        //底部按钮栏处理
                                        after_sale_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 7://已退货退款
                                        orderState = "已退货退款";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_aftermark_white);
                                        order_state.setText("已退货退款");
                                        //底部按钮栏处理
                                        after_sale_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 8://已换货
                                        orderState = "已换货";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_aftermark_white);
                                        order_state.setText("已换货");
                                        //底部按钮栏处理
                                        after_sale_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 9://已支付
                                        orderState = "已支付，正在审核";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_car_white);
                                        order_state.setText("已支付，正在审核");
                                        //底部按钮栏处理
                                        wait_receive_bottom.setVisibility(View.VISIBLE);
                                        break;
                                    case 10://关闭售后
                                        orderState = "关闭售后";
                                        //头部状态栏图标和文字的处理
                                        icon_state.setImageResource(R.drawable.icon_aftermark_white);
                                        order_state.setText("关闭售后");
                                        //底部按钮栏处理
                                        after_sale_bottom.setVisibility(View.VISIBLE);
                                        break;
                                }
                                setClickEvent();
                            }

                            cancelProgressDialog();
                        } else {
                            Toast.makeText(this, "" + orderDetail.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        Toast.makeText(this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                    });
        }else if(userId == -1){
//            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderDetailActivity.this,LoginActivity.class);
            startActivity(intent);
        }else if(orderId == -1){
            Toast.makeText(this, "页面传值没有接收到", Toast.LENGTH_SHORT).show();
        }

    }




}
