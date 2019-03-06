package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.GrabTheRecordBean;
import com.txd.hzj.wjlp.catchDoll.bean.RoomBean;
import com.txd.hzj.wjlp.catchDoll.ui.activity.GameRoomActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.VideoPlayerActivity;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class GranTheRecordAdapter extends RecyclerView.Adapter<GranTheRecordAdapter.ViewHolder> {

    private List<GrabTheRecordBean> list;
    private Context context;

    public GranTheRecordAdapter(List<GrabTheRecordBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grab_the_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GrabTheRecordBean grabTheRecordBean = list.get(position);

        GlideUtils.urlCirclePicNoBg(grabTheRecordBean.getHeadUrl(), 60, 60, holder.grabTheRecord_header_imgv);
        holder.grabTheRecord_name_tv.setText(grabTheRecordBean.getUserName());
        holder.grabTheRecord_time_tv.setText(Util.millis2String(grabTheRecordBean.getTime(), "yyyy-MM-dd HH:mm"));
        holder.grabTheRecord_content_tv.setText(grabTheRecordBean.getContent());

        holder.grabTheRecord_video_llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                if (grabTheRecordBean.getVideoUrl() != null && !grabTheRecordBean.getVideoUrl().isEmpty()) {
                    intent.putExtra(Constant.VIDEO_VOD_URL_KEY, grabTheRecordBean.getVideoUrl());
                } else {
                    // TODO 走到这就是回传的回播地址为空了，暂时添加了一个网络点播资源《喜欢你》MV
                    intent.putExtra(Constant.VIDEO_VOD_URL_KEY, "http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4");
                    Toast.makeText(context, "回传的回播地址为空，播放预置视频", Toast.LENGTH_SHORT).show();
                }
                context.startActivity(intent);
            }
        });

        holder.grabTheRecord_goInRoom_llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 这边点击的时候直接使用获取数据跳转到游戏房间就行
                RoomBean roomBean = grabTheRecordBean.getRoomBean(); // TODO 这里把进房间的数据都准备好了，可按照首页那么加或者封装一个类
                if (roomBean != null) {
                    Toast.makeText(context, "房间号：" + roomBean.getId() + " MAC地址：" + roomBean.getMac(), Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context, GameRoomActivity.class));
                } else {
                    Toast.makeText(context, "回传房间为null", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView grabTheRecord_header_imgv;
        private TextView grabTheRecord_name_tv;
        private TextView grabTheRecord_time_tv;
        private TextView grabTheRecord_content_tv;
        private LinearLayout grabTheRecord_video_llayout;
        private LinearLayout grabTheRecord_goInRoom_llayout;

        public ViewHolder(View itemView) {
            super(itemView);
            grabTheRecord_header_imgv = itemView.findViewById(R.id.itemGrabTheRecord_header_imgv);
            grabTheRecord_name_tv = itemView.findViewById(R.id.itemGrabTheRecord_name_tv);
            grabTheRecord_time_tv = itemView.findViewById(R.id.itemGrabTheRecord_time_tv);
            grabTheRecord_content_tv = itemView.findViewById(R.id.itemGrabTheRecord_content_tv);
            grabTheRecord_video_llayout = itemView.findViewById(R.id.itemGrabTheRecord_video_llayout);
            grabTheRecord_goInRoom_llayout = itemView.findViewById(R.id.itemGrabTheRecord_goInRoom_llayout);
        }
    }
}
