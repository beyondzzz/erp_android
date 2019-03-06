package com.jl.jlapp.mvp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.EvalSubmitSuccessAdapter;
import com.jl.jlapp.eneity.Evaluation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EvalSubmitSuccessFragment extends Fragment {

    View view;


    EvalSubmitSuccessAdapter evalSubmitSuccessAdapter;
    List<Evaluation> evaluationList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_eval_submit_success, container, false);
        ButterKnife.bind(this,view);


        return view;
    }

    /*private void setAdapter(){
        rlvEvaluate.setLayoutManager(new LinearLayoutManager(getActivity()));
        evalSubmitSuccessAdapter=new EvalSubmitSuccessAdapter(R.layout.evaluation_submit_success_item,evaluationList);
        rlvEvaluate.setAdapter(evalSubmitSuccessAdapter);
    }

    private void getEvaluationMsg(){
        evaluationList=new ArrayList<>();
        Evaluation evaluation;
        for(int i=0;i<3;i++){
            evaluation=new Evaluation();
            evaluation.setUserPhone("1888888888"+i);
            evaluation.setEvaluationMsg("包装很棒，没有一个损坏的，很新鲜的样子，外面的一层还是润的，按照说明书，先放在外面透气的地方干燥好，后面吃了再来追评，反正好吃肯定会再来光顾的");
            evaluation.setStar(i);
            evaluation.setGoodsImg(R.drawable.img_steamed);
//            if (i==1){
//                List image=new ArrayList();
//                image.add(R.drawable.img_dessert);
//                image.add(R.drawable.img_steamed);
//                image.add(R.drawable.img_dessert);
//                image.add(R.drawable.img_steamed);
//                image.add(R.drawable.img_dessert);
//                evaluation.setImages(image);
//            }
            evaluationList.add(evaluation);

        }
    }*/

}
