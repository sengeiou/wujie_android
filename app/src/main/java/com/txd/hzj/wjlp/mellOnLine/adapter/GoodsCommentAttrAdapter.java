package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.GoodsCommonAttr;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：10:10
 * 描述：产品属性列表适配器
 * ===============Txunda===============
 */

public class GoodsCommentAttrAdapter extends BaseAdapter {

    private Context context;

    private List<GoodsCommonAttr> commonAttrs;

    private LayoutInflater inflater;

    private CommentAttrVH cavh;

    public GoodsCommentAttrAdapter(Context context, List<GoodsCommonAttr> commonAttrs) {
        this.context = context;
        this.commonAttrs = commonAttrs;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return commonAttrs.size();
    }

    @Override
    public GoodsCommonAttr getItem(int i) {
        return commonAttrs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GoodsCommonAttr goodsCommonAttr = getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.item_goods_common_attr_lv, viewGroup, false);
            cavh = new CommentAttrVH();
            ViewUtils.inject(cavh, view);
            view.setTag(cavh);
        } else {
            cavh = (CommentAttrVH) view.getTag();
        }

        cavh.attr_name_tv.setText(goodsCommonAttr.getAttr_name());
        cavh.attr_value_tv.setText(goodsCommonAttr.getAttr_value());

        return view;
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

}
