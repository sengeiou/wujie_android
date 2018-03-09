package com.txd.hzj.wjlp.wjyp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.txd.hzj.wjlp.R;

/**
 * by Txunda_LH on 2018/1/17.
 */

public class PayVipAty extends BaseAty {

    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pay_vip);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("支付成为拓展商");
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }
}
