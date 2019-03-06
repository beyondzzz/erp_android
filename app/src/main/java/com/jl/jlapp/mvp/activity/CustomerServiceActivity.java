package com.jl.jlapp.mvp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.nets.AppFinal;
import com.jl.jlapp.nets.Net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.sephiroth.android.library.easing.Linear;

/**
 * Created by 柳亚婷 on 2018/2/9 0009.
 */

public class CustomerServiceActivity extends Activity {

    @BindView(R.id.customer_service_web)
    WebView customerServiceWeb;
    /*@BindView(R.id.customer_service_return)
    ImageView customerServiceReturn;*/
    @BindView(R.id.root_linear)
    LinearLayout rootLinear;

    String userPhone="";
    String userName="";
    int userId=0;
    int isVip=0;
    int goodsId=0;
    int isPresell=0;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private Uri imageUri;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppFinal.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        userPhone = sharedPreferences.getString(AppFinal.USERPHONE,"");
        userName=sharedPreferences.getString(AppFinal.USERNAME,"");
        isVip=sharedPreferences.getInt(AppFinal.ISVIP,0);
        userId=sharedPreferences.getInt(AppFinal.USERID,0);
        Log.d("aaaaaweb","phone:"+userPhone);
        //if (userPhone!=null&&!"".equals(userPhone)) {
            init();
           /* customerServiceReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "您还未登录，请先登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("aaaaaaaaaaaaaaaaaaaa","我被销毁了");
       //销毁Webview
        rootLinear.removeView(customerServiceWeb);
        customerServiceWeb.destroy();
        //清除网页访问留下的缓存
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        customerServiceWeb.clearCache(true);

        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        customerServiceWeb.clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        customerServiceWeb.clearFormData();
    }

    public void init(){
        WebSettings webSettings = customerServiceWeb.getSettings();

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webSettings.setLoadWithOverviewMode(true);
        //  webView.setWebChromeClient(new MyWebChromeClient());

        customerServiceWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                //注意：super句话一定要删除，或者注释掉，否则又走handler.cancel()默认的不支持https的了。
                //super.onReceivedSslError(view, handler, error);
                //handler.cancel(); // Android默认的处理方式
                //handler.handleMessage(Message msg); // 进行其他处理
                handler.proceed(); // 接受所有网站的证书
            }
        });
        customerServiceWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                takePhoto();
                return true;
            }
            // For Android  < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }
            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }
        });

        customerServiceWeb.setDownloadListener(new MyWebViewDownLoadListener());

        customerServiceWeb.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");

        //customerServiceWeb.loadUrl("http://117.158.178.202:8000/qiqiim-server/loginMoberl?account="+userPhone);
        //customerServiceWeb.loadUrl("http://117.158.178.202:8000/Kf/chat.html");
        //customerServiceWeb.loadUrl("http://172.16.8.39:8080/Kf/chat.html");
         customerServiceWeb.loadUrl(AppFinal.BASEURL_JLKF+"chatClient?loginName="+userPhone+"&userName="+userName+"&isVip="+isVip+"&clientId="+userId+"");
        //customerServiceWeb.loadUrl("http://172.16.8.42:8080/chatClient?loginName=1&userName=1&isVip=1&clientId=5");
        //customerServiceWeb.loadUrl("https://172.16.8.42:8443/chat.jsp");
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,

                                    long contentLength) {

            Log.i("tag", "url="+url);

            Log.i("tag", "userAgent="+userAgent);

            Log.i("tag", "contentDisposition="+contentDisposition);

            Log.i("tag", "mimetype="+mimetype);

            Log.i("tag", "contentLength="+contentLength);

            Uri uri = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                Log.e("Mr.Kang", "onActivityResult: "+result);
                if (result == null) {
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                    Log.e("Mr.Kang", "onActivityResult: "+imageUri);
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }


            }
        }
    }
    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }

    private void takePhoto() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        CustomerServiceActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }


    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        @JavascriptInterface
        public void clickOnAndroid(String goodId,String ispresell) {
            //Toast.makeText(CustomerServiceActivity.this, goodsId, Toast.LENGTH_SHORT).show();
            goodsId=Integer.parseInt(goodId);
           // goodsId=102;
            isPresell=Integer.parseInt(ispresell);
            //isPresell=1;
            //是预售
            if(isPresell==1) {
                getPreSellActivityInformationByGoodsDetailsId(goodsId);
            }
            //不是预售
            else{
                Intent intent = new Intent(CustomerServiceActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goodsId", goodsId);
                intent.putExtra("activityId", 0);
                startActivity(intent);
            }
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
            Toast.makeText(CustomerServiceActivity.this, toast, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void backFunction()
        {
            finish();
        }

        @JavascriptInterface
        public void callDial(String tel)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tel));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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

    //根据商品id获取正在上线的且结束时间最早的预售活动信息
    public void getPreSellActivityInformationByGoodsDetailsId(Integer goodsId) {
        Net.get().getPreSellActivityInformationByGoodsDetailsId(goodsId,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(presellMsg -> {
                    if (presellMsg.getCode() == 200) {
                        if(presellMsg.getResultData()!=null){
                            Intent intent = new Intent(CustomerServiceActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("goodsId", goodsId);
                            intent.putExtra("activityId", presellMsg.getResultData().getId());
                            intent.putExtra("activityName", presellMsg.getResultData().getName());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(this, "预售活动已结束", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, presellMsg.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, AppFinal.NET_ERROR, Toast.LENGTH_SHORT).show();
                });
    }
}
