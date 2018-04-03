package com.txd.hzj.wjlp.minetoAty;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.new_wjyp.VipDetailsAty;
import com.txd.hzj.wjlp.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;
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
    @ViewInject(R.id.upview1)
    private UPMarqueeView upview1;
    private List<View> views;

    @ViewInject(R.id.tv_tk)
    private TextView tv_tk;

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
        views = new ArrayList<>();
        titlt_conter_tv.setText("会员等级");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void requestData() {
        User.userCard(this);
        showProgressDialog();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        User.userCard(this);
        showProgressDialog();
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < advertisement.size(); i = i + 2) {
            if (advertisement.get(i).get("type").equals("1")) {
                //设置滚动的单个布局
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(this).inflate(
                        R.layout.iten_wj_top_view, null);
                //初始化布局的控件
                TextView tv1 = moreView.findViewById(R.id.top_tv1);
                tv1.setTextColor(Color.WHITE);
                tv1.setTextSize(20f);
                //初始化布局的控件
                TextView tv2 = moreView.findViewById(R.id.top_tv2);
                //进行对控件赋值
                tv1.setText(advertisement.get(i).get("content"));
//
//                if (advertisement.size() > i + 1) {
//                    //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
//                    tv2.setText(advertisement.get(i + 1).get("content"));
//                } else {
//                    tv2.setVisibility(View.GONE);
//                }

                //添加到循环滚动数组里面去
                views.add(moreView);
            } else {
                tv_tk.setText(advertisement.get(i).get("content"));
            }
        }
    }

    Map<String, String> map;
    ArrayList<Map<String, String>> list = new ArrayList<>();
    ArrayList<Map<String, String>> advertisement = new ArrayList<>();

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        list = JSONUtils.parseKeyAndValueToMapList(map.get("list"));
        advertisement = JSONUtils.parseKeyAndValueToMapList(map.get("advertisement"));
        setView();
        upview1.setViews(views);
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
            holder.tv_ms.setText(getItem(position).get("this_description"));
            if (!getItem(position).get("is_get").equals("0")) {
                holder.im.setVisibility(View.VISIBLE);
                if (getItem(position).get("over_time").equals("0")) {
                    holder.tv_time.setText("永久有效");
                } else {
                    holder.tv_time.setText(getItem(position).get("over_time"));
                }
                if (getItem(position).get("is_discount").equals("1")) {
                    holder.im.setImageResource(R.mipmap.icon_vip_h);
                } else {
                    holder.im.setImageResource(R.mipmap.icon_vip_t);

                }
            } else {
                holder.im.setVisibility(View.GONE);
                holder.tv_time.setText("点击查看" + getItem(position).get("rank_name") + "权益详情");
            }
            holder.layout_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItem(position).get("sale_status").equals("1")) {
                        String data = getItem(position).toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", data);
                        startActivity(VipDetailsAty.class, bundle);
                    } else if (getItem(position).get("sale_status").equals("0")) {
                        String data = getItem(position).toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", data);
                        startActivity(VipDetailsAty.class, bundle);
                    }

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
