package com.jl.jlapp.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.mvp.activity.AddVatInvoiceActivity;
import com.jl.jlapp.mvp.activity.AfterSaleApplyActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.nets.AppFinal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by THINK on 2018-02-27.
 */

public class AddVatInvoiceFragment  extends Fragment {

    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.rb_agree)
    CheckBox rbAgree;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_add_vat_invoice, null, false);

        //控制注解
        ButterKnife.bind(this,view);
        handleTips();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unitNameStr = unitName.getText().toString().trim();
                String taxpayerIdentificationNumberStr = taxpayerIdentificationNumber.getText().toString().trim();
                String registeredAddressStr = registeredAddress.getText().toString().toString().trim();
                String registeredTelStr = registeredTel.getText().toString().trim();
                String depositBankStr = depositBank.getText().toString().trim();
                String bankAccountStr = bankAccount.getText().toString().toString().trim();
                if("".equals(unitNameStr)){
                    Toast.makeText(getActivity(), "请填写单位名称", Toast.LENGTH_SHORT).show();
                }else if("".equals(taxpayerIdentificationNumberStr)){
                    Toast.makeText(getActivity(), "请填写纳税人识别码", Toast.LENGTH_SHORT).show();
                }else if("".equals(registeredAddressStr) ){
                    Toast.makeText(getActivity(), "请填写注册地址", Toast.LENGTH_SHORT).show();
                }else if("".equals(registeredTelStr)){
                    Toast.makeText(getActivity(), "请填写注册电话", Toast.LENGTH_SHORT).show();
                }else if("".equals(depositBankStr) ){
                Toast.makeText(getActivity(), "请填写开户银行", Toast.LENGTH_SHORT).show();
            }else if("".equals(bankAccountStr)){
                Toast.makeText(getActivity(), "请填写银行账户", Toast.LENGTH_SHORT).show();
            }else{
//                    AddVatInvoiceActivity addVatInvoiceActivity  = (AddVatInvoiceActivity) getActivity();
//                    AddVatInvoicePicFragment addVatInvoicePicFragment = new AddVatInvoicePicFragment();
//                    addVatInvoiceActivity.replaceFragmentPage(addVatInvoicePicFragment);
//                    addVatInvoiceActivity.replaceBgPic(2);
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

        return view;
    }



    public void handleTips(){
        //提示信息变色及点击事件处理
        String tips = rbAgree.getText().toString().trim();
        SpannableStringBuilder spannable = new SpannableStringBuilder(tips);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Do something.
                Toast.makeText(getActivity(), "暂无内容!", Toast.LENGTH_LONG).show();

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
        rbAgree.setText(spannable);

        rbAgree.setMovementMethod(LinkMovementMethod.getInstance());//想要实现指定文字的点击事件，需要添加该方法

    }
}
