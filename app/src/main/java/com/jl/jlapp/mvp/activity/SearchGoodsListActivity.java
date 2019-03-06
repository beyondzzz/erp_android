package com.jl.jlapp.mvp.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.BrandOrderByRecyclerAdapter;
import com.jl.jlapp.adapter.SideslipBrandAdapter;
import com.jl.jlapp.adapter.SideslipClassifityAdapter;
import com.jl.jlapp.eneity.ClassificationModel;
import com.jl.jlapp.mvp.fragment.GoodsListFragment;
import com.jl.jlapp.mvp.fragment.NotFoundGoodsListFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CustomGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchGoodsListActivity extends FragmentActivity implements View.OnClickListener, BrandOrderByRecyclerAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView, sideslipBrandContent, sideslipClassifyContent;
    public PopupWindow popupwindow;
    BrandOrderByRecyclerAdapter recyclerAdapter;
    SideslipBrandAdapter sideslipBrandAdapter;
    SideslipClassifityAdapter sideslipClassifityAdapter;
    TextView goodsSearchRailingBottom, brandSearchOkBtn, brandSearchResetBtn, brandBtnText, popupShadow, shaixuanBtn, onlyLookHasGoodsBtn, sideslipClassifyAllBtn, sideslipBrandAllBtn;
    ImageView brandBtnUpordownImg,closeBtnImg;
    EditText minPriceText, maxPriceText;
    LinearLayout brandSearchPopuBtn, sideslipOkBtn, sideslipResetBtn;
    RelativeLayout sideslipClassifyContentRelative;
    RadioButton synthesizedBtn, saleNumBtn, priceBtn;
    FrameLayout shopListFragment;
    @BindView(R.id.goods_list_top_search_text)
    TextView goodsListTopSearchText;
    @BindView(R.id.iv_back_left)
    ImageView ivBackLeft;

    View checkedtianchuView;
    // 抽屉菜单对象
    private ActionBarDrawerToggle drawerbar;
    public DrawerLayout drawerLayout;
    private RelativeLayout main_right_drawer_layout;

    //加载碎片
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    boolean isCheckOnlyLookHasGoods = true;//仅看有货的按钮是否被选中，默认为选中状态
    List<String> sideSlipClickBrand;//在侧滑栏里点击的品牌
    List<String> sideSlipCeckedBrand;//在侧滑栏里点击确定选择的品牌
    Map<String, Object> sideSlipCeckedClassifity;//在侧滑栏里点击确定选择的分类
    Map<String, Object> sideSlipClickClassifity;//在侧滑栏里点击的分类
    public static int index = 0;

    /*碎片*/
    Fragment notFoundGoodsListFragment, goodsListFragment;

    List<Map<String, Object>> brandMsgList = new ArrayList<>();//选中的品牌但还没有点击确定
    Map<String, Object> brandMsgMap;
    List<Map<String, Object>> chechedBrandMsgList = new ArrayList<>();//最终确定的品牌

    List<ClassificationModel.ResultDataBean> classificationModels = new ArrayList<>();//保存通过接口获取的一二级分类的信息
    List<ClassificationModel.ResultDataBean.TwoClassificationsBean> twoClassificationModels = new ArrayList<>();//分离出来的二级分类
    List<ClassificationModel.ResultDataBean.TwoClassificationsBean> twoClassificationModelsTwo = new ArrayList<>();//分离出来的二级分类(前两个)
    List<String> brands=new ArrayList<>();//保存获取到的品牌信息
    List<String> brandsThree=new ArrayList<>();//保存获取到的品牌信息(前三个)

    boolean brandsVisibleOrGone=false;//点击品牌的收起或展示  false：收起  true：展示 默认收起
    boolean classificationVisibleOrGone=false;//点击分类的收起或展示  false：收起  true：展示 默认收起

    public String mSearchWorld = "";//搜索页搜索的内容
    public String priceSort = "";//根据价格排序时 是倒序还是正序（asc：正序，desc：倒序）默认为""
    public Integer sortType = 1;//页面list排序(1:综合排序，2：销量排序，3：价格排序，4：热门分类)，默认传1
    public String isHasGoods = "true";//是否仅查看有货("true":是，"false"：否),默认传true
    public double minPrice = 0;//价格区间的最低价(不设置的话传0)，默认传0
    public double maxPrice = 0;//价格区间的最高价(不设置的话传0)，默认传0
    public String brandName = "all";//品牌名称(全部传 all)，默认传 all
    public Integer classificationId = 0;//二级分类id(全部传0)，默认传0


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods_list);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        mSearchWorld = intent.getStringExtra("searchMsg");
        sortType = intent.getIntExtra("sortType", 1);
        classificationId = intent.getIntExtra("classificationId", 0);



        //初始化组件
        init();
        //给控件添加数据
        setDateToControl();
        //设置popupwindow的一系列东西
        setPopupWindow();
        //设置监听事件
        setListener();
        //获取品牌信息
        getBrands();

        //刚进页面是的fragment的控制
        replaceFragment();

        //通过接口获取一二级分类的信息
        getClassificationMsg();



        //popupwindow的消失监听事件
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //还原商品列表的背景色以及透明度
               /* shopListFragment.setBackgroundColor(getResources().getColor(R.color.white));
                shopListFragment.setAlpha(0);*/
                //设置品牌按钮的颜色以及旁边的小图标
                brandBtnText.setTextColor(getResources().getColor(R.color.basic_wheelview_color));
                brandBtnUpordownImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_triangle_down));
            }
        });


    }

    //初始化组件
    public void init() {
        goodsSearchRailingBottom = findViewById(R.id.goods_search_railing_bottom);
        brandSearchPopuBtn = findViewById(R.id.brand_search_popu_btn);
        brandBtnText = findViewById(R.id.brand_btn_text);
        brandBtnUpordownImg = findViewById(R.id.brand_btn_upordown_img);
        shopListFragment = findViewById(R.id.shop_list_fragment);
        synthesizedBtn = findViewById(R.id.shop_list_synthesized_text);
        saleNumBtn = findViewById(R.id.shop_list_sale_num_text);
        priceBtn = findViewById(R.id.shop_list_price_text);
        shaixuanBtn = findViewById(R.id.tv_shaixuan);
        onlyLookHasGoodsBtn = findViewById(R.id.only_look_has_goods_btn);
        sideslipBrandContent = findViewById(R.id.sideslip_brand_content);
        sideslipClassifyContent = findViewById(R.id.sideslip_classify_content);
        sideslipOkBtn = findViewById(R.id.sideslip_ok_btn);
        sideslipResetBtn = findViewById(R.id.sideslip_reset_btn);
        minPriceText = findViewById(R.id.sideslip_min_price);
        minPriceText.setInputType(EditorInfo.TYPE_CLASS_PHONE);//设置数字键盘
        minPriceText.setInputType(InputType.TYPE_CLASS_NUMBER);
        maxPriceText = findViewById(R.id.sideslip_max_price);
        maxPriceText.setInputType(EditorInfo.TYPE_CLASS_PHONE);//设置数字键盘
        maxPriceText.setInputType(InputType.TYPE_CLASS_NUMBER);
        sideslipClassifyAllBtn = findViewById(R.id.sideslip_classify_all_btn);
        sideslipBrandAllBtn = findViewById(R.id.sideslip_brand_all_btn);
        sideslipClassifyContentRelative = findViewById(R.id.relativeLayout_contect);
        closeBtnImg=findViewById(R.id.close_btn_img);


        //带有侧滑菜单的主布局的最外层布局
        drawerLayout = (DrawerLayout) findViewById(R.id.goods_search_drawer_layout);

    }


    //给控件设置监听事件
    public void setListener() {
        brandSearchOkBtn.setOnClickListener(this);
        brandSearchPopuBtn.setOnClickListener(this);
        brandSearchResetBtn.setOnClickListener(this);
        popupShadow.setOnClickListener(this);
        shaixuanBtn.setOnClickListener(this);
        onlyLookHasGoodsBtn.setOnClickListener(this);
        sideslipOkBtn.setOnClickListener(this);
        sideslipResetBtn.setOnClickListener(this);
        sideslipClassifyAllBtn.setOnClickListener(this);
        sideslipBrandAllBtn.setOnClickListener(this);
        goodsListTopSearchText.setOnClickListener(this);
        synthesizedBtn.setOnClickListener(this);
        saleNumBtn.setOnClickListener(this);
        priceBtn.setOnClickListener(this);
        ivBackLeft.setOnClickListener(this);
        closeBtnImg.setOnClickListener(this);
    }

    public void replaceFragment() {
        Log.d("aaaaaaActivity", "replaceFragment");
        // notFoundGoodsListFragment = new NotFoundGoodsListFragment();
        transaction = fragmentManager.beginTransaction();
        goodsListFragment = new GoodsListFragment();

        transaction.replace(R.id.shop_list_fragment, goodsListFragment);
        transaction.commit();
    }

    //给控件设置适配器
    public void setBeandAdapter() {
        //----给侧滑栏里的品牌内容设置适配器 start-------
        //设置RecyclerView的布局方式
        GridLayoutManager gridLayoutManager = new CustomGridLayoutManager(this, 3, false);
        sideslipBrandContent.setLayoutManager(gridLayoutManager);

        //初始化适配器
        /*if (sideslipBrandAdapter == null) {
            Log.d("brandssize",brands.size()+"");*/
        //操作要展示
        if (brandsVisibleOrGone) {
            sideslipBrandAdapter = new SideslipBrandAdapter(R.layout.shop_list_brand_search_item, brands);
        }
        //操作要收起
        else{
            sideslipBrandAdapter = new SideslipBrandAdapter(R.layout.shop_list_brand_search_item, brandsThree);
        }

            sideslipBrandAdapter.setChechedName(sideSlipClickBrand);
            sideslipBrandContent.setAdapter(sideslipBrandAdapter);
       /* } else {*/
            //sideslipBrandAdapter.setChechedName(sideSlipClickBrand);
        /*    sideslipBrandAdapter.notifyDataSetChanged();
        }*/

        sideslipBrandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView t = view.findViewById(R.id.brand_name);
                String aa = (String) t.getText();
                int size = sideSlipClickBrand.size();
                for (int i = 0; i < sideSlipClickBrand.size(); i++) {
                    if (sideSlipClickBrand.get(i).equals(aa)) {
                        sideSlipClickBrand.remove(i);
                        break;
                    }
                }
                if (size == sideSlipClickBrand.size()) {
                    sideSlipClickBrand.add(aa);
                }

                sideslipBrandAdapter.setChechedName(sideSlipClickBrand);
                sideslipBrandAdapter.notifyDataSetChanged();


            }
        });
        //----给侧滑栏里的品牌内容设置适配器 end-------
    }

    public void setClassifityAdapter() {
        //----给侧滑栏里的分类内容设置适配器 start-------
        //设置RecyclerView的布局方式
        GridLayoutManager gridLayoutManager2 = new CustomGridLayoutManager(this, 2, false);
        sideslipClassifyContent.setLayoutManager(gridLayoutManager2);
        //初始化适配器
       // if (sideslipClassifityAdapter == null) {
        //操作要展示
        if (classificationVisibleOrGone) {
            sideslipClassifityAdapter = new SideslipClassifityAdapter(R.layout.shop_list_brand_search_item, twoClassificationModels);
        }
        //操作要收起
        else{
            sideslipClassifityAdapter = new SideslipClassifityAdapter(R.layout.shop_list_brand_search_item, twoClassificationModelsTwo);
        }

        sideslipClassifityAdapter.setCheckedClassifity(sideSlipCeckedClassifity);
        sideslipClassifyContent.setAdapter(sideslipClassifityAdapter);
       /* } else {
            sideslipClassifityAdapter.setCheckedClassifity(sideSlipClickClassifity);
            sideslipClassifityAdapter.notifyDataSetChanged();
        }*/


        sideslipClassifityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("aaaaaaa", "我选择了分类");

                TextView cName = view.findViewById(R.id.brand_name);
                TextView cId = view.findViewById(R.id.classifity_id);
                String classifityName = cName.getText().toString().trim();
                Integer classifityId = Integer.parseInt(cId.getText().toString().trim());

                //选中
                if (sideSlipClickClassifity.get("classifityName") != null) {
                    if (!sideSlipClickClassifity.get("classifityName").equals(classifityName) && ((Integer) sideSlipClickClassifity.get("classifityId")) != classifityId) {
                        sideSlipClickClassifity.put("classifityName", classifityName);
                        sideSlipClickClassifity.put("classifityId", classifityId);

                    }
                    //取消选中
                    else {
                        sideSlipClickClassifity.put("classifityName", "");
                        sideSlipClickClassifity.put("classifityId", 0);
                    }
                } else {
                    sideSlipClickClassifity.put("classifityName", classifityName);
                    sideSlipClickClassifity.put("classifityId", classifityId);
                }

                Log.d("aaaaaaa", "我选择de 分类:" + sideSlipClickClassifity.get("classifityName"));
                sideslipClassifityAdapter.setCheckedClassifity(sideSlipClickClassifity);
                sideslipClassifityAdapter.notifyDataSetChanged();


            }
        });
        //----给侧滑栏里的分类内容设置适配器 end-------
    }

    //给控件设置数据
    public void setDateToControl() {
        goodsListTopSearchText.setText(mSearchWorld);
        //----仅看有货的按钮默认被选中 start---
        Drawable drawableLeft = getResources().getDrawable(
                R.drawable.checkmark_m);
        onlyLookHasGoodsBtn.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        onlyLookHasGoodsBtn.setCompoundDrawablePadding(-50);//设置图片和text之间的间距

        onlyLookHasGoodsBtn.setPadding(20, 0, 0, 0);//设置整体的padding
        onlyLookHasGoodsBtn.setBackgroundResource(R.drawable.goods_check_button_m_checked);
        onlyLookHasGoodsBtn.setTextColor(getResources().getColor(R.color.checkGreenColor));
        //----仅看有货的按钮默认被选中 end---

        //-----侧滑菜单start---
        //设置菜单内容之外其他区域的背景色
        drawerLayout.setScrimColor(getResources().getColor(R.color.transparent_50));
        // 关闭手势侧边滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //右边菜单
        main_right_drawer_layout = (RelativeLayout) findViewById(R.id.main_right_drawer_layout);
        synthesizedBtn.setChecked(true);
        //-----侧滑菜单end---

        sideSlipClickBrand = new ArrayList<>();
        sideSlipClickClassifity = new HashMap<>();
        sideSlipCeckedBrand = new ArrayList<>();
        sideSlipCeckedClassifity = new HashMap<>();


    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //页面上的品牌按钮
            case R.id.brand_search_popu_btn:
                //调用显示或隐藏弹出框的方法；
                showOrDismissPopup();
                break;
            //弹窗里的确定按钮
            case R.id.brand_search_ok_btn:
                //关闭弹框
                popupwindow.dismiss();
                //若有选中的品牌，此时把数据移到chechedBrandMsgList中
                chechedBrandMsgList.clear();
                if (brandMsgList.size() > 0) {
                    for (Map<String, Object> map : brandMsgList) {
                        chechedBrandMsgList.add(map);
                    }
                }
                if(chechedBrandMsgList.size()>0){
                    brandName="";
                    for(int i=0;i<chechedBrandMsgList.size();i++){
                        brandName+=chechedBrandMsgList.get(i).get("brandName");
                        if(i<chechedBrandMsgList.size()-1){
                            brandName+=",";
                        }
                    }
                }
                else{
                    brandName="all";
                }

                replaceFragment();
                //Toast.makeText(this, "该功能正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            //弹窗里的重置按钮
            case R.id.brand_search_reset_btn:
                //清空数据
                chechedBrandMsgList.clear();
                brandMsgList.clear();
                //刷新弹窗里的数据源
                recyclerAdapter.setCheckedBrandMsgList(chechedBrandMsgList);
                recyclerAdapter.notifyDataSetChanged();
                break;
            //点击弹窗下部分的阴影部分
            case R.id.popup_shadow:
                //关闭弹框
                popupwindow.dismiss();
                break;
            //页面中的筛选按钮，点击出现侧滑栏
            case R.id.tv_shaixuan:

                //定义侧滑栏的监听事件
                if (drawerbar == null) {
                    drawerbar = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.open, R.string.close) {
                        //菜单打开
                        @Override
                        public void onDrawerOpened(View drawerView) {
                            super.onDrawerOpened(drawerView);
                        }

                        // 菜单关闭
                        @Override
                        public void onDrawerClosed(View drawerView) {
                            super.onDrawerClosed(drawerView);
                        }
                    };
                    drawerLayout.setDrawerListener(drawerbar);
                }
                //侧滑栏打开或关闭
                if (drawerLayout.isDrawerOpen(main_right_drawer_layout)) {
                    drawerLayout.closeDrawer(main_right_drawer_layout);
                } else {
                    Drawable drawableRight = getResources().getDrawable(
                            R.drawable.up);
                    sideslipBrandAllBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                    sideslipBrandContent.setVisibility(View.VISIBLE);
                    brandsVisibleOrGone=false;
                    setBeandAdapter();

                    Drawable drawableRight2 = getResources().getDrawable(
                            R.drawable.up);
                    sideslipClassifyAllBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight2, null);
                    sideslipClassifyContent.setVisibility(View.VISIBLE);
                    classificationVisibleOrGone=false;
                    setClassifityAdapter();
                    drawerLayout.openDrawer(main_right_drawer_layout);
                }
                if (sideSlipCeckedBrand.size() == 0) {
                    sideSlipClickBrand.clear();
                }
                if ("".equals(sideSlipCeckedClassifity.get("classifityName"))) {
                    sideSlipClickClassifity.put("classifityName", "");
                    sideSlipClickClassifity.put("classifityId", 0);
                }

                sideslipBrandAdapter.setChechedName(sideSlipCeckedBrand);
                sideslipBrandAdapter.notifyDataSetChanged();
                sideslipClassifityAdapter.setCheckedClassifity(sideSlipCeckedClassifity);
                sideslipClassifityAdapter.notifyDataSetChanged();
                break;
            //侧滑栏里的仅看有货按钮
            case R.id.only_look_has_goods_btn:
                //仅看有货的按钮是否被选中,此时要取消选中
                if (isCheckOnlyLookHasGoods) {
                    onlyLookHasGoodsBtn.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, null, null);
                    /*onlyLookHasGoodsBtn.setCompoundDrawablePadding(0);//设置图片和text之间的间距

                    onlyLookHasGoodsBtn.setPadding(0, 0, 0, 0);//设置整体的padding*/
                    onlyLookHasGoodsBtn.setBackgroundResource(R.drawable.goods_check_button_m);
                    onlyLookHasGoodsBtn.setTextColor(getResources().getColor(R.color.trans_333333));
                    isCheckOnlyLookHasGoods = false;

                }
                //没被选中，此时改变成选中状态
                else {
                    Drawable drawableLeft = getResources().getDrawable(
                            R.drawable.checkmark_m);
                    onlyLookHasGoodsBtn.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    onlyLookHasGoodsBtn.setCompoundDrawablePadding(-50);//设置图片和text之间的间距

                    onlyLookHasGoodsBtn.setPadding(20, 0, 0, 0);//设置整体的padding
                    onlyLookHasGoodsBtn.setBackgroundResource(R.drawable.goods_check_button_m_checked);
                    onlyLookHasGoodsBtn.setTextColor(getResources().getColor(R.color.checkGreenColor));
                    isCheckOnlyLookHasGoods = true;
                }

                break;
            //侧滑栏的确认按钮
            case R.id.sideslip_ok_btn:
                if (drawerLayout.isDrawerOpen(main_right_drawer_layout)) {
                    drawerLayout.closeDrawer(main_right_drawer_layout);
                } else {
                    drawerLayout.openDrawer(main_right_drawer_layout);
                }
                sideSlipCeckedBrand.clear();
                if (sideSlipClickBrand.size() > 0) {

                    for (String brand : sideSlipClickBrand) {
                        sideSlipCeckedBrand.add(brand);
                    }
                }
                if (sideSlipCeckedBrand.size()>0) {
                    brandName="";
                    for(int i=0;i<sideSlipCeckedBrand.size();i++){
                        brandName+=sideSlipCeckedBrand.get(i);
                        if(i<sideSlipCeckedBrand.size()-1){
                            brandName+=",";
                        }
                    }
                }
                else{
                    brandName="all";
                }


                if (sideSlipClickClassifity.get("classifityName") != null) {
                    if (!"".equals(sideSlipClickClassifity.get("classifityName")) && (Integer) sideSlipClickClassifity.get("classifityId") != 0) {
                        sideSlipCeckedClassifity.put("classifityName", sideSlipClickClassifity.get("classifityName"));
                        sideSlipCeckedClassifity.put("classifityId", (Integer) sideSlipClickClassifity.get("classifityId"));
                    }
                    Log.d("aaaaaaaa", "classifityName:" + sideSlipCeckedClassifity.get("classifityName") + " classifityId:" + sideSlipCeckedClassifity.get("classifityId"));
                    classificationId = (Integer) sideSlipCeckedClassifity.get("classifityId");
                }
                else{
                    classificationId=0;
                }


                isHasGoods = isCheckOnlyLookHasGoods + "";

                if ("".equals(minPriceText.getText().toString().trim())) {
                    minPrice = 0.0;
                } else {
                    minPrice = Double.parseDouble(minPriceText.getText().toString().trim());
                }
                if ("".equals(maxPriceText.getText().toString().trim())) {
                    maxPrice = 0.0;
                } else {
                    maxPrice = Double.parseDouble(maxPriceText.getText().toString().trim());
                }
                if (minPrice > 0 && maxPrice > 0) {
                    //判断数值的大小
                    if (maxPrice < minPrice) {
                        double flag = maxPrice;
                        maxPrice = minPrice;
                        minPrice = flag;
                    }
                }
                replaceFragment();
                break;
            //侧滑栏里的重置按钮
            case R.id.sideslip_reset_btn:
                sideSlipCeckedBrand.clear();
                sideSlipCeckedClassifity.clear();
                sideSlipClickBrand.clear();
                sideSlipClickClassifity.clear();
                sideslipBrandAdapter.notifyDataSetChanged();
                sideslipClassifityAdapter.notifyDataSetChanged();
                //----仅看有货的按钮默认被选中 start---
                Drawable drawableLeft = getResources().getDrawable(
                        R.drawable.checkmark_m);
                onlyLookHasGoodsBtn.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
                onlyLookHasGoodsBtn.setCompoundDrawablePadding(-50);//设置图片和text之间的间距

                onlyLookHasGoodsBtn.setPadding(20, 0, 0, 0);//设置整体的padding
                onlyLookHasGoodsBtn.setBackgroundResource(R.drawable.goods_check_button_m_checked);
                onlyLookHasGoodsBtn.setTextColor(getResources().getColor(R.color.checkGreenColor));
                isCheckOnlyLookHasGoods = true;
                //----仅看有货的按钮默认被选中 end---

                minPriceText.setText("");
                maxPriceText.setText("");
                break;
            //侧滑栏里的品牌全部按钮,隐藏或显示品牌框
            case R.id.sideslip_brand_all_btn:
                if (!brandsVisibleOrGone) {
                    Drawable drawableRight = getResources().getDrawable(
                            R.drawable.down);
                    sideslipBrandAllBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                   // sideslipBrandContent.setVisibility(View.GONE);
                    brandsVisibleOrGone=true;
                    setBeandAdapter();

                } else {
                    Drawable drawableRight = getResources().getDrawable(
                            R.drawable.up);
                    sideslipBrandAllBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                   // sideslipBrandContent.setVisibility(View.VISIBLE);
                    brandsVisibleOrGone=false;
                    setBeandAdapter();

                }

                break;
            //侧滑栏里的分类全部按钮,隐藏或显示全部分类
            case R.id.sideslip_classify_all_btn:
                if (!classificationVisibleOrGone) {
                    Drawable drawableRight2 = getResources().getDrawable(
                            R.drawable.down);
                    sideslipClassifyAllBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight2, null);
                   // sideslipClassifyContent.setVisibility(View.GONE);
                    classificationVisibleOrGone=true;
                    setClassifityAdapter();
                } else {
                    Drawable drawableRight2 = getResources().getDrawable(
                            R.drawable.up);
                    sideslipClassifyAllBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight2, null);
                   // sideslipClassifyContent.setVisibility(View.VISIBLE);
                    classificationVisibleOrGone=false;
                    setClassifityAdapter();
                }

                break;
            case R.id.close_btn_img:
                drawerLayout.closeDrawer(main_right_drawer_layout);
                break;
            //头部的搜索框
            case R.id.goods_list_top_search_text:
                Intent intent = new Intent(this, HomeSearchActivity.class);
                intent.putExtra("oldSearchWorld", mSearchWorld);
                startActivity(intent);
                break;
            //综合按钮
            case R.id.shop_list_synthesized_text:
                // Toast.makeText(this, "我点击了综合", Toast.LENGTH_SHORT).show();
                sortType = 1;
                priceSort = "";
                replaceFragment();
                break;
            //销量按钮
            case R.id.shop_list_sale_num_text:
                //Toast.makeText(this, "我点击了销量", Toast.LENGTH_SHORT).show();

                sortType = 2;
                priceSort = "";
                replaceFragment();

                break;
            //价格按钮
            case R.id.shop_list_price_text:
                // Toast.makeText(this, "我点击了价格", Toast.LENGTH_SHORT).show();
                if ("".equals(priceSort)) {
                    priceSort = "asc";
                } else if ("asc".equals(priceSort)) {
                    priceSort = "desc";
                } else {
                    priceSort = "asc";
                }
                sortType = 3;
                replaceFragment();
                break;
            //点击页面里的返回按钮
            case R.id.iv_back_left:
                /*Intent intentToHomePage = new Intent(SearchGoodsListActivity.this, BaseActivity.class);
                startActivity(intentToHomePage);*/
                finish();
                break;

        }
    }

    /**
     * 设置popupwindow的一系列东西
     */
    public void setPopupWindow() {
        //获取要显示在popupwindow里的内容
        checkedtianchuView = LayoutInflater.from(this).inflate(
                R.layout.shop_list_brand_search_fragment, null);
        //初始化popupwindow
        popupwindow = new PopupWindow(this);
        mRecyclerView = (RecyclerView) checkedtianchuView.findViewById(R.id.shop_brand_search);
        brandSearchOkBtn = checkedtianchuView.findViewById(R.id.brand_search_ok_btn);
        brandSearchResetBtn = checkedtianchuView.findViewById(R.id.brand_search_reset_btn);
        popupShadow = checkedtianchuView.findViewById(R.id.popup_shadow);
    }

    /**
     * 设置popupwindow的状态，是显示还是隐藏
     */
    public void showOrDismissPopup() {
        //取消popupwindow的背景
        popupwindow.setBackgroundDrawable(null);
        if (!popupwindow.isShowing()) {
            addDataToPopupwindow();
            popupwindow.setHeight(shopListFragment.getHeight());
            popupwindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            popupwindow.setContentView(checkedtianchuView);
            popupwindow.showAsDropDown(goodsSearchRailingBottom);
            // 设置此参数获取焦点
            popupwindow.setFocusable(true);
            //设置点击popupwindow外部的部分消失
            popupwindow.setOutsideTouchable(true);
            /*shopListFragment.setBackgroundColor(getResources().getColor(R.color.black));
            shopListFragment.setAlpha(0.5f);*/
            //设置品牌按钮的颜色以及旁边的小图标
            brandBtnText.setTextColor(getResources().getColor(R.color.checkGreenColor));
            brandBtnUpordownImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_triangle_up));

        } else {

            addDataToPopupwindow();

        }

    }

    /**
     * 给popoupwindow添加数据
     */
    public void addDataToPopupwindow() {
        if (!popupwindow.isShowing()) {
            //设置布局管理器 网格布局
            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
            //设置布局的排版方向
            mRecyclerView.setLayoutManager(layoutManager);
            // 如果recyclerAdapter的适配器不存在，就创建，如果存在，直接更新
            if (recyclerAdapter == null) {
                //绑定适配器
                recyclerAdapter = new BrandOrderByRecyclerAdapter(this, brands);
                mRecyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.setOnItemClickListener(this);//将接口传递到数据产生的地方
            }

            recyclerAdapter.setCheckedBrandMsgList(chechedBrandMsgList);
            recyclerAdapter.notifyDataSetChanged();
        } else {
            //关闭弹框
            popupwindow.dismiss();
        }
    }

    /**
     * item点击的事件
     *
     * @param childAdapterPosition   点击的第几个item
     * @param isCheckOrRemoveChecked 是否被选中/取消选中(0:取消选中，1：选中)
     * @param data                   品牌名称
     */
    @Override
    public void onItemClick(int childAdapterPosition, int isCheckOrRemoveChecked, String data) {
        // Log.e(TAG, "onItemClick: " + position);
        brandMsgMap = new HashMap<>();

        if (isCheckOrRemoveChecked == 0) {
            int brandSize = brandMsgList.size();
            //判断取消选中的那一项是否存在于选中的结果集中，若存在，则移除
            for (int i = 0; i < brandMsgList.size(); i++) {

                if ((int) brandMsgList.get(i).get("itemPosition") == childAdapterPosition) {
                    brandMsgList.remove(i);
                    break;
                }
            }


        } else if (isCheckOrRemoveChecked == 1) {
            brandMsgMap.put("itemPosition", childAdapterPosition);
            brandMsgMap.put("brandName", data);
            brandMsgList.add(brandMsgMap);
        }

    }

    public void replaceNoDataFrag() {
        transaction = fragmentManager.beginTransaction();
        if (notFoundGoodsListFragment == null) {
            notFoundGoodsListFragment = new NotFoundGoodsListFragment();
        }
        transaction.add(R.id.shop_list_fragment, notFoundGoodsListFragment);
        transaction.commit();
    }

    /*//控制手机自带的返回键按钮
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Toast.makeText(this,"我点击了手机自带的返回键",Toast.LENGTH_SHORT).show();
            Intent intentToHomePage = new Intent(SearchGoodsListActivity.this, BaseActivity.class);
            startActivity(intentToHomePage);
        }
        return true;
    }*/

    //通过接口获取一二级分类的信息
    public void getClassificationMsg() {
        Net.get().getClassificationMsg().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(classificationModel -> {
                    if (classificationModel.getCode() == 200) {
                        classificationModels = classificationModel.getResultData();
                        getTwoClassification(classificationModels);
                    } else {
                        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                    setClassifityAdapter();
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //从一二级分类中获取所有一级分类里的信息
    public void getTwoClassification(List<ClassificationModel.ResultDataBean> classificationModelList) {
        for (int i = 0; i < classificationModelList.size(); i++) {
            List<ClassificationModel.ResultDataBean.TwoClassificationsBean> twoClassificationsBeans = classificationModelList.get(i).getTwoClassifications();
            if (twoClassificationsBeans.size() > 0) {
                for (int tc = 0; tc < twoClassificationsBeans.size(); tc++) {
                    classificationModelList.get(i).getTwoClassifications().get(tc).setOneClassificationsName(classificationModelList.get(i).getName());
                    twoClassificationModels.add(classificationModelList.get(i).getTwoClassifications().get(tc));
                }

            }
        }
        if(twoClassificationModels.size()>=2){
            for(int k=0;k<2;k++) {
                twoClassificationModelsTwo.add(twoClassificationModels.get(k));
            }
        }
        else{
            for(int k=0;k<twoClassificationModels.size();k++) {
                twoClassificationModelsTwo.add(twoClassificationModels.get(k));
            }
        }

    }

    /*获取品牌信息*/
    public void getBrands() {
        Net.get().getBrands().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodsBrand -> {
                    if (goodsBrand.getCode() == 200) {
                        brands = goodsBrand.getResultData();
                        if(brands.size()>=3){
                            for(int i=0;i<3;i++){
                                brandsThree.add(brands.get(i));
                            }
                        }
                        else{
                            for(int i=0;i<brands.size();i++){
                                brandsThree.add(brands.get(i));
                            }
                        }

                        Log.d("brandssize","已获取数据："+brands.size());
                        Log.d("brandssize","已获取数据3333："+brandsThree.size());
                        //给品牌控件添加适配器
                        setBeandAdapter();
                    } else {
                        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                    setClassifityAdapter();
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


    /**
     * 模拟获取数据源
     *
     * @return
     */
    /*public List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("品牌" + i);
        }
        return data;
    }*/


}
