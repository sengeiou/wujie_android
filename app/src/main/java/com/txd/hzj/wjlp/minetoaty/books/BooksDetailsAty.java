package com.txd.hzj.wjlp.minetoaty.books;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.academy.AcademyPst;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;

import java.util.Map;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：下午 1:11
 * 描述：书院详情
 *
 */
public class BooksDetailsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

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

    @ViewInject(R.id.detalis_wb)
    private WebView detalis_wb;

    private AcademyPst academyPst;
    private String academy_id = "";

    private UserCollectPst collectPst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("详情");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_books_details_hzj;
    }

    @Override
    protected void initialized() {
        academyPst = new AcademyPst(this);
        academy_id = getIntent().getStringExtra("academy_id");
    }

    @Override
    protected void requestData() {

        academyPst.academyInfo(academy_id);

        collectPst = new UserCollectPst(this);
    }

    @Override
    @OnClick({R.id.titlt_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:// 收藏
                String str = titlt_right_tv.getText().toString();
                if (str.equals("收藏")) {
                    collectPst.addCollect("3", academy_id);
                } else {
                    collectPst.delOneCollect("3", academy_id);
                }
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("academyInfo")) {

            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            Map<String, String> data = (Map<String, String>) map.get("data");
            if (data.get("is_collect").equals("1")) {
                titlt_right_tv.setText("已收藏");
            } else {
                titlt_right_tv.setText("收藏");
            }
            titlt_right_tv.setVisibility(View.VISIBLE);
            titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));

            books_title_tv.setText(data.get("title"));

            books_source_tv.setText("来源：" + data.get("source"));

            books_other_info_tv.setText(data.get("collect_num") + "人收藏        " + data.get("page_views") + "人浏览");

            Glide.with(this).load(data.get("logo")).centerCrop()
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .override(Settings.displayWidth, Settings.displayWidth)
                    .into(books_logo_iv);
            L.e("=====数据=====", data.toString());
            detalis_wb.loadDataWithBaseURL(null, data.get("content"), "text/html", "utf-8", null);
            return;
        }
        if (requestUrl.contains("addCollect")) {
            titlt_right_tv.setText("已收藏");
            return;
        }
        if (requestUrl.contains("delOneCollect")) {
            titlt_right_tv.setText("收藏");
        }
    }
}
