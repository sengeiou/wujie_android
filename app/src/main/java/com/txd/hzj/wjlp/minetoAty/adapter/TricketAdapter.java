package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.TricketDetailks;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.RvAdapter;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.RvHolder;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.RvListener;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：15:58
 * 描述：购物券使用明细适配器
 * ===============Txunda===============
 */

public class TricketAdapter extends RvAdapter<TricketDetailks> {

    public TricketAdapter(Context context, List<TricketDetailks> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.item_title : R.layout.item_tricket_rv;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new TrickViewHolder(view, viewType, listener);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    public class TrickViewHolder extends RvHolder<TricketDetailks> {

        private TextView tvTitle;

        private ImageView t_details_logo_tv;

        private TextView t_details_title_tv;

        private TextView t_details_time_tv;

        private TextView t_details_price_tv;

        private View view_for_rv;

        public TrickViewHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            if (type == 0) {
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            } else {
                t_details_logo_tv = itemView.findViewById(R.id.t_details_logo_tv);
                t_details_title_tv = itemView.findViewById(R.id.t_details_title_tv);
                t_details_time_tv = itemView.findViewById(R.id.t_details_time_tv);
                t_details_price_tv = itemView.findViewById(R.id.t_details_price_tv);
                view_for_rv = itemView.findViewById(R.id.view_for_rv);
            }
        }

        @Override
        public void bindHolder(TricketDetailks tricketDetailks, int position) {
            int type = TricketAdapter.this.getItemViewType(position);
            switch (type) {
                case 0:
                    tvTitle.setText(list.get(position).getName());
                    tvTitle.setBackgroundColor(Color.parseColor("#E2E2E2"));
                    break;
                case 1:
                    t_details_title_tv.setText(list.get(position).getName());
                    t_details_time_tv.setText(list.get(position).getTime());
                    t_details_price_tv.setText("+" + list.get(position).getPrice());
                    break;
            }
        }
    }

}
