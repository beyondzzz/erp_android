package com.jl.jlapp.mvp.activity;

import android.app.Activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.db.DatabaseHelper;
import com.jl.jlapp.eneity.VersionMessageModel;
import com.jl.jlapp.mvp.base.MyApplication;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.service.DownloadService;
import com.jl.jlapp.utils.DataCleanManager;
import com.jl.jlapp.utils.Tools;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingsActivity extends Activity {

    private static final int UPDATA_CLIENT=0;
    private static final int GET_UNDATAINFO_ERROR=1;
    private static final int DOWN_ERROR=2;
    //private String apkUrl = "http://117.158.178.202:8000/jlfood.apk";
    @BindView(R.id.user_message_setting_rela)
    RelativeLayout userMessageSetting;
    @BindView(R.id.clear_cache_rela)
    RelativeLayout clearCacheRela;
    @BindView(R.id.version_check)
    RelativeLayout versionCheck;
    @BindView(R.id.icon_return)
    ImageView iconReturn;
    @BindView(R.id.user_pic)
    ImageView userPic;
    @BindView(R.id.clear_localhost_cashe_num)
    TextView clearLocalhostCasheNum;
    @BindView(R.id.version_checking_unit)
    TextView versionCheckingUnit;
    @BindView(R.id.user_name)
    TextView userNameTv;

    VersionMessageModel.ResultDataBean versionMessageBean=null;

    String picUrl = "";
    String userName = "";
    String cache = "";//缓存大小
    String versionName = "0";//版本名
    ClearHistorySearhPopu clearHistorySearhPopu;
    DatabaseHelper dbHelper;
    SQLiteDatabase sqliteDatabase;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Tools.addActivity(this);
        getNewVersionMessage();
        try {
            getVersionName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建SQLiteOpenHelper子类对象
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(this);
            //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
            sqliteDatabase = dbHelper.getWritableDatabase();
            // SQLiteDatabase  sqliteDatabase = dbHelper.getReadbleDatabase();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        picUrl = sharedPreferences.getString(AppFinal.USERPICURL, "userPicUrl");
        userName = sharedPreferences.getString(AppFinal.USERNAME, "");
        userNameTv.setText(userName);
        Tools.setCircleIcon(AppFinal.BASEURL + picUrl, userPic);

        try {
            cache = DataCleanManager.getTotalCacheSize(this);
            versionName = sharedPreferences.getString(AppFinal.VERSIONNAME, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearLocalhostCasheNum.setText(cache);


        versionCheckingUnit.setText(versionName);
    }

    /*
   * 获取当前程序的版本名
   */
    private String getVersionName() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        Log.d("TAG", "版本号" + packInfo.versionCode);
        Log.d("TAG", "版本名" + packInfo.versionName);
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        editor.putInt(AppFinal.VERSIONCODE,packInfo.versionCode);
        editor.putString(AppFinal.VERSIONNAME, packInfo.versionName);
        //提交
        editor.commit();//提交修改
        return packInfo.versionName;
    }

    @OnClick({R.id.user_message_setting_rela, R.id.icon_return, R.id.clear_cache_rela, R.id.logout, R.id.message_setting_rela, R.id.version_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_message_setting_rela:
                Intent intent = new Intent(SettingsActivity.this, UserMessageSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.icon_return:
                finish();
                break;
            //清除缓存
            case R.id.clear_cache_rela:
                clearHistorySearhPopu = new ClearHistorySearhPopu(this, itemclick, 6);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.setting), Gravity.CENTER, 0, 0);
                break;
            //版本检测
            case R.id.version_check:
                if (versionMessageBean!=null){
                    try {
                        if (versionMessageBean.getVersionName().equals(versionName)){
                            Toast.makeText(SettingsActivity.this,"已是最新版本",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //动态分配权限
                            verifyStoragePermissions(this);
                            //不是强制更新
                            if (versionMessageBean.getIsMustUpdate()==0){
                                noMustChange();
                            }
                            //强制更新
                            else{
                                 new CheckVersionTask().run();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SettingsActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SettingsActivity.this, "版本检测失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logout:
                clearHistorySearhPopu = new ClearHistorySearhPopu(this, itemclick2, 7);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.setting), Gravity.CENTER, 0, 0);
                break;
            case R.id.message_setting_rela:
                Intent intent2 = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                startActivity(intent2); // 打开系统设置界面
                break;
        }
    }

    //不是必须更新项目
    private void noMustChange(){
        android.support.v7.app.AlertDialog.Builder builer = new android.support.v7.app.AlertDialog.Builder(SettingsActivity.this) ;
        builer.setTitle("版本升级");
        builer.setMessage("检测到最新版本，请及时更新！");
        Log.d("apkurlaaaaaaaaa","apkurl:"+AppFinal.APKURL);
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                BaseActivity.IS_CHECK_NO_CHANGE=false;
                dialog.dismiss();
                // downLoadApk();
                DownloadService.startService(MyApplication.getInstance().getApplicationContext(), AppFinal.APKURL);
                //ToastUtils.showShortSafe("已在后台下载更新");
                Toast.makeText(SettingsActivity.this,"已在后台下载更新",Toast.LENGTH_SHORT).show();
            }
        });
        //当点取消按钮时进行登录
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                BaseActivity.IS_CHECK_NO_CHANGE=true;
                dialog.dismiss();
            }
        });
        builer.create().show();
    }

    //动态分配权限
    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除缓存的确认按钮事件
     */
    public View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearHistorySearhPopu.dismiss();
            DataCleanManager.clearAllCache(SettingsActivity.this);
            Toast.makeText(SettingsActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
            try {
                cache = DataCleanManager.getTotalCacheSize(SettingsActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }
            clearLocalhostCasheNum.setText(cache);
            dbHelper.delete(sqliteDatabase);
        }
    };
    /**
     * 退出登录
     */
    public View.OnClickListener itemclick2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearHistorySearhPopu.dismiss();
            if (sharedPreferences == null) {
                sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
            }
            editor = sharedPreferences.edit();//获取编辑器
            editor.clear();
            editor.commit();
            Tools.finishAll();
            Intent intent = new Intent(SettingsActivity.this, BaseActivity.class);
            intent.putExtra("shouldReplaceFragment",3);
            startActivity(intent);

        }
    };

    /*
    * 获取当前程序的版本名
    */
    /*private String getVersionName() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        Log.d("TAG", "版本号" + packInfo.versionCode);
        Log.d("TAG", "版本名" + packInfo.versionName);
        return packInfo.versionName;
    }*/

    //从服务器上获取新版本信息
    private void getNewVersionMessage(){
        Net.get().getNewVersionMessage(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(versionMessageModel -> {
                    if(versionMessageModel.getCode()==200){
                            versionMessageBean=versionMessageModel.getResultData();
                            if (!versionName.equals(versionMessageBean. getVersionName())){
                                Toast.makeText(this,"有新版本了，请及时更新",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(this,"已是最新版本",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "版本信息获取失败", Toast.LENGTH_SHORT).show();
                        }
                }, throwable -> {
                    Toast.makeText(SettingsActivity.this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }


    public class CheckVersionTask implements Runnable {
        @Override
        public void run() {
            try {
                if (versionMessageBean.getVersionName().equals(versionName)){
                    Toast.makeText(SettingsActivity.this,"已是最新版本",Toast.LENGTH_SHORT).show();
                }
                else{
                    Message msg = new Message();
                    msg.what = UPDATA_CLIENT;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                // 待处理
                Message msg = new Message();
                msg.what = GET_UNDATAINFO_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }

        Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATA_CLIENT:
                        //对话框通知用户升级程序
                        showUpdataDialog();
                        Log.d("aaaaaaaasetting","对话框通知用户升级程序");
                        break;
                    case GET_UNDATAINFO_ERROR:
                        //服务器超时
                        Toast.makeText(SettingsActivity.this, "获取服务器更新信息失败", Toast.LENGTH_LONG).show();
                        LoginMain();
                        break;
                    case DOWN_ERROR:
                        //下载apk失败
                        Toast.makeText(SettingsActivity.this, "下载新版本失败", Toast.LENGTH_LONG).show();
                        LoginMain();
                        break;
                }
            }
        };

        /*
    *
    * 弹出对话框通知用户更新程序
    *
    * 弹出对话框的步骤：
    *  1.创建alertDialog的builder.
    *  2.要给builder设置属性, 对话框的内容,样式,按钮
    *  3.通过builder 创建一个对话框
    *  4.对话框show()出来
    */
        protected void showUpdataDialog() {
            android.support.v7.app.AlertDialog.Builder builer = new android.support.v7.app.AlertDialog.Builder(SettingsActivity.this) ;
            builer.setTitle("版本升级");
            builer.setMessage("检测到新版本，部分功能需升级后才可使用，请先升级！");
            builer.setCancelable(false);
            //当点确定按钮时从服务器上下载 新的apk 然后安装
            builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("aaaaaaaasetting","下载apk,更新");
                    dialog.dismiss();
                    downLoadApk();

                }
            });
            //当点取消按钮时进行登
           /* builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    LoginMain();
                    //dialog.dismiss();
                }
            });*/
            builer.create().show();
        }

        /*
     * 从服务器中下载APK
     */
    protected void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(SettingsActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCancelable(false);
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(AppFinal.APKURL, pd);
                    //sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }}.start();
    }

        //自动安装apk
        protected void installApk(File file) {
            Uri uri;
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //如果是7.0以上的系统，要使用FileProvider的方式构建Uri
                uri = FileProvider.getUriForFile(MyApplication.getInstance().getApplicationContext(), "com.jl.jlapp.fileprovider", file);//在AndroidManifest中的android:authorities值
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent1.setDataAndType(uri, "application/vnd.android.package-archive");
            } else {
                intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivity(intent1);
        }

        /*
         * 进入程序的主界面
         */
        private void LoginMain(){
            Intent intent = new Intent(SettingsActivity.this,BaseActivity.class);
            startActivity(intent);
            //结束掉当前的activity
            Tools.finishAll();
        }
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "jlfood.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }


}
