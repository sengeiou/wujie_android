package com.txd.hzj.wjlp.minetoAty.tricket;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.minetoAty.balance.BankCardHzjAty;
import com.txd.hzj.wjlp.new_wjyp.VipPayAty;
import com.txd.hzj.wjlp.new_wjyp.http.User;
import com.txd.hzj.wjlp.new_wjyp.http.UserBalance;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    @ViewInject(R.id.et_password)
    private EditText et_password;

    @ViewInject(R.id.ll)
    LinearLayout ll;
    @ViewInject(R.id.operation_type_tv21)
    TextView operation_type_tv21;
    @ViewInject(R.id.password_et)
    EditText password_et;

    private CommonPopupWindow commonPopupWindow;
    private String rate;
    private String balanceStr; // 余额数值
    private String myChangeIntegralStr; // 可兑换积分总数
    private String moneyStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
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
//                    String[] split = msubmit_op_tv
                    money_ev.setText(myChangeIntegralStr);
                } else {
                    money_ev.setText(balanceStr);
                }

                break;

            case R.id.submit_op_tv:// 确认，提交

                moneyStr = money_ev.getText().toString();
                if (moneyStr.equals("") || moneyStr.equals("0") || moneyStr.equals("0.0") || moneyStr.equals("0.00")) {
                    showErrorTip("请输入有效数字");
                    break;
                }

//                1:积分转余额 2：提现
                if (type == 1) {
                    if (Double.parseDouble(moneyStr) - Double.parseDouble(myChangeIntegralStr) > 0) {
                        // 如果输入数值大于总积分
                        showErrorTip("积分不足，请检查积分剩余量");
                        break;
                    } else {
                        //  调用积分转余额接口进行操作
                        User.changeIntegral(this, moneyStr);
                    }
                } else {
                    if (TextUtils.isEmpty(bank_card_id)) {
                        showErrorTip("请选择银行卡");
                        break;
                    }
                    if (Double.parseDouble(moneyStr) - Double.parseDouble(balanceStr) > 0) {
                        // 如果输入提现数值大于总余额
                        showErrorTip("余额不足，请检查账户余额");
                        break;
                    } else {
                        if (TextUtils.isEmpty(password_et.getText().toString())) {
                            showErrorTip("请输入支付密码");
                            break;
                        } else {
                            User.verificationPayPwd(this, password_et.getText().toString());
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
            ll.setVisibility(View.VISIBLE);
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
            ll.setVisibility(View.VISIBLE);
            operation_type_tv21.setText(" 每次提现为100的倍数，最大额度为50000，每次提现收取5%综合服务费，每次收取综合服务费100元封顶。");
            UserBalance.cashIndex(this); // 提现首页
        }

    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        ;

        L.e("wang", "jsonStr:" + jsonStr + "\trequestUrl:" + requestUrl);

        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);

        if (requestUrl.contains("verificationPayPwd")) {
            if (map.get("code").equals("1")) { // 返回code为1说明验证成功
//                if (type == 1) { // 积分转余额
//                    User.changeIntegral(this, moneyStr);
//                } else {
                // 余额提现操作
                UserBalance.getCash(ExchangeMoneyAty.this, password_et.getText().toString(), money_ev.getText().toString().trim(), rate, bank_card_id);
//                }
            } else {
                showErrorTip(map.get("message"));
            }
        }
        if (requestUrl.contains("changeIntegral")) { // 积分转余额
            showToast(map.get("message"));
            if (map.get("code").equals("1")) {
                finish();
            }
        }
        if (requestUrl.contains("getCash")) { // 余额提现
            showToast(map.get("message"));
            if (map.get("code").equals("1")) {
                // 提现成功，关闭该界面
                finish();
            }
        }
        if (requestUrl.contains("myIntegral")) { // 获取积分
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
            L.e("wang", "=======>>>>>>>>>>> myChangeIntegralStr:" + myChangeIntegralStr);
            my_bal_tv1.setText("可兑换积分：" + myChangeIntegralStr + " "); // 显示积分总数
            rate_tv.setText(data.get("integral_percentage")); // 手续费率
//            data.get("point_list")
            String pointListStr = data.get("point_list");
            try {
                JSONArray jsonArray = new JSONArray(pointListStr);
                JSONObject json = (JSONObject) jsonArray.get(0);
                operation_type_tv21.setText(json.getString("change")); // 提示：你可以使用xx积分....
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestUrl.contains("cashIndex")) { // 提现首页
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : null);
            balance = data != null ? data.get("balance") : "0.00";
            bal = new BigDecimal(balance);
            balanceStr = data != null ? data.get("balance").trim() : "0.00";
            my_bal_tv1.setText("我的余额" + balanceStr + " ");
            rate_tv.setText(data.get("rate") + "%");
            rate = data.get("rate");
            delay_time_tv.setText(data.get("delay_time"));
        }
    }

    private void download() {

        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.addHeader("token", Config.getToken());
        parameters.put("token", Config.getToken());
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .addHeader(parameters)
                .build()
                .rxPost("User/myIntegral", parameters, new RxStringCallback() {
                    @Override
                    public void onNext(Object tag, String response) {
                        L.e("wang", "========>>>>>>>>>>>>>response:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            String my_integral = jsonObject2.getString("my_integral");
                            String integral_percentage = jsonObject2.getString("integral_percentage");
                            rate_tv.setText(integral_percentage);

                            String status = jsonObject2.getString("status");
                            JSONArray jsonArray = jsonObject2.getJSONArray("point_list");
                            ArrayList<String> list = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String my_change_integral = jsonObject1.getString("my_change_integral");
                                my_bal_tv1.setText("可兑换积分:" + my_change_integral);

                                String point_num = jsonObject1.getString("point_num");
                                String change = jsonObject1.getString("change");
                                String date = jsonObject1.getString("date");


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }

                });

    }

//    private void download2() {
//
//        L.e("=========>>>>>>>download2");
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        Map<String, Object> parameters1 = new HashMap<String, Object>();
////        integral
////        parameters.addHeader("token", Config.getToken());
//        parameters.put("token", Config.getToken());
//        parameters1.put("integral", money_ev.getText().toString());
//        new Novate.Builder(this)
//                .baseUrl(Config.BASE_URL)
//                .addHeader(parameters)
//                .build()
//                .rxPost("User/changeIntegral", parameters1, new RxStringCallback() {
//                    @Override
//                    public void onNext(Object tag, String response) {
//                        try {
//                            L.e("=========>>>>>>>" + response);
//                            JSONObject jsonObject = new JSONObject(response);
//                            String code = jsonObject.getString("code");
//                            String message = jsonObject.getString("message");
//                            showToast(message);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Object tag, Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onCancel(Object tag, Throwable e) {
//
//                    }
//
//                });
//
//    }

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
