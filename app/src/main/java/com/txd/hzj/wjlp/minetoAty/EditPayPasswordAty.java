package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.setting.ResetPayPwdAty;

/**
 * Created by lienchao on 2017/7/14 0014.
 */

public class EditPayPasswordAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("修改支付密码");
    }

    @Override
    @OnClick({R.id.forget_pay_pwd_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.forget_pay_pwd_tv://忘记支付密码
                startActivity(ResetPayPwdAty.class,null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_edit_pay_password_li;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
