package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
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

    private List<Map<String, String>> list;

    private LayoutInflater mInflayer;

    private int adsSize = 0;
    private int adsSize1 = 0;

    private int logo_size1 = 0;
    private int logo_size2 = 0;

    public MellOnlineGoodsAdapter(Context context, int type, List<Map<String, String>> list) {
        this.context = context;
        this.type = type;
        this.list = list;
        if (0 == type) {
            adsSize = Settings.displayWidth;
            adsSize1 = Settings.displayWidth / 2;
        } else {
            logo_size1 = ToolKit.dip2px(context, 36);
            logo_size2 = ToolKit.dip2px(context, 24);
        }
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
<<<<<<< HEAD
            case 4:// 无界预购
=======
            case 4:// xfte预购
>>>>>>> master
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
        Map<String, String> map = list.get(position);
        switch (type) {
            case 0:
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adsSize, adsSize1);
                holder.image_for_mell.setLayoutParams(params);

                Glide.with(context).load(map.get("ads_pic"))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .override(adsSize, adsSize1)
                        .centerCrop()
                        .into(holder.image_for_mell);

                break;
            case 1:
                Glide.with(context).load(map.get("country_logo"))
                        .override(logo_size1, logo_size2)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .into(holder.logo_for_country_iv);

                if(map.get("ticket_buy_id").equals("0")){
                    holder.use_coupon_tv.setText("不可使用代金券");
                    holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
                } else {
                    holder.use_coupon_tv.setText("可使用"+map.get("ticket_buy_discount")+"代金券");
                    holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);

                }

                break;
        }

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
        return list.size();
    }

    class MellGoodsViewHolder extends RecyclerView.ViewHolder {
        /**
         * 广告
         */
        @ViewInject(R.id.image_for_mell)
        private ShapedImageView image_for_mell;


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

        /**
         * 国旗
         */
        @ViewInject(R.id.logo_for_country_iv)
        private ImageView logo_for_country_iv;

        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;

        MellGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }

}
