package com.txd.hzj.wjlp.minetoaty.tricket;

import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/11 17:02
 * 功能描述：
 */
public class TradingStampAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trading_stamp;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("我的赠品券");
    }

    @Override
    protected void requestData() {

    }
}
