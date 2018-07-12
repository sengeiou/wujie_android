package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;

import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：16:07
 * 描述：
 */

public class WjMellAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private WJMVH wjmvh;

    private List<AllGoodsBean> goods;

    private int size;
    private int logo_size1;
    private int logo_size2;

    public WjMellAdapter(Context context, List<AllGoodsBean> goods) {
        this.context = context;
        this.goods = goods;
        inflater = LayoutInflater.from(context);
        size = ToolKit.dip2px(context, 180);
        logo_size1 = ToolKit.dip2px(context, 36);
        logo_size2 = ToolKit.dip2px(context, 24);
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public AllGoodsBean getItem(int i) {
        return goods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AllGoodsBean allGoodsBean = getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.item_ticket_gv, null);
            wjmvh = new WJMVH();
            ViewUtils.inject(wjmvh, view);
            view.setTag(wjmvh);
        } else {
            wjmvh = (WJMVH) view.getTag();
        }
        wjmvh.peice_tv.setText("兑换需要" + allGoodsBean.getUse_integral() + "积分");
        wjmvh.peice_tv.setTextSize(12);

        wjmvh.older_price_tv.setVisibility(View.GONE);
        // 优惠券
        wjmvh.use_coupon_tv.setVisibility(View.GONE);
        wjmvh.jf_layout.setVisibility(View.GONE);

        //需字
        wjmvh.tv_xu.setVisibility(View.VISIBLE);
        // 国旗
        Glide.with(context).load(allGoodsBean.getCountry_logo())
//                .override(logo_size1, logo_size2)
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.drawable.ic_default)
//                .error(R.drawable.ic_default)
                .into(wjmvh.country_logo_iv);

        // 商品图片
        Glide.with(context).load(allGoodsBean.getGoods_img())
                .override(size, size)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(wjmvh.goods_pic_iv);

        wjmvh.item_goods_name_tv.setText(allGoodsBean.getGoods_name());

        return view;
    }

    private class WJMVH {
        /**
         * 积分
         */
        @ViewInject(R.id.peice_tv)
        private TextView peice_tv;

        /**
         * 原价
         */
        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;

        /**
         * 积分
         */
        @ViewInject(R.id.jf_layout)
        private LinearLayout jf_layout;
        /**
         * 优惠券
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
         * 商品用户名
         */
        @ViewInject(R.id.item_goods_name_tv)
        private TextView item_goods_name_tv;

        @ViewInject(R.id.tv_xu)
        private TextView tv_xu;

    }
}
