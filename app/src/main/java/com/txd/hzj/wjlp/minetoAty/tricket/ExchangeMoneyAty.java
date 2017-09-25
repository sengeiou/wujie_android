package com.txd.hzj.wjlp.minetoAty.tricket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.minetoAty.balance.BankCardHzjAty;

import java.math.BigDecimal;
import java.util.Map;

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

    @ViewInject(R.id.my_bal_tv1)
    private TextView my_bal_tv1;
    @ViewInject(R.id.my_bal_tv2)
    private TextView my_bal_tv2;
    @ViewInject(R.id.rate_tv)
    private TextView rate_tv;
    @ViewInject(R.id.delay_time_tv)
    private TextView delay_time_tv;

    private BalancePst balancePst;

    @ViewInject(R.id.money_ev)
    private EditText money_ev;
    private String balance = "";

    private BigDecimal bal;
    private String bank_card_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (1 == type) {
            titlt_conter_tv.setText("积分转余额");
            type_logo_iv.setImageResource(R.drawable.icon_exchange_hzj);
            submit_op_tv.setText("确定");
            select_bank_card_layout.setVisibility(View.GONE);
            bottom_tip_layout.setVisibility(View.GONE);
            operation_type_tv.setText("积分");
            my_bal_tv2.setText("全部使用");
            my_bal_tv1.setText("我的积分300 ");
        } else {
            titlt_conter_tv.setText("提现");
            operation_type_tv.setText("提现金额");
            type_logo_iv.setImageResource(R.drawable.icon_withdraw_hzj_);
            submit_op_tv.setText("提交");
            select_bank_card_layout.setVisibility(View.VISIBLE);
            bottom_tip_layout.setVisibility(View.VISIBLE);
            operation_type_tv2.setText("提现到银行卡，手续费率");
            my_bal_tv2.setText("全部提现");
            MoneyUtils.setPricePoint(money_ev);
        }
    }

    @Override
    @OnClick({R.id.select_bank_card_layout, R.id.my_bal_tv2, R.id.submit_op_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.select_bank_card_layout:
                startActivityForResult(BankCardHzjAty.class, null, 100);
                break;
            case R.id.my_bal_tv2:// 全部使用
                if (2 == type) {
                    money_ev.setText(balance);
                }
                break;

            case R.id.submit_op_tv:// 确认，提交
                String money = money_ev.getText().toString();

                if (money.equals("") || money.equals("0") || money.equals("0.0") || money.equals("0.00")) {
                    showErrorTip("请输入有效数字");
                    break;
                }

                BigDecimal input = new BigDecimal(money);
                // 输入的比余额达
                if (input.compareTo(bal) == 1) {
                    showErrorTip("余额不足");
                    break;
                }
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
        balancePst = new BalancePst(this);
        bal = new BigDecimal("0.00");
    }

    @Override
    protected void requestData() {
        if (type == 2) {// 提现
            balancePst.cashIndex();
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("cashIndex")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : null);
            balance = data != null ? data.get("balance") : "0.00";
            bal = new BigDecimal(balance);
            my_bal_tv1.setText("我的余额" + (data != null ? data.get("balance") : "0.00") + " ");
            rate_tv.setText(data.get("rate") + "%");
            delay_time_tv.setText(data.get("delay_time"));
        }
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

                    String num = data.getStringExtra("num");
                    if (num.length() >= 16)
                        num = num.replaceAll("(\\d{4})\\d{8,11}(\\w{4})", "***************$2");
                    card_num_tv.setText(num);
                    bank_card_id = data.getStringExtra("bank_card_id");
                    break;
            }
        }
    }
}
