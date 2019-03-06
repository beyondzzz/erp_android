package com.jl.jlapp.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.mvp.fragment.GoodsDetailFragment;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.popu.ClearHistorySearhPopu;
import com.jl.jlapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressActivity extends Activity {
    @BindView(R.id.my_address_web)
    WebView myAddressWeb;
    @BindView(R.id.address_list_return)
    ImageView addressListReturn;
    Boolean isCheckAddOrEdit = false;
    ClearHistorySearhPopu clearHistorySearhPopu;
    int userId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        Tools.addActivity(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt(AppFinal.USERID, 0);
        if (userId > 0) {
            init();
            addressListReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isCheckAddOrEdit){
                        clearHistorySearhPopu = new ClearHistorySearhPopu(MyAddressActivity.this,  new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearHistorySearhPopu.dismiss();
                                finish();
                            }
                        }, 9);
                        clearHistorySearhPopu.showAtLocation(findViewById(R.id.my_address_page), Gravity.CENTER, 0, 0);
                    }
                    else{
                        Tools.finishAll();
                    }

                }
            });
        } else {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Tools.finishAll();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void init(){
        WebSettings webSettings = myAddressWeb.getSettings();

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

        myAddressWeb.setWebViewClient(new WebViewClient());

        myAddressWeb.addJavascriptInterface(new DemoJavaScriptInterface(), "Android");


       // myAddressWeb.loadUrl("http://117.158.178.202:8000/JLMIS/junlin/mis_jsp/webview/address.jsp?userId="+userId);
        myAddressWeb.loadUrl(AppFinal.BASEURL+"junlin/mis_jsp/webview/address.jsp?userId="+userId);
    }

    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        @JavascriptInterface
        public void clickOnAndroid(String test) {
            Toast.makeText(MyAddressActivity.this, test, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public int GetLat()
        {
            return userId;
        }

        @JavascriptInterface
        public void isCheckAddOrEdit(String isUpdate)
        {
            Log.d("aaaaasssssssssssss","isUpdate:"+isUpdate);
            if("true".equals(isUpdate)){
                isCheckAddOrEdit = true;
            }
            else{
                isCheckAddOrEdit = false;
            }

            Log.d("aaaaasssssssssssss","CheckAddOrEdit:"+isUpdate);
            // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            //Toast.makeText(MyAddressActivity.this, isCheckAddOrEdit+"", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void hrefToChooseAddressPage()
        {Toast.makeText(MyAddressActivity.this, "加载了", Toast.LENGTH_LONG).show();
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
}
