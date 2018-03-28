package com.txd.hzj.wjlp.minetoAty.balance;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.minetoAty.tricket.ExchangeMoneyAty;
import com.txd.hzj.wjlp.minetoAty.tricket.ParticularsUsedByTricketAty;
import com.txd.hzj.wjlp.new_wjyp.http.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @ViewInject(R.id.withdraw_tv) // 提现按钮
    private TextView withdraw_tv;
    @ViewInject(R.id.transfer_accounts_tv) // 转账按钮
    private TextView transfer_accounts_tv;

    /**
     * 金额
     */
    @ViewInject(R.id.layout_bottom_tv)
    public TextView layout_bottom_tv;
    private Bundle bundle;

    private BalancePst balancePst;
    private String balance = "0.00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("余额");

        layout_bg_ic.setImageResource(R.drawable.icon_balance_bg_hzj);
        layout_top_tv.setText("账户余额(元)");

        btnVisibility();

    }

    private void btnVisibility(){
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
                            int user_card_type = Integer.parseInt(data.getString("user_card_type"));
                            switch (user_card_type) {
                                case 1:
                                    // 提现、转账隐藏
                                    withdraw_tv.setVisibility(View.GONE);
                                    transfer_accounts_tv.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    // 转账隐藏
                                    transfer_accounts_tv.setVisibility(View.GONE);
                                    break;
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
    @OnClick({R.id.recharge_tv, R.id.withdraw_tv, R.id.transfer_accounts_tv,R.id.balance_details_tv})
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
            case R.id.transfer_accounts_tv:// 转账给用户
                bundle = new Bundle();
                bundle.putString("balance", balance);
                startActivity(TransferAccountsAty.class, bundle);
                break;
            case R.id.balance_details_tv:// 余额明细
                Bundle bundle = new Bundle();
                bundle.putInt("from", 3);
                startActivity(ParticularsUsedByTricketAty.class, bundle);
                break;

        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_balance;
    }

    @Override
    protected void initialized() {
        balancePst = new BalancePst(this);
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        balancePst.balanceIndex();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

//        withdraw_tv // 提现
//        transfer_accounts_tv // 转账

        // TODO 控件隐藏
        L.e("wang", "===========+>>>>>>>>>" + requestUrl + "\t" + jsonStr);

        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            balance = data != null ? data.get("balance") : "0.00";
            layout_bottom_tv.setText(balance);
        }
    }
}
