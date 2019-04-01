package com.jl.jlapp.mvp.activity;

import android.Manifest;
import android.app.Activity;
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
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ImageBean;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
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
 * Created by THINK on 2018-02-28.
 */

public class AddVatInvoicePicActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 233;

    @BindView(R.id.it_picker_view)
    ImageShowPickerView pickerView;

    @BindView(R.id.btn_submit)
    TextView btnSubmit;

    @BindView(R.id.return_btn)
    ImageView returnBtn;

    @BindView(R.id.title)
    TextView title;

    List<ImageBean> list;
    List<String> realPicUrlList = new ArrayList<>();
    List<String> picUrlList = new ArrayList<>();
    MultipartBody.Part[] partList = new MultipartBody.Part[10];
    String unitNameStr  ;
    String taxpayerIdentificationNumberStr ;
    String registeredAddressStr;
    String registeredTelStr ;
    String depositBankStr ;
    String bankAccountStr ;

    int userId = 0;
    int isUpdate = 0;
    int id = 0;
    //加载框
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vat_invoice_pic);
        ButterKnife.bind(this);
        Tools.addActivity(this);

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);

        Intent intent = getIntent();

        isUpdate = intent.getIntExtra("isUpdate",0);
        if(isUpdate == 1){
            id = intent.getIntExtra("id",0);
            title.setText("修改增票资质");
        }

        unitNameStr = intent.getStringExtra("unitName");
        taxpayerIdentificationNumberStr = intent.getStringExtra("taxpayerIdentificationNumber");
        registeredAddressStr = intent.getStringExtra("registeredAddress");
        registeredTelStr = intent.getStringExtra("registeredTel");
        depositBankStr = intent.getStringExtra("depositBank");
        bankAccountStr = intent.getStringExtra("bankAccount");
        handleImgPicker();
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
    }

    public void submitData(){

        if(realPicUrlList.size()>0){
            buildProgressDialog();
            photo(realPicUrlList);

            btnSubmit.setClickable(false);
            btnSubmit.setTextColor(getResources().getColor(R.color.font_grey));
            btnSubmit.setBackgroundResource(R.drawable.btn_gray_large);



            //
            Net.get().uploadPhoto(partList).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(imgUploadModel -> {

                        int code1 = imgUploadModel.getCode();
                        if (code1 == 200) {

                            picUrlList = imgUploadModel.getResultData();


                            if(isUpdate == 1){
                                if(id <= 0){
                                    Toast.makeText(AddVatInvoicePicActivity.this, "页面传值没有收到", Toast.LENGTH_SHORT).show();
                                }else{//修改
                                    Net.get().updateVatInvoiceAptitudeById(id,userId,unitNameStr,taxpayerIdentificationNumberStr,registeredAddressStr,registeredTelStr,depositBankStr,bankAccountStr,picUrlList).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(commitVatInvoiceAptitudeToCheckModel -> {
                                                //
                                                int code = commitVatInvoiceAptitudeToCheckModel.getCode();
                                                if (code == 200) {
                                                    cancelProgressDialog();
                                                    Intent intent = new Intent(AddVatInvoicePicActivity.this,AddVatInvoiceWaitCheckActivity.class);
                                                    intent.putExtra("isUpdate",isUpdate);
                                                    startActivity(intent);
                                                } else {
                                                    btnSubmit.setClickable(false);
                                                    btnSubmit.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                                    btnSubmit.setBackgroundResource(R.drawable.btn_green_large);
                                                    Toast.makeText(AddVatInvoicePicActivity.this, ""+ commitVatInvoiceAptitudeToCheckModel.getMsg(), Toast.LENGTH_SHORT).show();
                                                }
                                                cancelProgressDialog();
                                            }, throwable -> {
                                                cancelProgressDialog();
                                                Toast.makeText(AddVatInvoicePicActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                            });
                                }
                            }else{//添加
                                Net.get().commitVatInvoiceAptitudeToCheck(userId,unitNameStr,taxpayerIdentificationNumberStr,registeredAddressStr,registeredTelStr,depositBankStr,bankAccountStr,picUrlList).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(commitVatInvoiceAptitudeToCheckModel -> {
                                            //
                                            int code = commitVatInvoiceAptitudeToCheckModel.getCode();
                                            if (code == 200) {
                                                cancelProgressDialog();
                                                Intent intent = new Intent(AddVatInvoicePicActivity.this,AddVatInvoiceWaitCheckActivity.class);
                                                intent.putExtra("isUpdate",isUpdate);
                                                startActivity(intent);
                                            } else {
                                                btnSubmit.setClickable(false);
                                                btnSubmit.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                                btnSubmit.setBackgroundResource(R.drawable.btn_green_large);
                                                Toast.makeText(AddVatInvoicePicActivity.this, ""+ commitVatInvoiceAptitudeToCheckModel.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                            cancelProgressDialog();
                                        }, throwable -> {
                                            cancelProgressDialog();
                                            Toast.makeText(AddVatInvoicePicActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                        });
                            }





                        } else {
                            cancelProgressDialog();
                            Toast.makeText(AddVatInvoicePicActivity.this, ""+ imgUploadModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        cancelProgressDialog();
                        Toast.makeText(AddVatInvoicePicActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                    });

        } else{
            Toast.makeText(AddVatInvoicePicActivity.this, "请上传资质文件",Toast.LENGTH_SHORT).show();
        }

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
        pickerView.setMaxNum(1);
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
                if(ActivityCompat.checkSelfPermission(AddVatInvoicePicActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(AddVatInvoicePicActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){//没有授权
                    AndPermission.with(AddVatInvoicePicActivity.this)
                            .requestCode(300)
                            .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .rationale(rationaleListener)
                            .callback(this)
                            .start();

                }else {
                    Matisse.from(AddVatInvoicePicActivity.this)
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
                Intent intent = new Intent(AddVatInvoicePicActivity.this,PhotoViewActivity.class);
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
        Toast.makeText(AddVatInvoicePicActivity.this, "未授权", Toast.LENGTH_SHORT).show();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int i, final Rationale rationale) {
            // 自定义对话框。
//            AlertDialog.newBuilder(AddVatInvoicePicActivity.this)
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //            mSelected = Matisse.obtainResult(data);
            List<Uri> uriList = Matisse.obtainResult(data);
            if (uriList.size() == 1) {
                pickerView.addData(new ImageBean(getRealFilePath(AddVatInvoicePicActivity.this, uriList.get(0))));
            } else {
                List<ImageBean> list = new ArrayList<>();
                for (Uri uri : uriList) {
                    list.add(new ImageBean(getRealFilePath(AddVatInvoicePicActivity.this, uri)));
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
