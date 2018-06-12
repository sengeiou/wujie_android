package com.txd.hzj.wjlp.distribution;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：Zyf
 * 功能描述：申请开店
 * 联系方式：无
 */
public class ApplyForShop extends BaseAty {

    //标题
    TextView titlt_conter_tv;

    LinearLayout apply_for_shop_linlayout;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_for_shop;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv=findViewById(R.id.titlt_conter_tv);
        apply_for_shop_linlayout=findViewById(R.id.apply_for_shop_linlayout);

        titlt_conter_tv.setText("申请开店");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        apply_for_shop_linlayout.addView(mWebView);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
