package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.txd.hzj.wjlp.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * by Txunda_LH on 2018/1/18.
 */

public class TerraceTicketCheckAty extends BaseAty {

    private TextView tv_title;
    private TabLayout tablayout;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_terraceticketcheck);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        tv_title.setText("平台代金券审核");
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(TerraceTicketCheckAty.this, tab.getPosition() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_ticket, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (position) {
                case 0:
                    holder.tv_tag.setText("审核中");
                    holder.tv_button.setVisibility(View.GONE);
                    holder.tv_textview.setVisibility(View.VISIBLE);
                    holder.tv_textview.setText("正在审核中，请耐心等待~");
                    break;
                case 1:
                    holder.tv_tag.setText("已通过");
                    holder.tv_button.setVisibility(View.GONE);
                    holder.tv_textview.setVisibility(View.VISIBLE);
                    holder.tv_textview.setText("已通过审核！");
                    break;
                case 2:
                    holder.tv_tag.setText("未通过");
                    holder.tv_button.setVisibility(View.VISIBLE);
                    holder.tv_textview.setVisibility(View.GONE);
                    break;
                default:
                    holder.tv_tag.setText("已通过");
                    holder.tv_button.setVisibility(View.GONE);
                    holder.tv_textview.setVisibility(View.VISIBLE);
                    holder.tv_textview.setText("已通过审核！");
                    break;
            }


            holder.tv_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(TerraceTicketCheckAty.this, DetailsAty.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_tag;
            TextView tv_textview;
            TextView tv_button;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_tag = (TextView) itemView.findViewById(R.id.tag);
                tv_textview = (TextView) itemView.findViewById(R.id.textview);
                tv_button = (TextView) itemView.findViewById(R.id.button);
            }
        }
    }
}
