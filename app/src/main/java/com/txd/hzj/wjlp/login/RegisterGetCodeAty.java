package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CodeCountDown;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 1:20
 * 描述：注册获取验证码
 * ===============Txunda===============
 */
public class RegisterGetCodeAty extends BaseAty {

    /**
     * 中间标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.register_get_code_tv)
    private TextView register_get_code_tv;
    private CodeCountDown codeCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("输入验证码");
        if (codeCountDown == null) {
            codeCountDown = new CodeCountDown(60000, 1000, this, register_get_code_tv);
        }
        codeCountDown.start();
    }

    @Override
    @OnClick({R.id.register_next_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_next_tv:// 下一步
                startActivity(RegisterSetPwdAty.class,null);
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_register_get_code;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
