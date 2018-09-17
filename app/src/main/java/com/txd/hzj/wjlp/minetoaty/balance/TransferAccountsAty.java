package com.txd.hzj.wjlp.minetoAty.balance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.view.MyEditText;

import java.util.Map;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/14 0014
 * 时间：上午 11:33
 * 描述：转账给用户
 *
 */
public class TransferAccountsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.tr_acc_money_et)
    private EditText tr_acc_money_et;

    @ViewInject(R.id.can_use_money_tv)
    private TextView can_use_money_tv;
    private String balance = "0.00";
    /**
     * 对方账号信息
     */
    @ViewInject(R.id.opposite_side_ev)
    private EditText opposite_side_ev;

    @ViewInject(R.id.opposite_real_name_tv)
    private TextView opposite_real_name_tv;


    @ViewInject(R.id.ll)
    LinearLayout ll;
    @ViewInject(R.id.operation_type_tv21)
    TextView operation_type_tv21;

    private BalancePst balancePst;

    private String code = "";

    @ViewInject(R.id.pay_pwd_ev)
    private EditText pay_pwd_ev;
    private String real_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("转账");
        DigitsKeyListener keyListener = new DigitsKeyListener(false, true);
        tr_acc_money_et.setKeyListener(keyListener);
        tr_acc_money_et.setKeyListener(new NumberKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {
                return new char[]{'1', '2', '3', '4', '5', '6', '7', '8','9', '0'};
            }

            @Override
            public int getInputType() {
                return  InputType.TYPE_NUMBER_FLAG_DECIMAL;
            }
        });

    }

    @Override
    @OnClick({R.id.change_money_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.change_money_tv:
                String money = tr_acc_money_et.getText().toString();
                String pay_pwd = pay_pwd_ev.getText().toString();
                if (money.equals("") || money.equals("0") || money.equals("0.0") || money.equals("0.00")) {
                    showErrorTip("请输入有效金额");
                    break;
                }
                balancePst.changeMoney(code, money, real_name, pay_pwd);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_transfer_accounts_hzj;
    }

    @Override
    protected void initialized() {
        balancePst = new BalancePst(this);

        balance = getIntent().getStringExtra("balance");
        L.e("wang", "balance:" + balance);

        MoneyUtils.setPricePoint(tr_acc_money_et);
        can_use_money_tv.setText(balance);
        ll.setVisibility(View.VISIBLE);
        operation_type_tv21.setText(" 每次转账金额必须为整数，系统自动扣除转账额的1%综合服务费。");
        opposite_side_ev.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    code = opposite_side_ev.getText().toString();
                    balancePst.getUserName(code);
                    return true;
                }
                return false;
            }
        });

        opposite_side_ev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    code = opposite_side_ev.getText().toString();
                    balancePst.getUserName(code);
                }
            }
        });
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("getUserName")) {// 获取真实姓名
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            real_name = data != null ? data.get("real_name") : "您查找到账号不存在，请核对账号";
            if (real_name.equals("您查找到账号不存在，请核对账号")) {
                opposite_real_name_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            }
            opposite_real_name_tv.setText(real_name);
            return;
        }
        if (requestUrl.contains("changeMoney")) { // 转账成功
            showRightTip(map != null ? map.get("message") : "转账申请中，请耐心等待");
            finish();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        if (requestUrl.contains("getUserName")) {
            opposite_real_name_tv.setText("");
        }
    }
}
