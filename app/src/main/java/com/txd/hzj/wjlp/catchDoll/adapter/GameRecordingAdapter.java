package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.GameRecordingBean;
import com.txd.hzj.wjlp.catchDoll.ui.activity.GameRecordingInfoActivity;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏记录列表适配器
 */
public class GameRecordingAdapter extends RecyclerView.Adapter<GameRecordingAdapter.ViewHolder> {

    private List<GameRecordingBean> list;
    private Context context;

    public GameRecordingAdapter(List<GameRecordingBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game_recording, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GameRecordingBean gameRecordingBean = list.get(position);

        GlideUtils.urlCirclePicNoBg(gameRecordingBean.getRoom_pic(), 46, 46, holder.header_imgv);
        holder.name_tv.setText(gameRecordingBean.getName());
        holder.time_tv.setText(Util.millis2String(Long.parseLong(gameRecordingBean.getUpdate_time())*1000, "yyyy-MM-dd HH:mm"));
        holder.type_tv.setText((gameRecordingBean.getMode().equals("1") ) ? "抓取成功" : "抓取失败");
        holder.type_tv.setTextColor((gameRecordingBean.getMode().equals("1")) ? Color.parseColor("#F54697") : Color.parseColor("#999999"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameRecordingInfoActivity.class);
                intent.putExtra("GameRecordingAdapter",gameRecordingBean);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView header_imgv;
        TextView name_tv;
        TextView time_tv;
        TextView type_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            header_imgv = itemView.findViewById(R.id.itemGameRecord_header_imgv);
            name_tv = itemView.findViewById(R.id.itemGameRecord_name_tv);
            time_tv = itemView.findViewById(R.id.itemGameRecord_time_tv);
            type_tv = itemView.findViewById(R.id.itemGameRecord_type_tv);
        }
    }
}
