package com.txd.hzj.wjlp.mellonLine.fgt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CFGoodsList;
import com.txd.hzj.wjlp.bean.TwoCateListBean;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mainfgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.TicketZoonAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellonLine.SubclassificationAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.ExplosiveAreaGoodsDetialsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/12/18 14:45
 * 功能描述：首页横向分类
 */
public class ExplosiveClassifyFgt extends BaseFgt {
    private String type;
    @ViewInject(R.id.ntsv)
    NestedScrollView ntsv;
    @ViewInject(R.id.classify_goods_rv)
    private RecyclerView classify_goods_rv;

    private ExplosiveAdapter mExplosiveAdapter;

    private List<CFGoodsList> goodsLists;

    private int height = 0;


    @ViewInject(R.id.classify_vp)
    private ViewPager goods_menu_vp;
    private int pageSize = 10;
    private int curIndex = 0;
    /**
     * GridView数据列表
     */
    private List<TwoCateListBean> gv_classify;

    private GoodsPst goodsPst;
    private int p = 1;
    /**
     * 广告
     */
    @ViewInject(R.id.classify_ads_iv)
    private ImageView classify_ads_iv;

    private int ads_h;
    private String href = "";
    private String desc = "";
    private Bundle bundle;

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

    @ViewInject(R.id.super_classify_layout)
    private SuperSwipeRefreshLayout swipe_refresh;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    public static ExplosiveClassifyFgt newInstance(String type) {
        ExplosiveClassifyFgt fragment = new ExplosiveClassifyFgt();
        fragment.type = type;
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        classify_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        classify_goods_rv.setItemAnimator(new DefaultItemAnimator());
        classify_goods_rv.setHasFixedSize(true);
        classify_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        classify_goods_rv.setNestedScrollingEnabled(false);


        ads_h = Settings.displayWidth / 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Settings.displayWidth, ads_h);
        classify_ads_iv.setLayoutParams(params);

        swipe_refresh.setHeaderViewBackgroundColor(Color.WHITE);
        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
        swipe_refresh.setTargetScrollWithLayout(true);


        swipe_refresh
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        goodsPst.goodsList(p, type, 0,"5");
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

