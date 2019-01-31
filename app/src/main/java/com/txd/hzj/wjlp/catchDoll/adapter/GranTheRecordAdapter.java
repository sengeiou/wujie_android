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

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.GrabTheRecordBean;
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
        GrabTheRecordBean grabTheRecordBean = list.get(position);

        GlideUtils.urlCirclePicNoBg(grabTheRecordBean.getHeadUrl(), 60, 60, holder.grabTheRecord_header_imgv);
        holder.grabTheRecord_name_tv.setText(grabTheRecordBean.getUserName());
        holder.grabTheRecord_time_tv.setText(Util.millis2String(grabTheRecordBean.getTime(), "yyyy-MM-dd HH:mm"));
        holder.grabTheRecord_content_tv.setText(grabTheRecordBean.getContent());

        holder.grabTheRecord_video_llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                // 网络点播资源《喜欢你》MV
                intent.putExtra(Constant.VIDEO_VOD_URL_KEY, "http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4");
                context.startActivity(intent);
            }
        });

        holder.grabTheRecord_goInRoom_llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GameRoomActivity.class));
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
