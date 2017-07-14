package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class HelpCenterAty extends BaseAty {
    /**
     * 多级菜单
     * */
    private ExpandableListView expandableListView_1,expandableListView_2,expandableListView_3;
    /**
     * 一级菜单数据
     * */
    private String[] groups = {"1.如何充值", "2.如何充值", "3.如何充值"};
    /**
     * 二级菜单
     * */
    private String[][] childs = {{"如何充值如何充值如何充值如何充值如何充值如何充值如何充值如何充值" +
            "如何充值如何充值如何充值如何充值如何充值如何充值如何充值如何充值如何充值"}, {"如何充值", "如何充值", "如何充值", "如何充值"}, {"如何充值", "如何充值", "如何充值", "如何充值"}};
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("帮助中心");
        initView();
    }

    private void initView() {
        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.help_center_item_li, null);
        view2 = mInflater.inflate(R.layout.help_center_item_li, null);
        view3 = mInflater.inflate(R.layout.help_center_item_li, null);
        //初始化帮助菜单
        expandableListView_1 = (ExpandableListView)view1. findViewById(R.id.expandableListView);
        expandableListView_1.setGroupIndicator(null);
        expandableListView_1.setAdapter(new MyExpandableListView());
        expandableListView_2 = (ExpandableListView)view2. findViewById(R.id.expandableListView);
        expandableListView_2.setGroupIndicator(null);
        expandableListView_2.setAdapter(new MyExpandableListView());
        expandableListView_3 = (ExpandableListView)view3. findViewById(R.id.expandableListView);
        expandableListView_3.setGroupIndicator(null);
        expandableListView_3.setAdapter(new MyExpandableListView());
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        //添加页卡标题
        mTitleList.add("商家篇");
        mTitleList.add("用户篇");
        mTitleList.add("运营中心篇");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_help_center_li;
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
    //为ExpandableListView自定义适配器
    class MyExpandableListView extends BaseExpandableListAdapter {

        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return groups.length;
        }

        //返回每个二级列表的个数
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            Log.d("smyhvae", "-->" + groupPosition);
            return childs[groupPosition].length;
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        //返回二级列表中的单个item（返回的是对象）
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs[groupPosition][childPosition]; //不要误写成groups[groupPosition][childPosition]
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //每个item的id是否是固定？一般为true
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.help_item_parent, null);
            } else {

            }
            TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            ImageView img_group= (ImageView) convertView.findViewById(R.id.img_group);
            if(isExpanded){
                img_group.setBackgroundResource(R.drawable.icon_hide_other_layout);
            }else{
                img_group.setBackgroundResource(R.drawable.icon_show_other_layout);
            }
            tv_group.setText(groups[groupPosition]);
            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.help_item_child, null);
            }

            TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);

            //iv_child.setImageResource(resId);
            tv_child.setText(childs[groupPosition][childPosition]);

            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
