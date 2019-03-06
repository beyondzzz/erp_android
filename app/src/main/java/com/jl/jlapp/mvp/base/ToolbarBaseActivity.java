package com.jl.jlapp.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.jl.jlapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 带返回按钮的toolbar
 * Created by fyf on 2018/1/18.
 */

public abstract class ToolbarBaseActivity extends BaseActivity {

    @BindView(R.id.toolbarBanner)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initToolbar();
    }

    protected void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
