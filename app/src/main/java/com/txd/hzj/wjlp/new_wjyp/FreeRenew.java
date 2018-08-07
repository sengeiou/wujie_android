package com.txd.hzj.wjlp.new_wjyp;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/7 10:45
 * 功能描述：免费续约
 */
public class FreeRenew extends BaseAty{

    @ViewInject(R.id.titlt_conter_tv)
    TextView titlt_conter_tv;

    @ViewInject(R.id.webview)
    WebView webview;
    //个人中心请求状态  1代表成功 0代表失败
    private int userCenterCode=1;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_freerenew;
    }

    @Override
    protected void initialized() {
        userCenterCode = getIntent().getIntExtra("userCenterCode", 1);
        if (userCenterCode==1){
            titlt_conter_tv.setText("续约成功");
        }else {
            titlt_conter_tv.setText("续约失败");
        }
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        webview.setWebChromeClient(new WebChromeClient());
        String url= Config.OFFICIAL_WEB+"index.php/Wap/User/success/status/"+userCenterCode;
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient(){
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
