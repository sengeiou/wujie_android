package com.txd.hzj.wjlp.mellOnLine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/12 0012
 * 时间：上午 10:03
 * 描述：无界头条
 * ===============Txunda===============
 */
public class WujieTopHzjAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.wujie_top_lv)
    private ListView wujie_top_lv;

    private List<String> list;

    private TopAdapter topAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("无界头条");
        wujie_top_lv.setAdapter(topAdapter);
    }

    @Override
    @OnItemClick({R.id.wujie_top_lv})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        switch (parent.getId()){
            case R.id.wujie_top_lv://头条详情
                Bundle bundle = new Bundle();
                bundle.putInt("from",1);
                startActivity(NoticeDetailsAty.class,bundle);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_wujie_top_hzj;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        topAdapter = new TopAdapter();
    }

    @Override
    protected void requestData() {

    }
    private class TopAdapter extends BaseAdapter{
        private TopVH tvh;
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(null == view){
                view = LayoutInflater.from(WujieTopHzjAty.this).inflate(R.layout.item_wujie_top_lv,null);
                tvh = new TopVH();
                ViewUtils.inject(tvh,view);
                view.setTag(tvh);
            } else {
                tvh = (TopVH) view.getTag();
            }
            return view;
        }

        class TopVH{

        }
    }
}
