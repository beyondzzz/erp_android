package com.jl.jlapp.adapter;

import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.Evaluation;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.utils.CustomGridLayoutManager;
import com.jl.jlapp.utils.Tools;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 评价
 * Created by fyf on 2018/1/20.
 */

public class EvaluateListAdapter extends BaseQuickAdapter<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean, BaseViewHolder> {
    List<ImageView> starts;
    GoodsEvaluationImageAdapter goodsEvaluationImageAdapter;
    EvaluateListAdapter.OnClickGoodsPicItemListener onClickGoodsPicItemListener;
    public void setOnClickGoodsPicItemListener(EvaluateListAdapter.OnClickGoodsPicItemListener onClickGoodsPicItemListener) {
        this.onClickGoodsPicItemListener = onClickGoodsPicItemListener;
    }
    public EvaluateListAdapter(int layoutResId, @Nullable List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean item) {
        ImageView isVip=helper.getView(R.id.iv_vip);
        RecyclerView evaluationImg=helper.getView(R.id.evaluation_img);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        helper.setText(R.id.tv_date, simpleDateFormat.format(item.getEvaluationTime()));
        helper.setText(R.id.tv_evaluate_detail, item.getEvaluationContent());
        helper.setText(R.id.tv_user_num,replacePhoneNum(item.getUser().getPhone()));
        isVip.setVisibility(View.GONE);
        evaluationImg.setVisibility(View.GONE);

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

        if(item.getUser().getIsVip()==1){
            isVip.setVisibility(View.VISIBLE);
            isVip.setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.btn_vip));
        }

        int scorenum=(int) Math.floor(item.getScore());
        for(int i=0;i<scorenum;i++){

            starts.get(i).setImageDrawable(helper.itemView.getResources().getDrawable(R.drawable.icon_star_yellow_s));
        }

        if(item.getEvaluationPics().size()>0){
            evaluationImg.setVisibility(View.VISIBLE);
            evaluationImg.setLayoutManager(new CustomGridLayoutManager(helper.itemView.getContext(),5,false));
            goodsEvaluationImageAdapter=new GoodsEvaluationImageAdapter(R.layout.evaluation_img_item,item.getEvaluationPics());
            evaluationImg.setAdapter(goodsEvaluationImageAdapter);
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

    public String replacePhoneNum(String tel){
        return tel.substring(0, 3) + "****" + tel.substring(7, 11);
    }
    //自定义回调接口
    public interface OnClickGoodsPicItemListener {
        void OnClickGoodsPicItem(String goodsPicUrl);
    }
}
