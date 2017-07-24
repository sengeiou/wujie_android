package com.txd.hzj.wjlp.popAty.fgt;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 8:11
 * 描述：企业介绍
 * ===============Txunda===============
 */
public class HatchLeftFgt extends BaseFgt {
    @ViewInject(R.id.notice_details_wv)
    public WebView notice_details_wv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWebView();
    }

    /**
     * 加载网页内容
     */
    @SuppressLint("NewApi")
    private void initWebView() {
        WebSettings webSettings = notice_details_wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // WebView加载web资源
        notice_details_wv.loadUrl("http://game.huanqiu.com/gamenews/2017-07/10942781.html");
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        notice_details_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_hatch_left;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
