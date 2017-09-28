package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tools.MyCount;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.jpush.JpushSetTagAndAlias;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：上午 11:07
 * 描述：50-4绑定
 * ===============Txunda===============
 */
public class BindAccountAty extends BaseAty {

    /**
     * 中间标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.pwd_tv)
    public TextView pwd_tv;
    @ViewInject(R.id.cou_pwd_tv)
    public TextView cou_pwd_tv;

    /**
     * 获取验证码
     */
    @ViewInject(R.id.bind_get_code_tv)
    private TextView bind_get_code_tv;

    /**
     * 验证码
     */
    @ViewInject(R.id.in_put_code_ev)
    private EditText in_put_code_ev;

    /**
     * 密码
     */
    @ViewInject(R.id.new_pwd_ev)
    private EditText new_pwd_ev;
    /**
     * 确认密码
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

    private MyCount myCount;

    private String invite_code = "";

    private RegisterPst registerPst;
    /**
     * 三方登陆绑定手机号
     */
    @ViewInject(R.id.third_past_bind_phone_ev)
    private EditText third_past_bind_phone_ev;
    private String phone = "";
    private String bind_id = "";
    private int skip_type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("账号绑定");

        pwd_tv.setText("密码");
        cou_pwd_tv.setText("密码");

        new_pwd_ev.setHint("6-14位字符");
        countersign_pwd_ev.setHint("重复密码");
    }

    @Override
    @OnClick({R.id.bind_get_code_tv, R.id.new_pwd_iv, R.id.countersign_pwd_iv, R.id.bind_success_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bind_get_code_tv:// 获取验证码
                phone = third_past_bind_phone_ev.getText().toString();
                registerPst.getVerify(phone, "re_bind");
                break;
            case R.id.new_pwd_iv:// 新密码
                if (newPwd) {
                    //隐藏密码
                    new_pwd_ev.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPwd = false;
                    new_pwd_iv.setImageResource(R.drawable.icon_toggle_hzj);
                } else {
                    //显示密码
                    new_pwd_ev.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPwd = true;
                    new_pwd_iv.setImageResource(R.drawable.icon_untoggle_hzj);
                }
                break;
            case R.id.countersign_pwd_iv:// 确认新密码
                if (couPwd) {
                    //隐藏密码
                    countersign_pwd_ev.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    couPwd = false;
                    countersign_pwd_iv.setImageResource(R.drawable.icon_toggle_hzj);
                } else {
                    //显示密码
                    countersign_pwd_ev.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    couPwd = true;
                    countersign_pwd_iv.setImageResource(R.drawable.icon_untoggle_hzj);
                }
                break;
            case R.id.bind_success_tv:// 完成
                String verify = in_put_code_ev.getText().toString();
                registerPst.otherLoginBind(bind_id, phone, verify, invite_code);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_bind_account;
    }

    @Override
    protected void initialized() {
        invite_code = getIntent().getStringExtra("invite_code");
        bind_id = getIntent().getStringExtra("bind_id");
        skip_type = getIntent().getIntExtra("skip_type", 1);
        registerPst = new RegisterPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("sendVerify")) {
            if (myCount == null) {
                myCount = new MyCount(60000, 1000, bind_get_code_tv);
            }
            myCount.start();
            in_put_code_ev.setHint("输入验证码");
            return;
        }
        if (requestUrl.contains("otherLoginBind")) {
            showRightTip("登录成功");
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            application.setUserInfo(data);
            Config.setLoginState(true);

            PreferencesUtils.putString(this, "token", data.get("token"));
            // 友盟统计
            MobclickAgent.onProfileSignIn(data.get("user_id"));
            // 极光设置Tag或者别名
            JpushSetTagAndAlias.getInstance().setAlias(getApplicationContext());
            JpushSetTagAndAlias.getInstance().setTag(getApplicationContext());
            // 环信登录
            registerPst.toLogin(data.get("easemob_account"), data.get("easemob_pwd"));
            if (0 == skip_type) {
                startActivity(MainAty.class, null);
                AppManager.getInstance().killAllActivity();
            }
            finish();
        }
    }
}
