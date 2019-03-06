package com.jl.jlapp.adapter;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Evaluation;
import com.jl.jlapp.utils.CustomGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/14 0014.
 */

public class EvalSubmitSuccessAdapter extends BaseQuickAdapter<Evaluation,BaseViewHolder> {

    List<ImageView> starts;
    GoodsEvaluationImageAdapter goodsEvaluationImageAdapter;

    public EvalSubmitSuccessAdapter(@LayoutRes int layoutResId, @Nullable List<Evaluation> data) {
        super(layoutResId, data);

    }
    @Override
    protected void convert(BaseViewHolder helper, Evaluation item) {
        RecyclerView goodsEvalImgRecyclerView=helper.getView(R.id.goods_eval_img_recycler_view);

        helper.setImageDrawable(R.id.should_evaluation_goods_img,helper.itemView.getResources().getDrawable(item.getGoodsImg()));
        helper.setText(R.id.edit_evaluation_content,item.getEvaluationMsg());
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

        for(int i=0;i<item.getStar();i++){
            starts.get(i).setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_star_yellow_s));
        }

        if(item.getImages()!=null&&item.getImages().size()>0){
            goodsEvalImgRecyclerView.setLayoutManager(new CustomGridLayoutManager(helper.itemView.getContext(),5,false));
            goodsEvaluationImageAdapter=new GoodsEvaluationImageAdapter(R.layout.evaluation_img_item,item.getImages());
            goodsEvalImgRecyclerView.setAdapter(goodsEvaluationImageAdapter);
        }

    }

}
