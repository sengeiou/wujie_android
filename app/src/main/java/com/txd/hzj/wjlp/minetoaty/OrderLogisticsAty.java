package com.txd.hzj.wjlp.minetoaty;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.OrderLogistics;
import com.txd.hzj.wjlp.http.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Txd_lienchao on 2018/3/8 0008 下午 7:40.
 * 功能描述:订单物流
 * email:470360046@qq.com
 * 不急不躁，BUG圆如，
 * 说改就改，不撕不怒，
 * 种种需求，过眼云烟，
 * 敏捷迭代，自在随心，
 * 立项结项，不如吃瓜。
 */

public class OrderLogisticsAty extends BaseAty {
    @ViewInject(R.id.lv_goods)
    private ListView lv_goods;
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    private   String type;//0 普通商品  1 拼单购商品
    private List<OrderLogistics.DataBean>logisticsList=new ArrayList<>();
    @Override
    protected int getLayoutResId() {
        return R.layout.aty_order_logistics;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("订单物流");
    }

    @Override
    protected void requestData() {
        Bundle bundle=this.getIntent().getExtras();
        String order_id=bundle.getString("order_id");
        type=bundle.getString("type");//0 普通商品  1 拼单购商品
        Order.orderLogistics(order_id,type,this);
        showProgressDialog();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if(requestUrl.contains("orderLogistics")){
            Gson gson=new Gson();
            OrderLogistics orderLogistics=gson.fromJson(jsonStr,OrderLogistics.class);
            if(orderLogistics.getCode().equals("1")){
                for(OrderLogistics.DataBean temp:orderLogistics.getData()){
                    logisticsList.add(temp);
                }
                lv_goods.setAdapter(new GoodsAdapter(logisticsList,OrderLogisticsAty.this));
            }
        }
    }

    class GoodsAdapter extends BaseAdapter{
        private ViewHolder viewHolder;
        private List<OrderLogistics.DataBean>list;
        private Context context;

        public GoodsAdapter(List<OrderLogistics.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(OrderLogisticsAty.this).inflate(R.layout.item_goods, null);
                viewHolder = new ViewHolder();
                view.setTag(viewHolder);
                ViewUtils.inject(viewHolder, view);


            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            Glide.with(OrderLogisticsAty.this).load(list.get(i).getGoods_img()).into(viewHolder.image);
            viewHolder.jifenTv.setVisibility(View.GONE);
            viewHolder.goosOrderItem_wuliu_layout.setVisibility(View.VISIBLE);
            viewHolder.goosOrderItem_wuliuNumber_tv.setText(list.get(i).getExpress_no());
            viewHolder.goosOrderItem_showLogistics_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("order_goods_id", list.get(i).getId()); // 订单中的商品ID
                    bundle.putString("type",type); //0普通商品 1拼单购
                    startActivity(ExpressAtv.class, bundle);
                }
            });
            if (list.get(i).getIs_active().equals("2")){
                viewHolder.tv_2980.setText("399");
                viewHolder.tv_2980.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_2980.setVisibility(View.GONE);
            }

            viewHolder.name.setText(list.get(i).getGoods_name());
            viewHolder.num.setText("x" + list.get(i).getGoods_num());
            viewHolder.title.setText("规格" + list.get(i).getAttr());
            viewHolder.tv_price.setText("¥" + list.get(i).getShop_price());
            viewHolder.tv_price.setVisibility(View.VISIBLE);
            return view;
        }
        class ViewHolder{

            @ViewInject(R.id.goosOrderItem_wuliu_layout) // 显示头
            private LinearLayout goosOrderItem_wuliu_layout;
            @ViewInject(R.id.goosOrderItem_wuliuNumber_tv) // 物流单号
            private TextView goosOrderItem_wuliuNumber_tv;
            @ViewInject(R.id.goosOrderItem_showLogistics_tv) // 查看物流按钮
            private TextView goosOrderItem_showLogistics_tv;

            @ViewInject(R.id.image)
            private ImageView image;
            @ViewInject(R.id.name)
            private TextView name;
            @ViewInject(R.id.num)
            private TextView num;
            @ViewInject(R.id.title)
            private TextView title;
            @ViewInject(R.id.tv_price)
            private TextView tv_price;
            @ViewInject(R.id.jifenTv)
            private TextView jifenTv;
            @ViewInject(R.id.tv_2980)
            private TextView tv_2980;
        }
    }
}
