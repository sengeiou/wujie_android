package com.txd.hzj.wjlp.mellOffLine.point;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Mell;
import com.txd.hzj.wjlp.mainFgt.adapter.MellNearByHzjAdapter;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/28 0028
 * 时间：下午 2:46
 * 描述：无界驿站
 * ===============Txunda===============
 */
public class PointWjAty extends BaseAty {

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

    /**
     * 附近商家
     */
    @ViewInject(R.id.point_mell_lv)
    private ListViewForScrollView point_mell_lv;

    @ViewInject(R.id.point_sc)
    private ObservableScrollView point_sc;

    private List<Mell> mells;

    private MellNearByHzjAdapter mellNearByHzjAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("无界驿站");
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth / 2);
        online_carvouse_view.setLayoutParams(layoutParams);
        // 轮播图
        forBanner();

        point_sc.smoothScrollTo(0, 0);
        point_mell_lv.setAdapter(mellNearByHzjAdapter);

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

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_point_wj;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        mells = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if(i%2==0){
                mells.add(new Mell(true, false));
            } else {
                mells.add(new Mell(false, false));
            }
        }
        mellNearByHzjAdapter = new MellNearByHzjAdapter(this, mells);
    }

    @Override
    protected void requestData() {

    }
}
