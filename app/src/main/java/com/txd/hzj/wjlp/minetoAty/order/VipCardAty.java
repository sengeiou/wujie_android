package com.txd.hzj.wjlp.minetoAty.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.adapter.VpAdapter;
import com.txd.hzj.wjlp.minetoAty.order.fgt.VipCardFgt;

import java.util.ArrayList;

/**
 * Created by Txd_lienchao on 2018/3/16 0016 上午 10:47.
 * 功能描述:会员卡
 * email:470360046@qq.com
 * 不急不躁，BUG圆如，
 * 说改就改，不撕不怒，
 * 种种需求，过眼云烟，
 * 敏捷迭代，自在随心，
 * 立项结项，不如吃瓜。
 */

public class VipCardAty extends BaseAty {
    @ViewInject(R.id.tl_main)
    private TabLayout tl_main;
    @ViewInject(R.id.vp_main)
    private ViewPager vp_main;
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    private ArrayList<String> titleList=new ArrayList<>();
    private ArrayList<Fragment>fragmentList=new ArrayList<>();
    private VpAdapter vpAdapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.aty_vip_card;
    }

    @Override
    protected void initialized() {
        titleList.add("全部");
        titleList.add("未支付");
        titleList.add("已支付");
        titlt_conter_tv.setText("会员卡");
        fragmentList.add(new VipCardFgt(""));
        fragmentList.add(new VipCardFgt("0"));
        fragmentList.add(new VipCardFgt("1"));
        vp_main.setOffscreenPageLimit(0);
    }

    @Override
    protected void requestData() {
        vpAdapter=new VpAdapter(getSupportFragmentManager(), titleList, fragmentList);
        vp_main.setAdapter(vpAdapter);
        vp_main.setOffscreenPageLimit(2);
        tl_main.setupWithViewPager(vp_main, true);
        tl_main.setTabsFromPagerAdapter(vpAdapter);
    }
}
