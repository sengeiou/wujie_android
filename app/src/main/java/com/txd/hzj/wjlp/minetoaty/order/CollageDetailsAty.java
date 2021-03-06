package com.txd.hzj.wjlp.minetoaty.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
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
import android.widget.Space;
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
import com.txd.hzj.wjlp.http.AuctionOrder;
import com.txd.hzj.wjlp.http.GroupBuyOrder;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.mellonLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.minetoaty.OrderLogisticsAty;
import com.txd.hzj.wjlp.minetoaty.PayForAppAty;
import com.txd.hzj.wjlp.new_wjyp.After_aty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 描述：拼单购订单详情
 * <p>
 * //商品
 *
 * @property (nonatomic, copy) NSArray * item;
 * @property (nonatomic, copy) NSString * welfare;//": 5.696,            // 公益金额
 * @property (nonatomic, copy) NSString * is_welfare;//": 1,   // 是否存在公益金额    （1存在，0不存在）
 * @property (nonatomic, copy) NSString * after_sale_status;//": 1,   // 是否存在售后服务类型    （1存在，0不存在）
 * @property (nonatomic, copy) NSString * after_sale_type;//": "商家承诺在您签收货物后的7天内，在无人为损坏的情形下，向您提供退货服务。",                      // 售后服务类型
 * @property (nonatomic, copy) NSString * invoice_status;//": 1,   // 是否可开发票    （1存在，0不存在）
 */
public class CollageDetailsAty extends BaseAty {

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
    @ViewInject(R.id.order_freight_tv)
    private TextView order_freight_tv;
    @ViewInject(R.id.space_address)
    private View space_address;

    @ViewInject(R.id.lyLayout)
    private View lyLayout;

    private String is_pay_password = "0";//是否设置支付密码

    private CommonPopupWindow commonPopupWindow;
    private Map<String, String> clickMap; // 点击列表中按钮时选择的项
    private View clickView; // 点击时创建Dialog需要传入的View
    private int clickIndex; // 确认收货时点击的商品信息索引

