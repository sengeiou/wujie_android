package com.txd.hzj.wjlp.minetoaty.order.fgt;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.bean.UserBalanceHjs;
import com.txd.hzj.wjlp.http.AuctionOrder;
import com.txd.hzj.wjlp.http.CarOrder;
import com.txd.hzj.wjlp.http.GroupBuyOrder;
import com.txd.hzj.wjlp.http.HouseOrder;
import com.txd.hzj.wjlp.http.IntegralBuyOrder;
import com.txd.hzj.wjlp.http.IntegralOrder;
import com.txd.hzj.wjlp.http.PreOrder;
import com.txd.hzj.wjlp.http.UserBalance;
import com.txd.hzj.wjlp.mainfgt.adapter.IndianaRecordAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.MyOrderAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.OnlineChongAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.CreateGroupAty;
import com.txd.hzj.wjlp.minetoaty.PayForAppAty;
import com.txd.hzj.wjlp.minetoaty.order.CollageDetailsAty;
import com.txd.hzj.wjlp.minetoaty.order.EvaluationReleaseAty;
import com.txd.hzj.wjlp.minetoaty.order.OnlineChongDetailsAty;
import com.txd.hzj.wjlp.minetoaty.order.OrderDetailsAty;
import com.txd.hzj.wjlp.new_wjyp.CarOrderInfo;
import com.txd.hzj.wjlp.popAty.LovingAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 11:46
 * 描述：线上商城订单碎片
 */
public class OrderOnLineFgt extends BaseFgt {
    /**
     * 跳转过来的类型
     * 0：普通订单列表
     * 1：汽车购订单列表
     * 2：房产订单列表
     * 3：拼单购订单列表
     * 4：预购订单列表
     * 5：积分订单列表
     * 6：拍品订单列表
     * 8：充值订单列表
     * 10：积分商店订单列表
     */
    private String from = "";
    /**
     * 订单分类
     */
    private String title;
    /**
     * 订单类型
     * 订单状态
     */
    private String type;  //
    @ViewInject(R.id.super_sr_layout)
    private SuperSwipeRefreshLayout swipe_refresh;
    // Header View
    private RelativeLayout head_container;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    @ViewInject(R.id.layout)
    FrameLayout root;
    @ViewInject(R.id.order_on_line_lv)
    private ListView order_on_line_lv;
    private MyOrderAdapter adapter;

    private LovingAdapter lovingAdapter;
    private List<Order> list;
    private List<Order> more;
    private int p = 1;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;
    private GoodsAdapter goodsAdapter;
    private IndianaRecordAdapter indianarecordAdp;
    private String is_pay_password = "0";//是否设置密码
    private UserBalanceHjs userBalanceHjs;

