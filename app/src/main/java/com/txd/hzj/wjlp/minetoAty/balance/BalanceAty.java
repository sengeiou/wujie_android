package com.txd.hzj.wjlp.minetoAty.balance;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.tricket.ExchangeMoneyAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：上午 10:21
 * 描述：余额(16-1余额)
 * ===============Txunda===============
 */
public class BalanceAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 背景图片
     */
    @ViewInject(R.id.layout_bg_ic)
    public ImageView layout_bg_ic;

    /**
     * 账户余额(元)
     */
    @ViewInject(R.id.layout_top_tv)
    public TextView layout_top_tv;

    /**
     * 金额
     */
    @ViewInject(R.id.layout_bottom_tv)
    public TextView layout_bottom_tv;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("余额");

        layout_bg_ic.setImageResource(R.drawable.icon_balance_bg_hzj);
        layout_top_tv.setText("账户余额(元)");
        layout_bottom_tv.setText("8.54");
    }

    @Override
    @OnClick({R.id.recharge_tv, R.id.withdraw_tv, R.id.balance_details_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.recharge_tv:// 充值
                startActivity(RechargeAty.class, null);
                break;
            case R.id.withdraw_tv:// 提现
                bundle = new Bundle();
                bundle.putInt("to", 2);
                startActivity(ExchangeMoneyAty.class, bundle);
                break;
            case R.id.balance_details_tv:// 余额明细
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_balance;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}