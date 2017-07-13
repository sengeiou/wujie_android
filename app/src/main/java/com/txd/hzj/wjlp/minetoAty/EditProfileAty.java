package com.txd.hzj.wjlp.minetoAty;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * Created by lienchao on 2017/7/13 0013.
 */

public class EditProfileAty extends BaseAty{
    /**
     * 标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 修改后保存
     * */
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("修改个人资料");
        titlt_right_tv.setText("保存");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(Color.RED);
        initEvent();
    }

    private void initEvent() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_editprofile;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
