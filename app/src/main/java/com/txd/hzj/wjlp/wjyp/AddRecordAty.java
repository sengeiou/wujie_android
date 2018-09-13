package com.txd.hzj.wjlp.wjyp;

import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/20.
 */

public class AddRecordAty extends BaseAty {
    TextView tv_title;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_addrecord;
    }

    @Override
    protected void initialized() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("拓展员添加");
    }

    @Override
    protected void requestData() {

    }

}
