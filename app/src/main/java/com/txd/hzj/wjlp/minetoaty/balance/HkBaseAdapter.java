package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.ArrayList;

/**
 * by Txunda_LH on 2018/2/27.
 */
public class HkBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<HkEntity> alist;

    public HkBaseAdapter(Context context, ArrayList<HkEntity> list) {
        this.alist = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_bank_info_lv, null);
            vh.bank_card_num_tv = convertView.findViewById(R.id.bank_card_num_tv);
            vh.bank_card_owner_name = convertView.findViewById(R.id.bank_card_owner_name);
            vh.create_card_bank_name_tv = convertView.findViewById(R.id.create_card_bank_name_tv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.bank_card_num_tv.setText(alist.get(position).getBank_num());
        vh.bank_card_owner_name.setText(alist.get(position).getBank_name());
        vh.create_card_bank_name_tv.setText(alist.get(position).getOpen_bank());

        return convertView;
    }

    class ViewHolder {

        /**
         * 银行卡号
         */
        private TextView bank_card_num_tv;

        /**
         * 持卡人
         */
        private TextView bank_card_owner_name;

        /**
         * 开户行
         */
        private TextView create_card_bank_name_tv;
    }
}
