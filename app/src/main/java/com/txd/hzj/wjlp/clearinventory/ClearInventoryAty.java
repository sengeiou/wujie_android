package com.txd.hzj.wjlp.clearinventory;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/2/28 9:01
 * 功能描述：互清库存
 */
public class ClearInventoryAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.gradeTv)
    private TextView gradeTv;

    @ViewInject(R.id.moneyTv)
    private TextView moneyTv;

    @ViewInject(R.id.couldSaleTv)
    private TextView couldSaleTv;

    @ViewInject(R.id.consignmentTv)
    private TextView consignmentTv;

    @ViewInject(R.id.completeTv)
    private TextView completeTv;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clear_inventory;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("互清库存");
    }

    @Override
    protected void requestData() {

    }


    @Override
    @OnClick({R.id.saleLayout, R.id.tradeLayout,R.id.pickUpGoodsLayout, R.id.refundLayout,R.id.manageLayout, R.id.incomeLayout,R.id.strategyLayout, R.id.problemLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saleLayout:
                startActivity(ConsignmentAty.class,null);
                break;
            case R.id.tradeLayout:
                break;
            case R.id.pickUpGoodsLayout:
                break;
            case R.id.refundLayout:
                break;
            case R.id.manageLayout:
                break;
            case R.id.incomeLayout:
                break;
            case R.id.strategyLayout:
                break;
            case R.id.problemLayout:
                break;
        }
    }
}
