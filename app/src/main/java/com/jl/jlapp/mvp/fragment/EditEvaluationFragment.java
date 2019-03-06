package com.jl.jlapp.mvp.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ImageBean;
import com.jl.jlapp.eneity.OrderDetail;
import com.jl.jlapp.eneity.OrderTable;
import com.jl.jlapp.mvp.activity.AfterSaleApplyActivity;
import com.jl.jlapp.mvp.activity.EvaluationActivity;
import com.jl.jlapp.mvp.activity.MyOrderListActivity;
import com.jl.jlapp.mvp.activity.PayOrderActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class EditEvaluationFragment extends Fragment {
    private static final int REQUEST_CODE_CHOOSE = 233;
    public static final int RESULT_OK = -1;

  /*  @BindView(R.id.edit_evaluation_content_recycler_view)
    RecyclerView editEvaluationContentRecyclerView;*/

    EvaluationActivity evaluationActivity;



    View view;
    List<Integer> goodsList;

    OrderDetail.ResultDataBean resultDataBean;

    int orderId = 0;

    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        evaluationActivity = (EvaluationActivity) getActivity();
        this.orderId = evaluationActivity.orderId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // view = inflater.inflate(R.layout.fragment_edit_evaluation, container, false);
        view = inflater.inflate(R.layout.edit_evaluation_item, container, false);
        ButterKnife.bind(this, view);
        /*if(orderId>0){
            getOrderDetail(22,orderId);
        }else{
            Toast.makeText(getContext(),"数据错误",Toast.LENGTH_SHORT).show();
        }
*/

        return view;
    }

    /*public void setAdapter() {
        editEvaluationContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        editEvaluationContentRecyclerView.setAdapter(editEvaluationAdapter);
    }*/

   /* public void getData() {
        goodsList = new ArrayList<>();
        goodsList.add(R.drawable.img_steamed);
        goodsList.add(R.drawable.img_dessert);
        goodsList.add(R.drawable.img_steamed);
    }*/

    /*public void getOrderDetail(Integer userId, Integer orderId) {
        Net.get().getOrderDetail(orderId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderDetail -> {
                    if(orderDetail.getCode()==200){
                        resultDataBean=orderDetail.getResultData();

                        setAdapter();
                    }
                    else{
                        Toast.makeText(getContext(), orderDetail.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }*/
}
