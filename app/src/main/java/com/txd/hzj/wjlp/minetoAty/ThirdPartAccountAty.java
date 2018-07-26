package com.txd.hzj.wjlp.minetoAty;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.PayeeBindBean;
import com.txd.hzj.wjlp.http.User;
import com.txd.hzj.wjlp.minetoAty.setting.EditPayPasswordAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.iwgang.countdownview.CustomCountDownTimer;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/25 025 下午 16:02
 * 功能描述：三方收款账户
 */
public class ThirdPartAccountAty extends BaseAty {

    // 平台账号
    @ViewInject(R.id.thirdPartyAcc_wujieAccount_tv)
    private TextView thirdPartyAcc_wujieAccount_tv;
    // 平台复选框
    @ViewInject(R.id.thirdPartyAcc_wujieAccount_cb)
    private CheckBox thirdPartyAcc_wujieAccount_cb;
    // 微信账号
    @ViewInject(R.id.thirdPartyAcc_weChatAccount_tv)
    private TextView thirdPartyAcc_weChatAccount_tv;
    // 微信账号复选框
    @ViewInject(R.id.thirdPartyAcc_weChatAccount_cb)
    private CheckBox thirdPartyAcc_weChatAccount_cb;
    // 验证微信按钮
    @ViewInject(R.id.thirdPartyAcc_verificationWeChat_tv)
    private TextView thirdPartyAcc_verificationWeChat_tv;
    // 支付宝账号
    @ViewInject(R.id.thirdPartyAcc_alipayAccount_et)
    private EditText thirdPartyAcc_alipayAccount_et;
    // 支付宝复选框
    @ViewInject(R.id.thirdPartyAcc_alipayAccount_cb)
    private CheckBox thirdPartyAcc_alipayAccount_cb;
    // 验证支付宝
    @ViewInject(R.id.thirdPartyAcc_verificationAlipay_tv)
    private TextView thirdPartyAcc_verificationAlipay_tv;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    private PayeeBindBean payeeBindBean;
    private CommonPopupWindow commonPopupWindow;

    private int oldSelectCheckBox = 0; // 原始选中的复选框
    private int newSelectCheckBox = 0; // 点击之后选中的复选框

    private String weChatOpenid; // 绑定微信的openid
    private String nickName; // 微信昵称
    private String alipayAccounts; // 支付宝账户

