package com.txd.hzj.wjlp.minetoAty.balance;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：下午 7:49
 * 描述：添加银行卡
 * ===============Txunda===============
 */
public class AddBankCardAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("添加银行卡");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_add_bank_card;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
