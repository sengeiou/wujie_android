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
import com.txd.hzj.wjlp.catchDoll.bean.GameRoomGoodsBean;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏房间中商品列表适配器
 */
public class GameRoomGoodsAdapter extends RecyclerView.Adapter<GameRoomGoodsAdapter.ViewHolder> {

    private List<GameRoomGoodsBean> list;
    private Context context;

    public GameRoomGoodsAdapter(List<GameRoomGoodsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game_room_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GameRoomGoodsBean gameRoomGoodsBean = list.get(position);

        GlideUtils.loadUrlImg(context, gameRoomGoodsBean.getImgUrl(), holder.goodsImage_imgv);
        holder.name_tv.setText(gameRoomGoodsBean.getName());
        holder.number_tv.setText(new StringBuffer().append("需要抓中 ").append(gameRoomGoodsBean.getNumber()).append(" 次"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodsItemClickListener != null) {
                    onGoodsItemClickListener.goodsItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView goodsImage_imgv;
        private TextView name_tv;
        private TextView number_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsImage_imgv = itemView.findViewById(R.id.itemGameRoomGoods_goodsImage_imgv);
            name_tv = itemView.findViewById(R.id.itemGameRoomGoods_name_tv);
            number_tv = itemView.findViewById(R.id.itemGameRoomGoods_number_tv);
        }
    }

    public static OnGoodsItemClickListener onGoodsItemClickListener;

    public void setOnGoodsItemClickListener(OnGoodsItemClickListener onGoodsItemClickListener) {
        GameRoomGoodsAdapter.onGoodsItemClickListener = onGoodsItemClickListener;
    }

    public interface OnGoodsItemClickListener {
        void goodsItemClick(int position);
    }

}
