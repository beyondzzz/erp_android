package com.jl.jlapp.mvp.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ImageBean;
import com.jl.jlapp.mvp.activity.AddVatInvoiceActivity;
import com.jl.jlapp.mvp.activity.AfterSaleApplyActivity;
import com.jl.jlapp.mvp.activity.EditEvaluationActivity;
import com.jl.jlapp.mvp.activity.PhotoViewActivity;
import com.jl.jlapp.utils.MimeTypeUtils;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by THINK on 2018-02-27.
 */

public class AddVatInvoicePicFragment extends Fragment {
    private static final int REQUEST_CODE_CHOOSE = 233;

    @BindView(R.id.it_picker_view)
    ImageShowPickerView pickerView;

    List<ImageBean> list;
    List<String> realPicUrlList = new ArrayList<>();

    MultipartBody.Part[] partList = new MultipartBody.Part[10];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_add_vat_invoice_pic, null, false);

        //控制注解
        ButterKnife.bind(this,view);
        handleImgPicker();

        return view;
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
        pickerView.setMaxNum(2);
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

                Matisse.from(getActivity())
                        .choose(MimeType.of(MimeType.JPEG,MimeType.PNG))//选择mime的类型
                        .countable(true)//设置从1开始的数字
                        .maxSelectable(remainNum + 1)//选择图片的最大数量限制
                        //                        .capture(true)//启用相机
                        //                        .captureStrategy(new CaptureStrategy(true,"com.gaosi.provider.MyFileProvider"))//自定义FileProvider
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//屏幕显示方向
                        .gridExpectedSize(300) // 列表中显示的图片大小
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(new PicassoEngine()) // 使用的图片加载引擎
                        .theme(R.style.Matisse_Dracula) // 黑色背景
                        .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码



                //                Toast.makeText(AfterSaleApplyActivity.this, "remainNum" + remainNum, Toast.LENGTH_SHORT).show();

                //                Toast.makeText(AfterSaleApplyActivity.this, "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
                //                //在listview或recyclerview才会使用这个list.add(),其他情况都不用
                //                list.add(new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg"));
                //                pickerView.addData(new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg"));
            }

            @Override
            public void picOnClickListener(List<ImageShowPickerBean> list, int position, int remainNum) {
                //                Toast.makeText(AfterSaleApplyActivity.this, list.size() + "========" + position + "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),PhotoViewActivity.class);
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
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
        Toast.makeText(getActivity(), "未授权", Toast.LENGTH_SHORT).show();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int i, final Rationale rationale) {
            // 自定义对话框。
//            AlertDialog.newBuilder(getActivity())
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
//        AddVatInvoiceActivity addVatInvoiceActivity  = (AddVatInvoiceActivity) getActivity();
//        AddVatInvoicePicFragment addVatInvoicePicFragment = new AddVatInvoicePicFragment();
//        addVatInvoiceActivity.replaceFragmentPage(addVatInvoicePicFragment);
//        addVatInvoiceActivity.replaceBgPic(2);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            //            mSelected = Matisse.obtainResult(data);
            List<Uri> uriList = Matisse.obtainResult(data);
            if (uriList.size() == 1) {
                pickerView.addData(new ImageBean(getRealFilePath(getActivity(), uriList.get(0))));
            } else {
                List<ImageBean> list = new ArrayList<>();
                for (Uri uri : uriList) {
                    list.add(new ImageBean(getRealFilePath(getActivity(), uri)));
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
}
