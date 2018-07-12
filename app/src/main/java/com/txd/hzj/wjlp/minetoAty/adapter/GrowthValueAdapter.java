package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.StringUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.listener.ItemClickForRecyclerView;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：10:20
 * 描述：成长值适配器
 */

public class GrowthValueAdapter extends RecyclerView.Adapter<GrowthValueAdapter.GVViewHolder> {

    private Context context;
    private List<Map<String, String>> list;
    /**
     * 0.会员成长
     * 1.会员等级
     */
    private int type;
    private LayoutInflater mInflater;

    private ItemClickForRecyclerView itemClickForRecyclerView;

    public GrowthValueAdapter(Context context, List<Map<String, String>> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        mInflater = LayoutInflater.from(context);
    }

    public void setItemClickForRecyclerView(ItemClickForRecyclerView itemClickForRecyclerView) {
        this.itemClickForRecyclerView = itemClickForRecyclerView;
    }

    @Override
    public GVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_grawth_value_rv, parent, false);
        GVViewHolder gvvh = new GVViewHolder(view);
        ViewUtils.inject(gvvh, view);
        return gvvh;
    }

    @Override
    public void onBindViewHolder(final GVViewHolder holder, int position) {
        Map<String, String> map = list.get(position);

        holder.value_head_imgv.setVisibility(View.VISIBLE);
        Glide.with(context).load(map.get("icon")).into(holder.value_head_imgv);

        // 最后一条隐藏分割线
        if (position == (getItemCount() - 1)) {
            holder.bottom_view.setVisibility(View.GONE);
        } else {
            holder.bottom_view.setVisibility(View.VISIBLE);
        }
        if (0 == type) {
            if (map.get("level_name").equals("注册")) {
                holder.under_title_tv.setText("无");
                holder.value_title_tv.setText("注册");
            } else {
                holder.under_title_tv.setText(map.get("min_points") + "-" + map.get("max_points") + "成长值");
                holder.value_title_tv.setText(map.get("level_name"));
            }
            if (map.get("is_get").equals("1")) {// 已获得
                holder.get_value_tv.setText("已获得");
            } else {// 未获得
                holder.get_value_tv.setText("未获得");
            }
        } else {
            holder.value_title_tv.setText(map.get("rank_name"));
            holder.under_title_tv.setText(map.get("desc"));

            if (map.get("is_get").equals("1")) {// 已获得
                if (StringUtils.nullStrToEmpty(map.get("fee")).equals("")) {
                    holder.get_value_tv.setText("已获得");
                } else {
                    holder.get_value_tv.setText(map.get("fee") + "/年");
                }
            } else {// 未获得
                if (StringUtils.nullStrToEmpty(map.get("fee")).equals("")) {
                    holder.get_value_tv.setText("未获得");
                } else {
                    holder.get_value_tv.setText(map.get("fee") + "/年");
                }
            }
        }

        //设置右边文本框的字体颜色
        if (map.get("is_get").equals("1")) {// 已获得
            holder.get_value_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
        } else {// 未获得
            holder.get_value_tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                if (null != itemClickForRecyclerView) {
                    itemClickForRecyclerView.OnItemClick(view, pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GVViewHolder extends RecyclerView.ViewHolder {

        /**
         * 已获得，未获得(交费)
         */
        @ViewInject(R.id.get_value_tv)
        private TextView get_value_tv;

        /**
         * 图标
         */
        @ViewInject(R.id.value_head_imgv)
        private ImageView value_head_imgv;

        /**
         * 成长值(说明文字)
         */
        @ViewInject(R.id.under_title_tv)
        private TextView under_title_tv;

        /**
         * 标题
         */
        @ViewInject(R.id.value_title_tv)
        private TextView value_title_tv;

        @ViewInject(R.id.bottom_view)
        private View bottom_view;

        public GVViewHolder(View itemView) {
            super(itemView);
        }
    }

}
