package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsEvaluateAty;

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
    @OnClick({R.id.check_all_comment_tv, R.id.mell_reported_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_all_comment_tv:
                Bundle bundle = new Bundle();
                bundle.putInt("from", 2);
                startActivity(GoodsEvaluateAty.class, bundle);
                break;
            case R.id.mell_reported_tv:// 举报商家
                startActivity(MellReportedAty.class, null);
                break;
        }
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
