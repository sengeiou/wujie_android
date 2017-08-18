package com.txd.hzj.wjlp.minetoAty.setting;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.CodeCountDown;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj 
 * 日期：2017/8/17 0017
 * 时间：下午 3:39
 * 描述：第二步充值支付密码
 * ===============Txunda===============
 */
public class ResetPayPwdSecAty extends BaseAty {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("重置支付密码");

        if (codeCountDown == null) {// 倒计时
            codeCountDown = new CodeCountDown(60000, 1000, this, register_get_code_tv);
        }
        codeCountDown.start();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_reset_pay_pwd_sec_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
