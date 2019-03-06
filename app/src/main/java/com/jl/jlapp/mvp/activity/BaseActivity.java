package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.eneity.ClissifyModel;
import com.jl.jlapp.eneity.VersionMessageModel;
import com.jl.jlapp.mvp.base.MyApplication;
import com.jl.jlapp.mvp.fragment.ClassifityFragment;
import com.jl.jlapp.mvp.fragment.Fragment_FirstPages;
import com.jl.jlapp.mvp.fragment.Fragment_User;
import com.jl.jlapp.mvp.fragment.ShopCartFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.service.DownloadService;
import com.jl.jlapp.utils.Tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by THINK on 2018-01-17.
 */

public class BaseActivity extends FragmentActivity {

    private static final int UPDATA_CLIENT = 0;
    private static final int GET_UNDATAINFO_ERROR = 1;
    private static final int DOWN_ERROR = 2;


    public static boolean IS_CHECK_NO_CHANGE = false;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    Fragment_FirstPages fragment_FirstPages;
    ClassifityFragment fragment_classify;
    ShopCartFragment fragment_shoppingCat;
    Fragment_User fragment_user;
    ArrayList<Fragment> fragmentList;
    FragmentManager fragmentManager;//事务管理器
    FragmentTransaction transaction;//碎片执行事务
    RadioGroup rg;
    RadioButton rb1, rb2, rb3, rb4;
    ArrayList<RadioButton> listRadioButton;

