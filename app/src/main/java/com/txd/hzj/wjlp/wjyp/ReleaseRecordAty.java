package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/22.
 */

public class ReleaseRecordAty extends BaseAty {
    private RecyclerView recyclerview;
    private TextView tv_title;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_releaserecord;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("发放记录");
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new MyAdapter());
    }

    @Override
    protected void requestData() {

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_releaserecord, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ReleaseRecordAty.this, ReleaseRecordDetailsAty.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textview;

            public ViewHolder(View itemView) {
                super(itemView);
                textview =  itemView.findViewById(R.id.textview);
            }
        }

    }

}
