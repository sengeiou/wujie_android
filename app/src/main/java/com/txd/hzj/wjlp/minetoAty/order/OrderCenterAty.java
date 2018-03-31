package com.txd.hzj.wjlp.minetoAty.order;

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

public class OrderCenterAty extends BaseAty implements View.OnClickListener {
    /**
     * 线上商城
     */
    @ViewInject(R.id.tv_online_shops)
    TextView tv_online_shops;
    /**
     * 线下商城
     */
    @ViewInject(R.id.tv_offline_shop)
    TextView tv_offline_shop;
    /**
     * 无界商店
     */
    @ViewInject(R.id.tv_wj_shop)
    TextView tv_wj_shop;
    /**
     * 拼团区
     */
    @ViewInject(R.id.tv_pintuan)
    TextView tv_pintuan;
    /**
     * 无界预购
     */
    @ViewInject(R.id.tv_wjyg)
    TextView tv_wjyg;
    /**
     * 竞拍区
     */
    @ViewInject(R.id.tv_jpq)
    TextView tv_jpq;
    /**
     * 一元夺宝
     */
    @ViewInject(R.id.tv_yydb)
    TextView tv_yydb;
    /**
     * 汽车购
     */
    @ViewInject(R.id.tv_car_buy)
    TextView tv_car_buy;
    /**
     * 房产购
     */
    @ViewInject(R.id.tv_home_buy)
    TextView tv_home_buy;
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 会员卡
     */
    @ViewInject(R.id.tv_vip_card)
    private TextView tv_vip_card;

    Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单中心");
        initEvent();
    }

    private void initEvent() {

        tv_online_shops.setOnClickListener(this);
        tv_offline_shop.setOnClickListener(this);
        tv_wj_shop.setOnClickListener(this);
        tv_pintuan.setOnClickListener(this);
        tv_wjyg.setOnClickListener(this);
        tv_jpq.setOnClickListener(this);
        tv_yydb.setOnClickListener(this);
        tv_car_buy.setOnClickListener(this);
        tv_home_buy.setOnClickListener(this);
        tv_vip_card.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.tv_online_shops:
                mBundle.putString("title", "线上商城");
                mBundle.putString("type", "0");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_offline_shop:
//                mBundle.putString("title", "线下商铺");
//                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_wj_shop:
                mBundle.putString("title", "无界商店");
                mBundle.putString("type", "7");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_pintuan:
                mBundle.putString("title", "拼团区");
                mBundle.putString("type", "3");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_wjyg:
                mBundle.putString("title", "无界预购");
                mBundle.putString("type", "4");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_jpq:
                mBundle.putString("title", "比价购");
                mBundle.putString("type", "6");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_yydb:
                mBundle.putString("title", "积分抽奖");
                mBundle.putString("type", "5");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_car_buy:
                mBundle.putString("title", "汽车购");
                mBundle.putString("type", "1");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_home_buy:
                mBundle.putString("title", "房产购");
                mBundle.putString("type", "2");
                startActivity(OnlineShopAty.class, mBundle);
                break;
            case R.id.tv_vip_card:
                startActivity(VipCardAty.class,null);
                break;
        }
        super.onClick(v);
    }
}
