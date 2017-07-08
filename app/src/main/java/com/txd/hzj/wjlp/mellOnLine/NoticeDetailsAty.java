package com.txd.hzj.wjlp.mellOnLine;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：上午 10:47
 * 描述：公告详情
 * ===============Txunda===============
 */
public class NoticeDetailsAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.notice_details_wv)
    public WebView notice_details_wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText("详情");
        showStatusBar(R.id.title_re_layout);
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
        return R.layout.aty_notice_details;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}