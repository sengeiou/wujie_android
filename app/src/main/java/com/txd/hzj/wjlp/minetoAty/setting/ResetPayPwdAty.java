package com.txd.hzj.wjlp.minetoAty.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/17 0017
 * 时间：下午 3:26
 * 描述：重置支付密码
 * ===============Txunda===============
 */
public class ResetPayPwdAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.user_phone_tv)
    private TextView user_phone_tv;
    private String phone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("重置支付密码");
        user_phone_tv.setText("手机号    " + phone);
    }

    @Override
    @OnClick({R.id.find_pay_pwd_next_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.find_pay_pwd_next_tv:// 下一步
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                startActivity(ResetPayPwdSecAty.class, bundle);
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_reset_pay_pwd_hzj;
    }

    @Override
    protected void initialized() {
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    protected void requestData() {

    }
}
