package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/8 0008 14:04
 * 功能描述：小店上货选择分类的adapter
 * 联系方式：无
 */
public class ShopUpGoodsAdapet extends BaseAdapter{

    private List list;
    private Context context;
    private int clickTemp = 0;

    public void setseclection(int position) {
        clickTemp = position;
    }

    public ShopUpGoodsAdapet(Context context, List list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;

        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = View.inflate(context, R.layout.shop_up_goods_gr_view_item, null);
            hodler.nameItem = convertView.findViewById(R.id.name_item);
            hodler.nameItem.setText(list.get(position).toString());
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        if (clickTemp == position) {
            hodler.nameItem.setBackgroundResource(R.drawable.gr_item_back_white);

        } else {
            hodler.nameItem.setBackgroundResource(R.drawable.gr_item_back);
        }
        return convertView;
    }

    class ViewHodler {
        private TextView nameItem;
    }
}
