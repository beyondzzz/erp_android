package com.jl.jlapp.mvp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.AfterSaleDetailGoodsListAdapter;
import com.jl.jlapp.adapter.AfterSaleImgAdapter;
import com.jl.jlapp.eneity.AfterSalePic;
import com.jl.jlapp.eneity.CustomerServiceByUserIdAndOrderIdModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CustomGridLayoutManager;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
import com.jl.jlapp.utils.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-20.
 */

public class AfterSaleDetailActivity extends AppCompatActivity{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imgRecyclerView)
    RecyclerView imgRecyclerView;
    @BindView(R.id.service_type)
    TextView service_type;
    @BindView(R.id.problem_description)
    TextView problem_description;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.pic_sc)
    TextView picSc;
    @BindView(R.id.img)
    PhotoView mPhotoView;
    @BindView(R.id.parent_detail)
    LinearLayout parentDetail;
    @BindView(R.id.parent_photo_view)
    RelativeLayout parentPhotoView;
    @BindView(R.id.bg)
        ImageView mBg;
    Info mInfo;

//    Bitmap bitmap;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

//    ArrayList<Order> orderList = new ArrayList<>();
    AfterSaleDetailGoodsListAdapter goodsListAdapter;
    AfterSaleImgAdapter afterSaleImgAdapter;
    ArrayList<AfterSalePic> afterSaleImgList;
    List<CustomerServiceByUserIdAndOrderIdModel.ResultDataBean.OrderTableBean.OrderDetailsBean> orderDetailsBeanList = new ArrayList<>();
    List<CustomerServiceByUserIdAndOrderIdModel.ResultDataBean.AfterSalePicsBean> afterSalePicsBeanList = new ArrayList<>();
    //加载框
    private ProgressDialog progressDialog;

    int orderId = -1;
    int userId = -1;//获取App中存储的用户id

    //定义Handler对象
//    private Handler handler ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_detail);

        orderId = getIntent().getIntExtra("orderId",-1);
        //控制注解
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);


        //开启加载框
        buildProgressDialog();

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        parentDetail.setVisibility(View.VISIBLE);
        parentPhotoView.setVisibility(View.GONE);
    }

    private void init(){
        getNetData();
//        setAdapter();
        imgSetAdapter();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        parentPhotoView.setVisibility(View.GONE);
                        parentDetail.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }



    public void setAdapter(){
        //设置RecyclerView的布局方式
        CustomLanearLayoutManager clm = new CustomLanearLayoutManager(this);
        clm.setScrollEnabled(false);
        recyclerView.setLayoutManager(clm);
        //初始化适配器
        goodsListAdapter=new AfterSaleDetailGoodsListAdapter(R.layout.after_sale_detail_goods_list_item,orderDetailsBeanList);
        //设置适配器
        recyclerView.setAdapter(goodsListAdapter);


    }
    public void imgSetAdapter(){
        //设置RecyclerView的布局方式
        imgRecyclerView.setLayoutManager(new CustomGridLayoutManager(this,5,false));
        //初始化适配器
        afterSaleImgAdapter=new AfterSaleImgAdapter(R.layout.after_sale_img_item,afterSalePicsBeanList);
        //设置适配器
        imgRecyclerView.setAdapter(afterSaleImgAdapter);
        afterSaleImgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img:
                        String picUrl = afterSalePicsBeanList.get(position).getPicUrl();

                        parentDetail.setVisibility(View.GONE);
                        mInfo = mPhotoView.getInfo();

//                        new Thread(){
//                            @Override
//                            public void run(){
//                                //你要执行的方法
//                                bitmap = getBitmap(picUrl);
//                                //执行完毕后给handler发送一个空消息
//                                handler.sendEmptyMessage(0);
//                            }
//                        }.start();
//
//                    handler=new Handler(){
//                        @Override
//                        //当有消息发送出来的时候就执行Handler的这个方法
//                        public void handleMessage(Message msg){
//                            super.handleMessage(msg);
//                            //处理UI
//                            mPhotoView.setImageBitmap(bitmap);
//
//                            mBg.startAnimation(in);
//                            mBg.setVisibility(View.VISIBLE);
//                            parentPhotoView.setVisibility(View.VISIBLE);
//                            mPhotoView.animaFrom(mInfo);
//                        }
//                    };


                        Glide
                                .with(AfterSaleDetailActivity.this)
                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_xss).error(R.drawable.img_lose_xs))
                                .load(AppFinal.BASEURL + picUrl)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                                        mPhotoView.setImageBitmap(resource);

                                        mBg.startAnimation(in);
                                        mBg.setVisibility(View.VISIBLE);
                                        parentPhotoView.setVisibility(View.VISIBLE);
                                        mPhotoView.animaFrom(mInfo);
                                    }
                                });



                        break;
                }
            }
        });
    }
