package com.txd.hzj.wjlp.minetoAty.order.fgt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.mainFgt.adapter.IndianaRecordAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.MyOrderAdapter;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.minetoAty.order.EvaluationReleaseAty;
import com.txd.hzj.wjlp.minetoAty.order.OrderDetailsAty;
import com.txd.hzj.wjlp.popAty.LovingAdapter;
import com.txd.hzj.wjlp.txunda_lh.CarOrderInfo;
import com.txd.hzj.wjlp.txunda_lh.http.AuctionOrder;
import com.txd.hzj.wjlp.txunda_lh.http.CarOrder;
import com.txd.hzj.wjlp.txunda_lh.http.GroupBuyOrder;
import com.txd.hzj.wjlp.txunda_lh.http.HouseOrder;
import com.txd.hzj.wjlp.txunda_lh.http.IntegralOrder;
import com.txd.hzj.wjlp.txunda_lh.http.PreOrder;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 11:46
 * 描述：线上商城订单碎片
 * ===============Txunda===============
 */
public class OrderOnLineFgt extends BaseFgt {
    private String from = "";
    /**
     * 订单分类
     */
    private String title;
    /**
     * 订单类型
     */
    private String type;
    @ViewInject(R.id.super_sr_layout)
    private SuperSwipeRefreshLayout swipe_refresh;
    // Header View
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

    public OrderOnLineFgt() {

    }

