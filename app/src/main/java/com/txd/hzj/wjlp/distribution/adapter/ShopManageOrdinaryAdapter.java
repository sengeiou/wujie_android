package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
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
    private List<DistributionGoodsBean.DataBean> list;
    private CheckBox checkBox; // 调用时候全选框
    private boolean isShowCbox = false; // 是否显示复选框
    private int checkedCount = 0; // 选中记录的数量
    private ImageClick imageClick;
    /**
     * Fragment标示
     * 0 为出售中
     * 1 为已下架
     * 2 是已售罄
     */
    private int from;

    public ShopManageOrdinaryAdapter(Context context, List<DistributionGoodsBean.DataBean> list, CheckBox checkBox,int from) {
        this.context = context;
        this.list = list;
        this.checkBox = checkBox;
        this.from=from;
    }


    public void setCheckedCount(){
        this.checkedCount=0;
    }

    public void setShowCbox(boolean showCbox) {
        isShowCbox = showCbox;
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
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
            holder.recommend_img = convertView.findViewById(R.id.recommend_img);
            holder.itemShopManageOrd_title_tv = convertView.findViewById(R.id.itemShopManageOrd_title_tv);
            holder.itemShopManageOrd_daiJinQuan_tv = convertView.findViewById(R.id.itemShopManageOrd_daiJinQuan_tv);
            holder.itemShopManageOrd_meny_tv = convertView.findViewById(R.id.itemShopManageOrd_meny_tv);
            holder.itemShopManageOrd_jifen_tv = convertView.findViewById(R.id.itemShopManageOrd_jifen_tv);
            holder.itemShopManageOrd_share_imgv = convertView.findViewById(R.id.itemShopManageOrd_share_imgv);
            holder.itemShopManageOrd_recommend = convertView.findViewById(R.id.itemShopManageOrd_recommend);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DistributionGoodsBean.DataBean dataBean = list.get(position);
        Glide.with(context).load(dataBean.getGoods_img()).asBitmap().into(holder.itemShopManageOrd_image_imgv);
        if (dataBean.getShop_goods_rec()!=null && dataBean.getShop_goods_rec().equals("1")){
            holder.recommend_img.setVisibility(View.VISIBLE);
        }else {
            holder.recommend_img.setVisibility(View.GONE);
        }
        holder.itemShopManageOrd_title_tv.setText(dataBean.getGoods_name());
        holder.itemShopManageOrd_daiJinQuan_tv.setText("最多可用"+ dataBean.getTicket_buy_discount()+"%代金券");
        holder.itemShopManageOrd_meny_tv.setText(dataBean.getShop_price());
        holder.itemShopManageOrd_jifen_tv.setText(dataBean.getIntegral());
        holder.itemShopManageOrd_share_imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null !=imageClick){
                    imageClick.onImageClick(v,position);
                }
            }
        });
        if (from==0){
            holder.itemShopManageOrd_recommend.setVisibility(View.VISIBLE);
            holder.itemShopManageOrd_recommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null !=imageClick){
                        imageClick.onImageClick(v,position);
                    }
                }
            });
        }else {
            holder.itemShopManageOrd_recommend.setVisibility(View.GONE);
        }
        holder.itemShopManageOrd_select_cbox.setVisibility(isShowCbox ? View.VISIBLE : View.GONE);
        holder.itemShopManageOrd_select_cbox.setChecked(dataBean.isChecked());
        holder.itemShopManageOrd_select_cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBean.setChecked(holder.itemShopManageOrd_select_cbox.isChecked());
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
        private ImageView recommend_img;
        private TextView itemShopManageOrd_title_tv;
        private TextView itemShopManageOrd_daiJinQuan_tv;
        private TextView itemShopManageOrd_meny_tv;
        private TextView itemShopManageOrd_jifen_tv;
        private ImageView itemShopManageOrd_share_imgv;
        private ImageView itemShopManageOrd_recommend;
    }

    public void setOnImageClickListener(ImageClick imageClick){
        this.imageClick=imageClick;
    }
    public interface  ImageClick{
        void onImageClick(View view,int position);
    }
}
