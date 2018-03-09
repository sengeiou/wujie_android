package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * 日期：2017/7/5 0005
 * 时间：13:32
 * 描述：
 * ===============Txunda===============
 */

public class RacycleAllAdapter extends RecyclerView.Adapter<RacycleAllAdapter.ItemView> {
    private Context context;
    private List<CFGoodsList> list;
    private LayoutInflater inflater;

    /**
     * 标识
     * 0.限量购
     * 1.票券区
     * 2.xfte预购
     * 3.进口馆
     * 4.竞拍汇
     * 5.一元夺宝
     * 6.汽车购
     * 7.房产购
     * 8.拼团购
     */
    private int type = 0;

    private boolean showSelect;

    private int goods_pic = 0;

    private int logo_size1 = 0;
    private int logo_size2 = 0;

    public RacycleAllAdapter(Context context, List<CFGoodsList> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        goods_pic = ToolKit.dip2px(context, 180);
        logo_size1 = ToolKit.dip2px(context, 36);
        logo_size2 = ToolKit.dip2px(context, 23);
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = inflater.inflate(R.layout.item_ticket_gv, parent, false);
//        switch (viewType) {
//            case 0:// 限量购
//            case 2:// xfte预购
//                view = inflater.inflate(R.layout.item_purchase_gv, parent, false);
//                break;
//            case 1:// 票券区
//                view = inflater.inflate(R.layout.item_ticket_gv, parent, false);
//                break;
//            case 3:// 进口馆
//                view = inflater.inflate(R.layout.item_import_gv, parent, false);
//                break;
//            case 4:// 竞拍汇
//                view = inflater.inflate(R.layout.item_auction_gv, parent, false);
//                break;
//            case 5:// 一元夺宝
//                view = inflater.inflate(R.layout.item_good_luck_gv, parent, false);
//                break;
//            case 6:// 汽车购
//                view = inflater.inflate(R.layout.item_car_gv, parent, false);
//                break;
//            case 7:// 房产购
//                view = inflater.inflate(R.layout.item_house_gv, parent, false);
//                break;
//            case 8:// 拼好货
//                view = inflater.inflate(R.layout.item_group_shaopping_lv2, parent, false);
//                break;
//        }
        ItemView itemView = new ItemView(view);
        ViewUtils.inject(itemView, view);
        return itemView;
    }

    @Override
    public void onBindViewHolder(final ItemView holder, int position) {
        final CFGoodsList cfGoodsList = list.get(position);

        holder.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.sold_num_tv.setVisibility(View.VISIBLE);
<<<<<<< HEAD
        if (cfGoodsList.getTicket_buy_id().equals("0")) {
=======
//        ticket_buy_discount
        if (cfGoodsList.getTicket_buy_discount().equals("0")) {
>>>>>>> master
            holder.use_coupon_tv.setText("不可使用代金券");
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
        } else {
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
            holder.use_coupon_tv.setText("最多可使用" + cfGoodsList.getTicket_buy_discount() + "%代金券");
        }

        if (itemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    itemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }

        // 是不是显示选中按钮
        if (showSelect) {
            holder.collect_goods_status_iv.setVisibility(View.VISIBLE);

            if (cfGoodsList.getIsSelect()) {
                holder.collect_goods_status_iv.setImageResource(R.drawable.icon_collect_goods_selected);
            } else {
                holder.collect_goods_status_iv.setImageResource(R.drawable.icon_collect_goods_unselect);
            }

        } else {
            holder.collect_goods_status_iv.setVisibility(View.GONE);
        }

        Glide.with(context).load(cfGoodsList.getCountry_logo())
                .centerCrop()
                .override(logo_size1, logo_size2)
                .placeholder(R.drawable.ic_default)
//                .error(R.id.ic_right_arrow)
                .dontAnimate()
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

        holder.collect_goods_status_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cfGoodsList.getIsSelect()) {
                    cfGoodsList.setIsSelect(false);
                    holder.collect_goods_status_iv.setImageResource(R.drawable.icon_collect_goods_unselect);
                } else {
                    cfGoodsList.setIsSelect(true);
                    holder.collect_goods_status_iv.setImageResource(R.drawable.icon_collect_goods_selected);
                }
                int select = 0;
                for (CFGoodsList cg : list) {
                    if (cg.getIsSelect()) {
                        select++;
                    }
                }
                if (selectNum != null) {
                    selectNum.selectNum(select);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return list.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        type = position;
        return position;
    }

    class ItemView extends RecyclerView.ViewHolder {

        /**
         * 原价
         */
        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;
//        /**
//         * 倒计时(type=0,2)
//         */
//        @ViewInject(R.id.home_count_down_view)
//        private CountdownView home_count_down_view;
//        /**
//         * 进度条(type=0,2,5)
//         */
//        @ViewInject(R.id.cpb_progresbar2)
//        private CustomProgressBar cpb_progresbar2;

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
         * 选中不选中
         */
        @ViewInject(R.id.collect_goods_status_iv)
        private ImageView collect_goods_status_iv;

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

        ItemView(View itemView) {
            super(itemView);
        }
    }

    private HorizontalAdapter.OnItemClickLitener itemClickLitener;

    public void setListener(HorizontalAdapter.OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }

    public interface SelectNum {
        void selectNum(int num);
    }

    private SelectNum selectNum;

    public void setSelectNum(SelectNum selectNum) {
        this.selectNum = selectNum;
    }
}
