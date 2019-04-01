package com.jl.jlapp.mvp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.AfterSaleApplyGoodsListAdapter;
import com.jl.jlapp.eneity.ImageBean;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
import com.jl.jlapp.utils.MimeTypeUtils;
import com.jl.jlapp.utils.Tools;
import com.jl.jlapp.utils.loader.Loader;
import com.yanzhenjie.alertdialog.AlertDialog;
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
import com.zhihu.matisse.ui.MatisseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by THINK on 2018-01-20.
 */

public class AfterSaleApplyActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_CHOOSE = 233;
    private static final String TAG = "AfterSaleApplyActivity";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<OrderDetail.ResultDataBean.OrderDetailsBean> orderDetailsBeanList = new ArrayList<>();
    AfterSaleApplyGoodsListAdapter goodsListAdapter;
    @BindView(R.id.length_limit)
    TextView lengthLimitTextView;
    @BindView(R.id.describe)
    EditText describeView;
    @BindView(R.id.phone_number)
    EditText phone_number;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.it_picker_view)
    ImageShowPickerView pickerView;
    @BindView(R.id.return_goods)
    TextView return_goods;
    @BindView(R.id.change_goods)
    TextView change_goods;
    @BindView(R.id.other)
    TextView other;
    @BindView(R.id.commit_view)
    TextView commit_view;
    @BindView(R.id.btn_return)
    ImageView btn_return;

    //加载框
    private ProgressDialog progressDialog;
    List<ImageBean> list;
    List<String> realPicUrlList = new ArrayList<>();

    MultipartBody.Part[] partList = new MultipartBody.Part[10];

    int serviceType = -1;
    String describeStr = "";
    String phoneNumberStr = "";
    String nameStr = "";
    String intentName = "";
    String intentPhone = "";

    List<String> picUrlList = new ArrayList<>();
    String[] picUrlArr = new String[10];
    ClearHistorySearhPopu clearHistorySearhPopu;

    int netCode = -1;


    int orderId = -1;
    int userId = -1;//获取App中存储的用户id



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_apply);

        orderId = getIntent().getIntExtra("orderId",-1);
        intentName = getIntent().getStringExtra("name");
        intentPhone = getIntent().getStringExtra("phone");
        //        orderId = 30;
        //控制注解
        ButterKnife.bind(this);

        handleImgPicker();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        //开启加载框
        buildProgressDialog();

        init();
    }

    //初始化
    private void init(){
        phone_number.setText(intentPhone);
        name.setText(intentName);
        phone_number.setInputType(InputType.TYPE_CLASS_PHONE);
        setListener();
        getNetData();
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearHistorySearhPopu != null ){
                    clearHistorySearhPopu.dismiss();
                    Intent intent = new Intent(AfterSaleApplyActivity.this,OrderDetailActivity.class);
                    intent.putExtra("netCode",netCode);
                    setResult(0,intent);

                    Tools.finishAll();
                }else{
                    Intent intent = new Intent(AfterSaleApplyActivity.this,OrderDetailActivity.class);
                    intent.putExtra("netCode",netCode);
                    setResult(0,intent);
                    finish();
                }
            }
        });
        commit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addActivity(AfterSaleApplyActivity.this);
                describeStr = describeView.getText().toString().trim();
                phoneNumberStr = phone_number.getText().toString().trim();
                nameStr = name.getText().toString().toString().trim();
                if(orderId == -1 || userId == -1){
                    Toast.makeText(AfterSaleApplyActivity.this, "内部错误", Toast.LENGTH_SHORT).show();
                }else if(serviceType == -1){
                    Toast.makeText(AfterSaleApplyActivity.this, "请选择服务类型", Toast.LENGTH_SHORT).show();
                }else if("".equals(describeStr)){
                    Toast.makeText(AfterSaleApplyActivity.this, "请填写问题描述", Toast.LENGTH_SHORT).show();
                }else if("".equals(nameStr)){
                    Toast.makeText(AfterSaleApplyActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                }else if("".equals(phoneNumberStr) ){
                    Toast.makeText(AfterSaleApplyActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if(!isMobile(phoneNumberStr)){
                    Toast.makeText(AfterSaleApplyActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else if(serviceType != -1 && !"".equals(describeStr) && !"".equals(nameStr) && !"".equals(phoneNumberStr)&&isMobile(phoneNumberStr)){
                    buildProgressDialog();

                    if(realPicUrlList.size()>0){
                        photo(realPicUrlList);

                        commit_view.setClickable(false);
                        commit_view.setTextColor(getResources().getColor(R.color.font_grey));

                        //
                        Net.get().uploadPhoto(partList).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(imgUploadModel -> {

                                    int code1 = imgUploadModel.getCode();
                                    if (code1 == 200) {

                                        picUrlList = imgUploadModel.getResultData();
                                        Net.get().applyCustomerService(serviceType,userId,orderId,describeStr,nameStr,phoneNumberStr,picUrlList).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(applyCustomerServiceModel -> {
                                                    //
                                                    int code = applyCustomerServiceModel.getCode();
                                                    if (code == 200) {
                                                        netCode = code;
                                                        clearHistorySearhPopu = new ClearHistorySearhPopu(AfterSaleApplyActivity.this,confirmClick,3);
                                                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.after_sale_apply_page), Gravity.CENTER, 0, 0);

                                                    } else {
                                                        commit_view.setClickable(true);
                                                        commit_view.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                                        Toast.makeText(AfterSaleApplyActivity.this, ""+ applyCustomerServiceModel.getMsg(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    cancelProgressDialog();
                                                }, throwable -> {
                                                    cancelProgressDialog();
                                                    Toast.makeText(AfterSaleApplyActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                                });


                                    } else {
                                        cancelProgressDialog();
                                        Toast.makeText(AfterSaleApplyActivity.this, ""+ imgUploadModel.getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                }, throwable -> {
                                    cancelProgressDialog();
                                    Toast.makeText(AfterSaleApplyActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                });

                    }
                    else{
                        Net.get().applyCustomerService(serviceType,userId,orderId,describeStr,nameStr,phoneNumberStr,picUrlList).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(applyCustomerServiceModel -> {
                                    cancelProgressDialog();
                                    int code = applyCustomerServiceModel.getCode();
                                    if (code == 200) {
                                        netCode = code;
                                        clearHistorySearhPopu = new ClearHistorySearhPopu(AfterSaleApplyActivity.this,confirmClick,3);
                                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.after_sale_apply_page), Gravity.CENTER, 0, 0);

                                    } else {
                                        commit_view.setClickable(true);
                                        commit_view.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                        Toast.makeText(AfterSaleApplyActivity.this, ""+ applyCustomerServiceModel.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }, throwable -> {
                                    cancelProgressDialog();
                                    Toast.makeText(AfterSaleApplyActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                });

                    }


                }

            }
        });

        //        setAdapter();

        describeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleLengthLimit();//edittext剩余字数处理方法
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    public View.OnClickListener confirmClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearHistorySearhPopu.dismiss();

            Intent intent = new Intent(AfterSaleApplyActivity.this,OrderDetailActivity.class);
            intent.putExtra("netCode",netCode);
            setResult(0,intent);

            Tools.finishAll();
//            finish();

        }

    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if(clearHistorySearhPopu != null ){
                clearHistorySearhPopu.dismiss();
                Intent intent = new Intent(AfterSaleApplyActivity.this,OrderDetailActivity.class);
                intent.putExtra("netCode",netCode);
                setResult(0,intent);

                Tools.finishAll();
            }else{
                Intent intent = new Intent(AfterSaleApplyActivity.this,OrderDetailActivity.class);
                intent.putExtra("netCode",netCode);
                setResult(0,intent);
                finish();
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 验证手机格式
     */
    public  boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    private void photo(List<String> list){
        partList = new MultipartBody.Part[10];

        String path;
        for (int i = 0; i <list.size() ; i++) {
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

    //RecyclerView设置适配器
    public void setAdapter(){
        //设置RecyclerView的布局方式
        CustomLanearLayoutManager clm = new CustomLanearLayoutManager(this);
        clm.setScrollEnabled(false);
        recyclerView.setLayoutManager(clm);
        //初始化适配器
        goodsListAdapter=new AfterSaleApplyGoodsListAdapter(R.layout.after_sale_detail_goods_list_item,orderDetailsBeanList);
        //设置适配器
        recyclerView.setAdapter(goodsListAdapter);
    }

    /**
     * 设置输入框的剩余字数提示信息
     */
    public void handleLengthLimit(){
        String s = describeView.getText().toString();
        int num = s.length();
        lengthLimitTextView.setText(num+"/500");
    }

    /**
     *  处理图片上传控件
     */
    public void handleImgPicker(){

        list = new ArrayList<>();

        pickerView.setImageLoaderInterface(new Loader());
        pickerView.setNewData(list);
        //展示有动画和无动画

        pickerView.setShowAnim(true);
        pickerView.setMaxNum(5);
        pickerView.setOneLineShowNum(5);
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
                if(ActivityCompat.checkSelfPermission(AfterSaleApplyActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(AfterSaleApplyActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){//没有授权
                    AndPermission.with(AfterSaleApplyActivity.this)
                            .requestCode(300)
                            .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .rationale(rationaleListener)
                            .callback(this)
                            .start();

                }else {
                    Matisse.from(AfterSaleApplyActivity.this)
                            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))//选择mime的类型
                            .countable(true)//设置从1开始的数字
                            .maxSelectable(remainNum + 1)//选择图片的最大数量限制
                            .capture(true)//启用相机
                            .captureStrategy(new CaptureStrategy(true, "com.jl.jlapp.publish.MyFileProvider"))//拍照的图片路径
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
                Intent intent = new Intent(AfterSaleApplyActivity.this,PhotoViewActivity.class);
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
        Toast.makeText(AfterSaleApplyActivity.this, "未授权", Toast.LENGTH_SHORT).show();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int i, final Rationale rationale) {
            // 自定义对话框。
//            AlertDialog.newBuilder(AfterSaleApplyActivity.this)
//                    .setTitle("请求权限")
//                    .setMessage("\"百度\"申请使用相机及图库权限，您是否允许？")
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
                pickerView.addData(new ImageBean(getRealFilePath(AfterSaleApplyActivity.this, uriList.get(0))));
            } else {
                List<ImageBean> list = new ArrayList<>();
                for (Uri uri : uriList) {
                    list.add(new ImageBean(getRealFilePath(AfterSaleApplyActivity.this, uri)));
                }
                pickerView.addData(list);
            }
        }
    }


    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
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


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.return_goods:
                backToDefaultStyle();
                return_goods.setBackgroundResource(R.drawable.btn_aftermarket_green);
                return_goods.setTextColor(getResources().getColor(R.color.checkGreenColor));
                serviceType = 0;
                break;
            case R.id.change_goods:
                backToDefaultStyle();
                change_goods.setBackgroundResource(R.drawable.btn_aftermarket_green);
                change_goods.setTextColor(getResources().getColor(R.color.checkGreenColor));
                serviceType = 1;

                break;
            case R.id.other:
                backToDefaultStyle();
                other.setBackgroundResource(R.drawable.btn_aftermarket_green);
                other.setTextColor(getResources().getColor(R.color.checkGreenColor));
                serviceType = 2 ;

                break;
        }
    }
    //设置事件
    private void setListener() {
        return_goods.setOnClickListener(this);
        change_goods.setOnClickListener(this);
        other.setOnClickListener(this);

    }
    //恢复默认样式
    private void backToDefaultStyle(){
        serviceType = -1;
        return_goods.setBackgroundResource(R.drawable.btn_aftermarket_gray);
        return_goods.setTextColor(getResources().getColor(R.color.font_grey));
        change_goods.setBackgroundResource(R.drawable.btn_aftermarket_gray);
        change_goods.setTextColor(getResources().getColor(R.color.font_grey));
        other.setBackgroundResource(R.drawable.btn_aftermarket_gray);
        other.setTextColor(getResources().getColor(R.color.font_grey));
    }

    public void getNetData(){
        if(orderId != -1 && userId != -1){
            Net.get().getOrderDetail(orderId,userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(orderDetail -> {
                        Log.d("aaaaaaaaaaaaaaa", "获取数据加载了");
                        int code1 = orderDetail.getCode();
                        if (code1 == 200) {
                            orderDetailsBeanList = orderDetail.getResultData().getOrderDetails();
                            if(orderDetailsBeanList.size()>0){
                                setAdapter();
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
            Intent intent = new Intent(AfterSaleApplyActivity.this,LoginActivity.class);
            startActivity(intent);
        }else if(orderId == -1){
            Toast.makeText(this, "页面传值没有接收到", Toast.LENGTH_SHORT).show();
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


}
