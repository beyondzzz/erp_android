package com.jl.jlapp.mvp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.WriteOrderGoodsMsgAdapter;
import com.jl.jlapp.eneity.ActivitysAndCouponsByGoodsMsgModel;
import com.jl.jlapp.eneity.DecisionInventoryResultModel;
import com.jl.jlapp.eneity.OrderTable;
import com.jl.jlapp.eneity.GoodsDetailModel;
import com.jl.jlapp.eneity.ParamForGetActivitysAndCouponsByGoodsMsg;
import com.jl.jlapp.eneity.ShipAddressModel;
import com.jl.jlapp.eneity.ShoppingCartModel;
import com.jl.jlapp.mvp.fragment.ShopCartFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ChooseSendToTimePopu;
import com.jl.jlapp.popu.ModifyNumPopu;
import com.jl.jlapp.popu.OrderChooseGoodsActivityPopu;
import com.jl.jlapp.popu.OrderPromptPopu;
import com.jl.jlapp.popu.PaymentTypePopu;
import com.jl.jlapp.utils.CustomLanearLayoutManager;
import com.jl.jlapp.utils.SuperTextView;
import com.jl.jlapp.utils.Tools;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lyt on 2018/1/15.
 * 订单填写
 */

public class OrderSubmitActivity extends AppCompatActivity implements View.OnClickListener, OrderChooseGoodsActivityPopu.OnClickActivityItem, ChooseSendToTimePopu.OnClickBottomOKBtn, ModifyNumPopu.OnClickOkBtn {

    public static final String TAG = "OrderSubmitActivity";

