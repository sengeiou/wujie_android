package com.txd.hzj.wjlp.mellonLine.gridClassify.giveawayarea;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.OpenShopGoods;
import com.txd.hzj.wjlp.tool.WJConfig;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/22 14:28
 * 功能描述：赠品专区
 */
public class GiveAwayAreaAty extends BaseAty {


    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    //    @ViewInject(R.id.giveawayArea_horizontal_rv)
    //    public RecyclerView giveawayArea_horizontal_rv;
    @ViewInject(R.id.giveaway_refreshLayout)
    private SuperSwipeRefreshLayout mSuperSwipeRefreshLayout;
    @ViewInject(R.id.giveaway_recyclerView)
    private RecyclerView mRecyclerView;
    private int p = 1; // 请求的分页
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    //    private GiveAwayAdapter mGiveAwayAdapter;
    //    private OpenShopGoodsAdapter mOpenShopGoodsAdapter;

    private MyAdapter mMyAdapter;

    private ArrayList<Map<String, String>> mGift_goods_list;
    private List<OpenShopGoods> mOpenShop_goods_list; // 开店商品列表
    private String shop_id; // 店铺id

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_giveaway_area;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("赠品专区");
        mSuperSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        mSuperSwipeRefreshLayout.setFooterView(createFooterView());
        mSuperSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        mSuperSwipeRefreshLayout.setTargetScrollWithLayout(true);
        mGift_goods_list = new ArrayList<>();
        mOpenShop_goods_list = new ArrayList<>();


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);


        //        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        //            @Override
        //            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //                super.getItemOffsets(outRect, view, parent, state);
        //                if (parent.getChildLayoutPosition(view) % 2 == 0) {
        //                    outRect.set(0, 0, 10, 0);
        //                } else {
        //                    outRect.set(10, 0, 0, 0);
        //                }
        //            }
        //        });
        //        mRecyclerView.setLayoutManager(gridLayoutManager);
        //        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        //            @Override
        //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //                super.onScrollStateChanged(recyclerView, newState);
        //                if (!mRecyclerView.canScrollVertically(-1)){
        //                    mSuperSwipeRefreshLayout.setEnabled(false);
        //                    mSuperSwipeRefreshLayout.setRefreshing(false);
        //                }
        //                if (!mRecyclerView.canScrollVertically(1)){
        //                    mSuperSwipeRefreshLayout.setRefreshing(false);
        //                }
        //            }
        //
        //            @Override
        //            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //                super.onScrolled(recyclerView, dx, dy);
        //                Log.e("TAG", "onScrolled: "+dy);
        //            }
        //        });


        mSuperSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                requestData();
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
        mSuperSwipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                requestData();
            }

            @Override
            public void onPushDistance(int distance) {
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
    protected void requestData() {
        GiveAwayModel.postGiftGoodsIndex(String.valueOf(p), this);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.endsWith("giftGoodsIndex")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> stringMap = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            ArrayList<Map<String, String>> gift_goods_list = JSONUtils.parseKeyAndValueToMapList(stringMap.get("gift_goods_list"));

            // ========================================= 设置开店商品 START =========================================
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject jsonData = jsonObject.getJSONObject("data");
                shop_id = jsonData.getString("shop_id"); // 获取店铺id，在列表项点击的时候拼接字段用 http://test3.wujiemall.com/Distribution/DistributionShop/shop/g_id/672/shop_id/0
                final JSONArray jsonDistributionList = jsonData.getJSONArray("distribution_list");

                Gson gson = new Gson();
                for (int i = 0; i < jsonDistributionList.length(); i++) {
                    OpenShopGoods openShopGoods = gson.fromJson(jsonDistributionList.getString(i), OpenShopGoods.class);
                    mOpenShop_goods_list.add(openShopGoods);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // ========================================= 设置开店商品 END =========================================

            if (p == 1) {
                mGift_goods_list.clear();
            }
            mGift_goods_list.addAll(gift_goods_list);
            if (mMyAdapter==null){
                mMyAdapter = new MyAdapter(mGift_goods_list,mOpenShop_goods_list,shop_id);
                mRecyclerView.setAdapter(mMyAdapter);
            }else {
                mMyAdapter.notifyDataSetChanged();
            }

        }
        refreshVisibleState();
    }

    private void refreshVisibleState() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            mSuperSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility() == View.VISIBLE) {
            mSuperSwipeRefreshLayout.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 创建底部加载布局
     *
     * @return
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(mSuperSwipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    /**
     * 创建头部加载布局
     *
     * @return
     */
    private View createHeaderView() {
        View headerView = LayoutInflater.from(mSuperSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private static class MyAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private static final int TYPE_ZERO = 0;
        private static final int TYPE_FIRST = 1;
        private static final int TYPE_SECOND = 2;
        private static final int TYPE_THREE = 3;
        private GiveAwayAdapter mGiveAwayAdapter;
        private OpenShopGoodsAdapter mOpenShopGoodsAdapter;
        private ArrayList<Map<String, String>> mGift_goods_list;
        private List<OpenShopGoods> mOpenShop_goods_list;
        private String shop_id;

        public MyAdapter(ArrayList<Map<String, String>> gift_goods_list, List<OpenShopGoods> openShop_goods_list, String shop_id) {
            mGift_goods_list = gift_goods_list;
            mOpenShop_goods_list = openShop_goods_list;
            this.shop_id = shop_id;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            if (viewType == TYPE_ZERO) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.give_away_item1, parent, false);
                MyViewHolder holder = new MyViewHolder(view);
                return holder;
            } else if (viewType == TYPE_FIRST) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.give_away_item2, parent, false);
                FirstViewHolder holder = new FirstViewHolder(view);
                ViewUtils.inject(holder, view);
                return holder;
            } else if (viewType == TYPE_SECOND) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.give_away_item3, parent, false);
                MyViewHolder holder = new MyViewHolder(view);
                return holder;
            } else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.give_away_item4, parent, false);
                ThreeViewHolder holder = new ThreeViewHolder(view);
                ViewUtils.inject(holder, view);
                return holder;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof FirstViewHolder) {
                FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                firstViewHolder.kaidian_rv.setLayoutManager(manager); // 给RecyClerView 添加设置好的布局样式

                if (mOpenShopGoodsAdapter == null) { // 设置Adapter
                    mOpenShopGoodsAdapter = new OpenShopGoodsAdapter(mOpenShop_goods_list);
                    firstViewHolder.kaidian_rv.setAdapter(mOpenShopGoodsAdapter);
                } else {
                    mOpenShopGoodsAdapter.notifyDataSetChanged(); // 刷新Adapter
                }
                mOpenShopGoodsAdapter.setOnItemClickListener(new OpenShopGoodsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        String goods_id = mOpenShop_goods_list.get(position).getGoods_id();
                        // 拼接字符串跳转至wap界面 ==> http://test3.wujiemall.com/Distribution/DistributionShop/shop/g_id/672/shop_id/0
                        String urlStr = Config.SHARE_URL + "Distribution/DistributionShop/shop/g_id/" + goods_id + "/shop_id/" + shop_id;
                        Bundle bundle = new Bundle();
                        bundle.putString("url", urlStr);
                        Intent intent = new Intent();
                        intent.setClass(mContext, WebViewAty.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
            }
            if (holder instanceof ThreeViewHolder) {
                ThreeViewHolder threeViewHolder = (ThreeViewHolder) holder;
                final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
                threeViewHolder.zengpin_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        if (parent.getChildLayoutPosition(view) % 2 == 0) {
                            outRect.set(0, 0, 10, 0);
                        } else {
                            outRect.set(10, 0, 0, 0);
                        }
                    }
                });
                threeViewHolder.zengpin_rv.setLayoutManager(gridLayoutManager);
                if (null == mGiveAwayAdapter) {
                    mGiveAwayAdapter = new GiveAwayAdapter(mGift_goods_list);
                    threeViewHolder.zengpin_rv.setAdapter(mGiveAwayAdapter);
                } else {
                    mGiveAwayAdapter.notifyDataSetChanged();
                }

                mGiveAwayAdapter.setOnItemClickListener(new GiveAwayAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Map<String, String> map = mGift_goods_list.get(position);
                        String gift_goods_id = map.containsKey("gift_goods_id") ? map.get("gift_goods_id") : "";
                        if (!gift_goods_id.isEmpty()) {
                            Bundle bundle = new Bundle();
                            bundle.putString("gift_goods_id", gift_goods_id);
                            bundle.putInt("type", WJConfig.ZPZQ);
                            Intent intent = new Intent();
                            intent.setClass(mContext, GiveAwayDetailsAty.class);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }

                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_ZERO;
            } else if (position == 1) {
                return TYPE_FIRST;
            } else if (position == 2) {
                return TYPE_SECOND;
            } else {
                return TYPE_THREE;
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }

        class FirstViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.kaidian_rv)
            private RecyclerView kaidian_rv;

            public FirstViewHolder(View itemView) {
                super(itemView);
            }
        }

        class ThreeViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.zengpin_rv)
            private RecyclerView zengpin_rv;

            public ThreeViewHolder(View itemView) {
                super(itemView);
            }
        }

    }


    private static class GiveAwayAdapter extends RecyclerView.Adapter<GiveAwayAdapter.MyViewHolder> {

        private Context mContext;
        private ArrayList<Map<String, String>> gift_goods_list;
        private OnItemClickListener mOnItemClickListener;

        public GiveAwayAdapter(ArrayList<Map<String, String>> gift_goods_list) {
            this.gift_goods_list = gift_goods_list;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.giveaway_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            /**
             * "goods_id": "666",
             "product_id": "1065",
             "goods_name": "滑板谢",
             "goods_img": "http://img.wujiemall.com/Uploads/Goods/2018-08-09/5b6c0341475a9.jpg",
             "country_id": "47",
             "country_logo": "http://img.wujiemall.com/Uploads/Country/2017-11-29/5a1e84bd339ca.png",
             "gift_goods_id": "21",
             "use_voucher": "399.00",
             "exchange_num": "4",
             "shop_price": "399.00"
             */
            final Map<String, String> map = gift_goods_list.get(position);
            String goods_img = map.containsKey("goods_img") ? map.get("goods_img") : "";
            String country_logo = map.containsKey("country_logo") ? map.get("country_logo") : "";
            String goods_name = map.containsKey("goods_name") ? map.get("goods_name") : "";
            String use_voucher = map.containsKey("use_voucher") ? map.get("use_voucher") : "";
            String exchange_num = map.containsKey("exchange_num") ? map.get("exchange_num") : "";
            String shop_price = map.containsKey("shop_price") ? map.get("shop_price") : "";

            if (!goods_img.isEmpty()) {
                Glide.with(mContext).load(goods_img).into(holder.goods_img);
            }
            if (!country_logo.isEmpty()) {
                Glide.with(mContext).load(country_logo).into(holder.goods_icon);
            }
            holder.goods_name_tv.setText(goods_name);
            holder.goods_price_info_tv.setText("原价¥" + shop_price + "|已售" + exchange_num + "件");
            holder.goods_price_tv.setText("赠品券价 ¥" + use_voucher);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onClick(holder.getLayoutPosition());
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return gift_goods_list.size() > 0 ? gift_goods_list.size() : 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.goods_img)
            private ImageView goods_img;
            @ViewInject(R.id.goods_icon)
            private ImageView goods_icon;
            @ViewInject(R.id.goods_name_tv)
            private TextView goods_name_tv;
            @ViewInject(R.id.goods_price_info_tv)
            private TextView goods_price_info_tv;
            @ViewInject(R.id.goods_price_tv)
            private TextView goods_price_tv;

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener {
            void onClick(int position);
        }
    }

    private static class OpenShopGoodsAdapter extends RecyclerView.Adapter<OpenShopGoodsAdapter.MyViewHolder> {
        private Context mContext;
        private List<OpenShopGoods> list;
        private OnItemClickListener mOnItemClickListener;

        public OpenShopGoodsAdapter(List<OpenShopGoods> list) {
            this.list = list;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_gave_away_open_shop, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            final OpenShopGoods openShopGoods = list.get(position);

            Glide.with(mContext).load(openShopGoods.getGoods_img()).into(holder.itemGaveAwayOpenShop_goodsLogo_imgv);
            holder.itemGaveAwayOpenShop_name_tv.setText(openShopGoods.getGoods_name());
            holder.itemGaveAwayOpenShop_dengji_imgv.setImageResource(openShopGoods.getActive_type().equals("0") ? R.drawable.fx_icon_chuji : openShopGoods.getActive_type().equals("1") ? R.drawable.fx_icon_zhongji : R.drawable.fx_icon_gaoji);
            holder.itemGaveAwayOpenShop_gaveGiftCoupon_tv.setText("送赠品券" + openShopGoods.getGoods_gift());
            holder.itemGaveAwayOpenShop_money_tv.setText(openShopGoods.getShop_price());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onClick(holder.getLayoutPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size() > 0 ? list.size() : 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.itemGaveAwayOpenShop_llayout)
            public LinearLayout itemGaveAwayOpenShop_llayout;
            @ViewInject(R.id.itemGaveAwayOpenShop_goodsLogo_imgv)
            public ImageView itemGaveAwayOpenShop_goodsLogo_imgv;
            @ViewInject(R.id.itemGaveAwayOpenShop_dengji_imgv)
            public ImageView itemGaveAwayOpenShop_dengji_imgv;
            @ViewInject(R.id.itemGaveAwayOpenShop_name_tv)
            public TextView itemGaveAwayOpenShop_name_tv;
            @ViewInject(R.id.itemGaveAwayOpenShop_gaveGiftCoupon_tv)
            public TextView itemGaveAwayOpenShop_gaveGiftCoupon_tv;
            @ViewInject(R.id.itemGaveAwayOpenShop_renminbi_tv)
            public TextView itemGaveAwayOpenShop_renminbi_tv;
            @ViewInject(R.id.itemGaveAwayOpenShop_money_tv)
            public TextView itemGaveAwayOpenShop_money_tv;

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener {
            void onClick(int position);
        }
    }

}
