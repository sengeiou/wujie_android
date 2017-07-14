package com.txd.hzj.wjlp.mellOffLine.fgt;

import android.os.Bundle;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 4:41
 * 描述：自营商品
 * ===============Txunda===============
 */
public class GoodsByMySelfFgt extends BaseFgt {

    public GoodsByMySelfFgt() {
    }

    private int page;

    public static GoodsByMySelfFgt newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        GoodsByMySelfFgt pageFragment = new GoodsByMySelfFgt();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_goods_by_my_self;
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
