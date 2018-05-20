package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.adapter.StickyExampleAdapter;

/**
 * 创建者：Qyl
 * 创建时间：2018/5/20 0020 9:52
 * 功能描述：
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
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Log.i("赠送代金券详情", jsonStr);

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
