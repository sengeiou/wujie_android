package com.txd.hzj.wjlp.minetoAty.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.AuctionRecordAty;
import com.txd.hzj.wjlp.minetoAty.order.fgt.OrderOnLineFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 11:48
 * 描述：订单列表
 * ===============Txunda===============
 */
public class OnlineShopAty extends BaseAty implements View.OnClickListener {
    /**
     * 标题栏右侧文字
     */
    @ViewInject(R.id.titlt_right_tv)
    TextView titlt_right_tv;
    private String title;//标题
    /**
     * 页卡
     */
    @ViewInject(R.id.tabs)
    private TabLayout mTabLayout;
    /**
     * ViewPager
     */
    @ViewInject(R.id.vp_view)
    private ViewPager mViewPager;

    private List<Map<String, String>> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mFragment = new ArrayList<>();//页卡视图集合
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        Bundle mBundle = getIntent().getExtras();
        title = mBundle.getString("title");
        titlt_conter_tv.setText(title);
        if (mTitleList.size() >= 5) {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_online_shop_li;
    }

    @Override
    protected void initialized() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        title = getIntent().getStringExtra("title");
        //添加页卡标题
<<<<<<< HEAD
        if (title.equals("线上商城") || title.equals("线下商城") || title.equals("无界商店")
                || title.equals("无界商店")) {
=======
        if (title.equals("线上商城") || title.equals("线下商城") || title.equals("xfte商店")
                || title.equals("xfte商店")) {
>>>>>>> master
            mTitleList = OrderTitleUtils.getInstance().orderTitle1();
        } else if (title.equals("拼团区")) {
            mTitleList = OrderTitleUtils.getInstance().orderTitle2();
        } else if (title.equals("xfte预购")) {
            mTitleList = OrderTitleUtils.getInstance().orderTitle3();
        } else if (title.equals("比价购")) {
            titlt_right_tv.setText("比价纪录");
            titlt_right_tv.setVisibility(View.VISIBLE);
            titlt_right_tv.setTextColor(Color.RED);
            mTitleList = OrderTitleUtils.getInstance().orderTitle1();
        } else if (title.equals("积分抽奖")) {
            mTitleList = OrderTitleUtils.getInstance().orderTitle4();
        } else {
            mTitleList = OrderTitleUtils.getInstance().orderTitle5();
        }
        // 添加碎片
        for (Map<String, String> fgt : mTitleList) {
            mFragment.add(OrderOnLineFgt.getFgt(title, fgt.get("type"),getIntent().getStringExtra("type")));
        }
    }

    @Override
    protected void requestData() {
    }

    @Override
    @OnClick({R.id.titlt_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:
                startActivity(AuctionRecordAty.class, null);
                break;
        }
    }

    /**
     * ViewPager适配器
     */
    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position).get("title");
        }
    }
}
