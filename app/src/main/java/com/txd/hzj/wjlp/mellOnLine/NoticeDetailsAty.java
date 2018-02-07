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

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.article.Article;
import com.txd.hzj.wjlp.http.article.ArticlePst;
import com.txd.hzj.wjlp.http.index.IndexPst;
import com.txd.hzj.wjlp.http.message.UserMessagePst;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：上午 10:47
 * 描述：公告详情(无界头条详情)
 * ===============Txunda===============
 */
public class NoticeDetailsAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;


    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;


    @ViewInject(R.id.notice_details_wv)
    public WebView notice_details_wv;

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
            titlt_conter_tv.setText(desc);
            url = getIntent().getStringExtra("href");
            initWebView();
        } else if (4 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            url = getIntent().getStringExtra("href");
            initWebView();
        } else if (5 == from) {
            only_for_top_layout.setVisibility(View.GONE);
            titlt_conter_tv.setText("会员协议");
            articlePst.getArticle("2");
        }
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
            notice_details_wv.loadDataWithBaseURL(null, data != null ? data.get("content") : "", "text/html",
                    "utf-8", null);
            return;
        }
        if (requestUrl.contains("announceInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : "");
            notice_details_wv.loadDataWithBaseURL(null, data != null ? data.get("content") : "", "text/html",
                    "utf-8", null);
            return;
        }
        if (requestUrl.contains("headInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : "");

            books_title_tv.setText(data.get("title"));

            books_source_tv.setText("来源：" + data.get("source"));

            books_other_info_tv.setText("创建时间：" + data.get("create_time") + "\n修改时间：" + data.get("update_time"));

            Glide.with(this).load(data.get("logo")).centerCrop()
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .override(Settings.displayWidth, Settings.displayWidth)
                    .into(books_logo_iv);

            notice_details_wv.loadDataWithBaseURL(null, data != null ? data.get("content") : "", "text/html",
                    "utf-8", null);
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
