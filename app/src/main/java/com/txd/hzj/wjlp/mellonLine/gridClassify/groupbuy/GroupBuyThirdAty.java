package com.txd.hzj.wjlp.mellonLine.gridClassify.groupbuy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.tools.ObserTool;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.commodity.ThreeCateBean;
import com.txd.hzj.wjlp.bean.commodity.ThreeListBean;
import com.txd.hzj.wjlp.bean.commodity.ThreeListDataBean;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：16:45
 * 描述： 拼团购
 *
 */

public class GroupBuyThirdAty extends BaseAty {
    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * TabLayout
     */
    @ViewInject(R.id.sub_classify_stl)
    private SlidingTabLayout sub_classify_stl;

    @ViewInject(R.id.sub_classify_vp)
    private ViewPager sub_classify_vp;

    private List<ThreeCateBean> mTitles;

    private ArrayList<Fragment> mFragments;

    private MyPagerAdapter myPagerAdapter;

    /**
     * 标题
     */
    private String appBarTitle = "";
    /**
     * 二级地址
     */
    private String two_cate_id = "";

    private GroupBuyPst groupBuyPst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText(appBarTitle);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_subclassification;
    }

    @Override
    protected void initialized() {
        groupBuyPst = new GroupBuyPst(this);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        appBarTitle = getIntent().getStringExtra("appBarTitle");
        two_cate_id = getIntent().getStringExtra("two_cate_id");
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
    }

    @Override
    protected void requestData() {
        groupBuyPst.threeList(two_cate_id, 1, "");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("threeList")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, ThreeListBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    ThreeListBean threeListBean = (ThreeListBean) t;
                    ThreeListDataBean dataBean = threeListBean.getData();
                    mTitles = dataBean.getThree_cate_list();
                    if (null != mTitles && mTitles.size() > 0) {
                        for (ThreeCateBean title : mTitles) {
                            mFragments.add(ThirdClassifyFgt.getFgt(two_cate_id, title.getThree_cate_id()));
                        }
                        sub_classify_vp.setAdapter(myPagerAdapter);
                        sub_classify_stl.setViewPager(sub_classify_vp);
                        sub_classify_vp.setCurrentItem(0);
                    }
                }
            });


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
