package com.txd.hzj.wjlp.new_wjyp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.new_wjyp.http.AfterSale;

import java.util.List;
import java.util.Map;

/**
 * 售后列表
 */
public class aty_after extends BaseAty {

    @ViewInject(R.id.tv_btn_left)
    private TextView tv_btn_left;

    @ViewInject(R.id.tv_btn_right)
    private TextView tv_btn_right;
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("协商退货退款");
        if (getIntent().getStringExtra("is_sales").equals("1")) {
            tv_btn_right.setVisibility(View.VISIBLE);
        } else {
            tv_btn_right.setVisibility(View.GONE);
        }
        tv_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfterSale.cancelAfter(getIntent().getStringExtra("back_apply_id"), aty_after.this);
                showProgressDialog();
            }
        });
        tv_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getStringExtra("back_apply_id"));
                startActivity(aty_addshipping.class, bundle);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_after;
    }

    @Override
    protected void initialized() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        AfterSale.showAfter(getIntent().getStringExtra("back_apply_id"), this);
    }

    @Override
    protected void requestData() {
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//    }

    Map<String, String> map;
    List<Map<String, String>> list;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("cancelAfter")) {
            showToast("取消成功！");
            finish();
        }
        if (requestUrl.contains("showAfter")) {
            list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            rv.setAdapter(new MyAdapter());
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_after, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItem(position).get("type").equals("1")) {
                holder.layout.setBackgroundResource(R.mipmap.icon_qipao1);
                holder.textview.setTextColor(Color.parseColor("#5E5E5E"));
                if (getItem(position).get("is_title").equals("1")) {
                    holder.tv_title.setVisibility(View.VISIBLE);
                    holder.view.setVisibility(View.VISIBLE);
                    holder.tv_title.setTextColor(Color.parseColor("#5E5E5E"));
                    holder.view.setBackgroundColor(Color.parseColor("#D7D7D7"));
                } else {
                    holder.tv_title.setVisibility(View.GONE);
                    holder.view.setVisibility(View.GONE);
                }
            } else {
                if (getItem(position).get("setting").equals("1")) {
                    holder.layout.setBackgroundResource(R.mipmap.icon_qipao2);
                } else {
                    holder.layout.setBackgroundResource(R.mipmap.icon_qipao3);
                }
                holder.textview.setTextColor(Color.WHITE);
                if (getItem(position).get("is_title").equals("1")) {
                    holder.tv_title.setVisibility(View.VISIBLE);
                    holder.view.setVisibility(View.VISIBLE);
                    holder.tv_title.setTextColor(Color.WHITE);
                    holder.view.setBackgroundColor(Color.WHITE);
                } else {
                    holder.tv_title.setVisibility(View.GONE);
                    holder.view.setVisibility(View.GONE);
                }

            }
            if (getItem(position).get("is_title").equals("1")) {
                holder.tv_title.setText(getItem(position).get("title"));
            }
            holder.tv_date.setText(getItem(position).get("time"));
            holder.textview.setText(getItem(position).get("content"));
        }

        private Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_date;
            TextView tv_title;
            LinearLayout layout;
            View view;
            TextView textview;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);
                tv_date = itemView.findViewById(R.id.tv_date);
                layout = itemView.findViewById(R.id.layout);
                view = itemView.findViewById(R.id.view);
                textview = itemView.findViewById(R.id.textview);
            }
        }
    }
}
