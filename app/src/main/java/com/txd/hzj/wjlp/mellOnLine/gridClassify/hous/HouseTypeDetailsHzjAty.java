package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.ArrayList;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：下午 4:09
 * 描述：10-4房产详情3(户型详情)
 * ===============Txunda===============
 */
public class HouseTypeDetailsHzjAty extends BaseAty {

    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 商品轮播
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private ArrayList<Integer> image;

    /**
     * 大小
     */
    @ViewInject(R.id.house_area_tv)
    private TextView house_area_tv;
    /**
     * 大小
     */
    @ViewInject(R.id.house_loc_tv)
    private TextView house_loc_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("95平户型");
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth);
        online_carvouse_view.setLayoutParams(layoutParams);
        forBanner();

        ChangeTextViewStyle.getInstance().forTextColorSub(this, house_area_tv, "建筑面积:75.00m2", 5,
                ContextCompat.getColor(this, R.color.app_text_color));



        ChangeTextViewStyle.getInstance().forTextColor(this, house_loc_tv, "楼        盘:大运河孔雀城学府公园", 11,
                ContextCompat.getColor(this, R.color.app_text_color));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_house_type_details_hzj;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
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
