package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("修改登录密码");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_edit_login_password_li;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
