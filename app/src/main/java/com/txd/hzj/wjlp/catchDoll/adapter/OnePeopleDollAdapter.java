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
import com.txd.hzj.wjlp.catchDoll.bean.OnePeopleDollBean;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：TA的娃娃列表适配器
 */
public class OnePeopleDollAdapter extends RecyclerView.Adapter<OnePeopleDollAdapter.ViewHolder> {

    private Context context;
    private List<OnePeopleDollBean> list;

    public OnePeopleDollAdapter(Context context, List<OnePeopleDollBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_one_people_doll, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnePeopleDollBean onePeopleDollBean = list.get(position);

        GlideUtils.loadUrlImg(context, onePeopleDollBean.getImageUrl(), holder.image_imgv);
        holder.name_tv.setText(onePeopleDollBean.getName());
        holder.time_tv.setText(Util.millis2String(onePeopleDollBean.getTime(), "yyyy.MM.dd HH:mm:ss"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_imgv;
        TextView name_tv;
        TextView time_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            image_imgv = itemView.findViewById(R.id.onePeopleDoll_image_imgv);
            name_tv = itemView.findViewById(R.id.onePeopleDoll_name_tv);
            time_tv = itemView.findViewById(R.id.onePeopleDoll_time_tv);
        }
    }

}
