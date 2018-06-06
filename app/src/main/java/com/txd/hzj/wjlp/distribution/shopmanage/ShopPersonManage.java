package com.txd.hzj.wjlp.distribution.shopmanage;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 14:36
 * 功能描述：顾客管理页面
 * 联系方式：无
 */
public class ShopPersonManage extends BaseAty implements View.OnClickListener {

    private LinearLayout titleManage;
    private TextView shopKeeper;
    private TextView person;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_person_manage;
    }

    @Override
    protected void initialized() {
    findViewById(R.id.titlt_conter_tv).setVisibility(View.GONE);
        titleManage = findViewById(R.id.shop_person_title_manage);
        titleManage.setVisibility(View.VISIBLE);
        //店主身份
        shopKeeper = findViewById(R.id.shop_shopkeeper);
        //普通顾客
        person = findViewById(R.id.shop_person);
        //注册点击事件
        shopKeeper.setOnClickListener(this);
        person.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.shop_shopkeeper:
                shopKeeper.setTextColor(this.getResources().getColor(R.color.white));
                shopKeeper.setBackgroundColor(this.getResources().getColor(R.color.titleColors));
                person.setBackgroundColor(this.getResources().getColor(R.color.white));
                person.setTextColor(this.getResources().getColor(R.color.titleColors));
                break;
            case R.id.shop_person:
                shopKeeper.setTextColor(this.getResources().getColor(R.color.titleColors));
                shopKeeper.setBackgroundColor(this.getResources().getColor(R.color.white));
                person.setBackgroundColor(this.getResources().getColor(R.color.titleColors));
                person.setTextColor(this.getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    protected void requestData() {

    }
}
