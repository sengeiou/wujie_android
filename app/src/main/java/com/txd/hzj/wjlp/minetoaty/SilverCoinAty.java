package com.txd.hzj.wjlp.minetoaty;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.User;
import com.txd.hzj.wjlp.minetoaty.balance.RechargeAty;
import com.txd.hzj.wjlp.minetoaty.tricket.DetailsOfSilverAty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/7 14:01
 * 功能描述：银两
 */
public class SilverCoinAty extends BaseAty {

//    private String auth_status = "";
//    private String comp_auth_status = "";

//    private Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message message) {
//            Bundle bb = new Bundle();
//            bb.putString("auth_status", auth_status);
//            bb.putString("comp_auth_status", comp_auth_status);
//            startActivity(Authentication_aty.class, bb);
//            return false;
//        }
//    });

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

    @ViewInject(R.id.recharge_tv) // 充值按钮
    private TextView recharge_tv;

    @ViewInject(R.id.withdraw_tv) // 提现按钮
    private TextView withdraw_tv;
    @ViewInject(R.id.transfer_accounts_tv) // 转账按钮
    private TextView transfer_accounts_tv;

    @ViewInject(R.id.balance_details_tv) //银两明细
    private TextView balance_details_tv;

    /**
     * 金额
     */
    @ViewInject(R.id.layout_bottom_tv)
    public TextView layout_bottom_tv;
    private Bundle bundle;

//    private BalancePst balancePst;
//    private String balance = "0";

    private int clickIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("银两");

        layout_bg_ic.setImageResource(R.drawable.icon_balance_bg_hzj);
        layout_top_tv.setText("我的银子(两)");

//        btnVisibility();

    }

    private void btnVisibility() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("token", Config.getToken());
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .addHeader(parameters)
                .build()
                .rxPost("User/userCenter", parameters, new RxStringCallback() {

                    @Override
                    public void onNext(Object tag, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            int user_card_type = Integer.parseInt(data.isNull("user_card_type") ? "0" : data.getString("user_card_type"));
                            //                            int complete_status = Integer.parseInt(data.isNull("complete_status") ? "0" : data.getString("complete_status"));
                            int alliance_merchant = Integer.parseInt(data.isNull("alliance_merchant") ? "0" : data.getString("alliance_merchant"));
                            int shop_id = Integer.parseInt(data.isNull("shop_id_ming") ? "0" : data.getString("shop_id_ming"));
                            if (alliance_merchant > 0) { // 如果存在该字段，执行if中的判断逻辑
                                transfer_accounts_tv.setVisibility(View.VISIBLE); // 转账
                                withdraw_tv.setVisibility(View.VISIBLE); // 提现
                            } else { // 否则的话
                                if (user_card_type == 2 || (shop_id>0 && user_card_type !=3)){
                                    transfer_accounts_tv.setVisibility(View.GONE); // 转账
                                    withdraw_tv.setVisibility(View.VISIBLE); // 提现
                                }else if (user_card_type == 3){
                                    transfer_accounts_tv.setVisibility(View.VISIBLE); // 转账
                                    withdraw_tv.setVisibility(View.VISIBLE); // 提现
                                }else {
                                    transfer_accounts_tv.setVisibility(View.GONE); // 转账
                                    withdraw_tv.setVisibility(View.GONE); // 提现
                                }
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

    @Override
    @OnClick({R.id.recharge_tv, R.id.withdraw_tv, R.id.transfer_accounts_tv, R.id.balance_details_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.recharge_tv:// 充值
                startActivity(RechargeAty.class, null);
                break;
            case R.id.withdraw_tv:// 提现
                clickIndex = R.id.withdraw_tv;
                User.userInfo(this); // 获取用户信息
                break;
            case R.id.transfer_accounts_tv:// 转账给用户
                clickIndex = R.id.transfer_accounts_tv;
                User.userInfo(this); // 获取用户信息
                break;
            case R.id.balance_details_tv:// 银两明细
                startActivity(DetailsOfSilverAty.class, null);
                break;

        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_balance;
    }

    @Override
    protected void initialized() {
//        balancePst = new BalancePst(this);
        recharge_tv.setVisibility(View.GONE); //充值
        transfer_accounts_tv.setVisibility(View.GONE); // 转账
        withdraw_tv.setVisibility(View.GONE); // 提现
        layout_bottom_tv.setText(getIntent().getStringExtra("chance_num"));
        balance_details_tv.setText("银两明细");
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
//        balancePst.balanceIndex();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
//        if (requestUrl.contains("balanceIndex")) {
//            balance = data != null ? data.get("balance") : "0.00";
//            layout_bottom_tv.setText(balance);
//        }

//        if (requestUrl.contains("userInfo")) {
//            Map<String, String> data1 = JSONUtils.parseKeyAndValueToMap(jsonStr);
//            if (data1.get("code").equals("1")) {
//                data1 = JSONUtils.parseKeyAndValueToMap(data1.get("data"));
//                auth_status = data1.get("auth_status");
//                comp_auth_status = data1.get("comp_auth_status");
//                if (auth_status.equals("0") || auth_status.equals("3")) {
//                    showToast(auth_status.equals("0") ? "请先进行实名认证" : "您的认证被拒绝，请重新进行认证");
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            Message message = new Message();
//                            message.what = 0;
//                            message.obj = 0;
//                            handler.sendMessage(message);
//                        }
//                    }).start();
//                    return;
//                }
//                if (auth_status.equals("1")) {
//                    showToast("正在实名认证中，请耐心等待");
//                    return;
//                }
//                if (auth_status.equals("2")) {
//
//                    if (clickIndex == R.id.transfer_accounts_tv) { // 转账
//                        // 转账
//                        bundle = new Bundle();
//                        bundle.putString("balance", balance);
//                        L.e("wang", "layout_bottom_tv.getText().toString():" + balance);
//                        startActivity(TransferAccountsAty.class, bundle);
//                        //                        startActivity(TransferAccountsAty.class, bundle);
//                    } else if (clickIndex == R.id.withdraw_tv) { // 提现
//                        // 提现
//                        bundle = new Bundle();
//                        bundle.putInt("to", 2);
//                        startActivity(ExchangeMoneyAty.class, bundle);
//                    }
//                    return;
//                }
//            }
//            return;
//
//        }

    }
}
