package com.jl.jlapp.mvp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jl.jlapp.R;
import com.jl.jlapp.adapter.DiscountAdapter;
import com.jl.jlapp.adapter.GeneralInvoiceListAdapter;
import com.jl.jlapp.eneity.GeneralInvoiceModel;
import com.jl.jlapp.eneity.VatInvoiceAptitudeByUserIdModel;
import com.jl.jlapp.mvp.activity.AddGeneralInvoiceActivity;
import com.jl.jlapp.mvp.activity.AddVatInvoiceActivity;
import com.jl.jlapp.mvp.activity.AfterSaleDetailActivity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.mvp.activity.MyAddressActivity;
import com.jl.jlapp.mvp.activity.OrderSubmitActivity;
import com.jl.jlapp.mvp.activity.PhotoViewActivity;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-02-26.
 */

public class InvoiceFragment extends LazyLoadFragment implements View.OnClickListener {
    @BindView(R.id.lrv_list)
    RecyclerView lrvList;
    @BindView(R.id.no_invoice_show)
    RelativeLayout noInvoiceShow;
    @BindView(R.id.add_text)
    TextView addText;
    @BindView(R.id.vat_invoice_content)
    LinearLayout vatInvoiceContent;
    @BindView(R.id.add_general_invoice_linear)
    LinearLayout addGeneralInvoiceLinear;

