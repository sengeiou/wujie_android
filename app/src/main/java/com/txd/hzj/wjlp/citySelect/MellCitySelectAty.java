package com.txd.hzj.wjlp.citySelect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.City;
import com.txd.hzj.wjlp.citySelect.adapter.CityAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cc.solart.turbo.OnItemClickListener;
import cc.solart.wave.WaveSideBarView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/1 0001
 * 时间：下午 4:29
 * 描述：商家选择地址
 * ===============Txunda===============
 */
public class MellCitySelectAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    /**
     * 城市列表
     */
    @ViewInject(R.id.city_name_rv)
    private RecyclerView mRecyclerView;
    /**
     * 字母列表
     */
    @ViewInject(R.id.city_name_side_view)
    private WaveSideBarView mSideBarView;
    private CityAdapter adapter;

    /**
     * poi检索列表布局
     */
    @ViewInject(R.id.poi_search_layout)
    private LinearLayout poi_search_layout;

    /**
     * 城市选择列表布局
     */
    @ViewInject(R.id.select_city_layout)
    private FrameLayout select_city_layout;

    /**
     * 城市搜索按钮
     */
    @ViewInject(R.id.search_for_city_tv)
    private TextView search_for_city_tv;

    @ViewInject(R.id.show_or_hind_iv)
    private ImageView show_or_hind_iv;

    /**
     * 是否是选择城市
     */
    private boolean selectCity = true;


    @ViewInject(R.id.get_poi_search_lv)
    private ListView get_poi_search_lv;

    private POIAdapter poiAdapter;
    private List<City> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("定位");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);
        // 解析数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<ArrayList<City>>() {
                }.getType();
                Gson gson = new Gson();
                list = gson.fromJson(City.DATA, listType);
                Collections.sort(list, new LetterComparator());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CityAdapter(MellCitySelectAty.this, list);
                        mRecyclerView.setAdapter(adapter);
                        // Item的点击事件
                        adapter.addOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                                showRightTip(list.get(position).name);
                            }
                        });

                    }
                });
            }
        }).start();


        // 设置SideBar的点击事件
        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getLetterPosition(letter);

                if (pos != -1) {
                    mRecyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });
        get_poi_search_lv.setAdapter(poiAdapter);
    }

    @Override
    @OnClick({R.id.switch_search_or_select_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.switch_search_or_select_layout:

                if (selectCity) {// 城市选择
                    // 隐藏选择城市布局
                    select_city_layout.setVisibility(View.GONE);
                    // 显示POI检索列表布局
                    poi_search_layout.setVisibility(View.VISIBLE);
                    // 箭头向下
                    show_or_hind_iv.setImageResource(R.drawable.icon_hide_other_layout);
                    // 搜索按钮展示
                    search_for_city_tv.setVisibility(View.VISIBLE);
                    selectCity = false;
                } else {// POI检索
                    // 显示城市选择布局
                    select_city_layout.setVisibility(View.VISIBLE);
                    // 隐藏POI检索布局
                    poi_search_layout.setVisibility(View.GONE);
                    // 箭头向上
                    show_or_hind_iv.setImageResource(R.drawable.icon_show_other_layout);
                    // 隐藏搜索按钮
                    search_for_city_tv.setVisibility(View.GONE);
                    selectCity = true;

                }

                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_city_select;
    }

    @Override
    protected void initialized() {
        poiAdapter = new POIAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class POIAdapter extends BaseAdapter {

        private PoiVh poiVh;

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
                view = LayoutInflater.from(MellCitySelectAty.this).inflate(R.layout.item_text_lv_hzj, null);
                poiVh = new PoiVh();
                ViewUtils.inject(poiVh, view);
                view.setTag(poiVh);
            } else {
                poiVh = (PoiVh) view.getTag();
            }
            poiVh.text_context_tv.setText("鑫茂科技园");
            return view;
        }

        private class PoiVh {
            @ViewInject(R.id.text_context_tv)
            private TextView text_context_tv;
        }
    }

}
