package com.jl.jlapp.mvp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.bumptech.glide.Glide;
import com.jl.jlapp.R;
import com.jl.jlapp.eneity.AuthResult;
import com.jl.jlapp.eneity.PayResult;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.popu.ConnectServerPopu;
import com.jl.jlapp.utils.Config;
import com.jl.jlapp.utils.CountDownTimerUtils;
import com.jl.jlapp.utils.Tools;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-18.
 */

public class LoginActivity extends AppCompatActivity{
    @BindView(R.id.tips)
    TextView tipsView;
    @BindView(R.id.phone_number)
    EditText phone_number;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.get_code)
    TextView get_code;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.connect_server)
    TextView connect_server;

    @BindView(R.id.VX)
    LinearLayout vx;
    @BindView(R.id.QQ)
    LinearLayout qq;
    //
    public static Tencent mTencent;
    IUiListener IListener=new BaseUiListener();

    private IWXAPI api;

    @BindView(R.id.ZFB)
    LinearLayout zfb;


    String messageCode = "";
    String mobile = "";
    String beforMobile = "";
    long failureTime = -1;
    String inputCode = "";
    ConnectServerPopu clearHistorySearhPopu;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //沙箱环境
        //EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Tools.addActivity(this);
        //控制注解
        ButterKnife.bind(this);

        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, Config.APP_ID_WX,true);
        //将应用的appid注册到微信
        api.registerApp(Config.APP_ID_WX);

        init();


        Intent intent = getIntent();
        int type=intent.getIntExtra("type",0);
        if(type==1){
            String Openid = intent.getStringExtra("Openid");
            login(Openid,1);
        }

    }
    public View.OnClickListener confirmClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearHistorySearhPopu.dismiss();

            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+"4006865856"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    };
    private void init(){
        handleTips();
        phone_number.setInputType(InputType.TYPE_CLASS_PHONE);
        password.setInputType(InputType.TYPE_CLASS_NUMBER);
        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = phone_number.getText().toString().trim();
                beforMobile = mobile;
//                Log.d("LoginOnClick", "mobile: "+mobile);
//                Toast.makeText(LoginActivity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                //调用获取短信接口
                if("".equals(mobile)){
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if(!isMobile(mobile)){
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else{
                    Net.get().getMessageCode(mobile).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(MessageCodeModel -> {

                                int code1 = MessageCodeModel.getCode();
                                if (code1 == 200) {
                                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(get_code, 60000, 1000);
                                    mCountDownTimerUtils.start();
                                    //保存验证码以及失效时间
                                    messageCode = MessageCodeModel.getResultData().getMessageCode();
                                    failureTime = MessageCodeModel.getResultData().getFailureTime();
                                    //                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    //                                String formatTime = sdf.format(FailureTime);

                                } else {
                                    Toast.makeText(LoginActivity.this, ""+MessageCodeModel.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                                //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                            }, throwable -> {
                                Toast.makeText(LoginActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCode = password.getText().toString().trim();
                mobile = phone_number.getText().toString().trim();
                long nowData = System.currentTimeMillis();
//                Log.d("Login", "nowData: "+nowData);
//                Log.d("Login", "failureTime: "+nowData);
                if("".equals(mobile)){
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if(!isMobile(mobile)){
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else if("".equals(messageCode)){
                    Toast.makeText(LoginActivity.this, "请先获取动态密码", Toast.LENGTH_SHORT).show();
                }else if("".equals(inputCode)){//没有输入验证码
                    Toast.makeText(LoginActivity.this, "请输入动态密码", Toast.LENGTH_SHORT).show();
                }else if(failureTime == -1){
                    Toast.makeText(LoginActivity.this, "请先获取动态密码", Toast.LENGTH_SHORT).show();
                }else if(nowData>failureTime){
                    Toast.makeText(LoginActivity.this, "动态密码已失效，请重新获取", Toast.LENGTH_SHORT).show();
                }else if(inputCode.equals(messageCode)){//验证码正确
                    if(!beforMobile.equals(mobile)){//获取验证码的手机号和当前的手机号不一致
                        Toast.makeText(LoginActivity.this, "手机号与动态密码不匹配，请重试", Toast.LENGTH_SHORT).show();
                    }else{
                        //调用登录接口  手机号登录
                        Net.get().login(mobile,0).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(LoginModel -> {

                                    int code1 = LoginModel.getCode();
                                    if (code1 == 200) {

                                        int userId = LoginModel.getResultData().getUserId();
                                        String userPicUrl = LoginModel.getResultData().getUserPicUrl();
                                        String message = LoginModel.getResultData().getMessage();
                                        String userName=LoginModel.getResultData().getUserName();
                                        int isVIP=LoginModel.getResultData().getIsVIP();
                                        //                                        if(message.equals("登录成功") || message.equals("注册成功并返券")){
                                        //                                            Toast.makeText(LoginActivity.this, "登录成功   "+, Toast.LENGTH_SHORT).show();
                                        //                                        }else if(message.equals("注册成功无可返优惠券")){
                                        //
                                        //                                        }
                                        if(message.equals("注册成功并返券")){
                                            Toast.makeText(LoginActivity.this, "新用户登录，优惠券已发放到您的账户", Toast.LENGTH_SHORT).show();
                                        }

                                        //获取sharedPreferences对象
                                        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
                                        //获取editor对象
                                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                        //存储键值对
                                        editor.putInt(AppFinal.USERID,userId);
                                        editor.putString(AppFinal.USERPHONE, mobile);
                                        editor.putString(AppFinal.USERPICURL, userPicUrl);
                                        editor.putString(AppFinal.USERNAME, userName);
                                        editor.putInt(AppFinal.ISVIP, isVIP);
                                        //提交
                                        editor.commit();//提交修改

                                        //                                        Toast.makeText(LoginActivity.this, "登录成功"+userId, Toast.LENGTH_SHORT).show();

                                        finish();

                                    } else {
                                        Toast.makeText(LoginActivity.this, ""+LoginModel.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                    //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    Toast.makeText(LoginActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                });
                    }


                }else if(!inputCode.equals(messageCode)){
                    Toast.makeText(LoginActivity.this, "动态密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });

        connect_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistorySearhPopu = new ConnectServerPopu(LoginActivity.this,confirmClick);

                clearHistorySearhPopu.showAtLocation(findViewById(R.id.activity_login), Gravity.CENTER, 0, 0);

//                Toast.makeText(LoginActivity.this, "正在开发中...",Toast.LENGTH_SHORT).show();
            }
        });
        vx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "微信...",Toast.LENGTH_SHORT).show();
                doLogin(2);
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(1);

               Toast.makeText(LoginActivity.this, "QQ...",Toast.LENGTH_SHORT).show();

            }
        });
        zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "支付宝...",Toast.LENGTH_SHORT).show();
                doLogin(3);
            }
        });
    }
    public void handleTips(){
        //提示信息变色及点击事件处理
        String tips = tipsView.getText().toString().trim();
        SpannableStringBuilder spannable = new SpannableStringBuilder(tips);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Do something.
//                Toast.makeText(LoginActivity.this, "暂无内容!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this,RegistBookActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(android.R.color.holo_orange_dark));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        //        spannableString.setSpan(clickableSpan,0,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //        textView.setText(spannableString);
        spannable.setSpan(clickableSpan,tips.length()-6,tips.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tipsView.setText(spannable);

        tipsView.setMovementMethod(LinkMovementMethod.getInstance());//想要实现指定文字的点击事件，需要添加该方法

    }

    /**
     * 验证手机格式
     */
    public  boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }




    //第三登录
   public void doLogin(int type){
      switch (type){
          case 1:{//QQ登录
              String SCOPE="all";
              mTencent = Tencent.createInstance(Config.QQ_LOGIN_APP_ID, getApplicationContext());
              mTencent.login(this,SCOPE, IListener);
              break;
          }
          case 2:{//微信登录
              Tools.addActivity(this);
              SendAuth.Req req = new SendAuth.Req();
              req.scope = "snsapi_userinfo";
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
              req.state = "wechat_sdk_微信登录";
              api.sendReq(req);
              break;
          }
          case 3:{//支付宝登录
              Net.get().getAuthInfo()
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(authTask1 -> {
                          Runnable authRunnable = new Runnable() {
                              @Override
                              public void run() {
                                  // 构造AuthTask 对象
                                  AuthTask authTask = new AuthTask(LoginActivity.this);
                                  // 调用授权接口，获取授权结果
                                  Map<String, String> result = authTask.authV2(authTask1.getResultData(), true);

                                  Message msg = new Message();
                                  msg.what = SDK_AUTH_FLAG;
                                  msg.obj = result;
                                  mHandler.sendMessage(msg);
                              }
                          };
                          // 必须异步调用
                          Thread authThread = new Thread(authRunnable);
                          authThread.start();
                      }, throwable -> {
                          Log.d("error:--A", "asdasdasd");
                      });
              break;
          }
      }
   }












    //QQ登录重写方法，返回调用的接口
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(LoginActivity.this, "返回为空，登录失败",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(LoginActivity.this, "返回为空，登录失败",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(LoginActivity.this, "登录成功",Toast.LENGTH_SHORT).show();
            String token= null;
            String expires= null;
            String openId=null;
            try {
                token = jsonResponse.getString(Constants.PARAM_ACCESS_TOKEN);
                expires=jsonResponse.getString(Constants.PARAM_EXPIRES_IN);
                openId = jsonResponse.getString(Constants.PARAM_OPEN_ID);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)&& !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token,expires);
                mTencent.setOpenId(openId);
                UserInfo info= new UserInfo(LoginActivity.this,mTencent.getQQToken());
                info.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object argo) {
                        try{
                            JSONObject jsonobj= (JSONObject) argo;
                            String qqOpenId =  mTencent.getOpenId();//qq登录openid
                            Log.d("loginActivity", "onComplete:qqOpenId: "+qqOpenId);
                            login(qqOpenId,2);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(UiError uiError) {

                    }
                    @Override
                    public void onCancel() {

                    }
                });
            }

            // 有奖分享处理
           /* handlePrizeShare();
            doComplete((JSONObject)response);*/
        }

        protected void doComplete(JSONObject values) {
            Log.d("qqqqqq",values.toString());
        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(LoginActivity.this, "ERROR"+e.errorDetail,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "登录关闭",Toast.LENGTH_SHORT).show();
        }
    }

   // 在调用login的Activity或者Fragment重写onActivityResult方法，示例代码如下：
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   qq
        if (requestCode == Constants.REQUEST_LOGIN||requestCode == Constants.REQUEST_APPBAR){
            Tencent.onActivityResultData(requestCode,resultCode,data,IListener);
        }
        //  weixin
        if(resultCode == 0){
            String Openid = data.getStringExtra("Openid");
            login(Openid,1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //account：账号(String)    type：账号类型(int：0:手机号码    1:微信    2:QQ    3:支付宝)
    private void login(String account, int type){
        Log.d("loginActivity", "login:account: "+account);

        Net.get().login(account,type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LoginModel -> {

                    int code1 = LoginModel.getCode();
                    if (code1 == 200) {
                        int flag = LoginModel.getResultData().getFlag();
                        if(flag == 0){//绑定手机号
                            Intent intent = new Intent(LoginActivity.this,BindPhoneActivity.class);
                            intent.putExtra("account",account);
                            intent.putExtra("type",type);
                            startActivity(intent);

                        }else if(flag == 1){//不用绑定手机号
                            int userId = LoginModel.getResultData().getUserId();
                            String userPicUrl = LoginModel.getResultData().getUserPicUrl();
                            String message = LoginModel.getResultData().getMessage();
                            String userName=LoginModel.getResultData().getUserName();
                            String mobile=LoginModel.getResultData().getUserPhone();
                            //                                        if(message.equals("登录成功") || message.equals("注册成功并返券")){
                            //                                            Toast.makeText(LoginActivity.this, "登录成功   "+, Toast.LENGTH_SHORT).show();
                            //                                        }else if(message.equals("注册成功无可返优惠券")){
                            //
                            //                                        }
                            if(message.equals("注册成功并返券")){
                                Toast.makeText(LoginActivity.this, "新用户登录，优惠券已发放到您的账户", Toast.LENGTH_SHORT).show();
                            }

                            //获取sharedPreferences对象
                            SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
                            //获取editor对象
                            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                            //存储键值对
                            editor.putInt(AppFinal.USERID,userId);
                            editor.putString(AppFinal.USERPHONE, mobile);
                            editor.putString(AppFinal.USERPICURL, userPicUrl);
                            editor.putString(AppFinal.USERNAME, userName);
                            //提交
                            editor.commit();//提交修改

                            //                                        Toast.makeText(LoginActivity.this, "登录成功"+userId, Toast.LENGTH_SHORT).show();

                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, ""+LoginModel.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }


                }, throwable -> {
                    Toast.makeText(LoginActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                });
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(LoginActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(LoginActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        login(authResult.getAlipayOpenId(),3);
                        Toast.makeText(LoginActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(LoginActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

}
