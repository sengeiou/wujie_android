package com.txd.hzj.wjlp.mellOffLine.fgt.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

import java.util.List;

public class SortAdapter extends RvAdapter<String> {

    private Context context;

    private int checkedPosition;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public SortAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);
        this.context = context;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_sort_list;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view, viewType, listener);
    }

    private class SortHolder extends RvHolder<String> {

        private TextView tvName;
        private View mView;
        private View left_view;

        SortHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            this.mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_sort);
            left_view = (View) itemView.findViewById(R.id.left_view);
        }

        @Override
        public void bindHolder(String string, int position) {
            tvName.setText(string);
            if (position == checkedPosition) {
                mView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                left_view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                tvName.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else {
                mView.setBackgroundColor(Color.parseColor("#EEEEEE"));
                left_view.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                tvName.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
            }
        }

    }
}
