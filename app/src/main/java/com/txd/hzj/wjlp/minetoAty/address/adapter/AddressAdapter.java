package com.txd.hzj.wjlp.minetoAty.address.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：20:26
 * 描述：地址适配器
 * ===============Txunda===============
 */

public class AddressAdapter extends BaseAdapter {

    private Context context;
    private List<String> address;
    private LayoutInflater inflater;
    private AVH avh;

    private AdapterTextViewClickListener adapterTextViewClickListener;

    public AddressAdapter(Context context, List<String> address) {
        this.context = context;
        this.address = address;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_address_hzj_lv, null);
            avh = new AVH();
            ViewUtils.inject(avh, view);
            view.setTag(avh);
        } else {
            avh = (AVH) view.getTag();
        }

        if (0 == i) {
            avh.address_status_iv.setImageResource(R.drawable.icon_default_address);
            avh.address_defailt_tv.setText("默认地址");
            avh.address_defailt_tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            avh.under_address_iv.setVisibility(View.VISIBLE);
        } else {
            avh.address_status_iv.setImageResource(R.drawable.icon_un_default_address);
            avh.address_defailt_tv.setText("设为默认");
            avh.address_defailt_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
            avh.under_address_iv.setVisibility(View.GONE);
        }
        if (adapterTextViewClickListener != null) {
            // 设置为默认地址
            avh.set_address_to_default_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterTextViewClickListener.onTextViewClick(view, i);
                }
            });
            // 编辑
            avh.edit_address_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterTextViewClickListener.onTextViewClick(view, i);
                }
            });
            // 删除
            avh.delete_address_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterTextViewClickListener.onTextViewClick(view, i);
                }
            });
        }

        return view;
    }

    public void setAdapterTextViewClickListener(AdapterTextViewClickListener adapterTextViewClickListener) {
        this.adapterTextViewClickListener = adapterTextViewClickListener;
    }

    class AVH {
        /**
         * 设置为默认地址布局
         */
        @ViewInject(R.id.set_address_to_default_layout)
        private LinearLayout set_address_to_default_layout;
        /**
         * 默认地址。非默认地址
         */
        @ViewInject(R.id.address_status_iv)
        private ImageView address_status_iv;
        /**
         * 默认地址。非默认地址
         */
        @ViewInject(R.id.address_defailt_tv)
        private TextView address_defailt_tv;
        /**
         * 编辑
         */
        @ViewInject(R.id.edit_address_tv)
        private TextView edit_address_tv;
        /**
         * 删除
         */
        @ViewInject(R.id.delete_address_tv)
        private TextView delete_address_tv;

        /**
         * 默认地址显示的线
         */
        @ViewInject(R.id.under_address_iv)
        private ImageView under_address_iv;

    }
}
