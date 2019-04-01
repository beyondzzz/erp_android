package com.jl.jlapp.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.GoodsListAdapter3;
import com.jl.jlapp.adapter.MyFragmentPagerAdapter;
import com.jl.jlapp.eneity.AdvertisementByIdModel;
import com.jl.jlapp.eneity.EffectAdvertisementByTypeModel;
import com.jl.jlapp.eneity.EffectPreSellActivityInformationModel;
import com.jl.jlapp.eneity.GoodsListModel;
import com.jl.jlapp.eneity.News_ListModel;
import com.jl.jlapp.mvp.activity.ActivityMessageListActivity;
import com.jl.jlapp.mvp.activity.ActivityNameActivity;
import com.jl.jlapp.mvp.activity.AfterSaleApplyActivity;
import com.jl.jlapp.mvp.activity.EvaluationActivity;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.HomeSearchActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.MessageCenterActivity;
import com.jl.jlapp.mvp.activity.NoSortGoodsListActivity;
import com.jl.jlapp.mvp.activity.NoSortGoodsListForNewsActivity;
import com.jl.jlapp.mvp.activity.OrderDetailActivity;
import com.jl.jlapp.mvp.activity.ScreenActivity;
import com.jl.jlapp.mvp.activity.SearchGoodsListActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.CityPopu;
import com.jl.jlapp.utils.CountDownTimerUtils;
import com.jl.jlapp.utils.CountDownTimerUtilsForLimitAD;
import com.jl.jlapp.utils.CustomGridLayoutManager;
import com.jl.jlapp.utils.Tools;
import com.jl.jlapp.utils.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.CityPickerDialogFragment;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-17.
 */

public class Fragment_FirstPages extends Fragment implements View.OnClickListener {

    Banner banner;
    List images ;
    @BindView(R.id.presell_banner)
    Banner presellbanner;
    List presellImages ;

    ViewPager viewpager;
    ArrayList<Fragment> listData;//viewPager中三个gridview
    ArrayList<ImageView> imgList;//viewPager中三个小圆点
    //    MyFragmentPagerAdapter mpagerAdapter;//自定义的FragmentPagerAdapter
    ImageView img1, img2, img3;
    View view;

    @BindView(R.id.bottom_goods_list_view)
    RecyclerView bottomGoodsListView;
    @BindView(R.id.icon_message)
    ImageView message;
    @BindView(R.id.search_main)
    LinearLayout searchMain;
    @BindView(R.id.location_main)
    LinearLayout location_main;

    @BindView(R.id.banner_limit_layout)
    RelativeLayout banner_limit_layout;
    @BindView(R.id.end_time)
    TextView end_time;
    private long countdownTime ;//倒计时的总时间(单位:毫秒)
    private String timefromServer;//从服务器获取的订单生成时间
    private long chaoshitime;//从服务器获取订单有效时长(单位:毫秒)

    GoodsListAdapter3 goodsListAdapter;
    List<GoodsListModel.ResultDataBean> goodsListModels ;//保存底部商品列表接口数据
    List<GoodsListModel.ResultDataBean> tempGoodsListModels;//保存底部商品列表接口数据
    List<EffectAdvertisementByTypeModel.ResultDataBean> advertisementListByType4 ;//保存火热促销列表接口数据
    List<EffectAdvertisementByTypeModel.ResultDataBean> advertisementListByType1 ;//保存首页广告列表接口数据
    List<EffectAdvertisementByTypeModel.ResultDataBean> advertisementListByType3 ;//保存限时抢购广告列表接口数据
    public static List<AdvertisementByIdModel.ResultDataBean.GoodsBean> promotionGoodsList ;//保存火热促销商品列表
    List<News_ListModel.ResultDataBean> newGoodsList ;//保存火热促销商品列表

    List<EffectPreSellActivityInformationModel.ResultDataBean> preSellActivityList ;//保存首页广告列表接口数据

