package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.groupbuy.PromotionBean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/31 0031
 * 时间：11:22
 * 描述：
 * ===============Txunda===============
 */

public class PromotionAdapter extends BaseAdapter {
    private Context context;

    private List<PromotionBean> list;

    private PVH pvh;

    public PromotionAdapter(Context context, List<PromotionBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PromotionBean getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PromotionBean promotionBean = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_promotion_lv_layout, null);
            pvh = new PVH();
            ViewUtils.inject(pvh, view);
            view.setTag(pvh);
        } else {
            pvh = (PVH) view.getTag();
        }
        String type = promotionBean.getType();
        int imageId = context.getResources().getIdentifier("icon_get_coupon_hzj_" + type, "drawable", context
                .getPackageName());
        pvh.pro_type_iv.setImageResource(imageId);

        pvh.pro_title_tv.setText(promotionBean.getTitle());

        return view;
    }

    private class PVH {
        /**
         * 优惠类型
         */
        @ViewInject(R.id.pro_type_iv)
        private ImageView pro_type_iv;
        /**
         * 优惠标题
         */
        @ViewInject(R.id.pro_title_tv)
        private TextView pro_title_tv;

    }
}
