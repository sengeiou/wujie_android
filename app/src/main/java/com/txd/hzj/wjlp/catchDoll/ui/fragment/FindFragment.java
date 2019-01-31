package com.txd.hzj.wjlp.catchDoll.ui.fragment;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.ui.H5WebViewJsInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class FindFragment extends BaseFgt {

    @ViewInject(R.id.webView_show_webv)
    public WebView webView_show_webv;

    private String url;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        url = "https://m.baidu.com";
        initWebView();
    }

    @Override
    protected void immersionInit() {
    }

    private void initWebView() {
        WebSettings webSettings = webView_show_webv.getSettings();
        webSettings.setJavaScriptEnabled(true); // JS支持
        webSettings.setAllowContentAccess(true); // 允许访问内容
        webSettings.setAppCacheEnabled(false); // 允许缓存
        webSettings.setBuiltInZoomControls(false); // 支持缩放
        webSettings.setUseWideViewPort(true); // 使用宽视图窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView_show_webv.addJavascriptInterface(new H5WebViewJsInterface(), H5WebViewJsInterface.H5NAME); // 原生的WebView主要是进行产品展示
        webView_show_webv.getSettings().setDomStorageEnabled(true); // 开启DOM缓存
        webView_show_webv.getSettings().setDatabaseEnabled(true); // 开启（LocalStorage）数据存储
        webView_show_webv.getSettings().setDatabasePath(getActivity().getCacheDir().getAbsolutePath()); // 设置数据缓存路径
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
}
