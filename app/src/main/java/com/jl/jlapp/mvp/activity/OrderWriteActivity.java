package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.CompanyListAdapter;
import com.jl.jlapp.eneity.CompanyListModel;
import com.jl.jlapp.eneity.InvoiceUnitAndVatInvoiceAptitude;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.popu.InvoiceAndCouponPromptPopu;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lyt on 2018/1/15.
 * 订单填写---发票选择
 */

public class OrderWriteActivity extends AppCompatActivity {


    @BindView(R.id.rb_ordinary_invoice)
    RadioButton rbOrdinaryInvoice;
    @BindView(R.id.rb_added_invoice)
    RadioButton rbAddedInvoice;
    @BindView(R.id.rb_no_invoice)
    RadioButton rbNoInvoice;
    @BindView(R.id.rb_personal)
    RadioButton rbPersonal;
    @BindView(R.id.rb_company)
    RadioButton rbCompany;
    @BindView(R.id.rb_detailed)
    RadioButton rbDetailed;
    @BindView(R.id.rb_foods)
    RadioButton rbFoods;
    @BindView(R.id.rlv_company_list)
    RecyclerView rlvCompanyList;
    @BindView(R.id.tv_add_company)
    TextView tvAddCompany;
    @BindView(R.id.invoice_must_know_message)
    TextView invoiceMustKnowMessage;
    @BindView(R.id.add_unit_name_world_rela)
    RelativeLayout addUnitNameWorldRela;
    @BindView(R.id.add_taxpayer_identification_number_world_rela)
    RelativeLayout addTaxpayerIdentificationNumberWorldRela;
    @BindView(R.id.tv_save_company)
    TextView tvSaveCompany;
    @BindView(R.id.invoice_looked_up_type)
    RelativeLayout invoiceLookedUpType;
    @BindView(R.id.rl_second_vate_invoice_msg)
    RelativeLayout rlSecondVateInvoiceMsg;
    @BindView(R.id.rl_second)
    RelativeLayout rlSecond;
    @BindView(R.id.invoice_content_rela)
    RelativeLayout invoiceContentRela;
    @BindView(R.id.rl_second_no_vate_invoice)
    RelativeLayout rlSecondNoVateInvoice;
    @BindView(R.id.vat_invoice_unit_content)
    TextView vatInvoiceUnitContent;
    @BindView(R.id.taxpayer_identification_number)
    TextView taxpayerIdentificationNumber;
    @BindView(R.id.registered_address)
    TextView registeredAddress;
    @BindView(R.id.registered_tel)
    TextView registeredTel;
    @BindView(R.id.deposit_bank)
    TextView depositBank;
    @BindView(R.id.bank_account)
    TextView bankAccount;
    @BindView(R.id.edit_unit_name)
    TextView editUnitName;
    @BindView(R.id.edit_taxpayer_identification_number)
    TextView editTaxpayerIdentificationNumber;
    @BindView(R.id.rl_second_no_vate_invoice_text)
    TextView rlSecondNoVateInvoiceText;

    InvoiceUnitAndVatInvoiceAptitude.ResultDataBean resultDataBean;
    private List<InvoiceUnitAndVatInvoiceAptitude.ResultDataBean.InvoiceUnitBean> invoiceUnitBeans;
    private InvoiceUnitAndVatInvoiceAptitude.ResultDataBean.VatInvoiceAptitudeBean vatInvoiceAptitudeBean;
    private CompanyListAdapter listAdapter;
    private InvoiceAndCouponPromptPopu invoiceAndCouponPromptPopu;
    public static int index = 0;
    int chooseInvoiceType = 2;//发票类型，默认为普通发票，0：普通发票 1：增值税专用发票 2：不开发票
    int chooseInvoiceLookedUpType = 1;//发票抬头，默认为个人  1：个人 0：单位
    int chooseInvoiceContentDetail = 1;//发票明细，默认为明细 1：明细 0：食品

