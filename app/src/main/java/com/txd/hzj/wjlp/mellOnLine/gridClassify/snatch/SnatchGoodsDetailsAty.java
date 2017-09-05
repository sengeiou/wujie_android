package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.onebuy.OneBuyPst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt.GraphicDetailsFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt.PastFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt.RecordFgt;

import java.util.ArrayList;
import java.util.Map;

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
    private ArrayList<Map<String, String>> image;

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
    /**
     * id
     */
    private String one_buy_id = "";

    private OneBuyPst buyPst;

    /**
     * 一元夺宝商品名称
     */
    @ViewInject(R.id.one_buy_info_name_tv)
    private TextView one_buy_info_name_tv;

    /**
     * 积分
     */
    @ViewInject(R.id.one_buy_goods_intergral_tv)
    private TextView one_buy_goods_intergral_tv;

    /**
     * 期号
     */
    @ViewInject(R.id.time_num_tv)
    private TextView time_num_tv;
    /**
     * 总需
     */
    @ViewInject(R.id.person_num_tv)
    private TextView person_num_tv;
    /**
     * 参与人数
     */
    @ViewInject(R.id.add_num_tv)
    private TextView add_num_tv;
    /**
     * 购买记录
     */
    private String oneBuyLog = "";
    /**
     * 图文详情
     */
    private String goods_desc = "";
    /**
     * 往期记录
     */
    private String outTime_log = "";
    /**
     * 规则
     */
    private String rules = "";

    /**
     * 活动状态
     */
    @ViewInject(R.id.t_status_tv)
    private TextView t_status_tv;
    private String t_status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("一元夺宝");

        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth);
        online_carvouse_view.setLayoutParams(layoutParams);

    }

    @Override
    @OnClick({R.id.left_lin_layout, R.id.middle_lin_layout, R.id.middle_right_lin_layout,
            R.id.right_lin_layout,R.id.rush_to_purchase_tv})
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
            case R.id.rush_to_purchase_tv:// 立即抢购
                if(!t_status.equals("进行中")){
                    showErrorTip("活动已结束");
                    break;
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_snatch_goods_details;
    }

    @Override
    protected void initialized() {
        one_buy_id = getIntent().getStringExtra("one_buy_id");
        image = new ArrayList<>();
        fragments = new ArrayList<>();
        buyPst = new OneBuyPst(this);
    }

    @Override
    protected void requestData() {
        buyPst.oneBuyInfo(one_buy_id);
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
            Glide.with(SnatchGoodsDetailsAty.this).load(image.get(position).get("path"))
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .override(Settings.displayWidth, Settings.displayWidth)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(imageView);
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

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("oneBuyInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (ToolKit.isList(data, "oneBuyInfo")) {
                Map<String, String> oneBuyInfo = JSONUtils.parseKeyAndValueToMap(data.get("oneBuyInfo"));
                // 商品名称
                one_buy_info_name_tv.setText(oneBuyInfo.get("goods_name"));
                // 活动状态
                t_status = oneBuyInfo.get("t_status");
                t_status_tv.setText(t_status);
                if (!t_status.equals("进行中")) {
                    t_status_tv.setBackgroundResource(R.drawable.shape_sn_ing_tv2);
                    t_status_tv.setTextColor(ContextCompat.getColor(this, R.color.view_color));
                }
                // 积分
                one_buy_goods_intergral_tv.setText(oneBuyInfo.get("integral"));
                // 期号
                time_num_tv.setText("期号" + oneBuyInfo.get("time_num"));
                // 总需参与人数
                int all;
                try {
                    all = Integer.parseInt(oneBuyInfo.get("person_num"));
                } catch (NumberFormatException e) {
                    all = 100;
                }
                // 参与人数
                int add;
                try {
                    add = Integer.parseInt(oneBuyInfo.get("add_num"));
                } catch (NumberFormatException e) {
                    add = 0;
                }
                person_num_tv.setText("总需" + all + "人/剩余");
                int num = all - add;
                add_num_tv.setText(String.valueOf(num));
                // 进度条最大值和进度
                cpb_progresbar2.setMaxProgress(all);
                cpb_progresbar2.setCurProgress(add);
            }
            if (ToolKit.isList(data, "goodsGallery")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("goodsGallery"));
                forBanner();
            }
            // 购买记录
            if (ToolKit.isList(data, "oneBuyLog")) {
                oneBuyLog = data.get("oneBuyLog");
            }
            // 图文详情
            if (ToolKit.isList(data, "goods_desc")) {
                goods_desc = data.get("goods_desc");
            }
            // 往期记录
            if (ToolKit.isList(data, "outTime_log")) {
                outTime_log = data.get("outTime_log");
            }
            // 规则说明
            if (ToolKit.isList(data, "rules")) {
                rules = data.get("rules");
            }

            fragments.add(RecordFgt.getFgt(oneBuyLog));
            fragments.add(GraphicDetailsFgt.getFgt(goods_desc));
            fragments.add(PastFgt.getFgt(outTime_log));
            fragments.add(GraphicDetailsFgt.getFgt(rules));

            // 添加碎片到布局
            changeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.snatch_goods_layout, fragments);
            setTvStyle(0);
        }
    }
}
