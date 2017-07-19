package com.txd.hzj.wjlp.minetoAty.balance;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 9:08
 * 描述：线下充值明细
 * ===============Txunda===============
 */
public class RechargeOffLineAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("线下充值详情");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_recharge_off_line;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
