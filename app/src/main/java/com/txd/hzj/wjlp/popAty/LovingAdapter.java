package com.txd.hzj.wjlp.popAty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.Order;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：19:29
 * 描述：
 * ===============Txunda===============
 */
public class LovingAdapter extends BaseAdapter {

    private Context context;
    private List<Order> list;
    private LayoutInflater mInflater;

    private ViewHolder holder;

    public LovingAdapter(Context context, List<Order> list) {
        this.context = context;
//        List<Order> orderlist = new ArrayList<>();
//        if (type == 0) {
//            this.list = list;
//        } else {
//            for (int i = 0; i < list.size(); i++) {
//                if (type == list.get(i).getType()) {
//                    orderlist.add(list.get(i));
//                }
//            }
//
//        }
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_loving_order_lv, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        holder.title.setText(list.get(i).getShop_name());
        switch (list.get(i).getStatus()) {
            case "1":
                holder.state.setText("待付款");
                holder.tv_btn_left.setText("取消订单");
                holder.tv_btn_right.setText("付款");
                break;
            case "2":
                holder.state.setText("买家已付款");
                holder.tv_btn_left.setVisibility(View.GONE);
                holder.tv_btn_right.setVisibility(View.GONE);
                break;
            case "3":
                holder.state.setText("卖家已发货");
                holder.tv_btn_left.setText("查看物流");
                holder.tv_btn_right.setText("确认收货");
                break;
            case "4":
                holder.state.setText("交易成功");
                holder.tv_btn_left.setText("查看物流");
                holder.tv_btn_right.setText("评价");
                break;
        }

        holder.goods_for_order_lv.setAdapter(new GoodsForOrderAdapter());
        holder.goods_for_order_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  context.startActivity(new Intent(context, OrderDetailsAty.class));
            }
        });
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.ItemTitle)
        public TextView title;
        @ViewInject(R.id.tv_state)
        public TextView state;

        @ViewInject(R.id.tv_btn_left)
        public TextView tv_btn_left;
        @ViewInject(R.id.tv_btn_right)
        public TextView tv_btn_right;


        @ViewInject(R.id.goods_for_order_lv)
        private ListViewForScrollView goods_for_order_lv;

    }

    private class GoodsForOrderAdapter extends BaseAdapter {

        private GoodsForOrderAdapter.GOVH goVh;

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_loving_goods_lv, null);
                goVh = new GoodsForOrderAdapter.GOVH();
                ViewUtils.inject(goVh, view);
                view.setTag(goVh);
            } else {
                goVh = (GoodsForOrderAdapter.GOVH) view.getTag();
                Glide.with(context).load(list.get(i).getCar_img()).into(goVh.image);
                goVh.name.setText(list.get(i).getCar_name());
                // goVh.title.setText(list.get(i).get);
                goVh.num.setText("x" + list.get(i).getNum());
                goVh.price.setText(list.get(i).getPre_money());
            }
            return view;
        }

        private class GOVH {
            @ViewInject(R.id.image)
            private ImageView image;
            @ViewInject(R.id.name)
            private TextView name;
            @ViewInject(R.id.title)
            private TextView title;
            @ViewInject(R.id.price)
            private TextView price;
            @ViewInject(R.id.num)
            private TextView num;
        }
    }

}