    @BindView(R.id.stv_payment_type)
    SuperTextView stvPaymentType;
    @BindView(R.id.stv_istribution_time)
    SuperTextView stvIstributionTime;
    @BindView(R.id.stv_coupon)
    SuperTextView stvCoupon;
    @BindView(R.id.stv_invoice)
    SuperTextView stvInvoice;
    @BindView(R.id.stv_activity)
    SuperTextView stvActivity;
    @BindView(R.id.ll_stv_activity)
    LinearLayoutCompat llStvActivity;
    @BindView(R.id.ll_stv_coupon)
    LinearLayoutCompat llStvCoupon;
    @BindView(R.id.rl_food_detail)
    RecyclerView rlFoodDetailRecyclerView;
    @BindView(R.id.choose_more_address)
    ImageView chooseMoreAddress;
    @BindView(R.id.tv_submit_prompt)
    TextView tvSubmitPrompt;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_consignee_name)
    TextView tvConsigneeName;
    @BindView(R.id.tv_consignee_phone)
    TextView tvConsigneePhone;
    @BindView(R.id.tv_consignee_address)
    TextView tvConsigneeAddress;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_freight_unit)
    TextView tvFreightUnit;
    @BindView(R.id.tv_freight_world)
    TextView tvFreightWorld;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.offline_payer_msg)
    LinearLayout offlinePayerMsg;
    @BindView(R.id.offline_payer_name)
    EditText offlinePayerName;
    @BindView(R.id.offline_payer_phone)
    EditText offlinePayerPhone;
    @BindView(R.id.no_address_msg)
    TextView noAddressMsg;
    @BindView(R.id.shipping_people_message)
    RelativeLayout shippingPeopleMessage;
    @BindView(R.id.return_btn)
    ImageView returnBtn;

    TextView numTv;

    private ModifyNumPopu modifyNumPopu;
    private PaymentTypePopu paymentTypePopu;
    private OrderChooseGoodsActivityPopu orderChooseGoodsActivityPopu;

    int mYear;
    int mMonth;
    int mDay;

    int chooseInvoiceType = 2;//选择发票的类型 0：普票 1：增票 2：无票
    int chooseInvoiceLookedUpType = 1;//发票抬头类型 1：个人 0：单位
    int chooseInvoiceContentDetail = 1;//发票明细，默认为明细 1：明细 0：食品
    String unitName = ""; //单位名称
    String taxpayerIdentificationNumber = "";//纳税人识别号
    String registeredAddress = "";//注册地址
    String registeredTel = "";//注册电话
    String depositBank = "";//开户行
    String bankAccount = "";//银行账号
    String businessLicenseUrl = "";//营业执照
    int payType = 0;//支付方式  0：快捷支付 1：线下支付
    double postage = 0;//邮费
    int postagePayType = 0;//邮费的支付方式(0：线上，1：到付)


    WriteOrderGoodsMsgAdapter writeOrderGoodsMsgAdapter;
    GoodsDetailModel.ResultDataBean goodsDetailDataBean;
    List<ShoppingCartModel.ResultDataBean> shouldShowGoodsMsg;
    List<ShoppingCartModel.ResultDataBean> shoppingCartList;
    public static List<ShoppingCartModel.ResultDataBean> shouldBuyShoppingCartList = new ArrayList<>();//从购物车获取的需要购买的商品
    List<ShipAddressModel.ResultDataBean> shipAddressResultDataBeans;
    int isFromWhichPage = -1;//从那个页面跳转而来  0：购物车，1：商品详情
    int[] buyGoodsMsg;//保存传递到该页面的商品信息，若是从购物车页面跳转而来，则保存的是购物车ids；若是从商品详情页跳转而来，则[0]:商品id [1]:规格id [2]:购买数量
    double titalPrice = 0;//商品总价(没参加活动前的)
    double afterActivityTotalMoney = 0;//参与活动的商品总价
    double userCouponPrice = 0;
    int userCouponId = 0;

    boolean isOkUseCoupon = true;//通过接口查出该优惠券是否可用

    String provinceCode = "";
    String cityCode = "";
    String countyCode = "";
    String ringCode = "";

    String consigneeAddress = "";
    String consigneeName = "";
    String consigneeTel = "";
    int consigneeIsVip = 0;


    int buyNumFromGoodsDetailPage = 0;//从商品详情页跳转到订单填写页时获取的商品数量
    int goodsSpecIdFromGoodsDetailPage = 0;//从商品详情页跳转到订单填写页时获取的商品规格id
    int goodsIdFromGoodsDetailPage = 0;//从商品详情页跳转到订单填写页时获取的商品id


    int activityInformationId = 0;

    int isPresell=0;//是否是预售商品 1：是 ，2：不是
    String presellEndTime="";//预售结束时间
    int presellActivityId=0;//预售活动id

    ParamForGetActivitysAndCouponsByGoodsMsg paramForGetActivitysAndCouponsByGoodsMsg;
    List<ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean> goodsMsgListBeans;
    List<ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean.GoodsActivityListBean> goodsActivityListBeans;
    List<ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean.GoodsSpeActivityListBean> goodsSpeActivityListBeans;
    ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean goodsMsgListBean;
    ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean.GoodsActivityListBean goodsActivityListBean;
    ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean.GoodsSpeActivityListBean goodsSpeActivityListBean;

    public static List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean> shouldShowCouponsBeanList = new ArrayList<>();

    ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean couponsBean;
    ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean userCouponsBean1;
    ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean.CouponInformationBean couponInformationBean;

    List<ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.ActivitysBean> activitysBeanList = new ArrayList<>();

    List<Integer> goodsSpeIdList = new ArrayList<>();//保存活动所属的规格id，用于计算参与活动后的商品价格
    List<Map<String, Object>> goodsSpecPayMoney = new ArrayList<>();//保存参与活动后的商品价格

    List<Integer> noStockGoodsSpeIdList;//查询出无库存的商品信息
    List<Map<String, Object>> insufficientInventoryGoodsSpeIdList;//库存不足的商品信息

    int activityType = -1;//记录参与的活动类型 在最后判断库存的时候进行使用
    double thresholdPrice = 0;//门槛金额 在最后判断库存的时候进行使用
    double discount = 0;//折扣/立减金额/优惠金额 在最后判断库存的时候进行使用
    int maxNum = 0;//最大购买数量


    OrderTable addOrderModel;//添加订单的数据model
    List<OrderTable.OrderDetailsBean> orderDetailsBeanList;
    OrderTable.OrderDetailsBean orderDetailsBean;
    OrderTable.InvoiceBean invoiceBean;
    OrderTable.OfflinePaymentBean offlinePaymentBean;
    OrderTable.UserCouponsBean userCouponsBean;

    OrderPromptPopu orderPromptPopu;

    DecimalFormat df;
    int userId = 0;

    ChooseSendToTimePopu chooseSendToTimePopu;
    int goodsPosition = 0;

    //加载框
    private ProgressDialog progressDialog;

    int activityId=0;//预售活动id  专门给参与预售活动的商品进行使用

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("aaaaaaaGoods", "onResume");
        getShippingAddressByUserId();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submit);
        ButterKnife.bind(this);
        df = new DecimalFormat(".00");//保留两位小数
        offlinePayerPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);//设置数字键盘
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if (userId > 0) {
            buildProgressDialog();
            Intent intent = getIntent();
            isFromWhichPage = intent.getIntExtra("isFromWhichPage", -1);
            //getShippingAddressByUserId();
            //从购物车页面跳转而来,获取数据
            if (isFromWhichPage == 0) {
                buyGoodsMsg = intent.getIntArrayExtra("shopCartIds");
                isPresell=intent.getIntExtra("isPresell",0);//是否是预售商品
                activityId=intent.getIntExtra("activityId",0);
                Log.d("aaaaaaaa", "shopCartIds.size:" + buyGoodsMsg.length);
                if (buyGoodsMsg != null && buyGoodsMsg.length > 0) {
                    getShoppingCartByUserId(userId);

                } else {
                    Toast.makeText(this, "抱歉，数据出错啦!", Toast.LENGTH_SHORT).show();
                }
            }
            //从商品详情页直接进入订单填写页
            else if (isFromWhichPage == 1) {
                buyNumFromGoodsDetailPage = intent.getIntExtra("buyNum", 1);
                goodsSpecIdFromGoodsDetailPage = intent.getIntExtra("goodsSpecId", 0);
                goodsIdFromGoodsDetailPage = intent.getIntExtra("goodsId", 0);
                isPresell=intent.getIntExtra("isPresell",0);//是否是预售商品
                activityId=intent.getIntExtra("activityId",0);
                Log.d("aaaaordersubmit", "goodsId:" + goodsIdFromGoodsDetailPage + " goodsSpecId:" + goodsSpecIdFromGoodsDetailPage + " buyNum:" + buyNumFromGoodsDetailPage);
                if (goodsIdFromGoodsDetailPage != 0 && goodsSpecIdFromGoodsDetailPage != 0) {
                    getGoodsDetailMsgByGoodsId(goodsIdFromGoodsDetailPage);
                } else {
                    Toast.makeText(this, "抱歉，数据出错啦!", Toast.LENGTH_SHORT).show();
                }
            }

            setListener();

            //预售商品
            if(isPresell==1){
                stvIstributionTime.setSubTitle("预售活动结束后7天内");
            }
            else{
                stvIstributionTime.setSubTitle("请选择");
            }

            stvCoupon.setSubTitle("请选择");
            stvInvoice.setSubTitle("不开发票");
        } else {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


    }

    /**
     * 加载框
     */
    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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

    private void setListener() {
        stvPaymentType.setOnClickListener(this);
        chooseMoreAddress.setOnClickListener(this);
        stvInvoice.setOnClickListener(this);
        stvCoupon.setOnClickListener(this);
        stvIstributionTime.setOnClickListener(this);
        stvActivity.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.stv_payment_type:
                paymentTypePopu = new PaymentTypePopu(OrderSubmitActivity.this);
                paymentTypePopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);
                break;
            case R.id.stv_istribution_time:
               /* new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Log.d("aaaaaaa","时间选择："+String.format("%d-%d-%d",i,i1+1,i2));
                        stvIstributionTime.setSubTitle(String.format("%d-%d-%d",i,i1+1,i2));
                       // System.out.println(String.format("%d-%d-%d",i,i1+1,i2));
                       // mtimebutton.setText(String.format("%d-%d-%d",i,i1+1,i2));
                    }
                },2016,1,1).show();*/
               /* DatePickerDialog datePickerDialog = new DatePickerDialog(this,

                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;
                                // stvIstributionTime.setSubTitle(mYear + "-" + (mMonth + 1) + "-" + mDay);
                                stvIstributionTime.setSubTitle(String.format("%d-%d-%d", year, month + 1, dayOfMonth));

                            }
                        },
                        mYear, mMonth, mDay);
                //设置起始日期和结束日期
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMinDate(System.currentTimeMillis()+86400000);
                datePicker.setMaxDate(System.currentTimeMillis() + 604800000 + 1987200000);
                datePickerDialog.show();*/

                chooseSendToTimePopu = new ChooseSendToTimePopu(this, stvIstributionTime.getSubTitle().toString().trim(),isPresell,presellEndTime);
                chooseSendToTimePopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);
                chooseSendToTimePopu.setSetOnClickBottomOKBtn(this);
                break;
            case R.id.choose_more_address:
                Intent intent = new Intent(OrderSubmitActivity.this, ChooseAddressActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.stv_invoice:
                Intent intent2 = new Intent(OrderSubmitActivity.this, OrderWriteActivity.class);
                intent2.putExtra("chooseInvoiceType", chooseInvoiceType);
                intent2.putExtra("chooseInvoiceLookedUpType", chooseInvoiceLookedUpType);
                intent2.putExtra("chooseInvoiceContentDetail", chooseInvoiceContentDetail);

                startActivityForResult(intent2, 2);
                break;
            case R.id.stv_coupon:
                Intent intent3 = new Intent(OrderSubmitActivity.this, DiscountActivity.class);
                startActivityForResult(intent3, 3);
                break;
            case R.id.stv_activity:
                if(isPresell==2){
                    if (activitysBeanList.size() > 0) {
                        orderChooseGoodsActivityPopu = new OrderChooseGoodsActivityPopu(this, activitysBeanList, activityInformationId);
                        orderChooseGoodsActivityPopu.showAtLocation(this.findViewById(R.id.rl_submit), Gravity.BOTTOM, 0, 0);
                        orderChooseGoodsActivityPopu.setOnClickActivityItem(this);
                    }
                }

                break;
            case R.id.btn_submit:
                buildProgressDialog();
                Tools.addActivity(this);
                //判断是否有收货地址
                if (shippingPeopleMessage.getVisibility() == View.VISIBLE) {
                    //判断是否有购买的商品-
                    if (shouldBuyShoppingCartList.size() > 0) {
                        if ("请选择".equals(stvIstributionTime.getSubTitle().toString().trim())) {
                            cancelProgressDialog();
                            Toast.makeText(this, "请选择送货时间!", Toast.LENGTH_SHORT).show();
                        } else {
                            addOrderModel = new OrderTable();
                            addOrderModel.setUserId(userId);
                            addOrderModel.setOrderOriginalPrice(Double.parseDouble(df.format(titalPrice)));
                            addOrderModel.setOrderPresentPrice(Double.parseDouble(df.format((Double.parseDouble(tvTotal.getText().toString().trim())))) - postage);
                            addOrderModel.setConsigneeName(tvConsigneeName.getText().toString().trim());
                            addOrderModel.setConsigneeAddress(tvConsigneeAddress.getText().toString().trim());
                            addOrderModel.setConsigneeTel(tvConsigneePhone.getText().toString().trim());
                            addOrderModel.setPayType(payType);
                            addOrderModel.setPostage(postage);
                            addOrderModel.setPostagePayType(postagePayType);
                            //是预售商品，此时送货时间为空
                            if(isPresell==1){
                                addOrderModel.setActivityId(presellActivityId);
                            }
                            else{
                                addOrderModel.setActivityId(0);
                                addOrderModel.setDeliverGoodsTime(stvIstributionTime.getSubTitle().toString().trim());
                            }

                            orderDetailsBeanList = new ArrayList<>();
                            for (int i = 0; i < shouldBuyShoppingCartList.size(); i++) {
                                orderDetailsBean = new OrderTable.OrderDetailsBean();
                                for (int g = 0; g < shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size(); g++) {
                                    if (shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsId() == shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(g).getId()) {
                                        orderDetailsBean.setGoodsDetailsId(shouldBuyShoppingCartList.get(i).getGoodsDetailsId());
                                        orderDetailsBean.setGoodsQuantity(shouldBuyShoppingCartList.get(i).getGoodsNum());
                                        orderDetailsBean.setGoodsSpecificationDetailsId(shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsId());
                                        orderDetailsBean.setGoodsPurchasingPrice(shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(g).getGxcPurchase());
                                        orderDetailsBean.setGoodsOriginalPrice(shouldBuyShoppingCartList.get(i).getGoodsUnitlPrice());
                                        int a = 0;
                                        for (a = 0; a < goodsSpecPayMoney.size(); a++) {
                                            //该商品参与了活动
                                            if ((int) goodsSpecPayMoney.get(a).get("goodsSpecId") == shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsId()) {
                                                if ((double) goodsSpecPayMoney.get(a).get("afterActivityGoodsPrice") < 0) {
                                                    orderDetailsBean.setGoodsPaymentPrice(0);
                                                } else {
                                                    orderDetailsBean.setGoodsPaymentPrice((double) goodsSpecPayMoney.get(a).get("afterActivityGoodsPrice"));
                                                }

                                                break;
                                            }
                                        }
                                        if (a == goodsSpecPayMoney.size()) {
                                            orderDetailsBean.setGoodsPaymentPrice(shouldBuyShoppingCartList.get(i).getGoodsUnitlPrice() * shouldBuyShoppingCartList.get(i).getGoodsNum());
                                        }
                                        orderDetailsBean.setGoodsName(shouldBuyShoppingCartList.get(i).getGoodsName());
                                        orderDetailsBean.setGoodsCoverUrl(shouldBuyShoppingCartList.get(i).getGoodsPicUrl());
                                        orderDetailsBean.setGoodsSpecificationName(shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsName());

                                        orderDetailsBeanList.add(orderDetailsBean);
                                        break;
                                    }
                                }

                            }
                            addOrderModel.setOrderDetails(orderDetailsBeanList);

                            //线下支付
                            if (payType == 1) {
                                if ("".equals(offlinePayerName.getText().toString().trim())) {
                                    cancelProgressDialog();
                                    Toast.makeText(this, "请填写支付人姓名", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                if ("".equals(offlinePayerPhone.getText().toString().trim())) {
                                    cancelProgressDialog();
                                    Toast.makeText(this, "请填写支付人联系方式", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                String pattern = "^1[34578]\\d{9}$";
                                if (!offlinePayerPhone.getText().toString().trim().matches(pattern)) {
                                    cancelProgressDialog();
                                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                offlinePaymentBean = new OrderTable.OfflinePaymentBean();
                                offlinePaymentBean.setPayerName(offlinePayerName.getText().toString().trim());
                                offlinePaymentBean.setPayerTel(offlinePayerPhone.getText().toString().trim());
                                addOrderModel.setOfflinePayment(offlinePaymentBean);
                            }

                            //发票
                            if (chooseInvoiceType == 2) {
                                addOrderModel.setIsHasInvoice(0);//无发票
                            } else {
                                addOrderModel.setIsHasInvoice(1);
                                invoiceBean = new OrderTable.InvoiceBean();
                                invoiceBean.setInvoiceType(chooseInvoiceType);
                                invoiceBean.setInvoiceContent(chooseInvoiceContentDetail);
                                switch (chooseInvoiceType) {
                                    //普票
                                    case 0:
                                        invoiceBean.setInvoiceLookedUpType(chooseInvoiceLookedUpType);
                                        //单位
                                        if (chooseInvoiceLookedUpType == 0) {
                                            invoiceBean.setUnitName(unitName);
                                            invoiceBean.setTaxpayerIdentificationNumber(taxpayerIdentificationNumber);
                                        }
                                        break;
                                    //增票
                                    case 1:
                                        invoiceBean.setUnitName(unitName);
                                        invoiceBean.setTaxpayerIdentificationNumber(taxpayerIdentificationNumber);
                                        invoiceBean.setRegisteredAddress(registeredAddress);
                                        invoiceBean.setRegisteredTel(registeredTel);
                                        invoiceBean.setDepositBank(depositBank);
                                        invoiceBean.setBankAccount(bankAccount);
                                        invoiceBean.setBusinessLicenseUrl(businessLicenseUrl);
                                        break;
                                }
                                addOrderModel.setInvoice(invoiceBean);

                            }
                            //该商品是否来自购物车
                            if (isFromWhichPage == 0) {
                                addOrderModel.setIsFromShoppingCart(1);//来自购物车
                            } else {
                                addOrderModel.setIsFromShoppingCart(0);
                            }
                            //是否使用优惠券
                            if (userCouponId == 0) {
                                addOrderModel.setIsUseCoupon(0);//不使用优惠券
                            } else {
                                addOrderModel.setIsUseCoupon(1);
                                userCouponsBean = new OrderTable.UserCouponsBean();
                                userCouponsBean.setId(userCouponId);
                                addOrderModel.setUserCoupons(userCouponsBean);
                            }

                            //查询需购买的商品还有没有库存
                            decisionInventory(addOrderModel);
                        }
                    } else {
                        cancelProgressDialog();
                        Toast.makeText(this, "抱歉，信息错误。", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    cancelProgressDialog();
                    Toast.makeText(this, "暂无收货地址，不能提交订单。", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }

    //设置选中的送货时间
    @Override
    public void onClickBottomOkBtn(String time) {
        stvIstributionTime.setSubTitle(time);
        chooseSendToTimePopu.dismiss();
    }


    //在选择活动的popupwindow中点击选择活动信息
    @Override
    public void onClickActivityItem(int activityInformationId) {
        this.activityInformationId = activityInformationId;
        afterActivityTotalMoney = 0;
        goodsSpecPayMoney = new ArrayList<>();
        Map<String, Object> goodsSpecPayMoneyMap = new HashMap<>();
        for (int i = 0; i < activitysBeanList.size(); i++) {
            if (activitysBeanList.get(i).getActivityInformation().getId() == activityInformationId) {
                goodsSpeIdList = activitysBeanList.get(i).getGoodsSpeIdList();
                activityType = activitysBeanList.get(i).getActivityInformation().getActivityType();
                switch (activitysBeanList.get(i).getActivityInformation().getActivityType()) {
                    case 0:
                        discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//折扣

                        stvActivity.setSubTitle("折扣");
                        //计算参与活动后的总价
                        for (int s = 0; s < shouldBuyShoppingCartList.size(); s++) {
                            int g = 0;
                            for (g = 0; g < goodsSpeIdList.size(); g++) {
                                if (shouldBuyShoppingCartList.get(s).getGoodsSpecificationDetailsId() == goodsSpeIdList.get(g)) {
                                    goodsSpecPayMoneyMap = new HashMap<>();
                                    goodsSpecPayMoneyMap.put("goodsSpecId", goodsSpeIdList.get(g));

                                    double unitprice = shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice();
                                    int goodsNum = shouldBuyShoppingCartList.get(s).getGoodsNum();
                                    double discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//折扣
                                    double afterActivityGoodsPrice = 0;
                                    maxNum = activitysBeanList.get(i).getActivityInformation().getMaxNum();
                                    //限购数量大于等于购买数量
                                    if (activitysBeanList.get(i).getActivityInformation().getMaxNum() >= shouldBuyShoppingCartList.get(s).getGoodsNum()) {

                                        afterActivityGoodsPrice = Double.parseDouble(df.format(unitprice * goodsNum * discount));
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    } else {
                                        int maxNum = activitysBeanList.get(i).getActivityInformation().getMaxNum();
                                        afterActivityGoodsPrice = Double.parseDouble(df.format((unitprice * maxNum * discount) + (unitprice * (goodsNum - maxNum))));
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    }
                                    goodsSpecPayMoney.add(goodsSpecPayMoneyMap);
                                    afterActivityTotalMoney += afterActivityGoodsPrice;
                                    break;
                                }
                            }
                            if (g == goodsSpeIdList.size()) {
                                afterActivityTotalMoney += Double.parseDouble(df.format(shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice() * shouldBuyShoppingCartList.get(s).getGoodsNum()));
                            }
                        }
                        if (afterActivityTotalMoney < 0) {
                            tvTotal.setText(0 + postage + "");
                        } else {
                            if((afterActivityTotalMoney+ postage)>1){
                                tvTotal.setText(df.format(afterActivityTotalMoney+ postage)  + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(afterActivityTotalMoney+ postage)) + "");
                            }

                            if (afterActivityTotalMoney >= 50000) {
                                tvSubmitPrompt.setVisibility(View.VISIBLE);
                                tvSubmitPrompt.setText(R.string.text_submit_order_money_5);
                                stvPaymentType.setSubTitle("线下支付");
                                offlinePayerMsg.setVisibility(View.VISIBLE);
                                payType = 1;
                            } else {
                                //是预售商品
                                if(isPresell==1){
                                    tvSubmitPrompt.setVisibility(View.VISIBLE);
                                }
                                else{
                                    tvSubmitPrompt.setVisibility(View.GONE);
                                }
                                stvPaymentType.setSubTitle("在线支付");
                                offlinePayerMsg.setVisibility(View.GONE);
                                payType = 0;
                            }

                        }
                        break;
                    case 1:
                        discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//团购价
                        stvActivity.setSubTitle("团购");
                        for (int s = 0; s < shouldBuyShoppingCartList.size(); s++) {
                            int g = 0;
                            for (g = 0; g < goodsSpeIdList.size(); g++) {
                                if (shouldBuyShoppingCartList.get(s).getGoodsSpecificationDetailsId() == goodsSpeIdList.get(g)) {
                                    goodsSpecPayMoneyMap = new HashMap<>();
                                    goodsSpecPayMoneyMap.put("goodsSpecId", goodsSpeIdList.get(g));

                                    double unitprice = shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice();
                                    int goodsNum = shouldBuyShoppingCartList.get(s).getGoodsNum();
                                    double discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//团购价
                                    double afterActivityGoodsPrice = 0;
                                    maxNum = activitysBeanList.get(i).getActivityInformation().getMaxNum();
                                    //限购数量大于等于购买数量
                                    if (activitysBeanList.get(i).getActivityInformation().getMaxNum() >= shouldBuyShoppingCartList.get(s).getGoodsNum()) {
                                        afterActivityGoodsPrice = Double.parseDouble(df.format(goodsNum * discount));
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    } else {
                                        int maxNum = activitysBeanList.get(i).getActivityInformation().getMaxNum();
                                        afterActivityGoodsPrice = Double.parseDouble(df.format((maxNum * discount) + (unitprice * (goodsNum - maxNum))));
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    }
                                    goodsSpecPayMoney.add(goodsSpecPayMoneyMap);
                                    afterActivityTotalMoney += afterActivityGoodsPrice;
                                    break;
                                }
                            }
                            if (g == goodsSpeIdList.size()) {
                                afterActivityTotalMoney += Double.parseDouble(df.format(shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice() * shouldBuyShoppingCartList.get(s).getGoodsNum()));
                            }
                        }
                        if (afterActivityTotalMoney < 0) {
                            tvTotal.setText(0 + postage + "");
                        } else {
                            if((afterActivityTotalMoney+ postage)>1){
                                tvTotal.setText(df.format(afterActivityTotalMoney+ postage)  + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(afterActivityTotalMoney+ postage)) + "");
                            }

                            if (afterActivityTotalMoney >= 50000) {
                                tvSubmitPrompt.setVisibility(View.VISIBLE);
                                tvSubmitPrompt.setText(R.string.text_submit_order_money_5);
                                stvPaymentType.setSubTitle("线下支付");
                                offlinePayerMsg.setVisibility(View.VISIBLE);
                                payType = 1;
                            } else {
                                //是预售商品
                                if(isPresell==1){
                                    tvSubmitPrompt.setVisibility(View.VISIBLE);
                                }
                                else{
                                    tvSubmitPrompt.setVisibility(View.GONE);
                                }
                                stvPaymentType.setSubTitle("在线支付");
                                offlinePayerMsg.setVisibility(View.GONE);
                                payType = 0;
                            }

                        }
                        break;
                    case 2:
                        discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//秒杀价
                        stvActivity.setSubTitle("秒杀");
                        for (int s = 0; s < shouldBuyShoppingCartList.size(); s++) {
                            int g = 0;
                            for (g = 0; g < goodsSpeIdList.size(); g++) {
                                if (shouldBuyShoppingCartList.get(s).getGoodsSpecificationDetailsId() == goodsSpeIdList.get(g)) {
                                    goodsSpecPayMoneyMap = new HashMap<>();
                                    goodsSpecPayMoneyMap.put("goodsSpecId", goodsSpeIdList.get(g));

                                    double unitprice = shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice();
                                    int goodsNum = shouldBuyShoppingCartList.get(s).getGoodsNum();
                                    double discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//秒杀价
                                    double afterActivityGoodsPrice = 0;
                                    maxNum = activitysBeanList.get(i).getActivityInformation().getMaxNum();
                                    //限购数量大于等于购买数量
                                    if (activitysBeanList.get(i).getActivityInformation().getMaxNum() >= shouldBuyShoppingCartList.get(s).getGoodsNum()) {
                                        afterActivityGoodsPrice = Double.parseDouble(df.format(goodsNum * discount));
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    } else {
                                        int maxNum = activitysBeanList.get(i).getActivityInformation().getMaxNum();

                                        afterActivityGoodsPrice = Double.parseDouble(df.format((maxNum * discount) + (unitprice * (goodsNum - maxNum))));
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    }
                                    goodsSpecPayMoney.add(goodsSpecPayMoneyMap);
                                    afterActivityTotalMoney += afterActivityGoodsPrice;
                                    break;
                                }
                            }
                            if (g == goodsSpeIdList.size()) {
                                afterActivityTotalMoney += Double.parseDouble(df.format(shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice() * shouldBuyShoppingCartList.get(s).getGoodsNum()));
                            }
                        }
                        if (afterActivityTotalMoney < 0) {
                            tvTotal.setText(0 + postage + "");
                        } else {
                            if((afterActivityTotalMoney+ postage)>1){
                                tvTotal.setText(df.format(afterActivityTotalMoney+ postage)  + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(afterActivityTotalMoney+ postage)) + "");
                            }

                            if (afterActivityTotalMoney >= 50000) {
                                tvSubmitPrompt.setVisibility(View.VISIBLE);
                                tvSubmitPrompt.setText(R.string.text_submit_order_money_5);
                                stvPaymentType.setSubTitle("线下支付");
                                offlinePayerMsg.setVisibility(View.VISIBLE);
                                payType = 1;
                            } else {
                                //是预售商品
                                if(isPresell==1){
                                    tvSubmitPrompt.setVisibility(View.VISIBLE);
                                }
                                else{
                                    tvSubmitPrompt.setVisibility(View.GONE);
                                }
                                stvPaymentType.setSubTitle("在线支付");
                                offlinePayerMsg.setVisibility(View.GONE);
                                payType = 0;
                            }

                        }
                        break;
                    case 3:
                        discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//立减金额
                        stvActivity.setSubTitle("立减");
                        for (int s = 0; s < shouldBuyShoppingCartList.size(); s++) {
                            int g = 0;
                            for (g = 0; g < goodsSpeIdList.size(); g++) {
                                if (shouldBuyShoppingCartList.get(s).getGoodsSpecificationDetailsId() == goodsSpeIdList.get(g)) {
                                    goodsSpecPayMoneyMap = new HashMap<>();
                                    goodsSpecPayMoneyMap.put("goodsSpecId", goodsSpeIdList.get(g));

                                    double unitprice = shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice();
                                    int goodsNum = shouldBuyShoppingCartList.get(s).getGoodsNum();
                                    double discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//立减金额
                                    double afterActivityGoodsPrice = 0;
                                    maxNum = activitysBeanList.get(i).getActivityInformation().getMaxNum();

                                    afterActivityGoodsPrice = Double.parseDouble(df.format(goodsNum * unitprice - discount));
                                    goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    goodsSpecPayMoney.add(goodsSpecPayMoneyMap);
                                    //限购数量大于等于购买数量
                                    /*if(activitysBeanList.get(i).getActivityInformation().getMaxNum()>=shouldBuyShoppingCartList.get(s).getGoodsNum()){
                                        afterActivityGoodsPrice=goodsNum*unitprice-discount;
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice",afterActivityGoodsPrice);
                                    }
                                    else{
                                        int maxNum=activitysBeanList.get(i).getActivityInformation().getMaxNum();
                                        afterActivityGoodsPrice=(maxNum*unitprice-discount)+(unitprice*(goodsNum-maxNum));
                                        goodsSpecPayMoneyMap.put("afterActivityGoodsPrice",afterActivityGoodsPrice);
                                    }*/
                                    afterActivityTotalMoney += afterActivityGoodsPrice;
                                    break;
                                }
                            }
                            if (g == goodsSpeIdList.size()) {
                                afterActivityTotalMoney += Double.parseDouble(df.format(shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice() * shouldBuyShoppingCartList.get(s).getGoodsNum()));
                            }
                        }
                        if (afterActivityTotalMoney < 0) {
                            tvTotal.setText(0 + postage + "");
                        } else {
                            if((afterActivityTotalMoney+ postage)>1){
                                tvTotal.setText(df.format(afterActivityTotalMoney+ postage)  + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(afterActivityTotalMoney+ postage)) + "");
                            }

                            if (afterActivityTotalMoney >= 50000) {
                                tvSubmitPrompt.setVisibility(View.VISIBLE);
                                tvSubmitPrompt.setText(R.string.text_submit_order_money_5);
                                stvPaymentType.setSubTitle("线下支付");
                                offlinePayerMsg.setVisibility(View.VISIBLE);
                                payType = 1;
                            } else {
                                //是预售商品
                                if(isPresell==1){
                                    tvSubmitPrompt.setVisibility(View.VISIBLE);
                                }
                                else{
                                    tvSubmitPrompt.setVisibility(View.GONE);
                                }
                                stvPaymentType.setSubTitle("在线支付");
                                offlinePayerMsg.setVisibility(View.GONE);
                                payType = 0;
                            }

                        }
                        break;
                    case 4:
                        thresholdPrice = activitysBeanList.get(i).getActivityInformation().getPrice();//门槛金额
                        discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//满减金额
                        stvActivity.setSubTitle("满减");
                        for (int s = 0; s < shouldBuyShoppingCartList.size(); s++) {
                            int g = 0;
                            for (g = 0; g < goodsSpeIdList.size(); g++) {
                                if (shouldBuyShoppingCartList.get(s).getGoodsSpecificationDetailsId() == goodsSpeIdList.get(g)) {
                                    goodsSpecPayMoneyMap = new HashMap<>();
                                    goodsSpecPayMoneyMap.put("goodsSpecId", goodsSpeIdList.get(g));

                                    double unitprice = shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice();
                                    int goodsNum = shouldBuyShoppingCartList.get(s).getGoodsNum();
                                    double discount = activitysBeanList.get(i).getActivityInformation().getDiscount();//满减金额
                                    double afterActivityGoodsPrice = 0;

                                    afterActivityGoodsPrice = Double.parseDouble(df.format(goodsNum * unitprice - discount));
                                    goodsSpecPayMoneyMap.put("afterActivityGoodsPrice", afterActivityGoodsPrice);
                                    goodsSpecPayMoney.add(goodsSpecPayMoneyMap);

                                    afterActivityTotalMoney += afterActivityGoodsPrice;
                                    break;
                                }
                            }
                            if (g == goodsSpeIdList.size()) {
                                afterActivityTotalMoney += Double.parseDouble(df.format(shouldBuyShoppingCartList.get(s).getGoodsUnitlPrice() * shouldBuyShoppingCartList.get(s).getGoodsNum()));
                            }
                        }
                        if (afterActivityTotalMoney < 0) {
                            tvTotal.setText(0 + postage + "");
                        } else {
                            if((afterActivityTotalMoney+ postage)>1){
                                tvTotal.setText(df.format(afterActivityTotalMoney+ postage)  + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(afterActivityTotalMoney+ postage)) + "");
                            }

                            if (afterActivityTotalMoney >= 50000) {
                                tvSubmitPrompt.setVisibility(View.VISIBLE);
                                tvSubmitPrompt.setText(R.string.text_submit_order_money_5);
                                stvPaymentType.setSubTitle("线下支付");
                                offlinePayerMsg.setVisibility(View.VISIBLE);
                                payType = 1;
                            } else {
                                //是预售商品
                                if(isPresell==1){
                                    tvSubmitPrompt.setVisibility(View.VISIBLE);
                                }
                                else{
                                    tvSubmitPrompt.setVisibility(View.GONE);
                                }
                                stvPaymentType.setSubTitle("在线支付");
                                offlinePayerMsg.setVisibility(View.GONE);
                                payType = 0;
                            }

                        }

                        break;
                    default:
                        stvActivity.setSubTitle("请选择");
                        break;
                }
                //需给优惠券重新赋值，因为可能有某个优惠券不适用该活动
                shouldShowCouponsBeanList.clear();
                userCouponPrice = 0;
                userCouponId = 0;
                if (activitysBeanList.get(i).getUserCouponMsgList() != null) {
                    for (int a = 0; a < activitysBeanList.get(i).getUserCouponMsgList().size(); a++) {
                        couponsBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean();
                        userCouponsBean1 = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean();
                        userCouponsBean1.setId(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getId());
                        userCouponsBean1.setUserId(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getUserId());
                        userCouponsBean1.setCouponInformationId(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformationId());
                        userCouponsBean1.setStatus(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getStatus());
                        userCouponsBean1.setGetTime(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getGetTime());
                        couponInformationBean = new ActivitysAndCouponsByGoodsMsgModel.ResultDataBean.CouponsBean.UserCouponsBean.CouponInformationBean();
                        couponInformationBean.setId(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getId());
                        couponInformationBean.setIdentifier(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getIdentifier());
                        couponInformationBean.setName(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getName());
                        couponInformationBean.setPrice(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getPrice());
                        couponInformationBean.setTotal(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getTotal());
                        couponInformationBean.setUseLimit(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getUseLimit());
                        couponInformationBean.setCount(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getCount());
                        couponInformationBean.setState(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getState());
                        couponInformationBean.setRules(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getRules());
                        couponInformationBean.setBeginValidityTime(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getBeginValidityTime());
                        couponInformationBean.setEndValidityTime(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getEndValidityTime());
                        couponInformationBean.setBeginTime(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getBeginTime());
                        couponInformationBean.setEndTime(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getEndTime());
                        couponInformationBean.setOperatorIdentifier(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getOperatorIdentifier());
                        couponInformationBean.setOperatorTime(activitysBeanList.get(i).getUserCouponMsgList().get(a).getUserCoupons().getCouponInformation().getOperatorTime());
                        userCouponsBean1.setCouponInformation(couponInformationBean);
                        couponsBean.setUserCoupons(userCouponsBean1);
                        couponsBean.setAfterDiscountMoney(activitysBeanList.get(i).getUserCouponMsgList().get(a).getAfterDiscountMoney());
                        shouldShowCouponsBeanList.add(couponsBean);
                    }
                }
                if (shouldShowCouponsBeanList.size() == 0) {
                    llStvCoupon.setVisibility(View.GONE);
                    stvCoupon.setSubTitle("暂无优惠券");
                } else {
                    llStvCoupon.setVisibility(View.VISIBLE);
                    stvCoupon.setSubTitle("请选择");
                }


            }
        }
    }

    //获取从跳转后的页面返回的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("aaaaaaaGoods", "onActivityResult");
        Log.d("aaaaaaaGoods", "resultCode:" + resultCode);
        switch (resultCode) {
            //选择地址
            case 1:
                Log.d("aaaaaaaGoods", "address:" + data.getStringExtra("address"));
                consigneeAddress = data.getStringExtra("address");
                consigneeName = data.getStringExtra("name");
                consigneeTel = data.getStringExtra("phone");
                consigneeIsVip = data.getIntExtra("isDefault", 0);

                tvConsigneeAddress.setText(data.getStringExtra("address"));
                tvConsigneeName.setText(data.getStringExtra("name"));
                tvConsigneePhone.setText(data.getStringExtra("phone"));
                if (data.getIntExtra("isDefault", 0) == 1) {
                    tvDefault.setVisibility(View.VISIBLE);
                } else {
                    tvDefault.setVisibility(View.GONE);
                }

                provinceCode = data.getStringExtra("provinceCode");
                Log.d("aaaaaaaGoods", "onActivityResult provinceCode:" + provinceCode);
                cityCode = data.getStringExtra("cityCode");
                countyCode = data.getStringExtra("countyCode");
                ringCode = data.getStringExtra("ringCode");
                if (cityCode == null || "".equals(cityCode)) {
                    cityCode = "0";
                }
                getPostage(provinceCode, cityCode, countyCode, ringCode, titalPrice);
                break;
            case 2:
                Log.d(TAG, "chooseInvoiceType:" + data.getIntExtra("chooseInvoiceType", 2));
                chooseInvoiceType = data.getIntExtra("chooseInvoiceType", 2);

                switch (chooseInvoiceType) {
                    //普票
                    case 0:
                        chooseInvoiceLookedUpType = data.getIntExtra("chooseInvoiceLookedUpType", 0);
                        chooseInvoiceContentDetail = data.getIntExtra("chooseInvoiceContentDetail", 0);
                        //单位
                        if (chooseInvoiceLookedUpType == 0) {
                            unitName = data.getStringExtra("unitName");
                            taxpayerIdentificationNumber = data.getStringExtra("taxpayerIdentificationNumber");
                            stvInvoice.setSubTitle("单位");
                        } else {
                            stvInvoice.setSubTitle("个人");
                        }
                        break;
                    //增票
                    case 1:
                        stvInvoice.setSubTitle("增值税专用发票");

                        chooseInvoiceContentDetail = data.getIntExtra("chooseInvoiceContentDetail", 0);
                        unitName = data.getStringExtra("unitName");
                        taxpayerIdentificationNumber = data.getStringExtra("taxpayerIdentificationNumber");
                        registeredAddress = data.getStringExtra("registeredAddress");
                        registeredTel = data.getStringExtra("registeredTel");
                        depositBank = data.getStringExtra("depositBank");
                        bankAccount = data.getStringExtra("bankAccount");
                        businessLicenseUrl = data.getStringExtra("businessLicenseUrl");
                        break;
                    case 2:
                        stvInvoice.setSubTitle("不开发票");
                        break;
                }

                break;
            case 3:
                userCouponPrice = data.getDoubleExtra("price", 0);
                userCouponId = data.getIntExtra("couponId", 0);
                stvCoupon.setSubTitle(userCouponPrice + "元(" + data.getStringExtra("countMsg") + ")");
                double price = Double.parseDouble(tvTotal.getText().toString().trim());
                double isUseCouponPrice = Double.parseDouble(df.format(price - userCouponPrice));
                if (isUseCouponPrice <= 0) {
                    //是预售商品
                    if(isPresell==1){
                        tvSubmitPrompt.setVisibility(View.VISIBLE);
                    }
                    else{
                        tvSubmitPrompt.setVisibility(View.GONE);
                    }
                    tvTotal.setText(0 + "");
                    stvPaymentType.setSubTitle("在线支付");
                    offlinePayerMsg.setVisibility(View.GONE);
                    payType = 0;
                } else {
                    if((price - userCouponPrice)>1){
                        tvTotal.setText(df.format(price - userCouponPrice)  + "");
                    }else{
                        tvTotal.setText(Double.parseDouble(df.format(price - userCouponPrice)) + "");
                    }
                    Log.d("aaaaaaaaaaaaaaaaaa","tvTotal:"+tvTotal.getText().toString().trim());
                    Log.d("aaaaaaaaaaaaaaaaaa","price - userCouponPrice:"+isUseCouponPrice);
                    if (isUseCouponPrice >= 50000) {
                        tvSubmitPrompt.setVisibility(View.VISIBLE);
                        tvSubmitPrompt.setText(R.string.text_submit_order_money_5);
                        stvPaymentType.setSubTitle("线下支付");
                        offlinePayerMsg.setVisibility(View.VISIBLE);
                        payType = 1;
                    } else {
                        //是预售商品
                        if(isPresell==1){
                            tvSubmitPrompt.setVisibility(View.VISIBLE);
                        }
                        else{
                            tvSubmitPrompt.setVisibility(View.GONE);
                        }
                        stvPaymentType.setSubTitle("在线支付");
                        offlinePayerMsg.setVisibility(View.GONE);
                        payType = 0;
                    }
                }
                break;
        }

    }


    //给商品信息添加adapter
    private void setGoodsMsgAdapter() {
        rlFoodDetailRecyclerView.setLayoutManager(new CustomLanearLayoutManager(this, false));
        writeOrderGoodsMsgAdapter = new WriteOrderGoodsMsgAdapter(R.layout.item_write_order_goods_msg, shouldBuyShoppingCartList);
        writeOrderGoodsMsgAdapter.setIsFromGoodsDetail(isFromWhichPage);
        writeOrderGoodsMsgAdapter.setBuyNum(buyNumFromGoodsDetailPage);
        rlFoodDetailRecyclerView.setAdapter(writeOrderGoodsMsgAdapter);
        writeOrderGoodsMsgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                goodsPosition = position;
                afterActivityTotalMoney = 0;
                userCouponPrice = 0;
                userCouponId = 0;
                switch (view.getId()) {
                    //减
                    case R.id.tv_reduce:
                        //从购物车页面跳转而来
                        /*if (isFromWhichPage == 0) {*/
                        TextView tvReduce = view.findViewById(R.id.tv_reduce);
                        ShoppingCartModel.ResultDataBean changeDataBean = shouldBuyShoppingCartList.get(position);
                        if (changeDataBean.getGoodsDetails() != null) {
                            if (changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                for (int i = 0; i < changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                                    if (changeDataBean.getGoodsSpecificationDetailsId() == changeDataBean.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {
                                        if (changeDataBean.getGoodsNum() <= 1) {
                                            Toast.makeText(OrderSubmitActivity.this, "不能再减少了!", Toast.LENGTH_SHORT).show();
                                            tvReduce.setTextColor(getResources().getColor(R.color.moreText));
                                        } else {
                                            changeDataBean.setGoodsNum(changeDataBean.getGoodsNum() - 1);
                                        }
                                    }
                                }
                            }
                        }
                        writeOrderGoodsMsgAdapter.notifyDataSetChanged();
                        countGoodsAllPriceFromShopCart();

                        provinceCode = shipAddressResultDataBeans.get(0).getProvinceCode();
                        cityCode = shipAddressResultDataBeans.get(0).getCityCode();
                        countyCode = shipAddressResultDataBeans.get(0).getCountyCode();
                        ringCode = shipAddressResultDataBeans.get(0).getRingCode();
                        if (cityCode == null || "".equals(cityCode)) {
                            cityCode = "0";
                        }
                        //获取邮费并给底部总价赋值
                        getPostage(provinceCode, cityCode, countyCode, ringCode, titalPrice);
                        //重新获取商品活动信息
                        goodsSpecPayMoney.clear();
                        saveMsgToGetAvticityAndCoupon(shouldBuyShoppingCartList);
                        stvCoupon.setSubTitle("请选择");
                        stvActivity.setSubTitle("请选择");
                        activityInformationId = 0;
                        activityType = -1;
                       /* }*/

                        break;
                    //加
                    case R.id.tv_add:
                       /* if (isFromWhichPage == 0) {*/
                        TextView tvAdd = view.findViewById(R.id.tv_add);
                        ShoppingCartModel.ResultDataBean changeDataBean2 = shouldBuyShoppingCartList.get(position);
                        if (changeDataBean2.getGoodsDetails() != null) {
                            if (changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                for (int i = 0; i < changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                                    if (changeDataBean2.getGoodsSpecificationDetailsId() == changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {
                                        //不允许0库存出货并且不是预售商品
                                        if(changeDataBean2.getGoodsDetails().getZeroStock()==0&&changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState()==2){

                                            if (changeDataBean2.getGoodsNum() >= changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock()) {

                                                Toast.makeText(OrderSubmitActivity.this, "已到库存上限!", Toast.LENGTH_SHORT).show();
                                                tvAdd.setTextColor(getResources().getColor(R.color.moreText));
                                            } else {

                                                if (changeDataBean2.getGoodsNum() >= 500) {

                                                    changeDataBean2.setGoodsNum(500);
                                                    tvAdd.setTextColor(getResources().getColor(R.color.moreText));
                                                } else {

                                                    changeDataBean2.setGoodsNum(changeDataBean2.getGoodsNum() + 1);
                                                    if (changeDataBean2.getGoodsNum() == 500) {

                                                        tvAdd.setTextColor(getResources().getColor(R.color.moreText));
                                                    } else {

                                                        tvAdd.setTextColor(getResources().getColor(R.color.trans_333333));
                                                    }
                                                }
                                            }
                                        }
                                        else{

                                            if (changeDataBean2.getGoodsNum() >= 500) {

                                                changeDataBean2.setGoodsNum(500);
                                                tvAdd.setTextColor(getResources().getColor(R.color.moreText));
                                            } else {

                                                changeDataBean2.setGoodsNum(changeDataBean2.getGoodsNum() + 1);
                                                if (changeDataBean2.getGoodsNum() == 500) {

                                                    tvAdd.setTextColor(getResources().getColor(R.color.moreText));
                                                } else {

                                                    tvAdd.setTextColor(getResources().getColor(R.color.trans_333333));
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        writeOrderGoodsMsgAdapter.notifyDataSetChanged();
                        countGoodsAllPriceFromShopCart();

                        provinceCode = shipAddressResultDataBeans.get(0).getProvinceCode();
                        cityCode = shipAddressResultDataBeans.get(0).getCityCode();
                        countyCode = shipAddressResultDataBeans.get(0).getCountyCode();
                        ringCode = shipAddressResultDataBeans.get(0).getRingCode();
                        if (cityCode == null || "".equals(cityCode)) {
                            cityCode = "0";
                        }
                        //获取邮费并给底部总价赋值
                        getPostage(provinceCode, cityCode, countyCode, ringCode, titalPrice);
                        //重新获取商品活动信息
                        goodsSpecPayMoney.clear();
                        saveMsgToGetAvticityAndCoupon(shouldBuyShoppingCartList);
                        stvActivity.setSubTitle("请选择");
                        stvCoupon.setSubTitle("请选择");
                        activityInformationId = 0;
                        activityType = -1;
                        /*}*/
                        break;
                    case R.id.shop_cart_goods_buy_num:
                        numTv = view.findViewById(R.id.shop_cart_goods_buy_num);
                        ShoppingCartModel.ResultDataBean changeDataBean3 = shouldBuyShoppingCartList.get(position);
                        if (changeDataBean3.getGoodsDetails() != null) {
                            if (changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                                for (int i = 0; i < changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                                    if (changeDataBean3.getGoodsSpecificationDetailsId() == changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {
                                        modifyNumPopu = new ModifyNumPopu(OrderSubmitActivity.this, changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsStock(), numTv.getText().toString().trim(),changeDataBean3.getGoodsDetails().getZeroStock(),changeDataBean3.getGoodsDetails().getGoodsSpecificationDetails().get(i).getGxcGoodsState());
                                        modifyNumPopu.setOnClickOkBtn(OrderSubmitActivity.this);
                                        modifyNumPopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);
                                    }
                                }
                            }
                        }

                        break;
                }
            }
        });
    }

    //直接修改数量的底部确定按钮的点击事件
    @Override
    public void OnClickOkBtn(String num) {
        modifyNumPopu.dismiss();
        numTv.setText(num);
        ShoppingCartModel.ResultDataBean changeDataBean2 = shouldBuyShoppingCartList.get(goodsPosition);
        if (changeDataBean2.getGoodsDetails() != null) {
            if (changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                for (int i = 0; i < changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().size(); i++) {
                    if (changeDataBean2.getGoodsSpecificationDetailsId() == changeDataBean2.getGoodsDetails().getGoodsSpecificationDetails().get(i).getId()) {

                        changeDataBean2.setGoodsNum(Integer.parseInt(num));

                    }
                }
            }
        }
        writeOrderGoodsMsgAdapter.notifyDataSetChanged();
        countGoodsAllPriceFromShopCart();

        provinceCode = shipAddressResultDataBeans.get(0).getProvinceCode();
        cityCode = shipAddressResultDataBeans.get(0).getCityCode();
        countyCode = shipAddressResultDataBeans.get(0).getCountyCode();
        ringCode = shipAddressResultDataBeans.get(0).getRingCode();
        if (cityCode == null || "".equals(cityCode)) {
            cityCode = "0";
        }
        //获取邮费并给底部总价赋值
        getPostage(provinceCode, cityCode, countyCode, ringCode, titalPrice);
        //重新获取商品活动信息
        goodsSpecPayMoney.clear();
        saveMsgToGetAvticityAndCoupon(shouldBuyShoppingCartList);
        stvActivity.setSubTitle("请选择");
        stvCoupon.setSubTitle("请选择");
        activityInformationId = 0;
        activityType = -1;
    }

    //整理从购物车中获取的数据，与需要购买的数据进行比较，保存下来。
    private void getShouldBuyGoodsFromShopCart() {
        shouldBuyShoppingCartList.clear();
        if (shoppingCartList.size() > 0) {
            for (int i = 0; i < buyGoodsMsg.length; i++) {
                for (int s = 0; s < shoppingCartList.size(); s++) {
                    if (shoppingCartList.get(s).getId() == buyGoodsMsg[i]) {
                        shouldBuyShoppingCartList.add(shoppingCartList.get(s));
                        break;
                    }
                }
            }
            saveMsgToGetAvticityAndCoupon(shouldBuyShoppingCartList);

        } else {
            Toast.makeText(this, "抱歉，数据出错啦!", Toast.LENGTH_SHORT).show();
        }
    }

    //从商品详情页跳转过来后，根据规格id获取到数据，在放入shouldBuyShoppingCartList中
    private void getShouldBuyGoodsFromGoodsDetailPage(GoodsDetailModel.ResultDataBean goodsDetailDataBean) {
        shouldBuyShoppingCartList.clear();

        ShoppingCartModel.ResultDataBean dataBean = new ShoppingCartModel.ResultDataBean();
        dataBean.setGoodsDetailsId(goodsDetailDataBean.getId());
        dataBean.setGoodsNum(buyNumFromGoodsDetailPage);
        dataBean.setGoodsName(goodsDetailDataBean.getName());

        ShoppingCartModel.ResultDataBean.GoodsDetailsBean goodsDetailsBean = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean();
        List<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean> goodsSpecificationDetailsBeans = new ArrayList<>();

        for (int i = 0; i < goodsDetailDataBean.getGoodsSpecificationDetails().size(); i++) {
            if (goodsDetailDataBean.getGoodsSpecificationDetails().get(i).getId() == goodsSpecIdFromGoodsDetailPage) {
                GoodsDetailModel.ResultDataBean.GoodsSpecificationDetailsBean goodsSpecificationDetailsBean = goodsDetailDataBean.getGoodsSpecificationDetails().get(i);

                if (goodsSpecificationDetailsBean.getGoodsDisplayPictures().size() > 0) {
                    dataBean.setGoodsPicUrl(goodsSpecificationDetailsBean.getGoodsDisplayPictures().get(0).getPicUrl());
                } else {
                    dataBean.setGoodsPicUrl("");
                }
                dataBean.setGoodsSpecificationDetailsName(goodsSpecificationDetailsBean.getSpecifications());
                dataBean.setGoodsSpecificationDetailsId(goodsSpecificationDetailsBean.getId());
                dataBean.setGoodsUnitlPrice(goodsSpecificationDetailsBean.getPrice());
                dataBean.setState(0);

                goodsDetailsBean.setId(goodsDetailDataBean.getId());
                goodsDetailsBean.setKeyWord(goodsDetailDataBean.getKeyWord());
                goodsDetailsBean.setName(goodsDetailDataBean.getName());
                goodsDetailsBean.setZeroStock(goodsDetailDataBean.getZeroStock());

                ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean goodsSpecificationDetailsBean1 = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean();
                goodsSpecificationDetailsBean1.setId(goodsSpecificationDetailsBean.getId());
                goodsSpecificationDetailsBean1.setGoodsId(goodsSpecificationDetailsBean.getGoodsId());
                goodsSpecificationDetailsBean1.setGxcGoodsState(goodsSpecificationDetailsBean.getGxcGoodsState());
                goodsSpecificationDetailsBean1.setGxcGoodsStock(goodsSpecificationDetailsBean.getGxcGoodsStock());
                goodsSpecificationDetailsBean1.setGxcPurchase(goodsSpecificationDetailsBean.getGxcPurchase());
                goodsSpecificationDetailsBean1.setIdentifier(goodsSpecificationDetailsBean.getIdentifier());
                goodsSpecificationDetailsBean1.setPrice(goodsSpecificationDetailsBean.getPrice());
                goodsSpecificationDetailsBean1.setSpecifications(goodsSpecificationDetailsBean.getSpecifications());
                goodsSpecificationDetailsBean1.setState(goodsSpecificationDetailsBean.getState());

                //获取规格的活动
                List<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean> goodsActivitysBeans = new ArrayList<>();
                ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean goodsActivitysBean = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean();
                for (int a = 0; a < goodsSpecificationDetailsBean.getGoodsActivitys().size(); a++) {
                    goodsActivitysBean = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean();
                    GoodsDetailModel.ResultDataBean.GoodsActivitysBean goodsActivitysBean1 = goodsSpecificationDetailsBean.getGoodsActivitys().get(a);

                    goodsActivitysBean.setActivityInformationId(goodsActivitysBean1.getActivityInformationId());
                    goodsActivitysBean.setGoodsId(goodsActivitysBean1.getGoodsId());
                    goodsActivitysBean.setId(goodsActivitysBean1.getIdX());
                    goodsActivitysBean.setState(goodsActivitysBean1.getState());

                    ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean.ActivityInformationBean activityInformationBean = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean.ActivityInformationBean();
                    GoodsDetailModel.ResultDataBean.GoodsActivitysBean.ActivityInformationBean activityInformationBean1 = goodsSpecificationDetailsBean.getGoodsActivitys().get(a).getActivityInformation();
                    if (activityInformationBean1 != null) {
                        activityInformationBean.setActivityType(activityInformationBean1.getActivityType());
                        activityInformationBean.setBeginValidityTime(activityInformationBean1.getBeginValidityTime());
                        activityInformationBean.setDiscount(activityInformationBean1.getDiscount());
                        activityInformationBean.setEndValidityTime(activityInformationBean1.getEndValidityTime());
                        activityInformationBean.setId(activityInformationBean1.getIdX());
                        activityInformationBean.setIdentifier(activityInformationBean1.getIdentifier());
                        activityInformationBean.setName(activityInformationBean1.getNameX());
                        activityInformationBean.setPrice(activityInformationBean1.getPrice());
                        activityInformationBean.setMaxNum(activityInformationBean1.getMaxNum());
                        activityInformationBean.setState(4);
                    }
                    goodsActivitysBean.setActivityInformation(activityInformationBean);
                    goodsActivitysBeans.add(goodsActivitysBean);
                }
                goodsSpecificationDetailsBean1.setGoodsActivitys(goodsActivitysBeans);

                goodsSpecificationDetailsBeans.add(goodsSpecificationDetailsBean1);
                break;
            }


        }
        goodsDetailsBean.setGoodsSpecificationDetails(goodsSpecificationDetailsBeans);

        //获取商品的活动
        List<ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX> goodsActivitysBeanXES = new ArrayList<>();

        if (goodsDetailDataBean.getGoodsActivitysX().size() > 0) {
            ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX goodsActivitysBeanX;
            for (int i = 0; i < goodsDetailDataBean.getGoodsActivitysX().size(); i++) {
                GoodsDetailModel.ResultDataBean.GoodsActivitysBean goodsActivitysBean = goodsDetailDataBean.getGoodsActivitysX().get(i);
                goodsActivitysBeanX = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX();

                goodsActivitysBeanX.setActivityInformationId(goodsActivitysBean.getActivityInformationId());
                goodsActivitysBeanX.setGoodsId(goodsActivitysBean.getGoodsId());
                goodsActivitysBeanX.setId(goodsActivitysBean.getIdX());
                goodsActivitysBeanX.setState(goodsActivitysBean.getState());

                ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX.ActivityInformationBeanX activityInformationBeanX = new ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX.ActivityInformationBeanX();
                GoodsDetailModel.ResultDataBean.GoodsActivitysBean.ActivityInformationBean activityInformationBean1 = goodsActivitysBean.getActivityInformation();
                if (activityInformationBean1 != null) {
                    activityInformationBeanX.setActivityType(activityInformationBean1.getActivityType());
                    activityInformationBeanX.setBeginValidityTime(activityInformationBean1.getBeginValidityTime());
                    activityInformationBeanX.setDiscount(activityInformationBean1.getDiscount());
                    activityInformationBeanX.setEndValidityTime(activityInformationBean1.getEndValidityTime());
                    activityInformationBeanX.setId(activityInformationBean1.getIdX());
                    activityInformationBeanX.setIdentifier(activityInformationBean1.getIdentifier());
                    activityInformationBeanX.setName(activityInformationBean1.getNameX());
                    activityInformationBeanX.setPrice(activityInformationBean1.getPrice());
                    activityInformationBeanX.setMaxNum(activityInformationBean1.getMaxNum());
                    activityInformationBeanX.setState(4);
                }
                goodsActivitysBeanX.setActivityInformation(activityInformationBeanX);
                goodsActivitysBeanXES.add(goodsActivitysBeanX);
            }
        }
        goodsDetailsBean.setGoodsActivitys(goodsActivitysBeanXES);
        dataBean.setGoodsDetails(goodsDetailsBean);

        shouldBuyShoppingCartList.add(dataBean);

        saveMsgToGetAvticityAndCoupon(shouldBuyShoppingCartList);
    }


    //计算总价
    public void countGoodsAllPriceFromShopCart() {
        double allGoodsPrice = 0;
        for (int i = 0; i < shouldBuyShoppingCartList.size(); i++) {

            allGoodsPrice += (shouldBuyShoppingCartList.get(i).getGoodsUnitlPrice() * shouldBuyShoppingCartList.get(i).getGoodsNum());
        }

        titalPrice = Double.parseDouble(df.format(allGoodsPrice));

        if (titalPrice >= 50000) {
            tvSubmitPrompt.setVisibility(View.VISIBLE);
            tvSubmitPrompt.setText(R.string.text_submit_order_money_5);
            stvPaymentType.setSubTitle("线下支付");
            offlinePayerMsg.setVisibility(View.VISIBLE);
            payType = 1;
        } else {
            //是预售商品
            if(isPresell==1){
                tvSubmitPrompt.setVisibility(View.VISIBLE);
            }
            else{
                tvSubmitPrompt.setVisibility(View.GONE);
            }
            stvPaymentType.setSubTitle("在线支付");
            offlinePayerMsg.setVisibility(View.GONE);
            payType = 0;
        }

    }


    //通过传入商品信息获取商品所能参加的优惠活动以及该用户的优惠券信息
    public void saveMsgToGetAvticityAndCoupon(List<ShoppingCartModel.ResultDataBean> shouldBuyShoppingCartList) {
        paramForGetActivitysAndCouponsByGoodsMsg = new ParamForGetActivitysAndCouponsByGoodsMsg();
        goodsMsgListBeans = new ArrayList<>();
        goodsActivityListBeans = new ArrayList<>();
        goodsSpeActivityListBeans = new ArrayList<>();
        for (int i = 0; i < shouldBuyShoppingCartList.size(); i++) {
            goodsMsgListBean = new ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean();
            goodsSpeActivityListBeans = new ArrayList<>();
            goodsActivityListBeans = new ArrayList<>();

            if (shouldBuyShoppingCartList.get(i).getGoodsDetails() != null) {
                if (shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsActivitys().size() > 0) {
                    for (int g = 0; g < shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsActivitys().size(); g++) {
                        ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsActivitysBeanX goodsActivitysBeanX = shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsActivitys().get(g);
                        goodsActivityListBean = new ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean.GoodsActivityListBean();
                        goodsActivityListBean.setId(goodsActivitysBeanX.getActivityInformation().getId());
                        //goodsActivityListBean.setIdentifier((String)goodsActivitysBeanX.getActivityInformation().getIdentifier());
                        goodsActivityListBean.setName(goodsActivitysBeanX.getActivityInformation().getName());
                        goodsActivityListBean.setActivityType(goodsActivitysBeanX.getActivityInformation().getActivityType());
                        goodsActivityListBean.setPrice(goodsActivitysBeanX.getActivityInformation().getPrice());
                        goodsActivityListBean.setDiscount(goodsActivitysBeanX.getActivityInformation().getDiscount());
                        goodsActivityListBean.setMaxNum(goodsActivitysBeanX.getActivityInformation().getMaxNum());
                        //goodsActivityListBean.setIntroduction((String)goodsActivitysBeanX.getActivityInformation().getIntroduction());
                        //goodsActivityListBean.setMessagePicUrl((String)goodsActivitysBeanX.getActivityInformation().getMessagePicUrl());
                        //goodsActivityListBean.setShowPicUrl((String)goodsActivitysBeanX.getActivityInformation().getShowPicUrl());
                        //goodsActivityListBean.setBudget((double)goodsActivitysBeanX.getActivityInformation().getBudget());
                        goodsActivityListBean.setBeginValidityTime(goodsActivitysBeanX.getActivityInformation().getBeginValidityTime());
                        goodsActivityListBean.setEndValidityTime(goodsActivitysBeanX.getActivityInformation().getEndValidityTime());
                        goodsActivityListBean.setState(goodsActivitysBeanX.getActivityInformation().getState());
                        //goodsActivityListBean.setOperatorIdentifier((String)goodsActivitysBeanX.getActivityInformation().getOperatorIdentifier());
                        //goodsActivityListBean.setOperatorTime((long)goodsActivitysBeanX.getActivityInformation().getOperatorTime());

                        goodsActivityListBeans.add(goodsActivityListBean);
                    }


                }
                goodsMsgListBean.setGoodsActivityList(goodsActivityListBeans);
                Log.d("aaaaaaaaaaaaa", "goodsActivityListBeans.size:" + goodsActivityListBeans.size());
                if (shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size() > 0) {
                    for (int k = 0; k < shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().size(); k++) {
                        if (shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsId() == shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(k).getId()) {
                            if (shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(k).getGoodsActivitys().size() > 0) {
                                for (int g = 0; g < shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(k).getGoodsActivitys().size(); g++) {
                                    ShoppingCartModel.ResultDataBean.GoodsDetailsBean.GoodsSpecificationDetailsBean.GoodsActivitysBean goodsActivitysBean = shouldBuyShoppingCartList.get(i).getGoodsDetails().getGoodsSpecificationDetails().get(k).getGoodsActivitys().get(g);
                                    goodsSpeActivityListBean = new ParamForGetActivitysAndCouponsByGoodsMsg.GoodsMsgListBean.GoodsSpeActivityListBean();
                                    goodsSpeActivityListBean.setId(goodsActivitysBean.getActivityInformation().getId());
                                    //goodsSpeActivityListBean.setIdentifier((String)goodsActivitysBean.getActivityInformation().getIdentifier());
                                    goodsSpeActivityListBean.setName(goodsActivitysBean.getActivityInformation().getName());
                                    goodsSpeActivityListBean.setActivityType(goodsActivitysBean.getActivityInformation().getActivityType());
                                    goodsSpeActivityListBean.setPrice(goodsActivitysBean.getActivityInformation().getPrice());
                                    goodsSpeActivityListBean.setDiscount(goodsActivitysBean.getActivityInformation().getDiscount());
                                    goodsSpeActivityListBean.setMaxNum(goodsActivitysBean.getActivityInformation().getMaxNum());
                                    //goodsSpeActivityListBean.setIntroduction((String)goodsActivitysBean.getActivityInformation().getIntroduction());
                                    //goodsSpeActivityListBean.setMessagePicUrl((String)goodsActivitysBean.getActivityInformation().getMessagePicUrl());
                                    //goodsSpeActivityListBean.setShowPicUrl((String)goodsActivitysBean.getActivityInformation().getShowPicUrl());
                                    //goodsSpeActivityListBean.setBudget((double)goodsActivitysBean.getActivityInformation().getBudget());
                                    goodsSpeActivityListBean.setBeginValidityTime(goodsActivitysBean.getActivityInformation().getBeginValidityTime());
                                    goodsSpeActivityListBean.setEndValidityTime(goodsActivitysBean.getActivityInformation().getEndValidityTime());
                                    goodsSpeActivityListBean.setState(goodsActivitysBean.getActivityInformation().getState());
                                    //goodsSpeActivityListBean.setOperatorIdentifier((String)goodsActivitysBean.getActivityInformation().getOperatorIdentifier());
                                    //goodsSpeActivityListBean.setOperatorTime((long)goodsActivitysBean.getActivityInformation().getOperatorTime());

                                    goodsSpeActivityListBeans.add(goodsSpeActivityListBean);
                                }

                            }
                        }
                    }
                    goodsMsgListBean.setGoodsSpeActivityList(goodsSpeActivityListBeans);
                    Log.d("aaaaaaaaaaaaa", "goodsSpeActivityListBeans.size:" + goodsSpeActivityListBeans.size());
                }

            }
            goodsMsgListBean.setGoodsSpeId(shouldBuyShoppingCartList.get(i).getGoodsSpecificationDetailsId());
            goodsMsgListBean.setNumber(shouldBuyShoppingCartList.get(i).getGoodsNum());
            goodsMsgListBean.setUnitPrice(shouldBuyShoppingCartList.get(i).getGoodsUnitlPrice());

            goodsMsgListBeans.add(goodsMsgListBean);
        }
        paramForGetActivitysAndCouponsByGoodsMsg.setGoodsMsgList(goodsMsgListBeans);
        paramForGetActivitysAndCouponsByGoodsMsg.setUserId(userId);

        Log.d("aaaaaaaaaaaaa", "saveMsgToGetAvticityAndCoupon.userId:" + paramForGetActivitysAndCouponsByGoodsMsg.getUserId() + " msgList:" + paramForGetActivitysAndCouponsByGoodsMsg.getGoodsMsgList().size());

        getActivitysAndCouponsByGoodsMsg(paramForGetActivitysAndCouponsByGoodsMsg);
    }


    /*以下部分为接口的调用*/


    //根据商品信息列表获取活动列表和优惠券列表
    public void getActivitysAndCouponsByGoodsMsg(ParamForGetActivitysAndCouponsByGoodsMsg param) {
        Net.get().getActivitysAndCouponsByGoodsMsg(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activitysAndCouponsByGoodsMsgModel -> {
                            if (activitysAndCouponsByGoodsMsgModel.getCode() == 200) {
                                Log.d("aaaaaaOrderSubmit", "shouldShowCouponsBeanList.size:" + shouldShowCouponsBeanList.size());
                                if (activitysAndCouponsByGoodsMsgModel.getResultData().getCoupons().size() > 0) {
                                    shouldShowCouponsBeanList = activitysAndCouponsByGoodsMsgModel.getResultData().getCoupons();

                                    llStvCoupon.setVisibility(View.VISIBLE);
                                    stvCoupon.setSubTitle("请选择");

                                } else {
                                    Log.d("aaaaaaOrderSubmit", "暂无优惠券");
                                    llStvCoupon.setVisibility(View.GONE);
                                    stvCoupon.setSubTitle("暂无优惠券");
                                }
                                Log.d("aaaaaaOrderSubmit", "activitysBeanList.size:" + activitysBeanList.size());
                                if (activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys().size() > 0) {
                                    //预售活动
                                    if(isPresell==1){
                                        activitysBeanList = activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys();
                                        for(int i=0;i<activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys().size();i++){
                                            if(activityId==activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys().get(i).getActivityInformation().getId()){
                                                stvActivity.setSubTitle("已参与预售活动："+activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys().get(i).getActivityInformation().getName());

                                                stvActivity.setCompoundDrawables(null,null,null,null);

                                                llStvActivity.setVisibility(View.VISIBLE);

                                                tvSubmitPrompt.setVisibility(View.VISIBLE);
                                                tvSubmitPrompt.setText(R.string.text_submit_order_presell);
                                                Log.d("tvSubmitPrompttext",tvSubmitPrompt.getText().toString());
                                                presellEndTime=new SimpleDateFormat("yyyy-MM-dd").format(activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys().get(i).getActivityInformation().getEndValidityTime()) ;
                                                presellActivityId=activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys().get(i).getActivityInformation().getId();
                                            }
                                        }
                                    }
                                    else{
                                        activitysBeanList = activitysAndCouponsByGoodsMsgModel.getResultData().getActivitys();
                                        Drawable drawable =getResources().getDrawable(R.drawable.icon_checkaddress);
                                        stvActivity.setCompoundDrawables(null,null,drawable,null);
                                        stvActivity.setCompoundDrawablePadding(24);
                                        stvActivity.setSubTitle("请选择");
                                        llStvActivity.setVisibility(View.VISIBLE);
                                        if(titalPrice>=50000){
                                            tvSubmitPrompt.setVisibility(View.VISIBLE);
                                        }
                                        else{
                                            tvSubmitPrompt.setVisibility(View.GONE);
                                        }

                                    }


                                } else {
                                    Log.d("aaaaaaOrderSubmit", "暂无活动");
                                    stvActivity.setSubTitle("暂无活动");
                                    llStvActivity.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(this, activitysAndCouponsByGoodsMsgModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        },
                        throwable -> {
                            Toast.makeText(this, "coupon" + AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                        });
    }

    //生成订单时获取邮费
    public void getPostage(String provinceCode, String cityCode, String countyCode, String ringCode, double orderMoney) {
        Net.get().getPostage(provinceCode, cityCode, countyCode, ringCode, orderMoney)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postageModel -> {
                    Log.d("aaaaaaaaaaaaaaaaaa","getPostage");
                    if (postageModel.getCode() == 200) {
                        if (postageModel.getResultData() == 0) {
                            tvFreightUnit.setVisibility(View.GONE);
                            tvFreightWorld.setText("包邮");
                            tvFreight.setVisibility(View.GONE);
                            postage = 0;
                            postagePayType = 0;
                        } else {
                            tvFreightUnit.setVisibility(View.VISIBLE);
                            tvFreight.setText(postageModel.getResultData() + "");
                            tvFreight.setVisibility(View.VISIBLE);
                            postage = postageModel.getResultData();
                            postagePayType = 0;
                        }

                        if(afterActivityTotalMoney>0){
                            if((afterActivityTotalMoney-userCouponPrice + postage)>1){
                                tvTotal.setText(df.format(afterActivityTotalMoney-userCouponPrice + postage) + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(afterActivityTotalMoney-userCouponPrice + postage)) + "");
                            }

                        }
                        else{
                            if((titalPrice-userCouponPrice + postage)>1){
                                tvTotal.setText(df.format(titalPrice-userCouponPrice + postage) + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(titalPrice-userCouponPrice + postage)) + "");
                            }

                        }


                    } else if (postageModel.getCode() == 13001) {
                        tvFreightUnit.setVisibility(View.GONE);
                        tvFreightWorld.setText("邮费到付");
                        tvFreight.setVisibility(View.GONE);
                        postage = 0;
                        postagePayType = 1;

                        if(afterActivityTotalMoney>0){
                            if((afterActivityTotalMoney-userCouponPrice + postage)>1){
                                tvTotal.setText(df.format(afterActivityTotalMoney-userCouponPrice + postage) + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(afterActivityTotalMoney-userCouponPrice + postage)) + "");
                            }

                        }
                        else{
                            if((titalPrice-userCouponPrice + postage)>1){
                                tvTotal.setText(df.format(titalPrice-userCouponPrice + postage) + "");
                            }else{
                                tvTotal.setText(Double.parseDouble(df.format(titalPrice-userCouponPrice + postage)) + "");
                            }

                        }

                    } else {
                        Toast.makeText(this, postageModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, "postage" + AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //获取购物车中商品信息
    public void getShoppingCartByUserId(Integer userId) {
        Net.get().getShoppingCart(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shopingCartModel -> {
                    cancelProgressDialog();
                    if (shopingCartModel.getCode() == 200) {
                        shoppingCartList = shopingCartModel.getResultData();
                        getShouldBuyGoodsFromShopCart();
                        Log.d("aaaaaaaaa", "shouldBuyShoppingCartList.size()" + shouldBuyShoppingCartList.size());
                        Log.d("aaaaaaaaa", "buyGoodsMsg.length" + buyGoodsMsg.length);

                        if (shouldBuyShoppingCartList.size() > 0 && shouldBuyShoppingCartList.size() == buyGoodsMsg.length) {
                            setGoodsMsgAdapter();
                            //计算总价，并给支付方式处赋值
                            countGoodsAllPriceFromShopCart();
                            //获取用户收货人地址信息 并获取邮费 给底部总价赋值
                            getShippingAddressByUserId();
                        } else {
                            Toast.makeText(this, "抱歉，数据出错啦!", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(this, shopingCartModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, "shopping" + AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


    //获取商品详情信息
    public void getGoodsDetailMsgByGoodsId(int goodsId) {

        Net.get().getGoodsDetailMsgByGoodsId(goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodsDetailModel -> {
                    cancelProgressDialog();
                    Log.d("aaaaaaaaa", "goodsDetailModelSize:" + goodsDetailModel.getResultData().getGoodsSpecificationDetails().size());
                    if (goodsDetailModel.getCode() == 200) {
                        goodsDetailDataBean = goodsDetailModel.getResultData();
                        if (goodsDetailDataBean != null) {
                            getShouldBuyGoodsFromGoodsDetailPage(goodsDetailDataBean);
                            setGoodsMsgAdapter();
                            //计算总价
                            countGoodsAllPriceFromShopCart();
                            //获取用户收货人地址信息 并获取邮费 给底部总价赋值
                            getShippingAddressByUserId();
                        } else {
                            Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, goodsDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, "11" + AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });

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
                            noAddressMsg.setVisibility(View.GONE);
                            shippingPeopleMessage.setVisibility(View.VISIBLE);
                            tvConsigneeAddress.setVisibility(View.VISIBLE);
                            chooseMoreAddress.setVisibility(View.VISIBLE);

                            Log.d("aaaaaaaGoods", "consigneeAddress:" + consigneeAddress);
                            Log.d("aaaaaaaGoods", "http provinceCode:" + provinceCode);
                            if (!"".equals(consigneeAddress) && !"".equals(consigneeName) && !"".equals(consigneeTel)) {
                                tvConsigneeAddress.setText(consigneeAddress);
                                tvConsigneeName.setText(consigneeName);
                                tvConsigneePhone.setText(consigneeTel);
                                if (consigneeIsVip == 1) {
                                    tvDefault.setVisibility(View.VISIBLE);
                                } else {
                                    tvDefault.setVisibility(View.GONE);
                                }
                            } else {
                                int i = 0;
                                for (i = 0; i < shipAddressResultDataBeans.size(); i++) {
                                    if (shipAddressResultDataBeans.get(i).getIsCommonlyUsed() == 1) {
                                        tvConsigneeAddress.setText(shipAddressResultDataBeans.get(i).getRegion() + shipAddressResultDataBeans.get(i).getDetailedAddress());
                                        tvConsigneeName.setText(shipAddressResultDataBeans.get(i).getConsigneeName());
                                        tvConsigneePhone.setText(shipAddressResultDataBeans.get(i).getConsigneeTel());
                                        tvDefault.setVisibility(View.VISIBLE);

                                        provinceCode = shipAddressResultDataBeans.get(0).getProvinceCode();
                                        cityCode = shipAddressResultDataBeans.get(0).getCityCode();
                                        countyCode = shipAddressResultDataBeans.get(0).getCountyCode();
                                        ringCode = shipAddressResultDataBeans.get(0).getRingCode();
                                        if (cityCode == null || "".equals(cityCode)) {
                                            cityCode = "0";
                                        }
                                        getPostage(provinceCode, cityCode, countyCode, ringCode, titalPrice);

                                        break;
                                    }
                                }
                                if (i == shipAddressResultDataBeans.size()) {
                                    tvConsigneeAddress.setText(shipAddressResultDataBeans.get(0).getRegion() + shipAddressResultDataBeans.get(0).getDetailedAddress());
                                    tvConsigneeName.setText(shipAddressResultDataBeans.get(0).getConsigneeName());
                                    tvConsigneePhone.setText(shipAddressResultDataBeans.get(0).getConsigneeTel());
                                    tvDefault.setVisibility(View.GONE);


                                    provinceCode = shipAddressResultDataBeans.get(0).getProvinceCode();
                                    cityCode = shipAddressResultDataBeans.get(0).getCityCode();
                                    countyCode = shipAddressResultDataBeans.get(0).getCountyCode();
                                    ringCode = shipAddressResultDataBeans.get(0).getRingCode();
                                    if (cityCode == null || "".equals(cityCode)) {
                                        cityCode = "0";
                                    }
                                    getPostage(provinceCode, cityCode, countyCode, ringCode, titalPrice);


                                }
                            }

                        } else {
                            noAddressMsg.setVisibility(View.VISIBLE);
                            shippingPeopleMessage.setVisibility(View.GONE);
                            tvConsigneeAddress.setVisibility(View.GONE);
                            chooseMoreAddress.setVisibility(View.GONE);
                            Tools.addActivity(this);
                            Toast.makeText(OrderSubmitActivity.this, "暂无收货地址，请先添加。", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(OrderSubmitActivity.this, MyAddressActivity.class);
                            startActivity(intent1);
                        }
                    } else {
                        Toast.makeText(this, shipAddressModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }


                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //提交订单前判断商品是否有库存
    public void decisionInventory(OrderTable addOrderModel) {
        //设置提交按钮不能点击
        btnSubmit.setEnabled(false);
        btnSubmit.setBackgroundResource(R.color.moreText);
        Net.get().decisionInventory(addOrderModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(decisionInventoryResultModel -> {
                    if (decisionInventoryResultModel.getCode() == 200) {
                        noStockGoodsSpeIdList = new ArrayList<>();
                        insufficientInventoryGoodsSpeIdList = new ArrayList<>();
                        Map<String, Object> map;
                        for (int i = 0; i < decisionInventoryResultModel.getResultData().getGoodsMsg().size(); i++) {
                            DecisionInventoryResultModel.ResultDataBean.GoodsMsgBean goodsMsgBean = decisionInventoryResultModel.getResultData().getGoodsMsg().get(i);
                            //无库存
                            if (goodsMsgBean.getCode() == 80003) {
                                noStockGoodsSpeIdList.add(goodsMsgBean.getGoodsSpecificationDetailsId());
                            }
                            //库存不足
                            else if (goodsMsgBean.getCode() == 80004) {
                                map = new HashMap<>();
                                map.put("goodsSpecId", goodsMsgBean.getGoodsSpecificationDetailsId());
                                map.put("stock", goodsMsgBean.getStock());
                                insufficientInventoryGoodsSpeIdList.add(map);
                            }
                        }
                        if (decisionInventoryResultModel.getResultData().getCouponMsg() != null) {
                            //优惠券不能使用
                            if (decisionInventoryResultModel.getResultData().getCouponMsg().getCode() == 30002) {
                                isOkUseCoupon = false;
                            }
                        }
                        if (noStockGoodsSpeIdList.size() == decisionInventoryResultModel.getResultData().getGoodsMsg().size()) {
                            cancelProgressDialog();
                            orderPromptPopu = new OrderPromptPopu(this, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    btnSubmit.setEnabled(true);
                                    btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                                    Intent intent = new Intent(OrderSubmitActivity.this, BaseActivity.class);
                                    startActivity(intent);
                                }
                            }, 1, 0);
                            orderPromptPopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);
                        } else if (noStockGoodsSpeIdList.size() > 0 && noStockGoodsSpeIdList.size() != decisionInventoryResultModel.getResultData().getGoodsMsg().size() && insufficientInventoryGoodsSpeIdList.size() == 0) {
                            cancelProgressDialog();
                            orderPromptPopu = new OrderPromptPopu(this, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    buildProgressDialog();
                                    insertOrder(noGoodsStockOrInsufficientInventory(addOrderModel));
                                    orderPromptPopu.dismiss();
                                }
                            }, 2, 0);
                            orderPromptPopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);

                        } else if (noStockGoodsSpeIdList.size() > 0 && insufficientInventoryGoodsSpeIdList.size() > 0) {
                            cancelProgressDialog();
                            orderPromptPopu = new OrderPromptPopu(this, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    buildProgressDialog();
                                    insertOrder(noGoodsStockOrInsufficientInventory(addOrderModel));
                                    orderPromptPopu.dismiss();
                                }
                            }, 3, 0);
                            orderPromptPopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);

                        } else if (noStockGoodsSpeIdList.size() == 0 && insufficientInventoryGoodsSpeIdList.size() > 0) {
                            if (decisionInventoryResultModel.getResultData().getGoodsMsg().size() == 1) {
                                cancelProgressDialog();
                                orderPromptPopu = new OrderPromptPopu(this, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        buildProgressDialog();
                                        insertOrder(noGoodsStockOrInsufficientInventory(addOrderModel));
                                        orderPromptPopu.dismiss();
                                    }
                                }, 4, decisionInventoryResultModel.getResultData().getGoodsMsg().get(0).getStock());
                                orderPromptPopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);

                            } else {
                                cancelProgressDialog();
                                orderPromptPopu = new OrderPromptPopu(this, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        buildProgressDialog();
                                        insertOrder(noGoodsStockOrInsufficientInventory(addOrderModel));
                                        orderPromptPopu.dismiss();
                                    }
                                }, 5, 0);
                                orderPromptPopu.showAtLocation(findViewById(R.id.rl_submit), Gravity.CENTER, 0, 0);
                            }


                        } else if (noStockGoodsSpeIdList.size() == 0 && insufficientInventoryGoodsSpeIdList.size() == 0) {
                            //提交订单
                            insertOrder(addOrderModel);
                        }

                    } else {
                        btnSubmit.setEnabled(true);
                        btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                        cancelProgressDialog();
                        Toast.makeText(this, decisionInventoryResultModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    btnSubmit.setEnabled(true);
                    btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //提交订单
    public void insertOrder(OrderTable addOrderModel) {

        Net.get().insertOrder(addOrderModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertOrderResultModel -> {
                    cancelProgressDialog();
                    if (insertOrderResultModel.getCode() == 200) {
                        /*Toast.makeText(this, "提交成功!", Toast.LENGTH_SHORT).show();*/

                        if (addOrderModel.getPayType() == 0) {
                            Intent intent = new Intent(this, PayOrderActivity.class);
                            intent.putExtra("payMoney", addOrderModel.getOrderPresentPrice() + addOrderModel.getPostage());
                            Log.d("aaaaaorder", "insertOrderResultModel.getResultData().size:" + insertOrderResultModel.getResultData().size() + " 1:" + insertOrderResultModel.getResultData().get(0) + " 2:" + insertOrderResultModel.getResultData().get(1));
                            if (insertOrderResultModel.getResultData().size() == 2) {
                                if (insertOrderResultModel.getResultData().get(0) != null && !"".equals(insertOrderResultModel.getResultData().get(0))) {
                                    intent.putExtra("orderId", Integer.parseInt(insertOrderResultModel.getResultData().get(0)));
                                    intent.putExtra("postagePayType", addOrderModel.getPostagePayType());

                                    if (insertOrderResultModel.getResultData().get(1) != null && !"".equals(insertOrderResultModel.getResultData().get(1))) {
                                        intent.putExtra("orderNo", insertOrderResultModel.getResultData().get(1));
                                        startActivity(intent);
                                    } else {
                                        btnSubmit.setEnabled(true);
                                        btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                                        Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    btnSubmit.setEnabled(true);
                                    btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                                    Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                btnSubmit.setEnabled(true);
                                btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                                Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(this, UnderlineActivity.class);
                            intent.putExtra("payMoney", addOrderModel.getOrderPresentPrice() + addOrderModel.getPostage());
                            Log.d("aaaaaorder", "insertOrderResultModel.getResultData().size:" + insertOrderResultModel.getResultData().size() + " 1:" + insertOrderResultModel.getResultData().get(0) + " 2:" + insertOrderResultModel.getResultData().get(1));
                            if (insertOrderResultModel.getResultData().size() == 2) {
                                if (insertOrderResultModel.getResultData().get(0) != null && !"".equals(insertOrderResultModel.getResultData().get(0))) {
                                    intent.putExtra("orderId", Integer.parseInt(insertOrderResultModel.getResultData().get(0)));
                                    intent.putExtra("postagePayType", addOrderModel.getPostagePayType());

                                    if (insertOrderResultModel.getResultData().get(1) != null && !"".equals(insertOrderResultModel.getResultData().get(1))) {
                                        intent.putExtra("orderNo", insertOrderResultModel.getResultData().get(1));
                                        startActivity(intent);
                                    } else {
                                        btnSubmit.setEnabled(true);
                                        btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                                        Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    btnSubmit.setEnabled(true);
                                    btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                                    Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                btnSubmit.setEnabled(true);
                                btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                                Toast.makeText(this, "数据错误", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } else {
                        btnSubmit.setEnabled(true);
                        btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                        Toast.makeText(this, insertOrderResultModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    btnSubmit.setEnabled(true);
                    btnSubmit.setBackgroundResource(R.color.checkGreenColor);
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //提交订单之前判断需要提交的商品的库存情况---供 提交订单前判断商品是否有库存  该接口使用
    private OrderTable noGoodsStockOrInsufficientInventory(OrderTable addOrderModel) {
        for (int i = 0; i < addOrderModel.getOrderDetails().size(); i++) {
            //无货
            for (int j = 0; j < noStockGoodsSpeIdList.size(); j++) {
                if (noStockGoodsSpeIdList.get(j) == addOrderModel.getOrderDetails().get(i).getGoodsSpecificationDetailsId()) {
                    addOrderModel.getOrderDetails().remove(i);
                    i--;
                    break;
                }
            }
            //库存不足
            for (int j = 0; j < insufficientInventoryGoodsSpeIdList.size(); j++) {
                //库存不足
                if ((int) insufficientInventoryGoodsSpeIdList.get(j).get("goodsSpecId") == addOrderModel.getOrderDetails().get(i).getGoodsSpecificationDetailsId()) {
                    int stock = (int) insufficientInventoryGoodsSpeIdList.get(j).get("stock");
                    //是否参与了活动
                    for (int k = 0; k < goodsSpeIdList.size(); k++) {
                        if (goodsSpeIdList.get(k) == (int) insufficientInventoryGoodsSpeIdList.get(j).get("goodsSpecId")) {

                            double goodsPrice = addOrderModel.getOrderDetails().get(i).getGoodsOriginalPrice();//商品的单个原价
                            //0:折扣,1:团购，2:秒杀，3:立减,4:满减
                            switch (activityType) {
                                case 0:
                                    if (maxNum >= stock) {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(goodsPrice * stock * discount)));
                                    } else {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(goodsPrice * maxNum * discount + goodsPrice * (stock - maxNum))));
                                    }
                                    break;
                                case 1:
                                    if (maxNum >= stock) {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(stock * discount)));
                                    } else {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(maxNum * discount + goodsPrice * (stock - maxNum))));
                                    }
                                    break;
                                case 2:
                                    if (maxNum >= stock) {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(stock * discount)));
                                    } else {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(maxNum * discount + goodsPrice * (stock - maxNum))));
                                    }
                                    break;
                                case 3:
                                    addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(stock * goodsPrice - discount)));
                                    break;
                                case 4:
                                    //判断门槛金额是否满足
                                    if (stock * goodsPrice >= thresholdPrice) {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(stock * goodsPrice - discount)));
                                    } else {
                                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(stock * goodsPrice)));
                                    }
                                    break;
                            }
                        }
                    }
                    addOrderModel.getOrderDetails().get(i).setGoodsQuantity(stock);
                    if (goodsSpeIdList.size() == 0) {

                        addOrderModel.getOrderDetails().get(i).setGoodsPaymentPrice(Double.parseDouble(df.format(stock * addOrderModel.getOrderDetails().get(i).getGoodsOriginalPrice())));
                    }

                }
            }
        }
        double orderOriginalPrice = 0;//订单原总价
        double orderPresentPrice = 0;//订单参与活动与使用优惠券后的价格
        for (int i = 0; i < addOrderModel.getOrderDetails().size(); i++) {
            orderOriginalPrice += addOrderModel.getOrderDetails().get(i).getGoodsOriginalPrice() * addOrderModel.getOrderDetails().get(i).getGoodsQuantity();
            orderPresentPrice += addOrderModel.getOrderDetails().get(i).getGoodsPaymentPrice();
        }
        //重新查询邮费
        getPostage(provinceCode, cityCode, countyCode, ringCode, orderOriginalPrice);

        addOrderModel.setOrderOriginalPrice(Double.parseDouble(df.format(orderOriginalPrice)));
        addOrderModel.setOrderPresentPrice(Double.parseDouble(df.format(orderPresentPrice)));//未使用优惠券的价格
        //支付方式
        if (orderPresentPrice >= 50000) {
            payType = 1;
            offlinePayerMsg.setVisibility(View.VISIBLE);
        } else {
            payType = 0;
            offlinePayerMsg.setVisibility(View.GONE);
        }
        addOrderModel.setPayType(payType);
        addOrderModel.setPostage(postage);
        addOrderModel.setPostagePayType(postagePayType);

        //判断是否使用了优惠券
        //选择了优惠券
        if (userCouponId != 0) {
            //从后台中获取到该优惠券能否使用
            //优惠券可以使用
            if (isOkUseCoupon) {
                //判断现在商品的总价是否满足优惠券的使用门槛
                //满足了
                double useLimit = 0;//使用门槛
                for (int c = 0; c < shouldShowCouponsBeanList.size(); c++) {
                    if (shouldShowCouponsBeanList.get(c).getUserCoupons().getId() == userCouponId) {
                        useLimit = shouldShowCouponsBeanList.get(c).getUserCoupons().getCouponInformation().getUseLimit();
                    }
                }
                if (useLimit <= orderPresentPrice) {
                    addOrderModel.setOrderPresentPrice(Double.parseDouble(df.format(addOrderModel.getOrderPresentPrice() - userCouponPrice)));
                } else {
                    addOrderModel.setIsUseCoupon(0);//不使用优惠券
                }

            }
            //优惠券不能使用
            else {
                addOrderModel.setIsUseCoupon(0);//不使用优惠券
            }
        }
        return addOrderModel;
    }


}
