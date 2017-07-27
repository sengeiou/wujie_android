package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：下午 2:33
 * 描述：参与记录
 * ===============Txunda===============
 */
public class RecordFgt extends BaseFgt {

    @ViewInject(R.id.history_join_lv)
    private ListViewForScrollView history_join_lv;

    private HistoryAdapter historyAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        history_join_lv.setAdapter(historyAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_record;
    }

    @Override
    protected void initialized() {
        historyAdapter = new HistoryAdapter();
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    private class HistoryAdapter extends BaseAdapter {

        private HisVH hisVH;

        @Override
        public int getCount() {
            return 10;
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
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_history_join, null);
                hisVH = new HisVH();
                ViewUtils.inject(hisVH, view);
                view.setTag(hisVH);
            } else {
                hisVH = (HisVH) view.getTag();
            }

            return view;
        }

        class HisVH {

        }

        ;
    }

}
