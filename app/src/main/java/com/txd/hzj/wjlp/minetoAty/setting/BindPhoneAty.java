package com.txd.hzj.wjlp.minetoAty.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.tools.MyCount;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/23 0023
 * 时间：上午 11:31
 * 描述：绑定手机号
 * ===============Txunda===============
 */
public class BindPhoneAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.old_phone_tv)
    private TextView old_phone_tv;

    private String phone = "";

    /**
     * 获取验证码
     */
    @ViewInject(R.id.get_code_tv)
    private TextView get_code_tv;

    private MyCount myCount;

    private RegisterPst registerPst;

    @ViewInject(R.id.mod_bind_code_ev)
    private EditText mod_bind_code_ev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("换绑手机");
        old_phone_tv.setText(phone);
    }

    @Override
    @OnClick({R.id.get_code_tv, R.id.btn_next})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.get_code_tv:// 获取验证码
                registerPst.getVerify(phone, "mod_bind");
                break;
            case R.id.btn_next:// 下一步
                String verify = mod_bind_code_ev.getText().toString();
                registerPst.checkVerify(phone,"mod_bind",verify);
                hideSoftInput();
                break;
        }
    }

    /**
     * 强制收起输入键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mod_bind_code_ev.getWindowToken(), 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_bind_phone_li;
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
        if(requestUrl.contains("sendVerify")){
            if(myCount==null){
                myCount = new MyCount(60000,1000,get_code_tv);
            }
            myCount.start();
            return;
        }
        if(requestUrl.contains("checkVerify")){
            Bundle bundle = new Bundle();
            bundle.putInt("from", 0);
            startActivity(BindNewPhoneAty.class, bundle);
            finish();
        }
    }
}
