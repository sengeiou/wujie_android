package com.txd.hzj.wjlp.clearinventory;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.txd.hzj.wjlp.clearinventory.ManageAty.clean_order_list;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/27 17:11
 * 功能描述：
 */
public class ManageGoodsAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.goodsNameTv)
    private TextView goodsNameTv;
    @ViewInject(R.id.orderTv)
    private TextView orderTv;
    @ViewInject(R.id.numTv1)
    private TextView numTv1;
    @ViewInject(R.id.numTv2)
    private TextView numTv2;
    @ViewInject(R.id.numTv3)
    private TextView numTv3;
    @ViewInject(R.id.numTv4)
    private TextView numTv4;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private String mOrder_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_manage_goods;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("寄售商品");
        mOrder_id = getIntent().getStringExtra("order_id");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = 15;
                }
            }
        });
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mOrder_id)) {
            clean_order_list("2", mOrder_id, this);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        final Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("clean_order_list")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            Map<String, String> orderInfo = JSONUtils.parseKeyAndValueToMap(data.get("order_info"));
            goodsNameTv.setText(orderInfo.get("goods_name"));
            orderTv.setText(orderInfo.get("order_sn"));
            Map<String, String> param = JSONUtils.parseKeyAndValueToMap(orderInfo.get("param"));
            numTv1.setText(param.get("max_num"));
            numTv2.setText(param.get("already_num"));
            numTv3.setText(param.get("surplus_num"));
            numTv4.setText(param.get("cai_num"));
            ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(data.get("goods_list"));
            if (mapArrayList != null) {
                ManageGoodsAdapter manageGoodsAdapter = new ManageGoodsAdapter(mapArrayList);
                mRecyclerView.setAdapter(manageGoodsAdapter);
                manageGoodsAdapter.setOnItemClickListener(new ManageGoodsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                    }
                });
            }

        }
    }

    public static class ManageGoodsAdapter extends RecyclerView.Adapter<ManageGoodsAdapter.ViewHolder> {

        private Context mContext;

        private List<Map<String, String>> mList;

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public ManageGoodsAdapter(List<Map<String, String>> list) {
            mList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.clear_inventory_goods_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            if (map.containsKey("abs_url") && !TextUtils.isEmpty(map.get("abs_url"))) {
                Glide.with(mContext).load(map.get("abs_url")).into(holder.picImg);
            }

            holder.titleTv.setText(map.get("goods_name"));
            holder.descTv.setText("最多可使用"+map.get("discount")+"%红券");
            holder.salePriceTv.setText("¥" + map.get("wholesale_price"));
            holder.profitTv.setText("¥" + map.get("profit"));

            Map<String, String> param = JSONUtils.parseKeyAndValueToMap(map.get("param"));
            holder.numTv1.setText("可寄售数量：" + param.get("consign_sale_num"));
            holder.numTv2.setText("已寄售数量：" + param.get("deal_num"));
//            holder.numTv3.setText("剩余件数：" + param.get("surplus_num"));
            holder.numTv4.setText("可在线寄售数量：" + param.get("same_sale_num"));
            holder.numTv5.setText("已在线寄售数量：" + param.get("already_num"));

            holder.saleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
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
            @ViewInject(R.id.titleTv)
            TextView titleTv;
            @ViewInject(R.id.descTv)
            TextView descTv;
            @ViewInject(R.id.saleTv)
            TextView saleTv;
            @ViewInject(R.id.salePriceTv)
            TextView salePriceTv;
            @ViewInject(R.id.profitTv)
            TextView profitTv;
            @ViewInject(R.id.numTv1)
            TextView numTv1;
            @ViewInject(R.id.numTv2)
            TextView numTv2;
            @ViewInject(R.id.numTv3)
            TextView numTv3;
            @ViewInject(R.id.numTv4)
            TextView numTv4;
            @ViewInject(R.id.numTv5)
            TextView numTv5;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }
}
