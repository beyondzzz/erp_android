package com.jl.jlapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Evaluation;
import com.jl.jlapp.eneity.OrderEvaluationDetailModel;
import com.jl.jlapp.mvp.activity.EvaluationDetailActivity;
import com.jl.jlapp.mvp.activity.PhotoViewActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.utils.CustomGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class EvalDetailAdapter extends BaseQuickAdapter<OrderEvaluationDetailModel.ResultDataBean.OrderDetailsBean,BaseViewHolder> {

    List<ImageView> starts;
    OrderDetailEvaluationImageAdapter orderDetailEvaluationImageAdapter;
    OnClickGoodsPicItemListener onClickGoodsPicItemListener;

    public void setOnClickGoodsPicItemListener(OnClickGoodsPicItemListener onClickGoodsPicItemListener) {
        this.onClickGoodsPicItemListener = onClickGoodsPicItemListener;
    }

    public EvalDetailAdapter(@LayoutRes int layoutResId, @Nullable List<OrderEvaluationDetailModel.ResultDataBean.OrderDetailsBean> data) {
        super(layoutResId, data);


    }
    @Override
    protected void convert(BaseViewHolder helper, OrderEvaluationDetailModel.ResultDataBean.OrderDetailsBean item) {
        RecyclerView goodsEvalImgRecyclerView=helper.getView(R.id.goods_eval_img_recycler_view);
        if(item.getGoodsEvaluation()!=null){
            // helper.setImageDrawable(R.id.should_evaluation_goods_img,helper.itemView.getResources().getDrawable(item.getGoodsImg()));
            ImageView imageView = helper.getView(R.id.should_evaluation_goods_img);
            Glide
                    .with(helper.itemView.getContext())
                    .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_xs).error(R.drawable.img_lose_xs))
                    .load(AppFinal.BASEURL + item.getGoodsCoverUrl())
                    .into(imageView);

            helper.setText(R.id.edit_evaluation_content,item.getGoodsEvaluation().getEvaluationContent());
            ImageView start1=helper.getView(R.id.evaluation_star1);
            ImageView start2=helper.getView(R.id.evaluation_star2);
            ImageView start3=helper.getView(R.id.evaluation_star3);
            ImageView start4=helper.getView(R.id.evaluation_star4);
            ImageView start5=helper.getView(R.id.evaluation_star5);
            starts=new ArrayList<>();
            starts.add(start1);
            starts.add(start2);
            starts.add(start3);
            starts.add(start4);
            starts.add(start5);
            for(int i=0;i<5;i++){
                starts.get(i).setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_star_gray_s));
            }

            for(int i=0;i<item.getGoodsEvaluation().getScore();i++){
                starts.get(i).setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_star_yellow_s));
            }

            if(item.getGoodsEvaluation().getEvaluationPics()!=null&&item.getGoodsEvaluation().getEvaluationPics().size()>0){
                goodsEvalImgRecyclerView.setLayoutManager(new CustomGridLayoutManager(helper.itemView.getContext(),5,false));
                orderDetailEvaluationImageAdapter=new OrderDetailEvaluationImageAdapter(R.layout.evaluation_img_item,item.getGoodsEvaluation().getEvaluationPics());
                goodsEvalImgRecyclerView.setAdapter(orderDetailEvaluationImageAdapter);
                orderDetailEvaluationImageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        String picUrl = item.getGoodsEvaluation().getEvaluationPics().get(position).getPicUrl();

                        if(onClickGoodsPicItemListener != null){

                            onClickGoodsPicItemListener.OnClickGoodsPicItem(picUrl);

                        }
                    }
                });
            }
        }
        else{
            Toast.makeText(helper.itemView.getContext(),"抱歉，数据错误。",Toast.LENGTH_SHORT).show();
        }




    }


    //自定义回调接口
    public interface OnClickGoodsPicItemListener {
        void OnClickGoodsPicItem(String goodsPicUrl);
    }

}
