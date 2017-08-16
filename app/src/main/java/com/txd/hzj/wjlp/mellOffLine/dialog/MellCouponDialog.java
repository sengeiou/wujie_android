package com.txd.hzj.wjlp.mellOffLine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/29 0029
 * 时间：11:13
 * 描述：
 * ===============Txunda===============
 */

public class MellCouponDialog extends Dialog {
    private ListView mell_coup_lv;
    private CouDialogAdapter couponAdapter;

    private Context context;

    public MellCouponDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.context = context;
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

    private class CouDialogAdapter extends BaseAdapter{

        private CouVH couVH;

        @Override
        public int getCount() {
            return 10;
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
                view = LayoutInflater.from(context).inflate(R.layout.item_dialog_mell_coup_lv, null);
                couVH = new CouVH();
                ViewUtils.inject(couVH, view);
                view.setTag(couVH);
            } else {
                couVH = (CouVH) view.getTag();
            }

            if (i < 5) {
                couVH.mell_coupon_layout.setBackgroundResource(R.drawable.icon_get_coupon_bg);
                couVH.get_already_tv.setText("立即领取");
                couVH.get_already_tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else {
                couVH.mell_coupon_layout.setBackgroundResource(R.drawable.icon_get_coupon_already_bg);
                couVH.get_already_tv.setText("已领取");
                couVH.get_already_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
            }

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

    }

}
