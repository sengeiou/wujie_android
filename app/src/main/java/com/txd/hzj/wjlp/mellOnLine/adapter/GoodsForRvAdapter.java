package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.OneBuyBean;
import com.txd.hzj.wjlp.bean.OneBuyListBean;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：11:54
 * 描述：
 * ===============Txunda===============
 */

public class GoodsForRvAdapter extends RecyclerView.Adapter<GoodsForRvAdapter.ItemView> {

    private Context context;
    private List<OneBuyListBean> list;
    private LayoutInflater inflater;
    /**
     * 标识
     * 0.限量购
     * 1.票券区
     * 2.无界预购
     * 3.进口馆
     * 4.竞拍汇
     * 5.一元夺宝
     * 6.汽车购
     * 7.房产购
     * 8.拼团购
     */
    private int type = 0;

    private int size = 0;

    private int size2 = 0;
    private int size3 = 0;

    public GoodsForRvAdapter(Context context, List<OneBuyListBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        size = ToolKit.dip2px(context, 180);
        size2 = ToolKit.dip2px(context, 48);
        size3 = ToolKit.dip2px(context, 32);
        inflater = LayoutInflater.from(context);
    }

    /**
     * 根据状态获取布局
     *
     * @param parent   ViewCroup
     * @param viewType 类型
     * @return ItemView
     */
    @Override
    public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:// 限量购
            case 2:// 无界预购
                view = inflater.inflate(R.layout.item_purchase_gv, parent, false);
                break;
            case 1:// 票券区
                view = inflater.inflate(R.layout.item_ticket_gv, parent, false);
                break;
            case 3:// 进口馆
                view = inflater.inflate(R.layout.item_import_gv, parent, false);
                break;
            case 4:// 竞拍汇
                view = inflater.inflate(R.layout.item_auction_gv, parent, false);
                break;
            case 5:// 一元夺宝
                view = inflater.inflate(R.layout.item_good_luck_gv, parent, false);
                break;
            case 6:// 汽车购
                view = inflater.inflate(R.layout.item_car_gv, parent, false);
                break;
            case 7:// 房产购
                view = inflater.inflate(R.layout.item_house_gv, parent, false);
                break;
            case 8:// 拼好货
                view = inflater.inflate(R.layout.item_group_shaopping_lv2, parent, false);
                break;
        }
        ItemView itemView = new ItemView(view);
        ViewUtils.inject(itemView, view);
        return itemView;
    }

    @Override
    public void onBindViewHolder(final ItemView holder, int position) {
        OneBuyListBean bean = list.get(position);
        if (0 == type || 2 == type || 4 == type) {// 倒计时
            holder.home_count_down_view.setTag("text");
            holder.home_count_down_view.start(3600000);
        }
        if (0 == type || 2 == type || 5 == type) {// 商品进度
            // 最大值
            int max;
            try {
                max = Integer.parseInt(bean.getPerson_num());
            } catch (NumberFormatException e) {
                max = 100;
            }
            // 进度
            int progress;
            try {
                progress = Integer.parseInt(bean.getAdd_num());
            } catch (NumberFormatException e) {
                progress = 0;
            }
            // 设置进度
            holder.cpb_progresbar2.setMaxProgress(max);
            holder.cpb_progresbar2.setCurProgress(progress);
        }

        if (type < 5) {// 商品原价
            holder.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (itemClickLitener != null) {// 点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    itemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
        Glide.with(context).load(bean.getCountry_logo())
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_default)
                .override(size2, size3)
                .dontAnimate()
                .fitCenter()
                .into(holder.logo_for_country_iv);
        Glide.with(context).load(bean.getGoods_img())
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_default)
                .override(size, size)
                .centerCrop()
                .into(holder.goods_pic_iv);
        holder.one_buy_goods_name_tv.setText(bean.getGoods_name());
        holder.person_num_tv.setText("总需" + bean.getPerson_num() + "人");
        holder.add_num_tv.setText(bean.getDiff_num());
        holder.goods_integral_tv.setText(bean.getIntegral());

        // 是否可以使用优惠券，是用多少的优惠券
        if (bean.getTicket_buy_id().equals("0")) {
            holder.use_coupon_tv.setText("不可使用代金券");
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
        } else {
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
            holder.use_coupon_tv.setText("可使用" + bean.getTicket_buy_id() + "%代金券");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    class ItemView extends RecyclerView.ViewHolder {

        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;
        /**
         * 倒计时(type=0,2)
         */
        @ViewInject(R.id.home_count_down_view)
        private CountdownView home_count_down_view;
        /**
         * 进度条(type=0,2,5)
         */
        @ViewInject(R.id.cpb_progresbar2)
        private CustomProgressBar cpb_progresbar2;

        /**
         * 国家
         */
        @ViewInject(R.id.logo_for_country_iv)
        private ImageView logo_for_country_iv;
        /**
         * 优惠券
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
        @ViewInject(R.id.one_buy_goods_name_tv)
        private TextView one_buy_goods_name_tv;
        /**
         * 需要人数
         */
        @ViewInject(R.id.person_num_tv)
        private TextView person_num_tv;
        /**
         * 已参与人数
         */
        @ViewInject(R.id.add_num_tv)
        private TextView add_num_tv;
        /**
         * 积分
         */
        @ViewInject(R.id.goods_integral_tv)
        private TextView goods_integral_tv;

        public ItemView(View itemView) {
            super(itemView);
        }
    }

    private HorizontalAdapter.OnItemClickLitener itemClickLitener;

    public void setListener(HorizontalAdapter.OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }

}
