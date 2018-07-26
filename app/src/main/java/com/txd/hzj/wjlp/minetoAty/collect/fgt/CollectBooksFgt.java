package com.txd.hzj.wjlp.minetoAty.collect.fgt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.AcademyList;
import com.txd.hzj.wjlp.bean.CollectBooks;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.FootprintAty;
import com.txd.hzj.wjlp.minetoAty.adapter.WjBooksAdapter;
import com.txd.hzj.wjlp.minetoAty.books.BooksDetailsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：上午 10:01
 * 描述：收藏书院
 */
public class CollectBooksFgt extends BaseFgt implements WjBooksAdapter.ForSelectNum {

    private boolean status;
    private int dataType = 0;
    /**
     * 刷新
     */
    @ViewInject(R.id.book_super_layouts)
    private SuperSwipeRefreshLayout book_super_layouts;
    /**
     * 列表
     */
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    @ViewInject(R.id.collect_bools_lv)
    private ListView collect_bools_lv;
    /**
     * 全部收藏文章
     */
    private List<AcademyList> books;
    /**
     * 分页收藏文章
     */
    private List<AcademyList> books2;
    /**
     * 被选中的收藏文章
     */
    private List<AcademyList> books3;

    private WjBooksAdapter wjBooksAdapter;

    @ViewInject(R.id.operation_book_collect_layout)
    private LinearLayout operation_book_collect_layout;

    private UserCollectPst collectPst;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int p = 1;

    private int allNum = 0;

    /**
     * 全选，取消全选
     */
    @ViewInject(R.id.collect_books_select_all_cb)
    private CheckBox collect_books_select_all_cb;

    private Bundle bundle;
    private UserPst userPst;
    private Intent intent;

