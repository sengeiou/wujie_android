package com.txd.hzj.wjlp.minetoAty;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 开发者： Txd_WangJJ
 * 创建时间： 2018/3/28 028 0:48:56.
 * 功能描述： 物流查询
 * 联系方式： jingjie.office@qq.com
 */
public class ExpressAtv extends BaseAty {

    @ViewInject(R.id.express_webView)
    private WebView express_webView;
    @ViewInject(R.id.express_loading_pb)
    private ProgressBar express_loading_pb;

    // https://m.kuaidi100.com/index_all.html?type=[快递公司编码]&postid=[快递单号]&callbackurl=[点击"返回"跳转的地址，非必须]
    private String expressUrl = "https://m.kuaidi100.com/index_all.html?";

    @Override
    protected int getLayoutResId() {
        return R.layout.atv_express;
    }

    @Override
    protected void initialized() {

        String express_company = getIntent().getStringExtra("express_company"); // 快递公司 测试数据：yunda
        String express_nu = getIntent().getStringExtra("express_no"); // 快递单号 测试数据：1300110640157
        L.e("wang", "======>>>>>>> 快递公司代码：" + express_company + "  快递单号：" + express_nu);
        inquireExpress(express_company, express_nu);

    }

    /**
     * 加载WebView，查询快递详情
     * @param express_company 快递公司代码
     * @param express_nu 快递单号
     */
    private void inquireExpress(String express_company, String express_nu) {
        // 拼接Url字符串
        expressUrl = expressUrl + "type=" + express_company + "&postid=" + express_nu;

        express_webView.getSettings().setJavaScriptEnabled(true); // 允许使用JS
        express_webView.setWebChromeClient(new WebChromeClient());
        express_webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 网页开始加载
                express_loading_pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 网页加载结束
                express_loading_pb.setVisibility(View.GONE);
            }
        });
        express_webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不使用缓存，只从网络获取数据
        express_webView.getSettings().setSupportZoom(true); // 支持屏幕缩放
        express_webView.getSettings().setSupportMultipleWindows(true);

        express_webView.loadUrl(expressUrl);
    }

    @Override
    protected void requestData() {
    }

}
