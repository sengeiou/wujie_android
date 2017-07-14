package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class OrderCenterAty extends BaseAty implements View.OnClickListener{
    /**
     * 线上商城
     * */
    @ViewInject(R.id.tv_online_shops)
    TextView tv_online_shops;
    /**
     * 设置标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单中心");
        initEvent();
    }

    private void initEvent() {
        tv_online_shops.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_order_center_li;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_online_shops:
                startActivity(OnlineShopAty.class,null);
                break;
        }
        super.onClick(v);
    }
}
