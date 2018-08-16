package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.bean.offline.TicketBean;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.RatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：11:42
 * 描述：线上商城附近店铺列表适配器
 */

public class MellNearByHzjAdapter extends BaseAdapter {

    private Context context;
    private List<OffLineDataBean> list;
    private LayoutInflater mInflater;

    private NYVH nyvh;

    public MellNearByHzjAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public List<OffLineDataBean> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public OffLineDataBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final OffLineDataBean offLineDataBean = getItem(position);
        if (null == view) {
            view = mInflater.inflate(R.layout.item_mell_near_by_lv, viewGroup, false);
            nyvh = new NYVH();
            ViewUtils.inject(nyvh, view);
            view.setTag(nyvh);
        } else {
            nyvh = (NYVH) view.getTag();
        }
        if ("0".equals(offLineDataBean.getUser_id())) {
            nyvh.vertical_line.setVisibility(View.GONE);
            nyvh.mell_sell_num.setVisibility(View.GONE);
            nyvh.voucher_layout.setVisibility(View.GONE);
        } else {
            nyvh.vertical_line.setVisibility(View.VISIBLE);
            nyvh.mell_sell_num.setVisibility(View.VISIBLE);
            nyvh.voucher_layout.setVisibility(View.VISIBLE);
        }
        int width = (int) (Resources.getSystem().getDisplayMetrics().density * 67);
        GlideUtils.urlRoundPic(offLineDataBean.getLogo(), width, width, nyvh.mell_img, 2);
        nyvh.mell_intro.setText(offLineDataBean.getMerchant_desc());
        nyvh.mell_name.setText(offLineDataBean.getMerchant_name());
        nyvh.mell_score_rating_bar.setStar(Float.parseFloat(offLineDataBean.getScore()));
        nyvh.mell_sell_num.setText("月售" + offLineDataBean.getMonths_order() + "单");
        if (!TextUtils.isEmpty(offLineDataBean.getDistance()) && !"-1".equals(offLineDataBean.getDistance())) {
            nyvh.delivery_status_tv.setVisibility(View.GONE);
            ChangeTextViewStyle.getInstance().forTextColor(context, nyvh.distance_for_mell_tv,
                    "距您" + offLineDataBean.getDistance() + "km", 2, offLineDataBean.getDistance().length() + 2, ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            nyvh.delivery_status_tv.setVisibility(View.VISIBLE);
        }
        //        nyvh.mell_goods_gv.setAdapter(new GoodsAdapter(list2));
        // 是否有更多优惠
        List<TicketBean> ticketBeans = offLineDataBean.getTicket();
        if (null == nyvh.djpLayout.getTag()) {
            nyvh.djpLayout.setTag(position);
            nyvh.other_zk_layout.setTag(position);
            if (null != ticketBeans) {
                if (ticketBeans.size() > 1) {//都可见
                    nyvh.djpLayout.setVisibility(View.VISIBLE);
                    nyvh.show_or_hind_layout_iv.setVisibility(View.VISIBLE);

                    for (int i = 0; i < offLineDataBean.getTicket().size(); i++) {
                        TicketBean ticketBean = offLineDataBean.getTicket().get(i);
                        if (i < 1) {
                            addView(nyvh.djpLayout, ticketBean, false);
                        } else {
                            addView(nyvh.other_zk_layout, ticketBean, true);
                        }
                    }
                } else if (ticketBeans.size() == 1) {//只有第一行可见
                    nyvh.show_or_hind_layout_iv.setVisibility(View.GONE);
                    nyvh.other_zk_layout.setVisibility(View.GONE);
                    TicketBean ticketBean = offLineDataBean.getTicket().get(0);
                    addView(nyvh.djpLayout, ticketBean, false);
                } else {
                    nyvh.vertical_line.setVisibility(View.GONE);
                    nyvh.mell_sell_num.setVisibility(View.GONE);
                    nyvh.voucher_layout.setVisibility(View.GONE);
                }
            } else {// 都不可见
                nyvh.djpLayout.setVisibility(View.GONE);
                nyvh.show_or_hind_layout_iv.setVisibility(View.GONE);
                nyvh.other_zk_layout.setVisibility(View.GONE);
            }
        }
        if (null != ticketBeans && ticketBeans.size() > 1) {
            if (offLineDataBean.isShow()) {
                nyvh.other_zk_layout.setVisibility(View.VISIBLE);
                nyvh.show_or_hind_layout_iv.setImageResource(R.drawable.icon_show_other_layout);
            } else {
                nyvh.other_zk_layout.setVisibility(View.GONE);
                nyvh.show_or_hind_layout_iv.setImageResource(R.drawable.icon_hide_other_layout);
            }
        }
        // 点击事件
        nyvh.show_or_hind_layout_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offLineDataBean.isShow()) {
                    offLineDataBean.setShow(false);
                } else {
                    offLineDataBean.setShow(true);
                }
                notifyDataSetChanged();
            }
        });

        //        if (i % 3 == 0) {
        //            nyvh.delivery_status_tv.setText("达达专送-54分钟");
        //            nyvh.delivery_status_tv.setTextColor(Color.parseColor("#37A5FA"));
        //            nyvh.delivery_status_tv.setBackgroundResource(R.drawable.shape_off_line_hzj_tv);
        //        } else if (i % 2 == 0) {
        //            nyvh.delivery_status_tv.setText("商家自送-54分钟");
        //            nyvh.delivery_status_tv.setTextColor(Color.parseColor("#9E9E9E"));
        //            nyvh.delivery_status_tv.setBackgroundResource(R.drawable.shape_off_line_hzj2_tv);
        //        } else {
        //            nyvh.delivery_status_tv.setText("蜂鸟专送-54分钟");
        //            nyvh.delivery_status_tv.setTextColor(Color.parseColor("#FFB80F"));
        //            nyvh.delivery_status_tv.setBackgroundResource(R.drawable.shape_off_line_hzj3_tv);
        //        }


        return view;
    }

    private void addView(LinearLayout linearLayout, TicketBean ticketBean, boolean addLine) {
        if (addLine) {
            View view = new View(context);
            int line = (int) (Resources.getSystem().getDisplayMetrics().density * 7);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, line);
            view.setLayoutParams(params);
            linearLayout.addView(view);
        }
        View djqItem = LayoutInflater.from(context).inflate(R.layout.item_djq, linearLayout, false);
        TextView yhTv = djqItem.findViewById(R.id.yhTv);
        yhTv.setText(ticketBean.getDiscount_desc());
        switch (ticketBean.getType()) {
            case "1": {
                djqItem.findViewById(R.id.djqTv).setBackground(context.getResources().getDrawable(R.drawable.shape_red_2));
            }
            break;
            case "2": {
                djqItem.findViewById(R.id.djqTv).setBackground(context.getResources().getDrawable(R.drawable.shape_orange_2));
            }
            break;
            case "3": {
                djqItem.findViewById(R.id.djqTv).setBackground(context.getResources().getDrawable(R.drawable.shape_blue_2));
            }
            break;
        }
        linearLayout.addView(djqItem);
    }

    private class NYVH {
        @ViewInject(R.id.mell_img)
        private ImageView mell_img;

        @ViewInject(R.id.mell_name)
        private TextView mell_name;

        @ViewInject(R.id.distance_for_mell_tv)
        private TextView distance_for_mell_tv;

        @ViewInject(R.id.mell_score_rating_bar)
        private RatingBar mell_score_rating_bar;

        @ViewInject(R.id.vertical_line)
        private View vertical_line;

        @ViewInject(R.id.mell_sell_num)
        private TextView mell_sell_num;
        @ViewInject(R.id.mell_intro)
        private TextView mell_intro;
        @ViewInject(R.id.djpLayout)
        private LinearLayout djpLayout;

        @ViewInject(R.id.voucher_layout)
        private LinearLayout voucher_layout;
        /**
         * 其他优惠折扣布局
         */
        @ViewInject(R.id.other_zk_layout)
        private LinearLayout other_zk_layout;

        /**
         * 显示或隐藏其他布局
         */
        @ViewInject(R.id.show_or_hind_layout_iv)
        private ImageView show_or_hind_layout_iv;

        /**
         * 商品配送方式
         */
        @ViewInject(R.id.delivery_status_tv)
        private TextView delivery_status_tv;

        //        /**
        //         * 商品列表
        //         */
        //        @ViewInject(R.id.mell_goods_gv)
        //        private GridViewForScrollView mell_goods_gv;
    }

    //    /**
    //     * 商品列表
    //     */
    //    private class GoodsAdapter extends BaseAdapter {
    //        private NYGVH nygvh;
    //        private List<String> goods;
    //
    //        public GoodsAdapter(List<String> goods) {
    //            this.goods = goods;
    //        }
    //
    //        @Override
    //        public int getCount() {
    //            return 4;
    //        }
    //
    //        @Override
    //        public Object getItem(int i) {
    //            return goods.get(i);
    //        }
    //
    //        @Override
    //        public long getItemId(int i) {
    //            return i;
    //        }
    //
    //        @Override
    //        public View getView(int i, View view, ViewGroup viewGroup) {
    //            if (view == null) {
    //                view = LayoutInflater.from(context).inflate(R.layout.item_mell_goods_hzj_gv, viewGroup, false);
    //                nygvh = new NYGVH();
    //                ViewUtils.inject(nygvh, view);
    //                view.setTag(nygvh);
    //            } else {
    //                nygvh = (NYGVH) view.getTag();
    //            }
    //            return view;
    //        }
    //
    //        class NYGVH {
    //
    //        }
    //    }

}
