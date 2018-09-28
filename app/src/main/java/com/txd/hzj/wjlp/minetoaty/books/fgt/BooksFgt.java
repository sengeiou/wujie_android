package com.txd.hzj.wjlp.minetoaty.books.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.AcademyIndex;
import com.txd.hzj.wjlp.bean.AcademyList;
import com.txd.hzj.wjlp.http.academy.AcademyPst;
import com.txd.hzj.wjlp.mellonLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.minetoaty.adapter.WjBooksAdapter;
import com.txd.hzj.wjlp.minetoaty.books.BooksDetailsAty;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：上午 9:48
 * 描述：书院
 */
public class BooksFgt extends BaseFgt {

    private String type;

    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private List<AcademyIndex.DataBean.BannerList> image;

    @ViewInject(R.id.books_lv)
    private ListViewForScrollView books_lv;
    private List<AcademyList> books;
    private List<AcademyList> books2;

    private WjBooksAdapter wjBooksAdapter;

    @ViewInject(R.id.refresh_view)
    private SuperSwipeRefreshLayout refresh_view;


    private AcademyPst academyPst;
    private int p = 1;
    private int numall = 0;
    private Bundle bundle;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    public static BooksFgt newInstance(String type) {
        BooksFgt fragment = new BooksFgt();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth / 3);
        online_carvouse_view.setLayoutParams(layoutParams);
        // 轮播图
        forBanner();
        forUpdata();
        books_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("academy_id", books.get(i).getAcademy_id());
                startActivity(BooksDetailsAty.class, bundle);
            }
        });
        books_lv.setEmptyView(no_data_layout);
    }

    /**
     * 更新数据
     */
    private void forUpdata() {/*
        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                p = 1;
                academyPst.academyIndex(p, type);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (numall <= books.size()) {
                    refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
                    return;
                }
                // 加载操作
                p++;
                academyPst.academyIndex(p, type);
            }
        });*/

        refresh_view.setHeaderViewBackgroundColor(Color.WHITE);
        refresh_view.setHeaderView(createHeaderView());// add headerView
        refresh_view.setFooterView(createFooterView());
        refresh_view.setTargetScrollWithLayout(true);

        refresh_view
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        academyPst.academyIndex(p, type);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                        // 加载操作

                    }
                });

        refresh_view
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {

                        if (numall <= books.size()) {
                            refresh_view.setRefreshing(false);
                            refresh_view.setLoadMore(false);
                            return;
                        }
                        // 加载操作
                        p++;
                        academyPst.academyIndex(p, type);
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        if (numall <= books.size()) {
                            refresh_view.setRefreshing(false);
                            refresh_view.setLoadMore(false);
                            return;
                        }
                        // 加载操作
                    }

                    @Override
                    public void onPushDistance(int distance) {

                    }

                });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_books;
    }

    @Override
    protected void initialized() {
        academyPst = new AcademyPst(this);
        image = new ArrayList<>();
        books = new ArrayList<>();
        books2 = new ArrayList<>();

    }

    @Override
    protected void requestData() {
        academyPst.academyIndex(p, type);
    }


    @Override
    protected void immersionInit() {

    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setImageListener(imageListener);
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            Glide.with(getActivity()).load(image.get(position).getPicture())
                    .override(Settings.displayWidth, Settings.displayWidth / 3)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!TextUtils.isEmpty(image.get(position).getMerchant_id()) && !image.get(position).getMerchant_id().equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mell_id", image.get(position).getMerchant_id());
                        startActivity(MellInfoAty.class, bundle);
                    } else if (!TextUtils.isEmpty(image.get(position).getGoods_id()) && !image.get(position).getGoods_id().equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("ticket_buy_id", image.get(position).getGoods_id());
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                    } else {
                        bundle = new Bundle();
//                        bundle.putInt("from", 2);
//                        bundle.putString("desc", image.get(position).getDesc());
                        bundle.putString("url", image.get(position).getHref());
                        startActivity(WebViewAty.class, bundle);
                    }

                }
            });
        }
    };

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        AcademyIndex academyIndex = GsonUtil.GsonToBean(jsonStr, AcademyIndex.class);
        numall = academyIndex.getNums();
        if (1 == p) {
            image = academyIndex.getData().getBannerList();
            online_carvouse_view.setPageCount(image.size());

            books = academyIndex.getData().getAcademy_list();
            wjBooksAdapter = new WjBooksAdapter(getActivity(), books);
            books_lv.setAdapter(wjBooksAdapter);
            refresh_view.setRefreshing(false); // 刷新成功
        } else {
            books2 = academyIndex.getData().getAcademy_list();
            books.addAll(books2);
            wjBooksAdapter.notifyDataSetChanged();

            refresh_view.setLoadMore(false); // 刷新成功
        }
    }
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private View createHeaderView() {
        View headerView = LayoutInflater.from(refresh_view.getContext())
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

    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private View createFooterView() {
        View footerView = LayoutInflater.from(refresh_view.getContext())
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
}