    public static OrderOnLineFgt getFgt(String title, String type, String from) {
        OrderOnLineFgt fgt = new OrderOnLineFgt();
        fgt.type = type;
        fgt.from = from;
        fgt.title = title;
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
                if (from.equals("0")) {
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
                    Bundle bundle = new Bundle();
                    bundle.putString("id", goods_list.get(i).get("group_buy_order_id"));
                    bundle.putString("type", from);
                    startActivity(OrderDetailsAty.class, bundle);
                } else if (from.equals("4")) {
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
    protected void requestData() {
        if (from.equals("0")) {
            com.txd.hzj.wjlp.txunda_lh.http.Order.orderList(type, from, p, this);
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
        }
        swipe_refresh.setHeaderViewBackgroundColor(0xff888888);
        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
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
                            com.txd.hzj.wjlp.txunda_lh.http.Order.orderList(type, from, p, OrderOnLineFgt.this);
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
                        if (from.equals("0")) {
                            com.txd.hzj.wjlp.txunda_lh.http.Order.orderList(type, from, p, OrderOnLineFgt.this);
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

    private Map<String, String> data;
    private List<Map<String, String>> goods_list;
    private List<Map<String, String>> goods_more;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("orderList") || requestUrl.contains("OrderList") || requestUrl.contains("preOrderList")) {
            if (from.equals("0") || from.equals("3") || from.equals("4") || from.equals("6")) {
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
        if (requestUrl.contains("cancelOrder") || requestUrl.contains("preCancelOrder")) {
            showToast("取消成功");
            if (from.equals("0")) {
                com.txd.hzj.wjlp.txunda_lh.http.Order.orderList(type, from, p, this);
            } else if (from.equals("3")) {
                GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("4")) {
                PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("6")) {
                AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
            }
        }
        if (requestUrl.contains("deleteOrder") || requestUrl.contains("preDeleteOrder")) {
            showToast("删除成功");
            if (from.equals("0")) {
                com.txd.hzj.wjlp.txunda_lh.http.Order.orderList(type, from, p, this);
            } else if (from.equals("3")) {
                GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("4")) {
                PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("6")) {
                AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
            }
        }
        if (requestUrl.contains("receiving") || requestUrl.contains("preReceiving")) {
            if (from.equals("0")) {
                com.txd.hzj.wjlp.txunda_lh.http.Order.orderList(type, from, p, this);
            } else if (from.equals("3")) {
                GroupBuyOrder.orderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("4")) {
                PreOrder.preOrderList(type, p, OrderOnLineFgt.this);
            } else if (from.equals("6")) {
                AuctionOrder.OrderList(type, p, OrderOnLineFgt.this);
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

    }

    @Override
    protected void immersionInit() {

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

    class GoodsAdapter extends BaseAdapter {
        ViewHolder holder;

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
            return 0;
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
            List<Map<String, String>> list_data = JSONUtils.parseKeyAndValueToMapList(getItem(position).get("order_goods"));
            holder.title.setText(getItem(position).get("merchant_name"));
            holder.goods_price_info_tv.setText("共" + list_data.size() + "件商品 合计：¥" + getItem(position).get("order_price"));
            holder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (from.equals("0")) {
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
                    if (from.equals("0")) {
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

            holder.goods_for_order_lv.setAdapter(new GoodsForOrderAdapter(list_data));
            holder.goods_for_order_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    if (from.equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(position).get("order_id"));
                        bundle.putString("type", from);
                        startActivity(OrderDetailsAty.class, bundle);
                    } else if (from.equals("3")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(position).get("group_buy_order_id"));
                        bundle.putString("type", from);
                        startActivity(OrderDetailsAty.class, bundle);
                    } else if (from.equals("4")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", goods_list.get(position).get("order_id"));
                        bundle.putString("type", from);
                        startActivity(OrderDetailsAty.class, bundle);
                    }

                }
            });
            if (from.equals("0")) {
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
            if (getItem(position).get("order_status").equals("1") || getItem(position).get("order_status").equals("7")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                bundle.putString("group_buy_id", "");
                bundle.putString("type", "6");
                startActivity(PayForAppAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("4")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                startActivity(EvaluationReleaseAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("3")) {
                PreOrder.preReceiving(getItem(position).get("order_id"), OrderOnLineFgt.this);
                showProgressDialog();
            } else if (getItem(position).get("order_status").equals("6") || getItem(position).get("order_status").equals("5")) {

                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreOrder.preDeleteOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }


        }

        private void setGroupBuyOrderClickright(final int position) {
            if (getItem(position).get("order_status").equals("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("group_buy_order_id"));
                bundle.putString("group_buy_id", getItem(position).get("group_buy_id"));
                bundle.putString("type", String.valueOf(Integer.parseInt(getItem(position).get("order_type")) + 1));
                startActivity(PayForAppAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("4")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("group_buy_order_id"));
                startActivity(EvaluationReleaseAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("3")) {
                GroupBuyOrder.receiving(getItem(position).get("group_buy_order_id"), OrderOnLineFgt.this);
                showProgressDialog();
            } else if (getItem(position).get("order_status").equals("6") || getItem(position).get("order_status").equals("5")) {

                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupBuyOrder.deleteOrder(getItem(position).get("group_buy_order_id"), OrderOnLineFgt.this);
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }


        }

        private void setOrderClickleft(final int position) {
            if (getItem(position).get("order_status").equals("0")) {


                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        com.txd.hzj.wjlp.txunda_lh.http.Order.cancelOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        showProgressDialog();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        }

        private void setOrderClickright(final int position) {
            if (getItem(position).get("order_status").equals("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                startActivity(PayForAppAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("3")) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id", getItem(position).get("order_id"));
                startActivity(EvaluationReleaseAty.class, bundle);
            } else if (getItem(position).get("order_status").equals("2")) {
                com.txd.hzj.wjlp.txunda_lh.http.Order.receiving(getItem(position).get("order_id"), OrderOnLineFgt.this);
                showProgressDialog();
            } else if (getItem(position).get("order_status").equals("4") || getItem(position).get("order_status").equals("5")) {


                new AlertDialog(getActivity()).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        com.txd.hzj.wjlp.txunda_lh.http.Order.deleteOrder(getItem(position).get("order_id"), OrderOnLineFgt.this);
                        showProgressDialog();

                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        }

        private void setOrderStatus(int position) {
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
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
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
                    holder.tv_btn_right.setText("删除");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    holder.state.setText("取消订单");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
            }
        }

        private void setGroupBuyStatus(int position) {
            switch (getItem(position).get("order_status")) {
                case "0":
                    holder.state.setText("待付款");
                    holder.tv_btn_left.setText("取消订单");
                    holder.tv_btn_right.setText("付款");
                    holder.tv_btn_left.setVisibility(View.VISIBLE);
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    holder.state.setText("待发货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "1":
                    holder.state.setText("待成团");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setVisibility(View.GONE);
                    break;
                case "3":
                    holder.state.setText("待收货");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("确认收货");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    holder.state.setText("待评价");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("评价");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    holder.state.setText("已完成");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "6":
                    holder.state.setText("取消订单");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
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
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
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
                    holder.tv_btn_right.setText("删除");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "6":
                    holder.state.setText("已完成");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除");
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
                    bundle.putString("order_id", getItem(position).get("order_id"));
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
                        public void onClick(View v) {

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
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
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
                    holder.tv_btn_right.setText("删除");
                    holder.tv_btn_right.setVisibility(View.VISIBLE);
                    break;
                case "6":
                    holder.state.setText("已完成");
                    holder.tv_btn_left.setVisibility(View.GONE);
                    holder.tv_btn_right.setText("删除");
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

        }
    }

    private class GoodsForOrderAdapter extends BaseAdapter {

        List<Map<String, String>> list_data;

        GOVH goVh;

        public GoodsForOrderAdapter(List<Map<String, String>> list_data) {
            this.list_data = list_data;
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.aty_goods_for_order, null);
                goVh = new GOVH();
                ViewUtils.inject(goVh, view);
                view.setTag(goVh);
            } else {
                goVh = (GOVH) view.getTag();
            }
            Glide.with(getActivity()).load(getItem(i).get("pic")).into(goVh.image);
            goVh.name.setText(getItem(i).get("goods_name"));
            goVh.num.setText("x" + getItem(i).get("goods_num"));
            goVh.title.setText("规格" + getItem(i).get("goods_attr"));
            goVh.tv_price.setVisibility(View.VISIBLE);
            goVh.tv_price.setText("¥" + getItem(i).get("shop_price"));
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
        }

    }

}
