package com.txd.hzj.wjlp.minetoAty.order.fgt;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.MemberOrderList;
import com.txd.hzj.wjlp.minetoAty.order.VipCardDetailsAty;
import com.txd.hzj.wjlp.new_wjyp.VipDetailsAty;
import com.txd.hzj.wjlp.new_wjyp.VipPayAty;
import com.txd.hzj.wjlp.new_wjyp.http.MemberOrder;
import com.txd.hzj.wjlp.new_wjyp.http.User;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Txd_lienchao on 2018/3/16 0016 上午 11:00.
 * 功能描述:会员卡碎片
 * email:470360046@qq.com
 * 不急不躁，BUG圆如，
 * 说改就改，不撕不怒，
 * 种种需求，过眼云烟，
 * 敏捷迭代，自在随心，
 * 立项结项，不如吃瓜。
 */

public class VipCardFgt extends BaseFgt {
    @ViewInject(R.id.super_sr_layout)
    private SuperSwipeRefreshLayout swipe_refresh;
    @ViewInject(R.id.order_on_line_lv)
    private ListView order_on_line_lv;
    @ViewInject(R.id.tv_null)
    private TextView tv_null;
    private String pay_status;//会员卡状态
    private int p = 1;//分页
    private List<MemberOrderList.DataBean> dataBeanList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private String order_id = "";

    public VipCardFgt(String pay_status) {
        this.pay_status = pay_status;
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_vip_cart;
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void requestData() {
        order_on_line_lv.setEmptyView(tv_null);
        order_on_line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                order_id = dataBeanList.get(i).getId();
                bundle.putString("order_id", dataBeanList.get(i).getId());
                bundle.putString("member_coding", dataBeanList.get(i).getMember_coding());
                startActivity(VipCardDetailsAty.class, bundle);
            }
        });
        swipe_refresh.setHeaderViewBackgroundColor(0xff888888);
        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
        swipe_refresh.setTargetScrollWithLayout(true);
        swipe_refresh
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        dataBeanList.clear();
                        MemberOrder.memberOrderList(pay_status, p + "", VipCardFgt.this);
                        showProgressDialog();
                    }

                    @Override
                    public void onPullDistance(int distance) {
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

        swipe_refresh
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);
                        p++;
                        MemberOrder.memberOrderList(pay_status, p + "", VipCardFgt.this);
                        showProgressDialog();
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {

                    }

                });
        orderAdapter = new OrderAdapter(dataBeanList, getContext());
        order_on_line_lv.setAdapter(orderAdapter);
        MemberOrder.memberOrderList(pay_status, p + "", this);
        showProgressDialog();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        swipe_refresh.setRefreshing(false);
        swipe_refresh.setLoadMore(false);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        swipe_refresh.setRefreshing(false);
        swipe_refresh.setLoadMore(false);
        if (requestUrl.contains("memberOrderList")) {
            Gson gson = new Gson();
            MemberOrderList memberOrderList = gson.fromJson(jsonStr, MemberOrderList.class);
            if (memberOrderList.getCode().equals("1")) {
                for (MemberOrderList.DataBean temp : memberOrderList.getData()) {
                    dataBeanList.add(temp);
                }
                orderAdapter.notifyDataSetChanged();
            }
        }
        if (requestUrl.contains("delMemberOrder")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (data.get("code").equals("1")) {
                showToast(data.get("message"));
                EventBus.getDefault().post(new MessageEvent("更新会员卡列表"));

            }
        }
        if (requestUrl.contains("settlement")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
            Bundle bundle = new Bundle();
            bundle.putString("data", String.valueOf(data));

            bundle.putString("order_id", order_id); // 改参数传入下一页有用
            bundle.putString("money", data.get("pay_money")); // 改参数传入下一页有用
            bundle.putString("rank_name", data.get("rank_name")); // 改参数传入下一页有用
            bundle.putString("prescription", data.get("prescription")); // 后面这些其实没有一点用处
            bundle.putString("member_coding", data.get("member_coding")); // 但是为了防止下一界面加载的时候
            bundle.putString("sale_status", data.get("sale_status")); // 报空指针，所以这些参数还是传一下的好
            bundle.putString("big_gift", data.get("big_gift"));
            bundle.putString("score_status", data.get("score_status"));
            bundle.putString("abs_url", data.get("abs_url"));

            startActivity(VipPayAty.class, bundle);
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    class OrderAdapter extends BaseAdapter {

        private List<MemberOrderList.DataBean> list;
        private Context context;

        public OrderAdapter(List<MemberOrderList.DataBean> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_vip_card, null);
                viewHolder.tv_rank_name = view.findViewById(R.id.tv_rank_name);
                viewHolder.tv_create_time = view.findViewById(R.id.tv_create_time);
                viewHolder.tv_order_status = view.findViewById(R.id.tv_order_status);
                viewHolder.tv_order_sn = view.findViewById(R.id.tv_order_sn);
                viewHolder.tv_validity = view.findViewById(R.id.tv_validity);
                viewHolder.tv_btn_left = view.findViewById(R.id.tv_btn_left);
                viewHolder.tv_btn_right = view.findViewById(R.id.tv_btn_right);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv_rank_name.setText(list.get(i).getRank_name());
            viewHolder.tv_order_sn.setText("会员编码:" + list.get(i).getOrder_sn());
            viewHolder.tv_create_time.setText("(购买时间:" + list.get(i).getCreate_time() + ")");
            viewHolder.tv_validity.setText(list.get(i).getValidity());
            switch (list.get(i).getOrder_status()) {
                case "1":
                    viewHolder.tv_order_status.setText("未支付");
                    break;
                case "2":
                    viewHolder.tv_order_status.setText("已支付");
                    break;
                case "5":
                    viewHolder.tv_order_status.setText("已取消");
                    break;
            }
            switch (list.get(i).getOrder_status()) {
                case "1"://未支付
                    viewHolder.tv_btn_left.setVisibility(View.VISIBLE);
                    viewHolder.tv_btn_right.setVisibility(View.VISIBLE);
                    viewHolder.tv_btn_left.setText("取消订单");
                    viewHolder.tv_btn_right.setText("立即支付");
                    viewHolder.tv_btn_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                            builder.setMessage("是否取消订单");
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                }
                            });
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {
                                    MemberOrder.delMemberOrder(list.get(i).getId(), "5", VipCardFgt.this);
                                    showProgressDialog();
                                }
                            });
                            builder.show();
                        }
                    });