    int shouldReplaceFragment = 0;//启动该页面时需要加载第几个碎片 0：首页，1：分类，2：购物车，3：我的
    int userId = 0;
    VersionMessageModel.ResultDataBean versionMessageBean = null;
    private String versionName="";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        //获取新版本信息
        getNewVersionMessage();
    }

    // ClissifyModel.ResultDataBean ResultDataBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Intent intent = getIntent();
        shouldReplaceFragment = intent.getIntExtra("shouldReplaceFragment", 0);

        try {
            versionName=getVersionName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // RadioGroup RadioButton绑定
        rg = (RadioGroup) findViewById(R.id.rg);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        // 添加RadioButton
        listRadioButton = new ArrayList<RadioButton>();

        listRadioButton.add(rb1);
        listRadioButton.add(rb2);
        listRadioButton.add(rb3);
        listRadioButton.add(rb4);
        // 实例化碎片
        fragment_FirstPages = new Fragment_FirstPages();
        fragment_classify = new ClassifityFragment();
        fragment_shoppingCat = new ShopCartFragment();
        fragment_user = new Fragment_User();
        // 将碎片添加到碎片集合中
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragment_FirstPages);
        fragmentList.add(fragment_classify);
        fragmentList.add(fragment_shoppingCat);
        fragmentList.add(fragment_user);
        // 得到碎片管理器并设置适配器
        fragmentManager = getSupportFragmentManager();
        //首次进入加载碎片
        listRadioButton.get(shouldReplaceFragment).setChecked(true);
        switch (shouldReplaceFragment) {
            case 0:
                replaceFragmentPage(fragment_FirstPages);
                break;
            case 1:
                replaceFragmentPage(fragment_classify);
                break;
            case 2:
                replaceFragmentPage(fragment_shoppingCat);
                break;
            case 3:
                replaceFragmentPage(fragment_user);
                break;
        }

        // RadioGroup处理事件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // Toast.makeText(getApplicationContext(),ResultDataBean.getName(),Toast.LENGTH_SHORT).show();

                for (int i = 0; i < listRadioButton.size(); i++) {
                    shouldReplaceFragment = i;
                    if (arg1 == listRadioButton.get(i).getId()) {
                        /*switch (arg1){
                            case R.id.rb1:
                                fragment_FirstPages = new Fragment_FirstPages();
                                replaceFragmentPage(fragment_FirstPages);
                                break;
                            case R.id.rb2:
                                fragment_classify = new ClassifityFragment();
                                replaceFragmentPage(fragment_classify);
                                break;
                            case R.id.rb3:
                                fragment_shoppingCat = new ShopCartFragment();
                                replaceFragmentPage(fragment_shoppingCat);
                                break;
                            case R.id.rb4:
                                fragment_user = new Fragment_User();
                                replaceFragmentPage(fragment_user);
                                break;

                        }*/
                        if (arg1 == R.id.rb3) {
                            if (userId > 0) {
                                fragment_shoppingCat = new ShopCartFragment();
                                replaceFragmentPage(fragment_shoppingCat);
                            } else {
                                Toast.makeText(BaseActivity.this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
                                replaceFragmentPage(fragmentList.get(3));
                                listRadioButton.get(3).setChecked(true);
                            }


                        } else {
                            replaceFragmentPage(fragmentList.get(i));
                        }

                    }

                }
            }
        });
    }

    public void replaceFragmentPage(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();// 得到碎片管理器
        transaction = fragmentManager.beginTransaction();// 开启碎片执行事务

        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();

    }
    /*private long clickTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再次点击退出",  Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
           // Log.e(TAG, "exit application");
            this.finish();
            System.exit(0);
        }
    }*/

    //从服务器上获取新版本信息
    private void getNewVersionMessage() {
        Net.get().getNewVersionMessage(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(versionMessageModel -> {
                    if (versionMessageModel.getCode() == 200) {

                            versionMessageBean = versionMessageModel.getResultData();
                            //版本名不一致说明要更新
                            if (!versionName.equals(versionMessageBean.getVersionName())){
                                //动态赋权限
                                verifyStoragePermissions(this);
                                //必须更新项目
                                if (versionMessageBean.getIsMustUpdate()==1){
                                    new CheckVersionTask().run();
                                }
                                //不是必更
                                else{
                                    if (!IS_CHECK_NO_CHANGE){
                                        noMustChange();
                                    }
                                }
                            }
                    } else {
//                        Toast.makeText(this, "版本信息获取失败", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
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

    //不是必须更新项目
    private void noMustChange(){
        android.support.v7.app.AlertDialog.Builder builer = new android.support.v7.app.AlertDialog.Builder(this) ;
        builer.setTitle("版本升级");
        builer.setMessage("检测到最新版本，请及时更新！");
        Log.d("apkurlaaaaaaaaa","apkurl:"+AppFinal.APKURL);
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                IS_CHECK_NO_CHANGE=false;
                dialog.dismiss();

                DownloadService.startService(MyApplication.getInstance().getApplicationContext(), AppFinal.APKURL);
                Toast.makeText(BaseActivity.this,"已在后台下载更新",Toast.LENGTH_SHORT).show();
            }
        });
        //当点取消按钮时进行登录
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                IS_CHECK_NO_CHANGE=true;
                dialog.dismiss();
            }
        });
        builer.create().show();
    }

    //必须更新项目的代码
    public class CheckVersionTask implements Runnable {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                msg.what = UPDATA_CLIENT;
                handler.sendMessage(msg);

            } catch (Exception e) {
                // 待处理
                Message msg = new Message();
                msg.what = GET_UNDATAINFO_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }

        Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATA_CLIENT:
                        //对话框通知用户升级程序
                        showUpdataDialog();
                        Log.d("aaaaaaaasetting", "对话框通知用户升级程序");
                        break;
                    case GET_UNDATAINFO_ERROR:
                        //服务器超时
                        Toast.makeText(BaseActivity.this, "获取服务器更新信息失败", Toast.LENGTH_LONG).show();
                        LoginMain();
                        break;
                    case DOWN_ERROR:
                        //下载apk失败
                        Toast.makeText(BaseActivity.this, "下载新版本失败", Toast.LENGTH_LONG).show();
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
            android.support.v7.app.AlertDialog.Builder builer = new android.support.v7.app.AlertDialog.Builder(BaseActivity.this);
            builer.setTitle("版本升级");
            builer.setMessage("检测到新版本，部分功能需升级后才可使用，请先升级！");
            builer.setCancelable(false);
            //当点确定按钮时从服务器上下载 新的apk 然后安装
            builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("aaaaaaaasetting", "下载apk,更新");
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
            pd = new ProgressDialog(BaseActivity.this);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMessage("正在下载更新");
            pd.setCancelable(false);
            pd.show();
            new Thread() {
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
                }
            }.start();
        }

        //安装apk
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
        private void LoginMain() {
            Intent intent = new Intent(BaseActivity.this, BaseActivity.class);
            startActivity(intent);
            //结束掉当前的activity
            Tools.finishAll();
        }
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        Log.d("aaaaaaaasetting", "getFileFromServer");
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "jlfood.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }


}
