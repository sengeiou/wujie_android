package com.txd.hzj.wjlp.mellonLine;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.rsa.Base64Utils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.Pay;
import com.txd.hzj.wjlp.http.article.ArticlePst;
import com.txd.hzj.wjlp.http.index.IndexPst;
import com.txd.hzj.wjlp.http.message.UserMessagePst;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.minetoaty.PayForAppAty;
import com.txd.hzj.wjlp.view.NoScrollWebView;
import com.txd.hzj.wjlp.view.ScForWebView;
import com.txd.hzj.wjlp.wxapi.GetPrepayIdTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：上午 10:47
 * 描述：公告详情(无界头条详情)
 */
public class NoticeDetailsAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    @ViewInject(R.id.notice_details_wv)
    public NoScrollWebView notice_details_wv;

    @ViewInject(R.id.noticeDetails_ScForWebView)
    public ScForWebView noticeDetails_ScForWebView;

    @ViewInject(R.id.details_webview)
    public WebView details_webview;

    /**
     * 0.消息详情
     * 1.无界头条
     * 2.带url的轮播
     * 3.服务条款
     * 4.扫一扫出来的网页
     */
    private int from = 0;

    private ArticlePst articlePst;

    private UserMessagePst userMessagePst;

    private String url = "http://game.huanqiu.com/gamenews/2017-07/10942781.html";

    private IndexPst indexPst;

    @ViewInject(R.id.only_for_top_layout)
    private LinearLayout only_for_top_layout;

    /**
     * 标题
     */
    @ViewInject(R.id.books_title_tv)
    private TextView books_title_tv;
    /**
     * 来源
     */
    @ViewInject(R.id.books_source_tv)
    private TextView books_source_tv;
    /**
     * 其他信息
     */
    @ViewInject(R.id.books_other_info_tv)
    private TextView books_other_info_tv;

    /**
     * logo
     */
    @ViewInject(R.id.books_logo_iv)
    private ImageView books_logo_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (0 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            titlt_conter_tv.setText("详情");
            String id = getIntent().getStringExtra("id");
            userMessagePst.announceInfo(id);
        } else if (1 == from) {
            only_for_top_layout.setVisibility(View.VISIBLE);
            titlt_conter_tv.setText("无界头条");
            String headlines_id = getIntent().getStringExtra("headlines_id");
            indexPst.headInfo(headlines_id);
        } else if (3 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            titlt_conter_tv.setText("服务条款");
            articlePst.getArticle("1");
        } else if (2 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            String desc = getIntent().getStringExtra("desc");
            titlt_conter_tv.setText("无界优品");
            url = getIntent().getStringExtra("href");
            initWebView(false); // 不使用noScrollWebView
        } else if (4 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            url = getIntent().getStringExtra("href");
            initWebView(true); // 使用noScrollWebView
        } else if (5 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            titlt_conter_tv.setText("会员协议");
            articlePst.getArticle("2");
        } else if (6 == from) { //商家推荐
            only_for_top_layout.setVisibility(View.GONE);
            String desc = getIntent().getStringExtra("desc");
            titlt_conter_tv.setText("无界优品");
            url = getIntent().getStringExtra("href");
            initWebView(false); // 不使用noScrollWebView
        }

        wxPayReceiver = new WxPayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("wjyp.wxPay");
        registerReceiver(wxPayReceiver, intentFilter);

    }

    /**
     * 加载网页内容
     *
     * @param noScroll 是否使用noScrollWebView，头条界面使用不滑动的，其他界面使用原生WebView
     */
    @SuppressLint("NewApi")
    private void initWebView(boolean noScroll) {
        if (url.isEmpty()) { // 如果Url为空，则直接等待两秒退出界面
            showToast("链接地址为空，请重新检查");
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    NoticeDetailsAty.this.notice_details_wv_back();
                }
            }.start();
            return;
        }


        WebSettings webSettings = null;
        if (noScroll) { // 如果使用noScrollWebView则获取notice_details_wv进行设置
            webSettings = notice_details_wv.getSettings(); // 无滑动的主要是进行无界头条展示
        } else { // 否则的话设置原生的控件
            webSettings = details_webview.getSettings(); // 原生的WebView主要是进行产品展示
        }
        webSettings.setJavaScriptEnabled(true); // JS支持
        webSettings.setAllowContentAccess(true); // 允许访问内容
        webSettings.setAppCacheEnabled(false); // 允许缓存
        webSettings.setBuiltInZoomControls(false); // 支持缩放
        webSettings.setUseWideViewPort(true); // 使用宽视图窗口
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        details_webview.addJavascriptInterface(new NoticeDetailsJsInterface(), Constant.H5NAME); // 原生的WebView主要是进行产品展示
        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        details_webview.getSettings().setDomStorageEnabled(true);
        details_webview.getSettings().setDatabaseEnabled(true);
        details_webview.getSettings().setDatabasePath(NoticeDetailsAty.this.getApplicationContext().getCacheDir().getAbsolutePath());

        if (noScroll) { // 使用noScrollWebView
            noticeDetails_ScForWebView.setVisibility(View.VISIBLE); // 显示noScrollWebView
            details_webview.setVisibility(View.GONE); // 隐藏原生WebView
            // WebView加载web资源
            notice_details_wv.loadUrl(url);
            // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            notice_details_wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
        } else { // 使用原生的WebView
            noticeDetails_ScForWebView.setVisibility(View.GONE); // 隐藏noScrollWebView
            details_webview.setVisibility(View.VISIBLE); // 显示原生WebView
            details_webview.setWebChromeClient(new WebChromeClient());
//            details_webview.setWebViewClient(new MyWebViewClient());

            // WebView加载web资源
//            if (6 == from) {
            Map<String, String> map = new HashMap<>();
            map.put("token", Config.getToken());
            map.put("device", "Android");
            details_webview.loadUrl(url, map);
//            } else {
//                details_webview.loadUrl(url);
//            }

            // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            details_webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
//                    http://test2.wujiemall.com/Wap/OfflineStore/offlineShop/merchant_id/12/invite_code/7wIv3xe4.html
                    return true;
                }
            });

        }
        if (from == 4) {
            WebChromeClient wvcc = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    titlt_conter_tv.setText(title);
                }
            };
            notice_details_wv.setWebChromeClient(wvcc);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_notice_details;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 0);
        articlePst = new ArticlePst(this);
        userMessagePst = new UserMessagePst(this);
        indexPst = new IndexPst(this);
    }

    @Override
    public void onBackPressed() {
        if (details_webview.canGoBack()) {
            details_webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("getArticle")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : "");
            notice_details_wv.loadDataWithBaseURL(null, data != null ? data.get("content") : "", "text/html", "utf-8", null);
            return;
        }
        if (requestUrl.contains("announceInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : "");
            notice_details_wv.loadDataWithBaseURL(null, data != null ? data.get("content") : "", "text/html", "utf-8", null);
            return;
        }
        if (requestUrl.contains("headInfo")) {

            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : "");

            books_title_tv.setText(data.get("title"));

            books_source_tv.setText("来源：" + data.get("source"));

            books_other_info_tv.setText("创建时间：" + data.get("create_time") + "\n修改时间：" + data.get("update_time"));


            Glide.with(this).load(data.get("logo"))
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .dontAnimate()
                    .into(books_logo_iv);

            notice_details_wv.loadDataWithBaseURL(null, data != null ? data.get("content") : "", "text/html", "utf-8", null);
        }
    }

    public void notice_details_wv_back() {
        if (notice_details_wv.canGoBack()) {
            notice_details_wv.goBack();
            return;
        }
        super.finish();
    }

    String payType;
    String type;
    String order_id;
    String discount_type;

    /**
     * H5交互接口
     */
    class NoticeDetailsJsInterface {

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
                    Pay.getJsTine(order_id, discount_type, type, new MyBaseView());
                } else if ("Alipay".equals(payType)) { // 支付宝支付
                    Pay.getAlipayParam(order_id, discount_type, type, new MyBaseView());
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
                PreferencesUtils.putString(NoticeDetailsAty.this, "NoticeDetailsUrl", new String(decode));
            }
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            startActivity(LoginAty.class, bundle);
        }

        /**
         * H5支付结果返回首页
         */
        @JavascriptInterface
        public void toOfflinePage() {
            NoticeDetailsAty.this.finish();
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
                NoticeDetailsAty.this.toShare(goodsName, share_img, share_url, share_content, id, "1");
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
            NoticeDetailsAty.this.finish();
        }
    }

    class MyBaseView implements BaseView {

        @Override
        public void showDialog() {
        }

        @Override
        public void showDialog(String text) {
        }

        @Override
        public void showContent() {
        }

        @Override
        public void removeDialog() {
        }

        @Override
        public void removeContent() {
        }

        @Override
        public void onStarted() {
        }

        @Override
        public void onCancelled() {
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
        }

        @Override
        public void onException(Exception exception) {
        }

        @Override
        public void onComplete(String requestUrl, String jsonStr) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject data = jsonObject.getJSONObject("data");

                // 微信支付
                if (requestUrl.contains("getJsTine")) {
                    GetPrepayIdTask wxPay = new GetPrepayIdTask(NoticeDetailsAty.this, data.getString("sign"), data.getString("appid"),
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
                            Pay.findPayResult(order_id, "11", new MyBaseView());
                        }

                        @Override
                        public void onFailure() {
                            showToast("支付失败！");
                            Pay.findPayResult(order_id, "11", new MyBaseView());
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

        @Override
        public void onError(String requestUrl, Map<String, String> error) {
        }

        @Override
        public void onErrorTip(String tips) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != wxPayReceiver) {
            unregisterReceiver(wxPayReceiver);
        }
//        url = Config.OFFICIAL_WEB;
//        if (url.contains("api")){
//            url = url.replace("api", "www");
//        }
//        url = url + "Wap/Register/loginOut.html";
//        initWebView(false);
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
            Pay.findPayResult(order_id, "11", new MyBaseView());
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
            initWebView(false);
        } catch (JSONException e) {
        }

    }
}
