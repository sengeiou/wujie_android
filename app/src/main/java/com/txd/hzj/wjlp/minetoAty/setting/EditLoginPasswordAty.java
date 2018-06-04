package com.txd.hzj.wjlp.minetoAty.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.hyphenate.EMCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.login.LoginAty;

/**
 * Created by lienchao on 2017/7/14 0014.
 */

public class EditLoginPasswordAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private String is_password = "0";

    @ViewInject(R.id.rel_editprofile)
    private LinearLayout rel_editprofile;

    /**
     * 旧密码
     */
    @ViewInject(R.id.old_pwd_tv)
    private EditText old_pwd_tv;

    /**
     * 新密码
     */
    @ViewInject(R.id.new_pwd_tv)
    private TextView new_pwd_tv;

    /**
     * 确认新密码
     */
    @ViewInject(R.id.re_pwd_tv)
    private TextView re_pwd_tv;

    private UserPst userPst;
    private String newPassword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (is_password.equals("1")) {
            titlt_conter_tv.setText("修改登录密码");
            rel_editprofile.setVisibility(View.VISIBLE);
        } else {
            titlt_conter_tv.setText("设置登录密码");
            rel_editprofile.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick({R.id.to_change_pwd_tv})
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.to_change_pwd_tv:// 修改密码
                newPassword = new_pwd_tv.getText().toString();
                String rePassword = re_pwd_tv.getText().toString();
                if (is_password.equals("1")) {
                    String oldPassword = old_pwd_tv.getText().toString();
                    userPst.changePassword(oldPassword, newPassword, rePassword);
                } else {
                    userPst.setPassword(newPassword, rePassword);
                }
                hideSoftInput();
                break;
        }
    }

    /**
     * 强制收起输入键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(old_pwd_tv.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(new_pwd_tv.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(re_pwd_tv.getWindowToken(), 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_edit_login_password_li;
    }

    @Override
    protected void initialized() {
        is_password = getIntent().getStringExtra("is_password");
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("changePassword")) {
            showRightTip("修改成功");
            Config.setLoginState(false);
            PreferencesUtils.putString(this, "pwd", "");
            // 收起键盘
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(new_pwd_tv.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            AppManager.getInstance().killAllActivity();
            startActivity(LoginAty.class,null);
//            finish();
//            logout();
            return;
        }
        if (requestUrl.contains("setPassword")) {
            showRightTip("设置成功");
            PreferencesUtils.putString(this, "pwd", newPassword);
            finish();
        }
    }

    private void logout() {
        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                L.e("=====退出登录=====", "成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loginoutToLogin();
                        AppManager.getInstance().killActivity(SetAty.class);
//                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
                L.e("=====退出登录=====", "退出中");
            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorTip("退出失败，请重新操作");
                    }
                });
                L.e("=====退出登录=====", "失败：" + code + "-----" + message);
            }
        });
    }
}
