package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.gson.GsonUtil;
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
import com.txd.hzj.wjlp.mellonLine.fgt.TicketZoonFgt;
import com.txd.hzj.wjlp.tool.WJConfig;
import com.txd.hzj.wjlp.view.TicketDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 拼单购页面
 */
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

    //拼单规则
    private String[] mGroup_buy_rule;

    //拼单规则显示状态" ,// 0:不显示；1：显示；
    private String mIs_show_group_buy_rule;

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
        switch (type) {
            case WJConfig.PQQ: {
                // 票券区
                ticketBuyPst = new TicketBuyPst(this);
            }
            break;
            case WJConfig.JFSD: {
                // 积分商店
                integralBuyPst = new IntegralBuyPst(this);
            }
            break;
            case WJConfig.JKG: {
                // 进口馆
                countryPst = new CountryPst(this);
            }
            break;
            case WJConfig.PTG: {
                // 拼团购
                groupBuyPst = new GroupBuyPst(this);
            }
            break;
            case WJConfig.WJYG: {
                // 无界预购
                perBuyPst = new PerBuyPst(this);
            }
            break;
        }
        //初始化上面的标题栏数组
        mTitles = new ArrayList<>();

    }



    @Override
    protected void requestData() {
        switch (type) {
            case WJConfig.PQQ:// 票券区
                ticketBuyPst.ticketBuyIndex(1, "");
                break;
            case WJConfig.WJYG:// 无界预购
                perBuyPst.preBuyIndex(1, "");
                break;
            case WJConfig.JKG:// 进口馆
                country_id = getIntent().getStringExtra("country_id");
                countryPst.countryGoods(1, country_id, "");
                break;
            case WJConfig.PTG:// 拼团购
                groupBuyPst.groupBuyIndex(1, "");
                break;
            case WJConfig.JFSD:// 积分商店
                integralBuyPst.integralBuyIndex(1, "");
                break;
        }
    }

    private void showTickDialog() {
        TicketDialog ticketDialog=new TicketDialog(this);
        List<String> group_rules_list= Arrays.asList(mGroup_buy_rule);
        if (group_rules_list.size()>0) {
            ticketDialog.setData(group_rules_list);
            ticketDialog.show();
        }
        ticketDialog.setChangeShowStatus(new TicketDialog.ChangeShowStatus() {
            @Override
            public void changeStatus() {
                groupBuyPst.changeShowStatus(TicketZoonAty.this);
            }
        });

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("changeShowStatus")){
            JSONObject jsonObject= JSON.parseObject(jsonStr);
            Log.e("TAG", "onComplete: "+jsonObject.getString("message") );
        }else {
            GroupBuyBean groupBuyBean = GsonUtil.GsonToBean(jsonStr, GroupBuyBean.class);
            mTitles = groupBuyBean.getData().getTop_nav();
            mGroup_buy_rule = groupBuyBean.getData().getGroup_buy_rule();
            mIs_show_group_buy_rule = groupBuyBean.getData().getIs_show_group_buy_rule();
            for (TopNavBean title : mTitles) {
                mFragments.add(TicketZoonFgt.getFgt(title.getCate_id(), type, country_id));
            }
            myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            vp_for_title.setAdapter(myPagerAdapter);
            title_s_tab_layout.setViewPager(vp_for_title);
            if (type == WJConfig.PTG && mIs_show_group_buy_rule.equals("1")) {
                showTickDialog();
            }
        }
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
