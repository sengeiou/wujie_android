package com.txd.hzj.wjlp.minetoAty.mell;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/1 0001
 * 时间：下午 1:40
 * 描述：进货记录
 */
public class StockRecordAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.stork_record_lv)
    private ListView stork_record_lv;

    private StockRecordAdapter srAdapter;

    private int from = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);

        if ((1 == from)) {
            titlt_conter_tv.setText("进货记录");
        }

        stork_record_lv.setAdapter(srAdapter);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_stock_record;
    }

    @Override
    protected void initialized() {
        srAdapter = new StockRecordAdapter();
        from = getIntent().getIntExtra("from", 1);
    }

    @Override
    protected void requestData() {

    }

    private class StockRecordAdapter extends BaseAdapter {

        private SRViewHolder srvh;

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
                view = LayoutInflater.from(StockRecordAty.this).inflate(R.layout.item_stock_record_lv, null);
                srvh = new SRViewHolder();
                ViewUtils.inject(srvh, view);
                view.setTag(srvh);
            } else {
                srvh = (SRViewHolder) view.getTag();
            }

            if (1 == from) {
                srvh.goods_time_tv.setTextColor(ContextCompat.getColor(StockRecordAty.this, R.color.colorAccent));
            } else {
                srvh.goods_time_tv.setTextColor(ContextCompat.getColor(StockRecordAty.this, R.color.app_text_color));
            }

            return view;
        }

        private class SRViewHolder {

            @ViewInject(R.id.goods_time_tv)
            private TextView goods_time_tv;

        }

    }

}
