package com.txd.hzj.wjlp.minetoaty.help;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.HashMap;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/21 11:50
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class NewHelpCenterAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.helpWebView)
    public WebView helpWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("帮助中心");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.new_aty_help_center_li;
    }

    @Override
    protected void initialized() {

        //iv_child.setImageResource(resId);
        WebSettings webSettings = helpWebView.getSettings();



//        int fontSize = (int) getResources().getDimension(R.dimen.chat_nick_text_size);
//        webSettings.setDefaultFontSize(fontSize);
//            settings.setTextSize(WebSettings.TextSize.LARGER);


//      helpWebView.loadData(source, "text/html; charset=UTF-8", null);//这种写法可以正确解码
//      helpWebView.loadDataWithBaseURL(null, source, "text/html", "utf-8", null);


        webSettings.setJavaScriptEnabled(true); // JS支持
        webSettings.setAllowContentAccess(true);// 允许访问内容
        webSettings.setAppCacheEnabled(false);// 允许缓存
        webSettings.setBuiltInZoomControls(false);// 支持缩放
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);;// 缩放至屏幕的大
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        helpWebView.addJavascriptInterface(new MyJavaScript(), "JsUtils");
        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put("Accept_type", "android");
        String loadUrl=Config.OFFICIAL_WEB.replace("api","www");
        helpWebView.loadUrl(loadUrl+"Wap/Article/helpCenter/type/0.html?Accept_type=android",headers);
//        helpWebView.loadUrl("http://www.baidu.com/");

        helpWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void requestData() {

    }
}
