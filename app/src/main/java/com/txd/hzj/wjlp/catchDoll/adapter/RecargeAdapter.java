package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.RechargeBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏币界面充值项列表适配器
 */
public class RecargeAdapter extends RecyclerView.Adapter<RecargeAdapter.ViewHolder> {

    private Context context;
    private List<RechargeBean> list;
    private View oldClickView = null;

    public RecargeAdapter(Context context, List<RechargeBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recharge_amount, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        RechargeBean rechargeBean = list.get(position);

        holder.rechargeAmount_numberOfCoins_tv.setText(String.valueOf(rechargeBean.getCoinsNumber()) + "银两");
        if (rechargeBean.isGift()) {
            holder.rechargeAmount_giftNumber_tv.setVisibility(View.VISIBLE);
            holder.rechargeAmount_giftNumber_tv.setText("再送" + String.valueOf(rechargeBean.getGiftNumber()) + "银两");
        } else {
            holder.rechargeAmount_giftNumber_tv.setVisibility(View.INVISIBLE);
        }
        holder.rechargeAmount_price_tv.setText("¥" + new DecimalFormat("#.00").format(rechargeBean.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用接口将选择的对象传出去
                if (onSelectChange != null) {
                    onSelectChange.change(list.get(position));
                }
                // 更改选中状态
                if (oldClickView != null && oldClickView != holder.itemView) {
                    oldClickView.setBackgroundResource(R.drawable.shape_money_item);
                }
                oldClickView = holder.itemView;
                holder.itemView.setBackgroundResource(R.drawable.shape_money_checked_item);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rechargeAmount_numberOfCoins_tv; // 购买数量
        private TextView rechargeAmount_giftNumber_tv; // 赠送数量
        private TextView rechargeAmount_price_tv; // 售价

        public ViewHolder(View itemView) {
            super(itemView);
            rechargeAmount_numberOfCoins_tv = itemView.findViewById(R.id.rechargeAmount_numberOfCoins_tv);
            rechargeAmount_giftNumber_tv = itemView.findViewById(R.id.rechargeAmount_giftNumber_tv);
            rechargeAmount_price_tv = itemView.findViewById(R.id.rechargeAmount_price_tv);
        }
    }

    private OnSelectChange onSelectChange;

    public void setOnSelectChange(OnSelectChange onSelectChange) {
        this.onSelectChange = onSelectChange;
    }

    /**
     * 选则改变接口
     */
    public interface OnSelectChange {
        void change(RechargeBean rechargeBean);
    }


}
