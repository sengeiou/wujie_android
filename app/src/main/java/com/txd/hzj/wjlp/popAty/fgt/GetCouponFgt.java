package com.txd.hzj.wjlp.popAty.fgt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 4:07
 * 描述：领券
 * ===============Txunda===============
 */
public class GetCouponFgt extends BaseFgt {

    @ViewInject(R.id.coupon_classify_rv)
    private RecyclerView coupon_classify_rv;
    /**
     * 分类列表
     */
    private List<String> horizontal_classify;

    /**
     * 横向滑动的分类适配器
     */
    private HorizontalAdapter horizontalAdapter;

    @ViewInject(R.id.coupon_lv)
    private ListView coupon_lv;

    private CouponAdapter couponAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 设置布局方式
        coupon_classify_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                false));
        coupon_classify_rv.setHasFixedSize(true);
        coupon_classify_rv.setAdapter(horizontalAdapter);
        horizontalAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                horizontalAdapter.setSelected(position);
                horizontalAdapter.notifyDataSetChanged();
            }
        });
        coupon_lv.setAdapter(couponAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_get_coupon;
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
        couponAdapter = new CouponAdapter(getActivity(), 0);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
