package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GroupBuyBean;
import com.txd.hzj.wjlp.bean.TopNavBean;
import com.txd.hzj.wjlp.http.country.CountryPst;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.http.integral.IntegralBuyPst;
import com.txd.hzj.wjlp.http.prebuy.PerBuyPst;
import com.txd.hzj.wjlp.http.ticketbuy.TicketBuyPst;
import com.txd.hzj.wjlp.mellOnLine.fgt.TicketZoonFgt;

import java.util.ArrayList;
import java.util.List;

public class TicketZoonAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.title_s_tab_layout)
    private SlidingTabLayout title_s_tab_layout;

    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;

    private List<TopNavBean> mTitles;
    private ArrayList<Fragment> mFragments;
    private MyPagerAdapter myPagerAdapter;
    /**
     * 8.拼团购
     */
    private int type = 0;
    private String title = "";

    private GroupBuyPst groupBuyPst;

    private PerBuyPst perBuyPst;

    private TicketBuyPst ticketBuyPst;

    private IntegralBuyPst integralBuyPst;

    private CountryPst countryPst;

    private String country_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText(title);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_ticket_zoon;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("type", 0);
        title = getIntent().getStringExtra("title");
        mFragments = new ArrayList<>();
        // 拼团购
        groupBuyPst = new GroupBuyPst(this);
        // xfte预购
        perBuyPst = new PerBuyPst(this);
        // 票券区
        ticketBuyPst = new TicketBuyPst(this);
        // xfte商店
        integralBuyPst = new IntegralBuyPst(this);
        // 进口馆
        countryPst = new CountryPst(this);

        mTitles = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        switch (type) {
            case 1:// 票券区
                ticketBuyPst.ticketBuyIndex(1, "");
                break;
            case 2:// xfte预购
                perBuyPst.preBuyIndex(1, "");
                break;
            case 3:// 进口馆
                country_id = getIntent().getStringExtra("country_id");
                countryPst.countryGoods(1, country_id, "");
                break;
            case 8:// 拼团购
                groupBuyPst.groupBuyIndex(1, "");
                break;
            case 10:// xfte商店
                integralBuyPst.integralBuyIndex(1, "");
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        GroupBuyBean groupBuyBean = GsonUtil.GsonToBean(jsonStr, GroupBuyBean.class);
        mTitles = groupBuyBean.getData().getTop_nav();
        for (TopNavBean title : mTitles) {
            mFragments.add(TicketZoonFgt.getFgt(title.getCate_id(), type, country_id));
        }
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp_for_title.setAdapter(myPagerAdapter);
        title_s_tab_layout.setViewPager(vp_for_title);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position).getName();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
