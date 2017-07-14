package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

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
            case R.id.register_success_tv:
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_register_set_pwd;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