    int promotionAdId = -1;//火热促销广告id
    int newGoodsAdId = -1;//新品上架广告id
    int hotSaleAdId = -1;//百度热卖广告id
    int preSaleAdId = -1;//爆品预售广告id
    int flashSaleAdId = -1;//限时抢购广告id
    public static int flashSaleGoodsId =-1;//限时抢购商品id
    @BindView(R.id.ad_promotion)
    ImageView ad_promotion;
    @BindView(R.id.ad_hot_sale)
    ImageView ad_hot_sale;
    @BindView(R.id.ad_presale)
    ImageView ad_presale;
    @BindView(R.id.banner_limit)
    ImageView banner_limit;
    @BindView(R.id.ad_freash)
    ImageView ad_freash;

    @BindView(R.id.rl1)
    ImageView rl1;
    @BindView(R.id.rl2)
    ImageView rl2;
    @BindView(R.id.rl3)
    ImageView rl3;
    @BindView(R.id.rl4)
    ImageView rl4;
    @BindView(R.id.rl5)
    ImageView rl5;
    List<ImageView> imageViews;

    @BindView(R.id.classify_title1)
    TextView classify_title1;
    @BindView(R.id.classify_title2)
    TextView classify_title2;
    @BindView(R.id.classify_title3)
    TextView classify_title3;
    @BindView(R.id.classify_title4)
    TextView classify_title4;
    @BindView(R.id.classify_title5)
    TextView classify_title5;
    List<TextView> textViews ;
    @BindView(R.id.bottom_more)
    TextView bottom_more;
    @BindView(R.id.news_more)
    TextView news_more;
    @BindView(R.id.promotion_more)
    TextView promotion_more;

    @BindView(R.id.city)
    TextView city;

    private List<HotCity> hotCities;
    String  msg="";

    int userId=0;
    CityPopu cityPopu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = inflater.inflate(R.layout.activity_main, null, false);

        //控制注解
        ButterKnife.bind(this, view);

        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));



        //初始化type=4的广告的id供其他模块点击事件传值使用
        getAdvertisementGoodsListByType4();
        textViews = new ArrayList<>();
        imageViews = new ArrayList<>();
        images = new ArrayList();
        presellImages = new ArrayList();

        //新品上架处理
        imageViews.add(rl1);
        imageViews.add(rl2);
        imageViews.add(rl3);
        imageViews.add(rl4);
        imageViews.add(rl5);
        textViews.add(classify_title1);
        textViews.add(classify_title2);
        textViews.add(classify_title3);
        textViews.add(classify_title4);
        textViews.add(classify_title5);

        goodsListModels = new ArrayList<>();//保存底部商品列表接口数据
        tempGoodsListModels = new ArrayList<>();//保存底部商品列表接口数据
        advertisementListByType4 = new ArrayList<>();//保存火热促销列表接口数据
        advertisementListByType1 = new ArrayList<>();//保存首页广告列表接口数据
        advertisementListByType3 = new ArrayList<>();//保存限时抢购广告列表接口数据
        promotionGoodsList = new ArrayList<>();//保存火热促销商品列表
        newGoodsList = new ArrayList<>();//保存火热促销商品列表

        promotionAdId = -1;//火热促销广告id
        newGoodsAdId = -1;//新品上架广告id
        hotSaleAdId = -1;//百度热卖广告id
        preSaleAdId = -1;//爆品预售广告id
        flashSaleAdId = -1;//限时抢购广告id
        flashSaleGoodsId =-1;//限时抢购商品id

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        if (userId>0){
            getMessageNum(userId);
        }
        else{
            message.setImageResource(R.drawable.icon_message_white);
        }


        //设置点击事件监听
        setListener();

        //轮播图处理
        //        initBanner();
        getAdvertisementGoodsListByType1();
        //预售活动轮播图处理
        getAllEffectPreSellActivityInformation();

        //底部商品列表处理
        getBottomGoodsListViewData();

        //限时抢购部分处理
        getAdvertisementGoodsListByType3();
        return view;
    }

    //设置事件
    private void setListener() {
        searchMain.setOnClickListener(this);
        ad_freash.setOnClickListener(this);
        message.setOnClickListener(this);
        bottom_more.setOnClickListener(this);
        news_more.setOnClickListener(this);
        promotion_more.setOnClickListener(this);
        location_main.setOnClickListener(this);
    }
    public View.OnClickListener confirmClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cityPopu.dismiss();
            city.setText("北京");

        }

    };
    //点击事件实现
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.location_main:
//                Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();


                CityPicker.getInstance()
                        .setFragmentManager(getChildFragmentManager())
                        .enableAnimation(true)
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                        .setHotCities(null)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                city.setText(data == null ? city.getText() : data.getName());

                                if(data!=null && !"北京".equals(data.getName())){
                                    cityPopu = new CityPopu(getContext(),confirmClick);
                                    cityPopu.setOutsideTouchable(false);
                                    cityPopu.showAtLocation(getActivity().findViewById(R.id.first_page), Gravity.CENTER,0,0);
                                }
