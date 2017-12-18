package com.txd.hzj.wjlp.minetoAty.order;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.baidu.mapapi.map.Text;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.minetoAty.order.fgt.OrderOnLineFgt;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.txunda_lh.aty_after;
import com.txd.hzj.wjlp.txunda_lh.http.GroupBuyOrder;
import com.txd.hzj.wjlp.txunda_lh.http.Order;
import com.txd.hzj.wjlp.txunda_lh.http.PreOrder;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：下午 3:02
 * 描述：订单详情
 * ===============Txunda===============
 */
public class OrderDetailsAty extends BaseAty {
    private String type;
    private String order_status = "";
    private String order_id = "";
    @ViewInject(R.id.tv_state)
    private TextView tv_state;
    @ViewInject(R.id.tv_logistics)
    private TextView tv_logistics;
    @ViewInject(R.id.tv_logistics_time)
    private TextView tv_logistics_time;
    @ViewInject(R.id.tv_merchant_name)
    private TextView tv_merchant_name;
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.goods_for_this_order_lv)
    private ListViewForScrollView goods_for_this_order_lv;

    @ViewInject(R.id.order_price_info_tv)
    private TextView order_price_info_tv;
    @ViewInject(R.id.tv_order_sn)
    private TextView tv_order_sn;
    @ViewInject(R.id.tv_create_time)
    private TextView tv_create_time;
    @ViewInject(R.id.tv_pay_time)
    private TextView tv_pay_time;
    @ViewInject(R.id.bot_for_order)
    private LinearLayout bot_for_order;

    /**
     * 预订第一阶段(定金实付款)
     */
    @ViewInject(R.id.reserve_first_step_price_tv)
    private TextView reserve_first_step_price_tv;

    /**
     * 预定第二阶段(尾款实付款)
     */
    @ViewInject(R.id.reserve_sec_step_price_tv)
    private TextView reserve_sec_step_price_tv;
    @ViewInject(R.id.layout_yugou)
    private LinearLayout layout_yugou;

    @ViewInject(R.id.details_order_sc)
    private ScrollView details_order_sc;
    private ThisGoodsAdapter thisAdapter;
    @ViewInject(R.id.tv_btn_left)
    public TextView tv_btn_left;
    @ViewInject(R.id.tv_btn_right)
    public TextView tv_btn_right;
    private String group_buy_id;
    private String order_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单详情");
        details_order_sc.smoothScrollTo(0, 0);
        bot_for_order.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
