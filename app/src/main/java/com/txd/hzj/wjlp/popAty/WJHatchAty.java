package com.txd.hzj.wjlp.popAty;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.popAty.adapter.RedPackageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 7:51
 * 描述：上市孵化
 * ===============Txunda===============
 */
public class WJHatchAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;

    /**
     * 轮播图图片
     */
    private ArrayList<Integer> image;

    @ViewInject(R.id.hatch_lv)
    private ListViewForScrollView hatch_lv;

    private RedPackageAdapter redPackageAdapter;

    @ViewInject(R.id.wjh_sc)
    private ScrollView wjh_sc;

    private List<Map<String,String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("上市孵化");

        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth / 2);
        online_carvouse_view.setLayoutParams(layoutParams);

        forBanner();

        hatch_lv.setAdapter(redPackageAdapter);
        hatch_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(HatchDetailsAty.class, null);
            }
        });
        wjh_sc.scrollTo(0, 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_wjhatch_hzj;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        redPackageAdapter = new RedPackageAdapter(this, 0,list);

        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
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
}
