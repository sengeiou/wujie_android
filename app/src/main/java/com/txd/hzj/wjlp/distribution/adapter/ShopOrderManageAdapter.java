package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.bean.ShopOrderBean;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/15 0015 9:22
 * 功能描述：分销订单管理adapter
 * 联系方式：无
 */
public class ShopOrderManageAdapter extends RecyclerView.Adapter {
    private List<ShopOrderBean.DataBean> list;
    private Context context;
    private ChangeTextViewStyle instance;
    private String type;

    public ShopOrderManageAdapter(List<ShopOrderBean.DataBean> list, Context context, String type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_order_relist_item, parent,false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        instance = ChangeTextViewStyle.getInstance();
        MyViewHolder holders = (MyViewHolder) holder;

        instance.forTextColor(context, holders.buyer, "买家：你的长安城", 3, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.supplier, "供货商：123456", 4, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.priFit, "收益：156.00积分", 3, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.vouvherType, "用卷类型：红券", 5, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.proportion, "用卷比例：50%", 5, Color.parseColor("#777777"));

        ShopOrderBean.DataBean dataBean = list.get(position);
        holders.shop_name_tv.setText(dataBean.getShop_name());
        List<ShopOrderBean.DataBean.OrderGoodsBean> order_goods = dataBean.getOrder_goods();

        holders.mListView.setAdapter(new MyAdapter(order_goods,context));


        if ("全部".equals(type)) {
            holders.orderType.setText("全部");
        } else if ("待付款".equals(type)) {
            holders.orderType.setText("待付款");
        } else if ("待发货".equals(type)) {
            holders.orderType.setText("待发货");
        } else if ("待收货".equals(type)) {
            holders.orderType.setText("待收货");
        } else if ("已完成".equals(type)) {
            holders.orderType.setText("已完成");
        }
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView buyer, supplier, priFit, vouvherType, proportion, orderType ,shop_name_tv;

        private ListView mListView;


        public MyViewHolder(View itemView) {
            super(itemView);

            shop_name_tv=itemView.findViewById(R.id.shop_name_tv);
            //买家
            buyer = itemView.findViewById(R.id.shop_order_item_buyer);
            //供货商
            supplier = itemView.findViewById(R.id.shop_order_item_supplier);
            //收益
            priFit = itemView.findViewById(R.id.shop_order_item_profit);
            //用卷类型
            vouvherType = itemView.findViewById(R.id.shop_order_voucher_type);
            //用卷比例
            proportion = itemView.findViewById(R.id.shop_order_item_voucher_proportion);
            //订单状态
            orderType = itemView.findViewById(R.id.shop_order_type);

            mListView=itemView.findViewById(R.id.listview);

        }
    }


    private class MyAdapter extends BaseAdapter{
        private  List<ShopOrderBean.DataBean.OrderGoodsBean> mGoodsBeanList;
        private Context mContext;
        private  LayoutInflater mInflater;

        public MyAdapter(List<ShopOrderBean.DataBean.OrderGoodsBean> goodsBeanList,Context context) {
            mGoodsBeanList = goodsBeanList;
            mContext=context;
            mInflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mGoodsBeanList!=null && mGoodsBeanList.size()>0?mGoodsBeanList.size():0;
        }

        @Override
        public Object getItem(int position) {
            return mGoodsBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view==null){
                holder=new ViewHolder();
                view=mInflater.inflate(R.layout.shop_order_relist_item2,parent,false);
                holder.head_img=view.findViewById(R.id.shop_order_ima);
                holder.title_tv=view.findViewById(R.id.shop_order_content);
                holder.price_tv=view.findViewById(R.id.price_tv);
                holder.nums_tv=view.findViewById(R.id.nums_tv);
                view.setTag(holder);
            }else {
                holder= (ViewHolder) view.getTag();
            }
            ShopOrderBean.DataBean.OrderGoodsBean orderGoodsBean = mGoodsBeanList.get(position);
            Glide.with(mContext).load(orderGoodsBean.getPic()).into(holder.head_img);
            holder.title_tv.setText(orderGoodsBean.getGoods_name());
            holder.price_tv.setText("￥"+orderGoodsBean.getShop_price());
            holder.nums_tv.setText("x"+orderGoodsBean.getGoods_num());
            return view;
        }

        class ViewHolder{
            private ImageView head_img;
            private TextView  title_tv;
            private TextView  price_tv;
            private TextView nums_tv;
        }
    }
}
