package com.txd.hzj.wjlp.wjyp;

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

public class ReleaseRecordDetailsAty extends BaseAty {
    private TextView tv_title;
    private RecyclerView recyclerView;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_releaserecorddetails;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("详情");
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }

    @Override
    protected void requestData() {

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_releaserecorddetails, parent, false);
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
