package com.txd.hzj.wjlp.wjyp;

import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/17.
 */

public class PayVipAty extends BaseAty {

    private TextView tv_title;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_pay_vip;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("支付成为拓展商");
    }

    @Override
    protected void requestData() {

    }

}
