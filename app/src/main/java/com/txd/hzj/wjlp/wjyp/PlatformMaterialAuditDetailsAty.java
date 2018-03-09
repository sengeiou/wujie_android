package com.txd.hzj.wjlp.wjyp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.txd.hzj.wjlp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * by Txunda_LH on 2018/1/22.
 */

public class PlatformMaterialAuditDetailsAty extends BaseAty {
    private RecyclerView recyclerview;
    private RecyclerView recyclerview2;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_platformmaterialauditdetails);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("审核详情");
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview2 = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new MyAdapter());
        recyclerview2.setNestedScrollingEnabled(false);
        recyclerview2.setLayoutManager(new LinearLayoutManager(this));
        recyclerview2.setAdapter(new MyAdapter2());


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

    class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typenumber2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 15;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);

            }
        }
    }
}
