package com.txd.hzj.wjlp.minetoAty.myGrade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：下午 4:27
 * 描述：分享次数
 */
public class ShareTimesAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.share_times_lv)
    private PullToRefreshListView share_times_lv;

    private ShapeAdapter shapeAdapter;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int p = 1;
    private UserPst userPst;

    private List<Map<String, String>> list;

    private int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分享次数");
        share_times_lv.setEmptyView(no_data_layout);
        share_times_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                userPst.myShare(p, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                userPst.myShare(p, false);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_share_times;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
        list = new ArrayList<>();
        size = ToolKit.dip2px(this, 60);
        shapeAdapter = new ShapeAdapter();
    }

    @Override
    protected void requestData() {
        userPst.myShare(p, true);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("myShare")) {
            if (ToolKit.isList(map, "data")) {
                if (1 == p) {
                    list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    share_times_lv.setAdapter(shapeAdapter);
                } else {
                    list.addAll(JSONUtils.parseKeyAndValueToMapList(map.get("data")));
                    shapeAdapter.notifyDataSetChanged();
                }
            }
            share_times_lv.onRefreshComplete();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        share_times_lv.onRefreshComplete();
    }

    private class ShapeAdapter extends BaseAdapter {

        private TimesVH timesVh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(ShareTimesAty.this).inflate(R.layout.item_shape_time_lv, viewGroup, false);
                timesVh = new TimesVH();
                ViewUtils.inject(timesVh, view);
                view.setTag(timesVh);
            } else {
                timesVh = (TimesVH) view.getTag();
            }

            Glide.with(ShareTimesAty.this).load(map.get("pic"))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(timesVh.my_share_pic_iv);

            timesVh.my_share_time_tv.setText("分享时间\n" + map.get("create_time"));

            timesVh.my_share_title_tv.setText(map.get("title"));

            timesVh.my_share_typt_tv.setText(map.get("type"));

            return view;
        }

        private class TimesVH {

            @ViewInject(R.id.my_share_pic_iv)
            private ImageView my_share_pic_iv;

            @ViewInject(R.id.my_share_title_tv)
            private TextView my_share_title_tv;

            @ViewInject(R.id.my_share_time_tv)
            private TextView my_share_time_tv;

            @ViewInject(R.id.my_share_typt_tv)
            private TextView my_share_typt_tv;


        }
    }

}
