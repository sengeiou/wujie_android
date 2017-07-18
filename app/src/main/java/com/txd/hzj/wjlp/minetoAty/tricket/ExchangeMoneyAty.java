package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：上午 9:18
 * 描述：积分转余额
 * ===============Txunda===============
 */
public class ExchangeMoneyAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("积分转余额");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_exchange_money;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