    public static CollectBooksFgt newInstance(boolean param1, int dataType) {
        CollectBooksFgt fragment = new CollectBooksFgt();
        fragment.status = param1;
        fragment.dataType = dataType;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        intent = new Intent();
        intent.setAction("sftv");
        intent.putExtra("index", 2);
        collect_bools_lv.setEmptyView(no_data_layout);

        book_super_layouts.setHeaderViewBackgroundColor(Color.WHITE);
        book_super_layouts.setHeaderView(createHeaderView());// add headerView
        book_super_layouts.setFooterView(createFooterView());
        book_super_layouts.setTargetScrollWithLayout(true); // 跟随手指滑动
        book_super_layouts.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                if (0 == dataType)
                    userPst.myfooter(p, "3");
                else
                    collectPst.collectList(p, "3");
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        book_super_layouts.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                if (allNum <= books.size()) {
                    book_super_layouts.setLoadMore(false);
                    return;
                }
                p++;
                if (0 == dataType)
                    userPst.myfooter(p, "3");
                else
                    collectPst.collectList(p, "3");
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });

        collect_bools_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                if (0 == dataType) {
                    bundle.putString("academy_id", books.get(i - 1).getAcademy_id());
                } else {
                    bundle.putString("academy_id", books.get(i - 1).getAid());
                }
                startActivity(BooksDetailsAty.class, bundle);
            }
        });
    }

    @Override
    @OnClick({R.id.collect_books_select_all_cb, R.id.operation_books_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.collect_books_select_all_cb:// 全选取消全选
                boolean select = collect_books_select_all_cb.isChecked();
                for (AcademyList book : books) {
                    book.setSelect(select);
                }
                wjBooksAdapter.notifyDataSetChanged();
                break;
            case R.id.operation_books_tv:// 删除
                List<String> ids;
                if (0 == dataType) {
                    ids = new ArrayList<>();
                    books3 = new ArrayList<>();
                    for (AcademyList book : books) {
                        if (book.isSelect()) {
                            ids.add(book.getFooter_id());
                            books3.add(book);
                        }
                    }
                    String collect_ids = ListUtils.join(ids).replace("[", "").replace("]", "");

                    userPst.delFooter(collect_ids);
                } else {
                    ids = new ArrayList<>();
                    books3 = new ArrayList<>();
                    for (AcademyList book : books) {
                        if (book.isSelect()) {
                            ids.add(book.getCollect_id());
                            books3.add(book);
                        }
                    }
                    String collect_ids = ListUtils.join(ids);

                    collectPst.delCollect(collect_ids);
                }
                break;
        }
    }

    public void r() {
        if (0 == dataType) {
            userPst.myfooter(p, "3");
        } else {
            collectPst.collectList(p, "3");
        }
    }

    public void setStatus(boolean status) {
//        if (allNum <= 0) {
//            return;
//        }
        this.status = status;
        if (!status) {
            operation_book_collect_layout.setVisibility(View.GONE);
        } else {
            operation_book_collect_layout.setVisibility(View.VISIBLE);
        }
        if (wjBooksAdapter != null) {
            wjBooksAdapter.setCanEdit(status);
            wjBooksAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_books;
    }

    @Override
    protected void initialized() {
        books = new ArrayList<>();
        books2 = new ArrayList<>();
        collectPst = new UserCollectPst(this);
        userPst = new UserPst(this);
    }


    @Override
    protected void requestData() {

    }

    @Override
    public void onResume() {
        super.onResume();
//
//        if (0 == dataType) {
//            userPst.myfooter(p, "3");
//        } else {
//            collectPst.collectList(p, "3");
//        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("delCollect")) {
            books.removeAll(books3);
            allNum -= books3.size();
            wjBooksAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(books)) {
                operation_book_collect_layout.setVisibility(View.GONE);
            }
            return;
        }

        if (requestUrl.contains("delFooter")) {
            books.removeAll(books3);
            allNum -= books3.size();
            wjBooksAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(books)) {
                operation_book_collect_layout.setVisibility(View.GONE);
            }
            return;
        }

        if (requestUrl.contains("collectList")) {
            getActivity().sendBroadcast(intent);
            CollectBooks collectBooks = GsonUtil.GsonToBean(jsonStr, CollectBooks.class);
            allNum = collectBooks.getNums();
            if (1 == p) {
                books = collectBooks.getData();
                if (!ListUtils.isEmpty(books)) {
                    wjBooksAdapter = new WjBooksAdapter(getActivity(), books);
                    collect_bools_lv.setAdapter(wjBooksAdapter);
                    wjBooksAdapter.setForSelectNum(this);
                }
            } else {
                books2 = collectBooks.getData();
                if (!ListUtils.isEmpty(books2)) {
                    books.addAll(books2);
                }
                wjBooksAdapter.notifyDataSetChanged();
            }
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            book_super_layouts.setLoadMore(false);
            book_super_layouts.setRefreshing(false);
            setStatus(status);
            return;
        }
        if (requestUrl.contains("myfooter")) {
//            ((FootprintAty)getActivity()).setView(View.VISIBLE);

            getActivity().sendBroadcast(intent);
            CollectBooks collectBooks = GsonUtil.GsonToBean(jsonStr, CollectBooks.class);
            allNum = collectBooks.getNums();
            if (1 == p) {
                books = collectBooks.getData();
                if (!ListUtils.isEmpty(books)) {
                    wjBooksAdapter = new WjBooksAdapter(getActivity(), books);
                    collect_bools_lv.setAdapter(wjBooksAdapter);
                    wjBooksAdapter.setForSelectNum(this);
                }
            } else {
                books2 = collectBooks.getData();
                if (!ListUtils.isEmpty(books2)) {
                    books.addAll(books2);
                }
                wjBooksAdapter.notifyDataSetChanged();
            }
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            book_super_layouts.setLoadMore(false);
            book_super_layouts.setRefreshing(false);
            setStatus(status);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        if (requestUrl.contains("collectList") || requestUrl.contains("myfooter")) {
//            ((FootprintAty)getActivity()).setView(View.VISIBLE);
            removeContent();
            removeDialog();
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            book_super_layouts.setLoadMore(false);
            book_super_layouts.setRefreshing(false);

        } else {
            super.onError(requestUrl, error);
        }
    }

    @Override
    public void getSelectNum(int num) {
        if (num >= books.size()) {
            collect_books_select_all_cb.setChecked(true);
        } else {
            collect_books_select_all_cb.setChecked(false);
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(book_super_layouts.getContext()).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(book_super_layouts.getContext()).inflate(R.layout.layout_head, null);
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
