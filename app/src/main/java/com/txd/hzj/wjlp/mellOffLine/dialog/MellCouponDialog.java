package com.txd.hzj.wjlp.mellOffLine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

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
            return 6;
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
            return view;
        }
    }
    class CouVH {

    }

}
