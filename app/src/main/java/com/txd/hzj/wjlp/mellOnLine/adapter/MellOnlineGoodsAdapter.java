package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/11 0011
 * 时间：09:09
 * 描述：
 * ===============Txunda===============
 */

public class MellOnlineGoodsAdapter extends RecyclerView.Adapter<MellOnlineGoodsAdapter.MellGoodsViewHolder> {

    private int type;

    private Context context;

    private LayoutInflater mInflayer;

    public MellOnlineGoodsAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        mInflayer = LayoutInflater.from(context);
    }

    @Override
    public MellGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (type) {
            case 0:// 店铺首页
                view = mInflayer.inflate(R.layout.item_red_package_lv, parent, false);
                break;
            case 1:// 全部商品,热销,最新上架
                view = mInflayer.inflate(R.layout.item_mell_goods_rv, parent, false);
                break;
            case 2:// 限量购
            case 4:// 无界预购
                view = mInflayer.inflate(R.layout.item_purchase_gv, parent, false);
                break;
            case 3:// 拼团购
                view = mInflayer.inflate(R.layout.item_group_shaopping_lv2, parent, false);
                break;
            case 5:// 竞拍汇
                view = mInflayer.inflate(R.layout.item_auction_gv, parent, false);
                break;
            case 6:// 一元夺宝
                view = mInflayer.inflate(R.layout.item_good_luck_gv, parent, false);
                break;
        }
        MellGoodsViewHolder mellGoodsViewHolder = new MellGoodsViewHolder(view);
        ViewUtils.inject(mellGoodsViewHolder, view);
        return mellGoodsViewHolder;
    }

    @Override
    public void onBindViewHolder(MellGoodsViewHolder holder, int position) {
        if (2 == type || 4 == type || 5 == type) {
            holder.home_count_down_view.setTag("text");
            holder.home_count_down_view.start(3600000);
        }
        if (2 == type || 4 == type || 6 == type) {
            // 设置进度
            holder.cpb_progresbar2.setMaxProgress(100);
            holder.cpb_progresbar2.setCurProgress(50);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MellGoodsViewHolder extends RecyclerView.ViewHolder {

        // =========== 全部商品，热销商品，最近新上
        @ViewInject(R.id.bottom_layout_for_goods)
        private LinearLayout bottom_layout_for_goods;

        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;

        @ViewInject(R.id.coupon_layout)
        private LinearLayout coupon_layout;


        /**
         * 倒计时(type=2,4,5)
         */
        @ViewInject(R.id.home_count_down_view)
        private CountdownView home_count_down_view;
        /**
         * 进度条(type=2,4)
         */
        @ViewInject(R.id.cpb_progresbar2)
        private CustomProgressBar cpb_progresbar2;


        public MellGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }

}
