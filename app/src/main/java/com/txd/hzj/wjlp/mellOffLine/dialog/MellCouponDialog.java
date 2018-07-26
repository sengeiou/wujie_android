package com.txd.hzj.wjlp.mellOffLine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;
import com.txd.hzj.wjlp.view.ArcProgress;

import java.util.List;
import java.util.Map;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/7/29 0029
 * 时间：11:13
 * 描述：
 *
 */

public class MellCouponDialog extends Dialog implements AdapterView.OnItemClickListener {
    private ListView mell_coup_lv;
    private CouDialogAdapter couponAdapter;

    private Context context;

    private List<Map<String, String>> list;

    private ItemClick itemClick;

    private int mell_logo_size = 0;

    public MellCouponDialog(@NonNull Context context, List<Map<String, String>> list, ItemClick itemClick) {
        super(context, R.style.dialog_style);
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
        mell_logo_size = ToolKit.dip2px(context, 50);
        couponAdapter = new CouDialogAdapter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mell_coupon_layout);
        mell_coup_lv = findViewById(R.id.mell_coup_lv);
        mell_coup_lv.setAdapter(couponAdapter);
        findViewById(R.id.be_dimiss_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (itemClick != null)
            itemClick.onItemClick(adapterView, view, i, l);
    }

    public interface ItemClick {
        void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }

    private class CouDialogAdapter extends BaseAdapter {

        private CouVH couVH;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_dialog_mell_coup_lv, null);
                couVH = new CouVH();
                ViewUtils.inject(couVH, view);
                view.setTag(couVH);
            } else {
                couVH = (CouVH) view.getTag();
            }

            if (map.get("is_get").equals("0")) {
                couVH.mell_coupon_layout.setBackgroundResource(R.drawable.icon_get_coupon_bg);
                couVH.get_already_tv.setText("立即领取");
                couVH.get_already_tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else {
                couVH.mell_coupon_layout.setBackgroundResource(R.drawable.icon_get_coupon_already_bg);
                couVH.get_already_tv.setText("已领取");
                couVH.get_already_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
            }

            Glide.with(context).load(map.get("logo"))
                    .override(mell_logo_size, mell_logo_size).centerCrop()
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(couVH.mell_logo_cou_iv);

            int ticket_num;
            try {
                ticket_num = Integer.parseInt(map.get("ticket_num"));
            } catch (NumberFormatException e) {
                ticket_num = 100;
            }
            int use_num;
            try {
                use_num = Integer.parseInt(map.get("use_num"));
            } catch (NumberFormatException e) {
                use_num = 0;
            }
            int pro;
            if (use_num >= ticket_num) {
                pro = 100;
            } else {
                pro = use_num * 100 / ticket_num;
            }
            couVH.cou_progress.setProgress(pro);

            if (map.get("ticket_type").equals("1")) {
                couVH.begin_tv.setText("￥");
                couVH.begin_tv.setVisibility(View.VISIBLE);
                couVH.condition_tv.setText("满" + map.get("condition") + "减" + map.get("value"));
            } else if (map.get("ticket_type").equals("2")) {
                couVH.begin_tv.setText("折扣");
                couVH.begin_tv.setVisibility(View.VISIBLE);
                couVH.condition_tv.setText("满" + map.get("condition") + "折" + map.get("value"));
            } else if (map.get("ticket_type").equals("2")) {
                couVH.begin_tv.setVisibility(View.GONE);
                couVH.condition_tv.setText("满" + map.get("condition") + "赠送商品");
            }

            couVH.mell_name_cou_tv.setText(map.get("ticket_name"));

            couVH.price_tv.setText(map.get("value"));

            // 优惠券类型
            int res;
            res = context.getResources().getIdentifier("icon_get_coupon_hzj_" + map.get("ticket_type"), "drawable",
                    context.getPackageName());
            couVH.coupon_type_iv.setImageResource(res);

            return view;
        }
    }

    class CouVH {

        /**
         * 背景
         */
        @ViewInject(R.id.mell_coupon_layout)
        private LinearLayout mell_coupon_layout;

        /**
         * 立即获取，已获取
         */
        @ViewInject(R.id.get_already_tv)
        private TextView get_already_tv;

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

        /**
         * 价格
         */
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
        /**
         * 满足条件
         */
        @ViewInject(R.id.condition_tv)
        private TextView condition_tv;

        /**
         * 领取进度
         */
        @ViewInject(R.id.cou_progress)
        private ArcProgress cou_progress;

        @ViewInject(R.id.begin_tv)
        private TextView begin_tv;

    }

}
