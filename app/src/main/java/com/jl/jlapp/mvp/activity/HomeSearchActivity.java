package com.jl.jlapp.mvp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.HotSearchAdapter;
import com.jl.jlapp.adapter.SearchHistoryWorldAdapter;
import com.jl.jlapp.db.DatabaseHelper;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.CustomGridLayoutManager;
import com.jl.jlapp.utils.CustomLanearLayoutManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeSearchActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.hot_search_no_word_recycler_view)
    RecyclerView hotSearchRecyclerView;
    @BindView(R.id.search_history_recycler_view)
    RecyclerView searchHistoryRecyclerView;
    @BindView(R.id.hot_search_bottom_clear_history)
    LinearLayout hotSearchBottomClearHistory;
    @BindView(R.id.hot_search_has_word_scroll_view)
    HorizontalScrollView hotSearchHasWordScrollView;
    @BindView(R.id.has_history_search_world_linear)
    LinearLayout hasHistorySearchWorldLinear;
    @BindView(R.id.hot_search_btn)
    TextView hotSearchBtn;
    @BindView(R.id.search_world)
    EditText searchWorld;
    @BindView(R.id.hot_search_has_word_linear_layout)
    LinearLayout hotSearchHasWordLinearLayout;
    @BindView(R.id.hot_search_return_btn)
    ImageView hotSearchReturnBtn;

    HotSearchAdapter hotSearchAdapter, hotSearchHasWorldRecyclerViewAdapter;
    SearchHistoryWorldAdapter searchHistoryWorldAdapter;
    ClearHistorySearhPopu clearHistorySearhPopu;

    DatabaseHelper dbHelper;
    SQLiteDatabase sqliteDatabase;

    List<String> hotWord;
    List<String> searchHistoryWord;
    private LayoutInflater mInflater;
    String oldSearchWorld = "";
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        //控制注解
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);

        Intent intent = getIntent();
        oldSearchWorld = intent.getStringExtra("oldSearchWorld");
        searchWorld.setText(oldSearchWorld);

        // 创建SQLiteOpenHelper子类对象
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(this);
            //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
            sqliteDatabase = dbHelper.getWritableDatabase();
            // SQLiteDatabase  sqliteDatabase = dbHelper.getReadbleDatabase();
        }


        setListener();
        getSearchHistoryData();
        //调取接口获取结果集
        selectHotSearchWord();



    }

    //给控件添加事件
    private void setListener() {
        hotSearchBottomClearHistory.setOnClickListener(this);
        hotSearchBtn.setOnClickListener(this);
        hotSearchReturnBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hot_search_bottom_clear_history:
                clearHistorySearhPopu = new ClearHistorySearhPopu(this, itemclick,0);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.home_search_page), Gravity.CENTER, 0, 0);
                break;
            case R.id.hot_search_btn:
                String mSearchWorld = searchWorld.getText().toString().trim();
                if (!"".equals(mSearchWorld)) {
                    int i = 0;
                    for (i = 0; i < searchHistoryWord.size(); i++) {
                        if (searchHistoryWord.get(i).equals(mSearchWorld)) {
                            break;
                        }
                    }
                    //说明没有重复的
                    if (i == searchHistoryWord.size()) {
                        // 调用insert()方法将数据插入到数据库当中
                        dbHelper.add(sqliteDatabase, mSearchWorld);
                        getSearchHistoryData();

                    }

                    //------搜索历史recycleview适配器 start----------

                    if (searchHistoryWorldAdapter == null) {
                        CustomLanearLayoutManager customLanearLayoutManager = new CustomLanearLayoutManager(this);
                        customLanearLayoutManager.setScrollEnabled(false);

                        searchHistoryRecyclerView.setLayoutManager(customLanearLayoutManager);
                        searchHistoryWorldAdapter = new SearchHistoryWorldAdapter(R.layout.search_history_item, searchHistoryWord);
                        searchHistoryRecyclerView.setAdapter(searchHistoryWorldAdapter);

                        searchHistoryWorldAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                TextView searchwold = view.findViewById(R.id.search_history_world);
                                Intent intent = new Intent(HomeSearchActivity.this, SearchGoodsListActivity.class);
                                intent.putExtra("searchMsg", searchwold.getText().toString().trim());
                                startActivity(intent);
                            }
                        });
                    }
                    searchHistoryWorldAdapter.notifyDataSetChanged();
                    //------搜索历史recycleview适配器 end----------
                }
                Intent intent = new Intent(HomeSearchActivity.this, SearchGoodsListActivity.class);
                intent.putExtra("searchMsg", mSearchWorld);
                startActivity(intent);

                break;
            case R.id.hot_search_return_btn:
                /*Intent intent2 = new Intent(HomeSearchActivity.this, BaseActivity.class);
                startActivity(intent2);*/
                finish();
                break;
        }
    }

    private void setAdapter() {
        if (searchHistoryWord.size() == 0) {
            hotSearchHasWordScrollView.setVisibility(View.GONE);
            hotSearchRecyclerView.setVisibility(View.VISIBLE);
            hasHistorySearchWorldLinear.setVisibility(View.GONE);

            //------无搜索历史时展示的热门搜索recycleview适配器 start----------

            if (hotSearchAdapter == null) {
                hotSearchRecyclerView.setLayoutManager(new CustomGridLayoutManager(this, 4, false));
                hotSearchAdapter = new HotSearchAdapter(R.layout.hot_search_item, hotWord);
                hotSearchRecyclerView.setAdapter(hotSearchAdapter);
                hotSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        TextView searchwold = view.findViewById(R.id.hot_search_content);
                        Intent intent = new Intent(HomeSearchActivity.this, SearchGoodsListActivity.class);
                        intent.putExtra("searchMsg", searchwold.getText().toString().trim());
                        startActivity(intent);
                    }
                });
            }
            hotSearchAdapter.notifyDataSetChanged();
            //------无搜索历史时展示的热门搜索recycleview适配器 end----------
        } else {
            hotSearchHasWordScrollView.setVisibility(View.VISIBLE);
            hotSearchRecyclerView.setVisibility(View.GONE);
            hasHistorySearchWorldLinear.setVisibility(View.VISIBLE);

            //-------有搜索历史的时候的热门搜索词动态添加view start---------

            for (int i = 0; i < hotWord.size(); i++) {
                view = mInflater.inflate(R.layout.hot_search_item,
                        hotSearchHasWordLinearLayout, false);
                TextView txt = (TextView) view
                        .findViewById(R.id.hot_search_content);
                txt.setText(hotWord.get(i));
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeSearchActivity.this, SearchGoodsListActivity.class);
                        intent.putExtra("searchMsg", txt.getText().toString().trim());
                        startActivity(intent);
                    }
                });
                hotSearchHasWordLinearLayout.addView(view);

            }

            //-------有搜索历史的时候的热门搜索词适配器 end---------

            //------搜索历史recycleview适配器 start----------

            if (searchHistoryWorldAdapter == null) {
                CustomLanearLayoutManager customLanearLayoutManager = new CustomLanearLayoutManager(this);
                customLanearLayoutManager.setScrollEnabled(false);

                searchHistoryRecyclerView.setLayoutManager(customLanearLayoutManager);
                searchHistoryWorldAdapter = new SearchHistoryWorldAdapter(R.layout.search_history_item, searchHistoryWord);
                searchHistoryRecyclerView.setAdapter(searchHistoryWorldAdapter);

                searchHistoryWorldAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        TextView searchwold = view.findViewById(R.id.search_history_world);
                        Intent intent = new Intent(HomeSearchActivity.this, SearchGoodsListActivity.class);
                        intent.putExtra("searchMsg", searchwold.getText().toString().trim());
                        startActivity(intent);
                    }
                });
            }
            searchHistoryWorldAdapter.notifyDataSetChanged();
            //------搜索历史recycleview适配器 end----------
        }


    }

    public View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbHelper.delete(sqliteDatabase);
            clearHistorySearhPopu.dismiss();
            searchHistoryWord.clear();
            setAdapter();
        }
    };

    /*//控制手机自带的返回键按钮
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Toast.makeText(this,"我点击了手机自带的返回键",Toast.LENGTH_SHORT).show();
            Intent intentToHomePage = new Intent(HomeSearchActivity.this, BaseActivity.class);
            startActivity(intentToHomePage);
        }
        return true;
    }*/

    //调取接口获取结果集
    public void selectHotSearchWord() {
        Net.get().selectHotSearchWord().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hotSearchWorld -> {
                    hotWord=new ArrayList<>();
                    if(hotSearchWorld.getCode()==200){
                        hotWord=hotSearchWorld.getResultData();
                        setAdapter();
                    }
                    else{
                        Toast.makeText(this,"请求失败",Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });

    }


    private void getSearchHistoryData() {
        Cursor cursor = dbHelper.select(sqliteDatabase);
        if (searchHistoryWord == null) {
            searchHistoryWord = new ArrayList<>();
        }
        searchHistoryWord.clear();
        //将光标移动到下一行，从而判断该结果集是否还有下一条数据
        //如果有则返回true，没有则返回false
        while (cursor.moveToNext()) {
            searchHistoryWord.add(cursor.getString(cursor.getColumnIndex("name")));
        }
    }



}
