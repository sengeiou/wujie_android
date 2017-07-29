package com.txd.hzj.wjlp.mainFgt.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/28 0028
 * 时间：13:42
 * 描述：
 * ===============Txunda===============
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return (views.get(position));
    }

    @Override
    public int getCount() {
        if (views == null)
            return 0;
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
