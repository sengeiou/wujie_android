package com.txd.hzj.wjlp.mellonLine.gridClassify.giveawayarea;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/22 14:28
 * 功能描述：赠品专区
 */
public class GiveAwayAreaAty extends BaseAty {


    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
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

    private GiveAwayAdapter mGiveAwayAdapter;

    private ArrayList<Map<String, String>> mGift_goods_list;


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
        mGift_goods_list=new ArrayList<>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        mRecyclerView.setLayoutManager(gridLayoutManager);


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
        postGiftGoodsIndex(String.valueOf(p),this);
    }

    public void postGiftGoodsIndex(String p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", p);
        apiTool2.postApis(Config.GIVEAWAYAREA_URL + "giftGoodsIndex", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.endsWith("giftGoodsIndex")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> stringMap = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            ArrayList<Map<String, String>> gift_goods_list = JSONUtils.parseKeyAndValueToMapList(stringMap.get("gift_goods_list"));
            if (p==1){
               mGift_goods_list.clear();
            }
            mGift_goods_list.addAll(gift_goods_list);
            mGiveAwayAdapter = new GiveAwayAdapter(mGift_goods_list);
            mRecyclerView.setAdapter(mGiveAwayAdapter);
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


    private static class GiveAwayAdapter extends RecyclerView.Adapter<GiveAwayAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Map<String, String>> gift_goods_list;

        public GiveAwayAdapter(ArrayList<Map<String, String>> gift_goods_list) {
            this.gift_goods_list = gift_goods_list;
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
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
            Map<String, String> map=gift_goods_list.get(position);
            String goods_img = map.containsKey("goods_img")?map.get("goods_img"):"";
            String country_logo = map.containsKey("country_logo")?map.get("country_logo"):"";
            String goods_name = map.containsKey("goods_name")?map.get("goods_name"):"";
            String gift_goods_id = map.containsKey("gift_goods_id")?map.get("gift_goods_id"):"";
            String use_voucher = map.containsKey("use_voucher")?map.get("use_voucher"):"";
            String exchange_num = map.containsKey("exchange_num")?map.get("exchange_num"):"";
            String shop_price = map.containsKey("shop_price")?map.get("shop_price"):"";

            if (!goods_img.isEmpty()){
                Glide.with(mContext).load(goods_img).into(holder.goods_img);
            }
            if (!country_logo.isEmpty()){
                Glide.with(mContext).load(country_logo).into(holder.goods_icon);
            }

            holder.goods_name_tv.setText(goods_name);
            holder.goods_price_info_tv.setText("原价￥"+shop_price+"|已售"+exchange_num+"件");
            holder.goods_price_tv.setText("赠品券价 ￥"+use_voucher);


        }

        @Override
        public int getItemCount() {
            return gift_goods_list.size()>0?gift_goods_list.size():0;
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
    }
}
