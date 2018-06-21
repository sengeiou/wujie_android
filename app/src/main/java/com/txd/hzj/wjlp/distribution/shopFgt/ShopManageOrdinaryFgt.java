package com.txd.hzj.wjlp.distribution.shopFgt;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.shopAty.ShopGoodsManage;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：wjj
 * 创建时间：2018-06-11 上午 11:02
 * 功能描述： 普通商品管理界面
 */
public class ShopManageOrdinaryFgt extends BaseFgt {

    @ViewInject(R.id.shopManageOrdinary_tab_tLayout)
    private TabLayout shopManageOrdinary_tab_tLayout;

    @ViewInject(R.id.shopManageOrdinary_content_vp)
    private ViewPager shopManageOrdinary_content_vp;

    private List<Fragment> fragments = new ArrayList<>(); // 卡片页面集合
    private List<String> titles = new ArrayList<>(); // 标题集合

    private MyAdapter myAdapter;

    private int pageSelected = -1;

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_shopmanage_ordinary;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
        for (int i = 0; i < 3; i++) {
            // 循环添加Fragment 0为出售中，1为已下架，2是已售罄
            fragments.add(new ShopManageOrdinaryChildFgt(i));
        }

        titles.add("出售中");
        titles.add("已下架");
        titles.add("已售罄");
    }

    @Override
    protected void immersionInit() {
        shopManageOrdinary_tab_tLayout.setupWithViewPager(shopManageOrdinary_content_vp); // 将TabLayout和ViewPager关联起来。
        shopManageOrdinary_content_vp.setOffscreenPageLimit(3); // 设置预定加载的页面个数
        shopManageOrdinary_content_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                // 获取当前显示页面的索引，在重新加载的时候进行设置
                pageSelected = shopManageOrdinary_content_vp.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        final ShopGoodsManage shopGoodsManage= (ShopGoodsManage) getActivity();
        shopManageOrdinary_tab_tLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                shopGoodsManage.setTitltRightVisibility(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onStart() {
        myAdapter = new MyAdapter(getChildFragmentManager());
        shopManageOrdinary_content_vp.setAdapter(myAdapter);
        // 页面启动的时候进行判断是否滑动或选择了某个界面，滑动界面之后索引值一定不为负数
        if (pageSelected >= 0) {
            // 设置显示的界面
            shopManageOrdinary_content_vp.setCurrentItem(pageSelected);
        }
        super.onStart();
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
