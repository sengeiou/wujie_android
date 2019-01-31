package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.RuleNameBean;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class ActivRuleAdapter extends RecyclerView.Adapter<ActivRuleAdapter.ViewHolder> {

    private Context context;
    private List<RuleNameBean> list;

    public ActivRuleAdapter(Context context, List<RuleNameBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activ_rule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RuleNameBean ruleNameBean = list.get(position);
        GlideUtils.urlCirclePicNoBg(ruleNameBean.getHeaderUrl(), 41, 41, holder.itemActivRule_header_imgv);
        holder.itemActivRule_name_tv.setText(ruleNameBean.getName());
        holder.itemActivRule_number_tv.setText(ruleNameBean.getNumber() + "两");
        holder.itemActivRule_time_tv.setText(Util.millis2String(ruleNameBean.getTime(), "yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemActivRule_header_imgv;
        TextView itemActivRule_name_tv;
        TextView itemActivRule_number_tv;
        TextView itemActivRule_time_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            itemActivRule_header_imgv = itemView.findViewById(R.id.itemActivRule_header_imgv);
            itemActivRule_name_tv = itemView.findViewById(R.id.itemActivRule_name_tv);
            itemActivRule_number_tv = itemView.findViewById(R.id.itemActivRule_number_tv);
            itemActivRule_time_tv = itemView.findViewById(R.id.itemActivRule_time_tv);
        }
    }

}
