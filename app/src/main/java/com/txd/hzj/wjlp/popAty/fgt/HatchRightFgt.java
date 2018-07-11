package com.txd.hzj.wjlp.popAty.fgt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.MellInfoList;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellListAdapter;
import com.txd.hzj.wjlp.minetoAty.coupon.fgt.ShareOptionFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 8:13
 * 描述：企业店铺
 */
public class HatchRightFgt extends BaseFgt {

    @ViewInject(R.id.shop_enterprise_lv)
    private ListView shop_enterprise_lv;

    private ShapAdapter shopAdapter;

    private MellListAdapter mellListAdapter;
    private List<MellInfoList> mells;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shop_enterprise_lv.setAdapter(mellListAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_hatch_right;
    }

    @Override
    protected void initialized() {
        shopAdapter = new ShapAdapter();
        mells = new ArrayList<>();
        mellListAdapter = new MellListAdapter(getActivity(), mells);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    private class ShapAdapter extends BaseAdapter {

        private ShopViewHolder svh;

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_shop_enterprise_lv, null);
                svh = new ShopViewHolder();
                ViewUtils.inject(svh, view);
                view.setTag(svh);
            } else {
                svh = (ShopViewHolder) view.getTag();
            }
            return view;
        }

        private class ShopViewHolder {

        }
    }

}
