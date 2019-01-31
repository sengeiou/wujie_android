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
import com.txd.hzj.wjlp.catchDoll.bean.PlayerPeopleBean;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：房间玩家列表适配器
 */
public class PlayerPeopleAdapter extends RecyclerView.Adapter<PlayerPeopleAdapter.ViewHolder> {

    Context context;
    List<PlayerPeopleBean> list;

    public PlayerPeopleAdapter(Context context, List<PlayerPeopleBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayerPeopleBean playerPeopleBean = list.get(position);

        GlideUtils.urlCirclePicNoBg(playerPeopleBean.getHeaderUrl(), 46, 46, holder.header_imgv);
        holder.name_tv.setText(playerPeopleBean.getName());
        holder.time_tv.setText(Util.millis2String(playerPeopleBean.getTime(), "yyyy-MM-dd HH:mm"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView header_imgv;
        private TextView name_tv;
        private TextView time_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            header_imgv = itemView.findViewById(R.id.itemPlayerProple_header_imgv);
            name_tv = itemView.findViewById(R.id.itemPlayerProple_name_tv);
            time_tv = itemView.findViewById(R.id.itemPlayerProple_time_tv);
        }
    }

}
