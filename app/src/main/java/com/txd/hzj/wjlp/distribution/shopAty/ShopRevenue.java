package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：Zyf
 * 功能描述：小店营收
 * 联系方式：无
 */
public class ShopRevenue extends BaseAty {

    TextView titlt_conter_tv;

    LinearLayout shop_person_title_manage;

    TextView shop_shopkeeper;

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
        titlt_conter_tv=findViewById(R.id.titlt_conter_tv);
        shop_person_title_manage=findViewById(R.id. shop_person_title_manage);
        shop_shopkeeper=findViewById(R.id.shop_shopkeeper);
        shop_person=findViewById(R.id.shop_person);


        titlt_conter_tv.setVisibility(View.GONE);
        shop_person_title_manage.setVisibility(View.VISIBLE);
        shop_shopkeeper.setText("销售额");
        shop_person.setText("净收益");
    }

    @Override
    protected void requestData() {

    }
}
