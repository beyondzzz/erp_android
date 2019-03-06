package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.AfterSaleImgAdapter;
import com.jl.jlapp.adapter.EvaluationPicAdapter;
import com.jl.jlapp.eneity.EvaluationDetailByUserIdAndIdModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.CustomGridLayoutManager;
import com.jl.jlapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-02-09.
 */

public class EvalSubmitSuccessActivity extends AppCompatActivity {

    int userId = 0;
    int id = 0;
    int orderDetailNum = 0;
    int orderId = 0;
    String orderDetailPic = "";
    int score = 0;

    @BindView(R.id.should_evaluation_goods_img)
    ImageView shouldEvaluationGoodsImg;

    @BindView(R.id.evaluation_star1)
    ImageView evaluationStar1;
    @BindView(R.id.evaluation_star2)
    ImageView evaluationStar2;
    @BindView(R.id.evaluation_star3)
    ImageView evaluationStar3;
    @BindView(R.id.evaluation_star4)
    ImageView evaluationStar4;
    @BindView(R.id.evaluation_star5)
    ImageView evaluationStar5;
    @BindView(R.id.edit_evaluation_content)
    TextView edit_evaluation_content;
    @BindView(R.id.back)
    ImageView back;
    List<EvaluationDetailByUserIdAndIdModel.ResultDataBean.EvaluationPicsBean> evaluationPicsBeans = new ArrayList<>();

    @BindView(R.id.goods_eval_img_recycler_view)
    RecyclerView imgRecyclerView;

    EvaluationPicAdapter evaluationPicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_eval_submit_success);

        //控制注解
        ButterKnife.bind(this);
        Tools.finishAll();
        Tools.addActivity(this);

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);



        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        orderDetailNum = intent.getIntExtra("orderDetailNum",0);
        orderDetailPic = intent.getStringExtra("orderDetailPic");
        orderId = intent.getIntExtra("orderId",0);

        Glide
                .with(this)
                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                .load(AppFinal.BASEURL + orderDetailPic)
                .into(shouldEvaluationGoodsImg);

        getNetData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderDetailNum == 0 && orderId == 0){
                    finish();
                }else if(orderDetailNum == 1){
                    Intent intent = new Intent(EvalSubmitSuccessActivity.this,MyOrderListActivity.class);
                    startActivity(intent);
                    finish();
                }else if(orderDetailNum > 1 && orderId > 0){
                    Tools.addActivity(EvalSubmitSuccessActivity.this);
                    Intent intent = new Intent(EvalSubmitSuccessActivity.this,ShouldEvaluationActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                    Tools.finishAll();
                }
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if(orderDetailNum == 0 && orderId == 0){
                finish();
            }else if(orderDetailNum == 1){
                Intent intent = new Intent(EvalSubmitSuccessActivity.this,MyOrderListActivity.class);
                startActivity(intent);
                finish();
            }else if(orderDetailNum > 1 && orderId > 0){
                Intent intent = new Intent(EvalSubmitSuccessActivity.this,ShouldEvaluationActivity.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
                finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void initStars(int score) {
        evaluationStar1.setImageResource(R.drawable.star_gray);
        evaluationStar2.setImageResource(R.drawable.star_gray);
        evaluationStar3.setImageResource(R.drawable.star_gray);
        evaluationStar4.setImageResource(R.drawable.star_gray);
        evaluationStar5.setImageResource(R.drawable.star_gray);

        switch (score){
            case  1:
                evaluationStar1.setImageResource(R.drawable.star_yellow);
                break;
            case  2:
                evaluationStar1.setImageResource(R.drawable.star_yellow);
                evaluationStar2.setImageResource(R.drawable.star_yellow);

                break;
            case  3:
                evaluationStar1.setImageResource(R.drawable.star_yellow);
                evaluationStar2.setImageResource(R.drawable.star_yellow);
                evaluationStar3.setImageResource(R.drawable.star_yellow);

                break;
            case  4:
                evaluationStar1.setImageResource(R.drawable.star_yellow);
                evaluationStar2.setImageResource(R.drawable.star_yellow);
                evaluationStar3.setImageResource(R.drawable.star_yellow);
                evaluationStar4.setImageResource(R.drawable.star_yellow);

                break;
            case  5:
                evaluationStar1.setImageResource(R.drawable.star_yellow);
                evaluationStar2.setImageResource(R.drawable.star_yellow);
                evaluationStar3.setImageResource(R.drawable.star_yellow);
                evaluationStar4.setImageResource(R.drawable.star_yellow);
                evaluationStar5.setImageResource(R.drawable.star_yellow);

                break;

        }
    }

    public void imgSetAdapter(){
        //设置RecyclerView的布局方式
        imgRecyclerView.setLayoutManager(new CustomGridLayoutManager(this,5,false));
        //初始化适配器
        evaluationPicAdapter=new EvaluationPicAdapter(R.layout.after_sale_img_item,evaluationPicsBeans);
        //设置适配器
        imgRecyclerView.setAdapter(evaluationPicAdapter);
        evaluationPicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(EvalSubmitSuccessActivity.this,PhotoViewActivity.class);
                intent.putExtra("picUrl",evaluationPicsBeans.get(position).getPicUrl());
                startActivity(intent);
            }
        });
    }

    public void getNetData(){
        if(id != 0 && orderDetailNum != 0){
            Net.get().getEvaluationDetailByUserIdAndId(id,userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(evaluationDetailByUserIdAndIdModel -> {
                        int code1 = evaluationDetailByUserIdAndIdModel.getCode();
                        if (code1 == 200) {
                            score = evaluationDetailByUserIdAndIdModel.getResultData().getScore();
                            initStars(score);
                            String edit_evaluation_content_str= evaluationDetailByUserIdAndIdModel.getResultData().getEvaluationContent();
                            edit_evaluation_content.setText(edit_evaluation_content_str);
                            evaluationPicsBeans = evaluationDetailByUserIdAndIdModel.getResultData().getEvaluationPics();
                            if(evaluationPicsBeans.size()>0){
                                imgSetAdapter();
                            }
                        } else {
                            Toast.makeText(this, "" + evaluationDetailByUserIdAndIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        Toast.makeText(this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                    });
        }else{
            Toast.makeText(this, "页面传值没有接收到", Toast.LENGTH_SHORT).show();
        }

    }
}
