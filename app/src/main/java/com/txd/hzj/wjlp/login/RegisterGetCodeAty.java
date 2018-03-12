package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.tool.CodeCountDown;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 1:20
 * 描述：注册获取验证码
 * ===============Txunda===============
 */
public class RegisterGetCodeAty extends BaseAty {

    /**
     * 中间标题
     */
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

    @ViewInject(R.id.get_code_ev)
    private EditText get_code_ev;
    private String verify = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("输入验证码");

        registerPst.getVerify(phone, "activate");
    }

    @Override
    @OnClick({R.id.register_next_tv, R.id.register_get_code_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_next_tv:// 下一步
                verify = get_code_ev.getText().toString();
                registerPst.checkVerify(phone, verify);
                break;
            case R.id.register_get_code_tv:// 重新获取验证码
                registerPst.getVerify(phone, "activate");
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_register_get_code;
    }

    @Override
    protected void initialized() {
        registerPst = new RegisterPst(this);
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("sendVerify")) {// 获取验证码
            if (codeCountDown == null) {// 倒计时
                codeCountDown = new CodeCountDown(60000, 1000, this, register_get_code_tv);
            }
            codeCountDown.start();
            return;
        }
        if (requestUrl.contains("checkVerify")) {// 检查验证码

            Bundle bundle = new Bundle();
            bundle.putString("phone", phone);
            startActivity(RegisterSetPwdAty.class, bundle);
            finish();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        L.e("eeeeee"+error.get("message"));
    }
}
