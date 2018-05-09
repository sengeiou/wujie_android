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
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.L;
import com.baidu.mapapi.map.Text;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.new_wjyp.http.Order;

/**
 * 开发者： WangJJ
 * 创建时间： 2018/3/28 028 0:48:56.
 * 功能描述： 物流查询
 * 联系方式： jingjie.office@qq.com
 */
public class ExpressAtv extends BaseAty {

    /**
     * 网页WebView控件
     */
    @ViewInject(R.id.express_webView)
    private WebView express_webView;
    /**
     * 加载进度条，环形方式
     */
    @ViewInject(R.id.express_loading_pb)
    private ProgressBar express_loading_pb;
    /**
     * 页面标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.atv_express;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("物流详情");
        // 订单商品的id
        String order_goods_id = getIntent().getStringExtra("order_goods_id");
        inquireExpress(order_goods_id);
    }

    /**
     * 加载WebView查询订单物流
     *
     * @param order_goods_id 订单中商品的id
     */
    private void inquireExpress(String order_goods_id) {

        // 字符串拼接
        String expressUrl = Config.BASE_URL + "Order/logistics/order_goods_id/" + order_goods_id;

        express_webView.getSettings().setJavaScriptEnabled(true); // 允许使用JS
        express_webView.setWebChromeClient(new WebChromeClient());
        express_webView.setWebViewClient(new WebViewClient() {
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
