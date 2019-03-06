package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.DataCleanManager;
import com.jl.jlapp.utils.NotificationsUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessageSettingsActivity extends Activity{
    private  String TAG="MessageSettingsActivityLog";

    @BindView(R.id.notification_state)
    TextView notificationState;
    @BindView(R.id.customer_service_webview)
    WebView customerServiceWebview;
    @BindView(R.id.activity_message_setting_page)
    LinearLayout activityMessageSettingPage;

    ClearHistorySearhPopu clearHistorySearhPopu;
    boolean notificationEnabled=false;
    //加载框
    private ProgressDialog progressDialog;
    int userId = -1;//获取App中存储的用户id
    String userPhone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_settings);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, -1);
        userPhone = sharedPreferences.getString(AppFinal.USERPHONE,"");
        if (userId > 0&&!"".equals(userPhone)) {
            initWebView();
            notificationEnabled=NotificationsUtils.isNotificationEnabled(this);
            Log.d(TAG,"通知栏权限状态:"+ NotificationsUtils.isNotificationEnabled(this));
            if(notificationEnabled){
                notificationState.setText("去设置");
            }else{
                notificationState.setText("去开启");
            }
        } else {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public void initWebView(){
        WebSettings webSettings = customerServiceWebview.getSettings();

        //WebView是否保存表单数据，默认值true。
        webSettings.setSaveFormData(false);
        //是否允许WebView度超出以概览的方式载入页面，默认false。即缩小内容以适应屏幕宽度。该项设置在内容宽度超出WebView控件的宽度时生效，例如当getUseWideViewPort() 返回true时。
        webSettings.setLoadWithOverviewMode (true);
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
        //不使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        customerServiceWebview.setWebViewClient(new WebViewClient());

        customerServiceWebview.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");


        customerServiceWebview.loadUrl(AppFinal.BASE_IP+":8000/qiqiim-server/loginMoberl?account="+userPhone);
        //customerServiceWeb.loadUrl("http://117.158.178.202:8000/JLMIS/junlin/mis_jsp/webview/address.jsp?userId=22");
    }

    @OnClick({R.id.icon_return,R.id.notification_state_rela,R.id.clear_all_message})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_return:
                finish();
                break;
            case R.id.notification_state_rela:
                requestPermission();
                break;
            case R.id.clear_all_message:
                clearHistorySearhPopu = new ClearHistorySearhPopu(this, itemclick, 11);
                clearHistorySearhPopu.showAtLocation(findViewById(R.id.activity_message_setting_page), Gravity.CENTER, 0, 0);
                break;
        }
    }

    /**
     * 清除全部消息的确认事件
     */
    public View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //开启加载框
            buildProgressDialog();
            clearHistorySearhPopu.dismiss();
            deldectMessage(userId);
            clearWebViewCache();

            customerServiceWebview.loadUrl("javascript:deletelog()");

        }
    };

    /**
     * 加载框
     */
    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("清除中");
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("aaaaaaaaaaaaaaaaaaaa","我被销毁了");
        //销毁Webview
        activityMessageSettingPage.removeView(customerServiceWebview);
        customerServiceWebview.destroy();
        //清除网页访问留下的缓存
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        customerServiceWebview.clearCache(true);

        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        customerServiceWebview.clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        customerServiceWebview.clearFormData();
    }

    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        @JavascriptInterface
        public void clickOnAndroid(String test) {
            Toast.makeText(MessageSettingsActivity.this, test, Toast.LENGTH_SHORT).show();
        }

       /* @JavascriptInterface
        public int GetLat()
        {
            return userPhone;
        }*/

        @JavascriptInterface
        public void showToast(String toast)
        {
            // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            Toast.makeText(MessageSettingsActivity.this, toast, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void hrefToChooseAddressPage()
        {//Toast.makeText(MessageSettingsActivity.this, "加载了", Toast.LENGTH_LONG).show();
            Log.d("aaaaaaaaaaaaaaaaaa","清除了");
            // webView.loadUrl("http://117.158.178.202:8000/JLMIS/junlin/mis/webview/addressModify.html");
        }
    }

    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("webview", message);
            result.confirm();
            return true;
        }
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //清除网页访问留下的缓存
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        customerServiceWebview.clearCache(true);

        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        customerServiceWebview.clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        customerServiceWebview.clearFormData();

       /* //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());*/

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
       /* //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }*/
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    //跳转到自身的设置界面
    protected void requestPermission() {
        // TODO Auto-generated method stub
        // 6.0以上系统才可以判断权限

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            // 进入设置系统应用权限界面
           /* Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);*/
            Uri packageURI = Uri.parse("package:" + getPackageName());
            Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
            startActivity(intent);
            return;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
            // 进入设置系统应用权限界面
         /*   Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);*/
            Uri packageURI = Uri.parse("package:" + getPackageName());
            Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
            startActivity(intent);
            return;
        }
        return;
    }

    //根据用户id删除所有消息
    public void deldectMessage(Integer userId) {
        Net.get().deldectMessage(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageNoReadNumModel -> {
                    cancelProgressDialog();
                    if (messageNoReadNumModel.getCode() == 200) {
                        Toast.makeText(this,"清除成功",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, messageNoReadNumModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    cancelProgressDialog();
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
}
