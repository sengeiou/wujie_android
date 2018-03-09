package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;

import java.util.ArrayList;
import java.util.List;
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

    @ViewInject(R.id.aptitude_lv)
    private ListView aptitude_lv;

    private List<Map<String, String>> list;

    private ApAdapter apAdapter;

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
        list = new ArrayList<>();
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
            if (ToolKit.isList(map, "data")) {
                list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                apAdapter = new ApAdapter();
                aptitude_lv.setAdapter(apAdapter);
            }
        }
    }

    private class ApAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ApVH apVH;
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(MellAptitudeAty.this).inflate(R.layout.item_aptitude_lv, viewGroup, false);
                apVH = new ApVH();
                ViewUtils.inject(apVH, view);
                view.setTag(apVH);
            } else {
                apVH = (ApVH) view.getTag();
            }

            if (map.get("status").equals("1")) {
                apVH.business_license_tv.setText("已认证");
                apVH.business_license_tv.setTextColor(ContextCompat.getColor(MellAptitudeAty.this,
                        R.color.theme_color));
            } else {
                apVH.business_license_tv.setText("未认证");
                apVH.business_license_tv.setTextColor(ContextCompat.getColor(MellAptitudeAty.this,
                        R.color.gray_text_color));
            }
            apVH.apt_type_name_tv.setText(map.get("name"));
            return view;
        }

        class ApVH {
            @ViewInject(R.id.apt_type_name_tv)
            private TextView apt_type_name_tv;

            @ViewInject(R.id.business_license_tv)
            private TextView business_license_tv;
        }

    }

}
