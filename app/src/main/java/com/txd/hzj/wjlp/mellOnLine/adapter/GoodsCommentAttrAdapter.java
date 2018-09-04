package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.GoodsCommonAttrBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsCommonAttrItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：10:10
 * 描述：产品属性列表适配器
 *
 */

public class GoodsCommentAttrAdapter extends BaseAdapter {

    private Context context;

    private List<GoodsCommonAttrBean> commonAttrs;

    private LayoutInflater inflater;

    private Holder cavh;

    public GoodsCommentAttrAdapter(Context context, List<GoodsCommonAttrBean> commonAttrs) {
        this.context = context;
        this.commonAttrs = commonAttrs;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return commonAttrs.size();
    }

    @Override
    public GoodsCommonAttrBean getItem(int i) {
        return commonAttrs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GoodsCommonAttrBean goodsCommonAttr = getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.item_attrs, viewGroup, false);
            cavh = new Holder();
            ViewUtils.inject(cavh, view);
            view.setTag(cavh);
        } else {
            cavh = (Holder) view.getTag();
        }
        cavh.tv.setText(goodsCommonAttr.getTitle());
        List<GoodsCommonAttrItemBean> list = goodsCommonAttr.getList();
        List<GoodsCommonAttrItemBean> goodList=new ArrayList<>();
        for (int k = 0; k < list.size(); k++) {
            String attr_name = list.get(k).getAttr_name();
            String attr_value = list.get(k).getAttr_value();
            if (!TextUtils.isEmpty(attr_name) && !TextUtils.isEmpty(attr_value)){
                goodList.add(list.get(k));
            }
        }
        MyAdapter adp = new MyAdapter(context, goodList);
        cavh.goods_common_attr_lv.setAdapter(adp);
        return view;
    }

    private class Holder {
        @ViewInject(R.id.title)
        TextView tv;
        @ViewInject(R.id.goods_common_attr_lv)
        private ListViewForScrollView goods_common_attr_lv;

    }

    private class CommentAttrVH {
        /**
         * 属性名称
         */
        @ViewInject(R.id.attr_name_tv)
        private TextView attr_name_tv;
        /**
         * 属性值
         */
        @ViewInject(R.id.attr_value_tv)
        private TextView attr_value_tv;

    }

    class MyAdapter extends BaseAdapter {

        List<GoodsCommonAttrItemBean> list;
        private LayoutInflater inflater;
        CommentAttrVH cavh;

        public MyAdapter(Context context, List<GoodsCommonAttrItemBean> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list!=null && list.size()>0?list.size():0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            if (view == null) {
                view = inflater.inflate(R.layout.item_goods_common_attr_lv, parent, false);
                cavh = new CommentAttrVH();
                ViewUtils.inject(cavh, view);
                view.setTag(cavh);
            } else {
                cavh = (CommentAttrVH) view.getTag();
            }

                cavh.attr_name_tv.setText(list.get(i).getAttr_name());
                cavh.attr_value_tv.setText(list.get(i).getAttr_value());

            return view;
        }
    }


}
