package com.jl.jlapp.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Evaluation;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.utils.CustomGridLayoutManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柳亚婷 on 2018/1/20 0020.
 */

public class GoodsMsgEvaluationAdapter extends BaseQuickAdapter<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean,BaseViewHolder> {

    List<ImageView> starts;
    GoodsEvaluationImageAdapter goodsEvaluationImageAdapter;
    OnClickGoodsPicItemListener onClickGoodsPicItemListener;
    public void setOnClickGoodsPicItemListener(OnClickGoodsPicItemListener onClickGoodsPicItemListener) {
        this.onClickGoodsPicItemListener = onClickGoodsPicItemListener;
    }
    public GoodsMsgEvaluationAdapter(int layoutResId, @Nullable List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean item) {
        RecyclerView evaluationImgRecyclerView=helper.getView(R.id.evaluation_img);

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


        helper.setText(R.id.evaluation_user_phone,item.getUser().getPhone());
        helper.setText(R.id.evaluation_msg,item.getEvaluationContent());

        int scorenum=(int) Math.floor(item.getScore());
        for(int i=0;i<scorenum;i++){
            starts.get(i).setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_star_yellow_s));
        }

        if(item.getEvaluationPics().size()>0){
            evaluationImgRecyclerView.setLayoutManager(new CustomGridLayoutManager(helper.itemView.getContext(),5,false));
            goodsEvaluationImageAdapter=new GoodsEvaluationImageAdapter(R.layout.evaluation_img_item,item.getEvaluationPics());
            evaluationImgRecyclerView.setAdapter(goodsEvaluationImageAdapter);
            goodsEvaluationImageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    String picUrl = item.getEvaluationPics().get(position).getPicUrl();
                    if(onClickGoodsPicItemListener != null){

                        onClickGoodsPicItemListener.OnClickGoodsPicItem(picUrl);

                    }
                }
            });
        }
    }
    //自定义回调接口
    public interface OnClickGoodsPicItemListener {
        void OnClickGoodsPicItem(String goodsPicUrl);
    }
}
