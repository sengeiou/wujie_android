package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

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
    private List<String> list;
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

    private boolean showSelect;

    public RacycleAllAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        L.e("=====ViewTYpe=====", String.valueOf(viewType));
        view = inflater.inflate(R.layout.item_ticket_gv, parent, false);
//        switch (viewType) {
//            case 0:// 限量购
//            case 2:// 无界预购
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

//        if (0 == type || 2 == type || 4 == type) {
//            holder.home_count_down_view.setTag("text");
//            holder.home_count_down_view.start(3600000);
//        }
//        if (0 == type || 2 == type || 5 == type) {
//            // 设置进度
//            holder.cpb_progresbar2.setMaxProgress(100);
//            holder.cpb_progresbar2.setCurProgress(50);
//        }
//
        holder.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.sold_num_tv.setVisibility(View.VISIBLE);
        if (position % 2 == 0) {
            holder.use_coupon_tv.setText("不可使用购物券");
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
        } else {
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
            holder.use_coupon_tv.setText("可使用30%购物券");
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
        } else {
            holder.collect_goods_status_iv.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        type = position;
        return position;
    }

    class ItemView extends RecyclerView.ViewHolder {

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

        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;

        /**
         * 选中不选中
         */
        @ViewInject(R.id.collect_goods_status_iv)
        private ImageView collect_goods_status_iv;

        public ItemView(View itemView) {
            super(itemView);
        }
    }

    private HorizontalAdapter.OnItemClickLitener itemClickLitener;

    public void setListener(HorizontalAdapter.OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }

}
