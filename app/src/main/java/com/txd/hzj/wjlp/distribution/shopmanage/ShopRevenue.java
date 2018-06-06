package com.txd.hzj.wjlp.distribution.shopmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 小店营收
 */
public class ShopRevenue extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    TextView titlt_conter_tv;

    @ViewInject(R.id.shop_person_title_manage)
    LinearLayout shop_person_title_manage;

    @ViewInject(R.id.shop_shopkeeper)
    TextView shop_shopkeeper;

    @ViewInject(R.id.shop_person)
    TextView shop_person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_revenue;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setVisibility(View.GONE);
        shop_person_title_manage.setVisibility(View.VISIBLE);
        shop_shopkeeper.setText("销售额");
        shop_person.setText("净收益");
    }

    @Override
    protected void requestData() {

    }
}
