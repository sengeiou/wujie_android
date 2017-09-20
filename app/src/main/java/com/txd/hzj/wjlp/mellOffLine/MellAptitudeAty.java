package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/12 0012
 * 时间：下午 5:09
 * 描述：商家资质
 * ===============Txunda===============
 */
public class MellAptitudeAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private MerchantPst merchantPst;
    private String merchant_id = "";

    @ViewInject(R.id.id_card_tv)
    private TextView id_card_tv;
    @ViewInject(R.id.food_license_tv)
    private TextView food_license_tv;
    @ViewInject(R.id.business_license_tv)
    private TextView business_license_tv;
    @ViewInject(R.id.health_license_tv)
    private TextView health_license_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商家资质");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_aptitude_hzj;
    }

    @Override
    protected void initialized() {
        merchant_id = getIntent().getStringExtra("merchant_id");
        merchantPst = new MerchantPst(this);
    }

    @Override
    protected void requestData() {
        merchantPst.license(merchant_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("license")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (data.get("id_card").equals("1")) {
                id_card_tv.setText("已认证");
                id_card_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            } else {
                id_card_tv.setText("未认证");
                id_card_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
            }
            if (data.get("food_license").equals("1")) {
                food_license_tv.setText("已认证");
                food_license_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            } else {
                food_license_tv.setText("未认证");
                food_license_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
            }
            if (data.get("business_license").equals("1")) {
                business_license_tv.setText("已认证");
                business_license_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            } else {
                business_license_tv.setText("未认证");
                business_license_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
            }
            if (data.get("health_license").equals("1")) {
                health_license_tv.setText("已认证");
                health_license_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            } else {
                health_license_tv.setText("未认证");
                health_license_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
            }
        }
    }
}
