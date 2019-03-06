package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.ActivityMessageListAdapter;
import com.jl.jlapp.eneity.ActivityMessageByUserIdModel;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActivityMessageListActivity extends Activity {

    @BindView(R.id.activity_message_recycler_view)
    RecyclerView activityMessageRecyclerView;
    @BindView(R.id.no_message_linear)
    LinearLayout noMessageLinear;

    ActivityMessageListAdapter activityMessageListAdapter;
    List<ActivityMessageByUserIdModel.ResultDataBean.ActivityMessageListBean> activityMessageList = new ArrayList();

    int userId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        getNetData();

    }

    @OnClick({R.id.icon_return})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_return:
                finish();
                break;
        }
    }



    private void setAdpter(){
        activityMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityMessageListAdapter=new ActivityMessageListAdapter(R.layout.activity_message_list_item_layout,activityMessageList);
        activityMessageRecyclerView.setAdapter(activityMessageListAdapter);

        activityMessageListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int activityInformationId = activityMessageList.get(position).getActivityInformatId();
                Intent intent=new Intent(ActivityMessageListActivity.this,ActivityNameActivity.class);
                intent.putExtra("activityInformationId",activityInformationId);
                startActivity(intent);
            }
        });
    }




    private void getNetData(){

        Net.get().getActivityMessageByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activityMessageByUserIdModel -> {
                    if (activityMessageByUserIdModel.getCode() == 200) {
                        activityMessageList = activityMessageByUserIdModel.getResultData().getActivityMessageList();
                        if(activityMessageList.size()>0){//有消息
                            activityMessageRecyclerView.setVisibility(View.VISIBLE);
                            noMessageLinear.setVisibility(View.GONE);
                            setAdpter();
                        }else{//暂无消息
                            activityMessageRecyclerView.setVisibility(View.GONE);
                            noMessageLinear.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(ActivityMessageListActivity.this, activityMessageByUserIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(ActivityMessageListActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });


    }
}
