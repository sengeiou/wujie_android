package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class MerchantCheckAty extends BaseAty {
    private TextView tv_title;
    private TabLayout tablayout;
    private RecyclerView recyclerView;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_merchantcheck;
    }

    @Override
    protected void initialized() {
        tablayout =  findViewById(R.id.tablayout);
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("推荐商家审核");
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }

    @Override
    protected void requestData() {

    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchantchenck, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MerchantCheckAty.this, MerchantCheckDetailsAty.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_tag;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_tag =  itemView.findViewById(R.id.tv_tag);
            }
        }
    }
}
