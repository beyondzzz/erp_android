package com.jl.jlapp.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.jaeger.library.StatusBarUtil;
import com.jl.jlapp.R;
import com.jl.jlapp.mvp.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fyf on 2018/1/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /*@BindView(R.id.toolbarBanner)
    protected Toolbar toolbar;*/

    @LayoutRes
    protected abstract int contentViewLayoutRes();

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewLayoutRes());
        StatusBarUtil.setColor(this, ContextCompat.getColor(getApplicationContext(), R.color.light_blue), 128);
        ButterKnife.bind(this);

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent == null) return;
        if (intent.getComponent() == null) return;
        String className = intent.getComponent().getClassName();
        if (!className.equals(MainActivity.class.getName())) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

    @Override
    public void finish() {
        super.finish();
        if (!((Object) this).getClass().equals(MainActivity.class)) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

}

