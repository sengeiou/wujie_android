package com.txd.hzj.wjlp.distribution.shopmanage;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 15:14
 * 功能描述：订单管理页面
 * 联系方式：无
 */
public class ShopOrderManage extends BaseAty {

    private TabLayout tabView;
    private TextView titleName;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_order_manage;
    }

    @Override
    protected void initialized() {
        tabView = findViewById(R.id.shop_tab_view);
        titleName = findViewById(R.id.titlt_conter_tv);

    }

    @Override
    protected void requestData() {
        titleName.setText("订单管理");
        tabView.addTab(tabView.newTab().setText("全部"));
        tabView.addTab(tabView.newTab().setText("代付款"));
        tabView.addTab(tabView.newTab().setText("代发货"));
        tabView.addTab(tabView.newTab().setText("待收货"));
        tabView.addTab(tabView.newTab().setText("已完成"));
    }
}
