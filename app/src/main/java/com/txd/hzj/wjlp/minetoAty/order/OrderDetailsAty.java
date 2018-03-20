package com.txd.hzj.wjlp.minetoAty.order;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.minetoAty.OrderLogisticsAty;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.new_wjyp.aty_after;
import com.txd.hzj.wjlp.new_wjyp.http.AuctionOrder;
import com.txd.hzj.wjlp.new_wjyp.http.GroupBuyOrder;
import com.txd.hzj.wjlp.new_wjyp.http.IntegralBuyOrder;
import com.txd.hzj.wjlp.new_wjyp.http.Order;
import com.txd.hzj.wjlp.new_wjyp.http.PreOrder;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;

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
    @ViewInject(R.id.layout_choose_address)
    private LinearLayout layout_choose_address;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_tel)
    private TextView tv_tel;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.leave_message)
    private TextView leave_message;
    private String is_pay_password="0";//是否设置支付密码


    private CommonPopupWindow commonPopupWindow;


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
    @OnClick({R.id.tv_btn_left, R.id.tv_btn_right,R.id.lin_logistics})
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
            case R.id.lin_logistics://订单物流
                Bundle bundle=new Bundle();
                bundle.putString("order_id",order_id);
                startActivity(OrderLogisticsAty.class,bundle);
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
        } else if (type.equals("6")) {
            AuctionOrder.preDetails(order_id, this);
        } else if (type.equals("7")) {
            IntegralBuyOrder.details(order_id, this);
        }
        showProgressDialog();
    }

    @Override
    protected void requestData() {
        tv_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("0") || type.equals("7")) {
                    if (order_status.equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        if (type.equals("7")) {
                            bundle.putString("type", "10");
                        }
                        bundle.putString("is_pay_password",is_pay_password);
                        startActivity(PayForAppAty.class, bundle);
                    } else if (order_status.equals("2")) {
                        if (type.equals("0")) {
//                            Order.receiving(order_id, OrderDetailsAty.this);
//                            showProgressDialog();
                        } else {
                            IntegralBuyOrder.Receiving(order_id, OrderDetailsAty.this);
                            showProgressDialog();
                        }
                    } else if (order_status.equals("3")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        startActivity(EvaluationReleaseAty.class, bundle);
                    } else if (order_status.equals("4") || order_status.equals("5")) {
                        new AlertDialog(OrderDetailsAty.this).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (type.equals("0")) {
                                    Order.deleteOrder(order_id, OrderDetailsAty.this);
                                    showProgressDialog();
                                } else {
                                    IntegralBuyOrder.DeleteOrder(order_id, OrderDetailsAty.this);
                                    showProgressDialog();
                                }
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
                        bundle.putString("is_pay_password",is_pay_password);
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
                        bundle.putString("is_pay_password",is_pay_password);
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
                } else if (type.equals("6")) {
                    switch (order_status) {
                        case "1":
                            break;
                        case "4":
                            AuctionOrder.Receiving(order_id, OrderDetailsAty.this);
                            showProgressDialog();
                            break;
                        case "5":

                        case "6":
                            new AlertDialog(OrderDetailsAty.this).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AuctionOrder.DeleteOrder(order_id, OrderDetailsAty.this);
                                    showProgressDialog();
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case "8":
                            Bundle bundle = new Bundle();
                            bundle.putString("order_id", order_id);
                            startActivity(EvaluationReleaseAty.class, bundle);
                            break;
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

                        } else if (type.equals("7")) {
                            if (order_status.equals("1")) {
                                IntegralBuyOrder.CancelOrder(order_id, OrderDetailsAty.this);
                                showProgressDialog();
                            }
                        }
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuctionOrder.CancelOrder(order_id, OrderDetailsAty.this);
                        showProgressDialog();
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
            if (type.equals("0") || type.equals("7")) {
                setOrderStatus();
            } else if (type.equals("3")) {
                setGroupBuyOrderStatus();
            } else if (type.equals("4")) {
                setPreOrderStatus();
            } else if (type.equals("6")) {
                setAuctionStatus();
            }
            tv_name.setText(data.get("user_name"));
            tv_tel.setText(data.get("phone"));
            tv_address.setText(data.get("address"));
            tv_logistics.setText(data.get("logistics"));
            tv_logistics_time.setText(data.get("logistics_time"));
            leave_message.setText(data.get("leave_message"));
            tv_merchant_name.setText(data.get("merchant_name"));
            list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
            order_price_info_tv.setText("共" +
                    list.size() + "件商品 合计：¥" + data.get("order_price"));
            tv_order_sn.setText("订单编号：" + data.get("order_sn"));
            is_pay_password=data.get("is_pay_password");
            tv_create_time.setText("创建时间：" + data.get("create_time"));
            tv_pay_time.setText("付款时间：" + data.get("pay_time"));
            if (type.equals("3")) {
                group_buy_id = data.get("group_buy_id");
                order_type = data.get("order_type");
            }
            goods_for_this_order_lv.setAdapter(thisAdapter);
        }

        if (requestUrl.contains("cancelOrder") ||
                requestUrl.contains("preCancelOrder") ||
                requestUrl.contains("CancelOrder")) {
            if (type.equals("0")) {
                Order.details(order_id, this);
            } else if (type.equals("3")) {
                GroupBuyOrder.details(order_id, this);
            } else if (type.equals("4")) {
                PreOrder.preDetails(order_id, this);
            } else if (type.equals("6")) {
                AuctionOrder.preDetails(order_id, this);
            } else if (type.equals("7")) {
                IntegralBuyOrder.details(order_id, this);
            }
            showProgressDialog();

        }
        if (requestUrl.contains("receiving") ||
                requestUrl.contains("preReceiving") ||
                requestUrl.contains("Receiving")) {
            if (type.equals("0")) {
                Order.details(order_id, this);
            } else if (type.equals("3")) {
                GroupBuyOrder.details(order_id, this);
            } else if (type.equals("4")) {
                PreOrder.preDetails(order_id, this);
            } else if (type.equals("6")) {
                AuctionOrder.preDetails(order_id, this);
            } else if (type.equals("7")) {
                IntegralBuyOrder.details(order_id, this);
            }
            showProgressDialog();

        }
        if (requestUrl.contains("deleteOrder") ||
                requestUrl.contains("preDeleteOrder") ||
                requestUrl.contains("DeleteOrder")) {
            showToast("删除成功！");
            finish();

        }
        if (requestUrl.contains("delayReceiving")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            showToast(data.get("message"));
        }
    }

    private void setOrderStatus() {
        switch (order_status) {
            case "0":
                tv_state.setText("待支付");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                layout_choose_address.setVisibility(View.GONE);
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
                tv_btn_right.setVisibility(View.GONE);
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
                layout_choose_address.setVisibility(View.GONE);
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
                layout_choose_address.setVisibility(View.GONE);
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
                tv_btn_right.setVisibility(View.GONE);
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
                layout_choose_address.setVisibility(View.GONE);
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
                tv_btn_right.setVisibility(View.GONE);
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

    private void setAuctionStatus() {
        switch (order_status) {
            case "1":
                tv_state.setText("待付款");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                tv_btn_left.setVisibility(View.VISIBLE);
                layout_choose_address.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.VISIBLE);
                break;

            case "3":
                tv_state.setText("待发货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "4":
                tv_state.setText("待收货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("确认收货");
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "8":
                tv_state.setText("待评价");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("评价");
                tv_btn_right.setVisibility(View.VISIBLE);
                break;

            case "5":
                tv_state.setText("已取消");
                tv_btn_left.setVisibility(View.GONE);
                layout_choose_address.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                tv_btn_right.setVisibility(View.VISIBLE);
                break;
            case "6":
                tv_state.setText("已完成");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                tv_btn_right.setVisibility(View.VISIBLE);
                break;
            case "10":
                tv_state.setText("竞拍中");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "11":
                tv_state.setText("竞拍成功");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                break;
            case "12":
                tv_state.setText("竞拍结束");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
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
            L.e("order_sta"+order_status);
            if (order_status.equals("0")||order_status.equals("5")) {
                tgvh.tv_btn_left.setVisibility(View.GONE);
                tgvh.tv_btn_right.setVisibility(View.GONE);
            } else {
                tgvh.tv_btn_left.setVisibility(View.VISIBLE);
            }

            if (getItem(i).get("after_type").equals("0")) {
                tgvh.tv_btn_left.setText("申请售后");

            } else {
                tgvh.tv_btn_left.setText("售后中");
            }
            if(order_status.equals("1")){
                tgvh.tv_btn_left.setVisibility(View.VISIBLE);
                tgvh.tv_btn_right.setVisibility(View.GONE);
            }
            if(order_status.equals("2")){
                if (getItem(i).get("sale_status").equals("0")) {
                    tgvh.delayReceiving.setVisibility(View.VISIBLE);
                } else {
                    tgvh.delayReceiving.setVisibility(View.GONE);
                }
                tgvh.tv_btn_right.setVisibility(View.VISIBLE);
                tgvh.tv_btn_right.setText("确认收货");
            }
            if(getItem(i).get("after_sale_status").equals("1")){
                tgvh.lin_shouhou.setVisibility(View.VISIBLE);
                tgvh.tv_shouhou.setText(getItem(i).get("after_sale_type"));
            }else{
                tgvh.lin_shouhou.setVisibility(View.GONE);
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
            tgvh.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(i).get("sure_status").equals("1")) {
                        showPwdPop(v, i);
                    } else {
                        Order.receiving(order_id, list.get(i).get("order_goods_id"), "", OrderDetailsAty.this);
                        showProgressDialog();
                    }
                }
            });
            Glide.with(OrderDetailsAty.this).load(getItem(i).get("goods_img")).into(tgvh.image);
            tgvh.name.setText(getItem(i).get("goods_name"));
            tgvh.num.setText("x" + getItem(i).get("goods_num"));
            tgvh.title.setText(getItem(i).get("attr"));
//            if (getItem(i).get("sale_status").equals("1")) {
//                tgvh.textview.setVisibility(View.VISIBLE);
            L.e("time"+getItem(i).get("sure_delivery_time"));
            if(getItem(i).get("status").equals("1")){
                tgvh.textview.setText("收货时间：" + getItem(i).get("sure_delivery_time"));
            }else{
                tgvh.textview.setText("系统自动收货时间：" + getItem(i).get("auto_time"));

            }

//            } else {
//                tgvh.textview.setVisibility(View.GONE);
//            }

            //是否存在公益宝贝
            if(!getItem(i).get("welfare").equals("0")){
                tgvh.layout_gongyi.setVisibility(View.VISIBLE);
                tgvh.tv_gongyi.setText("成交后卖家将捐赠"+getItem(i).get("welfare")+"元给公益计划");
            }else{
                tgvh.layout_gongyi.setVisibility(View.GONE);
            }
            //是否有特殊描述
            if(getItem(i).get("server_status").equals("1")){
                tgvh.lin_server_status.setVisibility(View.VISIBLE);
                tgvh.tv_pinzhibaozhang.setText(getItem(i).get("integrity_a"));
                tgvh.tv_fuwuchengnuo.setText(getItem(i).get("integrity_b"));
                tgvh.tv_fahuoshijian.setText(getItem(i).get("integrity_c"));
            }else{
                tgvh.lin_server_status.setVisibility(View.GONE);
            }

            tgvh.delayReceiving.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.delayReceiving(list.get(i).get("order_goods_id"), OrderDetailsAty.this);
                    showProgressDialog();
                }
            });
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
            @ViewInject(R.id.textview)
            private TextView textview;
            @ViewInject(R.id.delayReceiving)
            private TextView delayReceiving;
            @ViewInject(R.id.lin_shouhou)
            private LinearLayout lin_shouhou;
            @ViewInject(R.id.tv_shouhou)
            private TextView tv_shouhou;
            @ViewInject(R.id.layout_gongyi)
            private LinearLayout layout_gongyi;
            @ViewInject(R.id.tv_gongyi)
            private TextView tv_gongyi;
            @ViewInject(R.id.lin_server_status)
            private LinearLayout lin_server_status;
            @ViewInject(R.id.tv_pinzhibaozhang)
            private TextView tv_pinzhibaozhang;
            @ViewInject(R.id.tv_fuwuchengnuo)
            private TextView tv_fuwuchengnuo;
            @ViewInject(R.id.tv_fahuoshijian)
            private TextView tv_fahuoshijian;
        }
    }


    public void showPwdPop(View view, int i) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_confirmreceipt)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 3)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, final int position) {
                        TextView t1 = view.findViewById(R.id.t1);
                        TextView t2 = view.findViewById(R.id.t2);
                        t1.setText(list.get(position).get("server"));
                        t2.setText(list.get(position).get("server_else"));
                        TextView tv1 = view.findViewById(R.id.textview1);
                        TextView tv2 = view.findViewById(R.id.textview2);
                        tv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Order.receiving(order_id, list.get(position).get("order_goods_id"), "1", OrderDetailsAty.this);
                                showProgressDialog();
                                commonPopupWindow.dismiss();
                            }
                        });
                        tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Order.receiving(order_id, list.get(position).get("order_goods_id"), "2", OrderDetailsAty.this);
                                showProgressDialog();
                                commonPopupWindow.dismiss();

                            }
                        });
                    }
                }, i)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