    private int clickView = 0;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_third_party_accounts;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("三方收款账户");
        // 初始化的时候隐藏掉微信和支付宝复选框
        thirdPartyAcc_weChatAccount_cb.setVisibility(View.GONE);
        thirdPartyAcc_alipayAccount_cb.setVisibility(View.GONE);
    }

    @Override
    protected void requestData() {
        User.payeeBind(this);
    }

    @OnClick({R.id.thirdPartyAcc_verificationWeChat_tv, R.id.thirdPartyAcc_verificationAlipay_tv,
            R.id.thirdPartyAcc_wujieAccount_cb, R.id.thirdPartyAcc_weChatAccount_cb, R.id.thirdPartyAcc_alipayAccount_cb})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thirdPartyAcc_verificationWeChat_tv:
                clickView = R.id.thirdPartyAcc_verificationWeChat_tv;
                // 判断是否设置了支付密码，如果设置过，则直接去弹窗验证
                if (payeeBindBean.getData().getIs_pay_password() == 1) {
                    showPwdPop(v); // 验证支付密码
                } else { // 否则的话直接提示其进行设置支付密码
                    showToast("请设置支付密码");
                    Bundle bundle = new Bundle();
                    bundle.putString("is_pay_password", "0");
                    bundle.putString("phone", payeeBindBean.getData().getPhone());
                    startActivity(EditPayPasswordAty.class, bundle);
                }
                break;
            case R.id.thirdPartyAcc_verificationAlipay_tv:
                if (thirdPartyAcc_alipayAccount_et.getText().toString().trim().equals("")) {
                    showToast("请输入支付宝账号");
                    return;
                }
                alipayAccounts = thirdPartyAcc_alipayAccount_et.getText().toString().trim();
                clickView = R.id.thirdPartyAcc_verificationAlipay_tv;
                // 判断是否设置了支付密码，如果设置过，则直接去弹窗验证
                if (payeeBindBean.getData().getIs_pay_password() == 1) {
                    showPwdPop(v); // 验证支付密码
                } else { // 否则的话直接提示其进行设置支付密码
                    showToast("请设置支付密码");
                    Bundle bundle = new Bundle();
                    bundle.putString("is_pay_password", "0");
                    bundle.putString("phone", payeeBindBean.getData().getPhone());
                    startActivity(EditPayPasswordAty.class, bundle);
                }
                break;
            case R.id.thirdPartyAcc_wujieAccount_cb:
            case R.id.thirdPartyAcc_weChatAccount_cb:
            case R.id.thirdPartyAcc_alipayAccount_cb:
                if (payeeBindBean.getData().getChange_account_status().equals("1")) { // 支持切换账户
                    setCheckBoxChecked(oldSelectCheckBox);
                    newSelectCheckBox = v.getId() == R.id.thirdPartyAcc_wujieAccount_cb ? 1 : v.getId() == R.id.thirdPartyAcc_weChatAccount_cb ? 2 : 3;
                    if (newSelectCheckBox == oldSelectCheckBox) { // 如果点击的是原来就选中的
                        if (v.getId() == R.id.thirdPartyAcc_wujieAccount_cb) {
                            setCheckBoxChecked(1);
                        } else if (v.getId() == R.id.thirdPartyAcc_weChatAccount_cb) {
                            setCheckBoxChecked(2);
                        } else if (v.getId() == R.id.thirdPartyAcc_alipayAccount_cb) {
                            setCheckBoxChecked(3);
                        }
                    } else {
                        User.pay_account_bind(newSelectCheckBox + "", "", "", "", "", ThirdPartAccountAty.this);
                    }
                }
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        L.e("ThirdPartAccountAty", jsonStr);

        // 获取三方绑定
        if (requestUrl.contains("payee_bind")) {
            payeeBindBean = GsonUtil.GsonToBean(jsonStr, PayeeBindBean.class);
            final PayeeBindBean.DataBean data = payeeBindBean.getData();

            // 先设置验证控件的显隐
            // 如果账号信息为空字符串，则显示验证控件
            if (data.getWxpay_accounts().equals("")) {
                thirdPartyAcc_verificationWeChat_tv.setVisibility(View.VISIBLE); // 点击验证按钮显示
                thirdPartyAcc_weChatAccount_cb.setVisibility(View.GONE); // 复选框隐藏
            } else {
                thirdPartyAcc_verificationWeChat_tv.setVisibility(View.GONE); // 点击验证按钮显示
                thirdPartyAcc_weChatAccount_cb.setVisibility(View.VISIBLE); // 复选框隐藏
            }
            if (data.getAlipay_accounts().equals("")) {
                thirdPartyAcc_verificationAlipay_tv.setVisibility(View.VISIBLE); // 点击验证按钮显示
                thirdPartyAcc_alipayAccount_cb.setVisibility(View.GONE); // 复选框隐藏
            } else {
                thirdPartyAcc_verificationAlipay_tv.setVisibility(View.GONE); // 点击验证按钮显示
                thirdPartyAcc_alipayAccount_cb.setVisibility(View.VISIBLE); // 复选框隐藏
            }

            // 其次设置复选框的选中状态
            int i = Integer.parseInt(data.getDefault_account());
            oldSelectCheckBox = i;
            setCheckBoxChecked(i);

            // 再次设置显示的数据
            if (!data.getPhone().equals("")) {
                thirdPartyAcc_wujieAccount_tv.setText(data.getPhone()); // 设置平台账户
            }
            if (!data.getWxpay_accounts().equals("")) {
                thirdPartyAcc_weChatAccount_tv.setText(data.getWxpay_name()); // 设置微信账户
            }
            if (!data.getAlipay_accounts().equals("")) {
                thirdPartyAcc_alipayAccount_et.setText(data.getAlipay_accounts()); // 设置支付宝账户
                thirdPartyAcc_alipayAccount_et.setCursorVisible(false);
                thirdPartyAcc_alipayAccount_et.setFocusable(false);
            } else {
                thirdPartyAcc_alipayAccount_et.setFocusable(true);
                thirdPartyAcc_alipayAccount_et.setCursorVisible(true);
            }

            // 最后判断是否设置支付密码，如果未设置，则提示其跳转至设置支付密码界面
            if (data.getIs_pay_password() == 0) {
                // 未设置密码倒计时两秒跳入设置密码页
                showToast("请设置支付密码");
                new CustomCountDownTimer(2 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        Bundle bundle = new Bundle();
                        bundle.putString("is_pay_password", "0");
                        bundle.putString("phone", data.getPhone());
                        startActivity(EditPayPasswordAty.class, bundle);
                    }
                }.start();
            }
        }

        // 验证支付密码
        if (requestUrl.contains("verificationPayPwd")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String status = jsonObject.getJSONObject("data").getString("status");
                if (status.equals("1")) { // 验证成功
                    if (clickView == R.id.thirdPartyAcc_verificationWeChat_tv) {
                        // 微信验证
                        authorize(new Wechat(this));
                        User.pay_money_bind("wx", weChatOpenid, ThirdPartAccountAty.this);
                        showInputDialog();
                        return;
                    }
                    if (clickView == R.id.thirdPartyAcc_verificationAlipay_tv) {
                        // 支付宝验证
                        User.pay_money_bind("ali", alipayAccounts, ThirdPartAccountAty.this);
                        showInputDialog();
                        return;
                    }
                }
            } catch (JSONException e) {
            }
        }

        // 验证金额
        if (requestUrl.contains("pay_money_bind")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String message = jsonObject.getString("message");
                showToast(message);
            } catch (JSONException e) {
            }
        }

        // 收款账户绑定
        if (requestUrl.contains("pay_account_bind")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                showErrorTip(jsonObject.getString("message"));
                User.payeeBind(this);
            } catch (JSONException e) {
            }
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        // 收款账户绑定
        if (requestUrl.contains("pay_account_bind")) {
            showErrorTip(error.get("message"));
            User.payeeBind(this);
        }
    }

    /**
     * 设置复选框选中状态
     *
     * @param checkIndex 选中的索引
     */
    private void setCheckBoxChecked(int checkIndex) {
        thirdPartyAcc_wujieAccount_cb.setChecked(false);
        thirdPartyAcc_weChatAccount_cb.setChecked(false);
        thirdPartyAcc_alipayAccount_cb.setChecked(false);
        switch (checkIndex) {
            case 1:
                thirdPartyAcc_wujieAccount_cb.setChecked(true);
                break;
            case 2:
                thirdPartyAcc_weChatAccount_cb.setChecked(true);
                break;
            case 3:
                thirdPartyAcc_alipayAccount_cb.setChecked(true);
                break;
            default:
                break;
        }
    }

    /**
     * 验证转账弹窗
     */
    private void showInputDialog() {
        final EditText editText = new EditText(this);
        editText.setGravity(Gravity.CENTER);
        new AlertDialog.Builder(this)
                .setTitle("请输入金额")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String trim = editText.getText().toString().trim();
                        if (!trim.equals("")) {
                            if (clickView == R.id.thirdPartyAcc_verificationWeChat_tv) {
                                // 微信转账
                                User.pay_account_bind("", "wx", trim, weChatOpenid, nickName, ThirdPartAccountAty.this);
                            } else if (clickView == R.id.thirdPartyAcc_verificationAlipay_tv) {
                                // 支付宝转账
                                User.pay_account_bind("", "ali", trim, alipayAccounts, "", ThirdPartAccountAty.this);
                            }
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 输入支付密码验证框
     *
     * @param view
     */
    public void showPwdPop(View view) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
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
                                com.txd.hzj.wjlp.http.user.User.verificationPayPwd(et_password.getText().toString(), ThirdPartAccountAty.this);
                                showProgressDialog();
                                commonPopupWindow.dismiss();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void authorize(Platform plat) {
        // 获取微信openid
        weChatOpenid = plat.getDb().getUserId();
        // 获取微信昵称
        nickName = plat.getDb().getUserName();
        if (weChatOpenid != null && nickName != null) {
            thirdPartyAcc_weChatAccount_tv.setText(nickName);
            return;
        }
    }

}
