package com.txd.hzj.wjlp.minetoAty.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.tool.CodeCountDown;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/17 0017
 * 时间：下午 3:39
 * 描述：第二步充值支付密码
 * ===============Txunda===============
 */
public class ResetPayPwdSecAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 倒计时
     */
    @ViewInject(R.id.register_get_code_tv)
    private TextView register_get_code_tv;
    /**
     * 倒计时
     */
    private CodeCountDown codeCountDown;

    private RegisterPst registerPst;
    private String phone = "";

    @ViewInject(R.id.new_pay_pwd_ev)
    private EditText new_pay_pwd_ev;

    @ViewInject(R.id.re_pay_pwd_tv)
    private EditText re_pay_pwd_tv;
    @ViewInject(R.id.get_code_ev)
    private EditText get_code_ev;

    private UserPst userPst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("重置支付密码");
    }

    @Override
    @OnClick({R.id.reset_pay_pwd_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.reset_pay_pwd_tv:
                String verify = get_code_ev.getText().toString();
                String pwd = new_pay_pwd_ev.getText().toString();
                String repwd = re_pay_pwd_tv.getText().toString();

                userPst.resetPayPwd(phone, verify, pwd, repwd);

                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_reset_pay_pwd_sec_hzj;
    }

    @Override
    protected void initialized() {
        registerPst = new RegisterPst(this);
        phone = getIntent().getStringExtra("phone");
        userPst = new UserPst(this);
    }


    @Override
    protected void requestData() {
        registerPst.getVerify(phone, "re_pay_pwd");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("sendVerify")) {// 验证码
            showRightTip(map.get("message"));
            if (codeCountDown == null) {// 倒计时
                codeCountDown = new CodeCountDown(60000, 1000, this, register_get_code_tv);
            }
            codeCountDown.start();
            return;
        }
        if (requestUrl.contains("resetPayPwd")) {// 忘记密码
            showRightTip(map.get("message"));
            finish();
        }
    }
}
