package com.txd.hzj.wjlp.minetoAty.balance;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.tools.MoneyUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/14 0014
 * 时间：上午 11:33
 * 描述：转账给用户
 * ===============Txunda===============
 */
public class TransferAccountsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.tr_acc_money_et)
    private EditText tr_acc_money_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("转账");
        MoneyUtils.setPricePoint(tr_acc_money_et);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_transfer_accounts_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
