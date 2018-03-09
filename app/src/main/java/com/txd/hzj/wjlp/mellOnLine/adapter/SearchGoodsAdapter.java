package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.CFGoodsList;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/22 0022
 * 时间：10:25
 * 描述：商品搜索列表
 * ===============Txunda===============
 */

public class SearchGoodsAdapter extends BaseAdapter {

    private List<CFGoodsList> list;
    private Context context;
    private LayoutInflater inflater;

    private int goods_pic = 0;

    private int logo_size1 = 0;

    private int logo_size2 = 0;

    public SearchGoodsAdapter(List<CFGoodsList> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        goods_pic = ToolKit.dip2px(context, 180);
        logo_size1 = ToolKit.dip2px(context, 36);
        logo_size2 = ToolKit.dip2px(context, 24);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CFGoodsList getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        GVH holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_ticket_gv, viewGroup, false);
            holder = new GVH();
            ViewUtils.inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (GVH) view.getTag();
        }
        final CFGoodsList cfGoodsList = getItem(position);

        holder.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.sold_num_tv.setVisibility(View.VISIBLE);
        if (cfGoodsList.getTicket_buy_id().equals("0")) {
            holder.use_coupon_tv.setText("不可使用代金券");
<<<<<<< HEAD
=======
            holder.use_coupon_tv.setAlpha(0.3f);
>>>>>>> master
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
        } else {
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
            holder.use_coupon_tv.setText("可使用" + cfGoodsList.getTicket_buy_discount() + "代金券");
        }
        Glide.with(context).load(cfGoodsList.getCountry_logo())
                .centerCrop()
                .dontAnimate()
                .override(logo_size1,logo_size2)
                .placeholder(R.drawable.ic_default)
//                .error(R.id.ic_right_arrow)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.country_logo_iv);

        Glide.with(context).load(cfGoodsList.getGoods_img())
                .override(goods_pic, goods_pic)
                .centerCrop().placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default).into(holder.goods_pic_iv);

        holder.item_goods_name_tv.setText(cfGoodsList.getGoods_name());
        holder.peice_tv.setText(cfGoodsList.getShop_price());
        holder.older_price_tv.setText(cfGoodsList.getMarket_price());

        holder.get_integral_tv.setText(cfGoodsList.getIntegral());

        holder.sold_num_tv.setText("已售" + cfGoodsList.getSell_num() + "件");

        return view;
    }

    private class GVH {
        /**
         * 原价
         */
        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;

        /**
         * 销售量
         */
        @ViewInject(R.id.sold_num_tv)
        private TextView sold_num_tv;

        /**
         * 是否使用优惠券
         */
        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;

        /**
         * 国旗
         */
        @ViewInject(R.id.country_logo_iv)
        private ImageView country_logo_iv;

        /**
         * 商品图片
         */
        @ViewInject(R.id.goods_pic_iv)
        private ImageView goods_pic_iv;

        /**
         * 商品名称
         */
        @ViewInject(R.id.item_goods_name_tv)
        private TextView item_goods_name_tv;

        /**
         * 商品价格
         */
        @ViewInject(R.id.peice_tv)
        private TextView peice_tv;
        /**
         * 积分
         */
        @ViewInject(R.id.get_integral_tv)
        private TextView get_integral_tv;
    }

}
