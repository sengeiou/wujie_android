package com.txd.hzj.wjlp.minetoAty.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.tools.MyCount;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.register.RegisterPst;
import com.txd.hzj.wjlp.http.user.UserPst;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/23 0023
 * 时间：上午 11:53
 * 描述：绑定手机，换绑手机
 * ===============Txunda===============
 */
public class BindNewPhoneAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private int type = 0;

    /**
     * 获取验证码
     */
    @ViewInject(R.id.bind_phone_get_code_tv)
    private TextView bind_phone_get_code_tv;

    private MyCount count;

    private RegisterPst registerPst;

    private String phone = "";

    /**
     * 验证码
     */
    @ViewInject(R.id.bind_phone_ev)
    private EditText bind_phone_ev;

    /**
     * 验证码
     */
    @ViewInject(R.id.get_code_for_bind_tv)
    private EditText get_code_for_bind_tv;

    private UserPst userPst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (0 == type) {
            titlt_conter_tv.setText("换绑手机");
        } else {
            titlt_conter_tv.setText("绑定手机");
        }
    }

    @Override
    @OnClick({R.id.bind_phone_get_code_tv, R.id.btn_next})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bind_phone_get_code_tv:
                phone = bind_phone_ev.getText().toString();
                registerPst.getVerify(phone, "re_bind");
                break;
            case R.id.btn_next:
                String verify = get_code_for_bind_tv.getText().toString();
                userPst.changePhone(phone, verify);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_bind_new_phone_li;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("from", 0);
        registerPst = new RegisterPst(this);
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("sendVerify")) {
            if (count == null) {
                count = new MyCount(60000, 1000, bind_phone_get_code_tv);
            }
            count.start();
            return;
        }
        if (requestUrl.contains("changePhone")) {
            showRightTip("绑定成功");
            PreferencesUtils.putString(this, "phone", phone);
            finish();
        }
    }
}
