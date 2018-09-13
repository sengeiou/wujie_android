package com.txd.hzj.wjlp.wjyp;

import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/18.
 */

public class GetTicketAty extends BaseAty {
    TextView tv_title;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_getticket;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("代金券申请");
    }

    @Override
    protected void requestData() {

    }

}
