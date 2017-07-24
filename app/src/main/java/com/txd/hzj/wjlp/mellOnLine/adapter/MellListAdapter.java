package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.mellOnLine.MellListAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：11:17
 * 描述：
 * ===============Txunda===============
 */

public class MellListAdapter extends BaseAdapter {

    private Context context;
    private List<String> mells;

    public MellListAdapter(Context context, List<String> mells) {
        this.context = context;
        this.mells = mells;
    }

    private MellViewHolder mvh;
    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int i) {
        return mells.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_mell_lv, viewGroup, false);
            mvh = new MellViewHolder();
            ViewUtils.inject(mvh, view);
            view.setTag(mvh);
        } else {
            mvh = (MellViewHolder) view.getTag();
        }

        mvh.mell_prodect_gv.setAdapter(new MellProdectAdapter(mells));

        return view;
    }

    private class MellViewHolder {
        @ViewInject(R.id.mell_prodect_gv)
        private GridViewForScrollView mell_prodect_gv;
    }


    private class MellProdectAdapter extends BaseAdapter {
        private MPViewHolder mpvh;

        private List<String> prodect;

        public MellProdectAdapter(List<String> prodect) {
            this.prodect = prodect;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int i) {
            return prodect.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_mell_prodect_gv, viewGroup, false);
                mpvh = new MPViewHolder();
                ViewUtils.inject(mpvh, view);
                view.setTag(mpvh);
            } else {
                mpvh = (MPViewHolder) view.getTag();
            }
            ChangeTextViewStyle.getInstance().forMellProdect(context, mpvh.mell_prodect_price_tv, "￥12.00");
            return view;
        }

        class MPViewHolder {

            @ViewInject(R.id.mell_prodect_price_tv)
            private TextView mell_prodect_price_tv;

        }

    }
}
