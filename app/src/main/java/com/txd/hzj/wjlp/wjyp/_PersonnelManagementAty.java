package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

/**
 * by Txunda_LH on 2018/1/20.
 */

public class _PersonnelManagementAty extends BaseAty {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private AppBarLayout app_bar_layout;
    private RecyclerView recyclerview;
    private TextView tv_add;
    private TextView textview;
    private TextView tv_edit;
    private ImageView im_close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_personnelmanagement2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        setSupportActionBar(toolbar);
        collapsing_toolbar_layout.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_add = (TextView) findViewById(R.id.tv_add);
        textview = (TextView) findViewById(R.id.textview);
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        im_close = (ImageView) findViewById(R.id.im_close);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(new MyAdapter());
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_PersonnelManagementAty.this, AddRecordAty.class));
            }
        });
        im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAllActivity();
                finish();
            }
        });
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "2");
                intent.setClass(_PersonnelManagementAty.this, RecordAty.class);
                startActivity(intent);
            }
        });
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "3");
                intent.setClass(_PersonnelManagementAty.this, RecordAty.class);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subordinate, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(_PersonnelManagementAty.this, _PersonnelManagementAty.class));
                    addActivity(_PersonnelManagementAty.this);
                }
            });

            holder.textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("type", "2");
                    intent.setClass(_PersonnelManagementAty.this, RecordAty.class);
                    startActivity(intent);
                }
            });
            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(_PersonnelManagementAty.this, EditPresMangAty.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_next;
            TextView textview;
            TextView tv_edit;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_next = (TextView) itemView.findViewById(R.id.tv_next);
                textview = (TextView) itemView.findViewById(R.id.textview);
                tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);

            }
        }
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }
}