package com.txd.hzj.wjlp.popAty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.companyDevelop.CompanyDevelopPst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.popAty.fgt.HatchLeftFgt;
import com.txd.hzj.wjlp.popAty.fgt.HatchRightFgt;

import java.util.ArrayList;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 8:10
 * 描述：上市孵化
 * ===============Txunda===============
 */
public class HatchDetailsAty extends BaseAty {

    /**
     * 请红包
     */
    @ViewInject(R.id.ws_goods_tv)
    private TextView foot_print_goods_tv;
    @ViewInject(R.id.ws_goods_view)
    private View foot_print_goods_view;
    /**
     * 领券
     */
    @ViewInject(R.id.ws_mell_tv)
    private TextView foot_print_mell_tv;
    @ViewInject(R.id.ws_mell_view)
    private View foot_print_mell_view;

    @ViewInject(R.id.company_info_wv)
    private WebView company_info_wv;

    private CompanyDevelopPst companyDevelopPst;
    private String company_id = "";
    private String merchant_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.ws_title_layout);
        setTvAndViewStyle(0);
    }


    @Override
    @OnClick({R.id.ws_title_left_layout, R.id.ws_title_right_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ws_title_left_layout:// 领红包
                setTvAndViewStyle(0);
                break;
            case R.id.ws_title_right_layout:// 领券
                Bundle bundle = new Bundle();
                bundle.putString("mell_id",merchant_id);
                startActivity(MellInfoAty.class,bundle);
                break;
        }
    }

    private void setTvAndViewStyle(int position) {
        foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == position) {
            foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if ((1 == position)) {
            foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_hatch_details;
    }

    @Override
    protected void initialized() {
        companyDevelopPst = new CompanyDevelopPst(this);
        company_id = getIntent().getStringExtra("company_id");
    }

    @Override
    protected void requestData() {
        companyDevelopPst.companyInfo(company_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("companyInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            company_info_wv.loadDataWithBaseURL(null, data.get("content"), "text/html", "utf-8", null);
            merchant_id = data.get("merchant_id");
        }
    }
}
