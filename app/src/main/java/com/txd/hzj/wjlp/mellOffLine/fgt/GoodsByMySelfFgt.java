package com.txd.hzj.wjlp.mellOffLine.fgt;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.CheckListener;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.RvListener;
import com.txd.hzj.wjlp.mellOffLine.fgt.adapter.SortAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 4:41
 * 描述：自营商品
 * ===============Txunda===============
 */
public class GoodsByMySelfFgt extends BaseFgt implements CheckListener {
    public static GoodsByMySelfFgt newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        GoodsByMySelfFgt pageFragment = new GoodsByMySelfFgt();
        pageFragment.setArguments(args);
        return pageFragment;
    }


    @ViewInject(R.id.rv_sort)
    private RecyclerView rvSort;

    private SortAdapter mSortAdapter;
    private Context mContext;

    public static boolean select_left;
    public static int finalNumber = 0;

    private List<String> left;

    private SortDetailFragment sdfgt;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvSort.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        rvSort.addItemDecoration(decoration);

        rvSort.setAdapter(mSortAdapter);
        createFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_goods_by_my_self;
    }

    @Override
    protected void initialized() {
        left = new ArrayList<>();
        left.add("促销专区");
        left.add("满额优惠");
        left.add("店长推荐");
        left.add("雨具");
        left.add("毕业季");
        left.add("暑假大排档");
        left.add("日配冷藏");
        left.add("日配冷藏");
        left.add("日配冷藏");
        left.add("日配冷藏");
        left.add("日配冷藏");
        left.add("日配冷藏");
        mSortAdapter = new SortAdapter(getActivity(), left, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if (sdfgt != null) {
                    setChecked(position, true);
                }
            }
        });
    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void immersionInit() {

    }

    public void createFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        sdfgt = new SortDetailFragment();
        sdfgt.setListener(this);
        fragmentTransaction.add(R.id.lin_fragment, sdfgt);
        fragmentTransaction.commit();
    }

    private void setChecked(int position, boolean isLeft) {
        finalNumber = position;
        select_left = isLeft;
        Log.d("boolean---->", String.valueOf(left));
        mSortAdapter.setCheckedPosition(position);
        if (isLeft) {
            sdfgt.setData(position * 10 + position);
        }

    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }
}
