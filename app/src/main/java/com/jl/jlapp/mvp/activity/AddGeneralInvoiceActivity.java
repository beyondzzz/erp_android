package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.DataCleanManager;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddGeneralInvoiceActivity extends Activity {
    @BindView(R.id.et_unit_name)
    EditText etUnitName;
    @BindView(R.id.et_taxpayer_identification_number)
    EditText etTaxpayerIdentificationNumber;
    @BindView(R.id.page_title)
    TextView pageTitle;
    @BindView(R.id.add_page_top_del)
    TextView addPageTopDel;
    @BindView(R.id.save_general_invoice_linear)
    LinearLayout saveGeneralInvoiceLinear;

    int userId = 0;
    int whichButton = -1;//1表示编辑，0表示添加
    int unitId = 0;
    String unitName="";
    String taxpayerIdentificationNumber="";

    ClearHistorySearhPopu clearHistorySearhPopu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_general_invoice);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        whichButton = intent.getIntExtra("whichButton", -1);
        unitId = intent.getIntExtra("unitId", 0);
        unitName=intent.getStringExtra("unitName");
        taxpayerIdentificationNumber=intent.getStringExtra("taxpayerIdentificationNumber");
        if (whichButton == 0) {
            pageTitle.setText("新增公司信息");
            addPageTopDel.setVisibility(View.GONE);
        } else if (whichButton == 1 && unitId > 0&&!"".equals(unitName)&&!"".equals(taxpayerIdentificationNumber)) {
            pageTitle.setText("编辑公司信息");
            addPageTopDel.setVisibility(View.VISIBLE);
            etUnitName.setText(unitName);
            etTaxpayerIdentificationNumber.setText(taxpayerIdentificationNumber);
        } else {
            Toast.makeText(this, "页面传值错误", Toast.LENGTH_SHORT).show();
            saveGeneralInvoiceLinear.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if (userId <= 0) {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick({R.id.save_general_invoice_linear, R.id.add_page_top_del,R.id.return_btn})
    public void onClick(View view) {
        String newUnitName = etUnitName.getText().toString().trim();
        String newTaxpayerIdentificationNumber = etTaxpayerIdentificationNumber.getText().toString().trim();
        switch (view.getId()) {
            case R.id.save_general_invoice_linear:

                if ("".equals(newUnitName)) {
                    Toast.makeText(this, "请输入单位名称", Toast.LENGTH_SHORT).show();
                    break;
                }
                if ("".equals(newTaxpayerIdentificationNumber)) {
                    Toast.makeText(this, "请输入纳税人识别号", Toast.LENGTH_SHORT).show();
                    break;
                }
                //新增
                if (whichButton == 0) {
                    addInvoiceUnit(newUnitName, newTaxpayerIdentificationNumber, userId);
                }
                //修改
                else if (whichButton == 1) {
                    updateInvoiceUnitById(unitId, userId, newUnitName, newTaxpayerIdentificationNumber);
                }

                break;
            case R.id.add_page_top_del:
                clearHistorySearhPopu = new ClearHistorySearhPopu(this, itemclick, 8);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_unit_page), Gravity.CENTER, 0, 0);
                break;
            case R.id.return_btn:
                //新增
                if (whichButton == 0) {
                    if(!"".equals(newUnitName)||!"".equals(newTaxpayerIdentificationNumber)){
                        clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                finish();
                            }
                        }, 9);
                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_unit_page), Gravity.CENTER, 0, 0);
                    }
                    else{
                        finish();
                    }
                }
                //修改
                else if (whichButton == 1) {
                    if((!"".equals(newUnitName)&&!unitName.equals(newUnitName))||(!"".equals(newTaxpayerIdentificationNumber)&&!taxpayerIdentificationNumber.equals(newTaxpayerIdentificationNumber))){
                        clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                finish();
                            }
                        }, 9);
                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_unit_page), Gravity.CENTER, 0, 0);
                    }else{
                        finish();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            String newUnitName = etUnitName.getText().toString().trim();
            String newTaxpayerIdentificationNumber = etTaxpayerIdentificationNumber.getText().toString().trim();
            //新增
            if (whichButton == 0) {
                if(!"".equals(newUnitName)||!"".equals(newTaxpayerIdentificationNumber)){
                    clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearHistorySearhPopu.dismiss();
                            finish();
                        }
                    }, 9);
                    clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_unit_page), Gravity.CENTER, 0, 0);
                }
                else{
                    finish();
                }
            }
            //修改
            else if (whichButton == 1) {
                if((!"".equals(newUnitName)&&!unitName.equals(newUnitName))||(!"".equals(newTaxpayerIdentificationNumber)&&!taxpayerIdentificationNumber.equals(newTaxpayerIdentificationNumber))){
                    clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearHistorySearhPopu.dismiss();
                            finish();
                        }
                    }, 9);
                    clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_unit_page), Gravity.CENTER, 0, 0);
                }else{
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 删除的确认按钮事件
     */
    public View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearHistorySearhPopu.dismiss();
            deleteInvoiceUnitById(unitId, userId);
        }
    };

    //根据用户id添加发票抬头单位（普票）信息
    public void addInvoiceUnit(String unitName, String taxpayerIdentificationNumber, int userId) {
        Net.get().addInvoiceUnit(unitName, taxpayerIdentificationNumber, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() == 200) {
                        Toast.makeText(this, "添加成功!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //根据用户id和发票抬头单位信息id修改发票抬头单位（普票）信息
    public void updateInvoiceUnitById(int id, int userId, String unitName, String taxpayerIdentificationNumber) {
        Net.get().updateInvoiceUnitById(id, userId, unitName, taxpayerIdentificationNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() == 200) {
                        Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //根据用户id和发票抬头单位信息id修改发票抬头单位（普票）信息
    public void deleteInvoiceUnitById(int id, int userId) {
        Net.get().deleteInvoiceUnitById(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() == 200) {
                        Toast.makeText(this, "删除成功!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

}
