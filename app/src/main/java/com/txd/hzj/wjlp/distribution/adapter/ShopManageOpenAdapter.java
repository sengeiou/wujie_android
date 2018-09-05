package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.bean.DistributionGoodsBean;

import java.util.List;

/**
 * 创建者：wjj
 * 创建时间：${DATA} 上午 09:27
 * 功能描述： 分销开店商品管理的Adapter
 */
public class ShopManageOpenAdapter extends BaseAdapter {

    private Context context;
    private List<DistributionGoodsBean.DataBean> list;
    private ImageClick imageClick;

    public ShopManageOpenAdapter(Context context, List<DistributionGoodsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopmanage_openshop, null);
            holder.itemShopManageOpen_image_imgv = convertView.findViewById(R.id.itemShopManageOpen_image_imgv);
            holder.itemShopManageOpen_name_tv = convertView.findViewById(R.id.itemShopManageOpen_name_tv);
            holder.itemShopManageOpen_meny_tv = convertView.findViewById(R.id.itemShopManageOpen_meny_tv);
            holder.itemShopManageOpen_share_imgv = convertView.findViewById(R.id.itemShopManageOpen_share_imgv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(list.get(position).getGoods_img()).asBitmap().into(holder.itemShopManageOpen_image_imgv);
        holder.itemShopManageOpen_name_tv.setText(list.get(position).getGoods_name());
        holder.itemShopManageOpen_meny_tv.setText(list.get(position).getShop_price());
        holder.itemShopManageOpen_share_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick.onImageClick(v,position);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        private ImageView itemShopManageOpen_image_imgv;
        private TextView itemShopManageOpen_name_tv;
        private TextView itemShopManageOpen_meny_tv;
        private ImageView itemShopManageOpen_share_imgv;
    }
    public void setOnImageClickListener(ImageClick imageClick){
        this.imageClick=imageClick;
    }
    public interface  ImageClick{
        void onImageClick(View view,int position);
    }
}
