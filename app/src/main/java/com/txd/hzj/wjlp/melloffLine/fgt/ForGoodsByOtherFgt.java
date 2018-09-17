package com.txd.hzj.wjlp.melloffLine.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.melloffLine.dialog.MellCouponDialog;
import com.txd.hzj.wjlp.mellonLine.adapter.MellGoodsAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.List;
import java.util.Map;

public class ForGoodsByOtherFgt extends BaseFgt {

    private String type;

    /**
     * 商品列表
     */
    @ViewInject(R.id.off_line_goods_rv)
    private RecyclerView off_line_goods_rv;

    @ViewInject(R.id.nested_sc)
    private NestedScrollView nested_sc;

    private MellGoodsAdapter mellGoodsAdapter;

    private MellCouponDialog mellCouponDialog;

    private List<Map<String, String>> ticket_list;

    @ViewInject(R.id.un_use_view)
    private View un_use_view;

    private int height = 0;

    private int viewHeight = 0;

    private boolean isTouch = false;

    public static ForGoodsByOtherFgt newInstance(String type) {
        ForGoodsByOtherFgt fragment = new ForGoodsByOtherFgt();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        off_line_goods_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        off_line_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        off_line_goods_rv.setAdapter(mellGoodsAdapter);
        nested_sc.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                L.e("=====高度=====", viewHeight + "");
                if (scrollY <= viewHeight && !isTouch) {
                    L.e("=====距离=====", scrollY + "");
                    L.e("=====触摸=====", isTouch + "");
                    nested_sc.scrollTo(0, 0);
                }
            }
        });

        nested_sc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTouch = true;
                return false;
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isTouch = false;
        }
    }

    @Override
    @OnClick({R.id.check_all_coupon_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_all_coupon_tv:// 查看更多优惠券
                mellCouponDialog = new MellCouponDialog(getActivity(), ticket_list, new MellCouponDialog.ItemClick() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
                mellCouponDialog.show();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_for_goods_by_other;
    }

    @Override
    protected void initialized() {
        mellGoodsAdapter = new MellGoodsAdapter(getActivity(), 1);
        height = ToolKit.dip2px(getActivity(), 4);
        viewHeight = ToolKit.dip2px(getActivity(), 64);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

}
