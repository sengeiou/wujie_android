package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.mainFgt.adapter.AuctionRecordAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.MyOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lienchao on 2017/7/15 0015.
 */

public class AuctionRecordAty extends BaseAty {
    /**
     * 设置标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
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
    private View view1, view2, view3;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    /**
     * 订单详情
     * */
    private AuctionRecordAdapter adapter;
    private ListView lv_online_shop_01,lv_online_shop_02,lv_online_shop_03;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("竞拍纪录");
        initView();
    }

    private void initView() {
        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view2 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view3 = mInflater.inflate(R.layout.online_shop_item_li, null);
        //显示订单详情
        lv_online_shop_01=(ListView) view1.findViewById(R.id.lv_online_shop);
        lv_online_shop_02=(ListView) view2.findViewById(R.id.lv_online_shop);
        lv_online_shop_03=(ListView) view3.findViewById(R.id.lv_online_shop);
        List<Order>list=new ArrayList<>();
        for(int i=0;i<4;i++){
            Order order1=new Order(1,"宝马官方旗舰店");
            list.add(order1);
            Order order2=new Order(1,"奔驰官方旗舰店");
            list.add(order2);
            Order order3=new Order(2,"兰博官方旗舰店");
            list.add(order3);
            Order order4=new Order(3,"玛莎官方旗舰店");
            list.add(order4);
        }
        adapter=new AuctionRecordAdapter(this,list,1);
        lv_online_shop_01.setAdapter(adapter);//显示竞拍中list
        adapter=new AuctionRecordAdapter(this,list,2);
        lv_online_shop_02.setAdapter(adapter);//显示竞拍成功list
        adapter=new AuctionRecordAdapter(this,list,3);
        lv_online_shop_03.setAdapter(adapter);//显示竞拍结束list
        mTitleList.add("竞拍中");
        mTitleList.add("竞拍成功");
        mTitleList.add("竞拍结束");
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        for(int i=0;i<mTitleList.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));//添加tab选项卡
        }
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auction_record;
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
