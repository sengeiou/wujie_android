package com.txd.hzj.wjlp.distribution.shopmanage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 10:08
 * 功能描述：
 * 联系方式：无
 */
public class ShopMain extends BaseAty implements OnClickListener {

    private TextView titleName;
    private LinearLayout shopUpGoods;
    private LinearLayout shopGoodsManage;
    private LinearLayout shopMoney;
    private LinearLayout shopSetUp;
    private LinearLayout shopPersonManage;
    private LinearLayout shopOrderManage;

    @Override
    protected int getLayoutResId() {
        return R.layout.dbt_shop_manage_main;
    }

    @Override
    protected void initialized() {
        titleName = findViewById(R.id.titlt_conter_tv);
        //小店上货
        shopUpGoods = findViewById(R.id.shop_up_goods);
        //商品管理
        shopGoodsManage = findViewById(R.id.shop_goods_manage);
        //小店营销
        shopMoney = findViewById(R.id.shop_money);
        //店铺设置
        shopSetUp = findViewById(R.id.shop_set_up);
        //顾客管理
        shopPersonManage = findViewById(R.id.shop_person_manage);
        //订单管理
        shopOrderManage = findViewById(R.id.shop_order_magage);


        //注册点击事件
        shopUpGoods.setOnClickListener(this);
        shopGoodsManage.setOnClickListener(this);
        shopMoney.setOnClickListener(this);
        shopSetUp.setOnClickListener(this);
        shopPersonManage.setOnClickListener(this);
        shopOrderManage.setOnClickListener(this);
    }

    @Override
    protected void requestData() {
    titleName.setText("店铺管理");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.shop_up_goods:
                break;
            case R.id.shop_goods_manage:
                break;
            case R.id.shop_money:
                break;
            case R.id.shop_set_up:
                //店铺设置
                startActivity(ShopSetUp.class,null);
                break;
            case R.id.shop_person_manage:
                //顾客管理
                startActivity(ShopPersonManage.class,null);
                break;
            case R.id.shop_order_magage:
                //订单管理
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
    }


}
