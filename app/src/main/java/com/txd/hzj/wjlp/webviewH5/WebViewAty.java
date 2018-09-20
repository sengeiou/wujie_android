package com.txd.hzj.wjlp.webviewH5;

import android.content.Intent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：浏览器界面(展示商品什么的一些东西）
 */
public class WebViewAty extends BaseAty {

    private String url = Config.OFFICIAL_WEB;

    @ViewInject(R.id.webView_show_webv)
    private WebView webView_show_webv;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_webview;
    }

    @Override
    protected void initialized() {

        // 设置默认加载的Url
        if (url.contains("api")) {
            url = Config.OFFICIAL_WEB.replace("api", "www");
        }
        url = url + "wap";

        // 获取传入的Url
        Intent intent = getIntent();
        if (intent.hasExtra("url")) {
            url = intent.getStringExtra("url");
        }

        initWebView();

    }

    private void initWebView() {
        WebSettings webSettings = webView_show_webv.getSettings();
        webSettings.setJavaScriptEnabled(true); // JS支持
        webSettings.setAllowContentAccess(true); // 允许访问内容
        webSettings.setAppCacheEnabled(false); // 允许缓存
        webSettings.setBuiltInZoomControls(false); // 支持缩放
        webSettings.setUseWideViewPort(true); // 使用宽视图窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView_show_webv.addJavascriptInterface(new H5WebViewJsInterface(this), Constant.H5NAME); // 原生的WebView主要是进行产品展示
        webView_show_webv.getSettings().setDomStorageEnabled(true); // 开启DOM缓存
        webView_show_webv.getSettings().setDatabaseEnabled(true); // 开启（LocalStorage）数据存储
        webView_show_webv.getSettings().setDatabasePath(this.getCacheDir().getAbsolutePath()); // 设置数据缓存路径
        webView_show_webv.setWebChromeClient(new WebChromeClient());

        // WebView加载web资源头Head
        Map<String, String> map = new HashMap<>();
        map.put("token", Config.getToken());
        map.put("device", "Android");
        webView_show_webv.loadUrl(url, map);

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView_show_webv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void requestData() {

    }
}
