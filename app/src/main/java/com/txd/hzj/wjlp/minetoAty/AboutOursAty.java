package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.BuildConfig;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.article.ArticlePst;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/18 0018
 * 时间：下午 1:32
 * 描述：过于我们
 * ===============Txunda===============
 */
public class AboutOursAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 当前版
     */
    @ViewInject(R.id.app_version_tv)
    private TextView app_version_tv;
    /**
     * 版权
     */
    @ViewInject(R.id.bottom_top_tv)
    private TextView bottom_top_tv;
    /**
     * 公司
     */
    @ViewInject(R.id.bottom_bottom_tv)
    private TextView bottom_bottom_tv;

    private ArticlePst artickePst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("关于我们");
        app_version_tv.setText("当前版本:v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_about_ours_li;
    }

    @Override
    protected void initialized() {
        artickePst = new ArticlePst(this);
    }

    @Override
    protected void requestData() {
        artickePst.aboutUs();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String,Object> map = GsonUtil.GsonToMaps(jsonStr);
        Map<String,String> data = (Map<String, String>) map.get("data");
        bottom_top_tv.setText(data.get("copyright"));
        bottom_bottom_tv.setText(data.get("company_name"));
    }
}
