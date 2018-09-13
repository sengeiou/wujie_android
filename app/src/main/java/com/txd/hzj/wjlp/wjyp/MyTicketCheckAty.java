package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class MyTicketCheckAty extends BaseAty {
    private TextView tv_title;
    private TabLayout tablayout;
    private RecyclerView recyclerview;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_myticketcheck;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        recyclerview =  findViewById(R.id.recyclerview);
        tablayout =  findViewById(R.id.tablayout);
        tv_title.setText("代金券审核");
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(MyTicketCheckAty.this, tab.getPosition() + "", Toast.LENGTH_SHORT).show();
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
    protected void requestData() {

    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_ticket, parent, false);
            MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            switch (position) {
                case 0:
                    holder.tv_tag.setText("待审核");
                    holder.tv_button.setVisibility(View.VISIBLE);
                    holder.tv_button1.setVisibility(View.VISIBLE);
                    holder.tv_textview.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.tv_tag.setText("已通过");
                    holder.tv_button.setVisibility(View.GONE);
                    holder.tv_button1.setVisibility(View.GONE);
                    holder.tv_textview.setVisibility(View.VISIBLE);
                    holder.tv_textview.setText("已通过审核！");
                    break;
                case 2:
                    holder.tv_tag.setText("未通过");
                    holder.tv_button.setVisibility(View.VISIBLE);
                    holder.tv_button1.setVisibility(View.VISIBLE);
                    holder.tv_textview.setVisibility(View.VISIBLE);
                    holder.tv_textview.setText("没有通过审核！");
                    break;
                default:
                    holder.tv_tag.setText("已通过");
                    holder.tv_button.setVisibility(View.GONE);
                    holder.tv_button1.setVisibility(View.GONE);
                    holder.tv_textview.setVisibility(View.VISIBLE);
                    holder.tv_textview.setText("已通过审核！");
                    break;
            }


            holder.tv_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyTicketCheckAty.this, DetailsAty.class));
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
            TextView tv_button1;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_tag =  itemView.findViewById(R.id.tag);
                tv_textview =  itemView.findViewById(R.id.textview);
                tv_button =  itemView.findViewById(R.id.button);
                tv_button1 =  itemView.findViewById(R.id.button1);
            }
        }
    }
}
