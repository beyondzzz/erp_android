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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 柳亚婷 on 2018/2/9 0009.
 */

public class EditEvaluationActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_CHOOSE = 233;
    //加载框
    private ProgressDialog progressDialog;
    @BindView(R.id.evaluation_star1)
    ImageView evaluationStar1;
    @BindView(R.id.evaluation_star2)
    ImageView evaluationStar2;
    @BindView(R.id.evaluation_star3)
    ImageView evaluationStar3;
    @BindView(R.id.evaluation_star4)
    ImageView evaluationStar4;
    @BindView(R.id.evaluation_star5)
    ImageView evaluationStar5;
    @BindView(R.id.edit_evaluation_content)
    EditText editEvaluationContent;
    @BindView(R.id.now_evaluation_world)
    TextView nowEvaluationWorld;
    @BindView(R.id.should_evaluation_goods_img)
    ImageView shouldEvaluationGoodsImg;


    @BindView(R.id.it_picker_view)
    ImageShowPickerView pickerView;
    List<ImageBean> list;
//    List<ImageBean> listForOnActResult = new ArrayList<>();
    List<String> realPicUrlList = new ArrayList<>();
    MultipartBody.Part[] partList = new MultipartBody.Part[10];
    List<String> picUrlList = new ArrayList<>();
    @BindView(R.id.evaluation_submit)
    TextView commit_view;

    List<ImageView> starImageView;



    int orderId = 0;
    int userId = 0;
    int orderDetailId = 0;
    String orderDetailPic="";
    int orderDetailNum=0;


    int score=5;//评分 , 默认五分好评
    String describeStr = "";
    ClearHistorySearhPopu clearHistorySearhPopu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.edit_evaluation_item);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        orderDetailId=intent.getIntExtra("orderDetailId",0);
        orderDetailPic=intent.getStringExtra("orderDetailPic");
        orderDetailNum=intent.getIntExtra("orderDetailNum",0);
        orderId = intent.getIntExtra("orderId",0);

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);



        //计算输入框的输入的文字个数
        countEditTextNum();
        setData();
        handleImgPicker();

    }



    public void setData(){
        starImageView=new ArrayList<>();
        starImageView.add(evaluationStar1);
        starImageView.add(evaluationStar2);
        starImageView.add(evaluationStar3);
        starImageView.add(evaluationStar4);
        starImageView.add(evaluationStar5);
        for (int i = 0; i < starImageView.size(); i++) {
            starImageView.get(i).setImageResource(R.drawable.star_yellow);
        }

        Glide
                .with(this)
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                .load(AppFinal.BASEURL + orderDetailPic)
                .into(shouldEvaluationGoodsImg);
    }

    //计算输入框的输入的文字个数
    public void countEditTextNum(){
        editEvaluationContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = editEvaluationContent.getText().toString();
                int num = s.length();
//                Log.d("aaaaaaaaa", "num:" + num);
                nowEvaluationWorld.setText(num + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    @OnClick({R.id.evaluation_star1,R.id.evaluation_star2,R.id.evaluation_star3,R.id.evaluation_star4,R.id.evaluation_star5,R.id.evaluation_submit,R.id.back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.evaluation_star1:
                score=1;
                for (int i = 0; i < starImageView.size(); i++) {
                    if (i < 1) {
                        starImageView.get(i).setImageResource(R.drawable.star_yellow);
                    } else {
                        starImageView.get(i).setImageResource(R.drawable.star_gray);
                    }
                }
                break;
            case R.id.evaluation_star2:
                score=2;
                for (int i = 0; i < starImageView.size(); i++) {
                    if (i < 2) {
                        starImageView.get(i).setImageResource(R.drawable.star_yellow);
                    } else {
                        starImageView.get(i).setImageResource(R.drawable.star_gray);
                    }

                }
                break;
            case R.id.evaluation_star3:
                score=3;
                for (int i = 0; i < starImageView.size(); i++) {
                    if (i < 3) {
                        starImageView.get(i).setImageResource(R.drawable.star_yellow);
                    } else {
                        starImageView.get(i).setImageResource(R.drawable.star_gray);
                    }

                }
                break;
            case R.id.evaluation_star4:
                score=4;
                for (int i = 0; i < starImageView.size(); i++) {
                    if (i < 4) {
                        starImageView.get(i).setImageResource(R.drawable.star_yellow);
                    } else {
                        starImageView.get(i).setImageResource(R.drawable.star_gray);
                    }

                }
                break;
            case R.id.evaluation_star5:
                score=5;
                for (int i = 0; i < starImageView.size(); i++) {
                    starImageView.get(i).setImageResource(R.drawable.star_yellow);
                }
                break;

            case R.id.evaluation_submit:

                submitData();

                break;

            case R.id.back:
                String descript = editEvaluationContent.getText().toString().trim();
                if(score != 0 || (!"".equals(descript)) || realPicUrlList.size()>0){
                    clearHistorySearhPopu = new ClearHistorySearhPopu(EditEvaluationActivity.this,  new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearHistorySearhPopu.dismiss();
                            finish();
                        }
                    }, 9);
                    clearHistorySearhPopu.showAtLocation(findViewById(R.id.edit_evaluation_page), Gravity.CENTER, 0, 0);
                }else{
                    finish();
                }

                break;


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            String descript = editEvaluationContent.getText().toString().trim();
            if(score != 0 || (!"".equals(descript)) || realPicUrlList.size()>0){
                clearHistorySearhPopu = new ClearHistorySearhPopu(EditEvaluationActivity.this,  new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearHistorySearhPopu.dismiss();
                        finish();
                    }
                }, 9);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.edit_evaluation_page), Gravity.CENTER, 0, 0);
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
                if(ActivityCompat.checkSelfPermission(EditEvaluationActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(EditEvaluationActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){//没有授权
                    AndPermission.with(EditEvaluationActivity.this)
                            .requestCode(300)
                            .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .rationale(rationaleListener)
                            .callback(this)
                            .start();

                }else {
                    Matisse.from(EditEvaluationActivity.this)
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
                Intent intent = new Intent(EditEvaluationActivity.this,PhotoViewActivity.class);
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
        Toast.makeText(EditEvaluationActivity.this, "未授权", Toast.LENGTH_SHORT).show();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int i, final Rationale rationale) {
            // 自定义对话框。
//            AlertDialog.newBuilder(EditEvaluationActivity.this)
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
                pickerView.addData(new ImageBean(getRealFilePath(EditEvaluationActivity.this, uriList.get(0))));
            } else {
                List<ImageBean> list = new ArrayList<>();
                for (Uri uri : uriList) {
                    list.add(new ImageBean(getRealFilePath(EditEvaluationActivity.this, uri)));
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

    private void submitData(){
        buildProgressDialog();
        describeStr = editEvaluationContent.getText().toString().trim();
//        phoneNumberStr = phone_number.getText().toString().trim();
//        nameStr = name.getText().toString().toString().trim();
        if(userId <= 0){
            Toast.makeText(EditEvaluationActivity.this, "内部错误", Toast.LENGTH_SHORT).show();
            cancelProgressDialog();
        }else if(score <= 0){
            Toast.makeText(EditEvaluationActivity.this, "请评分", Toast.LENGTH_SHORT).show();
            cancelProgressDialog();

        }else if("".equals(describeStr)){
            Toast.makeText(EditEvaluationActivity.this, "请填写商品评价", Toast.LENGTH_SHORT).show();
            cancelProgressDialog();

        }else if(orderDetailId<=0){
            Toast.makeText(EditEvaluationActivity.this, "页面传值没收到", Toast.LENGTH_SHORT).show();
            cancelProgressDialog();

        }else if(orderDetailId>0 && userId > 0 && score > 0 && !"".equals(describeStr)){
            Tools.addActivity(this);
            if (realPicUrlList.size()>0) {
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
                                Net.get().commitEvaluation(orderDetailId,userId,describeStr,score,picUrlList).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(evaluationSubmitResultModel -> {
                                            cancelProgressDialog();
                                            int code = evaluationSubmitResultModel.getCode();
                                            if (code == 200) {


                                                Intent intent  = new Intent(EditEvaluationActivity.this,EvalSubmitSuccessActivity.class);
                                                intent.putExtra("orderDetailPic",orderDetailPic);
                                                intent.putExtra("orderDetailNum",orderDetailNum);
                                                intent.putExtra("orderId",orderId);

                                                intent.putExtra("id",evaluationSubmitResultModel.getResultData().getId());
                                                startActivity(intent);

                                            } else {
                                                commit_view.setClickable(true);
                                                commit_view.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                                Toast.makeText(EditEvaluationActivity.this, ""+ evaluationSubmitResultModel.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        }, throwable -> {
                                            cancelProgressDialog();
                                            Toast.makeText(EditEvaluationActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                        });


                            } else {
                                cancelProgressDialog();
                                Toast.makeText(EditEvaluationActivity.this, ""+ imgUploadModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }, throwable -> {
                            cancelProgressDialog();
                            Toast.makeText(EditEvaluationActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                        });
            }
            else{
                picUrlList = new ArrayList<>();
                Net.get().commitEvaluation(orderDetailId,userId,describeStr,score,picUrlList).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(evaluationSubmitResultModel -> {
                            cancelProgressDialog();
                            int code = evaluationSubmitResultModel.getCode();
                            if (code == 200) {


                                Intent intent  = new Intent(EditEvaluationActivity.this,EvalSubmitSuccessActivity.class);
                                intent.putExtra("orderDetailPic",orderDetailPic);
                                intent.putExtra("orderDetailNum",orderDetailNum);
                                intent.putExtra("orderId",orderId);

                                intent.putExtra("id",evaluationSubmitResultModel.getResultData().getId());
                                startActivity(intent);

                            } else {
                                commit_view.setClickable(true);
                                commit_view.setTextColor(getResources().getColor(R.color.checkGreenColor));
                                Toast.makeText(EditEvaluationActivity.this, "请评分", Toast.LENGTH_SHORT).show();
                                cancelProgressDialog();
                                Toast.makeText(EditEvaluationActivity.this, ""+ evaluationSubmitResultModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {
                            cancelProgressDialog();
                            Toast.makeText(EditEvaluationActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                        });
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
