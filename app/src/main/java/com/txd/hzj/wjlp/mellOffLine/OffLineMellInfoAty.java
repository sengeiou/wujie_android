package com.txd.hzj.wjlp.mellOffLine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    /**
     * 标题
     */
    @ViewInject(R.id.center_tv)
    private TextView center_tv;

    private int type = 0;

    @ViewInject(R.id.other_info_layout)
    private LinearLayout other_info_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式
        showStatusBar(R.id.mell_info_title_layout);
        if (0 == type) {
            center_tv.setText("好收成超市");
            other_info_layout.setBackgroundColor(Color.parseColor("#83CA9D"));
        } else {
            center_tv.setText("康尔馨酒店家纺");
            other_info_layout.setBackgroundColor(Color.parseColor("#2E94FF"));
        }
    }

    @Override
    @OnClick({R.id.check_all_comment_tv, R.id.mell_reported_tv, R.id.mell_aptitude_tv})
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
            case R.id.mell_aptitude_tv:// 商家资质
                startActivity(MellAptitudeAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_off_line_mell_info;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected void requestData() {

    }
}
