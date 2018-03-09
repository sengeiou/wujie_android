package com.txd.hzj.wjlp.mellOnLine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tool.glide.GlideUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.index.IndexPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/12 0012
 * 时间：上午 10:03
 * 描述：xfte头条
 * ===============Txunda===============
 */
public class WujieTopHzjAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.wujie_top_lv)
    private PullToRefreshListView wujie_top_lv;

    private List<Map<String, String>> list;

    private TopAdapter topAdapter;

    private IndexPst indexPst;
    private int p = 1;

    private int w = 0;
    private int h = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("xfte头条");
        wujie_top_lv.setAdapter(topAdapter);

        wujie_top_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                indexPst.headLineList(p, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                indexPst.headLineList(p, false);
            }
        });
        wujie_top_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("from", 1);
                bundle.putString("headlines_id", list.get(i - 1).get("headlines_id"));
                startActivity(NoticeDetailsAty.class, bundle);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_wujie_top_hzj;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        topAdapter = new TopAdapter();
        indexPst = new IndexPst(this);
        w = ToolKit.dip2px(this, 120);
        h = ToolKit.dip2px(this, 80);
    }

    @Override
    protected void requestData() {
        indexPst.headLineList(p, true);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("headLineList")) {
            if (ToolKit.isList(map, "data")) {
                if (1 == p) {
                    list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                } else {
                    list.addAll(JSONUtils.parseKeyAndValueToMapList(map.get("data")));
                }
                topAdapter.notifyDataSetChanged();
            }
            wujie_top_lv.onRefreshComplete();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        wujie_top_lv.onRefreshComplete();
    }

    private class TopAdapter extends BaseAdapter {
        private TopVH tvh;

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
            Map<String, String> data = getItem(i);
            if (null == view) {
                view = LayoutInflater.from(WujieTopHzjAty.this).inflate(R.layout.item_wujie_top_lv, null);
                tvh = new TopVH();
                ViewUtils.inject(tvh, view);
                view.setTag(tvh);
            } else {
                tvh = (TopVH) view.getTag();
            }
            tvh.top_title_tv.setText(data.get("title"));
            tvh.top_source_tv.setText(data.get("source"));
            GlideUtils.urlCenterCrop(data.get("logo"), w, h, tvh.top_logo_iv);

            return view;
        }

        class TopVH {
            @ViewInject(R.id.top_logo_iv)
            private ImageView top_logo_iv;

            @ViewInject(R.id.top_title_tv)
            private TextView top_title_tv;

            @ViewInject(R.id.top_source_tv)
            private TextView top_source_tv;

        }
    }
}
