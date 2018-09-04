package com.txd.hzj.wjlp.wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.new_wjyp.RoundTransformation;
import com.txd.hzj.wjlp.new_wjyp.UnionmerchartAty;
import com.txd.hzj.wjlp.http.Recommending;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线下店铺商家推荐
 * by Txunda_LH on 2018/2/2.
 */

public class PushMerchantAty extends BaseAty {

    private TextView tv_title;

    private RecyclerView recyclerView;

    private TextView button;

    private Map<String, String> map = new HashMap<>();

    private List<Map<String, String>> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pushmerchant);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("线下店铺商家推荐");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        button = (TextView) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PushMerchantAty.this, UnionmerchartAty.class);
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        try {
            Recommending.businessList(new BaseView() {
                @Override
                public void showDialog() {
                }

                @Override
                public void showDialog(String text) {
                }

                @Override
                public void showContent() {
                }

                @Override
                public void removeDialog() {
                }

                @Override
                public void removeContent() {
                }

                @Override
                public void onStarted() {
                }

                @Override
                public void onCancelled() {
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                }

                @Override
                public void onException(Exception exception) {
                }

                @Override
                public void onComplete(String requestUrl, String jsonStr) {
                    map = JSONUtils.parseKeyAndValueToMap(jsonStr);
                    list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    recyclerView.setAdapter(new MyAdpater());
                }

                @Override
                public void onError(String requestUrl, Map<String, String> error) {
                }

                @Override
                public void onErrorTip(String tips) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "数据有点问题", Toast.LENGTH_SHORT);
            L.e("===========pushMerchantAty load Exception=========" + e.toString());
        }
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }

    class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pushmerchant, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Glide.with(PushMerchantAty.this).load(list.get(position).get("logo")).bitmapTransform(new RoundTransformation(PushMerchantAty.this, 10)).into(holder.im_head);
            holder.tv_name.setText(list.get(position).get("user_name"));
            holder.tv_text.setText(list.get(position).get("merchant_name"));
            holder.tv_time.setText(list.get(position).get("create_time"));
            holder.tv_tel.setText(list.get(position).get("user_phone"));
            holder.tv_address.setText(list.get(position).get("city") + list.get(position).get("street"));
            switch (list.get(position).get("status")) {
                case "0":
                case "1":
                    holder.tv_edit.setText("审核中");
                    break;
                case "2":
                case "3":
                    holder.tv_edit.setText("审核拒绝");
                    break;
                case "4":
                    holder.tv_edit.setText("待入驻");
                    break;
                case "5":
                    holder.tv_edit.setText("入驻失败");
                    break;
                case "6":
                    holder.tv_edit.setText("入驻成功");
                    break;
                case "7":
                    holder.tv_edit.setText("入驻中");
                    break;
            }
            // type = 1 联盟商家   type = 2 无界驿店
            holder.tv_wjyd.setText(list.get(position).get("type").equals("1") ? "联盟商家" : "无界驿店");
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(PushMerchantAty.this, UnionmerchartAty.class);
                    intent.putExtra("data", list.get(position).toString());
                    intent.putExtra("type", "2");
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView im_head;
            TextView tv_text;
            TextView tv_address;
            TextView tv_edit;
            TextView tv_time;
            TextView tv_name;
            TextView tv_tel;
            TextView tv_wjyd;
            LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);
                im_head = (ImageView) itemView.findViewById(R.id.im_head);
                tv_text = (TextView) itemView.findViewById(R.id.tv_text);
                tv_address = (TextView) itemView.findViewById(R.id.tv_address);
                tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_tel = (TextView) itemView.findViewById(R.id.tv_tel);
                tv_wjyd = (TextView) itemView.findViewById(R.id.tv_wjyd);
                layout = (LinearLayout) itemView.findViewById(R.id.layout);

            }

        }
    }
}
