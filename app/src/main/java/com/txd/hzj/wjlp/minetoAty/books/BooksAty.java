package com.txd.hzj.wjlp.minetoAty.books;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.books.fgt.BooksFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：上午 9:26
 * 描述：无界书院
 * ===============Txunda===============
 */
public class BooksAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.books_classify_tab)
    private TabLayout books_classify_tab;

    @ViewInject(R.id.books_vp)
    private ViewPager books_vp;

    private List<String> titles;
    private List<Fragment> fragments;
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("无极书院");

        books_vp.setAdapter(pageAdapter);
        books_classify_tab.setupWithViewPager(books_vp);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_books;
    }

    @Override
    protected void initialized() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("推荐");
        titles.add("推荐");
        titles.add("推荐");
        titles.add("推荐");
        titles.add("推荐");
        titles.add("推荐");
        for (String title : titles) {
            fragments.add(BooksFgt.newInstance(title));
        }
        pageAdapter = new PageAdapter(getSupportFragmentManager());
    }

    @Override
    protected void requestData() {

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
            return titles.get(position);
        }
    }

}
