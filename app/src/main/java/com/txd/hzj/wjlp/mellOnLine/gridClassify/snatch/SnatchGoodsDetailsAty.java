package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt.GraphicDetailsFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt.PastFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt.RecordFgt;

import java.util.ArrayList;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：下午 1:33
 * 描述：一元夺宝详情
 * ===============Txunda===============
 */
public class SnatchGoodsDetailsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private ArrayList<Integer> image;

    @ViewInject(R.id.cpb_progresbar2)
    private CustomProgressBar cpb_progresbar2;

    //==========参与记录，图文详情，往期揭晓，规则说明============
    /**
     * 参与详情
     */
    @ViewInject(R.id.left_tv)
    private TextView left_tv;
    @ViewInject(R.id.left_view)
    private View left_view;
    /**
     * 图文详情
     */
    @ViewInject(R.id.middle_left_tv)
    private TextView middle_left_tv;
    @ViewInject(R.id.middle_left_view)
    private View middle_left_view;
    /**
     * 往期揭晓
     */
    @ViewInject(R.id.middle_right_tv)
    private TextView middle_right_tv;
    @ViewInject(R.id.middle_right_view)
    private View middle_right_view;
    /**
     * 规则说明
     */
    @ViewInject(R.id.righr_tv)
    private TextView righr_tv;
    @ViewInject(R.id.right_view)
    private View right_view;

    private FragmentChangeManager changeManager;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("一元夺宝");

        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, Settings
                .displayWidth);
        online_carvouse_view.setLayoutParams(layoutParams);
        forBanner();
        cpb_progresbar2.setMaxProgress(100);
        cpb_progresbar2.setCurProgress(50);

        changeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.snatch_goods_layout, fragments);
        setTvStyle(0);
    }

    @Override
    @OnClick({R.id.left_lin_layout, R.id.middle_lin_layout, R.id.middle_right_lin_layout, R.id.right_lin_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_lin_layout:// 参与记录
                setTvStyle(0);
                break;
            case R.id.middle_lin_layout:// 图文详情
                setTvStyle(1);
                break;
            case R.id.middle_right_lin_layout:// 往期揭晓
                setTvStyle(2);
                break;
            case R.id.right_lin_layout:// 规则说明
                setTvStyle(3);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_snatch_goods_details;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);

        fragments = new ArrayList<>();
        fragments.add(new RecordFgt());
        fragments.add(new GraphicDetailsFgt());
        fragments.add(new PastFgt());
        fragments.add(new GraphicDetailsFgt());
    }

    @Override
    protected void requestData() {

    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setPageCount(image.size());
        online_carvouse_view.setImageListener(imageListener);
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

    private void setTvStyle(int pos) {

        left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        middle_left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        middle_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        middle_right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        middle_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        righr_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == pos) {
            left_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        } else if (1 == pos) {
            middle_left_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            middle_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        } else if (2 == pos) {
            middle_right_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            middle_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        } else {
            righr_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        }
        changeManager.setFragments(pos);
    }

}
