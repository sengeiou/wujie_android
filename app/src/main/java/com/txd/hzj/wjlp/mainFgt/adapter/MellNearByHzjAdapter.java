package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
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
    private List<String> list;
    private List<String> list2;
    private LayoutInflater mInflater;

    private NYVH nyvh;

    public MellNearByHzjAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

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
        return view;
    }

    private class NYVH {
        @ViewInject(R.id.distance_for_mell_tv)
        private TextView distance_for_mell_tv;

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
