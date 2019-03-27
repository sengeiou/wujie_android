package com.txd.hzj.wjlp.clearinventory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.txd.hzj.wjlp.clearinventory.ConsignmentAty.clean_goods_list;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/26 14:21
 * 功能描述：
 */
public class OrderFragment extends BaseFgt {

    @ViewInject(R.id.superSwipeRefreshLayout)
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;


    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;


    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private String mFrom;
    private String mType;
    private String mClean_order_status;
    private String mOrder_status;

    /**
     * 
     * @param from  
     * @param type 
     * @return
     */
    public static OrderFragment getInstance(String from,String type){
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        bundle.putString("type",type);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fgt_refresh_recyclerview;
    }

    @Override
    protected void initialized() {
        mFrom = getArguments().getString("from");
        mType = getArguments().getString("type");
    }

    protected void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() -1){
                    outRect.bottom = 15;
                }
            }
        });
        superSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
//        superSwipeRefreshLayout.setFooterView(createFooterView());
        superSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        superSwipeRefreshLayout.setTargetScrollWithLayout(true);
        superSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                setData();
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
        superSwipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
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
        init();
        setData();
    }

    private void setData() {
        if ("已交易".equals(mFrom)){
            mClean_order_status = "2";
            if ("待发货".equals(mType)){
                mOrder_status = "1";
            }else if ("待收货".equals(mType)){
                mOrder_status = "2";
            }else if ("已退款".equals(mType)){
                mOrder_status = "3";
            }else if ("已完成".equals(mType)){
                mOrder_status = "4";
            }
        }else if ("已提货".equals(mFrom)){
            mClean_order_status = "3";
            if ("待发货".equals(mType)){
                mOrder_status = "1";
            }else if ("待收货".equals(mType)){
                mOrder_status = "2";
            }else if ("已完成".equals(mType)){
                mOrder_status = "3";
            }
        }else if ("已退款".equals(mFrom)){
            mClean_order_status = "4";
            if ("退款中".equals(mType)){
                mOrder_status = "1";
            }else if ("已完成".equals(mType)){
                mOrder_status = "2";
            }
        }
        if (!TextUtils.isEmpty(mClean_order_status) && !TextUtils.isEmpty(mOrder_status)){
            clean_goods_list(mClean_order_status,mOrder_status,this);
        }

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        refreshVisibleState();
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("clean_goods_list")){
            ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (mapArrayList!= null){
                OrderAdapter orderAdapter = new OrderAdapter(mapArrayList,mFrom);
                mRecyclerView.setAdapter(orderAdapter);
            }

        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        refreshVisibleState();
    }

    @Override
    protected void immersionInit() {

    }

    private void refreshVisibleState() {
        if (progressBar.getVisibility()== View.VISIBLE){
            superSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility()==View.VISIBLE) {
            superSwipeRefreshLayout.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 创建底部加载布局
     *
     * @return
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(superSwipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(superSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }


    public static class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

        private Context mContext;

        private List<Map<String, String>> mList;
        private String from;

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public OrderAdapter(List<Map<String, String>> list, String from) {
            mList = list;
            this.from = from;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.clear_inventory_order_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            if (map.containsKey("goods_img") && !TextUtils.isEmpty(map.get("goods_img"))) {
                Glide.with(mContext).load(map.get("goods_img")).into(holder.picImg);
            }
            if ("已交易".equals(from)){
                holder.priceNameTv.setText("利润:");
            }else if ("已提货".equals(from)){
                holder.priceNameTv.setText("补差价:");
            }else if ("已退款".equals(from)){
                holder.priceNameTv.setText("售价:");
            }
            holder.shopNameTv.setText(map.get("merchant_name"));
            holder.timeTv.setText(map.get("pay_time"));
            holder.titleTv.setText(map.get("goods_name"));
            holder.numTv.setText("x"+map.get("goods_num"));
            holder.descTv.setText(map.get("goods_attr"));
            holder.salePriceTv.setText("¥"+map.get("wholesale_price"));
            holder.priceTv.setText("¥"+map.get("profit"));
            holder.totalTv.setText("共"+map.get("goods_num")+"件商品\u3000合计：￥"+map.get("order_price"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.picImg)
            ImageView picImg;
            @ViewInject(R.id.shopNameTv)
            TextView shopNameTv;
            @ViewInject(R.id.timeTv)
            TextView timeTv;
            @ViewInject(R.id.titleTv)
            TextView titleTv;
            @ViewInject(R.id.numTv)
            TextView numTv;
            @ViewInject(R.id.descTv)
            TextView descTv;
            @ViewInject(R.id.salePriceTv)
            TextView salePriceTv;
            @ViewInject(R.id.priceNameTv)
            TextView priceNameTv;
            @ViewInject(R.id.priceTv)
            TextView priceTv;
            @ViewInject(R.id.totalTv)
            TextView totalTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener{
            void onItemClick(int position);
        }
    }
}
