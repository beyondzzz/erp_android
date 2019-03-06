package com.jl.jlapp.mvp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.common.eventbus.EventBus;
import com.jl.jlapp.R;
import com.jl.jlapp.mvp.base.BasePermActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.popu.PhotoPicPopupWindow;
import com.jl.jlapp.utils.FileUtil;
import com.jl.jlapp.utils.MimeTypeUtils;
import com.jl.jlapp.utils.PermissionTools;
import com.jl.jlapp.utils.ShareUtils;
import com.jl.jlapp.utils.Tools;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserMessageSettingsActivity extends BasePermActivity {

    @BindView(R.id.user_pic_rela)
    RelativeLayout userPicRela;
    @BindView(R.id.icon_return)
    ImageView iconReturn;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.account_number)
    TextView accountNumber;
    @BindView(R.id.user_name)
    TextView userNameTv;

    private PhotoPicPopupWindow picPopupWindow;
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";

    File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    String path = null;
    Drawable drawable;
    Bitmap photo;
    File fi;
    String picUrl = "";
    String mobile="";
    SharedPreferences sharedPreferences;
    int userId = 0;
    String userName="";

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.activity_user_message_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Tools.addActivity(this);
        sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        picUrl = sharedPreferences.getString(AppFinal.USERPICURL, "userPicUrl");
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        mobile=sharedPreferences.getString(AppFinal.USERPHONE,"");

        Tools.setCircleIcon(AppFinal.BASEURL + picUrl, ivAvatar);
        if(!"".equals(mobile)){
            accountNumber.setText(Tools.hidePhone(mobile));
        }
        else{
            accountNumber.setText("暂无");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userName=sharedPreferences.getString(AppFinal.USERNAME,"");
        userNameTv.setText(userName);
    }

    @OnClick({R.id.user_pic_rela, R.id.icon_return,R.id.user_name_settings_rela})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_pic_rela:
                Tools.isFastClick(1000);
                String[] need = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                requestRunTimePermissions(need, new PermissionCall() {
                    @Override
                    public void requestSuccess() {
                        picPopupWindow = new PhotoPicPopupWindow(UserMessageSettingsActivity.this, itemclick);
                        picPopupWindow.showAtLocation(UserMessageSettingsActivity.this.findViewById(R.id.my), Gravity.BOTTOM, 0, 0);
                    }

                    @Override
                    public void refused() {
                        // ToastUtils.showShortSafe("拒绝会导致拍照失败");
                        Toast.makeText(UserMessageSettingsActivity.this, "拒绝会导致拍照失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.icon_return:
                finish();
                break;
            case R.id.user_name_settings_rela:
                Intent intent=new Intent(UserMessageSettingsActivity.this,UserNameSettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.photographButton://拍照
                    Tools.isFastClick(1000);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                    }
                    if (PermissionTools.isCameraCanUse()) {
                        picPopupWindow.dismiss();
                        // 调用系统的拍照功能
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 指定调用相机拍照后照片的储存路径
                        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                        startActivityForResult(camera, PHOTO_GRAPH);
                    } else {
                        ActivityCompat.requestPermissions(UserMessageSettingsActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    break;
                case R.id.photoButton://从相册选取
                    Tools.isFastClick(1000);
                    picPopupWindow.dismiss();
                    // 调用系统的图库功能
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    // 指定打开相册中的各种文件。。
                    intent.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_UNSPECIFIED);
                    startActivityForResult(intent, PHOTO_ZOOM);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        if (requestCode == PHOTO_GRAPH) {
            // 设置文件保存路径
            File temp = new File(Environment.getExternalStorageDirectory()
                    + "/" + tempFile.getName());
            Log.d("useraaaaaa", "dadonActivityResult: " + temp);
            startPhotoZoom(Uri.fromFile(temp));
        }
        if (data == null) {
            return;
        }
        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
            //            getPath(data.getData());
        }
        if (requestCode == PHOTO_RESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                setPicToView(data);
                fi = new File(path);
                if (fi != null) {
                    RequestBody body = RequestBody.create(MediaType.parse(MimeTypeUtils.getMimeType(fi)), fi);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", fi.getName(), body);
                    upAvatar(part);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //上传头像以及修改用户头像信息
    private void upAvatar(MultipartBody.Part file) {
        MultipartBody.Part[] files = {file};
        //上传头像
        Net.get().uploadPhoto(files)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imgUploadModel -> {
                    if (imgUploadModel.getCode() == 200) {
                        //修改数据库中保存的用户头像信息
                        Net.get().updateUserPicUrl(userId,imgUploadModel.getResultData())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(imgUploadModel1 -> {
                                    if(imgUploadModel1.getCode()==200){
                                        Toast.makeText(UserMessageSettingsActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
                                        Tools.setCircleIcon(AppFinal.BASEURL + imgUploadModel.getResultData().get(0), ivAvatar);
                                        //通过sharedPreferences对象获取editor对象
                                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                        //存储键值对
                                        editor.putString(AppFinal.USERPICURL, imgUploadModel.getResultData().get(0));
                                        //提交
                                        editor.commit();//提交修改
                                        Log.d("useraaaaaaaaaaa", "BASEURL" + AppFinal.BASEURL + imgUploadModel.getResultData().get(0));
                                        refreshSDcard();
                                    }
                                    else{
                                        Toast.makeText(UserMessageSettingsActivity.this, "头像修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                }, throwable -> {
                                    Toast.makeText(UserMessageSettingsActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(UserMessageSettingsActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(UserMessageSettingsActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });

    }

    /**
     * 对图片进行剪裁
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            photo = extras.getParcelable("data");
            drawable = new BitmapDrawable(null, photo);
            //FileUtill里的37行需要改成自己项目的名字
            path = FileUtil.saveFile(UserMessageSettingsActivity.this, tempFile.getName(), photo);
            Tools.setCircleIcon(path, ivAvatar);
            // refreshSDcard();
        }
    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 刷新媒体库 若是在fragment中刷新会报错，必须在activity中刷新
     */
    private void refreshSDcard() {
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri imageUri = Uri.fromFile(fi);
        intentBc.setData(imageUri);
        sendBroadcast(intentBc);
    }
}
