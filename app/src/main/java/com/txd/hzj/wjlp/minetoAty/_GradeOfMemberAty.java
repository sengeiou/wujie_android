package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.txunda_lh.VipDetailsAty;

import java.util.ArrayList;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：上午 10:19
 * 描述：会员
 * ===============Txunda===============
 */
public class _GradeOfMemberAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerview;
    private MyAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout._aty_grade_of_member;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("会员等级");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void requestData() {
        User.userCard(this);
        showProgressDialog();
    }

    Map<String, String> map;
    ArrayList<Map<String, String>> list = new ArrayList<>();

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        list = JSONUtils.parseKeyAndValueToMapList(map.get("list"));
        adapter = new MyAdapter();
        recyclerview.setAdapter(adapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vip, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.textview1.setText(getItem(position).get("rank_name"));
            holder.layout_1.setBackgroundResource(getItem(position).get("is_get").equals("1") ? R.mipmap.icon_vip_card1 : R.mipmap.icon_vip_card2);
            holder.tv_time.setText(getItem(position).get("this_description"));
            if (!getItem(position).get("is_discount").equals("0")) {
                holder.im.setVisibility(View.VISIBLE);
                if (getItem(position).get("is_discount").equals("1")) {
                    holder.im.setImageResource(R.mipmap.icon_vip_h);
                } else {
                    holder.im.setImageResource(R.mipmap.icon_vip_t);
                }
            } else {
                holder.im.setVisibility(View.GONE);
            }
            holder.layout_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = getItem(position).toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", data);
                    startActivity(VipDetailsAty.class, bundle);
                }
            });
        }

        private Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout layout_1;
            TextView textview1;
            TextView tv_ms;
            ImageView im;
            TextView tv_time;

            public ViewHolder(View itemView) {
                super(itemView);
                layout_1 = itemView.findViewById(R.id.layout_v1);
                textview1 = itemView.findViewById(R.id.textview1);
                tv_ms = itemView.findViewById(R.id.tv_ms);
                im = itemView.findViewById(R.id.im);
                tv_time = itemView.findViewById(R.id.tv_time);
            }
        }
    }
}
