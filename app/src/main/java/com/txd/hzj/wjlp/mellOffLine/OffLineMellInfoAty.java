package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：上午 10:07
 * 描述：线下商家详情
 * ===============Txunda===============
 */
public class OffLineMellInfoAty extends BaseAty {

    /**
     * AppBarLAyout
     */
    @ViewInject(R.id.mell_info_app_bar_layout)
    private AppBarLayout app_bar_layout;

    /**
     * CollapsingToolbarLayout
     */
    @ViewInject(R.id.mell_info_collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    /**
     * ToolBar
     */
    @ViewInject(R.id.mell_info_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.mell_info_head_layout)
    private LinearLayout head_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 沉浸式
        showStatusBar(R.id.mell_info_toolbar);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 5) {
                    collapsing_toolbar_layout.setTitle("好收成超市");
                } else {
                    collapsing_toolbar_layout.setTitle(" ");
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_off_line_mell_info;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
