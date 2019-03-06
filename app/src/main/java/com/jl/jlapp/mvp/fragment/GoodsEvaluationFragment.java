package com.jl.jlapp.mvp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.EvaluateClassifyAdapter;
import com.jl.jlapp.adapter.EvaluateListAdapter;
import com.jl.jlapp.eneity.EvaluateTypeModel;
import com.jl.jlapp.eneity.Evaluation;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.PhotoViewActivity;
import com.jl.jlapp.utils.CustomGridLayoutManager;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsEvaluationFragment extends Fragment implements EvaluateListAdapter.OnClickGoodsPicItemListener {
    View view;

    @BindView(R.id.rlv_classify_recycle_view)
    RecyclerView rlvClassify;
    @BindView(R.id.rlv_evaluate_list_recycle_view)
    RecyclerView rlvEvaluateList;
    @BindView(R.id.tv_rate_percentage_num)
    TextView tvRatePercentageNum;
    @BindView(R.id.tv_score)
    TextView tvScore;
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
    @BindView(R.id.no_eval)
    TextView noEval;
    @BindView(R.id.no_evaluation)
    TextView noEvaluation;
    @BindView(R.id.has_eval_rela)
    RelativeLayout hasEvalRela;

    List<ImageView> imageViews;
    List<EvaluateTypeModel> typeModels;
    List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> listModels;
    List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> afterOrderByEvalList;//经过排序后的商品评价
    EvaluateClassifyAdapter classifyAdapter;
    EvaluateListAdapter listAdapter;
    GoodsDetailModel.ResultDataBean resultDataBeanMsg;
    GoodsDetailActivity goodsDetailActivity;

    Double goodEvaluationRating = 0.0;
    Integer goodEvalNum = 0;
    Integer middleEvalNum = 0;
    Integer discrepancyEvalNum = 0;
    Integer hasPicNum = 0;
    double compositeScores = 0;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        goodsDetailActivity = (GoodsDetailActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_evaluation, null, false);
        ButterKnife.bind(this, view);
        imageViews = new ArrayList<>();
        imageViews.add(evaluationStar1);
        imageViews.add(evaluationStar2);
        imageViews.add(evaluationStar3);
        imageViews.add(evaluationStar4);
        imageViews.add(evaluationStar5);

        if (goodsDetailActivity.resultDataBeanMsg != null) {
            resultDataBeanMsg = goodsDetailActivity.resultDataBeanMsg;

            if (goodsDetailActivity.resultDataBeanMsg.getGoodsEvaluations().size() > 0) {
                //给评价排序
                evalMsgOrderBy(goodsDetailActivity.resultDataBeanMsg.getGoodsEvaluations());

                noEval.setVisibility(View.GONE);
                rlvEvaluateList.setVisibility(View.VISIBLE);
                goodEvalNum = goodsDetailActivity.goodEvalNum;
                middleEvalNum = goodsDetailActivity.middleEvalNum;
                discrepancyEvalNum = goodsDetailActivity.discrepancyEvalNum;
                goodEvaluationRating = goodsDetailActivity.goodEvaluationRating;
                hasPicNum = goodsDetailActivity.hasPicNum;
                compositeScores = goodsDetailActivity.compositeScores;
                // compositeScores=4.5;
                tvRatePercentageNum.setText(goodEvaluationRating + "");

                tvScore.setText(compositeScores + "");
                int scores = (int) compositeScores;
                if (compositeScores + 0.5 >= scores + 1) {
                    for (int i = 0; i < scores + 1; i++) {
                        if (i < scores) {
                            imageViews.get(i).setImageResource(R.drawable.icon_star_yellow_s);
                        } else {
                            imageViews.get(i).setImageResource(R.drawable.icon_star_half_s);
                        }

                    }
                } else {
                    for (int i = 0; i < scores; i++) {
                        imageViews.get(i).setImageResource(R.drawable.icon_star_yellow_s);

                    }
                }

                setEvaluationStatisticsData();
                setEvaluationAdapter();
                // getEvaluationMsg();
                setAdapter();
            } else {
                hasEvalRela.setVisibility(View.GONE);
                noEvaluation.setVisibility(View.VISIBLE);
                noEval.setVisibility(View.VISIBLE);
                rlvEvaluateList.setVisibility(View.GONE);
            }
        }
        //没有获取到信息
        else {
            hasEvalRela.setVisibility(View.GONE);
            noEvaluation.setVisibility(View.VISIBLE);
            noEval.setVisibility(View.VISIBLE);
            rlvEvaluateList.setVisibility(View.GONE);
        }


        return view;
    }

    public void setAdapter() {
        rlvClassify.setLayoutManager(new CustomGridLayoutManager(getActivity(), 5, false));
        classifyAdapter = new EvaluateClassifyAdapter(R.layout.item_classify_list, typeModels);
        classifyAdapter.setClickItemNum(0);
        rlvClassify.setAdapter(classifyAdapter);

        classifyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView text = view.findViewById(R.id.tv_evaluate_type);
                listModels.clear();
                switch (text.getText().toString().trim()) {
                    case "全部评价":
                        for (int i = 0; i < afterOrderByEvalList.size(); i++) {
                            listModels.add(afterOrderByEvalList.get(i));
                        }
                        if (listModels.size() == 0) {
                            noEval.setVisibility(View.VISIBLE);
                            rlvEvaluateList.setVisibility(View.GONE);
                        } else {
                            noEval.setVisibility(View.GONE);
                            rlvEvaluateList.setVisibility(View.VISIBLE);
                            listAdapter.notifyDataSetChanged();
                        }

                        classifyAdapter.setClickItemNum(0);
                        classifyAdapter.notifyDataSetChanged();
                        break;
                    case "好评":
                        for (int i = 0; i < afterOrderByEvalList.size(); i++) {
                            if (afterOrderByEvalList.get(i).getScore() >= 4 && afterOrderByEvalList.get(i).getScore() <= 5) {
                                listModels.add(afterOrderByEvalList.get(i));
                            }

                        }
                        Log.d("aaaaaaaaeval", "好评：" + listModels.size());
                        if (listModels.size() == 0) {
                            noEval.setVisibility(View.VISIBLE);
                            rlvEvaluateList.setVisibility(View.GONE);
                        } else {
                            noEval.setVisibility(View.GONE);
                            rlvEvaluateList.setVisibility(View.VISIBLE);
                            listAdapter.notifyDataSetChanged();
                        }
                        classifyAdapter.setClickItemNum(1);
                        classifyAdapter.notifyDataSetChanged();
                        break;
                    case "中评":
                        for (int i = 0; i < afterOrderByEvalList.size(); i++) {
                            if (afterOrderByEvalList.get(i).getScore() >= 2 && afterOrderByEvalList.get(i).getScore() < 4) {
                                listModels.add(afterOrderByEvalList.get(i));
                            }

                        }
                        Log.d("aaaaaaaaeval", "中评：" + listModels.size());
                        if (listModels.size() == 0) {
                            noEval.setVisibility(View.VISIBLE);
                            rlvEvaluateList.setVisibility(View.GONE);
                        } else {
                            noEval.setVisibility(View.GONE);
                            rlvEvaluateList.setVisibility(View.VISIBLE);
                            listAdapter.notifyDataSetChanged();
                        }
                        classifyAdapter.setClickItemNum(2);
                        classifyAdapter.notifyDataSetChanged();
                        break;
                    case "差评":
                        for (int i = 0; i < afterOrderByEvalList.size(); i++) {
                            if (afterOrderByEvalList.get(i).getScore() < 2) {
                                listModels.add(afterOrderByEvalList.get(i));
                            }
                        }
                        Log.d("aaaaaaaaeval", "差评：" + listModels.size());
                        if (listModels.size() == 0) {
                            noEval.setVisibility(View.VISIBLE);
                            rlvEvaluateList.setVisibility(View.GONE);
                        } else {
                            noEval.setVisibility(View.GONE);
                            rlvEvaluateList.setVisibility(View.VISIBLE);
                            listAdapter.notifyDataSetChanged();
                        }
                        classifyAdapter.setClickItemNum(3);
                        classifyAdapter.notifyDataSetChanged();
                        break;
                    case "有图":
                        for (int i = 0; i < afterOrderByEvalList.size(); i++) {
                            if (afterOrderByEvalList.get(i).getEvaluationPics().size() > 0) {
                                listModels.add(afterOrderByEvalList.get(i));
                            }
                        }
                        Log.d("aaaaaaaaeval", "有图：" + listModels.size());
                        if (listModels.size() == 0) {
                            noEval.setVisibility(View.VISIBLE);
                            rlvEvaluateList.setVisibility(View.GONE);
                        } else {
                            noEval.setVisibility(View.GONE);
                            rlvEvaluateList.setVisibility(View.VISIBLE);
                            listAdapter.notifyDataSetChanged();
                        }
                        classifyAdapter.setClickItemNum(4);
                        classifyAdapter.notifyDataSetChanged();
                        break;

                }
            }
        });

    }

    public void setEvaluationAdapter() {
        listModels = new ArrayList<>();
        for (int i = 0; i < afterOrderByEvalList.size(); i++) {
            listModels.add(afterOrderByEvalList.get(i));
        }
        noEval.setVisibility(View.GONE);
        rlvEvaluateList.setVisibility(View.VISIBLE);

        rlvEvaluateList.setLayoutManager(new CustomLanearLayoutManager(getActivity(), false));
        listAdapter = new EvaluateListAdapter(R.layout.item_evaluate_list, listModels);
        listAdapter.setOnClickGoodsPicItemListener(this);
        rlvEvaluateList.setAdapter(listAdapter);


    }

    public void setEvaluationStatisticsData() {
        //设置评价统计信息
        typeModels = new ArrayList<>();

        String types[] = {"全部评价", "好评", "中评", "差评", "有图"};
        String num[] = {goodsDetailActivity.resultDataBeanMsg.getGoodsEvaluations().size() + "", goodEvalNum + "", middleEvalNum + "", discrepancyEvalNum + "", hasPicNum + ""};
        for (int i = 0; i < types.length; i++) {
            EvaluateTypeModel model = new EvaluateTypeModel();
            model.setType(types[i]);
            model.setEvaluate_num(num[i]);
            typeModels.add(model);
        }
    }


    @Override
    public void OnClickGoodsPicItem(String goodsPicUrl) {
        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
        intent.putExtra("picUrl", goodsPicUrl);
        startActivity(intent);
    }

    /*给商品评价做排序*/
    public void evalMsgOrderBy(List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> evals){
        afterOrderByEvalList=new ArrayList<>();
        List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> hasPicAndGoodEvalList=new ArrayList<>();
        List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> hasGoodEvalList=new ArrayList<>();
        List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> hasPicEvalList=new ArrayList<>();
        List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> otherEvalList = new ArrayList<>();//有图

        for(int i=0;i<evals.size();i++){
            //好评加有图
            if(evals.get(i).getScore() >= 4 && evals.get(i).getScore() <= 5&& evals.get(i).getEvaluationPics().size() > 0){
                hasPicAndGoodEvalList.add(evals.get(i));
            }
            //好评
            else if(evals.get(i).getScore() >= 4 && evals.get(i).getScore() <= 5&&evals.get(i).getEvaluationPics().size() <= 0){
                hasGoodEvalList.add(evals.get(i));
            }
            //有图
            else if(evals.get(i).getEvaluationPics().size() > 0){
                hasPicEvalList.add(evals.get(i));
            }
            else{
                otherEvalList.add(evals.get(i));
            }
        }

        if(hasPicAndGoodEvalList.size()>0){
            for(int i=0;i<hasPicAndGoodEvalList.size();i++){
                afterOrderByEvalList.add(hasPicAndGoodEvalList.get(i));
            }
        }
        if(hasGoodEvalList.size()>0){
            for(int i=0;i<hasGoodEvalList.size();i++){
                afterOrderByEvalList.add(hasGoodEvalList.get(i));
            }
        }
        if(hasPicEvalList.size()>0){
            for(int i=0;i<hasPicEvalList.size();i++){
                afterOrderByEvalList.add(hasPicEvalList.get(i));
            }
        }
        if(otherEvalList.size()>0){
            for(int i=0;i<otherEvalList.size();i++){
                afterOrderByEvalList.add(otherEvalList.get(i));
            }
        }
    }


}
