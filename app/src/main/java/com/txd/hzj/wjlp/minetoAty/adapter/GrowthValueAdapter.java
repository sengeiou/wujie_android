package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.listener.ItemClickForRecyclerView;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：10:20
 * 描述：成长值适配器
 * ===============Txunda===============
 */

public class GrowthValueAdapter extends RecyclerView.Adapter<GrowthValueAdapter.GVViewHolder> {

    private Context context;
    private List<String> list;
    /**
     * 0.会员成长
     * 1.会员等级
     */
    private int type;
    private LayoutInflater mInflater;

    private ItemClickForRecyclerView itemClickForRecyclerView;

    public GrowthValueAdapter(Context context, List<String> list, int type) {
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
        if (0 == position) {
            holder.get_value_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
        } else {
            holder.get_value_tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        // 最后一条隐藏分割线
        if (position == (getItemCount() - 1)) {
            holder.bottom_view.setVisibility(View.GONE);
        } else {
            holder.bottom_view.setVisibility(View.VISIBLE);
        }
        if (0 == type) {
            switch (position) {
                case 0:
                    holder.under_title_tv.setText("无");
                    holder.value_title_tv.setText("注册");
                    holder.get_value_tv.setText("已获得");
                    break;
                case 1:
                    holder.under_title_tv.setText("0-1999成长值");
                    holder.value_title_tv.setText("铜牌会员");
                    holder.get_value_tv.setText("未获得");
                    break;
                case 2:
                    holder.under_title_tv.setText("2000-9999成长值");
                    holder.value_title_tv.setText("银牌会员");
                    holder.get_value_tv.setText("未获得");
                    break;
                case 3:
                    holder.under_title_tv.setText("10000-29999成长值");
                    holder.value_title_tv.setText("金牌会员");
                    holder.get_value_tv.setText("未获得");
                    break;
                case 4:
                    holder.under_title_tv.setText("30000以上成长值");
                    holder.value_title_tv.setText("钻石会员");
                    holder.get_value_tv.setText("未获得");
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    holder.value_title_tv.setText("普通会员");
                    holder.under_title_tv.setText("享受购物送爱心，利润分红权益");
                    holder.get_value_tv.setText("已获得");
                    break;
                case 1:
                    holder.value_title_tv.setText("铜牌会员");
                    holder.under_title_tv.setText("消费99元，享受一度推荐奖金权益");
                    holder.get_value_tv.setText("未获得");
                    break;
                case 2:
                    holder.value_title_tv.setText("银牌会员");
                    holder.under_title_tv.setText("享受二度推荐奖金权益，并获得价值365元商品礼包");
                    ChangeTextViewStyle.getInstance().forMemberGrade(context, holder.get_value_tv, "365元/年", 3);
                    break;
                case 3:
                    holder.value_title_tv.setText("金牌会员");
                    holder.under_title_tv.setText("享受三度推荐奖金权益，并获得价值1980元商品礼包");
                    ChangeTextViewStyle.getInstance().forMemberGrade(context, holder.get_value_tv, "1980元/年", 4);
                    break;
            }
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
        if (0 == type) {
            return 5;
        }
        return 4;
    }

    class GVViewHolder extends RecyclerView.ViewHolder {

        /**
         * 已获得，未获得(交费)
         */
        @ViewInject(R.id.get_value_tv)
        private TextView get_value_tv;

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
