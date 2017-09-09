package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.auction.AuctionList;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：15:15
 * 描述：限量购适配器
 * ===============Txunda===============
 */

public class LimitAdapter extends BaseAdapter {
    private List<AuctionList> list;
    private Context context;
    private LayoutInflater inflater;
    private LimitViewHolder lvh;
    /**
     * 数据源，用于显示或者隐藏商品进度
     * 0.限量购
     * 1.竞拍汇
     */
    private int source = 0;
    private int type;

    private int size1 = 0;
    private int size2 = 0;

    private int size3 = 0;

    private AdapterTextViewClickListener tvClick;

    public LimitAdapter(List<AuctionList> list, Context context, int type, int source) {
        this.list = list;
        this.context = context;
        this.type = type;
        this.source = source;
        inflater = LayoutInflater.from(context);
        size1 = ToolKit.dip2px(context, 32);
        size2 = ToolKit.dip2px(context, 24);
        size3 = ToolKit.dip2px(context, 200);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AuctionList getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        AuctionList auctionList = getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.item_purchase_gv, null);
            lvh = new LimitViewHolder();
            ViewUtils.inject(lvh, view);
            view.setTag(lvh);
        } else {
            lvh = (LimitViewHolder) view.getTag();
        }
        // 国旗图片
        Glide.with(context).load(auctionList.getCountry_logo())
                .override(size1, size2)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(lvh.item_country_logo_tv);
        // 商品详情
        Glide.with(context).load(auctionList.getGoods_img())
                .override(size3, size3)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(lvh.goods_pic_iv);

        // 现在时间
        long now = Calendar.getInstance().getTimeInMillis() / 1000L;
        // 结束时间
        long end = Long.parseLong(auctionList.getEnd_time());
        lvh.home_count_down_view.setTag("limit" + i);
        lvh.home_count_down_view.start(end - now);

        // 控制显示或隐藏提醒我按钮
        if (1 == type) {
            lvh.limit_remeber_me_tv.setVisibility(View.VISIBLE);
            if (auctionList.getIs_remind().equals("1")) {
                lvh.limit_remeber_me_tv.setText("已提醒");
                lvh.limit_remeber_me_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
            } else {
                lvh.limit_remeber_me_tv.setText("提醒我");
                lvh.limit_remeber_me_tv.setBackgroundResource(R.drawable.shape_remember_me);
            }
        } else {
            lvh.limit_remeber_me_tv.setVisibility(View.GONE);
        }
        // 积分
        lvh.get_integral_tv.setText(auctionList.getIntegral());
        if (0 == source) {
            // 限量购价格
            lvh.peice_tv.setText("￥" + auctionList.getLimit_price());
        } else {
            // 起拍价
            lvh.peice_tv.setText("￥" + auctionList.getStart_price());
        }

        // 原价
        lvh.older_price_tv.setText("￥" + auctionList.getMarket_price());
        lvh.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if (auctionList.getTicket_buy_id().equals("0")) {
            lvh.use_coupon_tv.setText("不可使用优惠券");
            lvh.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
        } else {
            lvh.use_coupon_tv.setText("可用" + auctionList.getTicket_buy_discount() + "%的优惠券");
        }

        if (0 == source) {
            lvh.goods_pross_layout.setVisibility(View.VISIBLE);
            lvh.goods_num_already_tv.setText("已抢" + auctionList.getSell_num() + "件");

            int sell_num;
            try {
                sell_num = Integer.parseInt(auctionList.getSell_num());
            } catch (NumberFormatException e) {
                sell_num = 0;
            }
            int max = 0;
            try {
                max = Integer.parseInt(auctionList.getLimit_store());
            } catch (NumberFormatException e) {
                max = 100;
            }

            lvh.cpb_progresbar2.setMaxProgress(max);
            lvh.cpb_progresbar2.setCurProgress(sell_num);
            double d = sell_num * 100.0f / max;
            if (sell_num >= max) {
                d = 100f;
            }
            String str = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            lvh.preferential_type_tv.setText(str + "%");

        } else {
            lvh.goods_pross_layout.setVisibility(View.GONE);
        }

        lvh.sold_num_tv.setVisibility(View.GONE);

        lvh.limit_remeber_me_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvClick != null) {
                    tvClick.onTextViewClick(view, i);
                }
            }
        });


        return view;
    }

    public void setTvClick(AdapterTextViewClickListener tvClick) {
        this.tvClick = tvClick;
    }

    class LimitViewHolder {

        /**
         * 倒计时
         */
        @ViewInject(R.id.home_count_down_view)
        private CountdownView home_count_down_view;

        /**
         * 进度
         */
        @ViewInject(R.id.cpb_progresbar2)
        private CustomProgressBar cpb_progresbar2;
        /**
         * 销量(隐藏)
         */
        @ViewInject(R.id.sold_num_tv)
        private TextView sold_num_tv;
        @ViewInject(R.id.goods_pross_layout)
        private LinearLayout goods_pross_layout;
        /**
         * 国旗
         */
        @ViewInject(R.id.item_country_logo_tv)
        private ImageView item_country_logo_tv;
        /**
         * 优惠券
         * 是否使用优惠券，使用多少优惠券
         */
        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;
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
         * 价格
         */
        @ViewInject(R.id.peice_tv)
        private TextView peice_tv;
        /**
         * 原价(市场价)
         */
        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;
        /**
         * 积分
         */
        @ViewInject(R.id.get_integral_tv)
        private TextView get_integral_tv;
        /**
         * 提醒我
         */
        @ViewInject(R.id.limit_remeber_me_tv)
        private TextView limit_remeber_me_tv;

        @ViewInject(R.id.goods_num_already_tv)
        private TextView goods_num_already_tv;

        @ViewInject(R.id.preferential_type_tv)
        private TextView preferential_type_tv;

    }
}
