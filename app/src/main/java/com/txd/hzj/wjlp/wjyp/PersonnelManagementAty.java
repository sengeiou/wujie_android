package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/20.
 */

public class PersonnelManagementAty extends BaseAty {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private AppBarLayout app_bar_layout;
    private RecyclerView recyclerview;
    private TextView tv_add;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_personnelmanagement;
    }

    @Override
    protected void initialized() {
        toolbar =  findViewById(R.id.toolbar);
        collapsing_toolbar_layout =  findViewById(R.id.collapsing_toolbar_layout);
        app_bar_layout =  findViewById(R.id.app_bar_layout);
        recyclerview =  findViewById(R.id.recyclerview);
        tv_add = findViewById(R.id.tv_add);
        setSupportActionBar(toolbar);
        collapsing_toolbar_layout.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonnelManagementAty.this, AddRecordAty.class));
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(new MyAdapter());
    }

    @Override
    protected void requestData() {

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
                    startActivity(new Intent(PersonnelManagementAty.this, _PersonnelManagementAty.class));
                    AppManager.getInstance().addActivity(PersonnelManagementAty.this);
                }
            });
            holder.textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("type", "2");
                    intent.setClass(PersonnelManagementAty.this, RecordAty.class);
                    startActivity(intent);
                }
            });
            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PersonnelManagementAty.this,EditPresMangAty.class));
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
                tv_next =  itemView.findViewById(R.id.tv_next);
                textview =  itemView.findViewById(R.id.textview);
                tv_edit=  itemView.findViewById(R.id.tv_edit);

            }
        }
    }

}
