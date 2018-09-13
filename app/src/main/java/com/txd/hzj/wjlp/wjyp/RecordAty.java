package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class RecordAty extends BaseAty {
    private TextView tv_title;
    private ImageView im_right;
    private RecyclerView recyclerView;
    private String type;//1发放记录 2联盟商家列表 3联盟商家能编辑

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_record;
    }

    @Override
    protected void initialized() {
        type = getIntent().getStringExtra("type");
        tv_title =  findViewById(R.id.tv_title);
        im_right =  findViewById(R.id.im_right);
        recyclerView =  findViewById(R.id.recyclerView);
        tv_title.setText(type.equals("1") ? "代金券发放记录" : "联盟商家列表");
        im_right.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }

    @Override
    protected void requestData() {

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (type) {
                case "1":
                    holder.im_more.setVisibility(View.VISIBLE);
                    holder.tv_edit.setVisibility(View.GONE);
                    holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(RecordAty.this, OtherTicketAty.class));
                        }
                    });
                    break;
                case "2":
                    holder.im_more.setVisibility(View.GONE);
                    holder.tv_edit.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    holder.checkbox.setVisibility(View.VISIBLE);
                    holder.im_more.setVisibility(View.GONE);
                    holder.tv_edit.setVisibility(View.GONE);
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView im_more;
            TextView tv_edit;
            CheckBox checkbox;
            RelativeLayout relativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                im_more = (ImageView) itemView.findViewById(R.id.im_more);
                tv_edit =  itemView.findViewById(R.id.tv_edit);
                checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
            }
        }
    }
}
