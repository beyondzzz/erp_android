package com.jl.jlapp.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fyf on 2018/1/16.
 */

public class AddVatInvoiceWaitCheckActivity extends AppCompatActivity {

    @BindView(R.id.btn_finish)
    TextView btnFinish;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vat_invoice_wait_check);
        ButterKnife.bind(this);
        Tools.finishAll();
        Intent intent = getIntent();
        int isUpdate = intent.getIntExtra("isUpdate",0);
        if(isUpdate == 1){
            title.setText("修改增票资质");
            tips.setText("修改成功，请耐心等待审核");
        }
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addActivity(AddVatInvoiceWaitCheckActivity.this);
                Intent intent = new Intent(AddVatInvoiceWaitCheckActivity.this,InvoiceActivity.class);
                startActivity(intent);
            }
        });
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addActivity(AddVatInvoiceWaitCheckActivity.this);
                Intent intent = new Intent(AddVatInvoiceWaitCheckActivity.this,InvoiceActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Tools.addActivity(AddVatInvoiceWaitCheckActivity.this);
            Intent intent = new Intent(AddVatInvoiceWaitCheckActivity.this,InvoiceActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
