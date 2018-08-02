package com.txd.hzj.wjlp.mellOnLine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.article.ArticlePst;
import com.txd.hzj.wjlp.http.index.IndexPst;
import com.txd.hzj.wjlp.http.message.UserMessagePst;
import com.txd.hzj.wjlp.view.NoScrollWebView;
import com.txd.hzj.wjlp.view.ScForWebView;

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
//            initWebView();
        } else if (3 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            titlt_conter_tv.setText("服务条款");
            articlePst.getArticle("1");
        } else if (2 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            String desc = getIntent().getStringExtra("desc");
            titlt_conter_tv.setText("无界商店");
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
        }
    }

    /**
     * 加载网页内容
     *
     * @param noScroll 是否使用noScrollWebView，头条界面使用不滑动的，其他界面使用原生WebView
     */
    @SuppressLint("NewApi")
    private void initWebView(boolean noScroll) {
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
            // WebView加载web资源
            details_webview.loadUrl(url);
            // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            details_webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
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

//            ViewGroup.LayoutParams lp = books_logo_iv.getLayoutParams();
//            lp.width = Settings.displayWidth;
//            lp.height = Settings.displayWidth * 3 / 3;
//            books_logo_iv.setLayoutParams(lp);
//            books_logo_iv.setMaxWidth(lp.width);
//            books_logo_iv.setMaxHeight(lp.width  * 3 / 3);

            Glide.with(this).load(data.get("logo"))
//                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
//                    .centerCrop()
                    .dontAnimate()
//                    .override(lp.width, lp.height)
                    .into(books_logo_iv);

            notice_details_wv.loadDataWithBaseURL(null, data != null ? data.get("content") : "", "text/html", "utf-8", null);
        }
    }

    @Override
    public void finish() {
        if (notice_details_wv.canGoBack()) {
            notice_details_wv.goBack();
            return;
        }
        super.finish();
    }
}
