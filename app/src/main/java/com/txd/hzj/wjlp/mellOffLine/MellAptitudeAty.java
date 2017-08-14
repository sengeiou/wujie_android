package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/12 0012
 * 时间：下午 5:09
 * 描述：商家资质
 * ===============Txunda===============
 */
public class MellAptitudeAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商家资质");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_aptitude_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
