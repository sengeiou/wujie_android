package com.txd.hzj.wjlp.minetoAty.mell;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：上午 11:50
 * 描述：商家设置
 */
public class MellSettingAty extends BaseAty {

    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置");
    }

    @Override
    @OnClick({R.id.mell_info_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.mell_info_tv:
                startActivity(EditMellInfoAty.class,null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_setting;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
