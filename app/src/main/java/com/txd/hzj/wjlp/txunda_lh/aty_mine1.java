package com.txd.hzj.wjlp.txunda_lh;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.mellInto.MellIntoListAty;
import com.txd.hzj.wjlp.txunda_lh.http.Recommending;
import com.txd.hzj.wjlp.wjyp.PushMerchantAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * by Txunda_LH on 2018/1/30.
 */

public class aty_mine1 extends BaseAty {

    @ViewInject(R.id.im_main)
    private ImageView im_main;
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 广告高度
     */
    private int ads_w = 0;
    private int ads_h = 0;

    @OnClick({R.id.im1, R.id.im2})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.im1:
                startActivity(MellIntoListAty.class, null);
                break;
            case R.id.im2:
                startActivity(PushMerchantAty.class, null);
                break;
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_min1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商家推荐");
    }

    @Override
    protected void initialized() {
        // 广告宽高
        ads_h = Settings.displayWidth * 800 / 1242;
        ads_w = Settings.displayWidth;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ads_w, ads_h);
        im1.setLayoutParams(layoutParams);
        im2.setLayoutParams(layoutParams);
    }

    @Override
    protected void requestData() {
        Recommending.advertImg(this);
        showProgressDialog();

    }

    Map<String, String> map;
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
        int h = Settings.displayWidth * Integer.parseInt(list.get(0).get("height")) / Integer.parseInt(list.get(0).get("width"));
        int w = Settings.displayWidth;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
        im_main.setLayoutParams(layoutParams);
        Glide.with(getApplicationContext()).load(list.get(0).get("picture")).into(im_main);
        Glide.with(getApplicationContext()).load(list.get(1).get("picture")).into(im1);
        Glide.with(getApplicationContext()).load(list.get(2).get("picture")).into(im2);

    }
}
