package com.jl.jlapp.mvp.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jl.jlapp.R;
import com.jl.jlapp.mvp.activity.GoodsDetailActivity;
import com.jl.jlapp.mvp.activity.MainActivity;
import com.jl.jlapp.nets.AppFinal;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends Fragment {

    View view;
    WebView webView;
    GoodsDetailActivity goodsDetailActivity;
    int goodsId=0;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        goodsDetailActivity =(GoodsDetailActivity) getActivity();
        goodsId=goodsDetailActivity.goodsId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_goods_detail, container, false);
        webView=view.findViewById(R.id.show_goods_detail_web);
        WebSettings webSettings = webView.getSettings();
        Log.d("aaaaaaaa","webviewGoodsId:"+goodsId);

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

        webView.setWebViewClient(new WebViewClient());

        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");

        //webView.loadUrl("http://117.158.178.202:8000/Demo/demo.html");
        //webView.loadUrl("http://117.158.178.202:8000/JLMIS/junlin/mis/webview/goodsDetails.html");
       // webView.loadUrl("http://117.158.178.202:8000/JLMIS/junlin/mis/webview/goodsDetails.html?goodsId="+goodsId);
        webView.loadUrl(AppFinal.BASEURL+"junlin/mis_jsp/webview/goodsDetails.jsp?goodsId="+goodsId);
        return view;
    }

    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        @JavascriptInterface
        public void clickOnAndroid(String test) {
            Toast.makeText(getActivity(), test, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public int GetLat()
        {
            return goodsId;
        }

        @JavascriptInterface
        public void showToast(String toast)
        {
            // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void hrefToChooseAddressPage()
        {Toast.makeText(getActivity(), "加载了", Toast.LENGTH_LONG).show();
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
