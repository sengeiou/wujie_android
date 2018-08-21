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
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/12 0012
 * 时间：下午 5:09
 * 描述：商家资质
 */
public class MellAptitudeAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.aptitude_lv)
    private ListView aptitude_lv;

    private List<String> list;

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
        String aptitudeName = getIntent().getStringExtra("AptitudeName");
        list = new ArrayList<>();

        String[] split = aptitudeName.split(",");
        for (String tempName : split) {
            list.add(tempName);
        }

        apAdapter = new ApAdapter();
        aptitude_lv.setAdapter(apAdapter);

    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
//        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
//        if (requestUrl.contains("license")) {
//            if (ToolKit.isList(map, "data")) {
//                list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
//                apAdapter = new ApAdapter();
//                aptitude_lv.setAdapter(apAdapter);
//            }
//        }
    }

    private class ApAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ApVH apVH;
            String aptitudeName = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(MellAptitudeAty.this).inflate(R.layout.item_aptitude_lv, viewGroup, false);
                apVH = new ApVH();
                ViewUtils.inject(apVH, view);
                view.setTag(apVH);
            } else {
                apVH = (ApVH) view.getTag();
            }

            apVH.apt_type_name_tv.setText(aptitudeName);
            apVH.business_license_tv.setText("已认证");
            apVH.business_license_tv.setTextColor(ContextCompat.getColor(MellAptitudeAty.this, R.color.theme_color));
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
