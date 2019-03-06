package com.jl.jlapp.mvp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.OrderDetailsAdapter;
import com.jl.jlapp.eneity.Evaluate;
import com.jl.jlapp.eneity.ImageBean;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
import com.jl.jlapp.utils.MimeTypeUtils;
import com.jl.jlapp.utils.Tools;
import com.jl.jlapp.utils.loader.Loader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yzs.imageshowpickerview.ImageShowPickerBean;
import com.yzs.imageshowpickerview.ImageShowPickerListener;
import com.yzs.imageshowpickerview.ImageShowPickerView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UnderlineActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_CHOOSE = 233;

    @BindView(R.id.rlv_order_details)
    RecyclerView rlvOrderDetails;
    @BindView(R.id.tv_success)
    TextView tvSuccess;
    @BindView(R.id.it_picker_view)
    ImageShowPickerView pickerView;
    @BindView(R.id.offline_payment_submit_btn)
    TextView offlinePaymentSubmitBtn;
    @BindView(R.id.tv_remitter_name_details)
    EditText tvRemitterNameDetails;
    @BindView(R.id.tv_remitter_account_details)
    EditText tvRemitterAccountDetails;
    @BindView(R.id.pay_title)
    TextView payTitle;
    @BindView(R.id.out_of_time_tips)
    TextView outOfTimeTips;
    @BindView(R.id.overtime_pay_title)
    TextView overtimePayTitle;
    @BindView(R.id.timer_pay)
    TextView timer_view;
    @BindView(R.id.write_underline_msg)
    LinearLayout writeUnderlineMsg;

    List<Evaluate> models;

    List<ImageBean> list;
    List<String> realPicUrlList = new ArrayList<>();

    MultipartBody.Part[] partList = new MultipartBody.Part[10];
    List<String> picUrlList = new ArrayList<>();

    double payMoney = -1;
    int orderId = 0;
    String orderNo = "";
    int postagePayType = -1;
    String remitterName = "";
    String remitterAccount = "";

    int fromDetailPage=-1;

    int userId = 0;//获取App中存储的用户id
    private long countdownTime ;//倒计时的总时间(单位:毫秒)
    private String timefromServer;//从服务器获取的订单生成时间
    private long chaoshitime;//从服务器获取订单有效时长(单位:毫秒)
    boolean isNotOvertime=true;//判断是否超时
    ClearHistorySearhPopu clearHistorySearhPopu;

    //加载框
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_underline);
        ButterKnife.bind(this);
        Tools.finishAll();
        tvRemitterAccountDetails.setInputType(EditorInfo.TYPE_CLASS_PHONE);//设置数字键盘

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);

        Intent intent = getIntent();
        payMoney = intent.getDoubleExtra("payMoney", -1);
        orderId = intent.getIntExtra("orderId", 0);
        orderNo = intent.getStringExtra("orderNo");
        postagePayType = intent.getIntExtra("postagePayType", -1);
        fromDetailPage=intent.getIntExtra("fromDetailPage",-1);//等于1说明从订单详情页跳转而来
        if (payMoney != -1 && orderId != -1 && orderNo != null && orderNo != "" && postagePayType != -1) {
            if (payMoney >= 50000) {
                getOrderMsg();
                setData();
                setAdapter();

            } else {
                Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
        }


    }

    public void setData() {
        tvSuccess.setText("您的订单(订单号:" + orderNo + ")已提交成功");

        models = new ArrayList<>();
        String title[] = {"订单号：", "待支付金额：", "收款人：", "开户行：", "账号："};
        //邮费到付
        if (postagePayType==1){
            String s[] = {orderNo, "¥" + payMoney+"(订单超出配送范围，运费货到付款)", "北京食讯帮发展有限公司", "中国光大银行股份有限公司北京清华园支行", "35360188000020130"};
            for (int i = 0; i < title.length; i++) {
                Evaluate model = new Evaluate();
                model.setTitle(title[i]);
                model.setScroe(s[i]);
                models.add(model);
            }

            handleImgPicker();
            submitBtnClick();
        }
        else{
            String s[] = {orderNo, "¥" + payMoney, "北京食讯帮发展有限公司", "中国光大银行股份有限公司北京清华园支行", "35360188000020130"};
            for (int i = 0; i < title.length; i++) {
                Evaluate model = new Evaluate();
                model.setTitle(title[i]);
                model.setScroe(s[i]);
                models.add(model);
            }

            handleImgPicker();
            submitBtnClick();
        }


    }

    public void setAdapter() {
        CustomLanearLayoutManager layoutManager = new CustomLanearLayoutManager(this);
        layoutManager.setScrollEnabled(false);
        rlvOrderDetails.setLayoutManager(layoutManager);

        OrderDetailsAdapter detailsAdapter = new OrderDetailsAdapter(R.layout.item_order_details, models);
        rlvOrderDetails.setAdapter(detailsAdapter);
    }

    public void submitBtnClick() {
        offlinePaymentSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                remitterName = tvRemitterNameDetails.getText().toString().trim();
                remitterAccount = tvRemitterAccountDetails.getText().toString().trim();
                if (userId == 0 || "".equals(orderNo) || payMoney < 50000) {
                    Toast.makeText(UnderlineActivity.this, "内部错误", Toast.LENGTH_SHORT).show();
                } else if ("".equals(remitterName)) {
                    Toast.makeText(UnderlineActivity.this, "请输入汇款人姓名", Toast.LENGTH_SHORT).show();
                } else if ("".equals(remitterAccount)) {
                    Toast.makeText(UnderlineActivity.this, "请输入汇款人账号", Toast.LENGTH_SHORT).show();
                } else if (realPicUrlList.size() == 0) {
                    Toast.makeText(UnderlineActivity.this, "请上传支付凭证", Toast.LENGTH_SHORT).show();
                } else if (userId > 0 && !"".equals(orderNo) && payMoney >= 50000 && !"".equals(remitterName) && !"".equals(remitterAccount)) {
                    //开启加载框
                    buildProgressDialog();

                    photo(realPicUrlList);

                    offlinePaymentSubmitBtn.setClickable(false);
                    offlinePaymentSubmitBtn.setTextColor(getResources().getColor(R.color.font_grey));

                    Net.get().uploadPhoto(partList).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(imgUploadModel -> {

                                int code1 = imgUploadModel.getCode();
                                if (code1 == 200) {
                                    picUrlList = new ArrayList<>();
                                    picUrlList = imgUploadModel.getResultData();
                                    Net.get().insertOfflinePayment(orderNo, userId, remitterName, remitterAccount, "食讯帮", "621288XXXXXXXXXXX", "中国工商银行积水潭支行", payMoney, picUrlList).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(postModel -> {
                                                cancelProgressDialog();
                                                if (postModel.getCode() == 200) {
                                                    int picNum = picUrlList.size();
                                                    Intent intent = new Intent(UnderlineActivity.this, UnderLinePayResultActivity.class);
                                                    intent.putExtra("page", 1);
                                                    intent.putExtra("orderNo", orderNo);
                                                    intent.putExtra("orderId", orderId);
                                                    intent.putExtra("picNum",picNum);
                                                    switch (picNum){
                                                        case 1:
                                                            intent .putExtra("pic1",picUrlList.get(0));
                                                            break;
                                                        case 2:
                                                            intent .putExtra("pic1",picUrlList.get(0));
                                                            intent .putExtra("pic2",picUrlList.get(1));
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                    Tools.addActivity(UnderlineActivity.this);
                                                    startActivity(intent);
                                                } else {
                                                    offlinePaymentSubmitBtn.setClickable(true);
                                                    offlinePaymentSubmitBtn.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                                }
                                            }, throwable -> {

                                                cancelProgressDialog();
                                                Toast.makeText(UnderlineActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    offlinePaymentSubmitBtn.setClickable(true);
                                    offlinePaymentSubmitBtn.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                    cancelProgressDialog();
                                    Toast.makeText(UnderlineActivity.this, "" + imgUploadModel.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }, throwable -> {
                                cancelProgressDialog();
                                Toast.makeText(UnderlineActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                            });
                }

            }
        });
    }

    private void photo(List<String> list) {
        partList = new MultipartBody.Part[10];
        String path = "";
        for (int i = 0; i < list.size(); i++) {
            path = list.get(i);
            File fi = new File(path);
            if (fi != null) {
                RequestBody body = RequestBody.create(MediaType.parse(MimeTypeUtils.getMimeType(fi)), fi);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", fi.getName(), body);
                //            part
                partList[i] = part;
            }
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
        progressDialog.setMessage("提交中...");
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

    /**
     * 处理图片上传控件
     */
    public void handleImgPicker() {

        list = new ArrayList<>();

        pickerView.setImageLoaderInterface(new Loader());
        pickerView.setNewData(list);
        //展示有动画和无动画

        pickerView.setShowAnim(true);
        pickerView.setMaxNum(2);
        pickerView.setOneLineShowNum(4);
        pickerView.setmDelLabel(R.drawable.icon_del_certificate);
        pickerView.setmAddLabel(R.drawable.upload_certificate_s);
        pickerView.setmPicSize(200);

        pickerView.setPickerListener(new ImageShowPickerListener() {
            @Override
            public void addOnClickListener(int remainNum) {
                //                Matisse.from(AfterSaleApplyActivity.this)
                //                        .choose(MimeType.allOf())
                //                        .countable(true)
                //                        .maxSelectable(remainNum + 1)
                //                        .gridExpectedSize(300)
                //                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                //                        .thumbnailScale(0.85f)
                //                        .imageEngine(new GlideEngine())
                //                        .theme(R.style.Matisse_Dracula)
                //                        .forResult(REQUEST_CODE_CHOOSE);


                if(ActivityCompat.checkSelfPermission(UnderlineActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(UnderlineActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){//没有授权
                    AndPermission.with(UnderlineActivity.this)
                            .requestCode(300)
                            .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .rationale(rationaleListener)
                            .callback(this)
                            .start();

                }else{
                    Matisse.from(UnderlineActivity.this)
                            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))//选择mime的类型
                            .countable(true)//设置从1开始的数字
                            .maxSelectable(remainNum + 1)//选择图片的最大数量限制
                            .capture(true)//启用相机
                            .captureStrategy(new CaptureStrategy(true,"com.jl.jlapp.publish.MyFileProvider"))//拍照的图片路径
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//屏幕显示方向
                            .gridExpectedSize(300) // 列表中显示的图片大小
                            .thumbnailScale(0.85f) // 缩略图的比例
                            .imageEngine(new PicassoEngine()) // 使用的图片加载引擎
                            .theme(R.style.Matisse_Dracula) // 黑色背景
                            .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
                }




                //                Toast.makeText(AfterSaleApplyActivity.this, "remainNum" + remainNum, Toast.LENGTH_SHORT).show();

                //                Toast.makeText(AfterSaleApplyActivity.this, "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
                //                //在listview或recyclerview才会使用这个list.add(),其他情况都不用
                //                list.add(new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg"));
                //                pickerView.addData(new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg"));
            }

            @Override
            public void picOnClickListener(List<ImageShowPickerBean> list, int position, int remainNum) {
                //                Toast.makeText(AfterSaleApplyActivity.this, list.size() + "========" + position + "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UnderlineActivity.this,PhotoViewActivity.class);
                intent.putExtra("picUrl",realPicUrlList.get(position));
                intent.putExtra("flag",1);
                startActivity(intent);
            }

            @Override
            public void delOnClickListener(int position, int remainNum) {

                //                Toast.makeText(AfterSaleApplyActivity.this, "delOnClickListenerremainNum" + remainNum, Toast.LENGTH_SHORT).show();
                realPicUrlList.remove(position);

            }
        });
        pickerView.show();

        //权限
        // Activity:
        AndPermission.with(this)
                .requestCode(300)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .rationale(rationaleListener)
                .callback(this)
                .start();


        //获取所有数据
        pickerView.getDataList();
    }


    @PermissionYes(300)
    private void getPermissionYes(List<String> grantedPermissions) {
        // Successfully.
        //        Toast.makeText(AfterSaleApplyActivity.this, "已授权", Toast.LENGTH_SHORT).show();

    }

    @PermissionNo(300)
    private void getPermissionNo(List<String> deniedPermissions) {
        // Failure.
        Toast.makeText(UnderlineActivity.this, "未授权", Toast.LENGTH_SHORT).show();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int i, final Rationale rationale) {
            // 自定义对话框。
//            AlertDialog.newBuilder(UnderlineActivity.this)
//                    .setTitle("请求权限")
//                    .setMessage("\"食讯帮\"申请使用相机及图库权限，您是否允许？")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
                            rationale.resume();
//                        }
//                    })
//                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                            rationale.cancel();
//                        }
//                    }).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //            mSelected = Matisse.obtainResult(data);
            List<Uri> uriList = Matisse.obtainResult(data);
            if (uriList.size() == 1) {
                pickerView.addData(new ImageBean(getRealFilePath(UnderlineActivity.this, uriList.get(0))));
            } else {
                List<ImageBean> list = new ArrayList<>();
                for (Uri uri : uriList) {
                    list.add(new ImageBean(getRealFilePath(UnderlineActivity.this, uri)));
                }
                pickerView.addData(list);
            }
        }
    }


    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

            String auth = uri.getAuthority();
            if (auth.equals("media")) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {

                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            } else if (auth.equals("com.jl.jlapp.publish.MyFileProvider")) {
                //参看file_paths_public配置
                data = Environment.getExternalStorageDirectory() + "/Pictures/" + uri.getLastPathSegment();
            }

        }
        realPicUrlList.add(data);
        return data;
    }

    @OnClick({R.id.btn_return})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_return:
                if(isNotOvertime){
                    clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(fromDetailPage!=1) {
                                Intent intent = new Intent(UnderlineActivity.this, OrderDetailActivity.class);
                                intent.putExtra("orderId", orderId);
                                startActivity(intent);

                            }
                            clearHistorySearhPopu.dismiss();
                            finish();
                        }
                    }, 12);
                    clearHistorySearhPopu.showAtLocation(findViewById(R.id.underline_page), Gravity.CENTER, 0, 0);

                }
                else{
                    finish();

                }
                break;
        }
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if(countdownTime>1000){
                isNotOvertime=true;
                Log.d("aaaaaaaa", "run: "+countdownTime);
                countdownTime -= 1000;//倒计时总时间减1
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

            else {//自动取消订单
                isNotOvertime=false;
                payTitle.setVisibility(View.GONE);
                timer_view.setVisibility(View.GONE);
                outOfTimeTips.setVisibility(View.GONE);
                overtimePayTitle.setVisibility(View.VISIBLE);
                offlinePaymentSubmitBtn.setVisibility(View.GONE);
                writeUnderlineMsg.setVisibility(View.GONE);

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
        if( postagePayType==0){
            chaoshitime = 4 * 60 * 60 * 1000;//应该从服务器获取 4小时
        }else  if( postagePayType==1){
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

    //监听手机屏幕上的按键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isNotOvertime){
                clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(fromDetailPage!=1) {
                            Intent intent = new Intent(UnderlineActivity.this, OrderDetailActivity.class);
                            intent.putExtra("orderId", orderId);
                            startActivity(intent);

                        }
                        clearHistorySearhPopu.dismiss();
                        finish();
                    }
                }, 12);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.underline_page), Gravity.CENTER, 0, 0);
                return true;
            }
            else{
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /*获取订单信息*/
    public void getOrderMsg(){
        Net.get().getOrderDetail(orderId,userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetail -> {
                    Log.d("aaaaaaaaaaaaaaa", "获取数据加载了");
                    int code1 = orderDetail.getCode();
                    if (code1 == 200) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String order_commit_time_str = sdf.format(orderDetail.getResultData().getPlaceOrderTime());
                        Log.d("aaaaaaaa", "timer: "+order_commit_time_str);
                        timefromServer = order_commit_time_str;
                        postagePayType = orderDetail.getResultData().getPostagePayType();
                        getTimeDuring();
                    } else {
                        Toast.makeText(this, "" + orderDetail.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
    }
}
