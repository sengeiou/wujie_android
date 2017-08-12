package com.txd.hzj.wjlp.popAty;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;

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

    private List<Integer> pics;
    private int pos = 0;

    private MyCountDown myCountDown;

    private int padding = 0;
    private int pic_w = 0;
    private int pic_h = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        next_pic_tv.setEnabled(false);
        next_pic_tv.setBackgroundResource(R.drawable.shape_rp_un_click_next_tv);

        pic_w = Settings.displayWidth - padding;
        pic_h = pic_w;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pic_w, pic_h);
        ad_pic_iv.setLayoutParams(params);

        // 设置第一张图片
        ad_pic_iv.setImageResource(pics.get(pos));
        // 设置图片总数量和当前位置
        pic_num_tv.setText("1/" + pics.size());
        // 倒计时
        toCountDown();

    }

    private void toCountDown() {
        if (myCountDown == null) {
            myCountDown = new MyCountDown(10000, 1000);
        }
        myCountDown.start();
    }

    @Override
    @OnClick({R.id.next_pic_tv, R.id.to_share_and_get_rp_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next_pic_tv:
                ad_pic_iv.setImageResource(pics.get(pos));
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
        padding = ToolKit.dip2px(this, 64);
        pics = new ArrayList<>();
        pics.add(R.drawable.icon_temp_banner);
        pics.add(R.drawable.icon_temp_banner);
        pics.add(R.drawable.icon_temp_banner);
        pics.add(R.drawable.icon_temp_banner);
    }

    @Override
    protected void requestData() {

    }

    private class MyCountDown extends CountDownTimer {

        public MyCountDown(long millisInFuture, long countDownInterval) {
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
