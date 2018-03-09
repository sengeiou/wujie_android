package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.txd.hzj.wjlp.R;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * by Txunda_LH on 2018/1/20.
 */

public class OtherTicketAty extends BaseAty {
    private TabLayout tablayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private AppBarLayout app_bar_layout;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private TextView tv_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_otherticket);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        setSupportActionBar(toolbar);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherTicketAty.this, ReleaseRecordAty.class));
            }
        });
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
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myAdapter.setData(String.valueOf(tab.getPosition()));
                Toast.makeText(OtherTicketAty.this, tab.getPosition() + "", Toast.LENGTH_SHORT).show();
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
    protected int getStatusBarColor() {
        return 0;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String type = "0";

        public void setData(String type) {
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
            switch (type) {
                case "0":
                    holder.bg_layout.setBackgroundResource(R.drawable.shape_my_ticket1);
                    holder.im_head.setImageResource(R.mipmap.icon_myticket1);
                    break;
                case "1":
                    holder.bg_layout.setBackgroundResource(R.drawable.shape_my_ticket2);
                    holder.im_head.setImageResource(R.mipmap.icon_myticket1);
                    break;
                case "2":
                    holder.bg_layout.setBackgroundResource(R.drawable.shape_my_ticket3);
                    holder.im_head.setImageResource(R.mipmap.icon_myticket2);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout bg_layout;
            ImageView im_head;

            public ViewHolder(View itemView) {
                super(itemView);
                bg_layout = (LinearLayout) itemView.findViewById(R.id.bg_layout);
                im_head = (ImageView) itemView.findViewById(R.id.im_head);
            }
        }
    }
}
