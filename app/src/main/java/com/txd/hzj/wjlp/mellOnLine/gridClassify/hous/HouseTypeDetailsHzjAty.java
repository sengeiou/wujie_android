package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.house.HouseBuyPst;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.txunda_lh.aty_submit_order;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ArrayList<Map<String, String>> image;

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

    private String style_id = "";

    private HouseBuyPst houseBuyPst;

    @ViewInject(R.id.style_name_tv)
    private TextView style_name_tv;
    @ViewInject(R.id.style_tags_tv)
    private TextView style_tags_tv;
    @ViewInject(R.id.style_pre_money_tv)
    private TextView style_pre_money_tv;
    @ViewInject(R.id.style_one_price_tv)
    private TextView style_one_price_tv;
    @ViewInject(R.id.style_integral_tv)
    private TextView style_integral_tv;
    private List<Map<String, String>> other_style;
    private int size = 0;
    private int size1 = 0;
    private int size2 = 0;
    private Map<String, String> data;

    private String easemob_account = "";
    private String merchant_logo = "";
    private String merchant_name = "";
    @ViewInject(R.id.tv_car_num)
    private TextView tv_car_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth);
        online_carvouse_view.setLayoutParams(layoutParams);

        hxd_sc.setScrollViewListener(this);

        other_lp_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                style_id = other_style.get(i).get("style_id");
                houseBuyPst.styleInfo(style_id);
            }
        });
        titlt_conter_tv.setText("户型");

    }


    @Override
    @OnClick({R.id.hxd_be_back_top_iv, R.id.submit, R.id.be_back_main_tv, R.id.go_to_cart_layout, R.id.to_chat_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hxd_be_back_top_iv:
                hxd_sc.smoothScrollTo(0, 0);
                break;
            case R.id.submit:
                if (!Config.isLogin()) {
                    toLogin();
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", data.get("id"));
                bundle.putString("image", data.get("house_style_img"));
                bundle.putString("title", data.get("house_name"));
                bundle.putString("price", data.get("pre_money"));
                bundle.putString("money", toltal_payment_tv.getText().toString() + "\n" + counteract_price_tv.getText().toString());
                bundle.putString("developer", data.get("developer") + "\t\t" + data.get("one_price") + "元/平");
                bundle.putString("type", "2");
                startActivity(aty_submit_order.class, bundle);

                break;
            case R.id.be_back_main_tv:// 首页
                backMain(0);
                break;
            case R.id.go_to_cart_layout:// 购物车
                backMain(2);
                break;
            case R.id.to_chat_tv:// 客服
                toChat(easemob_account, merchant_logo, merchant_name);
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
        houseBuyPst = new HouseBuyPst(this);
        style_id = getIntent().getStringExtra("style_id");
        other_style = new ArrayList<>();
        size = ToolKit.dip2px(this, 180);
        size1 = ToolKit.dip2px(this, 32);
        size2 = ToolKit.dip2px(this, 24);
    }

    @Override
    protected void requestData() {
        houseBuyPst.styleInfo(style_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("styleInfo")) {
            data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
            if (ToolKit.isList(data, "banner")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("banner"));
                forBanner();
            }
            easemob_account = data.get("merchant_easemob_account");
            merchant_logo = data.get("server_head_pic");
            merchant_name = data.get("server_name");
//            titlt_conter_tv.setText(data.get("style_name"));
            tv_car_num.setText(data.get("cart_num"));
            if (data.get("cart_num").equals("0")) {
                tv_car_num.setVisibility(View.GONE);
            }
            ChangeTextViewStyle.getInstance().forTextColor(this, counteract_price_tv, "可抵:￥" +
                    data.get("true_pre_money") + "房款", 11, ContextCompat.getColor(this, R.color.app_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, toltal_payment_tv, "房全款:￥" + data.get("all_price"), 5,
                    ContextCompat.getColor(this, R.color.app_text_color));
            ChangeTextViewStyle.getInstance().forTextColorSub(this, house_area_tv, "建筑面积:" + data.get("area") + "m2", 5,
                    ContextCompat.getColor(this, R.color.app_text_color));
            ChangeTextViewStyle.getInstance().forTextColor(this, house_loc_tv, "楼        盘:" +
                    data.get("house_address"), 11, ContextCompat.getColor(this, R.color.app_text_color));

            style_name_tv.setText(data.get("style_name"));
            style_tags_tv.setText(data.get("tags"));
            style_pre_money_tv.setText(data.get("pre_money"));
            style_integral_tv.setText(data.get("integral"));
            style_one_price_tv.setText(data.get("one_price") + "元/平");

            if (ToolKit.isList(data, "other_style")) {
                other_style = JSONUtils.parseKeyAndValueToMapList(data.get("other_style"));
                hxAdapter = new HXAdapter();
                other_lp_gv.setAdapter(hxAdapter);
            }


        }
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

            Glide.with(HouseTypeDetailsHzjAty.this).load(image.get(position).get("path"))
                    .override(Settings.displayWidth, Settings.displayWidth)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .into(imageView);

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
        private HXVH holder;

        @Override
        public int getCount() {
            return other_style.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return other_style.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(HouseTypeDetailsHzjAty.this).inflate(R.layout.item_house_type_chen, null);
                holder = new HXVH();
                ViewUtils.inject(holder, view);
                view.setTag(holder);
            } else {
                holder = (HXVH) view.getTag();
            }

            int total;
            try {
                total = Integer.parseInt(map.get("total"));
            } catch (NumberFormatException e) {
                total = 100;
            }

            int sell_num;
            try {
                sell_num = Integer.parseInt(map.get("sell_num"));
            } catch (NumberFormatException e) {
                sell_num = 0;
            }

            holder.house_type_cpb.setMaxProgress(total);
            holder.house_type_cpb.setCurProgress(sell_num);

            Glide.with(HouseTypeDetailsHzjAty.this).load(map.get("house_style_img"))
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.house_style_pic_iv);

            Glide.with(HouseTypeDetailsHzjAty.this).load(map.get("country_logo"))
                    .override(size1, size2)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.logo_for_country_iv);

            holder.style_name_tv.setText(map.get("style_name"));

            holder.style_developer_tv.setText(map.get("developer"));

            holder.style_pre_money_tv.setText("￥" + map.get("pre_money"));

            holder.textView2.setText("可    抵：￥" + map.get("true_pre_money") + "\n房全款：￥" + map.get("all_price"));

            holder.one_price_tv.setText(map.get("one_price") + "元/平");

            holder.use_coupon_tv.setText("可使用" + map.get("ticket_discount") + "%购物券");

            return view;
        }

        private class HXVH {
            @ViewInject(R.id.house_type_cpb)
            private CustomProgressBar house_type_cpb;
            /**
             * 户型图
             */
            @ViewInject(R.id.house_style_pic_iv)
            private ImageView house_style_pic_iv;
            /**
             * 户型名称
             */
            @ViewInject(R.id.style_name_tv)
            private TextView style_name_tv;
            /**
             * 户型开发商
             */
            @ViewInject(R.id.style_developer_tv)
            private TextView style_developer_tv;
            /**
             * 户型代金券
             */
            @ViewInject(R.id.style_pre_money_tv)
            private TextView style_pre_money_tv;
            /**
             * 户型其他信息
             */
            @ViewInject(R.id.textView2)
            private TextView textView2;
            /**
             * 户型积分
             */
            @ViewInject(R.id.style_integral_tv)
            private TextView style_integral_tv;
            /**
             * 国旗
             */
            @ViewInject(R.id.logo_for_country_iv)
            private ImageView logo_for_country_iv;
            /**
             * 优惠券
             */
            @ViewInject(R.id.use_coupon_tv)
            private TextView use_coupon_tv;
            /**
             * 优惠券
             */
            @ViewInject(R.id.one_price_tv)
            private TextView one_price_tv;
        }
    }

}
