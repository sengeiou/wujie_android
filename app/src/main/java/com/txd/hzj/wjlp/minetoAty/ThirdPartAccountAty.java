package com.txd.hzj.wjlp.minetoAty;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.User;

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
    @ViewInject(R.id.thirdPartyAcc_alipayAccount_tv)
    private TextView thirdPartyAcc_alipayAccount_tv;
    // 支付宝复选框
    @ViewInject(R.id.thirdPartyAcc_alipayAccount_cb)
    private CheckBox thirdPartyAcc_alipayAccount_cb;
    // 验证支付宝
    @ViewInject(R.id.thirdPartyAcc_verificationAlipay_tv)
    private TextView thirdPartyAcc_verificationAlipay_tv;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_third_party_accounts;
    }

    @Override
    protected void initialized() {

        titlt_conter_tv.setText("三方收款账户");

        // 平台账号
//        thirdPartyAcc_wujieAccount_tv;
        // 平台复选框
//        thirdPartyAcc_wujieAccount_cb;
        // 微信账号
//        thirdPartyAcc_weChatAccount_tv;
        // 微信账号复选框
        thirdPartyAcc_weChatAccount_cb.setVisibility(View.GONE);
        // 验证微信按钮
//        thirdPartyAcc_verificationWeChat_tv;
        // 支付宝账号
//        thirdPartyAcc_alipayAccount_tv;
        // 支付宝复选框
        thirdPartyAcc_alipayAccount_cb.setVisibility(View.GONE);
        // 验证支付宝
//        thirdPartyAcc_verificationAlipay_tv;
    }

    @Override
    protected void requestData() {
        User.payeeBind(this);
    }

    @OnClick({R.id.thirdPartyAcc_verificationWeChat_tv, R.id.thirdPartyAcc_verificationAlipay_tv})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thirdPartyAcc_verificationWeChat_tv:
            case R.id.thirdPartyAcc_verificationAlipay_tv:
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("qazwsx", jsonStr);
        if (requestUrl.contains("payee_bind")) { // 获取三方绑定
        }
    }
}
