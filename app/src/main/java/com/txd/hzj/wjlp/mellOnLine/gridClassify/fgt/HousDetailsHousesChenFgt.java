package com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.banner.BannerAdapter;
import com.ants.theantsgo.view.banner.DotView;
import com.ants.theantsgo.view.banner.SliderBanner;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.FindHouseByMapAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsEvaluateAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购详情页_楼盘(10-2房产购)
 * ===============Txunda===============
 */

public class HousDetailsHousesChenFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
    /**
     * 轮播图
     */
    @ViewInject(R.id.sb_houses_banner)
    private SliderBanner sb_houses_banner;
    @ViewInject(R.id.sb_houses_viewpager)
    private ViewPager sb_houses_viewpager;
    @ViewInject(R.id.sb_houses_dotview)
    private DotView sb_houses_dotview;
    private List<ImageView> albums;//图片
    private List<Integer> list;//图片
    private MyViewAdapter adapter;//轮播图适配器

    @ViewInject(R.id.scrollView)
    private ObservableScrollView scrollView;

    @ViewInject(R.id.hd_be_back_top_iv)
    private ImageView hd_be_back_top_iv;

    @ViewInject(R.id.propety_fee_tv)
    private TextView propety_fee_tv;

    private SkipToComment skipToComment;

    @Override
    public void onAttach(Context context) {
        skipToComment = (SkipToComment) context;
        super.onAttach(context);
    }

    @OnClick({R.id.tv_houses_evaluate, R.id.to_check_location_layout, R.id.hd_be_back_top_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_houses_evaluate:
                if (skipToComment != null) {
                    skipToComment.beSkip(true);
                }
                break;
            case R.id.to_check_location_layout:// 查看地图
                startActivity(FindHouseByMapAty.class, null);
                break;
            case R.id.hd_be_back_top_iv:// 回到顶部
                scrollView.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /** 轮播图 **/
        initBanner();
        scrollView.setScrollViewListener(this);
        ChangeTextViewStyle.getInstance().forFeeStyle(propety_fee_tv, "2.80元/m2·月");
    }


    @Override
    protected void immersionInit() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_hous_details_houses_chen;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
        adapter = new MyViewAdapter();//轮播图适配器初始化
        albums = new ArrayList<>();
        list = new ArrayList<>();
        list.add(R.mipmap.icon_car_two_chen);
        list.add(R.mipmap.icon_car_two_chen);
        list.add(R.mipmap.icon_car_two_chen);
        list.add(R.mipmap.icon_car_two_chen);
        startBanner();//启动轮播图
    }


    /**
     * 轮播图
     */
    private void initBanner() {


    }

    /**
     * 启动轮播图
     */
    private void startBanner() {
        LayoutParams params = new LayoutParams(Settings.displayWidth, Settings.displayWidth);
        sb_houses_banner.setLayoutParams(params);
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            albums.add(imageView);
        }

        sb_houses_banner.setAdapter(adapter);
        sb_houses_banner.setDotNum(ListUtils.getSize(albums));
        sb_houses_banner.beginPlay();

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < Settings.displayWidth / 2) {
            hd_be_back_top_iv.setVisibility(View.GONE);
        } else {
            hd_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 轮播图适配器
     *
     * @author Administrator
     */
    private class MyViewAdapter extends BannerAdapter {

        @Override
        public View getView(LayoutInflater layoutInflater, final int position) {
            ImageView imageView = albums.get(position);
            imageView.setImageResource(list.get(position));
            return imageView;
        }

        @Override
        public int getCount() {
            return ListUtils.getSize(albums);
        }
    }

    public interface SkipToComment {
        void beSkip(boolean skip);
    }

}
