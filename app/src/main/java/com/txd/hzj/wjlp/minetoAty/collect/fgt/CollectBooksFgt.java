package com.txd.hzj.wjlp.minetoAty.collect.fgt;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.AcademyList;
import com.txd.hzj.wjlp.bean.CollectBooks;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.adapter.WjBooksAdapter;
import com.txd.hzj.wjlp.minetoAty.books.BooksDetailsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：上午 10:01
 * 描述：收藏书院
 * ===============Txunda===============
 */
public class CollectBooksFgt extends BaseFgt implements WjBooksAdapter.ForSelectNum {
    private boolean status;
    private int dataType;
    /**
     * 列表
     */
    @ViewInject(R.id.collect_bools_lv)
    private PullToRefreshListView collect_bools_lv;
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

    public static CollectBooksFgt newInstance(boolean param1, int dataType) {
        CollectBooksFgt fragment = new CollectBooksFgt();
        fragment.status = param1;
        fragment.dataType = dataType;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collect_bools_lv.setEmptyView(no_data_layout);
        collect_bools_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                if (0 == dataType)
                    userPst.myfooter(p, "3");
                else
                    collectPst.collectList(p, "3");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allNum <= books.size()) {
                    collect_bools_lv.onRefreshComplete();
                    return;
                }
                p++;
                if (0 == dataType)
                    userPst.myfooter(p, "3");
                else
                    collectPst.collectList(p, "3");
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
                    L.e("=====List转Json", collect_ids);
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
                    L.e("=====List转Json", collect_ids);
                    collectPst.delCollect(collect_ids);
                }
                break;
        }
    }

    public void setStatus(boolean status) {
        if (allNum <= 0) {
            return;
        }
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
        if (0 == dataType) {
            userPst.myfooter(p, "3");
        } else {
            collectPst.collectList(p, "3");
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("delCollect")) {
            books.removeAll(books3);
            allNum -= books3.size();
            wjBooksAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(books)) {
                L.e("=====书院=====", "收藏");
                operation_book_collect_layout.setVisibility(View.GONE);
            }
            return;
        }

        if (requestUrl.contains("delFooter")) {
            books.removeAll(books3);
            allNum -= books3.size();
            wjBooksAdapter.notifyDataSetChanged();
            if (ListUtils.isEmpty(books)) {
                L.e("=====书院=====", "收藏");
                operation_book_collect_layout.setVisibility(View.GONE);
            }
            return;
        }

        if (requestUrl.contains("collectList")) {
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
            collect_bools_lv.onRefreshComplete();
            setStatus(status);
            return;
        }
        if (requestUrl.contains("myfooter")) {
            L.e("书院=====原始数据======", jsonStr);
            CollectBooks collectBooks = GsonUtil.GsonToBean(jsonStr, CollectBooks.class);
            L.e("书院=====解析数据======", collectBooks.toString());
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
            collect_bools_lv.onRefreshComplete();
            setStatus(status);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        if (requestUrl.contains("collectList")) {
            removeContent();
            removeDialog();
            collect_bools_lv.onRefreshComplete();
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
}
