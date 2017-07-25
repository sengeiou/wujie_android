package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
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

    public GoodsForRvAdapter(Context context, List<String> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 根据状态获取布局
     * @param parent    ViewCroup
     * @param viewType  类型
     * @return  ItemView
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
        if (0 == type || 2 == type || 4 == type) {
            holder.home_count_down_view.setTag("text");
            holder.home_count_down_view.start(3600000);
        }
        if (0 == type || 2 == type || 5 == type) {
            // 设置进度
            holder.cpb_progresbar2.setMaxProgress(100);
            holder.cpb_progresbar2.setCurProgress(50);
        }

        if (type < 5) {
            holder.older_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
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
    }

    @Override
    public int getItemCount() {
        return 12;
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

        public ItemView(View itemView) {
            super(itemView);
        }
    }

    private HorizontalAdapter.OnItemClickLitener itemClickLitener;

    public void setListener(HorizontalAdapter.OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }

}
