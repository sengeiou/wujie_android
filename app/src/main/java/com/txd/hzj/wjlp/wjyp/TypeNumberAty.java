package com.txd.hzj.wjlp.wjyp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.txd.hzj.wjlp.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class TypeNumberAty extends BaseAty {

    private RecyclerView recyclerview;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_typenumber);
        tv_title = (TextView) findViewById(R.id.tv_title);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        tv_title.setText("数量类型");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typenumber, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    @Override
    protected int getStatusBarColor() {

        return 0;
    }
}
