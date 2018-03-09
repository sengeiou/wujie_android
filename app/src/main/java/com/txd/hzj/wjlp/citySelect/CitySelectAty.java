package com.txd.hzj.wjlp.citySelect;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.City;
import com.txd.hzj.wjlp.bean.City1;
import com.txd.hzj.wjlp.citySelect.adapter.CityAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.solart.wave.WaveSideBarView;

public class CitySelectAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 城市列表
     */
    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    /**
     * 字母列表
     */
    @ViewInject(R.id.side_view)
    private WaveSideBarView mSideBarView;

    private CityAdapter adapter;

    /**
     * 已定位城市
     */
    @ViewInject(R.id.location_city_gv)
    private GridViewForScrollView location_city_gv;
    /**
     * 最近访问的城市
     */
    @ViewInject(R.id.last_visit_city_gv)
    private GridViewForScrollView last_visit_city_gv;
    /**
     * 热门城市
     */
    @ViewInject(R.id.hot_city_gv)
    private GridViewForScrollView hot_city_gv;
    private CityForTitleAdapter cityForTitleAdapter;
    private CityForTitleAdapter cityForTitleAdapter1;
    private CityForTitleAdapter cityForTitleAdapter2;
      String json =null;
    String cityString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("当前地区—天津");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        download();
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
                final List<City> list = gson.fromJson(City.DATA, listType);
                Collections.sort(list, new LetterComparator());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CityAdapter(CitySelectAty.this, list);
                        mRecyclerView.setAdapter(adapter);
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
        location_city_gv.setAdapter(cityForTitleAdapter);
        last_visit_city_gv.setAdapter(cityForTitleAdapter1);
        hot_city_gv.setAdapter(cityForTitleAdapter2);
    }

    private String  download() {

         Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("region_id", "");
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .build()
                .rxPost("Address/getRegion", parameters, new RxStringCallback() {
                    @Override
                    public void onNext(Object tag, String response) {
                        Toast.makeText(CitySelectAty.this, response, Toast.LENGTH_SHORT).show();
                        json = response;

                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            JSONArray jsonArray = jsonObject2.getJSONArray("province_list");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String region_id =jsonObject1.getString("region_id");
                                String region_name = jsonObject1.getString("region_name");
                                String letter =jsonObject1.getString("letter");
                                ArrayList<City1> list = new ArrayList<City1>();
                                list.add(new City1(region_id,region_name,letter));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }


                });
        return  json;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_city_select;
    }

    @Override
    protected void initialized() {
        cityForTitleAdapter = new CityForTitleAdapter(0);
        cityForTitleAdapter1 = new CityForTitleAdapter(1);
        cityForTitleAdapter2 = new CityForTitleAdapter(2);
    }

    @Override
    protected void requestData() {

    }

    /**
     * 顶部的列表适配器
     */
    private class CityForTitleAdapter extends BaseAdapter {
        private int type;

        private CityTopViewHolder cityTopViewHolder;

        public CityForTitleAdapter(int type) {
            this.type = type;
        }

        @Override
        public int getCount() {
            int size;
            if (0 == type) {
                size = 1;
            } else if (1 == type) {
                size = 2;
            } else {
                size = 9;
            }
            return size;
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
                view = LayoutInflater.from(CitySelectAty.this).inflate(R.layout.item_city_for_top_gv, null);
                cityTopViewHolder = new CityTopViewHolder();
                ViewUtils.inject(cityTopViewHolder, view);
                view.setTag(cityTopViewHolder);
            } else {
                cityTopViewHolder = (CityTopViewHolder) view.getTag();
            }
            return view;
        }

        private class CityTopViewHolder {

        }

    }

}
