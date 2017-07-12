package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：上午 11:19
 * 描述：进口馆
 * ===============Txunda===============
 */
public class GoodsInputHzjAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.global_gv)
    private GridViewForScrollView global_gv;

    /**
     * 进口馆商品列表
     */
    @ViewInject(R.id.global_goods_gv)
    private GridViewForScrollView global_goods_gv;

    private AllGvLvAdapter allGvAdapter;

    private CountryAdapter countryAdapter;
    private List<String> list;
    private List<String> country;
    private int type = 3;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("进口馆");
        global_goods_gv.setAdapter(allGvAdapter);
        global_gv.setAdapter(countryAdapter);
        global_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putInt("type", type);
                bundle.putString("title", "英国馆");
                startActivity(TicketZoonAty.class, bundle);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_input_hzj;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        country = new ArrayList<>();
        type = getIntent().getIntExtra("type", 3);
        allGvAdapter = new AllGvLvAdapter(this, list, type);
        countryAdapter = new CountryAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class CountryAdapter extends BaseAdapter {
        private CountryVh countryVh;

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return country.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = LayoutInflater.from(GoodsInputHzjAty.this).inflate(R.layout.item_global_gv_hzj, viewGroup,
                        false);
                countryVh = new CountryVh();
                ViewUtils.inject(countryVh, view);
                view.setTag(countryVh);
            } else {
                countryVh = (CountryVh) view.getTag();
            }
            return view;
        }

        class CountryVh {
        }
    }

}
