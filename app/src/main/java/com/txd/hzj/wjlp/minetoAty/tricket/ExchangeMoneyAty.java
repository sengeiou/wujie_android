package com.txd.hzj.wjlp.minetoAty.tricket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.balance.BankCardHzjAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：上午 9:18
 * 描述：积分转余额
 * ===============Txunda===============
 */
public class ExchangeMoneyAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private int type = 1;

    /**
     * 类型
     */
    @ViewInject(R.id.type_logo_iv)
    private ImageView type_logo_iv;

    /**
     * 银行卡选择
     */
    @ViewInject(R.id.select_bank_card_layout)
    private LinearLayout select_bank_card_layout;

    /**
     * 银行名称
     */
    @ViewInject(R.id.card_name_tv)
    private TextView card_name_tv;
    /**
     * 银行卡号
     */
    @ViewInject(R.id.card_num_tv)
    private TextView card_num_tv;

    /**
     * 确定
     * 提交
     */
    @ViewInject(R.id.submit_op_tv)
    private TextView submit_op_tv;

    @ViewInject(R.id.operation_type_tv)
    private TextView operation_type_tv;

    @ViewInject(R.id.operation_type_tv2)
    private TextView operation_type_tv2;

    /**
     * 提现提示
     */
    @ViewInject(R.id.bottom_tip_layout)
    private LinearLayout bottom_tip_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (1 == type) {
            titlt_conter_tv.setText("积分转余额");
            type_logo_iv.setImageResource(R.drawable.icon_exchange_hzj);
            submit_op_tv.setText("确定");
            select_bank_card_layout.setVisibility(View.INVISIBLE);
            bottom_tip_layout.setVisibility(View.GONE);
            operation_type_tv.setText("积分");
        } else {
            titlt_conter_tv.setText("提现");
            operation_type_tv.setText("提现金额");
            type_logo_iv.setImageResource(R.drawable.icon_withdraw_hzj_);
            submit_op_tv.setText("提交");
            select_bank_card_layout.setVisibility(View.VISIBLE);
            bottom_tip_layout.setVisibility(View.VISIBLE);
            operation_type_tv2.setText("提现到银行卡，手续费率");
        }
    }

    @Override
    @OnClick({R.id.select_bank_card_layout, R.id.submit_op_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.select_bank_card_layout:
                startActivityForResult(BankCardHzjAty.class, null, 100);
                break;
            case R.id.submit_op_tv:// 确认，提交
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_exchange_money;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("to", 1);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 100:// 银行卡
                    card_name_tv.setText(data.getStringExtra("name"));
                    card_num_tv.setText(data.getStringExtra("num"));
                    break;
            }
        }
    }
}
