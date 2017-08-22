package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 1:35
 * 描述：设置密码
 * ===============Txunda===============
 */
public class RegisterSetPwdAty extends BaseAty {
    /**
     * 中间标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

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

    private String phone = "";

    private RegisterPst registerPst;
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置密码");
    }


    @Override
    @OnClick({R.id.countersign_pwd_iv, R.id.new_pwd_iv, R.id.register_success_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.new_pwd_iv:// 新密码
                if (newPwd) {
                    //隐藏密码
                    new_pwd_ev.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    new_pwd_iv.setImageResource(R.drawable.icon_untoggle_hzj);
                    newPwd = false;
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
                    countersign_pwd_iv.setImageResource(R.drawable.icon_untoggle_hzj);
                    couPwd = false;
                } else {
                    //显示密码
                    countersign_pwd_ev.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    countersign_pwd_iv.setImageResource(R.drawable.icon_toggle_hzj);
                    couPwd = true;
                }
                break;
            case R.id.register_success_tv:

                password = new_pwd_ev.getText().toString();
                String confirmPassword = countersign_pwd_ev.getText().toString();

                registerPst.register(phone, password, confirmPassword);
                hideKeyBoard();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_register_set_pwd;
    }

    @Override
    protected void initialized() {
        phone = getIntent().getStringExtra("phone");
        registerPst = new RegisterPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("register")) {
            showRightTip("注册并登陆成功");
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            application.setUserInfo(data);
            Config.setLoginState(true);

            PreferencesUtils.putString(this, "phone", phone);
            PreferencesUtils.putString(this, "pwd", password);
            PreferencesUtils.putString(this, "token", data.get("token"));

            registerPst.toLogin(data.get("easemob_account"), data.get("easemob_pwd"));
            finish();
        }
    }
}
