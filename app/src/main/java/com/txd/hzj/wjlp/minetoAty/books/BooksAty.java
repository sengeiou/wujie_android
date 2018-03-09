package com.txd.hzj.wjlp.minetoAty.books;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.academy.AcademyPst;
import com.txd.hzj.wjlp.minetoAty.books.fgt.BooksFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：上午 9:26
 * 描述：xfte书院
 * ===============Txunda===============
 */
public class BooksAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.books_classify_tab)
    private TabLayout books_classify_tab;

    @ViewInject(R.id.books_vp)
    private ViewPager books_vp;

    private List<Map<String, String>> titles;
    private List<Fragment> fragments;
    private PageAdapter pageAdapter;

    private AcademyPst academyPst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("xfte书院");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_books;
    }

    @Override
    protected void initialized() {
        academyPst = new AcademyPst(this);
        titles = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        academyPst.academyIndex(1, "");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        titles = JSONUtils.parseKeyAndValueToMapList(data.get("ac_type_list"));
        fragments = new ArrayList<>();
        for (Map<String, String> title : titles) {
            fragments.add(BooksFgt.newInstance(title.get("type_id")));
        }
        if (titles.size() >= 5) {
            books_classify_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            books_classify_tab.setTabMode(TabLayout.MODE_FIXED);
        }
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        books_vp.setAdapter(pageAdapter);
        books_classify_tab.setupWithViewPager(books_vp);
    }

    /**
     * 适配器
     */
    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position).get("type_name");
        }
    }

}
