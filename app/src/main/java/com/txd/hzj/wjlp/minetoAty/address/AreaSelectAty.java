package com.txd.hzj.wjlp.minetoAty.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Area;
import com.txd.hzj.wjlp.bean.AreaList;
import com.txd.hzj.wjlp.bean.CityList;
import com.txd.hzj.wjlp.bean.ProvinceList;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.minetoAty.address.adapter.AreaAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：下午 4:21
 * 描述：城市选择
 * ===============Txunda===============
 */
public class AreaSelectAty extends BaseAty {

    private AddressPst addressPst;

    /**
     * 省
     */
    @ViewInject(R.id.options1)
    private WheelView province_wv;
    /**
     * 市
     */
    @ViewInject(R.id.options2)
    private WheelView city_wv;
    /**
     * 区
     */
    @ViewInject(R.id.options3)
    private WheelView area_wv;

    /**
     * 省
     */
    private String province;
    /**
     * 省id
     */
    private String province_id;
    /**
     * 市
     */
    private String city;
    /**
     * 市id
     */
    private String city_id;
    /**
     * 区
     */
    private String area;
    /**
     * 区id
     */
    private String area_id;

    private List<ProvinceList> pros;
    private List<CityList> citys;
    private List<AreaList> areas;

    /**
     * 省 滚动
     */
    private boolean prov_select = false;
    /**
     * 市 滚动
     */
    private boolean city_select = false;
    /**
     * 区 滚动
     */
    private boolean area_select = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        province_wv.setCyclic(false);
        city_wv.setCyclic(false);
        area_wv.setCyclic(false);

        province_wv.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                province = pros.get(index).getRegion_name();
                province_id = pros.get(index).getRegion_id();
                L.e("=====省id=====", province_id);
                prov_select = true;
                city_select = false;
                area_select = false;
                addressPst.getRegion(province_id);

            }
        });
        city_wv.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                city = citys.get(index).getRegion_name();
                city_id = citys.get(index).getRegion_id();
                city_select = true;
                area_select = false;
                addressPst.getRegion(city_id);
            }
        });
        area_wv.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                area = areas.get(index).getRegion_name();
                area_id = areas.get(index).getRegion_id();
                area_select = true;
            }
        });

    }

    @Override
    @OnClick({R.id.right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_tv:// 城市选择

                Intent intent = new Intent();
                intent.putExtra("province", province);
                intent.putExtra("province_id", province_id);
                intent.putExtra("city", city);
                intent.putExtra("city_id", city_id);
                intent.putExtra("area", area);
                intent.putExtra("area_id", area_id);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_area_select_hzj;
    }

    @Override
    protected void initialized() {
        addressPst = new AddressPst(this);
        pros = new ArrayList<>();
        citys = new ArrayList<>();
        areas = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        addressPst.getRegion("");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Area getArea = GsonUtil.GsonToBean(jsonStr, Area.class);

        citys.clear();
        areas.clear();
        citys = getArea.getData().getCity_list();
        areas = getArea.getData().getArea_list();


        if (!prov_select) {// 省
            pros = getArea.getData().getProvince_list();
            L.e("=====省=====", pros.toString());
            province_wv.setAdapter(new AreaAdapter<>(pros));
            province = pros.get(0).getRegion_name();
            province_id = pros.get(0).getRegion_id();
        }
        L.e("=====市=====", citys.toString());
        if (!city_select) {// 市
            city_id = citys.get(0).getRegion_id();
            city = citys.get(0).getRegion_name();
            if (!ListUtils.isEmpty(citys)) {
                city_wv.setAdapter(new AreaAdapter<>(citys));
                city_wv.setCurrentItem(0);
            }
        }

        L.e("=====区=====", areas.toString());
        if (!area_select) {// 区
            area_id = areas.get(0).getRegion_id();
            area = areas.get(0).getRegion_name();
            if (!ListUtils.isEmpty(areas)) {
                area_wv.setAdapter(new AreaAdapter<>(areas));
                area_wv.setCurrentItem(0);
            }
        }

    }

}
