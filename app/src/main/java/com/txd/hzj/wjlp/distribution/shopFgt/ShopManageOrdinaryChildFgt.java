package com.txd.hzj.wjlp.distribution.shopFgt;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * 创建者：wjj
 * 创建时间：2018-06-11 上午 11:02
 * 功能描述： 普通商品管理界面
 */
@SuppressLint("ValidFragment")
public class ShopManageOrdinaryChildFgt extends BaseFgt {

    /**
     * Fragment标示
     * 0 为出售中
     * 1 为已下架
     * 2 是已售罄
     */
    private int from;

    @ViewInject(R.id.ordinaryChild_show_tv)
    private TextView ordinaryChild_show_tv;

    public ShopManageOrdinaryChildFgt(int index) {
        from = index;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_shopmanage_ordinary_child;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        ordinaryChild_show_tv.setText(from == 0 ? "出售中" : from == 1 ? "已下架" : "已售罄");
    }

    @Override
    protected void immersionInit() {
    }
}
