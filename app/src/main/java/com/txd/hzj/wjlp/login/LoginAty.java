package com.txd.hzj.wjlp.login;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import org.w3c.dom.Text;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：下午 7:10
 * 描述：登录，注册(50-1 登录，50-5 注册)
 * ===============Txunda===============
 */
public class LoginAty extends BaseAty {
    /**
     * 登录
     */
    @ViewInject(R.id.to_login_tv)
    private TextView to_login_tv;
    /**
     * 注册
     */
    @ViewInject(R.id.to_register_tv)
    private TextView to_register_tv;

    /**
     * 手机号
     */
    @ViewInject(R.id.phone_ev)
    private EditText phone_ev;
    /**
     * 密码
     */
    @ViewInject(R.id.pwd_ev)
    private EditText pwd_ev;

    /**
     * 登录，注册第一步
     */
    @ViewInject(R.id.to_login_or_register_tv)
    private TextView to_login_or_register_tv;

    /**
     * 三方登录提示
     */
    @ViewInject(R.id.use_trilateral_lin_layout)
    private LinearLayout use_trilateral_lin_layout;
    private int type = 0;

    @ViewInject(R.id.for_third_layout)
    private LinearLayout for_third_layout;

    /**
     * 服务条款
     */
    @ViewInject(R.id.terms_of_service_tv)
    private TextView terms_of_service_tv;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.longin_title_layout);
        toChangeTextViewStatus(0);
        for_third_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, terms_of_service_tv, "继续表示已经阅读并同意《服务条款》", 11,
                ContextCompat.getColor(this, R.color.theme_color));
    }

    @Override
    @OnClick({R.id.to_login_tv, R.id.to_register_tv, R.id.forget_pwd_tv, R.id.to_login_or_register_tv,
            R.id.share_to_wachar, R.id.share_to_qq, R.id.share_to_sine, R.id.terms_of_service_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_login_tv:// 登录(标题栏)
                type = 0;
                toChangeTextViewStatus(0);
                pwd_ev.setVisibility(View.VISIBLE);
                to_login_or_register_tv.setText("登录");
                use_trilateral_lin_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.to_register_tv:// 注册(标题栏)
                type = 1;
                toChangeTextViewStatus(1);
                pwd_ev.setVisibility(View.GONE);
                to_login_or_register_tv.setText("下一步");
                use_trilateral_lin_layout.setVisibility(View.GONE);
                break;
            case R.id.forget_pwd_tv:// 忘记密码
                startActivity(FindPwgBackHzjAty.class, null);
                break;
            case R.id.to_login_or_register_tv:// 登录，注册
                if (0 == type) {//登录
                    Config.isLogin();
                    finish();
                } else {// 注册下一步
                    startActivity(RegisterGetCodeAty.class, null);
                    finish();
                }
                break;
            case R.id.share_to_wachar:// 新浪微博
            case R.id.share_to_qq:// QQ
            case R.id.share_to_sine:// 新浪微博
                startActivity(BindAccountAty.class, null);
                break;
            case R.id.terms_of_service_tv:// 服务条款
                bundle = new Bundle();
                bundle.putInt("from", 2);
                startActivity(NoticeDetailsAty.class, bundle);
                break;
        }
    }

    private void toChangeTextViewStatus(int i) {
        to_login_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        to_login_tv.setBackgroundResource(R.drawable.shape_login_tv_unselect);
        to_register_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        to_register_tv.setBackgroundResource(R.drawable.shape_register_tv_unselect);

        if (0 == i) {
            to_login_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            to_login_tv.setBackgroundResource(R.drawable.shape_login_tv_selected);
        } else {
            to_register_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            to_register_tv.setBackgroundResource(R.drawable.shape_register_tv_select);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_login;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
