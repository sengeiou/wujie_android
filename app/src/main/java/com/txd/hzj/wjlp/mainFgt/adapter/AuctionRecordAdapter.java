package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：下午 2:07
 * 描述：竞拍记录适配器
 * ===============Txunda===============
 */
public class AuctionRecordAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<Order> list;

    public AuctionRecordAdapter(Context context, List<Order> list, int type) {
//        List<Order> orderlist = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            if (type == list.get(i).getType()) {
//                orderlist.add(list.get(i));
//            }
//        }
//        this.list = orderlist;
//        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.auction_record_item_li, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
//        if (list.get(i).getType() == 1) {
//            holder.state.setText("正在进行 今天21:00结束");
//        } else if (list.get(i).getType() == 2) {
//            holder.state.setText("竞拍成功");
//        } else {
//            holder.state.setText("竞拍结束");
//        }
        holder.goods_for_aution_lv.setAdapter(new GAAdapter());
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.tv_state)
        public TextView state;

        @ViewInject(R.id.goods_for_aution_lv)
        private ListViewForScrollView goods_for_aution_lv;
    }

    private class GAAdapter extends BaseAdapter {

        private GAVH gavh;

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_goods_auction_lv, null);
                gavh = new GAVH();
                ViewUtils.inject(gavh, view);
                view.setTag(gavh);
            } else {
                gavh = (GAVH) view.getTag();
            }
            return view;
        }

        class GAVH {
        }

    }

}
