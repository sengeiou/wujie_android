package com.txd.hzj.wjlp.mellOffLine.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.CheckListener;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.ClassifyDetailAdapter;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.RvListener;
import com.txd.hzj.wjlp.bean.SortBean;

import java.util.ArrayList;
import java.util.List;

public class SortDetailFragment extends BaseFgt implements CheckListener {

    @ViewInject(R.id.rv)
    private RecyclerView mRv;

    private ClassifyDetailAdapter mAdapter;

    private LinearLayoutManager mManager;
    private List<SortBean> mDatas;
    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
    private CheckListener checkListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(mManager);
        mAdapter = new ClassifyDetailAdapter(getContext(), mDatas, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                String content = "";
                switch (id) {
                    case R.id.root:
                        content = "title";
                        break;
                    case R.id.content:
                        content = "content";
                        break;

                }
                Snackbar snackbar = Snackbar.make(mRv, "当前点击的是" + content + ":" + mDatas.get(position).getName(),
                        Snackbar.LENGTH_SHORT);
                View mView = snackbar.getView();
                mView.setBackgroundColor(Color.BLUE);
                TextView text = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                text.setTextColor(Color.WHITE);
                text.setTextSize(25);
                snackbar.show();
            }
        });

        mRv.setAdapter(mAdapter);
        mDecoration = new ItemHeaderDecoration(getActivity(), mDatas);
        mRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sort_detail;
    }

    @Override
    protected void initialized() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            SortBean titleBean = new SortBean(String.valueOf(i));
            titleBean.setTitle(true);//头部设置为true
            titleBean.setTag(String.valueOf(i));
            mDatas.add(titleBean);
            for (int j = 0; j < 10; j++) {
                SortBean sortBean = new SortBean(String.valueOf(i + "行" + j + "个"));
                sortBean.setTag(String.valueOf(i));
                mDatas.add(sortBean);
            }
        }
    }

    @Override
    protected void requestData() {

    }


    public void setData(int n) {
        if (n < 0 || n >= mAdapter.getItemCount()) {
            Toast.makeText(getActivity(), "超出范围了", Toast.LENGTH_SHORT).show();
            return;
        }
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);
    }

    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRv.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRv.getChildAt(n - firstItem).getTop();
            mRv.smoothScrollBy(0, top);
        } else {
            mRv.smoothScrollToPosition(n);
            move = true;
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);
    }

    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    mRv.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

}