        swipe_refresh
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        frist = false;
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);

                        p++;
                        goodsPst.goodsList(p, type, 0,"5");
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {

        return R.layout.fragment_classify_fgt;

    }

    @Override
    protected void initialized() {
        goodsLists = new ArrayList<>();
        height = ToolKit.dip2px(getActivity(), 4);
        gv_classify = new ArrayList<>();


    }

    @Override
    protected void requestData() {
        goodsPst = new GoodsPst(this);
        goodsPst.goodsList(p, type, 0,"5");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("goodsList")) {
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

                if (1 == p) {
                    if (ToolKit.isList(data, "ads")) {
                        final Map<String, String> ads = JSONUtils.parseKeyAndValueToMap(data.get("ads"));

                        //                        621:200
                        ViewGroup.LayoutParams lp = classify_ads_iv.getLayoutParams();
                        lp.width = Settings.displayWidth;
                        lp.height = Settings.displayWidth * 200 / 621;
                        classify_ads_iv.setLayoutParams(lp);
                        classify_ads_iv.setMaxWidth(lp.width);
                        classify_ads_iv.setMaxHeight(lp.width * 200 / 621);

                        Glide.with(getActivity()).load(ads.get("picture"))
                                .override(Settings.displayWidth, ads_h)
                                .error(R.drawable.ic_default)
                                .placeholder(R.drawable.ic_default)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(classify_ads_iv);

                        classify_ads_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(ads.get("merchant_id")) && !ads.get("merchant_id").equals("0")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("mell_id", ads.get("merchant_id"));
                                    startActivity(MellInfoAty.class, bundle);
                                } else if (!TextUtils.isEmpty(ads.get("goods_id")) && !ads.get("goods_id").equals("0")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ticket_buy_id", ads.get("goods_id"));
                                    bundle.putInt("from", 1);
                                    startActivity(ExplosiveAreaGoodsDetialsAty.class, bundle);
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("href", href);
                                    startActivity(WebViewAty.class, bundle);
                                }
                            }
                        });
                        desc = ads.get("desc");
                        href = ads.get("href");
                    }
                    gv_classify = GsonUtil.getObjectList(data.get("two_cate_list"), TwoCateListBean.class);
                    if (!ListUtils.isEmpty(gv_classify)) {
                        LinearLayout.LayoutParams params1;
                        if (gv_classify.size() > 5) {
                            params1 = new LinearLayout.LayoutParams(Settings.displayWidth,
                                    ToolKit.dip2px(getActivity(), 160));
                        } else {
                            params1 = new LinearLayout.LayoutParams(Settings.displayWidth,
                                    ToolKit.dip2px(getActivity(), 80));
                        }
                        goods_menu_vp.setLayoutParams(params1);
                        forMenu();
                    }

                    if (ToolKit.isList(data, "list")) {
                        goodsLists = GsonUtil.getObjectList(data.get("list"), CFGoodsList.class);
                        mExplosiveAdapter = new ExplosiveAdapter(getActivity(), goodsLists);

                        classify_goods_rv.setAdapter(mExplosiveAdapter);
                        classify_goods_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        mExplosiveAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                bundle = new Bundle();
                                bundle.putString("ticket_buy_id", goodsLists.get(position).getGoods_id());
                                bundle.putInt("from", 1);
                                startActivity(ExplosiveAreaGoodsDetialsAty.class, bundle);
                            }
                        });
                    } else {
                        classify_goods_rv.setVisibility(View.GONE);
                        no_data_layout.setVisibility(View.VISIBLE);
                    }
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {

                    if (ToolKit.isList(data, "list")) {
                        goodsLists.addAll(GsonUtil.getObjectList(data.get("list"), CFGoodsList.class));
                        mExplosiveAdapter.notifyDataSetChanged();
                    }

                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            } else {
                if (1 == p) {
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            }
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        if (!type.equals("0") && !type.equals("")) {
            super.onError(requestUrl, error);
        }
    }

    private void forMenu() {
        // 获取总页数
        int pageCount = (int) Math.ceil(gv_classify.size() * 1.0 / pageSize);
        // 初始化View列表
        ArrayList<View> mPagerList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < pageCount; i++) {
            GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout,
                    goods_menu_vp, false);
            gridView.setAdapter(new TicketZoonAdapter(getActivity(), gv_classify, i));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int itemPos = i + curIndex * pageSize;
                    bundle = new Bundle();
                    bundle.putString("appBarTitle", gv_classify.get(itemPos).getName());
                    bundle.putString("two_cate_id", gv_classify.get(itemPos).getTwo_cate_id());
                    bundle.putString("is_active", "5");
                    startActivity(SubclassificationAty.class, bundle);
                }
            });
            // 给ViewPager设置适配器
            goods_menu_vp.setAdapter(new ViewPagerAdapter(mPagerList));
            // 添加页面改变监听事件
            goods_menu_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    curIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext())
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
        View headerView = LayoutInflater.from(swipe_refresh.getContext())
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

    public static class ExplosiveAdapter extends RecyclerView.Adapter<ExplosiveAdapter.ItemView> {
        private Context context;
        private List<CFGoodsList> list;
        private LayoutInflater inflater;

        private int logo_size1 ;
        private int logo_size2 ;
        private int imgWidth;
        public ExplosiveAdapter(Context context, List<CFGoodsList> list) {
            this.context = context;
            this.list = list;
            this.inflater = LayoutInflater.from(context);
            logo_size1 = ToolKit.dip2px(context, 36);
            logo_size2 = ToolKit.dip2px(context, 23);
            imgWidth=(ToolKit.getScreenWidth(context) - 10) / 2;
        }


        @Override
        public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            view = inflater.inflate(R.layout.explosive_item, parent, false);
            ExplosiveAdapter.ItemView itemView = new ExplosiveAdapter.ItemView(view);
            ViewUtils.inject(itemView, view);
            return itemView;
        }

        @Override
        public void onBindViewHolder(final ExplosiveAdapter.ItemView holder, int position) {
            final CFGoodsList cfGoodsList = list.get(position);
            if (itemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getLayoutPosition();
                        itemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }

            Glide.with(context).load(cfGoodsList.getCountry_logo())
                    .centerCrop()
                    .override(logo_size1, logo_size2)
                    .placeholder(R.drawable.ic_default)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.goods_icon);

            Glide.with(context).load(cfGoodsList.getGoods_img())
                    .override(imgWidth,imgWidth)
                    .centerCrop().placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .error(R.drawable.ic_default)
                    .into(holder.goods_img);

            holder.goods_name_tv.setText(cfGoodsList.getGoods_name());
            holder.goods_price_info_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.goods_price_info_tv.setText("¥"+cfGoodsList.getMarket_price());
            holder.num_tv.setText(" 已售"+cfGoodsList.getSell_num()+"件");
            holder.goods_price_tv.setText("爆款价 ¥"+cfGoodsList.getShop_price());

        }

        @Override
        public int getItemCount() {
            return  list.size()>0?list.size():0;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        class ItemView extends RecyclerView.ViewHolder {

            @ViewInject(R.id.goods_name_tv)
            private TextView goods_name_tv;

            @ViewInject(R.id.goods_price_info_tv)
            private TextView goods_price_info_tv;

            @ViewInject(R.id.num_tv)
            private TextView num_tv;

            @ViewInject(R.id.goods_price_tv)
            private TextView goods_price_tv;


            /**
             * 国旗
             */
            @ViewInject(R.id.goods_icon)
            private ImageView goods_icon;

            /**
             * 商品图片
             */
            @ViewInject(R.id.goods_img)
            private ImageView goods_img;


            ItemView(View itemView) {
                super(itemView);
            }
        }

        private HorizontalAdapter.OnItemClickLitener itemClickLitener;

        public void setListener(HorizontalAdapter.OnItemClickLitener itemClickLitener) {
            this.itemClickLitener = itemClickLitener;
        }


        @Override
        public void onViewRecycled(ItemView holder) {
            super.onViewRecycled(holder);
            Glide.clear(holder.goods_img);
        }
    }
}
