package com.txd.hzj.wjlp.melloffLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.ShopOffLineBean;
import com.txd.hzj.wjlp.view.RatingBar;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：Qyl
 * 创建时间：2018/7/24 0024 8:57
 * 功能描述：评价列表适配器
 * 联系方式：无
 */
public class ShopEvaluateAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ShopOffLineBean.DataBean.CommentBean.ListBean> list;

    public ShopEvaluateAdapter(Context context, List<ShopOffLineBean.DataBean.CommentBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolde myViewHolde = new MyViewHolde(LayoutInflater.from(context).inflate(R.layout.aty_down_shopmall_evaluate_item, parent, false));
        return myViewHolde;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolde viewHolder = (MyViewHolde) holder;
        ShopOffLineBean.DataBean.CommentBean.ListBean listBean = list.get(position);
        L.e("listBean:" + listBean.toString());
        Glide.with(context).load(listBean.getHead_pic()).into(viewHolder.imaHead);
        viewHolder.shopEvaluateName.setText(listBean.getNickname());
        viewHolder.shopEvaluateTime.setText(listBean.getStart_time());
        viewHolder.shop_evaluate_star_level.setStar(Integer.valueOf(listBean.getStar() != null ? listBean.getStar() : "0"));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolde extends RecyclerView.ViewHolder {

        private ShapedImageView imaHead;
        private TextView shopEvaluateName, shopEvaluateTime;
        private RatingBar shop_evaluate_star_level;

        public MyViewHolde(View itemView) {
            super(itemView);
            //图片
            imaHead = itemView.findViewById(R.id.shopmall_item_img_head);
            //评价用户名字
            shopEvaluateName = itemView.findViewById(R.id.shop_evaluate_name);
            //评价时间
            shopEvaluateTime = itemView.findViewById(R.id.shop_evaluate_time);
            //评价等级
            shop_evaluate_star_level = itemView.findViewById(R.id.shop_evaluate_star_level);

        }
    }
}
