package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mellonLine.fgt.ExplosiveClassifyFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/12/18 14:26
 * 功能描述：爆款专区
 */
public class ExplosiveAreaAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.title_s_tab_layout)
    private SlidingTabLayout title_s_tab_layout;

    @ViewInject(R.id.vp_for_title)
    private ViewPager vp_for_title;


    private GoodsPst goodsPst;
    private String cate_id = "";
    private ArrayList<Fragment> fragments;
    /**
     * 分类列表
     */
    private List<Map<String, String>> horizontal_classify;
    private MyPagerAdapter myPagerAdapter;
    private int pos=0;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_explosive_area;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("爆款专区");
        goodsPst = new GoodsPst(this);
        fragments = new ArrayList<>();
        horizontal_classify = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        goodsPst.goodsList(1, cate_id, 0,"5");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (ToolKit.isList(data, "top_nav")) {
            horizontal_classify = JSONUtils.parseKeyAndValueToMapList(data.get("top_nav"));
            myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            for (Map<String, String> title : horizontal_classify) {
                fragments.add(ExplosiveClassifyFgt.newInstance(title.get("cate_id")));
            }
            // ViewPager设置适配器
            vp_for_title.setAdapter(myPagerAdapter);
            // TabLayout绑定ViewPager
            title_s_tab_layout.setViewPager(vp_for_title);
            // 设置选中第pos个Tab
            title_s_tab_layout.setCurrentTab(pos);
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return horizontal_classify.get(position).get("short_name");
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