//                                if (data != null) {
//                                    Toast.makeText(
//                                           getActivity(),
//                                            String.format("点击的数据：%s，%s", data.getName(),data.getProvince(), data.getCode()),
//                                            Toast.LENGTH_SHORT)
//                                            .show();
//                                }
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        CityPicker.getInstance()
//                                                .locateComplete(new LocatedCity("北京", "北京", "101010100"),
//                                                        LocateState.SUCCESS);
//                                    }
//                                }, 2000);
                            }
                        })
                        .show();
                break;
            case R.id.search_main:
                intent = new Intent(getActivity(), HomeSearchActivity.class);
                startActivity(intent);
                break;

            case R.id.icon_message:
                //                intent = new Intent(getActivity(), OrderDetailActivity.class);
                //                startActivity(intent);
                if (userId>0){
                    intent = new Intent(getContext(), MessageCenterActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.ad_promotion://热门促销
                //                Toast.makeText(getActivity(), "" + promotionAdId, Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), NoSortGoodsListActivity.class);
                intent.putExtra("title","热门促销");
                intent.putExtra("advertisementInformationId",promotionAdId);
                startActivity(intent);

                break;
            case R.id.ad_freash://限时抢购icon
                //                Toast.makeText(getActivity(), "" + flashSaleAdId, Toast.LENGTH_SHORT).show();
                if(flashSaleGoodsId <=0){
                    Toast.makeText(getActivity(), "暂时还没有限时抢购商品哦，等会儿再来看看吧~" , Toast.LENGTH_SHORT).show();
                }else{
                    intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("goodsId",flashSaleGoodsId);
                    //                Toast.makeText(getActivity(), "" + flashSaleGoodsId, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                break;
            case R.id.ad_hot_sale://百度热卖
                //                Toast.makeText(getActivity(), "" + hotSaleAdId, Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), NoSortGoodsListActivity.class);
                intent.putExtra("title","百度热卖");
                intent.putExtra("advertisementInformationId",hotSaleAdId);
                startActivity(intent);
                break;
            case R.id.ad_presale://期货预售
                //                Toast.makeText(getActivity(), "" + preSaleAdId, Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), NoSortGoodsListActivity.class);
                intent.putExtra("title","期货预售");
                intent.putExtra("advertisementInformationId",preSaleAdId);
                startActivity(intent);
                break;
            case R.id.banner_limit://限时抢购banner
                //                Toast.makeText(getActivity(), "" + flashSaleAdId, Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodsId",flashSaleGoodsId);
                //                Toast.makeText(getActivity(), "" + flashSaleGoodsId, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.bottom_more://底部热门推荐的更多

                intent = new Intent(getActivity(), SearchGoodsListActivity.class);
                intent.putExtra("sortType",4);
                intent.putExtra("classificationId",0);
                intent.putExtra("searchMsg","");
                startActivity(intent);
                break;
            case R.id.news_more://新品上架的更多
                intent = new Intent(getActivity(), NoSortGoodsListForNewsActivity.class);
                intent.putExtra("title","新品上架");
                intent.putExtra("classificationId",0);
                startActivity(intent);
                break;
            case R.id.promotion_more://火热促销的更多
                intent = new Intent(getActivity(), NoSortGoodsListActivity.class);
                intent.putExtra("title","火热促销");
                intent.putExtra("advertisementInformationId",promotionAdId);
                startActivity(intent);
                break;
        }
    }

    private void initBanner(List images) {

        banner = view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                int id = advertisementListByType1.get(position).getId();
                //                Toast.makeText(getActivity(), "id:" + id, Toast.LENGTH_SHORT).show();
                Net.get().getAdvertisementById(id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(AdvertisementByIdModel -> {
                            //                            Log.d("aaaaaaaaaaaa", "banner点击获取数据加载了");
                            int code1 = AdvertisementByIdModel.getCode();
                            if (code1 == 200) {
                                int flag = AdvertisementByIdModel.getResultData().getFlag();
                                switch (flag){
                                    case 0://商品详情页面    goods代表商品详情表信息
                                        if(AdvertisementByIdModel.getResultData().getGoods().size()>0){
                                            int goodsId = AdvertisementByIdModel.getResultData().getGoods().get(0).getId();
                                            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                                            intent.putExtra("goodsId",goodsId);
                                            startActivity(intent);
                                        }

                                        break;
                                    case 1://活动页面   goods代表商品详情表列
                                        int activityInformationId = AdvertisementByIdModel.getResultData().getActivityInformation().getId();
                                        Intent intent=new Intent(getActivity(),ActivityNameActivity.class);
                                        intent.putExtra("activityInformationId",activityInformationId);
                                        startActivity(intent);
                                        //                                       Toast.makeText(getActivity(), "活动页面正在开发中..." , Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            } else {
                                Toast.makeText(getActivity(), "" + AdvertisementByIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                        }, throwable -> {
                            Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                        });
            }
        });

        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initViewPager() {
        // Log.d("aaa","进入方法...");
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        img1 = (ImageView) view.findViewById(R.id.img1);
        img2 = (ImageView) view.findViewById(R.id.img2);
        img3 = (ImageView) view.findViewById(R.id.img3);


        int size = promotionGoodsList.size();
        if (size > 0 && size <= 3) {
            // 添加指示器图片
            imgList = new ArrayList<ImageView>();
            imgList.add(img1);
            img2.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
            // 添加商品列表碎片
            listData = new ArrayList<Fragment>();
            FirstPageHotSaleGoodsListFragment frament1 = new FirstPageHotSaleGoodsListFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("page", 1);
            //            bundle1.putSerializable("data", (Serializable) promotionGoodsList);
            frament1.setArguments(bundle1);
            listData.add(frament1);
        } else if (size > 3 && size <= 6) {
            // 添加指示器图片
            imgList = new ArrayList<ImageView>();
            imgList.add(img1);
            imgList.add(img2);

            img3.setVisibility(View.GONE);
            // 添加商品列表碎片
            listData = new ArrayList<Fragment>();
            FirstPageHotSaleGoodsListFragment frament1 = new FirstPageHotSaleGoodsListFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("page", 1);
            //            bundle1.putSerializable("data", (Serializable) promotionGoodsList);
            frament1.setArguments(bundle1);
            FirstPageHotSaleGoodsListFragment frament2 = new FirstPageHotSaleGoodsListFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("page", 2);
            //            bundle2.putSerializable("data", (Serializable) promotionGoodsList);
            frament2.setArguments(bundle2);
            //FirstPageHotSaleGoodsListFragment frament3=new FirstPageHotSaleGoodsListFragment();
            listData.add(frament1);
            listData.add(frament2);
            //listData.add(frament3);
        } else if (size > 6) {
            // 添加指示器图片
            imgList = new ArrayList<ImageView>();
            imgList.add(img1);
            imgList.add(img2);
            imgList.add(img3);

            // 添加商品列表碎片
            listData = new ArrayList<Fragment>();
            FirstPageHotSaleGoodsListFragment frament1 = new FirstPageHotSaleGoodsListFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("page", 1);
            //            bundle1.putSerializable("data", (Serializable) promotionGoodsList);
            frament1.setArguments(bundle1);
            FirstPageHotSaleGoodsListFragment frament2 = new FirstPageHotSaleGoodsListFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("page", 2);
            //            bundle2.putSerializable("data", (Serializable) promotionGoodsList);
            frament2.setArguments(bundle2);
            FirstPageHotSaleGoodsListFragment frament3 = new FirstPageHotSaleGoodsListFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putInt("page", 3);
            //            bundle3.putSerializable("data", (Serializable) promotionGoodsList);
            frament3.setArguments(bundle3);
            listData.add(frament1);
            listData.add(frament2);
            listData.add(frament3);
        }


        //
        if (listData != null && listData.size() > 0) {
            // viewpager设置适配器
            FragmentPagerAdapter pageradapter = new MyFragmentPagerAdapter(getChildFragmentManager(), listData);
            viewpager.setAdapter(pageradapter);
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    for (int i = 0; i < imgList.size(); i++) {
                        if (i == arg0) {
                            imgList.get(i)
                                    .setBackgroundResource(R.drawable.point_black);
                        } else {
                            imgList.get(i).setBackgroundResource(
                                    R.drawable.point_gray);
                        }
                    }

                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    // arg0值 1:开始滑动 2:滑动结束3:惯性滑动

                }
            });
        }
    }


    private void initBottomGoodsList() {
        //设置RecyclerView的布局方式
        bottomGoodsListView.setLayoutManager(new CustomGridLayoutManager(getActivity(), 2, false));
        //初始化适配器
        goodsListAdapter = new GoodsListAdapter3(getActivity(), tempGoodsListModels);
        //设置适配器
        bottomGoodsListView.setAdapter(goodsListAdapter);

        goodsListAdapter.setOnItemClickListener(new GoodsListAdapter3.OnItemClickListener() {
            @Override
            public void onItemClick(int position, GoodsListModel.ResultDataBean data) {
                //                Toast.makeText(getActivity(),  "data.goodsname:" + data.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodsId",data.getId());
                startActivity(intent);
            }
        });
    }

    /**
     * 获取底部商品列表数据源
     */
    public void getBottomGoodsListViewData() {

        Net.get().getGoodsList(4, "", "", "true", 0.0, 0.0, "all", 0).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodsListModel -> {
                    //                    Log.d("aaaaaaaaaaaa", "getBottomGoodsListViewData数据加载了");
                    int code = goodsListModel.getCode();
                    if (code == 200) {
                        goodsListModels = goodsListModel.getResultData();
                        if(goodsListModels.size()>6){
                            for (int i = 0; i <6 ; i++) {
                                tempGoodsListModels.add(goodsListModels.get(i));
                            }
                        }else{
                            tempGoodsListModels.addAll(goodsListModels);
                        }
                        initBottomGoodsList();
                        //                        initViewPager();
                    } else {
                        Toast.makeText(getActivity(), "" + goodsListModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    //                    Log.d("bbbbbbbbbbbbb", "getBottomGoodsListViewData: ");
                    Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
        //        return goodsListModels;
    }

    /**
     * 获取正在生效的热门分类广告（对应APP首页的四个图标）
     */
    public void getAdvertisementGoodsListByType4() {

        Net.get().getEffectAdvertisementByType(4)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(EffectAdvertisementByTypeModel -> {
                    //                    Log.d("aaaaaaaaaaaa", "getAdvertisementGoodsListByType4数据加载了");
                    int code = EffectAdvertisementByTypeModel.getCode();
                    if (code == 200) {
                        advertisementListByType4 = EffectAdvertisementByTypeModel.getResultData();
                        if (advertisementListByType4.size() > 0) {
                            for (int i = 0; i < advertisementListByType4.size(); i++) {
                                EffectAdvertisementByTypeModel.ResultDataBean item = advertisementListByType4.get(i);
                                String AdName = item.getName();
                                String picUrl = item.getPicUrl();
                                int id = item.getId();
                                switch (AdName) {
                                    case "火热促销":
                                        promotionAdId = id;
                                        Glide
                                                .with(getActivity())
                                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.icon_sale).error(R.drawable.icon_sale))
                                                .load(AppFinal.BASEURL + picUrl)
                                                .into(ad_promotion);

                                        //火热促销商品获取

                                        Net.get().getAdvertisementById(promotionAdId).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(AdvertisementByIdModel -> {
                                                    //                                                    Log.d("aaaaaaaaaaaa", "火热促销商品获取数据加载了");
                                                    int code1 = AdvertisementByIdModel.getCode();
                                                    if (code1 == 200) {
                                                        promotionGoodsList = AdvertisementByIdModel.getResultData().getGoods();
                                                        initViewPager();
                                                    } else {
                                                        Toast.makeText(getActivity(), "" + AdvertisementByIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                                                }, throwable -> {
                                                    //                                                    Log.d("bbbbbbbbbbbbb", "getAdvertisementGoodsListByType4: ");
                                                    Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                                });


                                        break;
                                    case "新品上架":
                                        newGoodsAdId = id;
                                        //新品上架商品获取
                                        Net.get().getNews_List().subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(news_listModel -> {
                                                    //                                                    Log.d("aaaaaaaaaaaa", "新品上架商品获取数据加载了");
                                                    int code1 = news_listModel.getCode();
                                                    if (code1 == 200) {
                                                        newGoodsList = news_listModel.getResultData();

                                                        for (int j = 0; j < newGoodsList.size(); j++) {

                                                            News_ListModel.ResultDataBean  newGoodsItem = newGoodsList.get(j);
                                                            String newGoodsItemPicUrl = newGoodsItem.getPicUrl();
                                                            String newGoodsItemName = newGoodsItem.getName();
                                                            int newGoodsItemId = newGoodsItem.getId();
                                                            ImageView imageView = imageViews.get(j);
                                                            TextView textView = textViews.get(j);
                                                            if(j==0){
                                                                Glide
                                                                        .with(getActivity())
                                                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_new_l).error(R.drawable.img_noimg_new_l))
                                                                        .load(AppFinal.BASEURL + newGoodsItemPicUrl)
                                                                        .into(imageView);
                                                            }else{
                                                                Glide
                                                                        .with(getActivity())
                                                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_new_s).error(R.drawable.img_noimg_new_s))
                                                                        .load(AppFinal.BASEURL + newGoodsItemPicUrl)
                                                                        .into(imageView);
                                                            }
                                                            textView.setText(newGoodsItemName);
                                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    Intent intent = new Intent(getActivity(), NoSortGoodsListForNewsActivity.class);
                                                                    intent.putExtra("title",newGoodsItemName);
                                                                    intent.putExtra("classificationId",newGoodsItemId);
                                                                    startActivity(intent);
                                                                    //                                                                    Toast.makeText(getActivity(), "newGoodsItemId:" + newGoodsItemId, Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            if((j==newGoodsList.size()-1)&&j<imageViews.size()-1){
                                                                for (int k = 1; k <imageViews.size()-j ; k++) {
                                                                    ImageView imageView1 = imageViews.get(imageViews.size()-k);
                                                                    imageView1.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            Toast.makeText(getActivity(), "暂无商品~", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }

                                                    } else {
                                                        Toast.makeText(getActivity(), "" + news_listModel.getMsg(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                                                }, throwable -> {
                                                    //                                                    Log.d("bbbbbbbbbbbbbbbbbb", "getAdvertisementGoodsListByType4: 外层");
                                                    Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                                });

                                        break;
                                    case "百度热卖":
                                        hotSaleAdId = id;
                                        Glide
                                                .with(getActivity())
                                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.icon_hot).error(R.drawable.icon_hot))
                                                .load(AppFinal.BASEURL + picUrl)
                                                .into(ad_hot_sale);
                                        break;
                                    case "爆品预售":
                                        preSaleAdId = id;
                                        Glide
                                                .with(getActivity())
                                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.icon_presale).error(R.drawable.icon_presale))
                                                .load(AppFinal.BASEURL + picUrl)
                                                .into(ad_presale);
                                        break;
                                }
                            }

                            ad_promotion.setOnClickListener(this);
                            ad_presale.setOnClickListener(this);
                            ad_hot_sale.setOnClickListener(this);
                        } else {
                            Toast.makeText(getActivity(), "没有正在生效的热门分类广告", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(getActivity(), "" + EffectAdvertisementByTypeModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    //                    Log.d("bbbbbbbbbbbbbbbbbb", "getAdvertisementGoodsListByType4:最外层 ");
                    Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });

    }

    /**
     * 获取正在生效的首页广告（对应APP首页的banner图）
     */
    public void getAdvertisementGoodsListByType1() {

        Net.get().getEffectAdvertisementByType(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(EffectAdvertisementByTypeModel -> {
                    //                    Log.d("aaaaaaaaaaaa", "getAdvertisementGoodsListByType1数据加载了");
                    int code = EffectAdvertisementByTypeModel.getCode();
                    if (code == 200) {
                        advertisementListByType1 = EffectAdvertisementByTypeModel.getResultData();
                        if (advertisementListByType1.size() > 0) {
                            for (int i = 0; i < advertisementListByType1.size(); i++) {
                                EffectAdvertisementByTypeModel.ResultDataBean item = advertisementListByType1.get(i);
                                String picUrl = item.getPicUrl();
                                //如果有图片
                                if (picUrl != null && picUrl != "") {
                                    images.add(AppFinal.BASEURL + picUrl);
                                }
                                //无图片
                                else {
                                    images.add(R.drawable.img_noimg_banner_top);
                                }


                            }
                            initBanner(images);
                        } else {
                            Toast.makeText(getActivity(), "没有正在生效的首页广告", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(getActivity(), "" + EffectAdvertisementByTypeModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
        //        return goodsListModels;
    }

    /**
     * 获取正在生效的限时抢购广告（对应APP首页的限时抢购图）
     */
    public void getAdvertisementGoodsListByType3() {

        Net.get().getEffectAdvertisementByType(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((EffectAdvertisementByTypeModel EffectAdvertisementByTypeModel) -> {
                    //                    Log.d("aaaaaaaaaaaa", "getAdvertisementGoodsListByType3数据加载了");
                    int code = EffectAdvertisementByTypeModel.getCode();
                    if (code == 200) {
                        advertisementListByType3 = EffectAdvertisementByTypeModel.getResultData();
                        if (advertisementListByType3.size() > 0) {
                            banner_limit_layout.setVisibility(View.VISIBLE);
                            long time = 24 * 60 * 60 * 1000;//总倒计时24小时
                            //获取系统时间
                            Calendar calendar = Calendar.getInstance();
                            //小时
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            long hourTime = hour * 60 * 60 * 1000;//当前小时的毫秒数
                            //分钟
                            int minute = calendar.get(Calendar.MINUTE);
                            long minuteTime = minute * 60 * 1000;//当前分钟的毫秒数
                            //秒
                            int second = calendar.get(Calendar.SECOND);
                            long secondTime = second * 1000;//当前秒的毫秒数
                            long nowTime = hourTime + minuteTime +secondTime;
                            long countDownTime = time-nowTime;
                            CountDownTimerUtilsForLimitAD mCountDownTimerUtils = new CountDownTimerUtilsForLimitAD(banner_limit_layout, end_time, countDownTime, 1000);
                            mCountDownTimerUtils.start();
                            EffectAdvertisementByTypeModel.ResultDataBean item = advertisementListByType3.get(0);
                            flashSaleAdId = item.getId();
                            String picUrl = item.getPicUrl();
                            //如果有图片
                            if (picUrl != null && picUrl != "") {

                                Glide
                                        .with(getActivity())
                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_banner_middle).error(R.drawable.img_noimg_banner_middle))
                                        .load(AppFinal.BASEURL + picUrl)
                                        .into(banner_limit);

                            }
                            //无图片
                            else {
                                //
                                Glide
                                        .with(getActivity())
                                        .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_banner_middle).error(R.drawable.img_noimg_banner_middle))
                                        .load(R.drawable.img_noimg_banner_middle)
                                        .into(banner_limit);
                            }


                            //限时抢购商品详情获取
                            Net.get().getAdvertisementById(flashSaleAdId).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(AdvertisementByIdModel -> {
                                        //                                        Log.d("aaaaaaaaaaaa", "限时抢购商品获取数据加载了");
                                        int code1 = AdvertisementByIdModel.getCode();
                                        msg = AdvertisementByIdModel.getMsg();
                                        //                                        Log.d("code1", ""+code1);
                                        if (code1 == 200) {
                                            flashSaleGoodsId = AdvertisementByIdModel.getResultData().getGoods().get(0).getId();
                                            banner_limit.setOnClickListener(this);
                                        } else {
                                            Toast.makeText(getActivity(), "" + AdvertisementByIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                        //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                                    }, throwable -> {
                                        //                                        Log.d("bbbbbbbbbb", "限时抢购商品详情获取");
                                        Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                    });

                        } else {
                            banner_limit_layout.setVisibility(View.GONE);
                        }


                    } else {
                        Toast.makeText(getActivity(), "" + EffectAdvertisementByTypeModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    //                    Log.d("bbbbbbbbbb", "getAdvertisementGoodsListByType3");
                    Toast.makeText(getActivity(), AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
        //        return goodsListModels;
    }

    //根据用户id获取消息未读数
    public void getMessageNum(Integer userId) {
        Net.get().getMessageNum(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageNoReadNumModel -> {
                    if (messageNoReadNumModel.getCode() == 200) {
                        if (messageNoReadNumModel.getResultData().getActivityNum()>0||messageNoReadNumModel.getResultData().getOrderNum()>0||messageNoReadNumModel.getResultData().getUserMessageNum()>0){
                            message.setImageResource(R.drawable.icon_message_white_many);
                        }
                        else{
                            message.setImageResource(R.drawable.icon_message_white);
                        }
                    } else {
                        Toast.makeText(getContext(), messageNoReadNumModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {

                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
    private void initPreSellBanner(List images) {

        //presellbanner = view.findViewById(R.id.presell_banner);
        //设置图片加载器
        presellbanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        presellbanner.setImages(images);
        presellbanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                int activityInformationId = preSellActivityList.get(position).getId();
                Intent intent=new Intent(getActivity(),ActivityNameActivity.class);
                intent.putExtra("activityInformationId",activityInformationId);
                startActivity(intent);
            }
        });

        //banner设置方法全部调用完毕时最后调用
        presellbanner.start();
    }

    /**
     * 获取正在生效的预售活动（对应APP首页的预售活动模块banner图）
     */
    public void getAllEffectPreSellActivityInformation() {

        Net.get().getAllEffectPreSellActivityInformation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(effectPreSellActivityInformationModel -> {
                    int code = effectPreSellActivityInformationModel.getCode();
                    if (code == 200) {
                        preSellActivityList = effectPreSellActivityInformationModel.getResultData();
                        if (preSellActivityList.size() > 0) {
                            presellbanner.setVisibility(View.VISIBLE);
                            for (int i = 0; i < preSellActivityList.size(); i++) {
                                EffectPreSellActivityInformationModel.ResultDataBean item = preSellActivityList.get(i);
                                String picUrl = item.getShowPicUrl();
                                //如果有图片
                                if (picUrl != null && picUrl != "") {
                                    presellImages.add(AppFinal.BASEURL + picUrl);
                                }
                                //无图片
                                else {
                                    presellImages.add(R.drawable.img_noimg_banner_top);
                                }


                            }
                            initPreSellBanner(presellImages);
                        } else {
                            presellbanner.setVisibility(View.GONE);
                        }


                    } else {
                        Toast.makeText(getActivity(), "" + effectPreSellActivityInformationModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getActivity(), AppFinal.NET_ERROR+"11111",Toast.LENGTH_SHORT).show();
                });
    }


}
