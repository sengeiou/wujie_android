package com.txd.hzj.wjlp.wjyp;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * by Txunda_LH on 2018/1/19.
 */

public class MyTicketAty extends BaseAty {
    private TabLayout tablayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private AppBarLayout app_bar_layout;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_myticket;
    }

    @Override
    protected void initialized() {
        toolbar =  findViewById(R.id.toolbar);
        collapsing_toolbar_layout =  findViewById(R.id.collapsing_toolbar_layout);
        app_bar_layout =  findViewById(R.id.app_bar_layout);

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
        tablayout =  findViewById(R.id.tablayout);
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    myAdapter.setData(true);
                } else {
                    myAdapter.setData(false);
                }
                Toast.makeText(MyTicketAty.this, tab.getPosition() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void requestData() {

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private boolean type = true;

        public void setData(boolean type) {
            this.type = type;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (type) {
                holder.bg_layout.setBackgroundResource(R.drawable.shape_my_ticket1);
            } else {
                holder.bg_layout.setBackgroundResource(R.drawable.shape_my_ticket2);
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout bg_layout;

            public ViewHolder(View itemView) {
                super(itemView);
                bg_layout =  itemView.findViewById(R.id.bg_layout);
            }
        }
    }
}
