package com.txd.hzj.wjlp.shoppingCart.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.new_wjyp.InvoiceAty;

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

    public GoodsByOrderAdapter(Context context, List<Map<String, String>> data) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map<String, String> getItem(int i) {
        return data.get(i);
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
        Glide.with(context).load(getItem(i).get("goods_img")).into(govh.goods_comment_pic);
        govh.tv_number.setText("x" + getItem(i).get("num"));
        govh.goods_title_for_evaluate_tv.setText(getItem(i).get("goods_name"));

        if (!TextUtils.isEmpty(getItem(i).get("goods_attr_first"))) {
            govh.price_for_goods_tv.setText("规格：" + getItem(i).get("goods_attr_first") + "\n¥" + getItem(i).get("shop_price"));
        } else {
            govh.price_for_goods_tv.setText("¥" + getItem(i).get("shop_price"));
        }
        if (getItem(i).get("invoice_status").equals("1")) {
            govh.layout.setVisibility(View.VISIBLE);
        } else {
            govh.layout.setVisibility(View.GONE);
        }
        govh.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                InvoiceAty.class
            }
        });
        return view;
    }

    class GOVH {
        @ViewInject(R.id.goods_comment_pic)
        private ImageView goods_comment_pic;
        @ViewInject(R.id.goods_title_for_evaluate_tv)
        private TextView goods_title_for_evaluate_tv;
        @ViewInject(R.id.price_for_goods_tv)
        private TextView price_for_goods_tv;
        @ViewInject(R.id.tv_number)
        private TextView tv_number;
        @ViewInject(R.id.layout)
        private LinearLayout layout;
    }
}
