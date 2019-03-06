package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.LotteryPeopleBean;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class LotteryPeopleAdapter extends RecyclerView.Adapter<LotteryPeopleAdapter.ViewHolder> {

    List<LotteryPeopleBean> list;
    Context context;

    public LotteryPeopleAdapter(List<LotteryPeopleBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grand_prize_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LotteryPeopleBean lotteryPeopleBean = list.get(position);

        GlideUtils.urlCirclePicNoBg(lotteryPeopleBean.getHeaderUrl(), 35, 35, holder.header_imgv);
        holder.content_tv.setText(Html.fromHtml(lotteryPeopleBean.getContent()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView header_imgv; // 头像
        TextView content_tv; // 内容

        public ViewHolder(View itemView) {
            super(itemView);
            header_imgv = itemView.findViewById(R.id.itemGrandPrizePeople_header_imgv);
            content_tv = itemView.findViewById(R.id.itemGrandPrizePeople_content_tv);
        }
    }
}
