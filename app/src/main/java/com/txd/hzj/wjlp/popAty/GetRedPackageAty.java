package com.txd.hzj.wjlp.popAty;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.welfare.WelfarePst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 红包
 */
public class GetRedPackageAty extends BaseAty {
    /**
     * 倒计时时间
     */
    @ViewInject(R.id.count_down_tv)
    private TextView count_down_tv;

    /**
     * 图片轮播
     */
    @ViewInject(R.id.pic_lin_layout)
    private LinearLayout pic_lin_layout;

    /**
     * 去分享
     */
    @ViewInject(R.id.to_share_rp_layout)
    private LinearLayout to_share_rp_layout;

    /**
     * 下一步
     */
    @ViewInject(R.id.next_pic_tv)
    private TextView next_pic_tv;

    /**
     * 当前位置/图片总数
     */
    @ViewInject(R.id.pic_num_tv)
    private TextView pic_num_tv;

    @ViewInject(R.id.ad_pic_iv)
    private ShapedImageView ad_pic_iv;

    private List<Map<String, String>> pics;
    private int pos = 0;

    private MyCountDown myCountDown;

    private int padding = 0;
    private int pic_w = 0;
    private int pic_h = 0;
    private String bonus_id = "";

    private WelfarePst welfarePst;

    /**
     * 广告标题
     */
    @ViewInject(R.id.bonus_title_tv)
    private TextView bonus_title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        next_pic_tv.setEnabled(false);
        next_pic_tv.setBackgroundResource(R.drawable.shape_rp_un_click_next_tv);

        pic_w = Settings.displayWidth - padding;
        pic_h = pic_w;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pic_w, pic_h);
        ad_pic_iv.setLayoutParams(params);
    }

    private void toCountDown() {
        if (myCountDown == null) {
            long delay_time = Long.parseLong(pics.get(pos).get("delay_time"));
            myCountDown = new MyCountDown(delay_time, 1000);
        }
        myCountDown.start();
    }

    @Override
    @OnClick({R.id.next_pic_tv, R.id.to_share_and_get_rp_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next_pic_tv:
                setPicForAds(pics.get(pos).get("bonus_title"), pics.get(pos).get("bonus_ads"));
                toCountDown();
                break;
            case R.id.to_share_and_get_rp_tv:// 分享
                toShare();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_get_red_package;
    }

    @Override
    protected void initialized() {
        welfarePst = new WelfarePst(this);
        padding = ToolKit.dip2px(this, 64);
        bonus_id = getIntent().getStringExtra("bonus_id");
        pics = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        welfarePst.bonusList(bonus_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("bonusList")) {
            if (ToolKit.isList(map, "data")) {
                pics = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                // 设置第一张图片
                setPicForAds(pics.get(pos).get("bonus_title"), pics.get(pos).get("bonus_ads"));
                // 设置图片总数量和当前位置
                pic_num_tv.setText("1/" + pics.size());
            }
        }
    }

    private void setPicForAds(String bonus_title, String picUrl) {
        bonus_title_tv.setText(bonus_title);
        Glide.with(this).load(picUrl)
                .error(R.drawable.ic_default)
                .centerCrop()
                .override(pic_w, pic_h)
                .placeholder(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(ad_pic_iv) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable>
                            animation) {
                        super.onResourceReady(resource, animation);
                        // 倒计时
                        toCountDown();
                    }
                });
    }

    private class MyCountDown extends CountDownTimer {

        MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            long time = l / 1000;
            String t;
            if (time >= 60) {
                long m = time / 60;
                long s = time % 60;
                t = m + ":" + s;
            } else {
                if (time < 10) {
                    t = "00:0" + time;
                } else {
                    t = "00:" + time;
                }
            }
            count_down_tv.setText(t);
            next_pic_tv.setEnabled(false);
            next_pic_tv.setBackgroundResource(R.drawable.shape_rp_un_click_next_tv);
        }

        @Override
        public void onFinish() {
            next_pic_tv.setEnabled(true);
            next_pic_tv.setBackgroundResource(R.drawable.shape_rp_click_next_tv);
            count_down_tv.setText("00:00");
            L.e("=====下标=====", String.valueOf(pos));
            if (pos < pics.size() - 1) {
                pos += 1;
                pic_num_tv.setText((pos + 1) + "/" + pics.size());
            } else {
                to_share_rp_layout.setVisibility(View.VISIBLE);
                pic_lin_layout.setVisibility(View.GONE);
            }
        }
    }
}
