package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：下午 4:09
 * 描述：10-4房产详情3(户型详情)
 * ===============Txunda===============
 */
public class HouseTypeDetailsHzjAty extends BaseAty implements ObservableScrollView.ScrollViewListener {

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

    /**
     * 可低房价
     */
    @ViewInject(R.id.counteract_price_tv)
    private TextView counteract_price_tv;
    /**
     * 全款房价
     */
    @ViewInject(R.id.toltal_payment_tv)
    private TextView toltal_payment_tv;

    @ViewInject(R.id.hxd_be_back_top_iv)
    private ImageView hxd_be_back_top_iv;

    @ViewInject(R.id.hxd_sc)
    private ObservableScrollView hxd_sc;

    @ViewInject(R.id.other_lp_gv)
    private GridViewForScrollView other_lp_gv;

    private HXAdapter hxAdapter;

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

        ChangeTextViewStyle.getInstance().forTextColor(this, counteract_price_tv, "可        低:￥200房款", 11,
                ContextCompat.getColor(this, R.color.app_text_color));
        ChangeTextViewStyle.getInstance().forTextColor(this, toltal_payment_tv, "房款全价:￥9999999.00", 5,
                ContextCompat.getColor(this, R.color.app_text_color));
        ChangeTextViewStyle.getInstance().forTextColorSub(this, house_area_tv, "建筑面积:75.00m2", 5,
                ContextCompat.getColor(this, R.color.app_text_color));


        ChangeTextViewStyle.getInstance().forTextColor(this, house_loc_tv, "楼        盘:大运河孔雀城学府公园", 11,
                ContextCompat.getColor(this, R.color.app_text_color));

        hxd_sc.setScrollViewListener(this);

        other_lp_gv.setAdapter(hxAdapter);

    }


    @Override
    @OnClick({R.id.hxd_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hxd_be_back_top_iv:
                hxd_sc.smoothScrollTo(0, 0);
                break;
        }
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
        hxAdapter = new HXAdapter();
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

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < Settings.displayWidth / 2) {
            hxd_be_back_top_iv.setVisibility(View.GONE);
        } else {
            hxd_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private class HXAdapter extends BaseAdapter {
        private HXVH hxvh;

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(HouseTypeDetailsHzjAty.this).inflate(R.layout.item_house_type_chen, null);
                hxvh = new HXVH();
                ViewUtils.inject(hxvh, view);
                view.setTag(hxvh);
            } else {
                hxvh = (HXVH) view.getTag();
            }
            return view;
        }

        private class HXVH {

        }
    }

}
