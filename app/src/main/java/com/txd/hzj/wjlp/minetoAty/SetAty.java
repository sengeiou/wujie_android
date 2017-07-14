package com.txd.hzj.wjlp.minetoAty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * Created by lienchao on 2017/7/13 0013.
 */

public class SetAty extends BaseAty {
    /**
     * 设置标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 个人资料
     * */
    @ViewInject(R.id.rel_editprofile)
    public RelativeLayout rel_editprofile;
    /**
     * 修改登录密码
     * */
    @ViewInject(R.id.rel_editpassword)
    private RelativeLayout rel_editpassword;
    /**
     * 修改支付密码
     * */
    @ViewInject(R.id.rel_editpaypassword)
    private RelativeLayout rel_editpaypassword;
    /**
     * 实名认证
     * */
    @ViewInject(R.id.rel_realname)
    private RelativeLayout rel_realname;
    /**
     * 绑定手机
     * */
    @ViewInject(R.id.rel_bind_phone)
    private RelativeLayout rel_bind_phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置");
        initEvent();
    }
    private void initEvent() {
        rel_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetAty.this,EditProfileAty.class));
            }
        });
        rel_editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetAty.this,EditLoginPasswordAty.class));
            }
        });
        rel_editpaypassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetAty.this,EditPayPasswordAty.class));
            }
        });
        rel_realname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetAty.this,RealnameAty.class));
            }
        });
        rel_bind_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetAty.this,BindPhoneAty.class));
            }
        });
    }

    @Override
    @OnClick({R.id.rel_bind_phone,})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_set;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
