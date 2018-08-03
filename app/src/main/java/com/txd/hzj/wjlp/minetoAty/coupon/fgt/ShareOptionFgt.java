package com.txd.hzj.wjlp.minetoAty.coupon.fgt;


import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 9:56
 * 描述：期权券
 */
public class ShareOptionFgt extends BaseFgt {

    @ViewInject(R.id.webview)
    WebView mWebView;

    @ViewInject(R.id.linear_layout)
    LinearLayout linear_layout;

    public ShareOptionFgt() {
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_club_card;
    }

    @Override
    protected void initialized() {

}

    @Override
    protected void requestData() {
        linear_layout.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebChromeClient(new WebChromeClient());
        String url = Config.OFFICIAL_WEB + "Wap/User/myTicket/status/3/p/1/type/1.html";
        Map<String, String> map = new HashMap<>();
        String token = Config.getToken();
        if (TextUtils.isEmpty(token))
            return;
        map.put("token", token);
        mWebView.loadUrl(url, map);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    protected void immersionInit() {

    }
}
