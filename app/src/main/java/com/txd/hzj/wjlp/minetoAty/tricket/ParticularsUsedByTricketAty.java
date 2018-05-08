package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.TricketDetailks;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.adapter.StickyExampleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：下午 3:48
 * 描述：15-4购物券使用明细
 * ===============Txunda===============
 */
public class ParticularsUsedByTricketAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 明细列表
     */
    @ViewInject(R.id.tricket_rv)
    private RecyclerView tricket_rv;

    @ViewInject(R.id.tv_sticky_header_view)
    private TextView tv_sticky_header_view;

    private List<TricketDetailks> list;

    private StickyExampleAdapter stickyExampleAdapter;

    /**
     * 1.购物券使用明细
     * 2.积分明细
     * 3.余额明细
     * 4.线下充值明细
     * 5.成长值明细
     * 6.线上充值明细
     */
    private int from = 1;

    private UserPst userPst;

    private BalancePst balancePst;

    @ViewInject(R.id.part_swipe_refresh)
    private SuperSwipeRefreshLayout swipe_refresh;
    private int p = 1;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    /**
     * 列表视图
     */
    @ViewInject(R.id.lv_layout)
    private FrameLayout lv_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        L.e("=============from============:" + from);
        if (1 == from) {
            titlt_conter_tv.setText("代金券使用明细");
        } else if (2 == from) {
            titlt_conter_tv.setText("积分明细");
        } else if (3 == from) {
            titlt_conter_tv.setText("余额明细");
        } else if (4 == from) {
            titlt_conter_tv.setText("线下充值明细");
        } else if (5 == from) {
            titlt_conter_tv.setText("成长值明细");
        } else if (6 == from) {
            titlt_conter_tv.setText("线上充值明细");
        }

        tricket_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        tricket_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.findChildViewUnder(
                        tv_sticky_header_view.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tv_sticky_header_view.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tv_sticky_header_view.getMeasuredWidth() / 2, tv_sticky_header_view.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tv_sticky_header_view.getMeasuredHeight();

                    if (transViewStatus == StickyExampleAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tv_sticky_header_view.setTranslationY(dealtY);
                        } else {
                            tv_sticky_header_view.setTranslationY(0);
                        }
                    } else if (transViewStatus == StickyExampleAdapter.NONE_STICKY_VIEW) {
                        tv_sticky_header_view.setTranslationY(0);
                    }
                }

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
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        getData();
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
                        getData();
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
    protected int getLayoutResId() {
        return R.layout.aty_particulars_used_by_tricket;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 1);
        list = new ArrayList<>();
        userPst = new UserPst(this);
        balancePst = new BalancePst(this);
    }

    @Override
    protected void requestData() {
        getData();
    }

    private void getData() {
        switch (from) {
            case 1:// 优惠券使用明细
                userPst.vouchersLog(p);
                break;
            case 2:// 积分明细
                userPst.integralLog(p);
                break;
            case 6:// 线上充值明细
                // TODO 线上充值暂时使用原来的查询余额明细接口，待后台添加接口后改为线上充值
            case 3:// 余额明细
                balancePst.balanceLog(p);
                break;
            case 4:// 线下充值明细
                balancePst.underMoneys(p);
                break;
            case 5:// 成长明细
                userPst.userDevelopLog(p);
                break;
            // TODO 线上充值添加接口
//            case 6:// 线上充值明细
//                userPst.;
//                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

        L.e("wang", requestUrl + "明细列表===========>>>>>>>>>>>>" + jsonStr);

        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("underMoneys")) {
            if (1 == p) {
                if (ToolKit.isList(map, "data")) {
                    list.clear();

                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");

                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            boolean inMemberCoding = false; // 存在会员编号
                            for (Map.Entry<String, String> entry : temp2.entrySet()) {
                                if (entry.getKey().equals("member_coding")) {
                                    inMemberCoding = true;
                                } else {
                                    inMemberCoding = false;
                                }
                            }
                            // 时间， 名称， 创建时间， 金额， 拒绝理由，Id，状态， ， 加减号状态， 图标， 会员编号
                            list.add(new TricketDetailks(time, temp2.get("desc"), temp2.get("create_time"),
                                    temp2.get("money"), temp2.get("refuse_desc"), temp2.get("id"), temp2.get
                                    ("status"), "", temp2.get("add_sub"), temp2.get("img"), inMemberCoding ? temp2.get("member_coding") : ""));
                        }
                    }
                    stickyExampleAdapter = new StickyExampleAdapter(ParticularsUsedByTricketAty.this, list, from);
                    tricket_rv.setAdapter(stickyExampleAdapter);

                    lv_layout.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                } else {
                    lv_layout.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                if (ToolKit.isList(map, "data")) {
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            boolean inMemberCoding = false; // 存在会员编号
                            for (Map.Entry<String, String> entry : temp2.entrySet()) {
                                if (entry.getKey().equals("member_coding")) {
                                    inMemberCoding = true;
                                } else {
                                    inMemberCoding = false;
                                }
                            }
                            list.add(new TricketDetailks(time, "线下充值", temp2.get("create_time"),
                                    temp2.get("money"), temp2.get("refuse_desc"), temp2.get("id"),
                                    temp2.get("status"), "", temp2.get("add_sub"), temp2.get("img"), inMemberCoding ? temp2.get("member_coding") : ""));
                        }
                    }
                    stickyExampleAdapter.notifyDataSetChanged();
                }

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
            return;
        }

        if (requestUrl.contains("vouchersLog")) {
            if (1 == p) {
                if (ToolKit.isList(map, "data")) {
                    list.clear();
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    L.e("vouchersLog.data:" + data.toString());

                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            if (temp2.get("act_type").equals("1")) {
                                list.add(new TricketDetailks(time, "积分转余额 获得", temp2.get("create_time"),
                                        temp2.get("money"), temp2.get("reason"), temp2.get("log_id"), temp2.get
                                        ("act_type"), "", temp2.get("add_sub"), temp2.get("img"), temp2.get("member_coding")));
                            } else if (temp2.get("act_type").equals("2")) {
                                list.add(new TricketDetailks(time, "购买商品 消费", temp2.get("create_time"),
                                        temp2.get("money"), temp2.get("reason"), temp2.get("log_id"), temp2.get
                                        ("act_type"), "", temp2.get("add_sub"), temp2.get("img"), ""));
                            } else {
                                list.add(new TricketDetailks(time, "退还商品 退回", temp2.get("create_time"),
                                        temp2.get("money"), temp2.get("reason"), temp2.get("log_id"), temp2.get
                                        ("act_type"), "", temp2.get("add_sub"), temp2.get("img"), ""));
                            }
                        }
                    }
                    stickyExampleAdapter = new StickyExampleAdapter(this, list, from);
                    tricket_rv.setAdapter(stickyExampleAdapter);

                    lv_layout.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                } else {
                    lv_layout.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {

                if (ToolKit.isList(map, "data")) {
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            if (temp2.get("act_type").equals("1")) {
                                list.add(new TricketDetailks(time, "积分转余额 获得", temp2.get("create_time"),
                                        temp2.get("money"), temp2.get("reason"), temp2.get("log_id"),
                                        temp2.get("act_type"), "", temp2.get("add_sub"), temp2.get("img"), temp2.get("member_coding")));
                            } else if (temp2.get("act_type").equals("2")) {
                                list.add(new TricketDetailks(time, "购买商品 消费", temp2.get("create_time"),
                                        temp2.get("money"), temp2.get("reason"), temp2.get("log_id"),
                                        temp2.get("act_type"), "", temp2.get("add_sub"), temp2.get("img"), ""));
                            } else {
                                list.add(new TricketDetailks(time, "退还商品 退回", temp2.get("create_time"),
                                        temp2.get("money"), temp2.get("reason"), temp2.get("log_id"),
                                        temp2.get("act_type"), "", temp2.get("add_sub"), temp2.get("img"), ""));
                            }
                        }
                    }
                    stickyExampleAdapter.notifyDataSetChanged();
                }

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
            return;
        }
        if (requestUrl.contains("userDevelopLog")) {
            if (1 == p) {
                if (ToolKit.isList(map, "data")) {
                    lv_layout.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                    list.removeAll(list);
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time"); // 悬浮部分
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        L.e("list_temp time:" + time);
                        L.e("list_temp:" + list_temp.toString());
                        for (Map<String, String> temp2 : list_temp) {
                            L.e("list_temp temp2:" + temp2.toString());
                            list.add(new TricketDetailks(time, temp2.get("reason"), temp2.get("create_time"), temp2.get("get_point"), temp2.get("reason"), temp2.get("log_id"), "", "", "", temp2.get("img"), ""));
                        }
                    }
                    L.e("list_temp:" + list.toString());
                    L.e("list_temp from:" + from);
                    stickyExampleAdapter = new StickyExampleAdapter(this, list, from);
                    tricket_rv.setAdapter(stickyExampleAdapter);

                } else {
                    lv_layout.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                if (ToolKit.isList(map, "data")) {
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            list.add(new TricketDetailks(time, temp2.get("reason"), temp2.get("create_time"), temp2.get("get_point"), temp2.get("reason"), temp2.get("log_id"), "", "", temp2.get("add_sub"), temp2.get("img"), temp2.get("act_type").equals("10") ? temp2.get("member_coding") : ""));
                        }
                    }
                    stickyExampleAdapter.notifyDataSetChanged();
                }

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
            return;
        }
        if (requestUrl.contains("integralLog")) {
            if (1 == p) {
                if (ToolKit.isList(map, "data")) {
                    list.clear();
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");// 悬浮部分
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            list.add(new TricketDetailks(time, temp2.get("reason"), temp2.get("create_time"),
                                    temp2.get("use_integral"), temp2.get("reason"), temp2.get("log_id"),
                                    temp2.get("act_type"), "", temp2.get("add_sub"), temp2.get("img"),
                                    temp2.get("act_type").equals("10") ? temp2.get("member_coding") : ""));
                        }
                    }
                    stickyExampleAdapter = new StickyExampleAdapter(this, list, from);
                    tricket_rv.setAdapter(stickyExampleAdapter);

                    lv_layout.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                } else {
                    lv_layout.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                if (ToolKit.isList(map, "data")) {
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get
                                ("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            list.add(new TricketDetailks(time, temp2.get("reason"), temp2.get("create_time"),
                                    temp2.get("use_integral"), temp2.get("reason"), temp2.get("log_id"),
                                    temp2.get("act_type"), "", temp2.get("add_sub"), temp2.get("img"), temp2.get("act_type").equals("10") ? temp2.get("member_coding") : ""));
                        }
                    }
                    stickyExampleAdapter.notifyDataSetChanged();
                }

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
            return;
        }
        if (requestUrl.contains("balanceLog")) {
            if (1 == p) {
                if (ToolKit.isList(map, "data")) {
                    list.clear();
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");// 悬浮部分
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            String name = ActTypeForName(temp2);
                            list.add(new TricketDetailks(time, temp2.get("reason"), temp2.get("create_time"),
                                    temp2.get("money"), temp2.get("reason"), temp2.get("order_id"),
                                    temp2.get("act_type"), "", temp2.get("add_sub"), temp2.get("img"), temp2.get("act_type").equals("10") ? temp2.get("member_coding") : ""));
                        }
                    }
                    stickyExampleAdapter = new StickyExampleAdapter(this, list, from);
                    tricket_rv.setAdapter(stickyExampleAdapter);

                    lv_layout.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                } else {
                    lv_layout.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                if (ToolKit.isList(map, "data")) {
                    ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    for (Map<String, String> temp : data) {
                        String time = temp.get("time");// 悬浮部分
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            String name = ActTypeForName(temp2);
                            list.add(new TricketDetailks(time, temp2.get("reason"), temp2.get("create_time"),
                                    temp2.get("money"), temp2.get("reason"), temp2.get("order_id"),
                                    temp2.get("act_type"), "", temp2.get("add_sub"), temp2.get("img"), temp2.get("act_type").equals("10") ? temp2.get("member_coding") : ""));
                        }
                    }
                    stickyExampleAdapter.notifyDataSetChanged();
                }

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
        }
    }

    /**
     * 类型转换成名字
     *
     * @param temp2 数据
     * @return 返回字段
     */
    private String ActTypeForName(Map<String, String> temp2) {
        String name = "";
        switch (Integer.parseInt(temp2.get("act_type"))) {
            case 1://线上充值
                name = "线上充值";
                break;
            case 2://线下充值
                name = "线下充值";
                break;
            case 3://消费
                name = "消费";
                break;
            case 4://提现
                name = "提现";
                break;
            case 5://退款
                name = "退款";
                break;
            case 6://转账出
                name = "转账出";
                break;
            case 7://转账收入
                name = "转账收入";
                break;
            case 8://积分兑换余额
                name = "积分兑换余额";
                break;
        }
        return name;
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        if (1 == p) {
            lv_layout.setVisibility(View.GONE);
            no_data_layout.setVisibility(View.VISIBLE);
            if (!frist) {
                swipe_refresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        } else {
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            swipe_refresh.setLoadMore(false);
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_footer, null);
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
}
