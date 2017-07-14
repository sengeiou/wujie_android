package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tools.MyCount;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

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
                if (myCount == null) {
                    myCount = new MyCount(60000, 1000, bind_get_code_tv);
                }
                myCount.start();
                in_put_code_ev.setHint("输入验证码");
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
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_bind_account;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
