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
import com.txd.hzj.wjlp.catchDoll.bean.RoomBean;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：房间列表列表适配器
 */
public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    private List<RoomBean> list;
    private Context context;
    private boolean isEdit = false;

    public RoomListAdapter(List<RoomBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RoomBean roomBean = list.get(position);

        GlideUtils.loadUrlImg(context, roomBean.getPhotoUrl(), holder.itemRoomShow_roomImg_imgv);
        holder.itemRoomShow_roomName_tv.setText(roomBean.getName()); // 房间名称
        holder.itemRoomShow_price_tv.setText(roomBean.getPrice() + "银两/次"); // 价格
        switch (roomBean.getStatus()) {
            case 0: // 空闲
                holder.itemRoomShow_status_tv.setText("有空闲"); // 状态
                holder.itemRoomShow_status_tv.setBackgroundResource(R.drawable.shape_status_idle);
                break;
            case 1: // 热抓中
                holder.itemRoomShow_status_tv.setText("热抓中"); // 状态
                holder.itemRoomShow_status_tv.setBackgroundResource(R.drawable.shape_status_ing);
                break;
        }
        if (isEdit) {
            holder.itemRoomShow_delete_imgv.setVisibility(View.VISIBLE);
        } else {
            holder.itemRoomShow_delete_imgv.setVisibility(View.GONE);
        }
        holder.itemRoomShow_delete_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDeleteClickListener) {
                    onDeleteClickListener.delete(list, position);
                }
            }
        }); // 删除

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRoomItemClickListener != null) {
                    onRoomItemClickListener.onRoomItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemRoomShow_roomImg_imgv; // 房间图片
        private TextView itemRoomShow_roomName_tv; // 房间名称
        private TextView itemRoomShow_price_tv; // 价格
        private TextView itemRoomShow_status_tv; // 状态
        private ImageView itemRoomShow_delete_imgv; // 删除

        public ViewHolder(View itemView) {
            super(itemView);
            itemRoomShow_roomImg_imgv = itemView.findViewById(R.id.itemRoomShow_roomImg_imgv);
            itemRoomShow_roomName_tv = itemView.findViewById(R.id.itemRoomShow_roomName_tv);
            itemRoomShow_price_tv = itemView.findViewById(R.id.itemRoomShow_price_tv);
            itemRoomShow_status_tv = itemView.findViewById(R.id.itemRoomShow_status_tv);
            itemRoomShow_delete_imgv = itemView.findViewById(R.id.itemRoomShow_delete_imgv);
        }
    }

    public OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnDeleteClickListener {
        void delete(List<RoomBean> list, int position);
    }

    public OnRoomItemClickListener onRoomItemClickListener;

    public void setOnRoomItemClickListener(OnRoomItemClickListener onRoomItemClickListener) {
        this.onRoomItemClickListener = onRoomItemClickListener;
    }

    public interface OnRoomItemClickListener {
        void onRoomItemClick(int position);
    }

}
