package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.RecargeAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.RechargeBean;
import com.txd.hzj.wjlp.http.Pay;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.minetoaty.PayForAppAty;
import com.txd.hzj.wjlp.minetoaty.setting.EditPayPasswordAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.tool.WJConfig;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;
import com.txd.hzj.wjlp.wxapi.GetPrepayIdTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两界面
 */
public class MoneyActivity extends BaseAty implements RecargeAdapter.OnSelectChange {

    @ViewInject(R.id.titleView_bg_rlayout) // 设置背景颜色
    public RelativeLayout titleView_bg_rlayout;
    @ViewInject(R.id.titleView_goback_imgv) // 返回按钮
    public ImageView titleView_goback_imgv;
    @ViewInject(R.id.titleView_title_tv) // 标题文字
    public TextView titleView_title_tv;

    @ViewInject(R.id.money_recording_tv) // 游戏币记录按钮
    public TextView money_recording_tv;
    @ViewInject(R.id.money_number_tv) // 银两总数显示
    public TextView money_number_tv;
    @ViewInject(R.id.money_data_reView) // 选项支付列表
    public RecyclerView money_data_reView;
    @ViewInject(R.id.money_alipay_llayout) // 支付宝支付选项
    public LinearLayout money_alipay_llayout;
    @ViewInject(R.id.money_alipay_cbox) // 支付宝支付复选框
    public CheckBox money_alipay_cbox;
    @ViewInject(R.id.money_weChat_llayout) // 微信支付选项
    public LinearLayout money_weChat_llayout;
    @ViewInject(R.id.money_weChat_cbox) // 微信支付复选框
    public CheckBox money_weChat_cbox;
    @ViewInject(R.id.money_balance_llayout) // 余额支付选项
    public LinearLayout money_balance_llayout;
    @ViewInject(R.id.money_balance_cbox) // 余额支付复选框
    public CheckBox money_balance_cbox;
    @ViewInject(R.id.money_integral_llayout) // 积分支付选项
    public LinearLayout money_integral_llayout;
    @ViewInject(R.id.money_integral_cbox) // 积分支付复选框
    public CheckBox money_integral_cbox;
    @ViewInject(R.id.money_submit_tv) // 立即充值按钮
    public TextView money_submit_tv;