    @BindView(R.id.state)
    TextView tvState;
    @BindView(R.id.unitName)
    TextView unitName;
    @BindView(R.id.taxpayerIdentificationNumber)
    TextView taxpayerIdentificationNumber;
    @BindView(R.id.registeredAddress)
    TextView registeredAddress;
    @BindView(R.id.registeredTel)
    TextView registeredTel;
    @BindView(R.id.depositBank)
    TextView depositBank;
    @BindView(R.id.bankAccount)
    TextView bankAccount;

    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.vat_pic)
    ImageView vatPic;


    String picUrl;


    int vatInvoiceId = 0;


    public static final int TYPE_VATINVOICE = 1;
    public static final int TYPE_UNITINVOICE = 2;
    private static final String EXTRA_TYPE = "extra_type";
    int type = TYPE_VATINVOICE;
    Unbinder unbinder;
    int userId=0;

    VatInvoiceAptitudeByUserIdModel.ResultDataBean vatResultDataBean = null;
    List<GeneralInvoiceModel.ResultDataBean> generalInvoiceResultDataBeans=null;
    GeneralInvoiceListAdapter generalInvoiceListAdapter;
    ClearHistorySearhPopu clearHistorySearhPopu = null;


    public static InvoiceFragment newInstance(int type,int userId) {
        InvoiceFragment fragment = new InvoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        bundle.putInt("userId",userId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_invoice;
    }

    @Override
    public void initViews(View view) {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(EXTRA_TYPE)) {
            throw new IllegalArgumentException("NoticeListFragment must be created by type args");
        }
        type = args.getInt(EXTRA_TYPE, TYPE_VATINVOICE);
        userId=args.getInt("userId",0);


        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void loadData() {
        if(userId>0){
            getVatInvoiceAptitudeByUserId(userId);
//            getCouponInfoByUserId(userId);
//            usableCouponsBeanList = OrderSubmitActivity.shouldShowCouponsBeanList;
        }
        else{
            Toast.makeText(getContext(),"您还未登录，请先登录。",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(),LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initDatas(int type) {
        switch (type) {
            case TYPE_VATINVOICE:
                addText.setText("添加增票资质");

                if(vatResultDataBean == null){//无增票
                    lrvList.setVisibility(View.GONE);
                    addGeneralInvoiceLinear.setVisibility(View.GONE);
                    noInvoiceShow.setVisibility(View.VISIBLE);
                    vatInvoiceContent.setVisibility(View.GONE);
                    addText.setOnClickListener(this);
                }else{//有增票
                    lrvList.setVisibility(View.GONE);
                    addGeneralInvoiceLinear.setVisibility(View.GONE);
                    noInvoiceShow.setVisibility(View.GONE);
                    vatInvoiceContent.setVisibility(View.VISIBLE);

                    tvUpdate.setOnClickListener(this);
                    tvDelete.setOnClickListener(this);
                    vatPic.setOnClickListener(this);

                    int state = vatResultDataBean.getState();
                    Drawable drawableLeft;
                    switch (state){
                        case 0:
                            tvState.setText("增票资质已提交审核");
                            tvState.setBackgroundResource(R.color.state_wait_check);
                            drawableLeft = getResources().getDrawable(
                                    R.drawable.checkmark_going);
                            tvState.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                            break;
                        case 1:
                            tvState.setText("您填写的增票资质未通过审核，请重新填写");
                            tvState.setBackgroundResource(R.color.state_dis_pass);
                            drawableLeft = getResources().getDrawable(
                                    R.drawable.checkmark_wrong);
                            tvState.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                            break;
                        case 2:
                            tvState.setText("已通过审核");
                            tvState.setBackgroundResource(R.color.state_pass);
                            drawableLeft = getResources().getDrawable(
                                    R.drawable.checkmark_green);
                            tvState.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                            break;
                    }

                    vatInvoiceId = vatResultDataBean.getId();
                    unitName.setText(vatResultDataBean.getUnitName());
                    taxpayerIdentificationNumber.setText(vatResultDataBean.getTaxpayerIdentificationNumber());
                    registeredAddress.setText(vatResultDataBean.getRegisteredAddress());
                    registeredTel.setText(vatResultDataBean.getRegisteredTel());
                    depositBank.setText(vatResultDataBean.getDepositBank());
                    bankAccount.setText(vatResultDataBean.getBankAccount());
                    picUrl = vatResultDataBean.getBusinessLicenseUrl();
                    //如果无图片
                    if(picUrl==null || picUrl == ""){

                        Glide
                                .with(getActivity())
                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_noimg_m).error(R.drawable.img_noimg_m))
                                .load(R.drawable.img_noimg_m)
                                .into(vatPic);
                    }
                    //有图片
                    else{
                        Glide
                                .with(getActivity())
                                .asBitmap().apply(new RequestOptions().placeholder(R.drawable.img_lose_m).error(R.drawable.img_lose_m))
                                .load(AppFinal.BASEURL + picUrl)
                                .into(vatPic);
                    }

                }
                break;
            case TYPE_UNITINVOICE:
                addGeneralInvoiceLinear.setOnClickListener(this);
                getInvoiceUnitByUserId(userId);
                //addText.setText("添加普票单位");
               /* if(true){//无普票
                    lrvList.setVisibility(View.GONE);
                    noInvoiceShow.setVisibility(View.VISIBLE);
                    vatInvoiceContent.setVisibility(View.GONE);
                    addText.setOnClickListener(this);
                }else{//有普票
                    lrvList.setVisibility(View.VISIBLE);
                    noInvoiceShow.setVisibility(View.GONE);
                    vatInvoiceContent.setVisibility(View.GONE);

                }*/
                break;
        }

    }

    /**
     * 给普票列表设置适配器
     */
    private void setGeneralInvoiceListAdapter(){
        lrvList.setLayoutManager(new LinearLayoutManager(getContext()));
        generalInvoiceListAdapter=new GeneralInvoiceListAdapter(R.layout.general_invoice_list_item_layout,generalInvoiceResultDataBeans);
        lrvList.setAdapter(generalInvoiceListAdapter);

        generalInvoiceListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.general_invoice_edit_linear:
                        Intent intent=new Intent(getContext(),AddGeneralInvoiceActivity.class);
                        intent.putExtra("whichButton",1);//1表示编辑，0表示添加
                        intent.putExtra("unitId",generalInvoiceResultDataBeans.get(position).getId());
                        intent.putExtra("unitName",generalInvoiceResultDataBeans.get(position).getUnitName());
                        intent.putExtra("taxpayerIdentificationNumber",generalInvoiceResultDataBeans.get(position).getTaxpayerIdentificationNumber());
                        startActivity(intent);
                        break;
                    case R.id.general_invoice_del_linear:
                        clearHistorySearhPopu = new ClearHistorySearhPopu(getContext(), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                deleteInvoiceUnitById(generalInvoiceResultDataBeans.get(position).getId(), userId);
                            }
                        }, 8);
                        clearHistorySearhPopu.showAtLocation(getActivity().findViewById(R.id.invoice_page), Gravity.CENTER, 0, 0);
                        break;
                }
            }
        });
    }

    public void getVatInvoiceAptitudeByUserId(int userId) {
        Net.get().getVatInvoiceAptitudeByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(vatInvoiceAptitudeByUserIdModel -> {
                    if (vatInvoiceAptitudeByUserIdModel.getCode() == 200) {
                        vatResultDataBean = vatInvoiceAptitudeByUserIdModel.getResultData();
                        initDatas(type);
                    } else {
                        Toast.makeText(getContext(), vatInvoiceAptitudeByUserIdModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    public void getInvoiceUnitByUserId(int userId) {
        Net.get().getInvoiceUnitByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(generalInvoiceModel -> {
                    if (generalInvoiceModel.getCode() == 200) {
                        generalInvoiceResultDataBeans = generalInvoiceModel.getResultData();
                        //有普票
                        if (generalInvoiceResultDataBeans.size()>0){
                            lrvList.setVisibility(View.VISIBLE);
                            addGeneralInvoiceLinear.setVisibility(View.VISIBLE);
                            noInvoiceShow.setVisibility(View.GONE);
                            vatInvoiceContent.setVisibility(View.GONE);
                            setGeneralInvoiceListAdapter();
                        }
                        //无普票
                        else{
                            addText.setText("添加普票单位");
                            lrvList.setVisibility(View.GONE);
                            addGeneralInvoiceLinear.setVisibility(View.GONE);
                            noInvoiceShow.setVisibility(View.VISIBLE);
                            vatInvoiceContent.setVisibility(View.GONE);
                            addText.setOnClickListener(this);
                        }
                    } else {
                        Toast.makeText(getContext(), generalInvoiceModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    //根据用户id和发票抬头单位信息id修改发票抬头单位（普票）信息
    public void deleteInvoiceUnitById(int id, int userId) {
        Net.get().deleteInvoiceUnitById(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if (postModel.getCode() == 200) {
                        Toast.makeText(getContext(), "删除成功!", Toast.LENGTH_SHORT).show();
                        getInvoiceUnitByUserId(userId);
                    } else {
                        Toast.makeText(getContext(), postModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
    //根据用户id和增票资质id删除增票资质
    public void deleteVatInvoiceAptitudeById(int id, int userId) {
        Net.get().deleteVatInvoiceAptitudeById(id, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commitVatInvoiceAptitudeToCheckModel -> {
                    if (commitVatInvoiceAptitudeToCheckModel.getCode() == 200) {
                        Toast.makeText(getContext(), "删除成功!", Toast.LENGTH_SHORT).show();
//                        getInvoiceUnitByUserId(userId);
                        getVatInvoiceAptitudeByUserId(userId);
                    } else {
                        Toast.makeText(getContext(), commitVatInvoiceAptitudeToCheckModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(getContext(), AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_text:
                if(type == TYPE_VATINVOICE){
                    Tools.addActivity(getActivity());
                    intent = new Intent(getActivity(), AddVatInvoiceActivity.class);
                    intent.putExtra("isUpdate",0);//添加增票
                    startActivity(intent);
                    break;
                }else if(type == TYPE_UNITINVOICE){
                    intent = new Intent(getActivity(), AddGeneralInvoiceActivity.class);
                    intent.putExtra("whichButton",0);//1表示编辑，0表示添加
                    startActivity(intent);
                    break;
                }
            case R.id.add_general_invoice_linear:
                intent = new Intent(getActivity(), AddGeneralInvoiceActivity.class);
                intent.putExtra("whichButton",0);//1表示编辑，0表示添加
                startActivity(intent);
                break;
            case R.id.tv_update://增票修改
                Tools.addActivity(getActivity());
                intent = new Intent(getActivity(), AddVatInvoiceActivity.class);
                intent.putExtra("isUpdate",1);//修改增票
                String unitNameStr = unitName.getText().toString().trim();
                String taxpayerIdentificationNumberStr = taxpayerIdentificationNumber.getText().toString().trim();
                String registeredAddressStr = registeredAddress.getText().toString().toString().trim();
                String registeredTelStr = registeredTel.getText().toString().trim();
                String depositBankStr = depositBank.getText().toString().trim();
                String bankAccountStr = bankAccount.getText().toString().toString().trim();
                intent.putExtra("id",vatInvoiceId);
                intent.putExtra("unitName",unitNameStr);
                intent.putExtra("taxpayerIdentificationNumber",taxpayerIdentificationNumberStr);
                intent.putExtra("registeredAddress",registeredAddressStr);
                intent.putExtra("registeredTel",registeredTelStr);
                intent.putExtra("depositBank",depositBankStr);
                intent.putExtra("bankAccount",bankAccountStr);
                startActivity(intent);
                break;
            case R.id.tv_delete://增票删除
                clearHistorySearhPopu = new ClearHistorySearhPopu(getContext(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearHistorySearhPopu.dismiss();
                        deleteVatInvoiceAptitudeById(vatInvoiceId,userId);
                    }
                }, 10);
                clearHistorySearhPopu.showAtLocation(getActivity().findViewById(R.id.invoice_page), Gravity.CENTER, 0, 0);
                break;

            case R.id.vat_pic://增票资质图片点击事件

                intent = new Intent(getActivity(), PhotoViewActivity.class);
                intent.putExtra("picUrl",picUrl);
                startActivity(intent);

                break;

        }
    }
}
