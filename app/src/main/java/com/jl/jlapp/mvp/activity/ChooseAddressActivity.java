package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.AddressListAdapter;
import com.jl.jlapp.eneity.AddressListModel;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fyf on 2018/1/15.
 */

public class ChooseAddressActivity extends AppCompatActivity {

    @BindView(R.id.rlv_address_list)
    RecyclerView rlvAddressList;

    public static OnClicktem onClicktem;

    public static void setOnClicktem(OnClicktem onClicktem) {
        ChooseAddressActivity.onClicktem = onClicktem;
    }

    List<ShipAddressModel.ResultDataBean> shipAddressResultDataBeans;
    int userId=0;
    int whichPage=0;//1表示从商品页跳来，2表示从订单填写页跳来

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        whichPage=intent.getIntExtra("whichPage",0);
        if (whichPage==0){
            Toast.makeText(this,"数据接收错误",Toast.LENGTH_SHORT);
        }
        //initView();
        //initDatas();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if(userId>0) {
            getShippingAddressByUserId();
        }
        else{
            Toast.makeText(this,"您还未登录，请先登录。",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick({R.id.return_btn,R.id.shipping_address_management})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.shipping_address_management:
                Intent intent=new Intent(ChooseAddressActivity.this,MyAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    /* private void initDatas() {
         AddressListModel model;
         model = new AddressListModel();
         for (int i = 0; i < 15; i++) {
             model.setName("柳亚婷");
             model.setPhone("1886023251999");
             model.setAddress("洛阳市洛龙区开元大道与学府街交叉口科大德园");
             listModels.add(model);
         }
     }
 */
    private void initView() {
        // listModels = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlvAddressList.setLayoutManager(layoutManager);
        AddressListAdapter listAdapter = new AddressListAdapter(R.layout.item_choose_address, shipAddressResultDataBeans);
        rlvAddressList.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView name = view.findViewById(R.id.tv_person_name);
                TextView phone = view.findViewById(R.id.tv_person_phone);
                TextView address = view.findViewById(R.id.tv_address_details);
                TextView isDefault = view.findViewById(R.id.tv_default);

                Intent intentResult = new Intent();
                intentResult.putExtra("name", name.getText().toString().trim());
                intentResult.putExtra("phone", phone.getText().toString().trim());
                intentResult.putExtra("address", address.getText().toString().trim());
                intentResult.putExtra("provinceCode", shipAddressResultDataBeans.get(position).getProvinceCode());
                intentResult.putExtra("cityCode", shipAddressResultDataBeans.get(position).getCityCode());
                intentResult.putExtra("countyCode",shipAddressResultDataBeans.get(position).getCountyCode());
                intentResult.putExtra("ringCode", shipAddressResultDataBeans.get(position).getRingCode());
                if (isDefault.getVisibility() == View.VISIBLE) {
                    intentResult.putExtra("isDefault", 1);
                } else {
                    intentResult.putExtra("isDefault", 0);
                }

                if (whichPage==1){
                    if (onClicktem!=null){
                        onClicktem.onClicktem(address.getText().toString().trim());
                    }
                }
                else{
                    setResult(1, intentResult);
                }


                finish();
            }
        });
    }

    //获取收货人地址信息
    public void getShippingAddressByUserId() {
        Net.get().getShippingAddressByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shipAddressModel -> {
                    if (shipAddressModel.getCode() == 200) {
                        shipAddressResultDataBeans = shipAddressModel.getResultData();
                        if (shipAddressResultDataBeans.size() > 0) {
                            initView();

                        } else {
                            Tools.addActivity(this);
                            Intent intent=new Intent(ChooseAddressActivity.this,MyAddressActivity.class);
                            startActivity(intent);
                            /*sendToRoundGrayThree.setVisibility(View.GONE);
                            goodsMsgChooseSendAddress.setText("暂无地址");*/
                        }
                    } else {
                        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
                    }


                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    public interface OnClicktem{
        void onClicktem(String address);
    }
}
