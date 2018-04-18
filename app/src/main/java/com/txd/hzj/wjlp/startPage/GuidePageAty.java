package com.txd.hzj.wjlp.startPage;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人： Txd_Cjh
 * 创建时间： 2017/9/29 18:37
 * 功能描述：引导页
 */
public class GuidePageAty extends BaseAty implements ViewPager.OnPageChangeListener {
    /**
     * ViewPager
     */
    @ViewInject(R.id.intro_for_app_vp)
    private ViewPager intro_for_app_vp;
    @ViewInject(R.id.into_app_tv)
    private TextView into_app_tv;
    private List<Integer> pic;
    private List<ImageView> list;

    // 滑动
    private int lastValut = -1;
    private boolean isScrolling = false;
    // 判断只走一次
    private int count = 0;
    private ViewPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intro_for_app_vp.setAdapter(adapter);
        intro_for_app_vp.addOnPageChangeListener(this);
    }

    private void intoMainAty() {
        startActivity(MainAty.class, null);
        finish();
    }

    @Override
    @OnClick({R.id.into_app_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.into_app_tv:
                intoMainAty();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_guide_page;
    }

    @Override
    protected void initialized() {
        pic = new ArrayList<>();
        pic.add(R.mipmap.start_page_one);
        pic.add(R.mipmap.start_page_two);
        pic.add(R.mipmap.start_page_three);
        pic.add(R.mipmap.start_page_four);
        list = new ArrayList<>();
        adapter = new ViewPagerAdapter();
        for (Integer i : pic) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(i);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView);
        }
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (isScrolling) {
            if (lastValut == positionOffsetPixels && position == list.size() - 1 && count == 0) {
                count = 1;
            }
        }
        lastValut = positionOffsetPixels;

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) { // 四个引导页，最后伊特索引值为3的时候显示跳转按钮
            into_app_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intoMainAty();
                }
            });
            into_app_tv.setVisibility(View.VISIBLE);
        } else {
            into_app_tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        // i → 有三个值：0（END）,1(PRESS) , 2(UP)
        if (i == 1) {
            isScrolling = true;
        } else {
            isScrolling = false;
        }

    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * 初始化posotion位置
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position), 0);
            return list.get(position);
        }

        /**
         * 判断是不是对象生成
         *
         * @param view
         * @param o
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

    }
}
