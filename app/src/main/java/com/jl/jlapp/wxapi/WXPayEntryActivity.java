package com.jl.jlapp.wxapi;
import com.jl.jlapp.R;
import com.jl.jlapp.mvp.activity.BaseActivity;
import com.jl.jlapp.mvp.activity.OrderDetailActivity;
import com.jl.jlapp.mvp.activity.PayResultActivity;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.Config;
import com.jl.jlapp.utils.Tools;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	@BindView(R.id.tv_succsess_num)
	TextView tvSuccessNum;
	@BindView(R.id.rb_left)
	RadioButton returnHomePage;
	@BindView(R.id.rb_right)
	RadioButton seeOrder;
	@BindView(R.id.tv_result)
	TextView tvResult;
	
    private IWXAPI api;

    public static Integer orderId=0;
	public static String orderNo="";
	public static Integer resultCode=1;


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
		ButterKnife.bind(this);
		//通过WXAPIFactory工厂获取IWXApI的示例
    	api = WXAPIFactory.createWXAPI(this, Config.APP_ID_WX);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("aaaWXPayEntryActivity", "onPayFinish, errCode = " + resp.errCode+" orderId:"+orderId);
		int code=resp.errCode;
		//支付成功
		if(code==0){
			Tools.finishAll();
			resultCode=0;
			tvSuccessNum.setText("您的订单(订单号:"+orderNo+")已支付成功并提交仓库备货");
			returnHomePage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(WXPayEntryActivity.this,BaseActivity.class);
					intent .putExtra("shouldReplaceFragment",0);
					startActivity(intent);
				}
			});
			seeOrder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(WXPayEntryActivity.this,OrderDetailActivity.class);
					intent .putExtra("orderId",orderId);
					startActivity(intent);
				}
			});
			/*Net.get().orderQuery(orderId)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(orderPayResult -> {
						if(orderPayResult.getCode()==200){

						}
						else{
							Log.d("error:--WX", "error");
						}

					}, throwable -> {
						Log.d("error:--WX", "error");
					});
*/

		}
		//支付错误
		else if(code==-1){
			resultCode=-1;
			finish();
		}
		//用户取消
		else if(code==-2){
			resultCode=-2;
			finish();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}