package com.txd.hzj.wjlp.wjyp;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/18.
 */

public class DetailsAty extends BaseAty {
    private RecyclerView recyclerview;
    private TextView tv_title;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_details;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("审核详情");
        recyclerview =  findViewById(R.id.recyclerview);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new MyAdapter());
    }

    @Override
    protected void requestData() {

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position == 0) {
                holder.image_ddd.setImageResource(R.mipmap.icon_timeline_true);
                holder.view1.setVisibility(View.GONE);
                holder.view2.setVisibility(View.VISIBLE);
                holder.tv_text.setTextColor(Color.parseColor("#F23030"));
                holder.tv_date.setTextColor(Color.parseColor("#F23030"));
            } else {
                holder.image_ddd.setImageResource(R.mipmap.icon_timeline_false);
                holder.view1.setVisibility(View.VISIBLE);
                holder.view2.setVisibility(View.VISIBLE);
                holder.tv_text.setTextColor(Color.parseColor("#999999"));
                holder.tv_date.setTextColor(Color.parseColor("#999999"));

            }
            if (position == getItemCount() - 1) {
                holder.view2.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image_ddd;
            View view1;
            View view2;
            TextView tv_text;
            TextView tv_date;

            public ViewHolder(View itemView) {
                super(itemView);
                image_ddd =  itemView.findViewById(R.id.image_ddd);
                view1 = itemView.findViewById(R.id.view1);
                view2 = itemView.findViewById(R.id.view2);
                tv_text =  itemView.findViewById(R.id.tv_text);
                tv_date =  itemView.findViewById(R.id.tv_date);
            }
        }
    }
}
