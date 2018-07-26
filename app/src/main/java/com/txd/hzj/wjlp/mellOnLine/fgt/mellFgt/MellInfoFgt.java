package com.txd.hzj.wjlp.mellOnLine.fgt.mellFgt;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：下午 5:36
 * 描述：店铺信息
 */
public class MellInfoFgt extends BaseFgt {
    private String title;

    public static MellInfoFgt newInstance(String title) {
        MellInfoFgt fragment = new MellInfoFgt();
        fragment.title = title;
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mell_info;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void immersionInit() {

    }
}
