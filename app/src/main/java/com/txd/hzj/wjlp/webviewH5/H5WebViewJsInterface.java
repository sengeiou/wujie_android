package com.txd.hzj.wjlp.webviewH5;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020下午 12:04
 * <br>功能描述：H5交互接口（暂时是除了线下店铺付款登录等的功能）
 */
public class H5WebViewJsInterface {

    private WebViewAty activity;

    public H5WebViewJsInterface(Activity activity) {
        this.activity = (WebViewAty) activity;
    }

    @JavascriptInterface
    public void showToast(String msg) {
    }

}
