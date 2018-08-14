package com.txd.hzj.wjlp.minetoAty.order.fgt;

import android.graphics.Color;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.base.BaseFragment;
import com.ants.theantsgo.tools.AlertDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.OffLineOrderListBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.mellOffLine.OffLineEvaluationShopAty;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.minetoAty.order.OffLineShopDetailsAty;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/30 11:15
 * 功能描述：线下店铺订单列表页
 */
public class OffLineFgt extends BaseFragment {

    @ViewInject(R.id.super_sr_layout)
    private SuperSwipeRefreshLayout swipe_refresh;
    @ViewInject(R.id.order_on_line_lv)
    private ListView order_on_line_lv;
    @ViewInject(R.id.tv_null)
    private TextView tv_null;

    private static final String STATUS = "status";
    private String mPay_status = "";
    private int p = 1;//分页

    private List<OffLineOrderListBean.DataBean> dataBeanList = new ArrayList<>();
    private OffLineOrderAdapter mOffLineOrderAdapter;


    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    //5是取消订单 9是删除订单
    private String order_stats;


    public static OffLineFgt getInstance(String status) {
        OffLineFgt offLineFgt = new OffLineFgt();
        Bundle bundle = new Bundle();
        bundle.putString(STATUS, status);
        offLineFgt.setArguments(bundle);
        return offLineFgt;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.offline_fgt;
    }

    @Override
    protected void initialized() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPay_status = arguments.getString(STATUS);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    protected void requestData() {
        order_on_line_lv.setEmptyView(tv_null);
        order_on_line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", dataBeanList.get(i).getOrder_id());
                bundle.putString("merchant_id", dataBeanList.get(i).getMerchant_id());
                bundle.putString("pay_status", dataBeanList.get(i).getPay_status());
                bundle.putString("status", dataBeanList.get(i).getStatus());
                bundle.putString("common_status",dataBeanList.get(i).getCommon_status());
                bundle.putString("money",dataBeanList.get(i).getOrder_price());
                startActivity(OffLineShopDetailsAty.class, bundle);
            }
        });
        swipe_refresh.setHeaderViewBackgroundColor(Color.WHITE);
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
                        update();
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
                        OfflineStore.offLineOrderList(mPay_status, String.valueOf(p), OffLineFgt.this);
                        //                        new Handler().postDelayed(new Runnable() {
                        //
                        //                            @Override
                        //                            public void run() {
                        //                                //set false when finished
                        //                                showErrorTip("无更多数据了");
                        //                                swipe_refresh.setLoadMore(false);
                        //                                progressBar.setVisibility(View.GONE);
                        //                            }
                        //                        }, 5000);
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

    }

    private void update() {
        dataBeanList.clear();
        OfflineStore.offLineOrderList(mPay_status, String.valueOf(p), OffLineFgt.this);
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        refreshVisibleState();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("orderList")){
            refreshVisibleState();
            OffLineOrderListBean offLineOrderListBean = JSON.parseObject(jsonStr, OffLineOrderListBean.class);
            if (offLineOrderListBean.getCode().equals("1")) {
                dataBeanList.addAll(offLineOrderListBean.getData());
                mOffLineOrderAdapter = new OffLineOrderAdapter();
                order_on_line_lv.setAdapter(mOffLineOrderAdapter);
            }
        }

        if (requestUrl.contains("delOrder")){
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject.containsKey("code")) {
                if ("1".equals(jsonObject.getString("code"))) {
                    if (jsonObject.containsKey("message")) {
                        update();
                        showRightTip( jsonObject.getString("message"));
                    }
                }
            }
        }

    }

    private void refreshVisibleState() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            swipe_refresh.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility() == View.VISIBLE) {
            swipe_refresh.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void immersionInit() {

    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
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


    private class OffLineOrderAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dataBeanList.size() > 0 ? dataBeanList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return dataBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_offline_orderlist, parent, false);
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
            final OffLineOrderListBean.DataBean dataBean = dataBeanList.get(position);
            viewHolder.tv_rank_name.setText("店铺名称：" + dataBean.getMerchant_name());
            viewHolder.tv_order_sn.setText("订单编号:" + dataBean.getOrder_sn());
            viewHolder.tv_create_time.setText("下单时间:" + dataBean.getCreate_time());
            if ("4".equals(dataBean.getPay_type())){
                viewHolder.tv_validity.setText("订单金额：" + dataBean.getOrder_price() + "积分");
            }else {
                viewHolder.tv_validity.setText("订单金额：" + dataBean.getOrder_price() + "元");
            }

            if ("0".equals(dataBean.getPay_status())) {
                viewHolder.tv_order_status.setText("未支付");
                if ("0".equals(dataBean.getStatus())) {
                    viewHolder.tv_btn_left.setVisibility(View.VISIBLE);
                    viewHolder.tv_btn_right.setVisibility(View.VISIBLE);
                    viewHolder.tv_btn_left.setText("取消订单");
                    viewHolder.tv_btn_right.setText("立即支付");
                    viewHolder.tv_btn_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("是否取消订单").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    order_stats="5";
                                    OfflineStore.offLinedelOrder(dataBeanList.get(position).getOrder_id(), order_stats,OffLineFgt.this);
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                        }
                    });
                    viewHolder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //立即支付
                            Bundle bundle=new Bundle();
                            bundle.putString("order_id",dataBean.getOrder_id());
                            bundle.putString("type","100");
                            bundle.putString("money",dataBean.getOrder_price());
                            bundle.putString("merchant_id",dataBean.getMerchant_id());
                            startActivity(PayForAppAty.class,bundle);
                            getActivity().finish();
                        }
                    });
                } else if ("5".equals(dataBean.getStatus())) {
                    setButton(viewHolder, position);
                }
            } else if ("1".equals(dataBean.getPay_status())) {
                viewHolder.tv_order_status.setText("已支付");
                setButton(viewHolder, position);
                if ("0".equals(dataBean.getStatus())){
                    viewHolder.tv_btn_left.setVisibility(View.VISIBLE);
                    viewHolder.tv_btn_left.setText("评论店铺");
                    viewHolder.tv_btn_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("order_id",dataBean.getOrder_id());
                            startActivity(OffLineEvaluationShopAty.class,bundle);
                        }
                    });
                    if ("1".equals(dataBean.getCommon_status())){
                        viewHolder.tv_btn_left.setVisibility(View.GONE);
                    }
                }
            }
            return view;
        }

        private void setButton(ViewHolder viewHolder, final int position) {
            viewHolder.tv_btn_left.setVisibility(View.GONE);
            viewHolder.tv_btn_right.setVisibility(View.VISIBLE);
            viewHolder.tv_btn_right.setText("删除订单");
            viewHolder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("是否删除订单").setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order_stats="9";
                            OfflineStore.offLinedelOrder(dataBeanList.get(position).getOrder_id(), order_stats,OffLineFgt.this);
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                }
            });
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
}
