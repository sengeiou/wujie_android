package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：Zyf
 * 功能描述：小店营收
 * 联系方式：无
 */
public class ShopRevenue extends BaseAty implements View.OnClickListener{

    private TextView titlt_conter_tv;

    private LinearLayout shop_person_title_manage;

    private TextView shop_shopkeeper;

    private TextView shop_person;

    private ImageView time_select;


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
        time_select=findViewById(R.id.time_select_img);


        titlt_conter_tv.setVisibility(View.GONE);
        shop_person_title_manage.setVisibility(View.VISIBLE);
        shop_shopkeeper.setText("销售额");
        shop_person.setText("净收益");
        shop_shopkeeper.setOnClickListener(this);
        shop_person.setOnClickListener(this);
        time_select.setOnClickListener(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.shop_shopkeeper:
                shop_shopkeeper.setTextColor(this.getResources().getColor(R.color.white));
                shop_shopkeeper.setBackgroundResource(R.drawable.shop_title_choice_left);
                shop_person.setBackgroundResource(R.drawable.shop_title_unchoice_right);
                shop_person.setTextColor(this.getResources().getColor(R.color.titleColors));
                break;
            case R.id.shop_person:
                shop_shopkeeper.setTextColor(this.getResources().getColor(R.color.titleColors));
                shop_shopkeeper.setBackgroundResource(R.drawable.shop_title_unchoice_left);
                shop_person.setBackgroundResource(R.drawable.shop_title_choice_right);
                shop_person.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.time_select_img:
                break;
        }

    }
}
