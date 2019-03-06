package com.jl.jlapp.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jl.jlapp.R;
import com.jl.jlapp.mvp.fragment.EditEvaluationFragment;
import com.jl.jlapp.mvp.fragment.EvalSubmitSuccessFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluationActivity extends FragmentActivity implements View.OnClickListener {

    /*@BindView(R.id.evaluation_submit)
    TextView evaluationSubmit;*/

    //加载碎片
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    /*碎片*/
    Fragment editEvaluationFragment, evalSubmitSuccessFragment,shouldEvaluationFragment;
    public int orderId = 0;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", 0);

        //setListener();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        //shouldEvaluationFragment = new ShouldEvaluationFragment();

        transaction.replace(R.id.evaluation_fragment, shouldEvaluationFragment);

        transaction.commit();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

  /* private void setListener() {
        evaluationSubmit.setOnClickListener(this);
    }*/

    @Override
    public void onClick(View view) {
       /* switch (view.getId()) {
            case R.id.evaluation_submit:
                transaction = fragmentManager.beginTransaction();
                if (evalSubmitSuccessFragment == null) {
                    evalSubmitSuccessFragment = new EvalSubmitSuccessFragment();
                }
                transaction.replace(R.id.evaluation_fragment, evalSubmitSuccessFragment);
                transaction.commit();
                evaluationSubmit.setVisibility(View.GONE);
                break;
        }*/
    }

    public void replaceEditFragment(){
        transaction = fragmentManager.beginTransaction();

        editEvaluationFragment = new EditEvaluationFragment();

        transaction.replace(R.id.evaluation_fragment, editEvaluationFragment);

        transaction.commit();
    }
}
