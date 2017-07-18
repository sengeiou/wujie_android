package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

public class IntegralAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("积分");
    }

    @Override
    @OnClick({R.id.check_details_tv,R.id.exchange_money_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_details_tv:// 积分明细
                Bundle bundle = new Bundle();
                bundle.putInt("from", 2);
                startActivity(ParticularsUsedByTricketAty.class, bundle);
                break;
            case R.id.exchange_money_tv:// 确认兑换
                bundle = new Bundle();
                bundle.putInt("to",1);
                startActivity(ExchangeMoneyAty.class,bundle);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_integral;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
