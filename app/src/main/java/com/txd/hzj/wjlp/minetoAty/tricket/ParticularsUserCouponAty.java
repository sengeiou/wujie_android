package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.google.gson.Gson;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GifVoucherListDetailBean;
import com.txd.hzj.wjlp.bean.TricketDetailks;
import com.txd.hzj.wjlp.minetoAty.adapter.StickyExampleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：Qyl
 * 创建时间：2018/5/20 0020 9:52
 * 功能描述：蓝色代金券使用明细
 * 联系方式：无
 */
public class ParticularsUserCouponAty extends BaseAty {

    private TextView titleName;
    private RecyclerView recyList;
    private TextView headerView;
    private SuperSwipeRefreshLayout swipe_refresh;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private int p = 1;
    private String id;
    private List<TricketDetailks> tricketDetailksList;
    private StickyExampleAdapter stickyExampleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View stickyInfoView = recyclerView.findChildViewUnder(
                        headerView.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    headerView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        headerView.getMeasuredWidth() / 2, headerView.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - headerView.getMeasuredHeight();

                    if (transViewStatus == StickyExampleAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            headerView.setTranslationY(dealtY);
                        } else {
                            headerView.setTranslationY(0);
                        }
                    } else if (transViewStatus == StickyExampleAdapter.NONE_STICKY_VIEW) {
                        headerView.setTranslationY(0);
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
        return R.layout.aty_particulars_used_coupon_tricket;
    }

    @Override
    protected void initialized() {

        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");

        titleName = (TextView) findViewById(R.id.titlt_conter_tv);
        recyList = (RecyclerView) findViewById(R.id.tricket_rv);
        headerView = (TextView) findViewById(R.id.tv_sticky_header_view);
        swipe_refresh = (SuperSwipeRefreshLayout) findViewById(R.id.part_swipe_refresh);

        tricketDetailksList = new ArrayList<>();
        stickyExampleAdapter = new StickyExampleAdapter(this, tricketDetailksList, 8);
        recyList.setAdapter(stickyExampleAdapter);
    }

    public void getData() {
        com.txd.hzj.wjlp.http.user.User.gifVoucherListDetail(this, id, p + "");
    }

    @Override
    protected void requestData() {
        titleName.setText("代金券使用详情");
        com.txd.hzj.wjlp.http.user.User.gifVoucherListDetail(this, id, p + "");
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
        if (requestUrl.contains("User/gifVoucherListDetail")){ // 赠送券的流水明细
            Gson gson = new Gson();
            GifVoucherListDetailBean gifVoucherListDetailBean = gson.fromJson(jsonStr, GifVoucherListDetailBean.class);

            if (p == 1){
                tricketDetailksList.removeAll(tricketDetailksList); // 清空List
            }

            for (GifVoucherListDetailBean.DataBean dataBean: gifVoucherListDetailBean.getData()) {
//                sticky, name, gender, profession, String reason, String
//                log_id, String act_type, String act_id, String add_sub, String imgStr, String memberCoding
                TricketDetailks tricketDetailks = new TricketDetailks();
                tricketDetailks.setSticky(dataBean.getTime()); // 设置吸顶标题
                for (GifVoucherListDetailBean.DataBean.ListBean listBean: dataBean.getList()) {
                    tricketDetailks.setName(""); // 条目标题
                    tricketDetailks.setGender(listBean.getCreate_time()); // 交易记录时间
                    tricketDetailks.setProfession(listBean.getVoucher_price()); // 交易数量
                    tricketDetailks.setReason(listBean.getReason()); // 原因
                    tricketDetailks.setLog_id(listBean.getB_v_id()); // 记录id
                    tricketDetailks.setAct_type(listBean.getOrder_type()); // 操作类型
//                    tricketDetailks.setAct_id(); // 对应的操作对象id  只有线下充值的有用
                    tricketDetailks.setAdd_sub(""); // 正负号，消费只有减
                    tricketDetailks.setImgStr(listBean.getImg()); // 图标
//                    tricketDetailks.setMemberCoding(); // 会员编号 查看会员卡消费明细会用到
                    tricketDetailks.setOrderId(listBean.getOrder_id()); // 订单编号，查看详情才使用
                    tricketDetailksList.add(tricketDetailks);
                }

            }
            stickyExampleAdapter.notifyDataSetChanged();
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
