package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopOrderFragment;

import java.util.ArrayList;

import static android.support.design.widget.TabLayout.*;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 15:14
 * 功能描述：订单管理页面
 * 联系方式：无
 */
public class ShopOrderManage extends BaseAty {

    private TabLayout tabView;
    private TextView titleName;
    private ArrayList<Fragment> fragments;
    private ViewPager orderVp;
    private ArrayList<String> titles;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderVp.setAdapter(myPagerAdapter);
        tabView.setupWithViewPager(orderVp);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_order_manage;
    }

    @Override
    protected void initialized() {
        titles = new ArrayList();
        titles.add("全部");
        titles.add("代付款");
        titles.add("代发货");
        titles.add("待收货");
        titles.add("已完成");
        tabView = findViewById(R.id.shop_tab_view);
        titleName = findViewById(R.id.titlt_conter_tv);
        orderVp = findViewById(R.id.shop_order_vp);
        fragments = new ArrayList<>();
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < titles.size(); i++) {
            String s = titles.get(i);
            fragments.add(ShopOrderFragment.newInstance(s));
        }
    }

    @Override
    protected void requestData() {

        titleName.setText("订单管理");


    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position).toString();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
