package com.txd.hzj.wjlp.minetoAty.help;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.NoScrollWebView;

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
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        helpWebView.getSettings().setUseWideViewPort(true);
//        helpWebView.addJavascriptInterface(new MyJavaScript(), "JsUtils");
        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put("Accept_type", "android");

        helpWebView.loadUrl(Config.OFFICIAL_WEB+"Wap/Article/helpCenter/type/1.html?Accept_type=android",headers);
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
