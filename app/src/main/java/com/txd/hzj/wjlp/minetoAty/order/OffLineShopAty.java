package com.txd.hzj.wjlp.minetoAty.order;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.fgt.OffLineFgt;

import java.util.ArrayList;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/30 9:08
 * 功能描述：
 */
public class OffLineShopAty extends BaseAty {
    @ViewInject(R.id.tl_main)
    private TabLayout tl_main;
    @ViewInject(R.id.vp_main)
    private ViewPager vp_main;
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    private ArrayList<String> titleList=new ArrayList<>();
    private ArrayList<Fragment>fragmentList=new ArrayList<>();
    private OffLineAdapter mOffLineAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_offline_shop;
    }

    @Override
    protected void initialized() {
        titleList.add("全部");
        titleList.add("未支付");
        titleList.add("已支付");
        titlt_conter_tv.setText("线下店铺");
        fragmentList.add(OffLineFgt.getInstance(""));
        fragmentList.add(OffLineFgt.getInstance("0"));
        fragmentList.add(OffLineFgt.getInstance("1"));
    }

    @Override
    protected void requestData() {
        mOffLineAdapter=new OffLineAdapter(getSupportFragmentManager());
        vp_main.setAdapter(mOffLineAdapter);
        vp_main.setOffscreenPageLimit(2);
        tl_main.setupWithViewPager(vp_main);
        tl_main.setTabsFromPagerAdapter(mOffLineAdapter);
    }

    private class OffLineAdapter extends FragmentPagerAdapter{

        public OffLineAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
