package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserNameSettingActivity extends Activity{

    @BindView(R.id.user_name)
    EditText userNameET;

    int userId = 0;
    String name="";
    SharedPreferences sharedPreferences;

    ClearHistorySearhPopu clearHistorySearhPopu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_setting);
        ButterKnife.bind(this);
        Tools.addActivity(this);
        sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        name=sharedPreferences.getString(AppFinal.USERNAME,"");
        userNameET.setText(name);
    }

    @OnClick({R.id.ok_btn,R.id.input_del_img,R.id.icon_return})
    public void onClick(View view) {
        String userName=userNameET.getText().toString().trim();
        switch (view.getId()){
            case R.id.ok_btn:
                String regex="^[\u4e00-\u9fa5a-zA-Z]+[\u4e00-\u9fa5\\w-]+";
                if("".equals(userName)){
                    Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    break;
                }
                else if(userName.length()<2){
                    Toast.makeText(this,"请输入2-20个字符",Toast.LENGTH_SHORT).show();
                    break;
                }
                else if(!userName.matches(regex)){
                    Toast.makeText(this,"用户名不合法，请重新输入",Toast.LENGTH_SHORT).show();
                    break;
                }
                updateUserName(userName);
                break;
            case R.id.input_del_img:
                userNameET.setText("");
                break;
            case R.id.icon_return:
                if(!"".equals(userName)&&!name.equals(userName)){
                    clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearHistorySearhPopu.dismiss();
                            finish();
                        }
                    }, 9);
                    clearHistorySearhPopu.showAtLocation(findViewById(R.id.user_name_setting_page), Gravity.CENTER, 0, 0);
                }
                else{
                    finish();
                }

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            String userName=userNameET.getText().toString().trim();
            if(!"".equals(userName)&&!name.equals(userName)){
                clearHistorySearhPopu = new ClearHistorySearhPopu(this,  new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearHistorySearhPopu.dismiss();
                        finish();
                    }
                }, 9);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.user_name_setting_page), Gravity.CENTER, 0, 0);
            }
            else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 修改用户名
     * @param username
     */
    private void updateUserName(String username){
        Net.get().updateUserName(userId,username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postModel -> {
                    if(postModel.getCode()==200){
                        Toast.makeText(UserNameSettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        //通过sharedPreferences对象获取editor对象
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        //存储键值对
                        editor.putString(AppFinal.USERNAME, username);
                        //提交
                        editor.commit();//提交修改
                        finish();
                    }
                    else{
                        Toast.makeText(UserNameSettingActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(UserNameSettingActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


}
