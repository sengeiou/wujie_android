package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.street.ThemePst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 3:39
 * 描述：5-1主题街2(主题街商品列表)
 */
public class ThemeGoodsListAty extends BaseAty implements NestedScrollView.OnScrollChangeListener {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 主题街商品
     */
    @ViewInject(R.id.theme_goods_gv)
    private GridViewForScrollView theme_goods_gv;

    private List<Map<String, String>> list;
    private ThemeGoodsAdapter themeGoodsAdapter;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    /**
     * 滚动监听的ScrollView
     */
    @ViewInject(R.id.tg_sc)
    private NestedScrollView tg_sc;
    /**
     * 回到顶部
     */
    @ViewInject(R.id.tg_be_back_top_iv)
    private ImageView tg_be_back_top_iv;

    /**
     * 上拉加载，下拉刷新
     */
    @ViewInject(R.id.ptr_theme_goods_layout)
    private SuperSwipeRefreshLayout ptr_theme_goods_layout;
    private int size1 = 0;
    private int size2 = 0;
    private int logo_size1 = 0;
    private int logo_size2 = 0;
    private int goods_size = 0;
    /**
     * 主题图片
     */
    @ViewInject(R.id.theme_piv_iv)
    private ImageView theme_piv_iv;

    private int p = 1;
    private ThemePst themePst;
    private String theme_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("主题街");

        size1 = Settings.displayWidth;
        size2 = Settings.displayWidth / 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size1, size2);
        theme_piv_iv.setLayoutParams(params);
        theme_goods_gv.setFocusable(false);
        theme_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              /*  Bundle bundle = new Bundle();
                bundle.putString("goods_id", list.get(i).get("goods_id"));
                startActivity(ThemeGoodsDetailsAty.class, bundle);*/
                Bundle bundle = new Bundle();
                bundle = new Bundle();
                bundle.putString("ticket_buy_id", list.get(i).get("goods_id"));
                bundle.putInt("from", 1);
                startActivity(TicketGoodsDetialsAty.class, bundle);

            }
        });
        tg_sc.smoothScrollTo(0, 0);
        tg_sc.setOnScrollChangeListener(this);
        ptr_theme_goods_layout.setHeaderViewBackgroundColor(Color.WHITE);
        ptr_theme_goods_layout.setHeaderView(createHeaderView());// add headerView
        ptr_theme_goods_layout.setFooterView(createFooterView());
        ptr_theme_goods_layout.setTargetScrollWithLayout(true);
        ptr_theme_goods_layout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                themePst.themeGoods(theme_id, p);
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        ptr_theme_goods_layout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                themePst.themeGoods(theme_id, p);
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });

    }

    @Override
    @OnClick({R.id.tg_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tg_be_back_top_iv:
                tg_sc.smoothScrollTo(0, 0);
                tg_be_back_top_iv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_theme_goods_list;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        theme_id = getIntent().getStringExtra("theme_id");
        themeGoodsAdapter = new ThemeGoodsAdapter();
        themePst = new ThemePst(this);
        logo_size1 = ToolKit.dip2px(this, 36);
        logo_size2 = ToolKit.dip2px(this, 24);
        goods_size = ToolKit.dip2px(this, 180);
    }

    @Override
    protected void requestData() {
        themePst.themeGoods(theme_id, p);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("themeGoods")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                Glide.with(this).load(data.get("theme_img"))
                        .override(size1, size2)
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(theme_piv_iv);
                if (1 == p) {
                    if (ToolKit.isList(data, "list")) {
                        list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                        theme_goods_gv.setAdapter(themeGoodsAdapter);
                    }
                    progressBar.setVisibility(View.GONE);
                    ptr_theme_goods_layout.setRefreshing(false); // 刷新成功
                } else {
                    if (ToolKit.isList(data, "list")) {
                        List<Map<String, String>> list2 = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                        if (!ListUtils.isEmpty(list2)) {
                            list.addAll(list2);
                            themeGoodsAdapter.notifyDataSetChanged();
                        }
                    }
                    footerImageView.setVisibility(View.GONE);
                    footerProgressBar.setVisibility(View.VISIBLE);
                    ptr_theme_goods_layout.setLoadMore(false); // 刷新成功
                    themeGoodsAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY < Settings.displayWidth / 2) {
            tg_be_back_top_iv.setVisibility(View.GONE);
        } else {
            tg_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private class ThemeGoodsAdapter extends BaseAdapter {
        private TGVH tgvh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> goods = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(ThemeGoodsListAty.this).inflate(R.layout.item_goods_info, null);
                tgvh = new TGVH();
                ViewUtils.inject(tgvh, view);
                view.setTag(tgvh);
            } else {
                tgvh = (TGVH) view.getTag();
            }

            tgvh.theme_layout.setVisibility(View.VISIBLE);
            if (goods.get("ticket_buy_id").equals("0")) {
                tgvh.use_coupon_tv.setText("不可使用代金券");
                tgvh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
            } else {
                tgvh.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
                tgvh.use_coupon_tv.setText("最多可使用" + goods.get("ticket_buy_discount") + "%代金券");
            }
            // 国旗
            Glide.with(ThemeGoodsListAty.this).load(goods.get("country_logo"))
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(logo_size1, logo_size2)
                    .placeholder(R.drawable.ic_default)
                    .dontAnimate()
                    .fitCenter()
                    .into(tgvh.logo_for_country_iv);
            // 商品图片
            Glide.with(ThemeGoodsListAty.this).load(goods.get("goods_img"))
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(goods_size, goods_size)
                    .placeholder(R.drawable.ic_default)
                    .fitCenter()
                    .into(tgvh.goods_pic_iv);

            // 名称
            tgvh.item_goods_name_tv.setText(goods.get("goods_name"));
            // 价格
            tgvh.peice_tv.setText("¥" + goods.get("shop_price"));
            // 原价
            tgvh.older_price_tv.setText(goods.get("market_price"));
            tgvh.older_price_tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            // 积分
            tgvh.get_integral_tv.setText(goods.get("integral"));
            tgvh.sold_num_tv.setText("已售" + goods.get("sell_num") + "件");

            return view;
        }

        class TGVH {
            /**
             * 优惠券，国旗布局
             */
            @ViewInject(R.id.theme_layout)
            private LinearLayout theme_layout;
            /**
             * 国旗
             */
            @ViewInject(R.id.logo_for_country_iv1)
            private ImageView logo_for_country_iv;

            /**
             * 优惠券
             */
            @ViewInject(R.id.use_coupon_tv1)
            private TextView use_coupon_tv;
            /**
             * 商品图片
             */
            @ViewInject(R.id.goods_pic_iv)
            private ImageView goods_pic_iv;
            /**
             * 商品名称
             */
            @ViewInject(R.id.item_goods_name_tv)
            private TextView item_goods_name_tv;
            /**
             * 商品价格
             */
            @ViewInject(R.id.peice_tv)
            private TextView peice_tv;
            /**
             * 商品原价
             */
            @ViewInject(R.id.older_price_tv)
            private TextView older_price_tv;
            /**
             * 商品积分
             */
            @ViewInject(R.id.get_integral_tv)
            private TextView get_integral_tv;
            /**
             * 商品销售量
             */
            @ViewInject(R.id.sold_num_tv)
            private TextView sold_num_tv;

        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(ptr_theme_goods_layout.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(ptr_theme_goods_layout.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }
}
