package com.txd.hzj.wjlp.txunda_lh;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * {@link com.txd.hzj.wjlp.txunda_lh.Bean(com.txd.hzj.wjlp.txunda_lh.Bean)}
 * by Txunda_LH on 2017/11/24.
 * 这是一个搭配购
 */

public class aty_collocations extends BaseAty {
    @ViewInject(R.id.listview)
    private ListView listview;
    //    @ViewInject(R.id.recyckerview)
//    private RecyclerView recyckerview;
    private String goods_id = "";
    private List<Bean> list;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_collocations;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.goods_details_title);
    }

    @Override
    protected void initialized() {
        goods_id = getIntent().getStringExtra("goods_id");
    }

    @Override
    protected void requestData() {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        apiTool2.postApi(Config.BASE_URL + "Goods/groupGoodsList", params, this);
    }

    Map<String, String> data;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        list = GsonUtil.getObjectList(data.get("data"), Bean.class);
//        recyckerview.setLayoutManager(new LinearLayoutManager(this));
//        recyckerview.setAdapter(new MyAdp());
        listview.setAdapter(new listadp());
    }

    class listadp extends BaseAdapter {
        ViewHolder holder;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Bean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(aty_collocations.this, R.layout.item_collocations, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                ViewUtils.inject(holder, convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_group.setText("搭配组合：" + getItem(position).getGroup_name());
            holder.use_coupon_tv.setText("最多可用" + getItem(position).getTicket_buy_discount() + "%代金券");
            holder.tv_group_price.setText("搭配价：¥" + getItem(position).getGroup_price());
            holder.tv_group_integral.setText(getItem(position).getIntegral());
            double price = Double.parseDouble(getItem(position).getGoods_price()) - Double.parseDouble(getItem(position).getGroup_price());
            DecimalFormat df = new DecimalFormat("#.00");
            holder.tv_goods_price.setText("立省¥" + df.format(price));
            holder.listview.setAdapter(new MyListViewadp(getItem(position).getGoods()));
            holder.rv_cheap_group.setLayoutManager(new LinearLayoutManager(aty_collocations.this, LinearLayoutManager.HORIZONTAL, false));
            holder.rv_cheap_group.setAdapter(new cg_adp(getItem(position).getGoods()));
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getItem(position).setChack(isChecked);
                    notifyDataSetChanged();
                }
            });
            if (getItem(position).isChack()) {
                holder.rv_cheap_group.setVisibility(View.GONE);
                holder.listview.setVisibility(View.VISIBLE);
            } else {
                holder.rv_cheap_group.setVisibility(View.VISIBLE);
                holder.listview.setVisibility(View.GONE);
            }
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.tv_group)
            private TextView tv_group;//搭配组合
            @ViewInject(R.id.use_coupon_tv)
            private TextView use_coupon_tv;//代金券
            @ViewInject(R.id.checkbox)
            private CheckBox checkbox;
            @ViewInject(R.id.tv_group_price)
            private TextView tv_group_price;//搭配价
            @ViewInject(R.id.tv_group_integral)
            private TextView tv_group_integral;
            @ViewInject(R.id.tv_goods_price)
            private TextView tv_goods_price;
            @ViewInject(R.id.listview)
            private ListViewForScrollView listview;
            @ViewInject(R.id.rv_cheap_group)
            private RecyclerView rv_cheap_group;

        }
    }

