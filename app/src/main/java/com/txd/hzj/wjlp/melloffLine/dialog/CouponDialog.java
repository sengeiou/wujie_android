package com.txd.hzj.wjlp.melloffLine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.txd.hzj.wjlp.R;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/7/28 0028
 * 时间：17:27
 * 描述：
 *
 */

public class CouponDialog extends Dialog {


    private ListView coup_lv;
    private Context context;

    private DialogAdapter dialogAdapter;

    public CouponDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.context = context;
        dialogAdapter = new DialogAdapter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coupon_layout);
        coup_lv = (ListView) findViewById(R.id.coup_lv);
        coup_lv.setAdapter(dialogAdapter);
        findViewById(R.id.be_dimiss_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private class DialogAdapter extends BaseAdapter {

        private DCPVH dcpvh;

        @Override
        public int getCount() {
            return 3;
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
                view = LayoutInflater.from(context).inflate(R.layout.item_coupon_dialog_lv, null);
                dcpvh = new DCPVH();
                ViewUtils.inject(dcpvh, view);
                view.setTag(dcpvh);
            } else {
                dcpvh = (DCPVH) view.getTag();
            }

            return view;
        }

        class DCPVH {

        }
    }

}
