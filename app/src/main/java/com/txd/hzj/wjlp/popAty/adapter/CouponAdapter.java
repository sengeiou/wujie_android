package com.txd.hzj.wjlp.popAty.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：19:52
 * 描述：福利社领券
 * ===============Txunda===============
 */

public class CouponAdapter extends BaseAdapter {

    private Context context;
    private CouVH couVH;

    /**
     * 数据类型
     * 0.红的
     * 1.灰的
     */
    private int type;
    /**
     * 数据来源
     * 0.卡券包，优惠券
     * 1.福利社优惠券列表
     */
    private int from = 1;

    private List<Map<String, String>> data;

    private int size = 0;

    public CouponAdapter(Context context, int type, int from, List<Map<String, String>> data) {
        this.context = context;
        this.type = type;
        this.from = from;
        this.data = data;
        size = ToolKit.dip2px(context,80);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Map<String, String> getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Map<String, String> map = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_coupon_lv, null);
            couVH = new CouVH();
            ViewUtils.inject(couVH, view);
            view.setTag(couVH);
        } else {
            couVH = (CouVH) view.getTag();
        }

        if (0 == from) {// 卡券包，优惠券
            if (0 == type) {
                couVH.coupon_bg_layout.setBackgroundResource(R.drawable.icon_valid_ticket_bg_hzj);
            } else {
                couVH.coupon_bg_layout.setBackgroundResource(R.drawable.icon_no_uses_tick_bg_hzj);
            }
            couVH.coupon_already_layout.setVisibility(View.GONE);
        } else {// 福利社优惠券
            couVH.coupon_already_layout.setVisibility(View.VISIBLE);
            if (i < 5) {
                couVH.coupon_bg_layout.setBackgroundResource(R.drawable.icon_get_coupon_bg);
                couVH.can_get_coupon_tv.setText("立即领取");
                couVH.can_get_coupon_tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else {
                couVH.coupon_bg_layout.setBackgroundResource(R.drawable.icon_get_coupon_already_bg);
                couVH.can_get_coupon_tv.setText("已领取");
                couVH.can_get_coupon_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
            }
        }

        Glide.with(context).load(map.get("picture"))
                .override(size,size).centerCrop()
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(couVH.mell_logo_cou_iv);



        couVH.mell_name_cou_tv.setText(map.get("ticket_name"));

        couVH.price_tv.setText(map.get("value"));

        // 优惠券类型
        int res = 0;
        if(0 == type){
            res = context.getResources().getIdentifier("icon_get_coupon_hzj_" + map.get("ticket_type"), "drawable",
                    context.getPackageName());
        } else {
            res = context.getResources().getIdentifier("icon_get_coupon_hzj_gray_" + map.get("ticket_type"), "drawable",
                    context.getPackageName());
        }
        couVH.coupon_type_iv.setImageResource(res);

        return view;
    }

    class CouVH {

        @ViewInject(R.id.coupon_bg_layout)
        private LinearLayout coupon_bg_layout;

        @ViewInject(R.id.coupon_already_layout)
        private LinearLayout coupon_already_layout;

        /**
         * logo
         */
        @ViewInject(R.id.mell_logo_cou_iv)
        private ImageView mell_logo_cou_iv;

        /**
         * 商家名称
         */
        @ViewInject(R.id.mell_name_cou_tv)
        private TextView mell_name_cou_tv;

        @ViewInject(R.id.price_tv)
        private TextView price_tv;

        /**
         * 满减，满折，满赠
         */
        @ViewInject(R.id.coupon_type_iv)
        private ImageView coupon_type_iv;

        /**
         * 立即领取，已领取
         */
        @ViewInject(R.id.can_get_coupon_tv)
        private TextView can_get_coupon_tv;



    }

}
