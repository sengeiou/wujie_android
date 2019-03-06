package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.GameRoomHeaderBean;
import com.txd.hzj.wjlp.catchDoll.ui.activity.OnePeoplesDollActivity;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏房间中抓中记录适配器
 */
public class GameRoomHeaderAdapter extends RecyclerView.Adapter<GameRoomHeaderAdapter.ViewHolder> {

    private List<GameRoomHeaderBean> list;
    private Context context;

    public GameRoomHeaderAdapter(List<GameRoomHeaderBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game_room_recently_arrested, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameRoomHeaderBean gameRoomHeaderBean = list.get(position);

        GlideUtils.urlCirclePicNoBg(gameRoomHeaderBean.getHeaderUrl(), 10, 10, holder.header_imgv);
        holder.number_tv.setText(new StringBuffer().append("抓中 ").append(gameRoomHeaderBean.getNumber()).append(" 次"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转界面
                context.startActivity(new Intent(context, OnePeoplesDollActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView header_imgv;
        private TextView number_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            header_imgv = itemView.findViewById(R.id.itemGameRoomRecAeer_header_imgv);
            number_tv = itemView.findViewById(R.id.itemGameRoomRecAeer_number_tv);
        }
    }

}
