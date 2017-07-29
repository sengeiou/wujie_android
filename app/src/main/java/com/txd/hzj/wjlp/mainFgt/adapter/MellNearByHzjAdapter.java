package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Mell;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：11:42
 * 描述：线上商城附近店铺列表适配器
 * ===============Txunda===============
 */

public class MellNearByHzjAdapter extends BaseAdapter {

    private Context context;
    private List<Mell> list;
    private List<String> list2;
    private LayoutInflater mInflater;

    private NYVH nyvh;

    public MellNearByHzjAdapter(Context context, List<Mell> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Mell getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Mell mell = getItem(i);
        if (null == view) {
            view = mInflater.inflate(R.layout.item_mell_near_by_lv, viewGroup, false);
            nyvh = new NYVH();
            ViewUtils.inject(nyvh, view);
            view.setTag(nyvh);
        } else {
            nyvh = (NYVH) view.getTag();
        }
        ChangeTextViewStyle.getInstance().forTextColor(context, nyvh.distance_for_mell_tv, "距您4.5km", 2, 5,
                ContextCompat.getColor(context, R.color.colorAccent));
        list2 = new ArrayList<>();
        nyvh.mell_goods_gv.setAdapter(new GoodsAdapter(list2));
        // 是否有更多优惠
        if (mell.isMoreDiscount()) {
            nyvh.show_or_hind_layout_iv.setVisibility(View.VISIBLE);
        } else {
            nyvh.show_or_hind_layout_iv.setVisibility(View.GONE);
        }

        if(mell.isShow()){
            nyvh.other_zk_layout.setVisibility(View.VISIBLE);
            nyvh.show_or_hind_layout_iv.setImageResource(R.drawable.icon_show_other_layout);
        } else{
            nyvh.other_zk_layout.setVisibility(View.GONE);
            nyvh.show_or_hind_layout_iv.setImageResource(R.drawable.icon_hide_other_layout);
        }

        // 点击事件
        nyvh.show_or_hind_layout_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mell.isShow()){
                    mell.setShow(false);
                } else {
                    mell.setShow(true);
                }
                notifyDataSetChanged();
            }
        });

        if (i % 3 == 0) {
            nyvh.delivery_status_tv.setText("达达专送-54分钟");
            nyvh.delivery_status_tv.setTextColor(Color.parseColor("#37A5FA"));
            nyvh.delivery_status_tv.setBackgroundResource(R.drawable.shape_off_line_hzj_tv);
        } else if (i % 2 == 0) {
            nyvh.delivery_status_tv.setText("商家自送-54分钟");
            nyvh.delivery_status_tv.setTextColor(Color.parseColor("#9E9E9E"));
            nyvh.delivery_status_tv.setBackgroundResource(R.drawable.shape_off_line_hzj2_tv);
        } else {
            nyvh.delivery_status_tv.setText("蜂鸟专送-54分钟");
            nyvh.delivery_status_tv.setTextColor(Color.parseColor("#FFB80F"));
            nyvh.delivery_status_tv.setBackgroundResource(R.drawable.shape_off_line_hzj3_tv);
        }


        return view;
    }

    private class NYVH {
        @ViewInject(R.id.distance_for_mell_tv)
        private TextView distance_for_mell_tv;

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

        /**
         * 商品列表
         */
        @ViewInject(R.id.mell_goods_gv)
        private GridViewForScrollView mell_goods_gv;
    }

    /**
     * 商品列表
     */
    private class GoodsAdapter extends BaseAdapter {
        private NYGVH nygvh;
        private List<String> goods;

        public GoodsAdapter(List<String> goods) {
            this.goods = goods;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int i) {
            return goods.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_mell_goods_hzj_gv, viewGroup, false);
                nygvh = new NYGVH();
                ViewUtils.inject(nygvh, view);
                view.setTag(nygvh);
            } else {
                nygvh = (NYGVH) view.getTag();
            }
            return view;
        }

        class NYGVH {

        }
    }

}
