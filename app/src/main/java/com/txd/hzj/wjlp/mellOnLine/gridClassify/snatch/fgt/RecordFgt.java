package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.fgt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

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

    private String oneBuyLog = "";

    private List<Map<String, String>> buyLog;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @ViewInject(R.id.data_layout)
    private LinearLayout data_layout;

    private int size = 0;

    public static RecordFgt getFgt(String oneBuyLog) {
        RecordFgt recordFgt = new RecordFgt();
        recordFgt.oneBuyLog = oneBuyLog;
        return recordFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buyLog = JSONUtils.parseKeyAndValueToMapList(oneBuyLog);
        if (!ListUtils.isEmpty(buyLog)) {
            historyAdapter = new HistoryAdapter();
            history_join_lv.setAdapter(historyAdapter);
            data_layout.setVisibility(View.VISIBLE);
            no_data_layout.setVisibility(View.GONE);
        } else {
            data_layout.setVisibility(View.GONE);
            no_data_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_record;
    }

    @Override
    protected void initialized() {
        buyLog = new ArrayList<>();
        size = ToolKit.dip2px(getActivity(), 60);
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
            return buyLog.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return buyLog.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_history_join, null);
                hisVH = new HisVH();
                ViewUtils.inject(hisVH, view);
                view.setTag(hisVH);
            } else {
                hisVH = (HisVH) view.getTag();
            }
            Glide.with(getActivity()).load(map.get("head_pic")).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .override(size, size)
                    .into(hisVH.one_buy_head_iv);
            hisVH.nick_phone_tv.setText(map.get("nickname") + "(" + map.get("phone") + ")");
            hisVH.join_times_tv.setText(map.get("count"));
            hisVH.join_in_time_tv.setText(map.get("bid_time"));
            return view;
        }

        class HisVH {
            /**
             * 头像
             */
            @ViewInject(R.id.one_buy_head_iv)
            private ShapedImageView one_buy_head_iv;
            /**
             * 昵称，电话
             */
            @ViewInject(R.id.nick_phone_tv)
            private TextView nick_phone_tv;

            /**
             * 参与次数
             */
            @ViewInject(R.id.join_times_tv)
            private TextView join_times_tv;
            /**
             * 参与时间
             */
            @ViewInject(R.id.join_in_time_tv)
            private TextView join_in_time_tv;

        }
    }

}
