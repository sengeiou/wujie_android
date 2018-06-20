package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.bean.DistributionGoodsBean;

import java.util.List;

/**
 * 创建者：wjj
 * 创建时间：${DATA} 上午 09:27
 * 功能描述： 分销普通商品管理的Adapter
 */
public class ShopManageOrdinaryAdapter extends BaseAdapter {

    private Context context;
    private List<DistributionGoodsBean> list;
    private CheckBox checkBox; // 调用时候全选框
    private boolean isShowCbox = false; // 是否显示复选框
    private int checkedCount = 0; // 选中记录的数量
    private ImageClick imageClick;

    public ShopManageOrdinaryAdapter(Context context, List<DistributionGoodsBean> list, CheckBox checkBox) {
        this.context = context;
        this.list = list;
        this.checkBox = checkBox;
    }

    public boolean isShowCbox() {
        return isShowCbox;
    }

    public void setShowCbox(boolean showCbox) {
        isShowCbox = showCbox;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopmanage_ordinary_child, null);
            holder.itemShopManageOrd_select_cbox = convertView.findViewById(R.id.itemShopManageOrd_select_cbox);
            holder.itemShopManageOrd_image_imgv = convertView.findViewById(R.id.itemShopManageOrd_image_imgv);
            holder.itemShopManageOrd_title_tv = convertView.findViewById(R.id.itemShopManageOrd_title_tv);
            holder.itemShopManageOrd_daiJinQuan_tv = convertView.findViewById(R.id.itemShopManageOrd_daiJinQuan_tv);
            holder.itemShopManageOrd_meny_tv = convertView.findViewById(R.id.itemShopManageOrd_meny_tv);
            holder.itemShopManageOrd_jifen_tv = convertView.findViewById(R.id.itemShopManageOrd_jifen_tv);
            holder.itemShopManageOrd_share_imgv = convertView.findViewById(R.id.itemShopManageOrd_share_imgv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(list.get(position).getImageUrl()).asBitmap().into(holder.itemShopManageOrd_image_imgv);
        holder.itemShopManageOrd_title_tv.setText(list.get(position).getGoodsName());
        holder.itemShopManageOrd_daiJinQuan_tv.setText(list.get(position).getDaijinquan());
        holder.itemShopManageOrd_meny_tv.setText(list.get(position).getMeny());
        holder.itemShopManageOrd_jifen_tv.setText(list.get(position).getJifen());
        holder.itemShopManageOrd_share_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick.onImageClick(v,position);
                Log.e("TAG", "onClick: "+position );

            }
        });
        holder.itemShopManageOrd_select_cbox.setVisibility(isShowCbox ? View.VISIBLE : View.GONE);
        holder.itemShopManageOrd_select_cbox.setChecked(list.get(position).isChecked());
        holder.itemShopManageOrd_select_cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setChecked(holder.itemShopManageOrd_select_cbox.isChecked());
                if (holder.itemShopManageOrd_select_cbox.isChecked()) {
                    checkedCount++;
                    checkBox.setChecked(checkedCount == list.size());
                } else {
                    checkBox.setChecked(false);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public CheckBox itemShopManageOrd_select_cbox;
        private ImageView itemShopManageOrd_image_imgv;
        private TextView itemShopManageOrd_title_tv;
        private TextView itemShopManageOrd_daiJinQuan_tv;
        private TextView itemShopManageOrd_meny_tv;
        private TextView itemShopManageOrd_jifen_tv;
        private ImageView itemShopManageOrd_share_imgv;
    }

    public void setOnImageClickListener(ImageClick imageClick){
        this.imageClick=imageClick;
    }
    public interface  ImageClick{
        void onImageClick(View view,int position);
    }
}