//    public static Bitmap getBitmap(String urlpath) {
//        Bitmap map = null;
//        try {
//            URL url = new URL(urlpath);
//            URLConnection conn = url.openConnection();
//            conn.connect();
//            InputStream in;
//            in = conn.getInputStream();
//            map = BitmapFactory.decodeStream(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return map;
//    }
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK
            && event.getRepeatCount() == 0) {
        if(parentPhotoView.getVisibility() == View.VISIBLE){
           parentPhotoView.setVisibility(View.GONE);
           parentDetail.setVisibility(View.VISIBLE);
        }else{

            finish();
        }


        return true;
    }
    return super.onKeyDown(keyCode, event);
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
            Net.get().getCustomerServiceByUserIdAndOrderId(orderId,userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(customerServiceByUserIdAndOrderIdModel -> {
                        //                    Log.d("aaaaaaaaaaaaaaa", "获取数据加载了");
                        int code1 = customerServiceByUserIdAndOrderIdModel.getCode();
                        if (code1 == 200) {
                            int serviceType = customerServiceByUserIdAndOrderIdModel.getResultData().getServiceType();//服务类型  0：退货  1：换货  2：其他
                            switch (serviceType){
                                case 0:
                                    service_type.setText("退货");
                                    break;
                                case 1:
                                    service_type.setText("换货");
                                    break;
                                case 2:
                                    service_type.setText("其他");
                                    break;
                            }
                            String problemDescription = customerServiceByUserIdAndOrderIdModel.getResultData().getProblemDescription();
                            problem_description.setText(problemDescription);
                            String nameStr = customerServiceByUserIdAndOrderIdModel.getResultData().getName();
                            name.setText(nameStr);
                            String phoneStr = customerServiceByUserIdAndOrderIdModel.getResultData().getPhone();
                            phone.setText(phoneStr);
                            int statusInt = customerServiceByUserIdAndOrderIdModel.getResultData().getStatus();
                            switch (statusInt){
                                case 0:
                                    status.setText("未处理");
                                    break;
                                case 1:
                                    status.setText("已处理");
                                    break;

                            }
                            orderDetailsBeanList = customerServiceByUserIdAndOrderIdModel.getResultData().getOrderTable().getOrderDetails();
                            if(orderDetailsBeanList.size()>0){
                                setAdapter();
                            }
                            afterSalePicsBeanList = customerServiceByUserIdAndOrderIdModel.getResultData().getAfterSalePics();
                            if(afterSalePicsBeanList.size()>0){
                                imgSetAdapter();
                                picSc.setVisibility(View.VISIBLE);
                            }else{
                                picSc.setVisibility(View.GONE);
                            }
                            cancelProgressDialog();
                        } else {
                            Toast.makeText(this, "" + customerServiceByUserIdAndOrderIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                    });
        }else if(userId == -1){
//            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AfterSaleDetailActivity.this,LoginActivity.class);
            startActivity(intent);
        }else if(orderId == -1){
            Toast.makeText(this, "页面传值没有接收到", Toast.LENGTH_SHORT).show();
        }

    }
}
