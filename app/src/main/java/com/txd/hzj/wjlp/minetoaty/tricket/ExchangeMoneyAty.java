package com.txd.hzj.wjlp.minetoaty.tricket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.User;
import com.txd.hzj.wjlp.http.UserBalance;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.minetoaty.balance.BankCardHzjAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.view.MyEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：上午 9:18
 * 描述：积分转余额
 */
public class ExchangeMoneyAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private int type = 1; // 1:积分转余额 2：提现

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

    /**
     * lg，余额提示增加 desc提示
     */
    @ViewInject(R.id.cashDescLayout)
    private LinearLayout cashDescLayout;
    /**
     * lg，余额提示增加 desc提示
     */
    @ViewInject(R.id.cashDescTv)
    private TextView cashDescTv;

    @ViewInject(R.id.my_bal_tv1)
    private TextView my_bal_tv1;
    @ViewInject(R.id.my_bal_tv2)
    private TextView my_bal_tv2;
    @ViewInject(R.id.rate_tv)
    private TextView rate_tv;
    @ViewInject(R.id.delay_time_tv)
    private TextView delay_time_tv;
    @ViewInject(R.id.titlt_right_tv)
    private TextView titlt_right_tv;

    private BalancePst balancePst;

    @ViewInject(R.id.money_ev)
    private MyEditText money_ev;
    private String balance = "";

    private BigDecimal bal;
    private String bank_card_id = "";

    @ViewInject(R.id.et_password)
    private EditText et_password;

    @ViewInject(R.id.password_et)
    EditText password_et;

    private boolean isClick = false; // 提交按钮是否点击

    private CommonPopupWindow commonPopupWindow;
    private String rate;
    private String balanceStr; // 余额数值
    private String myChangeIntegralStr; // 可兑换积分总数
    private String moneyStr;
    private int rateInt; // 回传的手续费率，用于计算具体的手续费

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        DigitsKeyListener keyListener = new DigitsKeyListener(false, true);
        money_ev.setKeyListener(keyListener);
        money_ev.setKeyListener(new NumberKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {
                return new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_NUMBER_FLAG_DECIMAL;
            }
        });
    }

    @Override
    @OnClick({R.id.select_bank_card_layout, R.id.my_bal_tv2, R.id.submit_op_tv, R.id.submit_op_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.select_bank_card_layout:
                startActivityForResult(BankCardHzjAty.class, null, 100);
                break;
            case R.id.my_bal_tv2:// 全部使用

                //                1:积分转余额 2：提现
                if (type == 1) {
                    money_ev.setText(myChangeIntegralStr);
                } else {
                    money_ev.setText(balanceStr);
                }
                break;

            case R.id.submit_op_tv:// 确认，提交
                long val = 0;
                if (!isClick) { // 如果没有点击
                    moneyStr = money_ev.getText().toString();

                    try {
                        val = (long) Math.floor(Double.parseDouble(moneyStr));
                    } catch (NumberFormatException e) {
                        L.e("money_ev.textView To Number Double Exception:" + e.toString());
                        showToast("输入的数字格式异常，请检查！");
                    }

                    if (moneyStr.equals("") || moneyStr.equals("0") || moneyStr.equals("0.0") || moneyStr.equals("0.00")) {
                        showToast("请输入有效数字");
                        return;
                    } else if (type == 1 && (val % 10 != 0 || val / 10 == 0)) {
                        // 如果是积分转余额 并且 数值不为10的倍数
                        showToast("输入数值必须为10的倍数");
                        return;
                    } else if (type == 2 && (val % 100 != 0 || val / 100 == 0)) {
                        // 如果是提现 并且 数值不为100的倍数
                        showToast("提现金额必须为100的倍数");
                        return;
                    } else if (val > 50000 && type == 2) { // 单次提现超额
                        showToast("每次最大提现额度为50000");
                        return;
                    }

                    //                1:积分转余额 2：提现
                    if (type == 1) { // 积分转余额
                        if (Double.parseDouble(moneyStr) - Double.parseDouble(myChangeIntegralStr) > 0) {
                            // 如果输入数值大于总积分
                            showToast("积分不足，请检查积分剩余量");
                            break;
                        } else {
                            isClick = true; // 所有设置都正确则将按钮点击标识设为true，禁止连续点击
                            User.changeIntegral(this, moneyStr); //  调用积分转余额接口进行操作
                        }
                    } else { // 否则的话是提现
                        if (TextUtils.isEmpty(bank_card_id)) {
                            showToast("请选择银行卡");
                            break;
                        }
                        if (Double.parseDouble(moneyStr) - Double.parseDouble(balanceStr) > 0) {
                            // 如果输入提现数值大于总余额
                            showToast("余额不足，请检查账户余额");
                            break;
                        } else {
                            if (TextUtils.isEmpty(password_et.getText().toString())) {
                                showToast("请输入支付密码");
                                break;
                            } else {
                                isClick = true; // 所有设置都正确则将按钮点击标识设为true，禁止连续点击，在验证密码处验证失败再次设置为未点击状态
                                User.verificationPayPwd(this, password_et.getText().toString());
                            }
                        }
                    }
                }
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
        titlt_right_tv.setText("提现明细");
        titlt_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExchangeMoneyAty.this, ParticularsUsedByTricketAty.class);
                startActivity(intent);
                finish();
            }
        });

        if (1 == type) {
            password_et.setVisibility(View.GONE); // 隐藏密码输入框
            titlt_conter_tv.setText("积分转余额");
            type_logo_iv.setImageResource(R.drawable.icon_exchange_hzj);
            submit_op_tv.setText("确定");
            select_bank_card_layout.setVisibility(View.GONE);
            bottom_tip_layout.setVisibility(View.GONE);
            operation_type_tv.setText("积分");
            my_bal_tv2.setText("全部使用");
            my_bal_tv1.setText("我的积分300 ");
            cashDescLayout.setVisibility(View.VISIBLE);

            User.myIntegral(this); // 获取积分
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
            cashDescLayout.setVisibility(View.VISIBLE);
            titlt_right_tv.setVisibility(View.GONE); // 暂时隐藏提现明细操作入口

            UserBalance.cashIndex(this); // 提现首页
        }

        money_ev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String integral = money_ev.getText().toString() == null ? "" : money_ev.getText().toString().trim(); // 获取输入的数值
                if (1 == type) { //
                    if (Integer.parseInt(integral.isEmpty() ? "0" : integral) > Integer.parseInt(myChangeIntegralStr)) {
                        // 否则如果输入的数字值大于可兑换积分，直接设置最大积分数值
                        integral = myChangeIntegralStr;
                        money_ev.setText(myChangeIntegralStr);
                        money_ev.setSelection(myChangeIntegralStr.length());
                    }
                    User.autoChange(ExchangeMoneyAty.this, integral);
                } else if (type == 2) { // 提现
                    if (StringUtils.isEmpty(balanceStr) && balanceStr == null) {
                        return;
                    }
                    if (Double.parseDouble(integral.isEmpty() ? "0" : integral) > Double.parseDouble(balanceStr)) {
                        // 否则如果输入的数字值大于可提现的数值，直接设置最大提现数值
                        integral = balanceStr; // 将最大值赋给输入的值
                        money_ev.setText(balanceStr); // 设置最大值到输入框
                        money_ev.setSelection(balanceStr.length()); // 设置光标显示位置到输入框末位
                    }
                    if (integral.isEmpty()) { // 如果没有输入或输入值为空，则不显示括号中的内容
                        rate_tv.setText(rate + "%");
                    } else { // 否则显示括号中具体扣除的手续费
                        double integralDouble = Double.parseDouble(integral.isEmpty() ? "0" : integral) * rateInt / 100.0;
                        rate_tv.setText(rate + "%（需扣除" + (integralDouble >= 100.0 ? "100" : integralDouble) + "元手续费）");
                    }
                }
            }
        });

    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

        L.e("wang", "integral jsonStr:" + jsonStr + "\trequestUrl:" + requestUrl);
        String[] splitTemp = requestUrl.split("\\/");
        String urlLastStr = splitTemp[splitTemp.length - 1];
        for (String str : splitTemp) {
            L.e("wang", "string:" + str);
        }

        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);

        if (urlLastStr.equals("verificationPayPwd")) {
            if (map.get("code").equals("1")) { // 返回code为1说明验证成功
                UserBalance.getCash(ExchangeMoneyAty.this, password_et.getText().toString(), money_ev.getText().toString().trim(), rate, bank_card_id);
            } else {
                isClick = false;
                showToast(map.get("message"));
            }
        }
        if (urlLastStr.equals("changeIntegral")) { // 积分转余额
            showToast(map.get("message"));
            if (map.get("code").equals("1")) {
                finish();
            }
        }
        if (urlLastStr.equals("getCash")) { // 余额提现
            showToast(map.get("message"));
            if (map.get("code").equals("1")) {
                // 提现成功，关闭该界面
                finish();
            }
        }
        if (urlLastStr.equals("myIntegral")) { // 获取积分
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : null);
            // 可兑换积分总数
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject data1 = jsonObject.getJSONObject("data");
                JSONArray point_list = data1.getJSONArray("point_list");
                JSONObject jsonObject1 = (JSONObject) point_list.get(0);
                myChangeIntegralStr = jsonObject1.getString("my_change_integral");
                String[] split = myChangeIntegralStr.split("\\.");
                myChangeIntegralStr = split[0];
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //            myChangeIntegralStr = JSONUtils.parseKeyAndValueToMap(data.get("point_list")).get("my_change_integral");
            my_bal_tv1.setText("可兑换积分：" + myChangeIntegralStr + " "); // 显示积分总数
            rate_tv.setText(data.get("integral_percentage")); // 手续费率
            //            data.get("point_list")
            String pointListStr = data.get("point_list");
            try {
                JSONArray jsonArray = new JSONArray(pointListStr);
                JSONObject json = (JSONObject) jsonArray.get(0);
                cashDescTv.setText(json.getString("change")); // 提示：你可以使用xx积分....
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (urlLastStr.equals("cashIndex")) { // 提现首页
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : null);
            balance = data != null ? data.get("balance") : "0.00";
            bal = new BigDecimal(balance);
            balanceStr = data.get("balance") != null ? data.get("balance").trim() : "0.00";
            my_bal_tv1.setText("我的余额" + balanceStr + " ");
            String rate = data.get("rate"); // 获取手续费费率字符串
            rateInt = Integer.parseInt(rate); // 将手续费率字符串转换成Int用于计算具体金额
            rate_tv.setText(rate + "%");
            this.rate = data.get("rate");
            delay_time_tv.setText(data.get("delay_time"));
            if (data.containsKey("cash_desc")) {
                cashDescTv.setText(data.get("cash_desc"));
            }
        }
        if (urlLastStr.equals("autoChange")) { // 获取详情提示
            if (map.get("code").equals("1")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                rate_tv.setText(data.get("integral_percentage")); // 手续费率
                cashDescTv.setText(data.get("desc"));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 100:// 银行卡
                    card_name_tv.setText(data.getStringExtra("name"));

                    String num = data.getStringExtra("num");
                    if (num.length() >= 16) {
                        num = num.replaceAll("(\\d{4})\\d{8,11}(\\w{4})", "***************$2");
                    }
                    card_num_tv.setText(num);
                    bank_card_id = data.getStringExtra("bank_card_id");
                    break;
            }
        }
    }

}
