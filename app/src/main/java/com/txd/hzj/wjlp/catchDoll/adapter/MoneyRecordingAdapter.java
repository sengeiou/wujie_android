package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.MoneyRecordingBean;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两变动记录适配器
 */
public class MoneyRecordingAdapter extends RecyclerView.Adapter<MoneyRecordingAdapter.ViewHolder> {

    private Context context;
    private List<MoneyRecordingBean> list;
    private int type; // 1+ 2- 数据加减号

    public MoneyRecordingAdapter(Context context, List<MoneyRecordingBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_money_recording, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        MoneyRecordingBean moneyRecordingBean = list.get(position);

        holder.itemMoneyRecording_content_tv.setText(moneyRecordingBean.getDesc());
        holder.itemMoneyRecording_time_tv.setText(Util.millis2String(moneyRecordingBean.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
        holder.itemMoneyRecording_price_tv.setText(new StringBuffer().append(type == 1 ? "+" : "-").append(moneyRecordingBean.getMoney()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemMoneyRecording_content_tv; // 内容
        private TextView itemMoneyRecording_time_tv; // 时间
        private TextView itemMoneyRecording_price_tv; // 价格

        public ViewHolder(View itemView) {
            super(itemView);
            itemMoneyRecording_content_tv = itemView.findViewById(R.id.itemMoneyRecording_content_tv);
            itemMoneyRecording_time_tv = itemView.findViewById(R.id.itemMoneyRecording_time_tv);
            itemMoneyRecording_price_tv = itemView.findViewById(R.id.itemMoneyRecording_price_tv);
        }
    }

}
