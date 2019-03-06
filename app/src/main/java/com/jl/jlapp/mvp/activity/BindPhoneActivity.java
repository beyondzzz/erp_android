package com.jl.jlapp.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.utils.CountDownTimerUtils;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-18.
 */

public class BindPhoneActivity extends AppCompatActivity{
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
    @BindView(R.id.return_btn)
    ImageView returnBtn;

    String messageCode = "";
    String mobile = "";
    String beforMobile = "";

    long failureTime = -1;
    String inputCode = "";
    String account ="";
    int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);

        //控制注解
        ButterKnife.bind(this);
        Tools.addActivity(this);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        type = intent.getIntExtra("type",-1);
        Log.d("bindPhone", "onCreate: account:"+account);
        init();


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
                    Toast.makeText(BindPhoneActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if(!isMobile(mobile)){
                    Toast.makeText(BindPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(BindPhoneActivity.this, ""+MessageCodeModel.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                                //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                            }, throwable -> {
                                Toast.makeText(BindPhoneActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BindPhoneActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if(!isMobile(mobile)){
                    Toast.makeText(BindPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else if("".equals(messageCode)){
                    Toast.makeText(BindPhoneActivity.this, "请先获取动态密码", Toast.LENGTH_SHORT).show();
                }else if("".equals(inputCode)){//没有输入验证码
                    Toast.makeText(BindPhoneActivity.this, "请输入动态密码", Toast.LENGTH_SHORT).show();
                }else if(failureTime == -1){
                    Toast.makeText(BindPhoneActivity.this, "请先获取动态密码", Toast.LENGTH_SHORT).show();
                }else if(nowData>failureTime){
                    Toast.makeText(BindPhoneActivity.this, "动态密码已失效，请重新获取", Toast.LENGTH_SHORT).show();
                }else if(inputCode.equals(messageCode)){//验证码正确
                    //调用登录接口  手机号登录
                    if(!beforMobile.equals(mobile)){//获取验证码的手机号和当前的手机号不一致
                        Toast.makeText(BindPhoneActivity.this, "手机号与动态密码不匹配，请重试", Toast.LENGTH_SHORT).show();
                    }else {
                        Net.get().bindPhone(mobile,account,type).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(LoginModel -> {

                                    int code1 = LoginModel.getCode();
                                    if (code1 == 200) {

                                        int userId = LoginModel.getResultData().getUserId();
                                        String userPicUrl = LoginModel.getResultData().getUserPicUrl();
                                        String message = LoginModel.getResultData().getMessage();
                                        String userName=LoginModel.getResultData().getUserName();
                                        //                                        if(message.equals("登录成功") || message.equals("注册成功并返券")){
                                        //                                            Toast.makeText(LoginActivity.this, "登录成功   "+, Toast.LENGTH_SHORT).show();
                                        //                                        }else if(message.equals("注册成功无可返优惠券")){
                                        //
                                        //                                        }
                                        if(message.equals("注册成功并返券")){
                                            Toast.makeText(BindPhoneActivity.this, "新用户登录，优惠券已发放到您的账户", Toast.LENGTH_SHORT).show();
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

                                        Tools.finishAll();


                                    } else {
                                        Toast.makeText(BindPhoneActivity.this, ""+LoginModel.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                    //                    Toast.makeText(getContext(),"size:"+goodsListModels.size(),Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    Toast.makeText(BindPhoneActivity.this, AppFinal.NET_ERROR,Toast.LENGTH_SHORT).show();
                                });
                    }

                }else if(!inputCode.equals(messageCode)){
                    Toast.makeText(BindPhoneActivity.this, "动态密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });

        connect_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BindPhoneActivity.this, "正在开发中...",Toast.LENGTH_SHORT).show();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                Toast.makeText(BindPhoneActivity.this, "暂无内容!", Toast.LENGTH_LONG).show();

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
}
