package com.txd.hzj.wjlp.webviewH5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.rsa.Base64Utils;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.Pay;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.mellonLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.wxapi.GetPrepayIdTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：浏览器界面(展示商品什么的一些东西）
 */
public class WebViewAty extends BaseAty {

    private String url = Config.OFFICIAL_WEB;

    @ViewInject(R.id.webView_show_webv)
    private WebView webView_show_webv;

    // 标题栏
    @ViewInject(R.id.webView_title_layout)
    public LinearLayout webView_title_layout;
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private String payType; // 支付类型
    private String type; // 返回Type
    private String order_id; // 订单id
    private String discount_type; // 折扣类型

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_webview;
    }

    @Override
    protected void initialized() {

        // 设置默认加载的Url
        if (url.contains("api")) {
            url = Config.OFFICIAL_WEB.replace("api", "www");
        }
        url = url + "wap";

        // 获取传入的Url
        Intent intent = getIntent();
        if (intent.hasExtra("url")) {
            String urlTemp = intent.getStringExtra("url");
            if (!urlTemp.isEmpty()) {
                url = urlTemp; // 如果存在url字段，并且字段不为空，则赋值给需要加载的url
            }
        }

        wxPayReceiver = new WxPayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("wjyp.wxPay");
        registerReceiver(wxPayReceiver, intentFilter);

        initWebView();

    }

    private void initWebView() {
        WebSettings webSettings = webView_show_webv.getSettings();
        webSettings.setJavaScriptEnabled(true); // JS支持
        webSettings.setAllowContentAccess(true); // 允许访问内容
        webSettings.setAppCacheEnabled(false); // 允许缓存
        webSettings.setBuiltInZoomControls(false); // 支持缩放
        webSettings.setUseWideViewPort(true); // 使用宽视图窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView_show_webv.addJavascriptInterface(new H5WebViewJsInterface(), Constant.H5NAME); // 原生的WebView主要是进行产品展示
        webView_show_webv.getSettings().setDomStorageEnabled(true); // 开启DOM缓存
        webView_show_webv.getSettings().setDatabaseEnabled(true); // 开启（LocalStorage）数据存储
        webView_show_webv.getSettings().setDatabasePath(this.getCacheDir().getAbsolutePath()); // 设置数据缓存路径
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

    @Override
    protected void requestData() {
    }

    @OnClick({R.id.webView_title_layout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_be_back_iv:
                this.finish();
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject data = jsonObject.getJSONObject("data");

            // 微信支付
            if (requestUrl.contains("getJsTine")) {
                GetPrepayIdTask wxPay = new GetPrepayIdTask(WebViewAty.this, data.getString("sign"), data.getString("appid"),
                        data.getString("nonce_str"), data.getString("package"), data.getString("time_stamp"), data.getString("prepay_id"),
                        data.getString("mch_id"), "");
                wxPay.execute();
            }

            // 支付宝支付
            if (requestUrl.contains("getAlipayParam")) {
                AliPay aliPay = new AliPay(data.getString("pay_string"), new AliPayCallBack() {
                    @Override
                    public void onComplete() {
                        showToast("支付成功！");
                        Pay.findPayResult(order_id, type, WebViewAty.this);
                    }

                    @Override
                    public void onFailure() {
                        showToast("支付失败！");
                        Pay.findPayResult(order_id, type, WebViewAty.this);
                    }

                    @Override
                    public void onProcessing() {
                    }
                });
                aliPay.pay();
            }

            if (requestUrl.contains("findPayResult")) {
                paySuccess(jsonObject);
            }
        } catch (JSONException e) {
        }
    }

    /**
     * 微信支付以及回调
     */
    private WxPayReceiver wxPayReceiver;

    private class WxPayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 5);
            if (errCode == 0) {
                showToast("支付成功");
            } else {
                showToast("支付失败");
            }
            Pay.findPayResult(order_id, "11", WebViewAty.this);
        }
    }

    /**
     * 支付成功后执行方法
     */
    private void paySuccess(JSONObject jsonObject) {
        L.e("findPayResult:" + jsonObject.toString());
        try {
            JSONObject data = jsonObject.has("data") ? jsonObject.getJSONObject("data") : null;
            String order_sn = data.has("order_sn") ? data.getString("order_sn") : "";
            if (order_sn.isEmpty()) {
                return;
            }
            String urlStr = Config.OFFICIAL_WEB;
            if (urlStr.contains("api")) { // 正式版的情况下将api替换为www
                urlStr = urlStr.replace("api", "www");
            }
//          http://www.wujiemall.com/Wap/Pay/pay_back/order/153232656966415.html
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(urlStr);
            stringBuffer.append("Wap/Pay/pay_back/order/");
            stringBuffer.append(order_sn);
            stringBuffer.append(".html");
            url = stringBuffer.toString();
            initWebView();
        } catch (JSONException e) {
        }

    }

    /**
     * 初始化ImagePicker
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(false);// 不裁剪
        imagePicker.setMultiMode(false);// 单选
        imagePicker.setShowCamera(false);// 不显示拍照按钮
    }

    // ======================================== H5交互接口 ========================================
    class H5WebViewJsInterface {

        /**
         * H5调用付款（微信支付和支付宝支付）
         *
         * @param resultJson
         */
        @JavascriptInterface
        public void payForApplication(String resultJson) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                payType = jsonObject.has("pay_type") ? jsonObject.getString("pay_type") : "";
                type = jsonObject.has("type") ? jsonObject.getString("type") : "";
                order_id = jsonObject.has("order_id") ? jsonObject.getString("order_id") : "";
                discount_type = jsonObject.has("discount_type") ? jsonObject.getString("discount_type") : "";

                if ("WeChat".equals(payType)) { // 微信支付
                    Pay.getJsTine(order_id, discount_type, type, WebViewAty.this);
                } else if ("Alipay".equals(payType)) { // 支付宝支付
                    Pay.getAlipayParam(order_id, discount_type, type, WebViewAty.this);
                } else {
                    L.e("payForApplication show: other pay type");
                }
            } catch (JSONException e) {
                L.e("payForApplication Exception:" + e.toString());
            }
        }

        /**
         * H5调用登录
         *
         * @param url
         */
        @JavascriptInterface
        public void toLogin(String url) { // window.phone.toLogin(String url)
            if (!url.isEmpty()) { // 如果Url不为空，那么直接解码并添加到本地共享存储
                byte[] decode = Base64Utils.decode(url);
                PreferencesUtils.putString(WebViewAty.this, "NoticeDetailsUrl", new String(decode));
            }
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            startActivity(LoginAty.class, bundle);
            WebViewAty.this.finish();
        }

        /**
         * H5支付结果返回首页
         */
        @JavascriptInterface
        public void toOfflinePage() {
            WebViewAty.this.finish();
        }

        /**
         * H5调用分享
         *
         * @param content
         */
        @JavascriptInterface
        public void toShare(String content) {
            L.e("NoticeDetailsJsInterface.toShare" + content);
            try {
                JSONObject jsonObject = new JSONObject(content);
                String goodsName = jsonObject.has("goodsName") ? jsonObject.getString("goodsName") : "";
                String share_img = jsonObject.has("share_img") ? jsonObject.getString("share_img") : "";
                String share_url = jsonObject.has("share_url") ? jsonObject.getString("share_url") : "";
                String share_content = jsonObject.has("share_content") ? jsonObject.getString("share_content") : "";
                String id = jsonObject.has("id") ? jsonObject.getString("id") : "0";
                WebViewAty.this.toShare(goodsName, share_img, share_url, share_content, id, "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * 跳转至个人中心
         */
        @JavascriptInterface
        public void toPersonalCenter() {
            backMain(3);
            WebViewAty.this.finish();
        }

        /**
         * 打开相机
         */
        @JavascriptInterface
        public void openCamera() {
            initImagePicker();
            Intent intent = new Intent(WebViewAty.this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 直接调取相机
            startActivityForResult(intent, 121);
//            showToast("调取相机，直接拍照");
//            openPhotoFolder();
        }

        /**
         * 打开相册选择照片
         */
        @JavascriptInterface
        public void openPhotoFolder() {
            initImagePicker();
            Intent i = new Intent(WebViewAty.this, ImageGridActivity.class);
            startActivityForResult(i, 121);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != wxPayReceiver) {
            unregisterReceiver(wxPayReceiver);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                    switch (requestCode) {
                        case 121:
                            try {
                                File file = new File(pic_path);
                                webView_show_webv.loadUrl("JavaScript:TakePhoto(" + file + ")");
//                                L.e("File:" + file.getPath());
//                                L.e("File:" + file);
                            } catch (Exception e) {
                                L.e("File Exception:" + e.toString());
                                showToast("未找到图片文件，请重新选择。");
                            }
                            break;
                    }
                }
            } else {
                showErrorTip("没有返回任何数据。。");
            }
        }
    }
}