    @ViewInject(R.id.lin_logistics) // 订单物流入口
    private LinearLayout lin_logistics;
    private String type2WL = null;//给物流的type  0 普通商品 1 拼单购
    private boolean isTy = false;
    private Space buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        //        isTy = getIntent().getBooleanExtra("isTy", false);
        titlt_conter_tv.setText(" ");
        details_order_sc.smoothScrollTo(0, 0);
        bot_for_order.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

    }

    @Override
    @OnClick({R.id.tv_btn_left, R.id.tv_btn_right, R.id.lin_logistics})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_btn_left: // 最底部左侧按钮
                // 申请售后
                startActivity(ApplyForAfterSalesAty.class, null);
                break;
            case R.id.tv_btn_right: // 最底部右侧按钮
                // 评价商品
                break;
            case R.id.lin_logistics: // 订单物流
                Bundle bundle = new Bundle();
                bundle.putString("order_id", order_id);
                bundle.putString("type", type2WL);//普通商品 0 拼单购 1
                startActivity(OrderLogisticsAty.class, bundle);
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
        buttom = (Space) findViewById(R.id.pay_buttom);
    }

    @Override
    protected void onResume() {
        super.onResume();
        efreshPage(); // 刷新界面
    }

    /**
     * 刷新界面
     */
    private void efreshPage() {
        if (TextUtils.isEmpty(order_id)) {
            order_id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
        }
        if (type.equals("3")) {//拼单购
            type2WL = "1";
            GroupBuyOrder.details(order_id, this);
        }
    }

    @Override
    protected void requestData() {
        tv_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("3")) {
                    if (order_status.equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        bundle.putString("group_buy_id", group_buy_id);
                        bundle.putString("type", String.valueOf(Integer.parseInt(order_type) + 1));
                        bundle.putString("is_pay_password", is_pay_password);
                        startActivity(PayForAppAty.class, bundle);
                    } else if (order_status.equals("3")) {
                        GroupBuyOrder.receiving(order_id, "", CollageDetailsAty.this);
                        showProgressDialog();
                    } else if (order_status.equals("4")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id", order_id);
                        bundle.putString("type", type);
                        startActivity(EvaluationReleaseAty.class, bundle);
                    } else if (order_status.equals("5") || order_status.equals("6")) {

                        new AlertDialog(CollageDetailsAty.this).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                GroupBuyOrder.deleteOrder(order_id, CollageDetailsAty.this);
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
                new AlertDialog(CollageDetailsAty.this).builder()
                        .setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type.equals("3")) {
                            if (order_status.equals("0")) {
                                GroupBuyOrder.cancelOrder(order_id, CollageDetailsAty.this);
                                showProgressDialog();
                            }
                        }
                        efreshPage();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuctionOrder.CancelOrder(order_id, CollageDetailsAty.this);
                        showProgressDialog();
                    }
                }).show();
            }
        });
    }

    Map<String, String> data;
    List<Map<String, String>> list;
    private boolean isCJ = false;

    private int calcGoodsNum() {
        int numSum = 0;
        for (int i = 0; i < list.size(); i++) {
            String numStr = list.get(i).get("goods_num");
            if (TextUtils.isEmpty(numStr)) {
                continue;
            } else {
                numSum += Integer.parseInt(numStr);
            }
        }
        return numSum;
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        L.e("wang", requestUrl + "=======>>>>>>>>>" + jsonStr);
        super.onComplete(requestUrl, jsonStr);

        // 返回的Url中有好多字段包含在其他返回值中，所以字段截取进行精确匹配
        String[] split = requestUrl.split("/");
        String requestUrlSplit = split[split.length - 1];
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrlSplit.equals("details") || requestUrlSplit.equals("preDetails")) {
            data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
            /**
             * "order_status": "4",//订单状态 （0待支付 1拼单中 2待发货 3 待收货 4 待评价 5 已完成  6已取消 8未拼成 9删除  拼单购
             */
            order_status = data.get("order_status");
            if (data.containsKey("order_type")) {
                switch (data.get("order_type")) {///"1" //订单类型 1体验品拼单 2开团 3参团
                    case "1": {
                        isTy = true;
                    }
                    break;
                    default: {
                        isTy = false;
                    }
                }
            }

            if (isTy) {
                lin_logistics.setVisibility(View.GONE);
            }

            if ("7".equals(order_status) || "10".equals(order_status) || "8".equals(order_status)) {//7:待抽奖 10:未中奖   8:未拼成
                isCJ = true;
                if (order_status.equals("8")) {
                    tv_state.setText("未拼成");
                } else {
                    tv_state.setText("7".equals(order_status) ? "待抽奖" : "未中奖");
                }
                lin_logistics.setVisibility(View.GONE);
                space_address.setVisibility(View.GONE);
                lyLayout.setVisibility(View.GONE);
                bot_for_order.setVisibility(View.GONE);
                list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                tv_merchant_name.setText(data.get("merchant_name"));
                goods_for_this_order_lv.setAdapter(thisAdapter);
                tv_name.setText(data.get("user_name"));
                tv_tel.setText(data.get("phone"));
                tv_address.setText(data.get("address"));
                order_freight_tv.setText(Double.parseDouble(data.get("freight")) > 0 ? data.get("freight") + "元" : "包邮");
                setSumTxt();

                tv_order_sn.setText("订单编号：" + data.get("order_sn"));
                L.e("订单编号：" + data.get("order_sn"));
                tv_create_time.setText("创建时间：" + data.get("create_time"));
                tv_pay_time.setText("付款时间：" + data.get("pay_time"));
            } else {

                isCJ = false;
                if (order_status.equals("0") || order_status.equals("2")) {
                    // 如果订单状态为待付款或待发货，则隐藏订单详情查看入口
                    lin_logistics.setVisibility(View.GONE);
                }
                if (type.equals("3")) {
                    setGroupBuyOrderStatus();
                }
                tv_name.setText(data.get("user_name"));
                tv_tel.setText(data.get("phone"));
                tv_address.setText(data.get("address"));
                tv_logistics.setText(data.get("logistics"));
                tv_logistics_time.setText(data.get("logistics_time"));
                leave_message.setText(data.get("leave_message"));
                tv_merchant_name.setText(data.get("merchant_name"));
                order_freight_tv.setText(Double.parseDouble(data.get("freight")) > 0 ? data.get("freight") + "元" : "包邮");
                list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                setSumTxt();
                tv_order_sn.setText("订单编号：" + data.get("order_sn"));
                L.e("订单编号：" + data.get("order_sn"));
                is_pay_password = data.get("is_pay_password");
                tv_create_time.setText("创建时间：" + data.get("create_time"));
                tv_pay_time.setText("付款时间：" + data.get("pay_time"));
                if (type.equals("3")) {
                    group_buy_id = data.get("group_buy_id");
                    order_type = data.get("order_type");
                }
                goods_for_this_order_lv.setAdapter(thisAdapter);
            }
        }

        if (requestUrlSplit.equals("cancelOrder") ||
                requestUrlSplit.equals("preCancelOrder") ||
                requestUrlSplit.equals("CancelOrder")) {
            if (type.equals("3")) {
                GroupBuyOrder.details(order_id, this);
            }
            showProgressDialog();

        }
        if (requestUrlSplit.equals("receiving") ||
                requestUrlSplit.equals("preReceiving") ||
                requestUrlSplit.equals("Receiving")) {
            if (type.equals("3")) {
                GroupBuyOrder.details(order_id, this);
            }
            showProgressDialog();

        }
        if (requestUrlSplit.equals("deleteOrder") ||
                requestUrlSplit.equals("preDeleteOrder") ||
                requestUrlSplit.equals("DeleteOrder")) {
            showToast("删除成功！");
            finish();

        }
        if (requestUrlSplit.equals("delayReceiving")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            showToast(data.get("message"));
        }
        if (requestUrlSplit.equals("remind")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            efreshPage();
            showToast(data.get("message"));
        }
        // 验证密码回传结果
        if (requestUrlSplit.equals("verificationPayPwd")) {
            String requestCodeStr = data.get("code");
            if (requestCodeStr.equals("1")) {
                if (clickMap.get("sure_status").equals("1")) { // 如果该商品存在七天无理由退换货 1存在 0不存在
                    showPwdPop(clickView, 0);
                    efreshPage(); // 刷新界面
                } else {
                    GroupBuyOrder.receiving(order_id, "", CollageDetailsAty.this);
                    showProgressDialog();
                }
            } else {
                showToast(data.get("message"));
            }
        }

    }

    private void setSumTxt() {
        double total_price = 0.00f;//总价格
        BigDecimal bd = null;
        total_price = Double.parseDouble(data.get("order_price"));
        String ticket_color = null;
        if (data.containsKey("ticket_color")) {
            switch (data.get("ticket_color")) {
                case "1":
                    bd = new BigDecimal(total_price - Double.parseDouble(data.get("pay_tickets")));
                    ticket_color = "红券";
                    break;
                case "2":
                    bd = new BigDecimal(total_price - Double.parseDouble(data.get("pay_tickets")));
                    ticket_color = "黄券";
                    break;
                case "3":
                    bd = new BigDecimal(total_price - Double.parseDouble(data.get("pay_tickets")));
                    ticket_color = "蓝券";
                    break;
            }
            order_price_info_tv.setText(Html.fromHtml("共" + calcGoodsNum() + "件商品 合计： <font color='#DF3031'>¥" + (data.get("ticket_color").equals("0") ? data.get("order_price") :
                    (bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "(已抵" + data.get("pay_tickets") + ticket_color + ")")) + "</font>"));
        }
    }

    private void setGroupBuyOrderStatus() {
        switch (order_status) {
            case "0":
                //                titlt_conter_tv.setText("待支付订单");
                titlt_conter_tv.setText("订单详情");
                tv_state.setText("待支付");
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("付款");
                //                layout_choose_address.setVisibility(View.GONE);
                lin_logistics.setVisibility(View.GONE);
                break;
            case "1":
                //                titlt_conter_tv.setText("拼单中订单");
                titlt_conter_tv.setText("订单详情");
                tv_state.setText("拼单中");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                bot_for_order.setVisibility(View.GONE);
                buttom.setVisibility(View.GONE);
                lin_logistics.setVisibility(View.GONE);
                break;
            case "2":
                //                titlt_conter_tv.setText("待发货订单");
                titlt_conter_tv.setText("订单详情");
                tv_state.setText("待发货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                bot_for_order.setVisibility(View.GONE);
                buttom.setVisibility(View.GONE);
                lin_logistics.setVisibility(View.GONE);
                break;
            case "3":
                //                titlt_conter_tv.setText("待收货订单");
                titlt_conter_tv.setText("订单详情");
                tv_state.setText("待收货");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("确认收货");
                tv_btn_right.setVisibility(View.GONE);
                lin_logistics.setVisibility(View.VISIBLE);
                break;
            case "4":
                //                titlt_conter_tv.setText("待评价订单");
                titlt_conter_tv.setText("订单详情");
                tv_state.setText("待评价");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setText("评价");
                bot_for_order.setVisibility(View.VISIBLE);
                lin_logistics.setVisibility(View.VISIBLE);
                break;
            case "5":
                //                titlt_conter_tv.setText("已完成订单");
                titlt_conter_tv.setText("订单详情");
                tv_state.setText("已完成");
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                bot_for_order.setVisibility(View.VISIBLE);
                lin_logistics.setVisibility(View.VISIBLE);
                break;
            case "6":
                //                titlt_conter_tv.setText("已取消订单");
                titlt_conter_tv.setText("订单详情");
                tv_state.setText("已取消");
                tv_btn_left.setVisibility(View.GONE);
                layout_choose_address.setVisibility(View.GONE);
                tv_btn_right.setText("删除");
                bot_for_order.setVisibility(View.VISIBLE);
                break;
            case "7": {

            }
            break;
            case "10": {

            }
            break;
        }
    }

    private class ThisGoodsAdapter extends BaseAdapter {

        private ThisGoodsAdapter.TGVH tgvh;

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
                view = LayoutInflater.from(CollageDetailsAty.this).inflate(R.layout.item_goods_for_this_order_lv, null);
                tgvh = new ThisGoodsAdapter.TGVH();
                ViewUtils.inject(tgvh, view);
                view.setTag(tgvh);
            } else {
                tgvh = (ThisGoodsAdapter.TGVH) view.getTag();
            }
            Map<String, String> map = getItem(i);
            tgvh.confirmReceipt.setVisibility(order_status.equals("0") ? View.VISIBLE : View.GONE); // 设置中间催发货按钮为隐藏
            L.e("order_sta" + order_status);

            L.e("wang", "status = " + map.get("status") + "\tgetItem:" + map);
            /**
             * "order_status": "4",//订单状态 （0待支付 1拼单中 2待发货 3 待收货 4 待评价 5 已完成  6已取消 8未拼成 9删除  拼单购
             */
            if (order_status.equals("0") || order_status.equals("1") || order_status.equals("6")) { // 订单为0待支付或1拼单中或6已取消
                tgvh.applyAfterSaleTv.setVisibility(View.GONE); // 右侧按钮隐藏
                tgvh.confirmReceipt.setVisibility(View.GONE); // 中间按钮隐藏
            } else {
                if ("付款".equals(String.valueOf(tgvh.confirmReceipt.getText()))) {
                    tgvh.confirmReceipt.setVisibility(View.GONE); // 20180519  2待发货、3待收货、4待评价、5已完成 而且按钮上显示付款时候 隐藏付款按钮
                } else {
                    tgvh.confirmReceipt.setVisibility(View.VISIBLE); // 20180519  2待发货、3待收货、4待评价、5已完成 而且按钮上显示付款时候 隐藏付款按钮
                }
                tgvh.applyAfterSaleTv.setVisibility(View.VISIBLE); // 否则订单状态为2待发货、3待收货、4待评价、5已完成
            }

            if (map.containsKey("auto_time") && !TextUtils.isEmpty(map.get("auto_time"))) {
                tgvh.delayReceivingtv.setText(map.get("auto_time"));
                tgvh.delayReceivingtv.setVisibility(View.VISIBLE);
            } else {
                tgvh.delayReceivingtv.setVisibility(View.GONE);
            }

            if (isCJ) {
                //抽奖与未中奖
                tgvh.layout_fapiao.setVisibility(View.GONE);
                tgvh.lin_shouhou.setVisibility(View.GONE);
                tgvh.layout_gongyi.setVisibility(View.GONE);
                tgvh.lin_server_status.setVisibility(View.GONE);
                tgvh.bot_for_order.setVisibility(View.GONE);
                tgvh.itemGoods_goods_layout.setVisibility(View.VISIBLE);
                Glide.with(CollageDetailsAty.this).load(map.get("goods_img")).into(tgvh.image);
                if (isTy) {
                    tgvh.tyIv.setVisibility(View.VISIBLE);
                } else {
                    tgvh.tyIv.setVisibility(View.GONE);
                }
                tgvh.name.setText(map.get("goods_name")); // 设置商品名称显示
                tgvh.tv_price.setText("¥" + map.get("shop_price")); // 设置订单中商品价格
                tgvh.tv_price.setVisibility(View.VISIBLE); // 显示订单中商品价格
//                ChangeTextViewStyle.getInstance().forTextColor(CollageDetailsAty.this, tgvh.title,
//                        map.get("attr") + "（赠送:" + map.get("return_integral") + "积分）", map.get("attr").length(), Color.parseColor("#F6B87A"));


                if (map.containsKey("is_invoice") && Integer.parseInt(map.get("is_invoice")) == 1) {
                    // 是否开发票，1为开发票，显示该控件，否则为0，不开发票，隐藏该控件
                    tgvh.layout_fapiao.setVisibility(View.VISIBLE);
                    tgvh.textviews.setVisibility(View.VISIBLE); // 设置发票名称的控件显示或隐藏
                    //                    tgvh.textviews.setText(map.get("invoice_name")); // 设置发票名称
                    tgvh.textviews.setText("(发票运费：" + map.get("express_fee") + " 税金：" + map.get("tax_pay") + ")"); // 设置发票名称
                } else {
                    tgvh.layout_fapiao.setVisibility(View.GONE);
                }

                tgvh.num.setText("x" + map.get("goods_num")); // 设置商品数量显示
                tgvh.itemGoods_goods_layout.setTag(i);
                tgvh.itemGoods_goods_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) v.getTag();
                        Bundle bundle = new Bundle();
                        if ("3".equals(type)) {//拼单购
                            if (getItem(i).containsKey("group_buy_id")) {//拼单购
                                bundle.putString("group_buy_id", getItem(i).get("group_buy_id"));
                                bundle.putInt("from", 1);
                                if (getItem(i).containsKey("a_id")) {
                                    bundle.putString("a_id", getItem(i).get("a_id"));//  "a_id": "1",      //体验品活动id，非体验品拼单时此id为0
                                }
                                startActivity(GoodLuckDetailsAty.class, bundle);
                            }
                        }
                    }
                });
            } else {
                // 设置右侧按钮显示的文字
                /**
                 *  "after_type":"0" //0 申请售后  1售后中 2售后完成 3售后拒绝
                 */
                switch (Integer.parseInt(map.get("after_type"))) {
                    case 0:
                        tgvh.applyAfterSaleTv.setText("申请售后");
                        break;
                    case 1:
                        tgvh.applyAfterSaleTv.setText("售后中");
                        break;
                    case 2:
                        tgvh.applyAfterSaleTv.setText("售后完成");
                        break;
                    case 3:
                        tgvh.applyAfterSaleTv.setText("售后拒绝");
                        break;
                }
                if (order_status.equals("2")) { // 订单状态待发货
                    tgvh.applyAfterSaleTv.setVisibility(View.VISIBLE); // 右侧按钮申请售后显示
                    if ("付款".equals(String.valueOf(tgvh.confirmReceipt.getText()))) {
                        tgvh.confirmReceipt.setVisibility(View.GONE); // 20180519  1待发货、2待收货、3待评价、4已完成 而且按钮上显示付款时候 隐藏付款按钮
                    } else {
                        tgvh.confirmReceipt.setVisibility(View.VISIBLE); // 中间按钮催发货显示
                    }
                    if (Integer.valueOf(map.get("remind_status")) == 0) {
                        tgvh.tv_btn_remind.setVisibility(View.VISIBLE);
                    } else {
                        tgvh.tv_btn_remind.setVisibility(View.GONE);
                    }

                } else if (order_status.equals("3")) { // 订单待收货状态
                    if (Integer.valueOf(map.get("status")) > 1 && map.containsKey("sale_status")) {
                        tgvh.delayReceiving.setVisibility(map.get("sale_status").equals("0") ? View.VISIBLE : View.GONE); // 延长收货按钮显示
                        //                        tgvh.delayReceivingtv.setVisibility(map.get("sale_status").equals("0") ? View.GONE : View.VISIBLE);
                        //                        tgvh.delayReceivingtv.setText(map.get("sale_status").equals("0") ? "" : map.get("auto_time"));
                    } else {
                        tgvh.delayReceiving.setVisibility(View.GONE); // 延长收货按钮显示
                    }
                    tgvh.tv_btn_remind.setVisibility(View.GONE);
                    tgvh.confirmReceipt.setText("确认收货"); // 订单待收货状态下设置中间按钮为：确认收货
                    tgvh.applyAfterSaleTv.setVisibility(View.VISIBLE);
                    tgvh.confirmReceipt.setVisibility(View.VISIBLE); // 中间的确认收货显示
                } else if (order_status.equals("6")) { // 订单为已取消状态
                    tgvh.confirmReceipt.setVisibility(View.GONE); // 隐藏付款按钮
                    tgvh.applyAfterSaleTv.setVisibility(View.GONE); // 申请售后按钮隐藏
                    tgvh.delayReceiving.setVisibility(View.GONE); // 延长收货隐藏
                    tgvh.tv_btn_remind.setVisibility(View.GONE); // 提醒发货按钮隐藏
                }

                tgvh.tv_price.setText("¥" + map.get("shop_price")); // 设置订单中商品价格
                tgvh.tv_price.setVisibility(View.VISIBLE); // 显示订单中商品价格
                tgvh.itemGoods_goods_layout.setTag(i);
                // 商品点击跳转至商品详情
                tgvh.itemGoods_goods_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) v.getTag();
                        Bundle bundle = new Bundle();
                        if ("3".equals(type)) {//拼单购
                            if (getItem(i).containsKey("group_buy_id")) {//拼单购
                                bundle.putString("group_buy_id", getItem(i).get("group_buy_id"));
                                bundle.putInt("from", 1);
                                if (getItem(i).containsKey("a_id")) {
                                    bundle.putString("a_id", getItem(i).get("a_id"));//  "a_id": "1",      //体验品活动id，非体验品拼单时此id为0
                                }
                                startActivity(GoodLuckDetailsAty.class, bundle);
                            }
                        }
                    }
                });
                tgvh.applyAfterSaleTv.setTag(i);
                // 右侧按钮点击事件
                tgvh.applyAfterSaleTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, String> map = getItem((Integer) v.getTag());
                        if (map.get("after_type").equals("0")) { // 如果订单中商品为申请售后
                            // 跳转至申请售后界面发起售后
                            Bundle bundle = new Bundle();
                            //                        bundle.putString("price", String.valueOf(Double.parseDouble(map.get("shop_price")) * Integer.parseInt(map.get("goods_num"))));
                            if (map.containsKey("refund_price")) {
                                bundle.putString("maxPrice", String.valueOf(Double.parseDouble(map.get("refund_price")))); // 此商品可退换的全部金额
                            }
                            bundle.putString("order_goods_id", map.get("order_goods_id"));
                            bundle.putString("order_id", order_id);
                            bundle.putString("type", type);
                            startActivity(ApplyForAfterSalesAty.class, bundle);
                        } else { // 否则商品状态为售后中或已完成
                            // 跳转至查看售后详情信息的界面
                            Bundle bundle = new Bundle();
                            bundle.putString("is_sales", map.get("is_sales"));
                            bundle.putString("after_type", map.get("after_type"));
                            bundle.putString("back_apply_id", map.get("back_apply_id"));

                            // 继续申请售后需要传的参数
                            bundle.putString("price", String.valueOf(Double.parseDouble(map.get("refund_price"))));
                            bundle.putString("order_goods_id", map.get("order_goods_id"));
                            bundle.putString("order_id", order_id);
                            bundle.putString("type", type);

                            startActivity(After_aty.class, bundle);
                        }
                        efreshPage();
                    }
                });
                // 中间按钮点击事件
                tgvh.confirmReceipt.setTag(i);
                tgvh.confirmReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickIndex = (int) v.getTag();
                        //                    clickIndex = i;
                        //                    clickMap = list.get(i); // 点击时选的列表项
                        clickMap = list.get(clickIndex); // 点击时选的列表项
                        clickView = v; // View
                        showPwxPopWindow(v); // 显示弹框提示输入密码
                        // 思路：点击确认收货按钮，先弹出密码输入框，验证成功后弹出是否放弃权益提示框，让用户选择
                        efreshPage();
                    }
                });

                Glide.with(CollageDetailsAty.this).load(map.get("goods_img")).into(tgvh.image);
                tgvh.name.setText(map.get("goods_name")); // 设置商品名称显示
                tgvh.num.setText("x" + map.get("goods_num")); // 设置商品数量显示


                String return_integral = map.get("return_integral");
                if (Double.parseDouble(return_integral) == 0){
                    ChangeTextViewStyle.getInstance().forTextColor(CollageDetailsAty.this, tgvh.title,
                            map.get("attr"), map.get("attr").length(), Color.parseColor("#F6B87A"));
                }else {
                    ChangeTextViewStyle.getInstance().forTextColor(CollageDetailsAty.this, tgvh.title,
                            map.get("attr") + "（赠送:" + return_integral + "积分）", map.get("attr").length(), Color.parseColor("#F6B87A"));
                }

                L.e("time" + map.get("sure_delivery_time"));
                if (map.containsKey("is_invoice") && Integer.parseInt(map.get("is_invoice")) == 1) {
                    // 是否开发票，1为开发票，显示该控件，否则为0，不开发票，隐藏该控件
                    tgvh.layout_fapiao.setVisibility(View.VISIBLE);
                    tgvh.textviews.setVisibility(View.VISIBLE); // 设置发票名称的控件显示或隐藏
                    tgvh.textviews.setText("(发票运费：" + map.get("express_fee") + " 税金：" + map.get("tax_pay") + ")"); // 设置发票名称
                } else {
                    tgvh.layout_fapiao.setVisibility(View.GONE);
                }
                // 正品保证
                tgvh.layout_zhengpinbaozheng.setVisibility(map.get("integrity_a").isEmpty() ? View.GONE : View.VISIBLE);
                tgvh.tv_zhengpinbaozheng.setText(map.get("integrity_a").isEmpty() ? "" : map.get("integrity_a"));
                // 服务承诺
                tgvh.layout_fuwuchengnuo.setVisibility(map.get("integrity_b").isEmpty() ? View.GONE : View.VISIBLE);
                tgvh.tv_fuwuchengnuo.setText(map.get("integrity_b").isEmpty() ? "" : map.get("integrity_b"));
                // 发货时间
                tgvh.layout_fahuoshijian.setVisibility(map.get("integrity_c").isEmpty() ? View.GONE : View.VISIBLE);
                tgvh.tv_fahuoshijian.setText(map.get("integrity_c").isEmpty() ? "" : map.get("integrity_c"));


                String integrity_dStr = map.get("integrity_d");
                if (!TextUtils.isEmpty(integrity_dStr)) {
                    // 公益宝贝
                    tgvh.layout_gongyi.setVisibility(View.VISIBLE);
                    tgvh.tv_gongyi.setText(integrity_dStr);
                } else {
                    tgvh.layout_gongyi.setVisibility(View.GONE);
                    tgvh.tv_gongyi.setText("");
                }

                String afterSaleStatusStr = map.get("after_sale_status");
                if (!TextUtils.isEmpty(afterSaleStatusStr) && "1".equals(afterSaleStatusStr)) {
                    String after_sale_typeStr = map.get("after_sale_type");
                    if (!TextUtils.isEmpty(after_sale_typeStr)) {
                        //售后服务
                        tgvh.lin_shouhou.setVisibility(View.VISIBLE);
                        tgvh.tv_shouhou.setText(after_sale_typeStr);
                    } else {
                        tgvh.lin_shouhou.setVisibility(View.GONE);
                        tgvh.tv_shouhou.setText("");
                    }
                }


                // 左侧延长收货按钮点击事件
                tgvh.delayReceiving.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupBuyOrder.delayReceiving(list.get(i).get("order_goods_id"), CollageDetailsAty.this); // 请求后台延长收货接口
                        showProgressDialog(); // 显示加载框
                        efreshPage();
                    }
                });
                // 提醒发货按钮
                tgvh.tv_btn_remind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("3".equals(type)) {
                            GroupBuyOrder.remind(CollageDetailsAty.this, order_id);
                        }
                        showProgressDialog(); // 显示加载框
                        efreshPage();
                    }
                });


                if (order_status.equals("0")) { // 订单为待支付状态
                    tgvh.confirmReceipt.setVisibility(View.GONE); // 付款按钮显示
                    tgvh.applyAfterSaleTv.setVisibility(View.GONE); // 申请售后按钮隐藏
                    tgvh.delayReceiving.setVisibility(View.GONE); // 延长收货隐藏
                    tgvh.tv_btn_remind.setVisibility(View.GONE); // 提醒发货按钮隐藏
                } else if (order_status.equals("6")) { // 订单为已取消状态
                    tgvh.confirmReceipt.setVisibility(View.GONE); // 隐藏付款按钮
                    tgvh.applyAfterSaleTv.setVisibility(View.GONE); // 申请售后按钮隐藏
                    tgvh.delayReceiving.setVisibility(View.GONE); // 延长收货隐藏
                    tgvh.tv_btn_remind.setVisibility(View.GONE); // 提醒发货按钮隐藏
                }
                if (isTy) {
                    tgvh.tyIv.setVisibility(View.VISIBLE);
                } else {
                    tgvh.tyIv.setVisibility(View.GONE);
                }
            }
            //            "is_back_apply":  //是否对应售后服务 0不对应 1对应
            String is_back_apply = map.get("is_back_apply");
            if (!TextUtils.isEmpty(is_back_apply) && "1".equals(is_back_apply)) {
                tgvh.applyAfterSaleTv.setVisibility(View.VISIBLE);
            } else {
                tgvh.applyAfterSaleTv.setVisibility(View.GONE);
            }

            return view;
        }

        class TGVH {
            @ViewInject(R.id.bot_for_order)
            private View bot_for_order;
            @ViewInject(R.id.tyIv)
            private ImageView tyIv;
            @ViewInject(R.id.image)
            private ImageView image;
            @ViewInject(R.id.name)
            private TextView name;
            @ViewInject(R.id.num)
            private TextView num;
            @ViewInject(R.id.title)
            private TextView title;
            //            @ViewInject(R.id.jifenTv)
            //            private TextView jifenTv;
            @ViewInject(R.id.tv_price)
            private TextView tv_price;
            @ViewInject(R.id.applyAfterSaleTv)
            public TextView applyAfterSaleTv;
            @ViewInject(R.id.confirmReceipt)
            public TextView confirmReceipt;
            @ViewInject(R.id.tv_btn_remind)
            public TextView tv_btn_remind;
            @ViewInject(R.id.textview)
            private TextView textview;
            @ViewInject(R.id.itemGoods_invoiceName_tv)
            private TextView textviews;
            @ViewInject(R.id.delayReceiving)
            private TextView delayReceiving;
            @ViewInject(R.id.lin_shouhou)
            private View lin_shouhou;
            @ViewInject(R.id.tv_shouhou)
            private TextView tv_shouhou;
            @ViewInject(R.id.layout_gongyi)
            private View layout_gongyi;
            @ViewInject(R.id.tv_gongyi)
            private TextView tv_gongyi;
            @ViewInject(R.id.lin_server_status)
            private View lin_server_status;

            //            android:id="@+id/tv_btn_right"

            @ViewInject(R.id.layout_fapiao)
            private View layout_fapiao;

            @ViewInject(R.id.layout_zhengpinbaozheng) // 正品保证
            private View layout_zhengpinbaozheng;
            @ViewInject(R.id.tv_zhengpinbaozheng)
            private TextView tv_zhengpinbaozheng;
            @ViewInject(R.id.layout_fuwuchengnuo) // 服务承诺
            private View layout_fuwuchengnuo;
            @ViewInject(R.id.tv_fuwuchengnuo)
            private TextView tv_fuwuchengnuo;
            @ViewInject(R.id.layout_fahuoshijian) // 发货时间
            private View layout_fahuoshijian;
            @ViewInject(R.id.tv_fahuoshijian)
            private TextView tv_fahuoshijian;
            @ViewInject(R.id.itemGoods_goods_layout)
            private View itemGoods_goods_layout;

            @ViewInject(R.id.delayReceivingtv)
            private TextView delayReceivingtv;//订单详情中延长收货时间显示
        }
    }

    public void showPwxPopWindow(View view) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_pwd)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 4)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        final EditText et_password = view.findViewById(R.id.et_password);
                        TextView submit = view.findViewById(R.id.submit);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(et_password.getText().toString())) {
                                    showToast("请输入支付密码");
                                    return;
                                }
                                User.verificationPayPwd(et_password.getText().toString(), CollageDetailsAty.this);
                                commonPopupWindow.dismiss();
                                showProgressDialog();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void showPwdPop(View view, int i) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
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
                        tv2.setText("再等等");
                        tv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GroupBuyOrder.receiving(order_id, "2", CollageDetailsAty.this);
                                L.e("wang", "===============>>>>>>>>>>>>>>> tv1 click status = 1");
                                // 确定放弃七天售后
                                showProgressDialog();
                                commonPopupWindow.dismiss();
                            }
                        });
                        tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                L.e("wang", "===============>>>>>>>>>>>>>>> tv2 click status = 2");
                                GroupBuyOrder.receiving(order_id, "1", CollageDetailsAty.this);
                                showProgressDialog();
                                commonPopupWindow.dismiss();
                                efreshPage();
                            }
                        });
                    }
                }, i)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
