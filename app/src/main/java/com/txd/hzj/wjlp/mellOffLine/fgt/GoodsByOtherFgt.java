package com.txd.hzj.wjlp.mellOffLine.fgt;


import android.os.Bundle;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 4:38
 * 描述：流转商品
 * ===============Txunda===============
 */
public class GoodsByOtherFgt extends BaseFgt {

    private int page;


    public static GoodsByOtherFgt newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        GoodsByOtherFgt pageFragment = new GoodsByOtherFgt();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_goods_by_other;
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
