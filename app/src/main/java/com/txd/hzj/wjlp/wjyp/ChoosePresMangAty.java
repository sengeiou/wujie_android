package com.txd.hzj.wjlp.wjyp;

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
 * by Txunda_LH on 2018/1/20.
 */

public class ChoosePresMangAty extends BaseAty {
    private TextView tv_title;
    private ImageView im_right;
    private RecyclerView recyclerview;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_choosepresmang;
    }

    @Override
    protected void initialized() {
        tv_title =  findViewById(R.id.tv_title);
        tv_title.setText("选择所属拓展员");
        im_right =  findViewById(R.id.im_right);
        im_right.setVisibility(View.VISIBLE);
        recyclerview =  findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new MyAdapter());
    }

    @Override
    protected void requestData() {

    }




    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choosepresmang, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);

            }
        }
    }
}
