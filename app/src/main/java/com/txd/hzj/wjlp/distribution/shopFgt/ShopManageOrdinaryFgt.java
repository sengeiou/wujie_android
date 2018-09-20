package com.txd.hzj.wjlp.distribution.shopFgt;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.shopaty.ShopGoodsManage;

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
        myAdapter = new MyAdapter(getChildFragmentManager());
        shopManageOrdinary_content_vp.setCurrentItem(0);
        shopManageOrdinary_content_vp.setOffscreenPageLimit(3); // 设置预定加载的页面个数
        shopManageOrdinary_content_vp.setAdapter(myAdapter);
        shopManageOrdinary_tab_tLayout.setupWithViewPager(shopManageOrdinary_content_vp); // 将TabLayout和ViewPager关联起来。
        final ShopGoodsManage shopGoodsManage= (ShopGoodsManage) getActivity();
        shopManageOrdinary_content_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                shopGoodsManage.setTitltRightVisibility(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

        //防止fragment自动销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }

}
