package com.txd.hzj.wjlp.wjyp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.txd.hzj.wjlp.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class MerchantCheckDetailsAty extends BaseAty {
    private TextView tv_title;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_merchantcheckdetails);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("审核详情");
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new MyAdapter());
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
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
                image_ddd = (ImageView) itemView.findViewById(R.id.image_ddd);
                view1 = itemView.findViewById(R.id.view1);
                view2 = itemView.findViewById(R.id.view2);
                tv_text = (TextView) itemView.findViewById(R.id.tv_text);
                tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            }
        }
    }
}
