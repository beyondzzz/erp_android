package com.jl.jlapp.wxapi;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jl.jlapp.eneity.WXAccessTokenEntity;
import com.jl.jlapp.eneity.WXBaseRespEntity;
import com.jl.jlapp.mvp.activity.LoginActivity;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.Config;
import com.jl.jlapp.utils.Tools;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description ：
 * project name：CCloud
 * author : Vincent
 * creation date: 2017/6/9 18:13
 *
 * @version 1.0
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler{

    /**
     * 微信登录相关
     */
    private IWXAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, Config.APP_ID_WX,true);
        //将应用的appid注册到微信
        api.registerApp(Config.APP_ID_WX);

        Log.d("111111","--------------------------------");

        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result =  api.handleIntent(getIntent(), this);
            if(!result){
                Log.d("111111","参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data,this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("baseReq:",JSON.toJSONString(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("baseResp:--A",JSON.toJSONString(baseResp));
        Log.d("baseResp--B:",baseResp.errStr+","+baseResp.openId+","+baseResp.transaction+","+baseResp.errCode);
        WXBaseRespEntity entity = JSON.parseObject(JSON.toJSONString(baseResp),WXBaseRespEntity.class);
        String result = "";
        switch(baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
              Net.get("https://api.weixin.qq.com/").getAccessToken(Config.APP_ID_WX,Config.APP_SECRET_WX,entity.getCode(),"authorization_code")
                        .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(wxAccessTokenEntity -> {
                        if(wxAccessTokenEntity!=null){
                            getUserInfo(wxAccessTokenEntity);
                        }else {
                            Log.d("error:--A", "获取失败");
                        }
                    }, throwable -> {
                        Log.d("error:--A", "asdasdasd");
                    });

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:
                result = "签名错误";
                break;
            /*default:
                result = "发送返回";
                showMsg(0,result);
                finish();
                break;*/
        }

    }

    /**
     * 获取个人信息
     * @param accessTokenEntity
     */
    private void getUserInfo(WXAccessTokenEntity accessTokenEntity) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        Net.get("https://api.weixin.qq.com/").getUserInfo(accessTokenEntity.getAccess_token(),accessTokenEntity.getOpenid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wxUserInfo -> {
                    String Openid = wxUserInfo.getOpenid();
                  /*  Intent intent = getIntent();
                    intent.putExtra("Openid",Openid);
                    WXEntryActivity.this.setResult(0,intent);
                    finish();*/
                    Tools.addActivity(this);
                    Intent intent=new Intent();
                    intent.setClass(WXEntryActivity.this,LoginActivity.class);
                    intent.putExtra("type",1);
                    intent.putExtra("Openid",Openid);
                    startActivity(intent);

                    Tools.finishAll();
                }, throwable -> {
                    Log.d("error:--A", "获取错误");
                });
    }
}
