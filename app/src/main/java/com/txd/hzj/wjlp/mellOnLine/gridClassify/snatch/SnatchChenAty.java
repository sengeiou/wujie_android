package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.banner.BannerAdapter;
import com.ants.theantsgo.view.banner.DotView;
import com.ants.theantsgo.view.banner.SliderBanner;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.HousDetailsHousesChenFgt;

import java.util.ArrayList;
import java.util.List;


/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/13 0007
 * 时间：下午17:10
 * 描述： 一元夺宝(11-1房产购)
 * ===============Txunda===============
 */
public class SnatchChenAty extends BaseAty{
    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;

    /**
     *轮播图
     * @param savedInstanceState
     */
    @ViewInject(R.id.sb_snatch_banner)
    private SliderBanner sb_snatch_banner;
    @ViewInject(R.id.sb_snatch_viewpager)
    private ViewPager sb_snatch_viewpager;
    @ViewInject(R.id.sb_snatch_dotview)
    private DotView sb_snatch_dotview;
    private MyViewAdapter adapter;//轮播图适配器
    private List<ImageView> albums;//ImageView控件
    private List<Integer> list;//图片






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_snatch_chen;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("一元夺宝");
        /** 轮播图初始化 **/
        initBanner();

    }

    @Override
    protected void requestData() {

    }

    /**
     * 轮播图
     */
    private void initBanner() {
        adapter = new MyViewAdapter();//轮播图适配器初始化
        albums = new ArrayList<ImageView>();
        list=new ArrayList<Integer>();
        list.add(R.mipmap.icon_car_two_chen);
        list.add(R.mipmap.icon_car_two_chen);
        list.add(R.mipmap.icon_car_two_chen);
        list.add(R.mipmap.icon_car_two_chen);
        startBanner();//启动轮播图

    }


    /**
     *启动轮播图
     */
    private void startBanner() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Settings.displayWidth, Settings.displayWidth*2/5);
        sb_snatch_banner.setLayoutParams(params);
        for(int i=0;i<list.size();i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            albums.add(imageView);
        }

        sb_snatch_banner.setAdapter(adapter);
        sb_snatch_banner.setDotNum(ListUtils.getSize(albums));
        sb_snatch_banner.beginPlay();

    }


    /**
     * 轮播图适配器
     *
     * @author Administrator
     *
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
}
