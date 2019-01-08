package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/8 11:49
 * 功能描述：
 */
public class InputAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_input;
    }

    @Override
    protected void initialized() {
        mContext = this;
        titlt_conter_tv.setText("录入");
    }

    @Override
    protected void requestData() {

    }
}
