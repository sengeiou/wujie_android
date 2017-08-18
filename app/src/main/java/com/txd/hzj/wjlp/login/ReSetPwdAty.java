package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CodeCountDown;

public class ReSetPwdAty extends BaseAty {

    /**
     * 中间标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.get_code_tv)
    private TextView get_code_tv;

    /**
     * 新密码
     */
    @ViewInject(R.id.new_pwd_ev)
    private EditText new_pwd_ev;
    /**
     * 确认新密码
     */
    @ViewInject(R.id.countersign_pwd_ev)
    private EditText countersign_pwd_ev;

    /**
     * 显示隐藏新密码
     */
    @ViewInject(R.id.new_pwd_iv)
    private ImageView new_pwd_iv;
    /**
     * 显示隐藏queren
     */
    @ViewInject(R.id.countersign_pwd_iv)
    private ImageView countersign_pwd_iv;

    private boolean newPwd = false;
    private boolean couPwd = false;

    private CodeCountDown codeCountDown;

    private RegisterPst registerPst;
    private String phone = "";

    @ViewInject(R.id.reset_get_code_ev)
    private EditText reset_get_code_ev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("重置登录密码");

        registerPst.getVerify(phone, "retrieve");

    }

    @Override
    @OnClick({R.id.countersign_pwd_iv, R.id.new_pwd_iv, R.id.get_code_tv, R.id.get_pwd_success_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.new_pwd_iv:// 新密码
                if (newPwd) {
                    //隐藏密码
                    new_pwd_ev.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPwd = false;
                    new_pwd_iv.setImageResource(R.drawable.icon_untoggle_hzj);
                } else {
                    //显示密码
                    new_pwd_ev.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPwd = true;
                    new_pwd_iv.setImageResource(R.drawable.icon_toggle_hzj);
                }
                break;
            case R.id.countersign_pwd_iv:// 确认新密码
                if (couPwd) {
                    //隐藏密码
                    countersign_pwd_ev.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    couPwd = false;
                    countersign_pwd_iv.setImageResource(R.drawable.icon_untoggle_hzj);
                } else {
                    //显示密码
                    countersign_pwd_ev.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    couPwd = true;
                    countersign_pwd_iv.setImageResource(R.drawable.icon_toggle_hzj);
                }
                break;
            case R.id.get_code_tv:// 重新获取
                registerPst.getVerify(phone, "retrieve");
                break;
            case R.id.get_pwd_success_tv:// 完成
                String verify = reset_get_code_ev.getText().toString();
                String newPassword = new_pwd_ev.getText().toString();
                String confirmPassword = countersign_pwd_ev.getText().toString();
                registerPst.resetPassword(phone,verify, newPassword, confirmPassword);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_re_set_pwd;
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
        if (requestUrl.contains("sendVerify")) {
            if (codeCountDown == null) {
                codeCountDown = new CodeCountDown(60000, 1000, this, get_code_tv);
            }
            codeCountDown.start();
            return;
        }
        if (requestUrl.contains("resetPassword")) {
            showRightTip("重置成功");
            finish();
        }
    }
}