//                    viewHolder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
                    viewHolder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //立即支付
                            order_id = list.get(i).getId();
                            MemberOrder.settlement(list.get(i).getMember_coding(), VipCardFgt.this);
                            showProgressDialog();

                        }
                    });
                    break;
                case "2":
                    viewHolder.tv_btn_left.setVisibility(View.GONE);
                    viewHolder.tv_btn_right.setVisibility(View.VISIBLE);
                    viewHolder.tv_btn_right.setText("删除订单");
                    viewHolder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                            builder.setMessage("是否删除订单");
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                }
                            });
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {
                                    MemberOrder.delMemberOrder(list.get(i).getId(), "9", VipCardFgt.this);
                                    showProgressDialog();
                                }
                            });
                            builder.show();

                        }
                    });
                    break;
                case "5"://已取消
                    viewHolder.tv_btn_left.setVisibility(View.GONE);
                    viewHolder.tv_btn_right.setVisibility(View.VISIBLE);
                    viewHolder.tv_btn_right.setText("删除订单");
                    viewHolder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                            builder.setMessage("是否删除订单");
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {

                                }
                            });
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {
                                    MemberOrder.delMemberOrder(list.get(i).getId(), "9", VipCardFgt.this);
                                    showProgressDialog();
                                }
                            });
                            builder.show();
                        }
                    });
                    break;
            }
            return view;
        }

        class ViewHolder {
            private TextView tv_rank_name;
            private TextView tv_create_time;
            private TextView tv_order_status;
            private TextView tv_order_sn;
            private TextView tv_validity;
            private TextView tv_btn_left, tv_btn_right;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("更新会员卡列表")) {
            p = 1;
            dataBeanList.clear();
            MemberOrder.memberOrderList(pay_status, p + "", this);
            showProgressDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * 解除事件总线
         */
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
