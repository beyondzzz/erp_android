package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.mvp.fragment.InvoiceFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fyf on 2018/1/16.
 */

public class InvoiceActivity extends AppCompatActivity {

    @BindView(R.id.tv_vat_invoice)
    TextView tv_vat_invoice;
    @BindView(R.id.tv_unit_invoice)
    TextView tv_unit_invoice;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    private InvoiceFragment vatInvoice;
    private InvoiceFragment unitInvoice;

    int userId=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        ButterKnife.bind(this);
        Tools.finishAll();
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
//        Log.d("aaaaaaaaaaaaaaaaa", "onResume: "+userId);
        if(userId>0) {
            initView();
        }else{
            Toast.makeText(this,"您还未登录，请先登录。",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initView() {
        vatInvoice = InvoiceFragment.newInstance(InvoiceFragment.TYPE_VATINVOICE,userId);
        unitInvoice = InvoiceFragment.newInstance(InvoiceFragment.TYPE_UNITINVOICE,userId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_frame, vatInvoice)
                .add(R.id.lay_frame, unitInvoice)
                .show(vatInvoice)
                .hide(unitInvoice)
                .commitAllowingStateLoss();

        Drawable drawableBottom = getResources().getDrawable(
                R.drawable.splite_green);
        tv_vat_invoice.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableBottom);
        tv_unit_invoice.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

        tv_vat_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(vatInvoice).hide(unitInvoice).commitAllowingStateLoss();
                tv_vat_invoice.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableBottom);
                tv_unit_invoice.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }
        });
        tv_unit_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(unitInvoice).hide(vatInvoice).commitAllowingStateLoss();
                tv_vat_invoice.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, null);
                tv_unit_invoice.setCompoundDrawablesWithIntrinsicBounds(null,null,null,drawableBottom);
            }
        });

    }

//    @OnClick({R.id.return_btn})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.return_btn:
//                finish();
//                break;
//        }
//    }


}
