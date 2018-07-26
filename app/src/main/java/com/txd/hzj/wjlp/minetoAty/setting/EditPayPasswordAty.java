package com.txd.hzj.wjlp.minetoAty.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.setting.ResetPayPwdAty;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/23 0023
 * 时间：上午 10:03
 * 描述：支付密码(修改，设置)
 *
 */
public class EditPayPasswordAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private String is_pay_password = "0";

    @ViewInject(R.id.rel_editprofile)
    private LinearLayout rel_editprofile;

    @ViewInject(R.id.forget_pay_pwd_tv)
    private TextView forget_pay_pwd_tv;

    private UserPst userPst;

    /**
     * 旧支付密码
     */
    @ViewInject(R.id.old_pay_pwd_ev)
    private EditText old_pay_pwd_ev;

    /**
     * 新支付密码
     */
    @ViewInject(R.id.new_pay_pwd_ev)
    private EditText new_pay_pwd_ev;

    /**
     * 确认新支付密码
     */
    @ViewInject(R.id.re_pay_pwd_tv)
    private EditText re_pay_pwd_tv;
    private String phone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (is_pay_password.equals("1")) {
            rel_editprofile.setVisibility(View.VISIBLE);
            forget_pay_pwd_tv.setVisibility(View.VISIBLE);
            titlt_conter_tv.setText("修改支付密码");
        } else {
            titlt_conter_tv.setText("设置支付密码");
            rel_editprofile.setVisibility(View.GONE);
            forget_pay_pwd_tv.setVisibility(View.GONE);
        }
    }


    @Override
    @OnClick({R.id.forget_pay_pwd_tv, R.id.to_set_pay_pwd_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.forget_pay_pwd_tv://忘记支付密码
                Bundle bundle = new Bundle();
                if (phone.equals("")) {
                    bundle.putInt("from", 1);
                    startActivity(BindNewPhoneAty.class, bundle);
                }
                bundle.putString("phone", phone);
                startActivity(ResetPayPwdAty.class, bundle);
                break;
            case R.id.to_set_pay_pwd_tv:
                String newPayPwd = new_pay_pwd_ev.getText().toString();
                String rePayPwd = re_pay_pwd_tv.getText().toString();
                if (is_pay_password.equals("1")) {// 修改支付密码
                    String oldPayPwd = old_pay_pwd_ev.getText().toString();
                    userPst.rePayPwd(newPayPwd, rePayPwd, oldPayPwd);
                } else {
                    userPst.setPayPwd(newPayPwd, rePayPwd);
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
        imm.hideSoftInputFromWindow(new_pay_pwd_ev.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(old_pay_pwd_ev.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(re_pay_pwd_tv.getWindowToken(), 0);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.aty_edit_pay_password_li;
    }

    @Override
    protected void initialized() {
        is_pay_password = getIntent().getStringExtra("is_pay_password");
        phone = getIntent().getStringExtra("phone");
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("setPayPwd")) {
            showRightTip("设置成功");
            finish();
            return;
        }
        if (requestUrl.contains("rePayPwd")) {
            showRightTip("修改成功");
            finish();
        }
    }
}
