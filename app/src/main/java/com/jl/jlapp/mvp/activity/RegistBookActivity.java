package com.jl.jlapp.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jl.jlapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by THINK on 2018-03-01.
 */

public class RegistBookActivity extends AppCompatActivity {

    @BindView(R.id.return_btn)
    ImageView returnBtn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_book_layout);
        ButterKnife.bind(this);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
