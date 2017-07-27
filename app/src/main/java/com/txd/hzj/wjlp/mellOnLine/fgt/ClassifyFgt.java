package com.txd.hzj.wjlp.mellOnLine.fgt;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.GVClassifyAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.mellOnLine.SubclassificationAty;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/27 0027
 * 时间：上午 9:56
 * 描述：首页横向分类
 * ===============Txunda===============
 */
public class ClassifyFgt extends BaseFgt {
    private String type;


    @ViewInject(R.id.classify_goods_rv)
    private RecyclerView classify_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private List<String> data;

    private int height = 0;

    /**
     * GridView数据列表
     */
    private List<String> gv_classify;
    private GVClassifyAdapter gvClassifyAdapter;

    /**
     * GridView分类
     */
    @ViewInject(R.id.on_lin_classify_gv)
    private GridViewForScrollView on_lin_classify_gv;

    public static ClassifyFgt newInstance(String type) {
        ClassifyFgt fragment = new ClassifyFgt();
        fragment.type = type;
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        classify_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        classify_goods_rv.setItemAnimator(new DefaultItemAnimator());
        classify_goods_rv.setHasFixedSize(true);
        classify_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        classify_goods_rv.setAdapter(racycleAllAdapter);

        on_lin_classify_gv.setAdapter(gvClassifyAdapter);
        on_lin_classify_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(SubclassificationAty.class, null);
            }
        });
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_classify_fgt;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
        racycleAllAdapter = new RacycleAllAdapter(getActivity(), data);
        height = ToolKit.dip2px(getActivity(), 4);
        gv_classify = new ArrayList<>();
        gvClassifyAdapter = new GVClassifyAdapter(getActivity(), gv_classify, 1);
    }

    @Override
    protected void requestData() {

    }
}
