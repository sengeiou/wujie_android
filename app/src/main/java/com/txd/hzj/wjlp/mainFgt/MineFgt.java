package com.txd.hzj.wjlp.mainFgt;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.minetoAty.AboutOursAty;
import com.txd.hzj.wjlp.minetoAty.SetAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.flowlayout.WaveView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:54
 * 描述：我的
 * ===============Txunda===============
 */
public class MineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
    /**
     * 标题栏
     */
    @ViewInject(R.id.mine_title_layout)
    public RelativeLayout mine_title_layout;
    private int allHeight = 0;
    /**
     * 我的背景
     * */
    @ViewInject(R.id.rel_head_back)
    public RelativeLayout rel_head_back;
    /**
     * 设置
     * */
    @ViewInject(R.id.tv_set)
    TextView tv_set;
    @ViewInject(R.id.off_line_to_change_sc)
    private ObservableScrollView off_line_to_change_sc;
    /**
     * 波浪视图
     * */
    @ViewInject(R.id.wave)
    private WaveView wave;
    /**
     * 关于
     * */
    @ViewInject(R.id.rel_mine_about)
    private RelativeLayout rel_mine_about;
    public MineFgt() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mine_title_layout.setBackgroundColor(Color.TRANSPARENT);
        allHeight = Settings.displayWidth * 2 / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        rel_head_back.setLayoutParams(layoutParams);
        // 改变标题栏颜色
        off_line_to_change_sc.setScrollViewListener(MineFgt.this);
        wave.startAnim();
        initEvent();
    }

    private void initEvent() {
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SetAty.class));
            }
        });
        rel_mine_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutOursAty.class));
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mine;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.mine_title_layout);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            mine_title_layout.setBackgroundColor(Color.TRANSPARENT);//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= allHeight) {
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            mine_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));
        } else {
            mine_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
        }
        immersionInit();
    }
}
