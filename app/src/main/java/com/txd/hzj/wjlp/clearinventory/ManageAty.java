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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/26 16:10
 * 功能描述：
 */
public class ManageAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clear_inventory_manage;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("寄售管理");
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
        clean_order_list("1", "", this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        final Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("clean_order_list")) {
            final ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (mapArrayList != null) {
                mRecyclerView.setAdapter(new ManageAdapter(mapArrayList));
            }

        }
    }

    public static void clean_order_list(String type, String order_id, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", type);
        if (!TextUtils.isEmpty(order_id)) {
            params.addBodyParameter("order_id", order_id);
        }
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Clean/clean_order_list", params, baseView);
    }

    public static class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ViewHolder> {

        private Context mContext;

        private List<Map<String, String>> mList;

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public ManageAdapter(List<Map<String, String>> list) {
            mList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.clear_inventory_consignment_item, parent, false);
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

            holder.shopNameTv.setText(map.get("merchant_name"));
            holder.orderNumTv.setText(map.get("order_sn"));
            holder.titleTv.setText(map.get("goods_name"));
            holder.numTv.setText("x"+map.get("goods_num"));
            holder.descTv.setText(map.get("goods_attr"));
            holder.salePriceTv.setText("¥"+map.get("wholesale_price"));
            holder.totalTv.setText("共"+map.get("goods_num")+"件商品\u3000合计：￥"+map.get("order_price"));



            holder.itemView.setOnClickListener(new View.OnClickListener() {
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
            @ViewInject(R.id.shopNameTv)
            TextView shopNameTv;
            @ViewInject(R.id.orderNumTv)
            TextView orderNumTv;
            @ViewInject(R.id.titleTv)
            TextView titleTv;
            @ViewInject(R.id.numTv)
            TextView numTv;
            @ViewInject(R.id.descTv)
            TextView descTv;
            @ViewInject(R.id.salePriceTv)
            TextView salePriceTv;
            @ViewInject(R.id.totalTv)
            TextView totalTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }
}
