package com.jl.jlapp.mvp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.adapter.GoodsMsgActivityAdapter;
import com.jl.jlapp.adapter.GoodsMsgEvaluationAdapter;
import com.jl.jlapp.eneity.Evaluation;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.mvp.activity.ChooseAddressActivity;
import com.jl.jlapp.mvp.activity.EvaluationDetailActivity;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.MyAddressActivity;
import com.jl.jlapp.mvp.activity.OrderSubmitActivity;
import com.jl.jlapp.mvp.activity.PhotoViewActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ChooseGoodsSpecPopu;
import com.jl.jlapp.popu.ChooseSendToAddressPopu;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
import com.jl.jlapp.utils.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;


public class GoodsMsgFragment extends Fragment implements View.OnClickListener, ChooseSendToAddressPopu.OnClickItem,
        ChooseSendToAddressPopu.setOnClickBottomOKBtn, ChooseGoodsSpecPopu.OnClickBuyNowBtnListener,
        ChooseGoodsSpecPopu.OnClickAddShopCartBtnListener, ChooseGoodsSpecPopu.OnClickGoodsSpecItemListener,
        ChooseAddressActivity.OnClicktem, GoodsMsgEvaluationAdapter.OnClickGoodsPicItemListener {
    public static final String TAG = "GoodsMsgFragment";
    @BindView(R.id.goods_banner)
    Banner goodsBanner;
    @BindView(R.id.goods_msg_activity_recycler_view)
    RecyclerView goodsMsgActivityRecyclerView;
    @BindView(R.id.goods_msg_evaluation)
    RecyclerView goodsMsgEvaluationRecyclerView;
    @BindView(R.id.goods_msg_choose_spec_btn)
    ImageView goodsMsgChooseSpecBtn;
    @BindView(R.id.send_to_round_gray_three)
    ImageView sendToRoundGrayThree;
    @BindView(R.id.goods_msg_goods_spec_detail)
    TextView goodsMsgGoodsSpecDetail;
    @BindView(R.id.goods_msg_choose_send_address)
    TextView goodsMsgChooseSendAddress;
    @BindView(R.id.goods_msg_checked_look_detail)
    LinearLayout goodsMsgCheckedLookDetail;
    @BindView(R.id.goods_msg_checked_look_all_evaluation)
    TextView goodsMsgCheckedLookAllEvaluation;
    @BindView(R.id.goods_msg_goods_name)
    TextView goodsMsgGoodsName;
    @BindView(R.id.goods_msg_introduction)
    TextView goodsMsgIntroduction;
    @BindView(R.id.goods_msg_goods_price)
    TextView goodsMsgGoodsPrice;
    @BindView(R.id.is_saled_goods_num)
    TextView isSaledGoodsNum;
    @BindView(R.id.goods_evaluation_num)
    TextView goodsEvaluationNum;
    @BindView(R.id.goods_evaluation_high_praise)
    TextView goodsEvaluationHighPraise;
    @BindView(R.id.goods_msg_old_goods_price)
    TextView goodsMsgOldGoodsPrice;
    @BindView(R.id.layout_activityForNotPreSell)
    RelativeLayout activityForNotPreSell;
    @BindView(R.id.layout_activityForPreSell)
    RelativeLayout activityForPreSell;
    @BindView(R.id.text_activityNameAndDate)
    TextView activityNameAndDate;


    GoodsMsgActivityAdapter goodsMsgActivityAdapter;
    GoodsMsgEvaluationAdapter goodsMsgEvaluationAdapter;
    ChooseGoodsSpecPopu chooseGoodsSpecPopu;
    ChooseSendToAddressPopu chooseSendToAddressPopu;
    View view;
    List images;
    List activityList;
    List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> evaluationList;
    List<ShipAddressModel.ResultDataBean> shipAddressResultDataBeans;
    GoodsDetailActivity goodsDetailActivity;

    GoodsDetailModel.ResultDataBean goodsDetailDataBean;

    Double goodEvaluationRating = 0.0;
    double goodEvalNum = 0;
    int middleEvalNum = 0;
    int discrepancyEvalNum = 0;
    int hasPicNum = 0;
    double compositeScores = 0;
    Integer goodsId = 0;

    int userId = 0;

    String chooseAddress = "";
    WebView webView;

    Integer activityId;//预售活动id
    String activityName;//预售活动名称
    //加载框
    private ProgressDialog progressDialog;

    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        goodsDetailActivity = (GoodsDetailActivity) getActivity();
        goodsId = goodsDetailActivity.goodsId;
        activityId=goodsDetailActivity.activityId;
        activityName=goodsDetailActivity.activityName;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_msg, null, false);

        ButterKnife.bind(this, view);

        webView = view.findViewById(R.id.show_goods_detail_web);
        WebSettings webSettings = webView.getSettings();
        //WebView是否保存表单数据，默认值true。
        webSettings.setSaveFormData(false);
        //是否允许WebView度超出以概览的方式载入页面，默认false。即缩小内容以适应屏幕宽度。该项设置在内容宽度超出WebView控件的宽度时生效，例如当getUseWideViewPort() 返回true时。
        webSettings.setLoadWithOverviewMode(true);
        // 设置WebView是否允许执行JavaScript脚本，默认false，不允许。
        webSettings.setJavaScriptEnabled(true);
        // WebView是否支持使用屏幕上的缩放控件和手势进行缩放，默认值true。
        webSettings.setSupportZoom(true);
        //设置WebView是否使用viewport，当该属性被设置为false时，加载页面的宽度总是适应WebView控件宽度；当被设置为true，当前页面包含viewport属性标签，在标签中指定宽度值生效，如果页面不包含viewport标签，无法提供一个宽度值，这个时候该方法将被使用。
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setLoadWithOverviewMode(true);
        //  webView.setWebChromeClient(new MyWebChromeClient());

        webView.setWebViewClient(new WebViewClient());

        //webView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");

        //webView.loadUrl("http://117.158.178.202:8000/Demo/demo.html");
        //webView.loadUrl("http://117.158.178.202:8000/JLMIS/junlin/mis/webview/goodsDetails.html");
        //webView.loadUrl("http://117.158.178.202:8000/JLMIS/junlin/mis/webview/goodsDetails.html?goodsId="+goodsId);
        webView.loadUrl(AppFinal.BASEURL + "junlin/mis_jsp/webview/goodsDetails.jsp?goodsId=" + goodsId);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        //根据接口获取商品详情页面所需的数据
        getGoodsDetailMsgByGoodsId();

        if (userId > 0) {
            buildProgressDialog();
            //根据接口获取收货人地址所需的数据
            getShippingAddressByUserId();
        } else {
            sendToRoundGrayThree.setVisibility(View.INVISIBLE);
            goodsMsgChooseSendAddress.setText("暂无地址，请先登录。");
            goodsMsgChooseSendAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });

        }

        setListener();
    }

    /**
     * 加载框
     */
    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("加载中");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * @Description: TODO 取消加载框
     */
    public void cancelProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }

    public void setListener() {
        goodsMsgChooseSpecBtn.setOnClickListener(this);
        sendToRoundGrayThree.setOnClickListener(this);
        goodsMsgCheckedLookDetail.setOnClickListener(this);
        goodsMsgCheckedLookAllEvaluation.setOnClickListener(this);
    }

    //传入对象，调用pop中的方法
    public void setChooseGoodsSpecPopuListener() {
        chooseGoodsSpecPopu.setOnClickAddShopCartBtnListener(this);
        chooseGoodsSpecPopu.setOnClickBuyNowBtnListener(this);
        chooseGoodsSpecPopu.setOnClickGoodsSpecItemListener(this);
    }

    //传入对象，调用pop中的方法
    public void setChooseAddressPopuListener() {
        chooseSendToAddressPopu.setOnClickItem(this);
        chooseSendToAddressPopu.setSetOnClickBottomOKBtn(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击选择规格
            case R.id.goods_msg_choose_spec_btn:
                Log.d("aaaaaaaaa", "我点击了按钮");
                //弹出选择规格的pop
                chooseGoodsSpecPopu = new ChooseGoodsSpecPopu(getActivity(), goodsMsgGoodsSpecDetail.getText().toString().trim(), 0, goodsDetailDataBean);
                chooseGoodsSpecPopu.showAtLocation(getActivity().findViewById(R.id.goods_msg_page), Gravity.BOTTOM, 0, 0);
                setChooseGoodsSpecPopuListener();
                break;
            //点击选择地址
            case R.id.send_to_round_gray_three:
                chooseSendToAddressPopu = new ChooseSendToAddressPopu(getActivity(), goodsMsgChooseSendAddress.getText().toString().trim(), shipAddressResultDataBeans);
                chooseSendToAddressPopu.showAtLocation(getActivity().findViewById(R.id.goods_msg_page), Gravity.BOTTOM, 0, 0);
                setChooseAddressPopuListener();
                break;
            case R.id.goods_msg_checked_look_detail:
                if (goodsDetailActivity != null) {
                    goodsDetailActivity.onClickLookGoodsSpec();
                }

                break;
            case R.id.goods_msg_checked_look_all_evaluation:
                if (goodsDetailActivity != null) {
                    goodsDetailActivity.onClickLookEvaluation();
                }
                break;
        }
    }

    //获取完数据 给控件赋值
    public void getDataToControl(GoodsDetailModel.ResultDataBean resultDataBean) {

        initBanner(resultDataBean.getGoodsSpecificationDetails(), 0);
        goodsMsgGoodsName.setText(resultDataBean.getName());
        goodsMsgIntroduction.setText(resultDataBean.getIntrodution());
        if (resultDataBean.getGoodsSpecificationDetails().size() > 0) {
            goodsMsgChooseSpecBtn.setVisibility(View.VISIBLE);
            goodsMsgGoodsSpecDetail.setText(resultDataBean.getGoodsSpecificationDetails().get(0).getSpecifications());
            goodsMsgGoodsPrice.setText(resultDataBean.getGoodsSpecificationDetails().get(0).getPrice() + "");
            if(resultDataBean.getGoodsSpecificationDetails().get(0).getOldPrice()>0){
                goodsMsgOldGoodsPrice.setVisibility(View.VISIBLE);
                goodsMsgOldGoodsPrice.setText("¥"+resultDataBean.getGoodsSpecificationDetails().get(0).getOldPrice());
            }
            else{
                goodsMsgOldGoodsPrice.setVisibility(View.GONE);
            }

            goodsMsgOldGoodsPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

            goodsDetailActivity.goodsSpecificationId = resultDataBean.getGoodsSpecificationDetails().get(0).getId();//给GoodsDetailActivity页面的商品规格id赋值
        } else {
            goodsMsgChooseSpecBtn.setVisibility(View.GONE);
            goodsMsgGoodsSpecDetail.setText("暂无规格");
            Toast.makeText(getContext(), "数据错误，暂无规格。", Toast.LENGTH_SHORT).show();
        }
        isSaledGoodsNum.setText(resultDataBean.getSaleCount() + "");

        //商品活动处理
        int isPreSell = resultDataBean.getGoodsSpecificationDetails().get(0).getGxcGoodsState();
        if(isPreSell == 1){//预售商品
            activityForPreSell.setVisibility(View.VISIBLE);
            activityForNotPreSell.setVisibility(View.GONE);
            for (int i = 0; i < resultDataBean.getGoodsActivitysX().size(); i++) {
                if(activityId == resultDataBean.getGoodsActivitysX().get(i).getActivityInformation().getIdX()){
                    String activityName = resultDataBean.getGoodsActivitysX().get(i).getActivityInformation().getNameX();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String starDate = sdf.format(resultDataBean.getGoodsActivitysX().get(i).getActivityInformation().getBeginValidityTime());
                    String endDate = sdf.format(resultDataBean.getGoodsActivitysX().get(i).getActivityInformation().getEndValidityTime());
                    activityNameAndDate.setText(activityName+"("+starDate+"~"+endDate+")");
                }
            }
        }else{//非预售商品
            activityForPreSell.setVisibility(View.GONE);
            activityForNotPreSell.setVisibility(View.VISIBLE);
            setGoodsActivityAdapter(resultDataBean, 0);
        }



        goodsEvaluationNum.setText(resultDataBean.getGoodsEvaluations().size() + "");
        setGoodsEvaluationAdapter(resultDataBean);

    }


    private void initBanner(List<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean> specificationDetailsBean, int position) {
        images = new ArrayList();
        if (specificationDetailsBean.size() > 0) {
           /* for (int i = 0; i < specificationDetailsBean.size(); i++) {*/
                /*if (specificationDetailsBean.get(i).getGoodsDisplayPictures().size() > 0) {*/
            for (int p = 0; p < specificationDetailsBean.get(position).getGoodsDisplayPictures().size(); p++) {
                images.add(AppFinal.BASEURL + specificationDetailsBean.get(position).getGoodsDisplayPictures().get(p).getPicUrl());
            }
               /* }*/
            /*}*/
            //说明用户没有上传图片
            if (images.size() == 0) {
                images.add(R.drawable.img_noimg_l);
            }
        } else {
            Toast.makeText(getContext(), "数据错误，暂无规格。", Toast.LENGTH_SHORT).show();
        }

        //设置图片加载器
        goodsBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        goodsBanner.setImages(images);
        goodsBanner.setIndicatorGravity(BannerConfig.RIGHT);

        //banner设置方法全部调用完毕时最后调用
        goodsBanner.start();
    }

    //设置商品活动信息
    public void setGoodsActivityAdapter(GoodsDetailModel.ResultDataBean goodsDetailDataBean, int position) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        } else {
            activityList.clear();
        }

        if (goodsDetailDataBean.getGoodsActivitysX().size() > 0) {
            for (int i = 0; i < goodsDetailDataBean.getGoodsActivitysX().size(); i++) {
                activityList.add(goodsDetailDataBean.getGoodsActivitysX().get(i));
            }
        }
        if (goodsDetailDataBean.getGoodsSpecificationDetails().size() > 0) {
            //for (int i = 0; i < goodsDetailDataBean.getGoodsSpecificationDetails().size(); i++) {
            //if (goodsDetailDataBean.getGoodsSpecificationDetails().get(i).getGoodsActivitys().size() > 0) {
            for (int ga = 0; ga < goodsDetailDataBean.getGoodsSpecificationDetails().get(position).getGoodsActivitys().size(); ga++) {
                activityList.add(goodsDetailDataBean.getGoodsSpecificationDetails().get(position).getGoodsActivitys().get(ga));
            }
            //}
            //}
        }
        if (activityList.size() == 0) {
            GoodsDetailModel.ResultDataBean.GoodsActivitysBean goodsActivitysBean = new GoodsDetailModel.ResultDataBean.GoodsActivitysBean();
            GoodsDetailModel.ResultDataBean.GoodsActivitysBean.ActivityInformationBean activityInformationBean = new GoodsDetailModel.ResultDataBean.GoodsActivitysBean.ActivityInformationBean();
            activityInformationBean.setNameX("暂无优惠");
            goodsActivitysBean.setActivityInformation(activityInformationBean);
            activityList.add(goodsActivitysBean);
        }

        goodsMsgActivityRecyclerView.setLayoutManager(new CustomLanearLayoutManager(getActivity(), false));
        goodsMsgActivityAdapter = new GoodsMsgActivityAdapter(R.layout.goods_msg_activity_item, activityList);
        goodsMsgActivityRecyclerView.setAdapter(goodsMsgActivityAdapter);


    }

    //设置商品详情页的商品评价
    private void setGoodsEvaluationAdapter(GoodsDetailModel.ResultDataBean goodsDetailDataBean) {
        evaluationList = new ArrayList<>();
        goodEvaluationRating = 0.0;
        goodEvalNum = 0;
        middleEvalNum = 0;
        discrepancyEvalNum = 0;
        hasPicNum = 0;
        if (goodsDetailDataBean.getGoodsEvaluations().size() > 0) {
            goodsMsgCheckedLookAllEvaluation.setVisibility(View.VISIBLE);
            double allScoreNum = 0;
            for (int i = 0; i < goodsDetailDataBean.getGoodsEvaluations().size(); i++) {
                allScoreNum += goodsDetailDataBean.getGoodsEvaluations().get(i).getScore();

                if (goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() >= 4 && goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() <= 5) {
                    goodEvalNum++;
                } else if (goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() >= 2 && goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() < 4) {
                    middleEvalNum++;
                } else {
                    discrepancyEvalNum++;
                }

                if (goodsDetailDataBean.getGoodsEvaluations().get(i).getEvaluationPics().size() > 0) {
                    hasPicNum++;
                }
            }
            Log.d("aaaaaaaa", "goodEvalNum:" + goodEvalNum);
            if (goodEvalNum != 0) {
                double goodEvaluationValue = goodEvalNum / goodsDetailDataBean.getGoodsEvaluations().size();
                BigDecimal bg = new BigDecimal(goodEvaluationValue);
                double f1 = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                goodEvaluationRating = (Double) (f1 * 100);
                Log.d("aaaaaaaa", "好评人数：" + goodEvalNum + " goodEvaluationValue:" + goodEvaluationValue + " 好评率：" + f1 + "  goodEvaluationRating:" + goodEvaluationRating);
                goodsDetailActivity.goodEvalNum = (int) goodEvalNum;
                goodsDetailActivity.middleEvalNum = middleEvalNum;
                goodsDetailActivity.discrepancyEvalNum = discrepancyEvalNum;
                goodsDetailActivity.goodEvaluationRating = goodEvaluationRating;
                goodsDetailActivity.hasPicNum = hasPicNum;
                if (allScoreNum != 0) {
                    double compositeScoresValue = allScoreNum / goodsDetailDataBean.getGoodsEvaluations().size();
                    BigDecimal bg2 = new BigDecimal(compositeScoresValue);
                    compositeScores = bg2.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                    goodsDetailActivity.compositeScores = compositeScores;
                }
            } else if (middleEvalNum != 0) {
                Log.d("aaaaaaaa", "好评人数：" + goodEvalNum + " goodEvaluationValue:" + 0 + " 好评率：" + 0 + "  goodEvaluationRating:" + 0);
                goodsDetailActivity.goodEvalNum = (int) goodEvalNum;
                goodsDetailActivity.middleEvalNum = middleEvalNum;
                goodsDetailActivity.discrepancyEvalNum = discrepancyEvalNum;
                goodsDetailActivity.goodEvaluationRating = goodEvaluationRating;
                goodsDetailActivity.hasPicNum = hasPicNum;
                if (allScoreNum != 0) {
                    double compositeScoresValue = allScoreNum / goodsDetailDataBean.getGoodsEvaluations().size();
                    BigDecimal bg2 = new BigDecimal(compositeScoresValue);
                    compositeScores = bg2.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                    goodsDetailActivity.compositeScores = compositeScores;
                }
            } else {
                Log.d("aaaaaaaa", "好评人数：" + goodEvalNum + " goodEvaluationValue:" + 0 + " 好评率：" + 0 + "  goodEvaluationRating:" + 0);
                goodsDetailActivity.goodEvalNum = (int) goodEvalNum;
                goodsDetailActivity.middleEvalNum = middleEvalNum;
                goodsDetailActivity.discrepancyEvalNum = discrepancyEvalNum;
                goodsDetailActivity.goodEvaluationRating = goodEvaluationRating;
                goodsDetailActivity.hasPicNum = hasPicNum;
                if (allScoreNum != 0) {
                    double compositeScoresValue = allScoreNum / goodsDetailDataBean.getGoodsEvaluations().size();
                    BigDecimal bg2 = new BigDecimal(compositeScoresValue);
                    compositeScores = bg2.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                    goodsDetailActivity.compositeScores = compositeScores;
                }
            }
            goodsEvaluationHighPraise.setText(goodEvaluationRating + "");

            List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> hasPicAndGoodEvalList = new ArrayList<>(); //好评加有图
            List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> hasGoodEvalList = new ArrayList<>();//好评
            List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> hasPicEvalList = new ArrayList<>();//有图
            List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> otherEvalList = new ArrayList<>();//有图

            for (int i = 0; i < goodsDetailDataBean.getGoodsEvaluations().size(); i++) {
                if (goodsDetailDataBean.getGoodsEvaluations().get(i).getUser().getPhone() != null && goodsDetailDataBean.getGoodsEvaluations().get(i).getUser().getPhone().length() == 11) {
                    String phone = goodsDetailDataBean.getGoodsEvaluations().get(i).getUser().getPhone();
                    goodsDetailDataBean.getGoodsEvaluations().get(i).getUser().setPhone(replacePhoneNum(phone));
                }
                //好评加有图
                if (goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() >= 4 && goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() <= 5 && goodsDetailDataBean.getGoodsEvaluations().get(i).getEvaluationPics().size() > 0) {
                    hasPicAndGoodEvalList.add(goodsDetailDataBean.getGoodsEvaluations().get(i));
                }
                //好评
                else if (goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() >= 4 && goodsDetailDataBean.getGoodsEvaluations().get(i).getScore() <= 5 && goodsDetailDataBean.getGoodsEvaluations().get(i).getEvaluationPics().size() <= 0) {
                    hasGoodEvalList.add(goodsDetailDataBean.getGoodsEvaluations().get(i));
                }
                //有图
                else if (goodsDetailDataBean.getGoodsEvaluations().get(i).getEvaluationPics().size() > 0) {
                    hasPicEvalList.add(goodsDetailDataBean.getGoodsEvaluations().get(i));
                }
                else{
                    otherEvalList.add(goodsDetailDataBean.getGoodsEvaluations().get(i));
                }
                // evaluationList.add(goodsDetailDataBean.getGoodsEvaluations().get(i));
            }

            //有 3个及以上 好评+有图的评价
            if (hasPicAndGoodEvalList.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    evaluationList.add(hasPicAndGoodEvalList.get(i));
                }
            }
            //好评+有图的评价 有 但是 不够三个 或  没有
            else {
                for (int i = 0; i < hasPicAndGoodEvalList.size(); i++) {
                    evaluationList.add(hasPicAndGoodEvalList.get(i));
                }
                //好评的评价的数量能满足 剩余数量
                if (hasGoodEvalList.size() >= (3 - evaluationList.size())) {
                    for (int i = 0; i < 3 - evaluationList.size(); i++) {
                        evaluationList.add(hasGoodEvalList.get(i));
                    }
                }
                //好评的评价的数量 不能满足 剩余数量
                else {
                    for (int i = 0; i < hasGoodEvalList.size(); i++) {
                        evaluationList.add(hasGoodEvalList.get(i));
                    }

                    //有图的评价能满足 剩余数量
                    if (hasPicEvalList.size() >= (3 - evaluationList.size())) {
                        for (int i = 0; i < 3 - hasPicAndGoodEvalList.size(); i++) {
                            evaluationList.add(hasPicEvalList.get(i));
                        }
                    }
                    else{
                        for (int i = 0; i < hasPicEvalList.size(); i++) {
                            evaluationList.add(hasPicEvalList.get(i));
                        }

                        //其他的评价能满足 剩余数量
                        if (otherEvalList.size() >= (3 - evaluationList.size())) {
                            for (int i = 0; i < 3 - hasPicAndGoodEvalList.size(); i++) {
                                evaluationList.add(otherEvalList.get(i));
                            }
                        }
                        else{
                            for (int i = 0; i < otherEvalList.size(); i++) {
                                evaluationList.add(otherEvalList.get(i));
                            }
                        }
                    }
                }
            }

        } else {
            goodsMsgCheckedLookAllEvaluation.setVisibility(View.GONE);
        }
        goodsMsgEvaluationRecyclerView.setLayoutManager(new CustomLanearLayoutManager(getActivity(), false));
        goodsMsgEvaluationAdapter = new GoodsMsgEvaluationAdapter(R.layout.goods_msg_evaluation_item, evaluationList);
        goodsMsgEvaluationAdapter.setOnClickGoodsPicItemListener(GoodsMsgFragment.this);
        goodsMsgEvaluationRecyclerView.setAdapter(goodsMsgEvaluationAdapter);
    }

    //把评价排序
    public void paixu(List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> list) {
        List<GoodsDetailModel.ResultDataBean.GoodsEvaluationsBean> evals = new ArrayList<>();
        if (list.size() > 1) {

            while (list.size() > 1) {
                int evalWorldNum = list.get(0).getEvaluationContent().length();
                int worldMaxNumIndex = 0;
                for (int j = 0; j < list.size() - 1; j++)// j开始等于0，
                {
                    if (evalWorldNum < list.get(j).getEvaluationContent().length()) {
                        evalWorldNum = list.get(j).getEvaluationContent().length();
                        worldMaxNumIndex = j;
                    }
                }
                evals.add(list.get(worldMaxNumIndex));
                list.remove(worldMaxNumIndex);
            }


        }
    }

    public String replacePhoneNum(String tel) {
        return tel.substring(0, 3) + "****" + tel.substring(7, 11);
    }


    //地址选择框的item点击事件
    @Override
    public void onClickItem(String address) {
        chooseSendToAddressPopu.dismiss();
        goodsMsgChooseSendAddress.setText(address);

    }

    //地址选择框的底部按钮点击事件
    @Override
    public void onClickBottomOkBtn() {
        chooseSendToAddressPopu.dismiss();
        ChooseAddressActivity.setOnClicktem(GoodsMsgFragment.this);
        Intent intent = new Intent(getActivity(), ChooseAddressActivity.class);
        intent.putExtra("whichPage", 1);
        startActivity(intent);
    }

    //选择其他地址的item点击事件
    @Override
    public void onClicktem(String address) {
        Log.d("aaaaaaaGoods", "item:" + address);
        chooseAddress = address;
        goodsMsgChooseSendAddress.setText(address);
    }


    //选择规格的popupwindiw弹出层里的点击加入购物车按钮的点击事件
    @Override
    public void OnClickAddShopCartBtn(String goodsSpec, Integer goodsSpecId, Integer goodsNum) {
        chooseGoodsSpecPopu.dismiss();
        goodsMsgGoodsSpecDetail.setText(goodsSpec);
        goodsDetailActivity.goodsSpecificationId = goodsSpecId;//给GoodsDetailActivity页面的商品规格id赋值
        Log.d(TAG, "num:" + goodsNum);
        if (userId > 0) {
            insertShoppingCart(userId, goodsId, goodsSpecId, goodsNum,activityId,activityName);
        } else {
            Toast.makeText(getContext(), "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }


    }

    //选择规格的popupwindiw弹出层里的点击立即购买按钮的点击事件
    @Override
    public void OnClickBuyNowBtn(String goodsSpec, Integer goodsSpecId, Integer goodsNum,Integer isPresell) {
        // Toast.makeText(getContext(),"我点击了立即购买",Toast.LENGTH_SHORT).show();
        if (userId > 0) {
            Intent intent = new Intent(getActivity(), OrderSubmitActivity.class);
            intent.putExtra("isFromWhichPage", 1);
            intent.putExtra("buyNum", goodsNum);
            intent.putExtra("goodsSpecId", goodsSpecId);
            intent.putExtra("goodsId", goodsId);
            intent.putExtra("isPresell", isPresell);
            if(isPresell==1){
                intent.putExtra("activityId", activityId);
            }
            startActivity(intent);
            chooseGoodsSpecPopu.dismiss();
        } else {
            Toast.makeText(getContext(), "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    //选择规格的popupwindiw弹出层里的点击选择商品规格item的点击事件
    @Override
    public void OnClickGoodsSpecItem(String goodsSpec, Integer goodsSpecId, Integer position) {

        initBanner(goodsDetailActivity.resultDataBeanMsg.getGoodsSpecificationDetails(), position);
        setGoodsActivityAdapter(goodsDetailActivity.resultDataBeanMsg, position);
        goodsMsgGoodsSpecDetail.setText(goodsSpec);
        goodsDetailActivity.goodsSpecificationId = goodsSpecId;//给GoodsDetailActivity页面的商品规格id赋值
        goodsMsgGoodsPrice.setText(goodsDetailActivity.resultDataBeanMsg.getGoodsSpecificationDetails().get(position).getPrice() + "");
        if(goodsDetailActivity.resultDataBeanMsg.getGoodsSpecificationDetails().get(position).getOldPrice()>0){
            goodsMsgOldGoodsPrice.setVisibility(View.VISIBLE);
            goodsMsgOldGoodsPrice.setText("¥"+goodsDetailActivity.resultDataBeanMsg.getGoodsSpecificationDetails().get(position).getOldPrice()+"");
        }
        else{
            goodsMsgOldGoodsPrice.setVisibility(View.GONE);
        }

        goodsMsgOldGoodsPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
    }

    @Override
    public void OnClickGoodsPicItem(String goodsPicUrl) {
        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
        intent.putExtra("picUrl", goodsPicUrl);
        startActivity(intent);
    }


    public interface SetOnClickLookGoodsSpec {
        void onClickLookGoodsSpec();
    }

    //判断有无库存的商品，如果无，则把商品放在规格的最后面
    private void getHasStrockGoods() {
        List<GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean> beans = new ArrayList<>();
        for (int i = 0; i < goodsDetailDataBean.getGoodsSpecificationDetails().size(); i++) {
            if(goodsDetailDataBean.getZeroStock()==0&&goodsDetailDataBean.getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2){
                if (goodsDetailDataBean.getGoodsSpecificationDetails().get(i).getGxcGoodsStock() <= 0) {
                    beans.add(goodsDetailDataBean.getGoodsSpecificationDetails().get(i));
                }
            }

        }

        if (beans.size() > 0) {
            for (int i = 0; i < goodsDetailDataBean.getGoodsSpecificationDetails().size(); i++) {
                for (int k = 0; k < beans.size(); k++) {
                    if (goodsDetailDataBean.getGoodsSpecificationDetails().get(i).getId() == beans.get(k).getId()) {
                        goodsDetailDataBean.getGoodsSpecificationDetails().remove(i);
                        goodsDetailDataBean.getGoodsSpecificationDetails().add(beans.get(k));
                        beans.remove(k);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    //获取商品详情信息
    public void getGoodsDetailMsgByGoodsId() {

        Net.get().getGoodsDetailMsgByGoodsId(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodsDetailModel -> {
                    cancelProgressDialog();
                    //  Log.d("aaaaaaaaa", "goodsDetailModelSize:" + goodsDetailModel.getResultData().getGoodsSpecificationDetails().size());
                    if (goodsDetailModel.getCode() == 200) {
                        goodsDetailDataBean = goodsDetailModel.getResultData();
                        //给activity中的对象赋值
                        goodsDetailActivity.resultDataBeanMsg = goodsDetailDataBean;

                        if (goodsDetailDataBean != null) {
                            //setAdapter();
                            getHasStrockGoods();
                            getDataToControl(goodsDetailDataBean);
                        } else {
                            Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();

                    }

                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });

       /* Net.get("http://passer-by.com/data_location/").getLocal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(classificationModel -> {},throwable -> {});*/
    }

    //获取收货人地址信息
    public void getShippingAddressByUserId() {
        Net.get().getShippingAddressByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shipAddressModel -> {
                    if (shipAddressModel.getCode() == 200) {
                        shipAddressResultDataBeans = shipAddressModel.getResultData();
                        if (shipAddressResultDataBeans.size() > 0) {
                            sendToRoundGrayThree.setVisibility(View.VISIBLE);
                            if (!"".equals(chooseAddress)) {
                                goodsMsgChooseSendAddress.setText(chooseAddress);
                            } else {
                                int i = 0;
                                for (i = 0; i < shipAddressResultDataBeans.size(); i++) {
                                    if (shipAddressResultDataBeans.get(i).getIsCommonlyUsed() == 1) {
                                        goodsMsgChooseSendAddress.setText(shipAddressResultDataBeans.get(i).getRegion() + shipAddressResultDataBeans.get(i).getDetailedAddress());
                                        break;
                                    }
                                }
                                if (i == shipAddressResultDataBeans.size()) {
                                    goodsMsgChooseSendAddress.setText(shipAddressResultDataBeans.get(0).getRegion() + shipAddressResultDataBeans.get(0).getDetailedAddress());
                                }
                            }


                        } else {
                            sendToRoundGrayThree.setVisibility(View.INVISIBLE);
                            goodsMsgChooseSendAddress.setText("暂无地址,点击去添加");
                            goodsMsgChooseSendAddress.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), ChooseAddressActivity.class);
                                    startActivityForResult(intent, 1);
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }


                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //添加购物车
    public void insertShoppingCart(Integer userId, Integer goodsDetailsId, Integer goodsSpecificationDetailsId, Integer goodsNum,Integer activityId, String activityName) {
        Net.get().insertShoppingCart(userId, goodsDetailsId, goodsSpecificationDetailsId, goodsNum,activityId,activityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() != 200) {
                        Toast.makeText(getContext(), postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "添加成功!", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


}


