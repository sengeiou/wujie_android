package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopManageOpenShopFgt;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopManageOrdinaryFgt;

import java.util.ArrayList;

/**
 * 创建者：Zyf
 * 功能描述：商品管理
 * 联系方式：无
 */
public class ShopGoodsManage extends BaseAty implements View.OnClickListener {

    TextView titlt_conter_tv;

    LinearLayout shop_person_title_manage;

    TextView shop_shopkeeper;

    TextView shop_person;

    TextView titlt_right_tv;

    private ArrayList<Fragment> fragments; // 展示的Fragment集合
    private FragmentChangeManager fragmentChangeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commodity_management;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv = findViewById(R.id.titlt_conter_tv);
        shop_person_title_manage = findViewById(R.id.shop_person_title_manage);
        shop_shopkeeper = findViewById(R.id.shop_shopkeeper);
        shop_person = findViewById(R.id.shop_person);
        titlt_right_tv = findViewById(R.id.titlt_right_tv);

        titlt_conter_tv.setVisibility(View.GONE);
        shop_person_title_manage.setVisibility(View.VISIBLE);
        shop_shopkeeper.setText("普通商品");
        shop_person.setText("开店商品");

        shop_shopkeeper.setOnClickListener(this);
        shop_person.setOnClickListener(this);
        titlt_right_tv.setOnClickListener(this);

    }

    @Override
    protected void requestData() {
        fragments = new ArrayList<>();
        fragments.add(new ShopManageOrdinaryFgt()); // 普通商品管理界面
        fragments.add(new ShopManageOpenShopFgt()); // 开店商品管理界面

        fragmentChangeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.commodityManage_content_fLayout, fragments);
        setTextViewAndViewColor(0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shop_shopkeeper: // 普通商品
                setTextViewAndViewColor(0);
                break;
            case R.id.shop_person: // 开店商品
                setTextViewAndViewColor(1);
                break;
        }
    }

    /**
     * 设置标题栏选中状态
     *
     * @param i 选中的下表
     */
    private void setTextViewAndViewColor(int i) {

        // 初始化普通商品字体和颜色
        shop_shopkeeper.setTextColor(ContextCompat.getColor(this, R.color.titleColors));
        shop_shopkeeper.setBackground(getResources().getDrawable(R.color.white));
        // 初始化开店商品字体和颜色
        shop_person.setTextColor(ContextCompat.getColor(this, R.color.titleColors));
        shop_person.setBackground(getResources().getDrawable(R.color.white));

        switch (i) {
            case 0: // 选中普通商品管理
                shop_shopkeeper.setTextColor(ContextCompat.getColor(this, R.color.white));
                shop_shopkeeper.setBackground(getResources().getDrawable(R.color.titleColors));
                break;
            case 1: // 选中开店商品管理
                shop_person.setTextColor(ContextCompat.getColor(this, R.color.white));
                shop_person.setBackground(getResources().getDrawable(R.color.titleColors));
                break;
        }

        fragmentChangeManager.setFragments(i);
    }

    /**
     * 设置控件的显示隐藏
     *
     * @param isShow 是否显示
     */
    public TextView setTitltRightVisibility(boolean isShow) {
        titlt_right_tv.setVisibility(isShow ?  View.VISIBLE : View.GONE);
        return titlt_right_tv;
    }

    /**
     * 设置显示文字
     *
     * @param showStr 显示的字符串
     */
    public void setTitltRightText(String showStr) {
        titlt_right_tv.setText(showStr);
    }

}
