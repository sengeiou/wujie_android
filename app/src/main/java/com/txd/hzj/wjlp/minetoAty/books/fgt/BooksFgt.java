package com.txd.hzj.wjlp.minetoAty.books.fgt;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.MellNearByHzjAdapter;
import com.txd.hzj.wjlp.minetoAty.adapter.WjBooksAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：上午 9:48
 * 描述：书院
 * ===============Txunda===============
 */
public class BooksFgt extends BaseFgt {

    private String type;

    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private ArrayList<Integer> image;

    @ViewInject(R.id.books_lv)
    private ListViewForScrollView books_lv;
    private List<String> books;

    private WjBooksAdapter wjBooksAdapter;

    @ViewInject(R.id.books_sc)
    private ScrollView books_sc;

    public static BooksFgt newInstance(String type) {
        BooksFgt fragment = new BooksFgt();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth / 3);
        online_carvouse_view.setLayoutParams(layoutParams);
        // 轮播图
        forBanner();
        books_sc.smoothScrollTo(0, 0);
        books_lv.setAdapter(wjBooksAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            try {
                books_sc.smoothScrollTo(0, 0);
            } catch (NullPointerException e) {
                L.e("=====");
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_books;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        books = new ArrayList<>();
        wjBooksAdapter = new WjBooksAdapter(getActivity(), books);
    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void immersionInit() {

    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(image.size());
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            imageView.setImageResource(image.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };
}
