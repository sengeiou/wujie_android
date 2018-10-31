package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
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
    private OnButtonClickListener mButtonClickListener;

    public interface OnButtonClickListener {
        void buttonClick(int num, String price, String ticket_status);
    }

    public void setButtonClickListener(OnButtonClickListener buttonClickListener) {
        mButtonClickListener = buttonClickListener;
    }

    public ShopOrderManageAdapter(List<ShopOrderBean.DataBean> list, Context context, String type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_order_relist_item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        instance = ChangeTextViewStyle.getInstance();
        final MyViewHolder holders = (MyViewHolder) holder;

        ShopOrderBean.DataBean dataBean = list.get(position);
        holders.shop_name_tv.setText(dataBean.getShop_name());
        instance.forTextColor(context, holders.buyer, "买家：" + dataBean.getBuyer(), 3, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.supplier, "供货商：" + dataBean.getSupply_name(), 4, Color.parseColor("#777777"));
        if (TextUtils.isEmpty(dataBean.getProfit_num()) || "0".equals(dataBean.getProfit_num())) {
            holders.priFit.setVisibility(View.GONE);
        } else {
            holders.priFit.setVisibility(View.VISIBLE);
            instance.forTextColor(context, holders.priFit, "收益：" + dataBean.getProfit_num() + "积分", 3, Color.parseColor("#777777"));
        }

        String ticket_color = dataBean.getTicket_color();
        if (TextUtils.isEmpty(ticket_color) || "0".equals(ticket_color)) {
            holders.vouvherType.setVisibility(View.GONE);
            holders.proportion.setVisibility(View.GONE);
        } else {
            holders.vouvherType.setVisibility(View.VISIBLE);
            holders.proportion.setVisibility(View.VISIBLE);
            String type = "";
            if ("1".equals(ticket_color)) {
                type = "红券";
            } else if ("2".equals(ticket_color)) {
                type = "黄券";
            } else if ("3".equals(ticket_color)) {
                type = "蓝券";
            }
            instance.forTextColor(context, holders.vouvherType, "用券类型：" + type, 5, Color.parseColor("#777777"));
        }

        instance.forTextColor(context, holders.proportion, "用券金额："+dataBean.getPay_tickets(), 5, Color.parseColor("#777777"));

        List<ShopOrderBean.DataBean.OrderGoodsBean> order_goods = dataBean.getOrder_goods();

        holders.mListView.setAdapter(new MyAdapter(order_goods, context));
        holders.time_tv.setVisibility(View.VISIBLE);
        holders.time_tv.setText(dataBean.getOrder_time());

        if (!TextUtils.isEmpty(dataBean.getIs_open()) || "0".equals(dataBean.getIs_open())) {
            holders.kaidian_pic.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(dataBean.getIs_open()) || "1".equals(dataBean.getIs_open())) {
            holders.kaidian_pic.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(dataBean.getShop_type()) && "本店".equals(dataBean.getShop_type())) {
            holders.bendian_tv.setVisibility(View.VISIBLE);
            holders.liandian_tv.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(dataBean.getShop_type()) && "链店".equals(dataBean.getShop_type())) {
            holders.bendian_tv.setVisibility(View.GONE);
            holders.liandian_tv.setVisibility(View.VISIBLE);
        }

        holders.button_layout.setVisibility(View.GONE);
        if ("代金券".equals(type)) {
            holders.button_layout.setVisibility(View.VISIBLE);
        }

        String order_status = dataBean.getOrder_status();
        if ("0".equals(order_status)){
            holders.orderType.setText("待付款");
        }else if ("1".equals(order_status)){
            holders.orderType.setText("待发货");
        }else if ("2".equals(order_status)){
            holders.orderType.setText("待收货");
        }else if ("3".equals(order_status)){
            holders.orderType.setText("待评价");
        }else if ("4".equals(order_status)){
            holders.orderType.setText("已完成");
        }

        if (holders.button_layout.getVisibility() == View.VISIBLE) {

            holders.time_tv.setVisibility(View.GONE);
            //黄券额度
            final double ticket_lines = TextUtils.isEmpty(dataBean.getTicket_lines()) ? 0 : Double.parseDouble(dataBean.getTicket_lines());


            final double ticket_pric = TextUtils.isEmpty(dataBean.getTicket_price()) ? 0 : Double.parseDouble(dataBean.getTicket_price());
            final double lesser = ticket_lines - ticket_pric > 0 ? ticket_pric : ticket_lines;
            holders.max_tv.setText("*最多可使用"+lesser+"元黄券");
            holders.input_et.setText(String.valueOf(lesser));
            holders.input_et.setSelection(holders.input_et.getText().length());
            holders.input_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String numStr = s.toString().trim();
                    Log.e("TAG", "afterTextChanged: "+numStr );
                    if (!TextUtils.isEmpty(numStr)){
                        holders.input_et.removeTextChangedListener(this);
                        double price = Double.parseDouble(numStr.toString());
                        if (price > lesser) {
                            holders.input_et.setText(String.valueOf(lesser));
                        }
                        holders.input_et.setSelection(holders.input_et.getText().length());
                        holders.input_et.addTextChangedListener(this);
                    }

                }
            });

            holders.tv_btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = holders.input_et.getText().toString();
                    if (TextUtils.isEmpty(s)) {
                        Toast.makeText(context, "金额不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (mButtonClickListener != null) {
                        mButtonClickListener.buttonClick(holder.getLayoutPosition(), s, "3");
                    }
                }
            });

            holders.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = holders.input_et.getText().toString();
                    if (TextUtils.isEmpty(s)) {
                        Toast.makeText(context, "金额不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (mButtonClickListener != null) {
                        mButtonClickListener.buttonClick(holder.getLayoutPosition(), s, "2");
                    }
                }
            });
        }
    }

    public void notifyData(int position){
        if (list!=null && list.size()>0){
            list.remove(position);
            notifyItemRemoved(position);
            if(position != list.size()){ // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(position, list.size() - position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView buyer, supplier, priFit, vouvherType, proportion, orderType, shop_name_tv, time_tv, bendian_tv, liandian_tv;

        private ListViewForScrollView mListView;

        private ImageView kaidian_pic;

        private LinearLayout button_layout;
        private EditText input_et;
        private TextView tv_btn_left;
        private TextView tv_btn_right;
        private TextView  max_tv;


        public MyViewHolder(View itemView) {
            super(itemView);

            shop_name_tv = itemView.findViewById(R.id.shop_name_tv);
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

            mListView = itemView.findViewById(R.id.listview);

            time_tv = itemView.findViewById(R.id.time_tv);

            kaidian_pic = itemView.findViewById(R.id.kaidian_img);

            bendian_tv = itemView.findViewById(R.id.bendian_tv);

            liandian_tv = itemView.findViewById(R.id.liandian_tv);

            button_layout = itemView.findViewById(R.id.button_layout);
            input_et = itemView.findViewById(R.id.input_et);
            tv_btn_left = itemView.findViewById(R.id.tv_btn_left);
            tv_btn_right = itemView.findViewById(R.id.tv_btn_right);
            max_tv=itemView.findViewById(R.id.max_tv);

        }
    }


    private class MyAdapter extends BaseAdapter {
        private List<ShopOrderBean.DataBean.OrderGoodsBean> mGoodsBeanList;
        private Context mContext;
        private LayoutInflater mInflater;

        public MyAdapter(List<ShopOrderBean.DataBean.OrderGoodsBean> goodsBeanList, Context context) {
            mGoodsBeanList = goodsBeanList;
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mGoodsBeanList != null && mGoodsBeanList.size() > 0 ? mGoodsBeanList.size() : 0;
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
            if (view == null) {
                holder = new ViewHolder();
                view = mInflater.inflate(R.layout.shop_order_relist_item2, parent, false);
                holder.head_img = view.findViewById(R.id.shop_order_ima);
                holder.title_tv = view.findViewById(R.id.shop_order_content);
                holder.price_tv = view.findViewById(R.id.price_tv);
                holder.nums_tv = view.findViewById(R.id.nums_tv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            ShopOrderBean.DataBean.OrderGoodsBean orderGoodsBean = mGoodsBeanList.get(position);
            Glide.with(mContext).load(orderGoodsBean.getPic()).into(holder.head_img);
            holder.title_tv.setText(orderGoodsBean.getGoods_name());
            holder.price_tv.setText("￥" + orderGoodsBean.getShop_price());
            holder.nums_tv.setText("x" + orderGoodsBean.getGoods_num());
            return view;
        }

        class ViewHolder {
            private ImageView head_img;
            private TextView title_tv;
            private TextView price_tv;
            private TextView nums_tv;
        }
    }
}
