package com.txd.hzj.wjlp.mellonLine.gridClassify.snatch.fgt;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：下午 2:35
 * 描述：图文详情
 */
public class GraphicDetailsFgt extends BaseFgt {


    @ViewInject(R.id.sn_notice_details_wv)
    private WebView sn_notice_details_wv;

    private String webContext;

    public static GraphicDetailsFgt getFgt(String webContext) {
        GraphicDetailsFgt graphicDetailsFgt = new GraphicDetailsFgt();
        graphicDetailsFgt.webContext = webContext;
        return graphicDetailsFgt;
    }

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
        WebSettings webSettings = sn_notice_details_wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // WebView加载web资源
//        sn_notice_details_wv.loadUrl("http://game.huanqiu.com/gamenews/2017-07/10942781.html");
        sn_notice_details_wv.loadDataWithBaseURL(null,webContext,"text/html","utf-8",null);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        sn_notice_details_wv.setWebViewClient(new WebViewClient() {
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
        return R.layout.fgt_graphic_details;
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