//    class MyAdp extends RecyclerView.Adapter<MyAdp.ViewHolder> {
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collocations, null);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.tv_group.setText("搭配组合：" + getItem(position).getGroup_name());
//            holder.use_coupon_tv.setText("最多可用" + getItem(position).getTicket_buy_discount() + "%代金券");
//            holder.tv_group_price.setText("搭配价：¥" + getItem(position).getGroup_price());
//            holder.tv_group_integral.setText(getItem(position).getIntegral());
//            double price = Double.parseDouble(getItem(position).getGoods_price()) - Double.parseDouble(getItem(position).getGroup_price());
//            DecimalFormat df = new DecimalFormat("#.00");
//            holder.tv_goods_price.setText("立省¥" + df.format(price));
////            holder.rv_cheap_group.setLayoutManager(new LinearLayoutManager(aty_collocations.this, LinearLayoutManager.HORIZONTAL, false));
////            holder.rv_cheap_group.setAdapter(new cg_adp(getItem(position).getGoods()));
//        }
//
//        private Bean getItem(int i) {
//            return list.get(i);
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//
//            private TextView tv_group;//搭配组合
//
//            private TextView use_coupon_tv;//代金券
////            @ViewInject(R.id.checkbox)
////            private CheckBox checkbox;
//
//            private TextView tv_group_price;//搭配价
//
//            private TextView tv_group_integral;
//
//            private TextView tv_goods_price;
////            @ViewInject(R.id.listview)
////            private ListViewForScrollView listview;
////            @ViewInject(R.id.rv_cheap_group)
////            private RecyclerView rv_cheap_group;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                tv_group = itemView.findViewById(R.id.tv_group);
//                use_coupon_tv = itemView.findViewById(R.id.use_coupon_tv);
//                tv_group_price = itemView.findViewById(R.id.tv_group_price);
//                tv_group_integral = itemView.findViewById(R.id.tv_group_integral);
//                tv_goods_price = itemView.findViewById(R.id.tv_goods_price);
//
//
//            }
//        }
//    }


    class cg_adp extends RecyclerView.Adapter<cg_adp.ViewHolder> {
        private List<Bean.GoodsBean> goods;

        public cg_adp(List<Bean.GoodsBean> goods) {
            this.goods = goods;
        }

        @Override
        public cg_adp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new cg_adp.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_dpg, null));
        }

        @Override
        public void onBindViewHolder(cg_adp.ViewHolder holder, int position) {
            Glide.with(aty_collocations.this).load(getItem(position).getGoods_img()).into(holder.imageview);
            holder.tv_price.setText("¥" + getItem(position).getShop_price());
            if (position == goods.size() - 1) {
                holder.im_jiahao.setVisibility(View.GONE);
            } else {
                holder.im_jiahao.setVisibility(View.VISIBLE);
            }
        }

        private Bean.GoodsBean getItem(int i) {
            return goods.get(i);
        }

        @Override
        public int getItemCount() {
            return goods.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageview;
            TextView tv_price;
            ImageView im_jiahao;

            public ViewHolder(View itemView) {
                super(itemView);
                imageview = (ImageView) itemView.findViewById(R.id.imageview);
                im_jiahao = (ImageView) itemView.findViewById(R.id.imageView3);
                tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            }
        }
    }

    class MyListViewadp extends BaseAdapter {
        ViewHolder vh;
        private List<Bean.GoodsBean> goods;

        public MyListViewadp(List<Bean.GoodsBean> goods) {
            this.goods = goods;
        }

        @Override
        public int getCount() {
            return goods.size();
        }

        @Override
        public Bean.GoodsBean getItem(int position) {
            return goods.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(aty_collocations.this, R.layout.item_collocations_listview, null);
                vh = new ViewHolder();
                convertView.setTag(vh);
                ViewUtils.inject(vh, convertView);
            }
            vh = (ViewHolder) convertView.getTag();
            Glide.with(aty_collocations.this).load(getItem(position).getGoods_img()).into(vh.imageview);
            vh.tv_good_name.setText(getItem(position).getGoods_name());
            vh.tv_good_price.setText("原价：" + getItem(position).getShop_price());
            return convertView;
        }

        class
        ViewHolder {
            @ViewInject(R.id.imageview)
            ImageView imageview;
            @ViewInject(R.id.tv_good_name)
            TextView tv_good_name;
            @ViewInject(R.id.tv_good_price)
            TextView tv_good_price;
        }

    }
}
