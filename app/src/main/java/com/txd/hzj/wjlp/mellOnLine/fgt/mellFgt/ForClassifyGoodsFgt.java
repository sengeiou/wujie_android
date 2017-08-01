package com.txd.hzj.wjlp.mellOnLine.fgt.mellFgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellGoodsAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：下午 5:58
 * 描述：分类商品
 * ===============Txunda===============
 */
public class ForClassifyGoodsFgt extends BaseFgt {
    private String title;

    @ViewInject(R.id.mell_goods_rv)
    private RecyclerView mell_goods_rv;
    private int height = 0;
    private MellGoodsAdapter mellGoodsAdapter;

    public static ForClassifyGoodsFgt newInstance(String title) {
        ForClassifyGoodsFgt fragment = new ForClassifyGoodsFgt();
        fragment.title = title;
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mell_goods_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mell_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        mell_goods_rv.setAdapter(mellGoodsAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_for_classify_goods;
    }

    @Override
    protected void initialized() {
        height = ToolKit.dip2px(getActivity(), 4);
        mellGoodsAdapter = new MellGoodsAdapter(getContext(), 0);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
