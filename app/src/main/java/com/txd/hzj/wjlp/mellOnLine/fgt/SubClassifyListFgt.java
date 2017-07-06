package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/5 0005
 * 时间：下午 4:09
 * 描述：1-1-2二级分类
 * ===============Txunda===============
 */
public class SubClassifyListFgt extends BaseFgt {
    private String title;

    @ViewInject(R.id.su_classify_goods_rv)
    private RecyclerView su_classify_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private List<String> data;

    private int height = 0;

    public SubClassifyListFgt() {
    }

    public static SubClassifyListFgt getInstance(String title) {
        SubClassifyListFgt sFgt = new SubClassifyListFgt();
        sFgt.title = title;
        return sFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        su_classify_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        su_classify_goods_rv.setItemAnimator(new DefaultItemAnimator());
        su_classify_goods_rv.setHasFixedSize(true);
        su_classify_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        su_classify_goods_rv.setAdapter(racycleAllAdapter);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_sub_classify_list;
    }

    @Override
    protected void initialized() {
        height = ToolKit.dip2px(getActivity(), 4);
        data = new ArrayList<>();
        racycleAllAdapter = new RacycleAllAdapter(getActivity(), data);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
