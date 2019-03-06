package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.mvp.fragment.InvoiceFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.connection.StreamAllocation;

/**
 * Created by fyf on 2018/1/16.
 */

public class AddVatInvoiceActivity extends AppCompatActivity {


    @BindView(R.id.progress_bar)
    ImageView progressBar;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.rb_agree)
    CheckBox rbAgree;
    @BindView(R.id.tv_confirm_book)
    TextView tvConfirmBook;

    @BindView(R.id.unitName)
    EditText unitName;
    @BindView(R.id.taxpayerIdentificationNumber)
    EditText taxpayerIdentificationNumber;
    @BindView(R.id.registeredAddress)
    EditText registeredAddress;
    @BindView(R.id.registeredTel)
    EditText registeredTel;
    @BindView(R.id.depositBank)
    EditText depositBank;
    @BindView(R.id.bankAccount)
    EditText bankAccount;
    @BindView(R.id.return_btn)
    ImageView returnBtn;

    @BindView(R.id.title)
    TextView title;
    int isUpdate = 0;//默认为添加
    int id =0;//增票资质id

    String unitNameStr,newUnitNameStr;
    String taxpayerIdentificationNumberStr,newTaxpayerIdentificationNumberStr;
    String registeredAddressStr,newRegisteredAddressStr ;
    String registeredTelStr,newRegisteredTelStr;
    String depositBankStr,newDepositBankStr;
    String bankAccountStr,newBankAccountStr;
    ClearHistorySearhPopu clearHistorySearhPopu = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vat_invoice);
        ButterKnife.bind(this);
        Tools.addActivity(this);
        Intent intent = getIntent();
        isUpdate = intent.getIntExtra("isUpdate",0);
        if(isUpdate == 1){//修改
            title.setText("修改增票资质");

            unitNameStr = intent.getStringExtra("unitName");
            taxpayerIdentificationNumberStr = intent.getStringExtra("taxpayerIdentificationNumber");
            registeredAddressStr = intent.getStringExtra("registeredAddress");
            registeredTelStr = intent.getStringExtra("registeredTel");
            depositBankStr = intent.getStringExtra("depositBank");
            bankAccountStr = intent.getStringExtra("bankAccount");

            id = intent.getIntExtra("id",0);

            unitName.setText(unitNameStr);
            taxpayerIdentificationNumber.setText(taxpayerIdentificationNumberStr);
            registeredAddress.setText(registeredAddressStr);
            registeredTel.setText(registeredTelStr);
            depositBank.setText(depositBankStr);
            bankAccount.setText(bankAccountStr);
        }

        handleTips();
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUnitNameStr = unitName.getText().toString().trim();
                newTaxpayerIdentificationNumberStr= taxpayerIdentificationNumber.getText().toString().trim();
                newRegisteredAddressStr = registeredAddress.getText().toString().toString().trim();
                newRegisteredTelStr = registeredTel.getText().toString().trim();
                newDepositBankStr = depositBank.getText().toString().trim();
                newBankAccountStr = bankAccount.getText().toString().toString().trim();

                if(isUpdate == 1){//修改
                    if((!"".equals(newUnitNameStr)&&!unitNameStr.equals(newUnitNameStr))||(!"".equals(newTaxpayerIdentificationNumberStr)&&!taxpayerIdentificationNumberStr.equals(newTaxpayerIdentificationNumberStr))||(!"".equals(newRegisteredAddressStr)&&!registeredAddressStr.equals(newRegisteredAddressStr))||(!"".equals(newRegisteredTelStr)&&!registeredTelStr.equals(newRegisteredTelStr))||(!"".equals(newDepositBankStr)&&!depositBankStr.equals(newDepositBankStr))||(!"".equals(newBankAccountStr)&&!bankAccountStr.equals(newBankAccountStr))){
                        clearHistorySearhPopu = new ClearHistorySearhPopu(AddVatInvoiceActivity.this,  new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                finish();
                            }
                        }, 9);
                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_vat_invoice_page), Gravity.CENTER, 0, 0);
                    }else{
                        finish();
                    }
                }else{//新增
                    if(!"".equals(newUnitNameStr)||!"".equals(newTaxpayerIdentificationNumberStr)||!"".equals(newRegisteredAddressStr)||!"".equals(newRegisteredTelStr)||!"".equals(newDepositBankStr)||!"".equals(newBankAccountStr)){
                        clearHistorySearhPopu = new ClearHistorySearhPopu(AddVatInvoiceActivity.this,  new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                finish();
                            }
                        }, 9);
                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_vat_invoice_page), Gravity.CENTER, 0, 0);
                    }
                    else{
                        finish();
                    }
                }

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newUnitNameStr = unitName.getText().toString().trim();
                newTaxpayerIdentificationNumberStr= taxpayerIdentificationNumber.getText().toString().trim();
                newRegisteredAddressStr = registeredAddress.getText().toString().toString().trim();
                newRegisteredTelStr = registeredTel.getText().toString().trim();
                newDepositBankStr = depositBank.getText().toString().trim();
                newBankAccountStr = bankAccount.getText().toString().toString().trim();
                String checkTell="^(\\d{3,4}-)?\\d{7,8}$";
                String checkMobilePhone="^1[34578]\\d{9}$";
                if("".equals(newUnitNameStr)){
                    Toast.makeText(AddVatInvoiceActivity.this, "请填写单位名称", Toast.LENGTH_SHORT).show();
                }else if("".equals(newTaxpayerIdentificationNumberStr)){
                    Toast.makeText(AddVatInvoiceActivity.this, "请填写纳税人识别码", Toast.LENGTH_SHORT).show();
                }else if("".equals(newRegisteredAddressStr) ){
                    Toast.makeText(AddVatInvoiceActivity.this, "请填写注册地址", Toast.LENGTH_SHORT).show();
                }else if("".equals(newRegisteredTelStr)){
                    Toast.makeText(AddVatInvoiceActivity.this, "请填写注册电话", Toast.LENGTH_SHORT).show();
                }else if(!newRegisteredTelStr.matches(checkTell)&&!newRegisteredTelStr.matches(checkMobilePhone)){
                    Toast.makeText(AddVatInvoiceActivity.this,"请输入正确的注册电话",Toast.LENGTH_SHORT).show();
                }else if("".equals(newDepositBankStr) ){
                    Toast.makeText(AddVatInvoiceActivity.this, "请填写开户银行", Toast.LENGTH_SHORT).show();
                }else if("".equals(newBankAccountStr)){
                    Toast.makeText(AddVatInvoiceActivity.this, "请填写银行账户", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AddVatInvoiceActivity.this,AddVatInvoicePicActivity.class);

                    intent.putExtra("unitName",newUnitNameStr);
                    intent.putExtra("taxpayerIdentificationNumber",newTaxpayerIdentificationNumberStr);
                    intent.putExtra("registeredAddress",newRegisteredAddressStr);
                    intent.putExtra("registeredTel",newRegisteredTelStr);
                    intent.putExtra("depositBank",newDepositBankStr);
                    intent.putExtra("bankAccount",newBankAccountStr);

                    intent.putExtra("isUpdate",isUpdate);
                    if(isUpdate == 1){//修改
                        if(id <= 0){
                            Toast.makeText(AddVatInvoiceActivity.this, "页面传值没有收到", Toast.LENGTH_SHORT).show();
                        }else{
                            intent.putExtra("id",id);
                        }
                    }

                    startActivity(intent);
                }

            }
        });
        rbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnNext.setClickable(true);
                    btnNext.setTextColor(getResources().getColor(R.color.checkGreenColor));
                    btnNext.setBackgroundResource(R.drawable.btn_green_large);
                }else{
                    btnNext.setClickable(false);
                    btnNext.setTextColor(getResources().getColor(R.color.font_grey));
                    btnNext.setBackgroundResource(R.drawable.btn_gray_large);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                newUnitNameStr = unitName.getText().toString().trim();
                newTaxpayerIdentificationNumberStr= taxpayerIdentificationNumber.getText().toString().trim();
                newRegisteredAddressStr = registeredAddress.getText().toString().toString().trim();
                newRegisteredTelStr = registeredTel.getText().toString().trim();
                newDepositBankStr = depositBank.getText().toString().trim();
                newBankAccountStr = bankAccount.getText().toString().toString().trim();

                if(isUpdate == 1){//修改
                    if((!"".equals(newUnitNameStr)&&!unitNameStr.equals(newUnitNameStr))||(!"".equals(newTaxpayerIdentificationNumberStr)&&!taxpayerIdentificationNumberStr.equals(newTaxpayerIdentificationNumberStr))||(!"".equals(newRegisteredAddressStr)&&!registeredAddressStr.equals(newRegisteredAddressStr))||(!"".equals(newRegisteredTelStr)&&!registeredTelStr.equals(newRegisteredTelStr))||(!"".equals(newDepositBankStr)&&!depositBankStr.equals(newDepositBankStr))||(!"".equals(newBankAccountStr)&&!bankAccountStr.equals(newBankAccountStr))){
                        clearHistorySearhPopu = new ClearHistorySearhPopu(AddVatInvoiceActivity.this,  new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                finish();
                            }
                        }, 9);
                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_vat_invoice_page), Gravity.CENTER, 0, 0);
                    }else{
                        finish();
                    }
                }else{//新增
                    if(!"".equals(newUnitNameStr)||!"".equals(newTaxpayerIdentificationNumberStr)||!"".equals(newRegisteredAddressStr)||!"".equals(newRegisteredTelStr)||!"".equals(newDepositBankStr)||!"".equals(newBankAccountStr)){
                        clearHistorySearhPopu = new ClearHistorySearhPopu(AddVatInvoiceActivity.this,  new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                finish();
                            }
                        }, 9);
                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.add_vat_invoice_page), Gravity.CENTER, 0, 0);
                    }
                    else{
                        finish();
                    }
                }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void handleTips(){
        //提示信息变色及点击事件处理
        String tips = tvConfirmBook.getText().toString().trim();
        SpannableStringBuilder spannable = new SpannableStringBuilder(tips);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Do something.

                Intent intent = new Intent(AddVatInvoiceActivity.this,vatInvoiceConfirmBookActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.checkGreenColor));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        //        spannableString.setSpan(clickableSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);
        spannable.setSpan(clickableSpan,tips.length()-9,tips.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvConfirmBook.setText(spannable);

        tvConfirmBook.setMovementMethod(LinkMovementMethod.getInstance());//想要实现指定文字的点击事件，需要添加该方法

    }



}
