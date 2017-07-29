package com.txd.hzj.wjlp.mellOffLine.fgt;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mellOffLine.dialog.CouponDialog;
import com.txd.hzj.wjlp.mellOffLine.dialog.MellCouponDialog;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellGoodsAdapter;

public class ForGoodsByOtherFgt extends BaseFgt {

    private String type;

    /**
     * 商品列表
     */
    @ViewInject(R.id.off_line_goods_rv)
    private RecyclerView off_line_goods_rv;
    private MellGoodsAdapter mellGoodsAdapter;

    private MellCouponDialog mellCouponDialog;

    public static ForGoodsByOtherFgt newInstance(String type) {
        ForGoodsByOtherFgt fragment = new ForGoodsByOtherFgt();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        off_line_goods_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        off_line_goods_rv.setAdapter(mellGoodsAdapter);
    }

    @Override
    @OnClick({R.id.check_all_coupon_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.check_all_coupon_tv:// 查看更多优惠券
                mellCouponDialog = new MellCouponDialog(getActivity());
                mellCouponDialog.show();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_for_goods_by_other;
    }

    @Override
    protected void initialized() {
        mellGoodsAdapter = new MellGoodsAdapter(getActivity(), 1);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