    int userId = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_write);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if (userId > 0) {
            Intent intent = getIntent();
            chooseInvoiceType = intent.getIntExtra("chooseInvoiceType", 2);
            if (chooseInvoiceType == 0) {
                rbOrdinaryInvoice.setTextColor(getResources().getColor(R.color.checkGreenColor));
                rbAddedInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbNoInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                chooseInvoiceLookedUpType = intent.getIntExtra("chooseInvoiceLookedUpType", 1);
                chooseInvoiceContentDetail = intent.getIntExtra("chooseInvoiceContentDetail", 1);
                if (chooseInvoiceLookedUpType == 1) {
                    rbPersonal.setChecked(true);
                } else {
                    rbCompany.setChecked(true);
                    tvAddCompany.setVisibility(View.VISIBLE);
                    rlvCompanyList.setVisibility(View.VISIBLE);
                }
                if (chooseInvoiceContentDetail == 1) {
                    rbDetailed.setChecked(true);
                } else {
                    rbFoods.setChecked(true);
                }
            } else if (chooseInvoiceType == 1) {
                chooseInvoiceContentDetail = intent.getIntExtra("chooseInvoiceContentDetail", 1);
                if (chooseInvoiceContentDetail == 1) {
                    rbDetailed.setChecked(true);
                } else {
                    rbFoods.setChecked(true);
                }
                rbAddedInvoice.setChecked(true);
                rbOrdinaryInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbAddedInvoice.setTextColor(getResources().getColor(R.color.checkGreenColor));
                rbNoInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                invoiceLookedUpType.setVisibility(View.GONE);
                rlSecondVateInvoiceMsg.setVisibility(View.VISIBLE);
                rlSecond.setVisibility(View.VISIBLE);
                invoiceContentRela.setVisibility(View.VISIBLE);
                rlSecondNoVateInvoice.setVisibility(View.GONE);
            } else {
                rbNoInvoice.setChecked(true);
                rbOrdinaryInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbAddedInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbNoInvoice.setTextColor(getResources().getColor(R.color.checkGreenColor));
                rlSecond.setVisibility(View.GONE);
                invoiceContentRela.setVisibility(View.GONE);
            }

            //获取发票抬头单位信息以及增票信息
            getInvoiceUnitAndVatInvoiceAptitudeByUserId(userId);

        } else {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick({R.id.rb_ordinary_invoice, R.id.rb_added_invoice, R.id.rb_no_invoice, R.id.rb_personal, R.id.rb_company, R.id.return_btn,
            R.id.tv_add_company, R.id.tv_save_company, R.id.btn_ok, R.id.rb_detailed, R.id.rb_foods,R.id.invoice_must_know_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_ordinary_invoice:
                chooseInvoiceType = 0;
                rbOrdinaryInvoice.setTextColor(getResources().getColor(R.color.checkGreenColor));
                rbAddedInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbNoInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                invoiceLookedUpType.setVisibility(View.VISIBLE);
                rlSecondVateInvoiceMsg.setVisibility(View.GONE);
                rlSecond.setVisibility(View.VISIBLE);
                invoiceContentRela.setVisibility(View.VISIBLE);
                rlSecondNoVateInvoice.setVisibility(View.GONE);
                break;
            case R.id.rb_added_invoice:
                chooseInvoiceType = 1;
                rbOrdinaryInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbAddedInvoice.setTextColor(getResources().getColor(R.color.checkGreenColor));
                rbNoInvoice.setTextColor(getResources().getColor(R.color.trans_333333));

                if (vatInvoiceAptitudeBean != null) {
                    //审核通过
                    if(vatInvoiceAptitudeBean.getState()==2){
                        invoiceLookedUpType.setVisibility(View.GONE);
                        rlSecondVateInvoiceMsg.setVisibility(View.VISIBLE);
                        rlSecond.setVisibility(View.VISIBLE);
                        invoiceContentRela.setVisibility(View.VISIBLE);
                        rlSecondNoVateInvoice.setVisibility(View.GONE);
                    }
                    //审核不通过
                    else if (vatInvoiceAptitudeBean.getState()==1){
                        rlSecondNoVateInvoice.setVisibility(View.VISIBLE);
                        invoiceLookedUpType.setVisibility(View.GONE);
                        rlSecondVateInvoiceMsg.setVisibility(View.GONE);
                        rlSecond.setVisibility(View.VISIBLE);
                        invoiceContentRela.setVisibility(View.GONE);
                        rlSecondNoVateInvoiceText.setText("增票资质审核未通过");
                    }
                    //待审核
                    else{
                        rlSecondNoVateInvoice.setVisibility(View.VISIBLE);
                        invoiceLookedUpType.setVisibility(View.GONE);
                        rlSecondVateInvoiceMsg.setVisibility(View.GONE);
                        rlSecond.setVisibility(View.VISIBLE);
                        invoiceContentRela.setVisibility(View.GONE);
                        rlSecondNoVateInvoiceText.setText("增票资质审核中");
                    }

                } else {
                    rlSecondNoVateInvoice.setVisibility(View.VISIBLE);
                    invoiceLookedUpType.setVisibility(View.GONE);
                    rlSecondVateInvoiceMsg.setVisibility(View.GONE);
                    rlSecond.setVisibility(View.VISIBLE);
                    invoiceContentRela.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_no_invoice:
                chooseInvoiceType = 2;
                rbOrdinaryInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbAddedInvoice.setTextColor(getResources().getColor(R.color.trans_333333));
                rbNoInvoice.setTextColor(getResources().getColor(R.color.checkGreenColor));
                rlSecond.setVisibility(View.GONE);
                invoiceContentRela.setVisibility(View.GONE);
                break;
            case R.id.rb_personal:
                chooseInvoiceLookedUpType = 1;
                tvAddCompany.setVisibility(View.GONE);
                rlvCompanyList.setVisibility(View.GONE);
                addUnitNameWorldRela.setVisibility(View.GONE);
                addTaxpayerIdentificationNumberWorldRela.setVisibility(View.GONE);
                tvSaveCompany.setVisibility(View.GONE);
                tvAddCompany.setVisibility(View.GONE);
                break;

            case R.id.rb_company:
                chooseInvoiceLookedUpType = 0;
                addUnitNameWorldRela.setVisibility(View.GONE);
                addTaxpayerIdentificationNumberWorldRela.setVisibility(View.GONE);
                tvSaveCompany.setVisibility(View.GONE);
                tvAddCompany.setVisibility(View.VISIBLE);

                if (invoiceUnitBeans.size() > 0) {
                    //该用户原本就有公司信息
                    tvAddCompany.setVisibility(View.VISIBLE);
                    rlvCompanyList.setVisibility(View.VISIBLE);
                } else {
                    //无公司信息
                    addUnitNameWorldRela.setVisibility(View.VISIBLE);
                    addTaxpayerIdentificationNumberWorldRela.setVisibility(View.VISIBLE);
                    tvSaveCompany.setVisibility(View.VISIBLE);
                    tvAddCompany.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_add_company:
                addUnitNameWorldRela.setVisibility(View.VISIBLE);
                addTaxpayerIdentificationNumberWorldRela.setVisibility(View.VISIBLE);
                tvSaveCompany.setVisibility(View.VISIBLE);
                tvAddCompany.setVisibility(View.GONE);
                break;
            case R.id.tv_save_company:
                String unitName = editUnitName.getText().toString().trim();
                String taxpayerIdentificationNumber = editTaxpayerIdentificationNumber.getText().toString().trim();
                if ("".equals(unitName)) {
                    Toast.makeText(this, "单位名称不能为空!", Toast.LENGTH_SHORT).show();
                    break;
                }
                if ("".equals(taxpayerIdentificationNumber)) {
                    Toast.makeText(this, "纳税人识别号不能为空!", Toast.LENGTH_SHORT).show();
                    break;
                }
                addInvoiceUnit(unitName, taxpayerIdentificationNumber, userId);
                break;
            case R.id.rb_detailed:
                chooseInvoiceContentDetail = 1;
                break;
            case R.id.rb_foods:
                chooseInvoiceContentDetail = 0;
                break;
            case R.id.btn_ok:
                Intent intent = new Intent();
                //选择发票的类型
                intent.putExtra("chooseInvoiceType", chooseInvoiceType);
                //普票
                if (chooseInvoiceType == 0) {
                    intent.putExtra("chooseInvoiceLookedUpType", chooseInvoiceLookedUpType);
                    intent.putExtra("chooseInvoiceContentDetail", chooseInvoiceContentDetail);
                    //单位
                    if (chooseInvoiceLookedUpType == 0) {
                        if (invoiceUnitBeans.size() > 0) {
                            intent.putExtra("unitName", invoiceUnitBeans.get(index).getUnitName());
                            intent.putExtra("taxpayerIdentificationNumber", invoiceUnitBeans.get(index).getTaxpayerIdentificationNumber());
                        } else {
                            Toast.makeText(this, "暂无单位信息，请添加后再点击确认!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else if (chooseInvoiceType == 1) {
                    intent.putExtra("chooseInvoiceContentDetail", chooseInvoiceContentDetail);
                    if (vatInvoiceAptitudeBean != null) {
                        //Log.d("vatInvoiceAptitudeBeanaaa","状态："+vatInvoiceAptitudeBean.getState());
                        //审核通过
                        if(vatInvoiceAptitudeBean.getState()==2){
                            intent.putExtra("unitName", vatInvoiceAptitudeBean.getUnitName());
                            intent.putExtra("taxpayerIdentificationNumber", vatInvoiceAptitudeBean.getTaxpayerIdentificationNumber());
                            intent.putExtra("registeredAddress", vatInvoiceAptitudeBean.getRegisteredAddress());
                            intent.putExtra("registeredTel", vatInvoiceAptitudeBean.getRegisteredTel());
                            intent.putExtra("depositBank", vatInvoiceAptitudeBean.getDepositBank());
                            intent.putExtra("bankAccount", vatInvoiceAptitudeBean.getBankAccount());
                            intent.putExtra("businessLicenseUrl", vatInvoiceAptitudeBean.getBusinessLicenseUrl());
                        }
                        //待审核
                        else if(vatInvoiceAptitudeBean.getState()==0){
                            Toast.makeText(this, "增票信息审核中，请耐心等待!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //审核未通过
                        else{
                            Toast.makeText(this, "增票信息审核未通过，请到个人中心进行修改!", Toast.LENGTH_SHORT).show();
                            break;
                        }

                    } else {
                        Toast.makeText(this, "暂无增票信息，请到个人中心进行添加!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                setResult(2, intent);
                finish();
                break;
            case R.id.invoice_must_know_message:
                invoiceAndCouponPromptPopu = new InvoiceAndCouponPromptPopu(this, 0);
                invoiceAndCouponPromptPopu.showAtLocation(findViewById(R.id.invoice_page), Gravity.CENTER, 0, 0);
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    private void initView(InvoiceUnitAndVatInvoiceAptitude.ResultDataBean resultDataBean) {
        if (invoiceUnitBeans == null) {
            invoiceUnitBeans = new ArrayList<>();
        }
        invoiceUnitBeans.clear();
        for (int i = 0; i < resultDataBean.getInvoiceUnit().size(); i++) {
            invoiceUnitBeans.add(resultDataBean.getInvoiceUnit().get(i));
        }
        if (listAdapter == null) {
            listAdapter = new CompanyListAdapter(R.layout.item_company_list, invoiceUnitBeans);
            CustomLanearLayoutManager lanearLayoutManager = new CustomLanearLayoutManager(this);
            lanearLayoutManager.setScrollEnabled(false);
            rlvCompanyList.setLayoutManager(lanearLayoutManager);
            rlvCompanyList.setAdapter(listAdapter);

            listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.rb_company_item:
                            index = position;
                            listAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            });
        } else {
            listAdapter.notifyDataSetChanged();
        }


    }

    private void setDataTovatInvoiceAptitudeControl(InvoiceUnitAndVatInvoiceAptitude.ResultDataBean.VatInvoiceAptitudeBean vatInvoiceAptitudeBean) {
        //审核通过
        if(vatInvoiceAptitudeBean.getState()==2){
            vatInvoiceUnitContent.setText(vatInvoiceAptitudeBean.getUnitName());
            taxpayerIdentificationNumber.setText(vatInvoiceAptitudeBean.getTaxpayerIdentificationNumber());
            registeredAddress.setText(vatInvoiceAptitudeBean.getRegisteredAddress());
            registeredTel.setText(vatInvoiceAptitudeBean.getRegisteredTel());
            depositBank.setText(vatInvoiceAptitudeBean.getDepositBank());
            bankAccount.setText(vatInvoiceAptitudeBean.getBankAccount());
        }


    }

    //根据用户id获取该用户的发票抬头单位信息以及增票信息
    public void getInvoiceUnitAndVatInvoiceAptitudeByUserId(Integer userId) {
        Net.get().getInvoiceUnitAndVatInvoiceAptitudeByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(invoiceUnitAndVatInvoiceAptitude -> {
                    if (invoiceUnitAndVatInvoiceAptitude.getCode() == 200) {
                        resultDataBean = invoiceUnitAndVatInvoiceAptitude.getResultData();
                        //给增值税专用发票信息赋值
                        if (invoiceUnitAndVatInvoiceAptitude.getResultData().getVatInvoiceAptitude() != null) {
                            vatInvoiceAptitudeBean = invoiceUnitAndVatInvoiceAptitude.getResultData().getVatInvoiceAptitude();
                            setDataTovatInvoiceAptitudeControl(vatInvoiceAptitudeBean);
                        }
                        //给发票抬头单位赋值
                        initView(resultDataBean);
                    } else {
                        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //根据用户id添加发票抬头单位（普票）信息
    public void addInvoiceUnit(String unitName, String taxpayerIdentificationNumber, int userId) {
        Net.get().addInvoiceUnit(unitName, taxpayerIdentificationNumber, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() == 200) {
                        Toast.makeText(this, "添加成功!", Toast.LENGTH_SHORT).show();
                        getInvoiceUnitAndVatInvoiceAptitudeByUserId(userId);
                        addUnitNameWorldRela.setVisibility(View.GONE);
                        addTaxpayerIdentificationNumberWorldRela.setVisibility(View.GONE);
                        tvSaveCompany.setVisibility(View.GONE);
                        tvAddCompany.setVisibility(View.VISIBLE);
                        tvAddCompany.setVisibility(View.VISIBLE);
                        rlvCompanyList.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(this, postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


}
