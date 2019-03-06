package com.jl.jlapp.mvp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.CliassifyAdapter;
import com.jl.jlapp.adapter.CliassifyDetailsAdapter;
import com.jl.jlapp.eneity.CliassifyDetailsModel;
import com.jl.jlapp.eneity.ClissifyModel;
import com.jl.jlapp.utils.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

        TextView textView;
        Banner banner;
        List images;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,MyOrderListActivity.class);
                    startActivity(intent);
                }
            });


            images = new ArrayList();
            images.add(R.drawable.img_banner_demo);
            images.add(R.drawable.img_steamed);
            images.add(R.drawable.img_dessert);
            images.add(R.drawable.img_steamed);
            images.add(R.drawable.img_dessert);
            images.add(R.drawable.img_steamed);
            banner = findViewById(R.id.banner);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(images);

            //banner设置方法全部调用完毕时最后调用
            banner.start();

        }


}
