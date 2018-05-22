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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.AfterSale;
import com.txd.hzj.wjlp.minetoAty.order.ApplyForAfterSalesAty;

import java.util.List;
import java.util.Map;

/**
 * 售后列表
 */
public class aty_after extends BaseAty {

    @ViewInject(R.id.tv_btn_quxiao)
    private TextView tv_btn_quxiao;
    @ViewInject(R.id.tv_btn_tuihuo)
    private TextView tv_btn_tuihuo;
    @ViewInject(R.id.tv_btn_chongxinshenqing)
    private TextView tv_btn_chongxinshenqing;
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.rv)
    RecyclerView rv;

    private String is_sales; // 退货按钮显隐状态 0不显示退货按钮 1显示退货按钮
    private String after_type; // 售后状态 0 申请售后  1售后中 2售后完成 3售后拒绝
    private String back_apply_id; // 售后id
    // ↓↓↓↓↓↓继续申请售后需要使用的参数↓↓↓↓↓↓↓
    private String price;
    private String order_goods_id;
    private String order_id;
    private String type;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("协商退货退款");
    }

    @Override
    @OnClick({R.id.tv_btn_tuihuo, R.id.tv_btn_quxiao, R.id.tv_btn_chongxinshenqing})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_btn_tuihuo: // 退货跳转至输入运单号等信息界面
                bundle = new Bundle();
                bundle.putString("id", back_apply_id);
                startActivity(aty_addshipping.class, bundle);
                break;
            case R.id.tv_btn_quxiao: // 取消售后申请
                AfterSale.cancelAfter(back_apply_id, aty_after.this);
                showProgressDialog();
                break;
            case R.id.tv_btn_chongxinshenqing: // 跳转至申请售后界面
                bundle = new Bundle();
                bundle.putString("price", price);
                bundle.putString("order_goods_id", order_goods_id);
                bundle.putString("order_id",order_id );
                bundle.putString("type", type);
                bundle.putString("maxPrice", price);
                startActivity(ApplyForAfterSalesAty.class, bundle);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_after;
    }

    @Override
    protected void initialized() {
        initPage();
    }

    private void initPage(){
        is_sales = getIntent().getStringExtra("is_sales");
        after_type = getIntent().getStringExtra("after_type");
        back_apply_id = getIntent().getStringExtra("back_apply_id");

        // 继续申请售后需要使用的参数
        price = getIntent().getStringExtra("price");
        order_goods_id = getIntent().getStringExtra("order_goods_id");
        order_id = getIntent().getStringExtra("order_id");
        type = getIntent().getStringExtra("type");

        tv_btn_tuihuo.setVisibility(is_sales.equals("1") ? View.VISIBLE : View.GONE);
        switch (Integer.parseInt(after_type)){
            case 1:
                tv_btn_quxiao.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_btn_quxiao.setVisibility(View.GONE);
                break;
            case 3:
                tv_btn_quxiao.setVisibility(View.VISIBLE);
                tv_btn_chongxinshenqing.setVisibility(View.VISIBLE);
                break;
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        AfterSale.showAfter(back_apply_id, this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AfterSale.showAfter(back_apply_id, this);
    }

    @Override
    protected void requestData() {
    }

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
                L.e("wang", "getItem(position) = " + getItem(position));
                if (getItem(position).get("type").equals("1")) {
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
