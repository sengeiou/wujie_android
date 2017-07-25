package com.txd.hzj.wjlp.mellOffLine.fgt;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mellOnLine.SubclassificationAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/14 0014
 * 时间：下午 4:38
 * 描述：流转商品
 * ===============Txunda===============
 */
public class GoodsByOtherFgt extends BaseFgt {

    /**
     * 分类
     */
    @ViewInject(R.id.classify_off_line_rv)
    private RecyclerView classify_off_line_rv;

    /**
     * 分类列表
     */
    private List<String> horizontal_classify;

    /**
     * 横向滑动的分类适配器
     */
    private HorizontalAdapter horizontalAdapter;

    /**
     * 商品列表
     */
    @ViewInject(R.id.off_line_goods_rv)
    private RecyclerView off_line_goods_rv;

    private int page;
    private MellGoodsAdapter mellGoodsAdapter;

    public static GoodsByOtherFgt newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        GoodsByOtherFgt pageFragment = new GoodsByOtherFgt();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // 设置布局方式
        classify_off_line_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        classify_off_line_rv.setHasFixedSize(true);
        classify_off_line_rv.setAdapter(horizontalAdapter);
        horizontalAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                horizontalAdapter.setSelected(position);
                horizontalAdapter.notifyDataSetChanged();
            }
        });


        off_line_goods_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        off_line_goods_rv.setAdapter(mellGoodsAdapter);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_goods_by_other;
    }

    @Override
    protected void initialized() {

        horizontal_classify = new ArrayList<>();
        horizontal_classify.add("全部");
        horizontal_classify.add("食品");
        horizontal_classify.add("生鲜");
        horizontal_classify.add("服饰");
        horizontal_classify.add("家居");
        horizontal_classify.add("进口");
        horizontal_classify.add("美妆");
        horizontal_classify.add("母婴");
        horizontal_classify.add("电子");
        horizontalAdapter = new HorizontalAdapter(horizontal_classify, getActivity());

        mellGoodsAdapter = new MellGoodsAdapter(getActivity(),1);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
