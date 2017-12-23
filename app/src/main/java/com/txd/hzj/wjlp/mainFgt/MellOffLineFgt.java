package com.txd.hzj.wjlp.mainFgt;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Mell;
import com.txd.hzj.wjlp.citySelect.MellCitySelectAty;
import com.txd.hzj.wjlp.mainFgt.adapter.MellNearByHzjAdapter;
import com.txd.hzj.wjlp.mellOffLine.OffLineDetailsAty;
import com.txd.hzj.wjlp.mellOffLine.point.PointWjAty;
import com.txd.hzj.wjlp.txunda_lh.http.OfflineStore;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:52
 * 描述：线下商铺
 * ===============Txunda===============
 */
public class MellOffLineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
    /**
     * 标题栏
     */
    @ViewInject(R.id.off_line_title_layout)
    public RelativeLayout off_line_title_layout;
    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */

    @ViewInject(R.id.off_line_to_change_sc)
    private ObservableScrollView off_line_to_change_sc;

    private int allHeight = 0;

    /**
     * 附近商家列表
     */
    @ViewInject(R.id.mell_near_by_lv)
    private ListViewForScrollView mell_near_by_lv;

    private List<Mell> mells;

    private MellNearByHzjAdapter mellNearByHzjAdapter;

    @ViewInject(R.id.to_location_tv)
    private TextView to_location_tv;

    @ViewInject(R.id.im_ads)
    private ImageView im_ads;
    @ViewInject(R.id.three_image_left_iv)
    private ImageView three_image_left_iv;
    @ViewInject(R.id.three_image_center_iv)
    private ImageView three_image_center_iv;
    @ViewInject(R.id.three_image_right_iv)
    private ImageView three_image_right_iv;

    private int img_w = 0;
    private int img_h = 0;


    /**
     * 广告高度
     */
    private int ads_w = 0;
    private int ads_h = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        off_line_title_layout.setBackgroundColor(Color.TRANSPARENT);
        allHeight = Settings.displayWidth * 3 / 5;
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        online_carvouse_view.setLayoutParams(layoutParams);
        // 轮播图
        // 改变标题栏颜色
        img_w = Settings.displayWidth / 3;
        img_h = Settings.displayWidth * 5 / 14;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(img_w, img_h);
        three_image_left_iv.setLayoutParams(params);
        three_image_center_iv.setLayoutParams(params);
        three_image_right_iv.setLayoutParams(params);

        // 广告宽高
        ads_h = Settings.displayWidth * 300 / 1242;
        ads_w = Settings.displayWidth;


        LinearLayout.LayoutParams adsParam = new LinearLayout.LayoutParams(ads_w, ads_h);
        im_ads.setLayoutParams(adsParam);

        off_line_to_change_sc.setScrollViewListener(MellOffLineFgt.this);
//        mell_near_by_lv.setAdapter(mellNearByHzjAdapter);
        mell_near_by_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(OffLineDetailsAty.class, null);
            }
        });

    }

    /**
     * 轮播图
     */
    private void forBanner() {

        /**
         * 轮播图的点击事件
         */
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(final int position, ImageView imageView) {
                Glide.with(getActivity()).load(list_pic.get(position).get("picture")).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        };
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(list_pic.size());
    }


    @Override
    @OnClick({R.id.to_location_tv, R.id.point_by_wj_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_location_tv:// 当前位置
                startActivity(MellCitySelectAty.class, null);
                break;
            case R.id.point_by_wj_tv:// 无界驿站
                startActivity(PointWjAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mell_off_line;
    }

    @Override
    protected void initialized() {
        mells = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                mells.add(new Mell(true, false));
            } else {
                mells.add(new Mell(false, false));
            }
        }
        mellNearByHzjAdapter = new MellNearByHzjAdapter(getActivity(), mells);

    }

    @Override
    protected void requestData() {
        to_location_tv.setText(DemoApplication.getInstance().getLocInfo().get("city"));
        OfflineStore.Index(this);
    }

    Map<String, String> data;
    List<Map<String, String>> list_pic = new ArrayList<>();
    List<Map<String, String>> list_ads = new ArrayList<>();
    List<Map<String, String>> list_brand = new ArrayList<>();

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        list_pic = JSONUtils.parseKeyAndValueToMapList(data.get("banner"));
        forBanner();
        list_ads = JSONUtils.parseKeyAndValueToMapList(data.get("ads"));
        Glide.with(getActivity()).load(list_ads.get(0).get("picture")).override(ads_w, ads_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.icon_200)
                .placeholder(R.mipmap.icon_200).into(im_ads);
        list_brand = JSONUtils.parseKeyAndValueToMapList(data.get("brand"));
        Glide.with(getActivity()).load(list_brand.get(0).get("picture")).override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.icon_200)
                .placeholder(R.mipmap.icon_200)
                .centerCrop().into(three_image_left_iv);
        Glide.with(getActivity()).load(list_brand.get(1).get("picture")).override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.icon_200)
                .placeholder(R.mipmap.icon_200)
                .centerCrop().into(three_image_center_iv);
        Glide.with(getActivity()).load(list_brand.get(2).get("picture")).override(img_w, img_h)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.icon_200)
                .placeholder(R.mipmap.icon_200)
                .centerCrop().into(three_image_right_iv);
    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.off_line_title_layout);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            off_line_title_layout.setBackgroundColor(Color.TRANSPARENT);//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= allHeight) {
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            off_line_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));
        } else {
            off_line_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
        }
        immersionInit();
    }
}
