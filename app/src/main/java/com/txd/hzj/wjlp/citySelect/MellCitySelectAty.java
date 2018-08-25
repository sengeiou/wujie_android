package com.txd.hzj.wjlp.citySelect;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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
 * 作者：DUKE_HwangZj
 * 日期：2017/8/1 0001
 * 时间：下午 4:29
 * 描述：商家选择地址
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

    @ViewInject(R.id.search_edit)
    private EditText search_edit;

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


    /**
     * 定位城市
     */
    @ViewInject(R.id.city_tv1)
    private TextView city_tv1;

    @ViewInject(R.id.city_tv2)
    private TextView city_tv2;

    /**
     * 重新定位
     */
    @ViewInject(R.id.reLocation_tv)
    private TextView reLocation_tv;


    @ViewInject(R.id.get_poi_search_lv)
    private ListView get_poi_search_lv;

    private POIAdapter poiAdapter;
    private List<City> list;
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private List<Poi> mPoiList;

    /**
     * poi
     */

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

    }

    @Override
    @OnClick({R.id.switch_search_or_select_layout,R.id.search_for_city_tv,R.id.reLocation_tv})
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
            case R.id.search_for_city_tv:
                String searchContent = search_edit.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)){
                    showToast("搜索内容不能为空");
                    return;
                }
                for (int i = 0; i < mPoiList.size(); i++) {
                    if (mPoiList.get(i).getName().contains(searchContent)){
                        get_poi_search_lv.smoothScrollToPosition(i);
                        break;
                    }
                }
                break;

            case R.id.reLocation_tv:
                if (mLocationClient!=null && !mLocationClient.isStarted()){
                    LocationClientOption option = new LocationClientOption();

                    option.setIsNeedAddress(true);
                    //可选，是否需要地址信息，默认为不需要，即参数为false
                    //如果开发者需要获得当前点的地址信息，此处必须为true

                    option.setIsNeedLocationDescribe(true);
                    //可选，是否需要位置描述信息，默认为不需要，即参数为false
                    //如果开发者需要获得当前点的位置信息，此处必须为true

                    option.setIsNeedLocationPoiList(true);
                    //可选，是否需要周边POI信息，默认为不需要，即参数为false
                    //如果开发者需要获得周边POI信息，此处必须为true

                    mLocationClient.setLocOption(option);
                    //mLocationClient为第二步初始化过的LocationClient对象
                    //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
                    //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
                    mLocationClient.start();
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

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        option.setIsNeedLocationDescribe(true);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true

        option.setIsNeedLocationPoiList(true);
        //可选，是否需要周边POI信息，默认为不需要，即参数为false
        //如果开发者需要获得周边POI信息，此处必须为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLocationClient!=null){
            mLocationClient.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient!=null){
            mLocationClient.stop();
        }
    }

    private class POIAdapter extends BaseAdapter {

        private PoiVh poiVh;
        private List<Poi> mPoiList;

        public POIAdapter(List<Poi> poiList) {
            mPoiList = poiList;
        }

        @Override
        public int getCount() {
            return mPoiList.size()>0?mPoiList.size():0;
        }

        @Override
        public Object getItem(int i) {
            return mPoiList.get(i);
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
            poiVh.text_context_tv.setText(mPoiList.get(i).getName());
            return view;
        }

        private class PoiVh {
            @ViewInject(R.id.text_context_tv)
            private TextView text_context_tv;
        }
    }


    private class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            //获取周边POI信息  POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明
            mPoiList = location.getPoiList();
            city_tv1.setText(city);
            city_tv2.setText(city);
            if (null!=mPoiList){
                poiAdapter = new POIAdapter(mPoiList);
                get_poi_search_lv.setAdapter(poiAdapter);
            }

            Log.e("TAG", "address:"+addr+"\ncountry"+country +"\nprovince"+province+"\ncity"+city+"\ndistrict"+district+"\nstreet"+street+"\nlocationDescribe"+locationDescribe);
        }
    }

}
