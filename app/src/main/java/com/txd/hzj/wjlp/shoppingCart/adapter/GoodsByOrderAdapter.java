package com.txd.hzj.wjlp.shoppingCart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.ViewUtils;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：09:26
 * 描述：
 * ===============Txunda===============
 */

public class GoodsByOrderAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private GOVH govh;
    List<Map<String, String>> data;

    public GoodsByOrderAdapter(Context context,List<Map<String, String>> data) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
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
            view = inflater.inflate(R.layout.item_goods_by_order_lv, null);
            govh = new GOVH();
            ViewUtils.inject(govh, view);
            view.setTag(govh);
        } else {
            govh = (GOVH) view.getTag();
        }
        return view;
    }

    class GOVH {

    }
}
