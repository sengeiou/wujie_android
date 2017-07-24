package com.txd.hzj.wjlp.minetoAty.collect.fgt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.minetoAty.adapter.WjBooksAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：上午 10:01
 * 描述：收藏书院
 * ===============Txunda===============
 */
public class CollectBooksFgt extends BaseFgt {
    private String status;
    /**
     * 列表
     */
    @ViewInject(R.id.collect_bools_lv)
    private ListView collect_bools_lv;
    private List<String> books;

    private WjBooksAdapter wjBooksAdapter;

    public static CollectBooksFgt newInstance(String param1) {
        CollectBooksFgt fragment = new CollectBooksFgt();
        fragment.status = param1;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collect_bools_lv.setAdapter(wjBooksAdapter);
    }

    public void setStatus(String status) {
        this.status = status;
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
        wjBooksAdapter = new WjBooksAdapter(getActivity(), books);
    }

    @Override
    protected void requestData() {

    }
}