//        ChangeTextViewStyle.getInstance().forOrderPrice2(this, order_price_info_tv, "共1件商品 合计：￥14.80");
//        ChangeTextViewStyle.getInstance().forOrderPrice2(this, reserve_first_step_price_tv, "￥5.24");
//        ChangeTextViewStyle.getInstance().forOrderPrice2(this, reserve_sec_step_price_tv, "￥43.86");
    }

    @Override
    @OnClick({R.id.tv_btn_left, R.id.tv_btn_right})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_btn_left:// 最底部左侧按钮
                // 申请售后
                startActivity(ApplyForAfterSalesAty.class, null);
                break;
            case R.id.tv_btn_right:// 最底部右侧按钮
                // 评价商品

                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_order_details;
    }

    @Override
    protected void initialized() {
        thisAdapter = new ThisGoodsAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(order_id)) {
            order_id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
        }
        if (type.equals("0")) {
            Order.details(order_id, this);
        } else if (type.equals("3")) {
            GroupBuyOrder.details(order_id, this);
        } else if (type.equals("4")) {
            PreOrder.preDetails(order_id, this);
            layout_yugou.setVisibility(View.VISIBLE);
        }
        showProgressDialog();
    }

    @Override
    protected void requestData() {
        tv_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("0")) {
                    if (order_status.equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        startActivity(PayForAppAty.class, bundle);
                    } else if (order_status.equals("2")) {
                        Order.receiving(order_id, OrderDetailsAty.this);
                        showProgressDialog();
                    } else if (order_status.equals("3")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        startActivity(EvaluationReleaseAty.class, bundle);
                    } else if (order_status.equals("4") || order_status.equals("5")) {
                        new AlertDialog(OrderDetailsAty.this).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Order.deleteOrder(order_id, OrderDetailsAty.this);
                                showProgressDialog();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                    }
                } else if (type.equals("3")) {
                    if (order_status.equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        bundle.putString("group_buy_id", group_buy_id);
                        bundle.putString("type", String.valueOf(Integer.parseInt(order_type) + 1));
                        startActivity(PayForAppAty.class, bundle);
                    } else if (order_status.equals("3")) {
                        GroupBuyOrder.receiving(order_id, OrderDetailsAty.this);
                        showProgressDialog();
                    } else if (order_status.equals("4")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        startActivity(EvaluationReleaseAty.class, bundle);
                    } else if (order_status.equals("5") || order_status.equals("6")) {

                        new AlertDialog(OrderDetailsAty.this).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                GroupBuyOrder.deleteOrder(order_id, OrderDetailsAty.this);
                                showProgressDialog();

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                    }
                } else if (type.equals("4")) {
                    if (order_status.equals("1") || order_status.equals("7")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        bundle.putString("group_buy_id", group_buy_id);
                        bundle.putString("type", "6");
                        startActivity(PayForAppAty.class, bundle);
                    } else if (order_status.equals("3")) {
                        PreOrder.preReceiving(order_id, OrderDetailsAty.this);
                        showProgressDialog();
                    } else if (order_status.equals("4")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        startActivity(EvaluationReleaseAty.class, bundle);
                    } else if (order_status.equals("5") || order_status.equals("6")) {
                        new AlertDialog(OrderDetailsAty.this).builder()
                                .setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PreOrder.preDeleteOrder(order_id, OrderDetailsAty.this);
                                showProgressDialog();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();

                    }
                }
            }
        });
        tv_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(OrderDetailsAty.this).builder()
                        .setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (type.equals("0")) {
                            if (order_status.equals("0")) {
                                Order.cancelOrder(order_id, OrderDetailsAty.this);
                                showProgressDialog();
                            }
                        } else if (type.equals("3")) {
                            if (order_status.equals("0")) {
                                GroupBuyOrder.cancelOrder(order_id, OrderDetailsAty.this);
                                showProgressDialog();
                            }
                        } else if (type.equals("4")) {
                            if (order_status.equals("1")) {
                                PreOrder.preCancelOrder(order_id, OrderDetailsAty.this);
                                showProgressDialog();
                            }

                        }
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });
    }

    Map<String, String> data;
    List<Map<String, String>> list;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("details") ||
                requestUrl.contains("preDetails")) {
            data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
            //订单状态（0待支付 1待发货  2待收货3 待评价4 已完成 5已取消
            order_status = data.get("order_status");
            if (type.equals("0")) {
                setOrderStatus();
            } else if (type.equals("3")) {
                setGroupBuyOrderStatus();
            } else if (type.equals("4")) {
                setPreOrderStatus();
            }
            tv_logistics.setText(data.get("logistics"));
            tv_logistics_time.setText(data.get("logistics_time"));
            tv_merchant_name.setText(data.get("merchant_name"));
            list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
            order_price_info_tv.setText("共" +
                    list.size() + "件商品 合计：¥" + data.get("order_price"));
            tv_order_sn.setText("订单编号：" + data.get("order_sn"));
            tv_create_time.setText("创建时间：" + data.get("create_time"));
            tv_pay_time.setText("付款时间：" + data.get("pay_time"));
            if (type.equals("3")) {
                group_buy_id = data.get("group_buy_id");
                order_type = data.get("order_type");
            }
            goods_for_this_order_lv.setAdapter(thisAdapter);
        }

        if (requestUrl.contains("cancelOrder") ||
                requestUrl.contains("preCancelOrder")) {
            if (type.equals("0")) {
                Order.details(order_id, this);
            } else if (type.equals("3")) {
                GroupBuyOrder.details(order_id, this);
            } else if (type.equals("4")) {
                PreOrder.preDetails(order_id, this);
            }
            showProgressDialog();

        }
        if (requestUrl.contains("receiving") ||
                requestUrl.contains("preReceiving")) {
            if (type.equals("0")) {
                Order.details(order_id, this);
            } else if (type.equals("3")) {
                GroupBuyOrder.details(order_id, this);
            } else if (type.equals("4")) {
                PreOrder.preDetails(order_id, this);
            }
            showProgressDialog();

        }
        if (requestUrl.contains("deleteOrder") ||
                requestUrl.contains("preDeleteOrder")) {
            showToast("删除成功！");
            finish();

        }
    }

    private void setOrderStatus() {
        switch (order_status) {
            case "0":
                tv_state.setText("待支付");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                break;
            case "1":
                tv_state.setText("待发货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "2":
                tv_state.setText("待收货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("确认收货");
                break;
            case "3":
                tv_state.setText("待评价");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("评价");
                break;
            case "4":
                tv_state.setText("已完成");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                break;
            case "5":
                tv_state.setText("已取消");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                break;

        }
    }

    private void setGroupBuyOrderStatus() {
        switch (order_status) {
            case "0":
                tv_state.setText("待支付");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                break;
            case "1":
                tv_state.setText("待成团");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "2":
                tv_state.setText("待发货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "3":
                tv_state.setText("待收货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("确认收货");
                break;
            case "4":
                tv_state.setText("待评价");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("评价");
                break;
            case "5":
                tv_state.setText("已完成");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                break;
            case "6":
                tv_state.setText("已取消");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                break;

        }
    }

    private void setPreOrderStatus() {
        switch (order_status) {
            case "0":
                tv_state.setText("预购中");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "1":
                tv_state.setText("代付尾款");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                break;
            case "2":
                tv_state.setText("待发货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "3":
                tv_state.setText("待收货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("确认收货");
                break;
            case "4":
                tv_state.setText("待评价");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("评价");
                break;
            case "5":
                tv_state.setText("已取消");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                break;
            case "6":
                tv_state.setText("已完成");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                break;
            case "7":
                tv_state.setText("待付定金");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                break;


        }
    }

    private class ThisGoodsAdapter extends BaseAdapter {

        private TGVH tgvh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(OrderDetailsAty.this).
                        inflate(R.layout.item_goods_for_this_order_lv, null);
                tgvh = new TGVH();
                ViewUtils.inject(tgvh, view);
                view.setTag(tgvh);
            } else {
                tgvh = (TGVH) view.getTag();
            }
            tgvh.tv_btn_right.setVisibility(View.GONE);
            if (order_status.equals("0")) {
                tgvh.tv_btn_left.setVisibility(View.GONE);
            } else {
                tgvh.tv_btn_left.setVisibility(View.VISIBLE);
            }
            if (getItem(i).get("after_type").equals("0")) {
                tgvh.tv_btn_left.setText("申请售后");
            } else {
                tgvh.tv_btn_left.setText("售后中");
            }
            tgvh.tv_price.setText("¥" + getItem(i).get("shop_price"));
            tgvh.tv_price.setVisibility(View.VISIBLE);
            tgvh.tv_btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItem(i).get("after_type").equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("price", String.valueOf(
                                Double.parseDouble(getItem(i).get("shop_price"))
                                        * Integer.parseInt(getItem(i).get("goods_num"))));
                        bundle.putString("order_goods_id", getItem(i).get("order_goods_id"));
                        bundle.putString("order_id", order_id);
                        bundle.putString("type", type);
                        startActivity(ApplyForAfterSalesAty.class, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("is_sales", getItem(i).get("is_sales"));
                        bundle.putString("back_apply_id", getItem(i).get("back_apply_id"));
                        bundle.putString("order_goods_id", getItem(i).get("order_goods_id"));
                        startActivity(aty_after.class, bundle);
                    }
                }
            });
            Glide.with(OrderDetailsAty.this).load(getItem(i).get("goods_img")).into(tgvh.image);
            tgvh.name.setText(getItem(i).get("goods_name"));
            tgvh.num.setText("x" + getItem(i).get("goods_num"));
            tgvh.title.setText("规格" + getItem(i).get("attr"));
            return view;
        }

        class TGVH {
            @ViewInject(R.id.image)
            private ImageView image;
            @ViewInject(R.id.name)
            private TextView name;
            @ViewInject(R.id.num)
            private TextView num;
            @ViewInject(R.id.title)
            private TextView title;
            @ViewInject(R.id.tv_price)
            private TextView tv_price;
            @ViewInject(R.id.tv_btn_left)
            public TextView tv_btn_left;
            @ViewInject(R.id.tv_btn_right)
            public TextView tv_btn_right;


        }
    }

}
