package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.http.address.AddressPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/22 11:08
 * 功能描述：按筛选条件
 */
public class ScreeningConditionsAty extends BaseAty{
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.addressTv)
    private TextView addressTv;

    @ViewInject(R.id.typeTv)
    private TextView typeTv;

    private ArrayList<Map<String, String>> mCateData;
    private String mSta_mid;
    private AddressPst mAddressPst;
    private ArrayList<ProvinceForTxd> options1Items = new ArrayList<>();//临时数据存储省份
    private ArrayList<ArrayList<CityForTxd>> options2Items = new ArrayList<>();//临时数据存储城市
    private ArrayList<ArrayList<ArrayList<DistrictsForTxd>>> options3Items = new ArrayList<>();//临时数据存储区域
    private String mCity_id;
    private String mCate_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_screening_conditions;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("按筛选条件");
        mCateData = (ArrayList<Map<String, String>>) getIntent().getSerializableExtra("cate_data");
        mSta_mid = getIntent().getStringExtra("sta_mid");
    }

    @Override
    protected void requestData() {
        mAddressPst = new AddressPst(this);
        mAddressPst.androidAddress();
    }

    @Override
    public void onComplete(String requestUrl, final String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        final Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("androidAddress")) {
            options1Items = JSONUtils.parseKeyAndValueToMapList(ProvinceForTxd.class, map.get("data"));
            for (int i = 0; i < options1Items.size(); i++) {
                ProvinceForTxd provinceForTxd = options1Items.get(i);
                ArrayList<CityForTxd> cities = (ArrayList<CityForTxd>) provinceForTxd.getCities();
                options2Items.add(cities);
                ArrayList<ArrayList<DistrictsForTxd>> hh = new ArrayList<>();
                for (int i1 = 0; i1 < cities.size(); i1++) {
                    CityForTxd cityForTxd = cities.get(i1);
                    ArrayList<DistrictsForTxd> districts = (ArrayList<DistrictsForTxd>) cityForTxd.getDistricts();
                    hh.add(districts);
                }
                options3Items.add(hh);
            }
            return;
        }

    }


    @Override
    @OnClick({R.id.addressLayout,R.id.typeLayout,R.id.screenTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addressLayout:
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        addressTv.setText(options1Items.get(options1).getProvincename() + options2Items.get(options1).get(option2).getCityname() + options3Items.get(options1).get(option2).get(options3).getDistrictname());
                        mCity_id = options3Items.get(options1).get(option2).get(options3).getDistrict_id();
                    }
                })
                        .setOutSideCancelable(false)
                        .build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();
                break;
            case R.id.typeLayout:
                final List<String> titles = new ArrayList<>();
                for (int i = 0; i < mCateData.size(); i++) {
                    titles.add(mCateData.get(i).get("text"));
                }
                OptionsPickerView pvOptions2 = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        typeTv.setText(titles.get(options1));
                        mCate_id = mCateData.get(options1).get("value");
                    }
                })
                        .setOutSideCancelable(false)
                        .build();
                pvOptions2.setPicker(titles);
                pvOptions2.show();
                break;
            case R.id.screenTv:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                bundle.putString("cate_id",mCate_id);
                bundle.putString("city_id",mCity_id);
                startActivity(ScreeningResultAty.class,bundle);
                break;
        }
    }


}
