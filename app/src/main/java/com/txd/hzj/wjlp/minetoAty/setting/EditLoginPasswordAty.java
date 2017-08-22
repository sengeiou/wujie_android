package com.txd.hzj.wjlp.minetoAty.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * Created by lienchao on 2017/7/14 0014.
 */

public class EditLoginPasswordAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private String is_password = "0";

    @ViewInject(R.id.rel_editprofile)
    private LinearLayout rel_editprofile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (is_password.equals("0")) {
            titlt_conter_tv.setText("修改登录密码");
            rel_editprofile.setVisibility(View.VISIBLE);
        } else {
            titlt_conter_tv.setText("设置登录密码");
            rel_editprofile.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_edit_login_password_li;
    }

    @Override
    protected void initialized() {
        is_password = getIntent().getStringExtra("is_password");
    }

    @Override
    protected void requestData() {

    }
}
