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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式
        showStatusBar(R.id.mell_info_title_layout);
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
