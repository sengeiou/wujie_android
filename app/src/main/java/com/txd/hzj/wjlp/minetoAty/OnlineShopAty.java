package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.adapter.MyOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class OnlineShopAty extends BaseAty {
    /**
     * 页卡
     * */
    @ViewInject(R.id.tabs)
    private TabLayout mTabLayout;
    /**
     * ViewPager
     * */
    @ViewInject(R.id.vp_view)
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2, view3,view4,view5;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    /**
     * 设置标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 订单详情
     * */
    private ListView lv_online_shop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("线上商城");
        initView();
    }

    private void initView() {
        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view2 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view3 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view4 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view5 = mInflater.inflate(R.layout.online_shop_item_li, null);
        //显示订单详情
        lv_online_shop=(ListView) view1.findViewById(R.id.lv_online_shop);
        List<String>list=new ArrayList<>();
        list.add("宝马旗舰店");
        list.add("奔驰旗舰店");
        list.add("奥迪旗舰店");
        list.add("兰博旗舰店");
        MyOrderAdapter adapter=new MyOrderAdapter(this,list);
        lv_online_shop.setAdapter(adapter);
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);
        //添加页卡标题
        mTitleList.add("全部");
        mTitleList.add("代付款");
        mTitleList.add("待发货");
        mTitleList.add("待收货");
        mTitleList.add("待评价");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_online_shop_li;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }
}
