package com.jl.jlapp.mvp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by THINK on 2018-03-20.
 */

public class PhotoViewActivity extends AppCompatActivity {

    @BindView(R.id.img)
    PhotoView mPhotoView;
    @BindView(R.id.parent_photo_view)
    RelativeLayout parentPhotoView;
    @BindView(R.id.bg)
    ImageView mBg;
    Info mInfo;


    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        in.setDuration(300);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                       finish();
                    }
                });
            }
        });



        Intent intent = getIntent();
        String picUrl = intent.getStringExtra("picUrl");
        int flag = intent.getIntExtra("flag",0);

        mInfo = mPhotoView.getInfo();
        if(flag==0){
            picUrl=AppFinal.BASEURL + picUrl;
        }
        Glide
                .with(PhotoViewActivity.this)
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_m).error(R.drawable.img_lose_m))
                .load(picUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                        mPhotoView.setImageBitmap(resource);

                        mBg.startAnimation(in);
                        mBg.setVisibility(View.VISIBLE);
                        parentPhotoView.setVisibility(View.VISIBLE);
                        mPhotoView.animaFrom(mInfo);
                    }
                });

    }
}