    private String is_pay_password = "0"; // 是否设置支付密码
    private CommonPopupWindow commonPopupWindow; // 常规弹窗

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_money;
    }

    @Override
    protected void initialized() {
        titleView_bg_rlayout.setBackgroundColor(getResources().getColor(R.color.white));
        titleView_goback_imgv.setImageResource(R.mipmap.icon_be_back_b);
        titleView_title_tv.setTextColor(getResources().getColor(R.color.black));
        titleView_title_tv.setText("银两");

        // 初始化微信支付
        wxPayReceiver = new WxPayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("wjyp.wxPay");
        registerReceiver(wxPayReceiver, intentFilter);

    }

    @Override
    protected void requestData() {
        User.settings(this); // 请求个人中心，主要是获取用户是否设置支付密码
        Catcher.exchangeList(this); // 获取充值金额列表
    }

    /**
     * 设置充值游戏币数量
     */
    private void setRechargeListShow(List<RechargeBean> rechargeBeanList) {
        RecargeAdapter recargeAdapter = new RecargeAdapter(this, rechargeBeanList);
        recargeAdapter.setOnSelectChange(this);
        money_data_reView.setLayoutManager(new LinearLayoutManager(this));
        money_data_reView.setNestedScrollingEnabled(false); // 设置阻尼效果
        money_data_reView.setAdapter(recargeAdapter);
    }

    @OnClick({R.id.titleView_goback_imgv, R.id.money_recording_tv, R.id.money_submit_tv, R.id.money_alipay_llayout, R.id.money_weChat_llayout, R.id.money_balance_llayout, R.id.money_integral_llayout, R.id.money_alipay_cbox, R.id.money_weChat_cbox, R.id.money_balance_cbox, R.id.money_integral_cbox})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
            case R.id.money_recording_tv:
                startActivity(MoneyRecordingActivity.class, null);
                break;
            case R.id.money_submit_tv:
                // 点击的时候如果是余额或积分需要输入密码
                if (money_balance_cbox.isChecked() || money_integral_cbox.isChecked()) { // 余额或者积分支付
                    if (is_pay_password.equals("1")) {
                        showPwdPop(v); // 如果已设置支付密码，则直接弹窗输入支付密码
                    } else {
                        // 否则跳转至设置支付密码界面
                        showToast("请设置支付密码");
                        Bundle bundle = new Bundle();
                        bundle.putString("is_pay_password", "0");
                        bundle.putString("phone", "");
                        startActivity(EditPayPasswordAty.class, bundle);
                    }
                } else if (money_alipay_cbox.isChecked()) { // 支付宝支付
//                    Pay.getAlipayParam();
//                    showProgressDialog();
                } else if (money_weChat_cbox.isChecked()) { // 微信支付
//                    Pay.getJsTine();
//                    showProgressDialog();
                }
                break;
            case R.id.money_alipay_llayout:
            case R.id.money_alipay_cbox:
                setCheckboxChecked(1);
                break;
            case R.id.money_weChat_llayout:
            case R.id.money_weChat_cbox:
                setCheckboxChecked(2);
                break;
            case R.id.money_balance_llayout:
            case R.id.money_balance_cbox:
                setCheckboxChecked(3);
                break;
            case R.id.money_integral_llayout:
            case R.id.money_integral_cbox:
                setCheckboxChecked(4);
                break;
        }
    }

    /**
     * 设置相应选项选择状态
     *
     * @param index 选择索引 1:支付宝 2:微信 3:余额 4:积分
     */
    private void setCheckboxChecked(int index) {
        money_alipay_cbox.setChecked(false);
        money_weChat_cbox.setChecked(false);
        money_balance_cbox.setChecked(false);
        money_integral_cbox.setChecked(false);
        switch (index) {
            case 1:
                money_alipay_cbox.setChecked(true);
                break;
            case 2:
                money_weChat_cbox.setChecked(true);
                break;
            case 3:
                money_balance_cbox.setChecked(true);
                break;
            case 4:
                money_integral_cbox.setChecked(true);
                break;
        }
    }

    @Override
    public void change(RechargeBean rechargeBean) {
        // 充值列表选择项选择改变
        L.e(rechargeBean.toString());
    }

    //支付弹出框
    public void showPwdPop(View view) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_pwd)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 4)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        final EditText et_password = view.findViewById(R.id.et_password);
                        TextView submit = view.findViewById(R.id.submit);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(et_password.getText().toString())) {
                                    showToast("请输入支付密码");
                                    return;
                                }
                                User.verificationPayPwd(et_password.getText().toString(), MoneyActivity.this);
                                commonPopupWindow.dismiss();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject requestJsonObject = new JSONObject(jsonStr);
            JSONObject data = requestJsonObject.getJSONObject("data");

            if (requestUrl.contains("setting")) { // 获取账号下个人设置
                is_pay_password = data.getString("is_pay_password");
                return;
            }

            if (requestUrl.contains("verificationPayPwd")) { // 验证支付密码回调
                // 进来表示验证成功，直接去支付
            }

            if (requestUrl.contains("exchangeList")) { // 获取充值金额列表
                String coin = data.getString("coin");
                money_number_tv.setText(coin); // 设置我的银两
                JSONArray listJSONArray = data.getJSONArray("list");
                List<RechargeBean> rechargeBeanList = new ArrayList<>();
                RechargeBean rechargeBean;
                for (int i = 0; i < listJSONArray.length(); i++) {
                    JSONObject jsonObject = listJSONArray.getJSONObject(i);
                    rechargeBean = new RechargeBean();
                    rechargeBean.setCoinsNumber(Integer.parseInt(jsonObject.getString("coinsNumber"))); // 充值游戏币
                    rechargeBean.setGift(jsonObject.getInt("isGift") == 1); // 是否赠送
                    rechargeBean.setGiftNumber(Integer.parseInt(jsonObject.getString("giftNumber"))); // 赠送数量
                    rechargeBean.setPrice(Double.parseDouble(jsonObject.getString("price"))); // 实应付价格
                    rechargeBeanList.add(rechargeBean);
                }
                setRechargeListShow(rechargeBeanList);
            }

            if (requestUrl.contains("getAlipayParam")) { // 支付宝支付
//                showProgressDialog();
//                AliPay aliPay = new AliPay(data.get("pay_string"), new AliPayCallBack() {
//                    @Override
//                    public void onComplete() {
//                        Pay.findPayResult(order_id, "16", MoneyActivity.this);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        showToast("支付失败！");
//                        removeProgressDialog();
//                        finish();
//                    }
//
//                    @Override
//                    public void onProcessing() {
//                    }
//                });
//                aliPay.pay();
            }

            if (requestUrl.contains("getJsTine")) { // 微信支付
//                GetPrepayIdTask wxPay = new GetPrepayIdTask(MoneyActivity.this, data.getString("sign"), data.getString("appid"),
//                        data.getString("nonce_str"), data.getString("package"), data.getString("time_stamp"), data.getString("prepay_id"),
//                        data.getString("mch_id"), "");
//                wxPay.execute();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付以及回调
     */
    private WxPayReceiver wxPayReceiver;

    private class WxPayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 5);
            if (errCode == 0) {
                showToast("支付成功");
            } else {
                showToast("支付失败");
            }
        }
    }
}
