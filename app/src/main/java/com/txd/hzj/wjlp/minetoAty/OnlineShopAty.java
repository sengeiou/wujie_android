package com.txd.hzj.wjlp.minetoAty;

import android.graphics.Color;
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
import com.txd.hzj.wjlp.mainFgt.adapter.IndianaRecordAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.MyOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lienchao on 2017/7/14 0014.
 */

public class OnlineShopAty extends BaseAty implements View.OnClickListener{
    /**
     * 标题栏右侧文字
     * */
    @ViewInject(R.id.titlt_right_tv)
    TextView titlt_right_tv;
    private String title;//标题
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
    private View view1, view2, view3,view4,view5,view6;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    /**
     * 设置标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 订单详情
     * */
    private MyOrderAdapter adapter;
    private ListView lv_online_shop_01,lv_online_shop_02,lv_online_shop_03,lv_online_shop_04,lv_online_shop_00;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        Bundle mBundle=getIntent().getExtras();
        title=mBundle.getString("title");
        titlt_conter_tv.setText(title);
        initView();
    }

    private void initView() {
        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view2 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view3 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view4 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view5 = mInflater.inflate(R.layout.online_shop_item_li, null);
        view6 = mInflater.inflate(R.layout.online_shop_item_li, null);
        //显示订单详情
        lv_online_shop_00=(ListView) view1.findViewById(R.id.lv_online_shop);
        lv_online_shop_01=(ListView) view2.findViewById(R.id.lv_online_shop);
        lv_online_shop_02=(ListView) view3.findViewById(R.id.lv_online_shop);
        lv_online_shop_03=(ListView) view4.findViewById(R.id.lv_online_shop);
        lv_online_shop_04=(ListView) view5.findViewById(R.id.lv_online_shop);
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
            Order order5=new Order(4,"大众官方旗舰店");
            list.add(order5);
        }
        adapter=new MyOrderAdapter(OnlineShopAty.this,list,0);
        lv_online_shop_00.setAdapter(adapter);//显示全部list
        adapter=new MyOrderAdapter(this,list,1);
        lv_online_shop_01.setAdapter(adapter);//显示待付款list
        adapter=new MyOrderAdapter(this,list,2);
        lv_online_shop_02.setAdapter(adapter);//显示待发货list
        adapter=new MyOrderAdapter(this,list,3);
        lv_online_shop_03.setAdapter(adapter);//显示待收货list
        adapter=new MyOrderAdapter(this,list,4);
        lv_online_shop_04.setAdapter(adapter);//显示待评价list
        //添加页卡标题
        if(title.equals("线上商城")||title.equals("线下商城")||title.equals("无界商店")){
            mTitleList.add("全部");
            mTitleList.add("待付款");
            mTitleList.add("待发货");
            mTitleList.add("待收货");
            mTitleList.add("待评价");
            addView(5);
        }else if(title.equals("拼团区")){
            mTitleList.add("全部");
            mTitleList.add("待付款");
            mTitleList.add("待成团");
            mTitleList.add("待发货");
            mTitleList.add("待收货");
            mTitleList.add("待评价");
            addView(6);
        }else if(title.equals("无界预购")){
            mTitleList.add("全部");
            mTitleList.add("预购中");
            mTitleList.add("待付尾款");
            mTitleList.add("待发货");
            mTitleList.add("待收货");
            mTitleList.add("待评价");
            addView(6);
        }else if(title.equals("竞拍汇")){
            titlt_right_tv.setText("竞拍纪录");
            titlt_right_tv.setVisibility(View.VISIBLE);
            titlt_right_tv.setTextColor(Color.RED);
            titlt_right_tv.setOnClickListener(this);
            mTitleList.add("全部");
            mTitleList.add("待付款");
            mTitleList.add("待发货");
            mTitleList.add("待收货");
            mTitleList.add("待评价");
            addView(5);
        }else if(title.equals("抢宝记录")){
            mTitleList.add("全部");
            mTitleList.add("进行中");
            mTitleList.add("已揭晓");
            mTitleList.add("中奖记录");
            IndianaRecordAdapter adapter=new IndianaRecordAdapter(OnlineShopAty.this,list,0);
            lv_online_shop_00.setAdapter(adapter);//显示全部list
            adapter=new IndianaRecordAdapter(this,list,1);
            lv_online_shop_01.setAdapter(adapter);//显示待付款list
            adapter=new IndianaRecordAdapter(this,list,2);
            lv_online_shop_02.setAdapter(adapter);//显示待发货list
            adapter=new IndianaRecordAdapter(this,list,3);
            lv_online_shop_03.setAdapter(adapter);//显示待收货list
            addView(4);
        }else{
            mTitleList.add("全部");
            mTitleList.add("待付款");
            mTitleList.add("办理手续中");
            mTitleList.add("待评价");
            addView(4);
        }
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        for(int i=0;i<mTitleList.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));//添加tab选项卡
        }
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }
    /**
     * 添加页卡视图
     * */
    private void addView(int i) {
        if(i==4){
            mViewList.add(view1);
            mViewList.add(view2);
            mViewList.add(view3);
            mViewList.add(view4);
        }else if(i==5){
            mViewList.add(view1);
            mViewList.add(view2);
            mViewList.add(view3);
            mViewList.add(view4);
            mViewList.add(view5);
        }else{
            mViewList.add(view1);
            mViewList.add(view2);
            mViewList.add(view3);
            mViewList.add(view4);
            mViewList.add(view5);
            mViewList.add(view6);
        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlt_right_tv:
                    startActivity(AuctionRecordAty.class,null);
                break;
        }
        super.onClick(v);
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