    public OrderOnLineFgt() {

    }


    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        swipe_refresh.setRefreshing(true);
    }

    public static OrderOnLineFgt getFgt(String title, String type, String from) {
        OrderOnLineFgt fgt = new OrderOnLineFgt();
        fgt.type = type == null ? "" : type;//订单状态
        fgt.from = from == null ? "" : from;//从哪里过来
        fgt.title = title == null ? "" : title;
        return fgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //        if (title.equals("抢宝记录")) {
        //            // 0.全部，1.代付款，2.代发货，3.待收货
        //            IndianaRecordAdapter adapter = new IndianaRecordAdapter(getActivity(), list, Integer.parseInt(type));
        //            order_on_line_lv.setAdapter(adapter);//显示全部list
        //        } else if (title.equals("爱心商店")) {
        //        } else {
        //            // 0.全部，1.代付款，2.代发货，3.待收货，4.待评价
        //            adapter = new MyOrderAdapter(getActivity(), list, Integer.parseInt(type));
        //            order_on_line_lv.setAdapter(adapter);//显示全部list
        //        }
        order_on_line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //                L.e("=====title=====", title);
                //                if (title.equals("抢宝记录")) {
                //                    startActivity(GoodLuckOrderDetailsAty.class, null);
                //                } else {
                //                    startActivity(OrderDetailsAty.class, null);
                //                }
                if (from.equals("0")  ) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", goods_list.get(i).get("order_id"));
                    bundle.putString("type", from);
                    startActivity(OrderDetailsAty.class, bundle);
                } else if (from.equals("1") || from.equals("2")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", list.get(i).getOrder_id());
                    bundle.putString("type", from);
                    startActivity(CarOrderInfo.class, bundle);
                } else if (from.equals("3")) {
                    if ("1".equals(goods_list.get(i).get("order_status"))) {
                        Bundle bundle = new Bundle();
                        if ("1".equals(goods_list.get(i).get("group_type"))) {
                            bundle.putString("id", goods_list.get(i).get("group_buy_order_id"));
                            bundle.putString("type", from);
                            bundle.putBoolean("isTy", map_Type.get(i));
                            startActivity(CollageDetailsAty.class, bundle);
                        } else {
                            if ("2".equals(goods_list.get(i).get("order_type"))) {
                                bundle.putString("id", goods_list.get(i).get("group_buy_order_id"));
                            } else if ("3".equals(goods_list.get(i).get("order_type"))) {
                                bundle.putString("id", goods_list.get(i).get("p_id"));
                            }
                            String order_goods = goods_list.get(i).get("order_goods");
                            JSONArray jsonArray = JSONArray.parseArray(order_goods);
                            bundle.putString("integral", ((JSONObject) jsonArray.get(0)).getString("return_integral"));
                            bundle.putString("group_buy_id", goods_list.get(i).get("group_buy_id"));
                            bundle.putInt("status", 0);
                            startActivity(CreateGroupAty.class, bundle);
                        }
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(i).get("group_buy_order_id"));
                        bundle.putString("type", from);
                        bundle.putBoolean("isTy", map_Type.get(i));
                        startActivity(CollageDetailsAty.class, bundle);
                    }
                } else if (from.equals("4")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", goods_list.get(i).get("order_id"));
                    bundle.putString("type", from);
                    startActivity(OrderDetailsAty.class, bundle);
                } else if (from.equals("6")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", goods_list.get(i).get("order_id"));
                    bundle.putString("type", from);
                    startActivity(OrderDetailsAty.class, bundle);
                } else if (from.equals("10")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", goods_list.get(i).get("order_id"));
                    bundle.putString("type", from);
                    startActivity(OrderDetailsAty.class, bundle);
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_on_line_fgt;
    }

    @Override
    protected void initialized() {

    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        if (from.equals("0")) {
            com.txd.hzj.wjlp.http.Order.orderList(type, from, p, this);
        } else if (from.equals("1")) {
            CarOrder.orderList(type, p, OrderOnLineFgt.this);
        } else if (from.equals("2")) {
            HouseOrder.orderList(type, p, OrderOnLineFgt.this);
        } else if (from.equals("3")) {
            GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
        } else if (from.equals("4")) {
            PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
        } else if (from.equals("5")) {
            IntegralOrder.orderList(type, p, OrderOnLineFgt.this);
        } else if (from.equals("6")) {
            AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
        } else if (from.equals("10")) {
            IntegralBuyOrder.OrderList(type, p, OrderOnLineFgt.this);
        } else if (from.equals("8")) {
            UserBalance.userBalanceHjs(type.equals("2") ? "0" : type.equals("3") ? "1" : "", this);
        }

        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
        swipe_refresh.setHeaderViewBackgroundColor(Color.WHITE);
        swipe_refresh.setTargetScrollWithLayout(true);
        swipe_refresh
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        if (from.equals("0")) {
                            com.txd.hzj.wjlp.http.Order.orderList(type, from, p, OrderOnLineFgt.this);
                        } else if (from.equals("1")) {
                            CarOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("2")) {
                            HouseOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("3")) {
                            GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("4")) {
                            PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("5")) {
                            IntegralOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("6")) {
                            AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("10")) {
                            IntegralBuyOrder.OrderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("8")) {
                            UserBalance.userBalanceHjs(type.equals("2") ? "0" : type.equals("3") ? "1" : "", OrderOnLineFgt.this);
                        }
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
                        frist = false;
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);
                        p++;
                        if (from.equals("0") ) {
                            com.txd.hzj.wjlp.http.Order.orderList(type, from, p, OrderOnLineFgt.this);
                        } else if (from.equals("1")) {
                            CarOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("2")) {
                            HouseOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("3")) {
                            GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("4")) {
                            PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("5")) {
                            IntegralOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("6")) {
                            AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("10")) {
                            IntegralBuyOrder.OrderList(type, p, OrderOnLineFgt.this);
                        } else if (from.equals("8")) {
                            UserBalance.userBalanceHjs(type.equals("2") ? "0" : type.equals("3") ? "1" : "", OrderOnLineFgt.this);
                        }
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

    @Override
    protected void requestData() {

    }

    private Map<String, String> data;
    private List<Map<String, String>> goods_list;
    private List<Map<String, String>> goods_more;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("orderList") || requestUrl.contains("OrderList") || requestUrl.contains("preOrderList")) {
            if (from.equals("0") || from.equals("3") || from.equals("4") || from.equals("6") || from.equals("10") ) {
                if (p == 1) {
                    goods_list = JSONUtils.parseKeyAndValueToMapList(data.get("data"));
                    goodsAdapter = new GoodsAdapter();
                    order_on_line_lv.setAdapter(goodsAdapter);
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    goods_more = JSONUtils.parseKeyAndValueToMapList(data.get("data"));
                    goods_list.addAll(goods_more);
                    goodsAdapter.notifyDataSetChanged();
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            } else if (from.equals("1") || from.equals("2")) {
                if (p == 1) {
                    list = GsonUtil.getObjectList(data.get("data"), Order.class);
                    adapter = new MyOrderAdapter(getActivity(), list, from);
                    order_on_line_lv.setAdapter(adapter);
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    more = GsonUtil.getObjectList(data.get("data"), Order.class);
                    list.addAll(more);
                    adapter.notifyDataSetChanged();
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            } else if (from.equals("5")) {
                if (p == 1) {
                    goods_list = JSONUtils.parseKeyAndValueToMapList(data.get("data"));
                    indianarecordAdp = new IndianaRecordAdapter(getActivity(), goods_list);
                    order_on_line_lv.setAdapter(indianarecordAdp);
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    goods_more = JSONUtils.parseKeyAndValueToMapList(data.get("data"));
                    goods_list.addAll(goods_more);
                    indianarecordAdp.notifyDataSetChanged();
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            }
        }

        if (requestUrl.contains("userBalanceHjs")) { // 线上充值订单列表

            L.e("wang", "jsonstr:" + jsonStr);

            Gson gson = new Gson();
            userBalanceHjs = gson.fromJson(jsonStr, UserBalanceHjs.class);

            OnlineChongAdapter onlineChongAdapter = null;
            if (null == order_on_line_lv.getAdapter()) {
                onlineChongAdapter = new OnlineChongAdapter(getActivity(), this);
                order_on_line_lv.setAdapter(onlineChongAdapter);
            } else {
                onlineChongAdapter = (OnlineChongAdapter) order_on_line_lv.getAdapter();
            }
            onlineChongAdapter.getList().clear();
            if (null != userBalanceHjs.getData()) {
                onlineChongAdapter.getList().addAll(userBalanceHjs.getData());
            }
            onlineChongAdapter.notifyDataSetChanged();

            order_on_line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//线上充值明细
                    Intent intent = new Intent(getActivity(), OnlineChongDetailsAty.class);
                    intent.putExtra("status", userBalanceHjs.getData().get(position).getStatus());
                    intent.putExtra("order_id", userBalanceHjs.getData().get(position).getId());
                    startActivity(intent);
                }
            });
            if (!frist) {
                swipe_refresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        }

        if (requestUrl.contains("delHjsInfo")) {
            if (data.get("code").equals("1")) {
                UserBalance.userBalanceHjs(type.equals("2") ? "0" : type.equals("3") ? "1" : "", OrderOnLineFgt.this);
            }
            showToast(data.get("message"));
        }

        if (requestUrl.contains("cancelOrder") || requestUrl.contains("preCancelOrder") || requestUrl.contains("CancelOrder")) {
            showToast("取消成功");
            if (from.equals("0") ) {
                com.txd.hzj.wjlp.http.Order.orderList(type, from, p, this);
            } else if (from.equals("3")) {
                GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("4")) {
                PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("6")) {
                AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("10")) {
                IntegralBuyOrder.OrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("8")) {
                UserBalance.userBalanceHjs(type.equals("2") ? "0" : type.equals("3") ? "1" : "", OrderOnLineFgt.this);
            }
        }
        if (requestUrl.contains("deleteOrder") || requestUrl.contains("preDeleteOrder")) {
            showToast("删除成功");
            if (from.equals("0") ) {
                com.txd.hzj.wjlp.http.Order.orderList(type, from, p, this);
            } else if (from.equals("3")) {
                GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("4")) {
                PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("6")) {
                AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("10")) {
                IntegralBuyOrder.OrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("8")) {
                UserBalance.userBalanceHjs(type.equals("2") ? "0" : type.equals("3") ? "1" : "", OrderOnLineFgt.this);
            }
        }
        if (requestUrl.contains("receiving") || requestUrl.contains("preReceiving")) {
            if (from.equals("0") ) {
                com.txd.hzj.wjlp.http.Order.orderList(type, from, p, this);
            } else if (from.equals("3")) {
                GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("4")) {
                PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("6")) {
                AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("10")) {
                IntegralBuyOrder.OrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("8")) {
                UserBalance.userBalanceHjs(type.equals("2") ? "0" : type.equals("3") ? "1" : "", OrderOnLineFgt.this);
            }
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        //        super.onError(requestUrl, error);
        removeProgressDialog();
        swipe_refresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        swipe_refresh.setLoadMore(false);
        if ("0".equals(error.get("code")) && "暂无订单".equals(error.get("message"))) {
            if (p == 1) {
                goods_list.clear();
            }
            if (goodsAdapter != null) {
                goodsAdapter.notifyDataSetChanged();
            }
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
        View headerView = LayoutInflater.from(swipe_refresh.getContext()).inflate(R.layout.layout_head, null);
        head_container = headerView.findViewById(R.id.head_container);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    Map<Integer, Boolean> map_Type = new HashMap<>();

    class GoodsAdapter extends BaseAdapter {
        ViewHolder holder;
        String mOrderType="0";

        @Override
        public int getCount() {
            return goods_list.size();
        }

        @Override
        public Map<String, String> getItem(int position) {
            return goods_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.order_item_li, null);
                ViewUtils.inject(holder, convertView);
                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }

            if ("3".equals(from)) {//拼单 体验   1试用品拼单 2常规拼单",
                String group_typeStr = getItem(position).get("group_type");
                if (!TextUtils.isEmpty(group_typeStr) && "1".equals(group_typeStr)) {
                    map_Type.put(position, true);
                } else {
                    //常规拼单隐藏标识
                    map_Type.put(position, false);
                }
            }
            final List<Map<String, String>> list_data = JSONUtils.parseKeyAndValueToMapList(getItem(position).get("order_goods"));
            if (getItem(position).containsKey("shop_id") && Integer.parseInt(getItem(position).get("shop_id"))>0){
                holder.title.setText(getItem(position).get("shop_name")+"(分销)");
            }else {
                holder.title.setText(getItem(position).get("merchant_name"));
            }

            String freight = getItem(position).get("freight");
            int num = 0;
            mOrderType=getItem(position).containsKey("order_type")?getItem(position).get("order_type"):"";
            if (list_data.size() > 0) {
                for (int i = 0; i < list_data.size(); i++) {
                    String goods_num = list_data.get(i).get("goods_num");
                    num += Integer.parseInt(goods_num);
                }
            }
            if ("10".equals(from)) {// 积分商店
                holder.goods_price_info_tv.setText("共" + num + "件商品 合计：" + getItem(position).get("order_price") + "积分" + " 运费：" + ("0.00".equals(freight) ? "包邮" : freight + "积分"));
            } else {
                holder.goods_price_info_tv.setText("共" + num + "件商品 合计：¥" + getItem(position).get("order_price") + " 运费：" + ("0.00".equals(freight) ? "包邮" : freight + "元"));
            }
            holder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (from.equals("0") || from.equals("10") ) {
                        setOrderClickright(position);
                    } else if (from.equals("3")) {
                        setGroupBuyOrderClickright(position);
                    } else if (from.equals("4")) {
                        setPreOrderClickright(position);
                    } else if (from.equals("6")) {
                        setAuctionClickright(position);
                    }
                }
            });
            holder.tv_btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (from.equals("0") || from.equals("10") ) {
                        setOrderClickleft(position);
                    } else if (from.equals("3")) {
                        setGroupBuyOrderClickleft(position);
                    } else if (from.equals("4")) {
                        setPreOrderClickleft(position);
                    } else if (from.equals("6")) {
                        setAuctionClickleft(position);
                    }
                }
            });
            is_pay_password = getItem(position).get("is_pay_password");
            L.e("wang", "getItem(position):" + getItem(position));
            // TODO ======================================设置商品显示适配器=======================================================
            holder.goods_for_order_lv.setAdapter(new GoodsForOrderAdapter(list_data, position,mOrderType));
            holder.goods_for_order_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    if (from.equals("0") ) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(position).get("order_id"));
                        bundle.putString("type", from);
                        startActivity(OrderDetailsAty.class, bundle);
                    } else if (from.equals("3")) {//拼团
                        if ("1".equals(goods_list.get(position).get("order_status"))) {//1拼单中
                            Bundle bundle = new Bundle();
                            if ("1".equals(goods_list.get(position).get("group_type"))) {//1试用品拼单
                                bundle.putString("id", goods_list.get(position).get("group_buy_order_id"));
                                bundle.putString("type", from);
                                bundle.putBoolean("isTy", map_Type.get(position));
                                startActivity(CollageDetailsAty.class, bundle);
                            } else {//2常规拼单
                                if ("2".equals(goods_list.get(position).get("order_type"))) {//1直接下单 2拼团 3参团
                                    bundle.putString("id", goods_list.get(position).get("group_buy_order_id"));
                                } else if ("3".equals(goods_list.get(position).get("order_type"))) {
                                    bundle.putString("id", goods_list.get(position).get("p_id"));
                                }
                                String order_goods = goods_list.get(position).get("order_goods");
                                JSONArray jsonArray = JSONArray.parseArray(order_goods);
                                bundle.putString("integral", ((JSONObject) jsonArray.get(i)).getString("return_integral"));
                                bundle.putString("group_buy_id", goods_list.get(position).get("group_buy_id"));
                                bundle.putInt("status", 0);
                                startActivity(CreateGroupAty.class, bundle);
                            }
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", goods_list.get(position).get("group_buy_order_id"));
                            bundle.putString("type", from);
                            bundle.putBoolean("isTy", map_Type.get(position));
                            startActivity(CollageDetailsAty.class, bundle);
                        }
                    } else if (from.equals("4")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(position).get("order_id"));
                        bundle.putString("type", from);
                        startActivity(OrderDetailsAty.class, bundle);
                    } else if (from.equals("6")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(position).get("order_id"));
                        bundle.putString("type", from);
                        startActivity(OrderDetailsAty.class, bundle);
                    } else if (from.equals("10")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(position).get("order_id"));
                        bundle.putString("type", from);
                        startActivity(OrderDetailsAty.class, bundle);
                    }

                }
            });
            if (from.equals("0") || from.equals("10") ) {
                setOrderStatus(position);
            } else if (from.equals("3")) {
                setGroupBuyStatus(position);
            } else if (from.equals("4")) {
                setPreStatus(position);
            } else if (from.equals("6")) {
                setAuctionStatus(position);
            }
            return convertView;
        }


        private void setPreOrderClickright(final int position) {
            if (getItem(position).get("order_status").equals("1") || getItem(position).get("order_status").equals("10")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                bundle.putString("group_buy_id", "");
                bundle.putString("type", "6");
                bundle.putString("is_pay_password", is_pay_password);
                startActivity(PayForAppAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("4")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                bundle.putString("type", from);
                startActivity(EvaluationReleaseAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("3")) {
                PreOrder.preReceiving(getItem(position).get("order_id"), OrderOnLineFgt.this);
                showProgressDialog();
            } else if (getItem(position).get("order_status").equals("6") || getItem(position).get("order_status").equals("5")) {

                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreOrder.preDeleteOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        goods_list.remove(position);
                        notifyDataSetChanged();
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }

        }

        private void setPreOrderClickleft(final int position) {
            if (getItem(position).get("order_status").equals("1")) {
                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreOrder.preCancelOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        goods_list.remove(position);
                        notifyDataSetChanged();
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }


        }

        private void setGroupBuyOrderClickright(final int position) {
            if (getItem(position).get("order_status").equals("0")) {
                String group_buy_id = getItem(position).get("group_buy_id");
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("group_buy_order_id"));
                bundle.putString("group_buy_id", group_buy_id);
                bundle.putString("type", from);
                //                bundle.putString("type", String.valueOf(Integer.parseInt(getItem(position).get("order_type")) + 1));
                bundle.putString("is_pay_password", is_pay_password);
                startActivity(PayForAppAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("4")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("group_buy_order_id"));
                bundle.putString("type", from);
                startActivity(EvaluationReleaseAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("3")) {
                GroupBuyOrder.receiving(getItem(position).get("group_buy_order_id"), "", OrderOnLineFgt.this);
                showProgressDialog();
            } else if (getItem(position).get("order_status").equals("6") || getItem(position).get("order_status").equals("5") || getItem(position).get("order_status").equals("10")) {

                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupBuyOrder.deleteOrder(getItem(position).get("group_buy_order_id"), OrderOnLineFgt.this);
                        goods_list.remove(position);
                        notifyDataSetChanged();
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }

        }

        private void setGroupBuyOrderClickleft(final int position) {
            if (getItem(position).get("order_status").equals("0")) {
                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupBuyOrder.cancelOrder(getItem(position).get("group_buy_order_id"), OrderOnLineFgt.this);
                        goods_list.remove(position);
                        notifyDataSetChanged();
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }


        }

        private void setOrderClickleft(final int position) {
            if (getItem(position).get("order_status").equals("0")) {
                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("取消订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (from.equals("0") ) {
                            com.txd.hzj.wjlp.http.Order.cancelOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        } else {
                            IntegralBuyOrder.CancelOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        }
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        }

        private void setOrderClickright(final int position) {
            if (getItem(position).get("order_status").equals("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                if (from.equals("10")) {
                    bundle.putString("type", from);
                }else {
                    bundle.putString("type", mOrderType);
                }
                bundle.putString("is_pay_password", is_pay_password);
                startActivity(PayForAppAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("3")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                bundle.putString("type", from);
                startActivity(EvaluationReleaseAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("2")) {
                if (from.equals("0") ) {
                    //                    com.txd.hzj.wjlp.http.Order.receiving(getItem(position).get("order_id"), OrderOnLineFgt.this);
                    showProgressDialog();
                } else {
                    IntegralBuyOrder.Receiving(getItem(position).get("order_id"), "", OrderOnLineFgt.this);
                    showProgressDialog();
                }
            } else if (getItem(position).get("order_status").equals("4") || getItem(position).get("order_status").equals("5")) {


                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (from.equals("0") ) {
                            com.txd.hzj.wjlp.http.Order.deleteOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        } else {
                            IntegralBuyOrder.DeleteOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        }
                        goods_list.remove(position);
                        notifyDataSetChanged();
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        }

        private void setOrderStatus(int position) {
            //	积分商店订单状态（'0': '待付款‘ ； '1': '待发货' ； '2': '待收货' ；'3': '待评价'；'4': '已完成；‘5’：取消订单） 默认9（全部）
            switch (getItem(position).get("order_status")) {
                case "0":
                    holder.state.setText("待付款");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.VISIBLE);
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "1":
                    holder.state.setText("待发货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "2":
                    holder.state.setText("待收货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("确认收货");
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "3":
                    holder.state.setText("待评价");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("评价");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    holder.state.setText("已完成");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "5":
                    holder.state.setText("取消订单");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
            }
        }

        private void setGroupBuyStatus(int position) {
            switch (getItem(position).get("order_status")) {
                case "0": {
                    holder.state.setText("待付款");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.bg_color)));
                    holder.tv_btn_left.setVisibility(View.VISIBLE);
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                }
                break;
                case "2": {
                    holder.state.setText("待发货");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                }
                break;
                case "1": {
                    holder.state.setText("拼单中");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                }
                break;
                case "3": {
                    holder.state.setText("待收货");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("确认收货");
                    holder.tv_btn_right.setVisibility(View.GONE);
                }

                break;
                case "4": {
                    holder.state.setText("待评价");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("评价");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.bg_color)));
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                }
                break;
                case "5": {
                    holder.state.setText("已完成");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.bg_color)));
                    holder.tv_btn_right.setVisibility(View.GONE);
                }
                break;
                case "6": {
                    holder.state.setText("取消订单");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.bg_color)));
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                }
                break;
                case "7": {
                    holder.state.setText("待抽奖");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                }

                break;
                case "10": {
                    holder.state.setText("未中奖");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.tv_btn_right.setVisibility(View.GONE);
                }
                break;
                case "8": {
                    holder.state.setText("未拼成");
                    holder.lineColor.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                }
                break;
            }
        }

        private void setPreStatus(int position) {
            switch (getItem(position).get("order_status")) {
                case "0":
                    holder.state.setText("预购中");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "2":
                    holder.state.setText("待发货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "1":
                    holder.state.setText("代付尾款");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.VISIBLE);
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    holder.state.setText("待收货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("确认收货");
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "4":
                    holder.state.setText("待评价");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("评价");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    holder.state.setText("已取消");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "6":
                    holder.state.setText("已完成");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "7":
                    holder.state.setText("待付定金");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.VISIBLE);
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
            }
        }

        private void setAuctionClickright(final int position) {
            switch (getItem(position).get("order_status")) {
                case "1":
                    break;
                case "4":
                    AuctionOrder.Receiving(getItem(position).get("order_id"), OrderOnLineFgt.this);
                    showProgressDialog();
                    break;
                case "5":

                case "6":
                    new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AuctionOrder.DeleteOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                            goods_list.remove(position);
                            notifyDataSetChanged();
                            showProgressDialog();
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                    break;
                case "8":
                    Bundle bundle = new Bundle();
                    bundle.putString("order_id", getItem(position).get("order_id"));
                    bundle.putString("type", from);
                    startActivity(EvaluationReleaseAty.class, bundle);
                    break;
            }
        }

        private void setAuctionClickleft(final int position) {
            switch (getItem(position).get("order_status")) {
                case "1":
                    new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("取消订单").setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AuctionOrder.CancelOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                            showProgressDialog();
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                    break;
            }
        }

        private void setAuctionStatus(int position) {
            switch (getItem(position).get("order_status")) {
                case "1":
                    holder.state.setText("待付款");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.VISIBLE);
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    holder.state.setText("待发货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "4":
                    holder.state.setText("待收货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("确认收货");
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "8":
                    holder.state.setText("待评价");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("评价");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    holder.state.setText("已取消");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "6":
                    holder.state.setText("已完成");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除订单");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "10":
                    holder.state.setText("竞拍中");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "11":
                    holder.state.setText("竞拍成功");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "12":
                    holder.state.setText("竞拍结束");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
            }
        }

        class ViewHolder {
            @ViewInject(R.id.ItemTitle)
            public TextView title;
            @ViewInject(R.id.tv_state)
            public TextView state;

            @ViewInject(R.id.goods_price_info_tv)
            private TextView goods_price_info_tv;

            @ViewInject(R.id.tv_btn_left)
            public TextView tv_btn_left;
            @ViewInject(R.id.tv_btn_right)
            public TextView tv_btn_right;


            @ViewInject(R.id.goods_for_order_lv)
            private ListViewForScrollView goods_for_order_lv;
            @ViewInject(R.id.lineColor)
            private View lineColor;

        }
    }

    private class GoodsForOrderAdapter extends BaseAdapter {

        List<Map<String, String>> list_data;

        GOVH goVh;
        int pPosition;//所在父层位置
        String order_type;

        public GoodsForOrderAdapter(List<Map<String, String>> list_data, int pPosition,String order_type) {
            this.list_data = list_data;
            this.pPosition = pPosition;
            this.order_type=order_type;
        }

        @Override
        public int getCount() {
            return list_data.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list_data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.aty_goods_for_order, null);
                goVh = new GOVH();
                ViewUtils.inject(goVh, view);
                view.setTag(goVh);
            } else {
                goVh = (GOVH) view.getTag();
            }
            if ("3".equals(from) && map_Type.get(pPosition)) {
                goVh.tyIv.setVisibility(View.VISIBLE);
            } else {
                goVh.tyIv.setVisibility(View.GONE);
            }
            Glide.with(getActivity()).load(getItem(i).get("pic")).into(goVh.image);
            goVh.name.setText(getItem(i).get("goods_name"));
            goVh.num.setText("x" + getItem(i).get("goods_num"));
            //            if (!"10".equals(from)) {
            //                goVh.jifenTv.setVisibility(View.VISIBLE);
            //                goVh.jifenTv.setText("(赠送：" + getItem(i).get("return_integral") + "积分）");
            //            } else {//积分商店
            //                goVh.jifenTv.setVisibility(View.GONE);
            //            }
            L.e("wang", "===============>>>>>>>>>>>>.minetoAty.order.fgt.getItem(i)" + getItem(i));
            //            goVh.textview.setText("最晚发货时间");
            // TODO ============================================时间、积分设置=========================================================


            if (TextUtils.isEmpty(getItem(i).get("goods_attr"))) {
                goVh.title.setVisibility(View.GONE);
            } else {
                goVh.title.setVisibility(View.VISIBLE);
                String goods_attr_str = "规格" + getItem(i).get("goods_attr");
                if ("13".equals(order_type)){
                    ChangeTextViewStyle.getInstance().forTextColor(getActivity(), goVh.title,
                            goods_attr_str, goods_attr_str.length(), Color.parseColor("#F6B87A"));
                }else {
                    String jifen = TextUtils.isEmpty(getItem(i).get("return_integral")) ? "" : "（赠送:" + getItem(i).get("return_integral") + "积分)";
                    ChangeTextViewStyle.getInstance().forTextColor(getActivity(), goVh.title,
                            goods_attr_str + jifen, goods_attr_str.length(), Color.parseColor("#F6B87A"));
                }
            }

            if ("13".equals(order_type)){
                    goVh.tv_2980.setVisibility(View.VISIBLE);
            }else {
                goVh.tv_2980.setVisibility(View.GONE);
            }


            if ("10".equals(from)) {
                if (TextUtils.isEmpty(getItem(i).get("use_integral"))) {
                    goVh.tv_price.setVisibility(View.GONE);
                } else {
                    goVh.tv_price.setVisibility(View.VISIBLE);
                    goVh.tv_price.setText(getItem(i).get("use_integral") + "积分");
                }
            } else {
                if (TextUtils.isEmpty(getItem(i).get("shop_price"))) {
                    goVh.tv_price.setVisibility(View.GONE);
                } else {
                    goVh.tv_price.setVisibility(View.VISIBLE);
                    goVh.tv_price.setText("¥" + getItem(i).get("shop_price"));
                }
            }
            return view;
        }

        private class GOVH {
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
            //                        @ViewInject(R.id.jifenTv)
            //            private TextView jifenTv;
            //            @ViewInject(R.id.textview) // 收货时间，最晚发货时间等等
            //            private TextView textview;
            @ViewInject(R.id.goodsForOrder_layout)
            private LinearLayout goodsForOrder_layout;
            @ViewInject(R.id.tyIv)
            private ImageView tyIv;
            @ViewInject(R.id.tv_2980)
            private TextView tv_2980;
        }

    }

}
