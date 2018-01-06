package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.auction.AdsBean;
import com.txd.hzj.wjlp.bean.auction.AuctionList;
import com.txd.hzj.wjlp.bean.auction.AuctonIndex;
import com.txd.hzj.wjlp.http.auction.AuctionPst;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.LimitAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：下午 1:41
 * 描述：竞拍汇(8-1竞拍汇)
 * ===============Txunda===============
 */
public class AuctionCollectAty extends BaseAty {


    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private int type;
    private List<AuctionList> list;
    private List<AuctionList> list2;

    @ViewInject(R.id.left_tv)
    private TextView left_tv;
    @ViewInject(R.id.left_view)
    private View left_view;

    @ViewInject(R.id.righr_tv)
    private TextView right_tv;

    @ViewInject(R.id.right_view)
    private View right_view;

    @ViewInject(R.id.suction_collect_gv)
    private GridViewForScrollView suction_collect_gv;
    private LimitAdapter limitAdapter;

    private AuctionPst auctionPst;

    private int p = 1;
    private int next = 1;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

    @ViewInject(R.id.super_sr_layout)
    private SuperSwipeRefreshLayout super_sr_layout;

    /**
     * 广告轮播
     */
    @ViewInject(R.id.ads_for_auction_iv)
    private ImageView ads_for_auction_iv;

    private CollapsingToolbarLayout.LayoutParams params;
    private int data_type = 0;
    private int setRemind = -1;

    private Set<String> auction_ids;
    /**
     * 设置要提醒的商品
     */
    private String auction_id = "";
    private String desc = "";
    private String href = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("比价购");
        changeViewStatus(0);
        suction_collect_gv.setEmptyView(no_data_layout);
        suction_collect_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("auction_id", list.get(i).getAuction_id());
                startActivity(AuctionGoodsDetailsAty.class, bundle);
            }
        });

        params = new CollapsingToolbarLayout.LayoutParams(Settings.displayWidth, Settings.displayWidth *400/ 1242);
        ads_for_auction_iv.setLayoutParams(params);

        getDataForPage();
    }

    /**
     * 分页数据
     */
    private void getDataForPage() {
        super_sr_layout.setHeaderViewBackgroundColor(0xff888888);
        super_sr_layout.setHeaderView(createHeaderView());// add headerView
        super_sr_layout.setFooterView(createFooterView());
        super_sr_layout.setTargetScrollWithLayout(true);

        super_sr_layout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        auctionPst.auctionIndex(next, p);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

        super_sr_layout
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        frist = false;
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);

                        p++;
                        auctionPst.auctionIndex(next, p);
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {

                    }

                });
    }

    private void changeViewStatus(int i) {
        left_tv.setTextColor(Color.parseColor("#FFC8C9"));
        right_tv.setTextColor(Color.parseColor("#FFC8C9"));
        left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        if (0 == i) {
            left_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        } else {
            right_tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        }
        data_type = i;
        auctionPst.auctionIndex(next, p);
    }

    @Override
    @OnClick({R.id.ads_for_auction_iv, R.id.left_lin_layout, R.id.right_lin_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ads_for_auction_iv:
                Bundle bundle = new Bundle();
                bundle.putInt("from", 2);
                bundle.putString("desc", desc);
                bundle.putString("href", href);
                startActivity(NoticeDetailsAty.class, bundle);
                break;
            case R.id.left_lin_layout:// 左(今日拍卖)
                next = 2;
                p = 1;
                changeViewStatus(0);
                break;
            case R.id.right_lin_layout:// 右(拍卖预展)
                next = 1;
                p = 1;
                changeViewStatus(1);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auction_collect;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        type = getIntent().getIntExtra("type", 0);
        auctionPst = new AuctionPst(this);
        auction_ids = new HashSet<>();
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("auctionIndex")) {
            AuctonIndex index = GsonUtil.GsonToBean(jsonStr, AuctonIndex.class);
            if (1 == p) {
                AdsBean adsBean = index.getData().getAds();
                desc = adsBean.getDesc();
                href = adsBean.getHref();

                Glide.with(this).load(adsBean.getPicture())
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(ads_for_auction_iv);

                list = index.getData().getAuction_list();
                if (!ListUtils.isEmpty(list)) {
                    limitAdapter = new LimitAdapter(list, this, data_type, 1);
                    suction_collect_gv.setAdapter(limitAdapter);
                    limitAdapter.setTvClick(new AdapterTextViewClickListener() {
                        @Override
                        public void onTextViewClick(View v, int position) {
                            switch (v.getId()) {
                                case R.id.limit_remeber_me_tv:// 设置提醒
                                    if (!Config.isLogin()) {
                                        toLogin();
                                        return;
                                    }
                                    if (list.get(position).getIs_remind().equals("1")) {
                                        showRightTip("您已经设置过提醒");
                                        return;
                                    }
                                    setRemind = position;
                                    auction_id = list.get(position).getAuction_id();
                                    auctionPst.remindMe(list.get(position).getAuction_id());
                                    break;
                            }
                        }
                    });
                }

                if (!frist) {
                    super_sr_layout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                list2 = index.getData().getAuction_list();
                if (!ListUtils.isEmpty(list2)) {
                    list.addAll(list2);
                    limitAdapter.setType(data_type);
                    limitAdapter.notifyDataSetChanged();
                }
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                super_sr_layout.setLoadMore(false);
            }
            if (list.size() == 0) {
                list.clear();
                limitAdapter = new LimitAdapter(list, this, data_type, 1);
                suction_collect_gv.setAdapter(limitAdapter);
            }
            return;
        }
        if (requestUrl.contains("remindMe")) {
            showRightTip("设置成功");
            if (setRemind >= 0) {
                list.get(setRemind).setIs_remind("1");
            }
            limitAdapter.setType(1);
            limitAdapter.notifyDataSetChanged();
            auction_ids.add(auction_id);
        }

    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(super_sr_layout.getContext())
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
        View headerView = LayoutInflater.from(super_sr_layout.getContext())
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
