package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：下午 4:02
 * 描述：位置(10-6位置)
 * ===============Txunda===============
 */
public class FindHouseByMapAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("位置");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_find_house_by_map;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
