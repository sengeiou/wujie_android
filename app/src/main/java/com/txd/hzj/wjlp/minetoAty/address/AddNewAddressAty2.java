package com.txd.hzj.wjlp.minetoAty.address;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.tool.GetJsonDataUtil;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/25 0025
 * 时间：10:29
 * 描述：
 * ===============Txunda===============
 */

public class AddNewAddressAty2 extends BaseAty implements ProUrbAreaUtil.GetData {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    /**
     * 省市区
     */
    @ViewInject(R.id.zore_tv)
    private TextView zore_tv;

    @ViewInject(R.id.street_tv)
    private TextView street_tv;

    /**
     * 省
     */
    private String province = "";
    /**
     * 市
     */
    private String city = "";
    /**
     * 区
     */
    private String area = "";

    private AddressPst addressPst;

    /**
     * 省id
     */
    private String province_id = "";
    /**
     * 市id
     */
    private String city_id = "";
    /**
     * 区id
     */
    private String area_id = "";

    /**
     * 街道
     */
    private String street = "";
    /**
     * 街道id
     */
    private String street_id = "";

    private int type = 0;

    private String address_id = "";


    /**
     * 姓名
     */
    @ViewInject(R.id.address_name_tv)
    private EditText address_name_tv;
    /**
     * 电话
     */
    @ViewInject(R.id.address_phone_tv)
    private EditText address_phone_tv;

    /**
     * 详细地址
     */
    @ViewInject(R.id.address_details_tv)
    private EditText address_details_tv;
    private String lng = "";
    private String lat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("收货地址");
        titlt_right_tv.setText("保存");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        ProUrbAreaUtil.gainInstance().setGetData(this);
    }

    @Override
    @OnClick({R.id.titlt_right_tv, R.id.zore_layout, R.id.street_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:
                String receiver = address_name_tv.getText().toString().trim();
                String phone = address_phone_tv.getText().toString().trim();
                String address = address_details_tv.getText().toString().trim();
                if (receiver.equals("")) {
                    showToast("姓名不能为空");
                    return;
                } else if (phone.equals("")) {
                    showToast("电话不能为空");
                    return;
                } else if (zore_tv.getText().toString().trim().equals("")) {
                    showToast("区域不能为空");
                    return;
                } else if (street_tv.getText().toString().trim().equals("")) {
                    showToast("街道不能为空");
                    return;
                } else if (address.equals("")) {
                    showToast("详细地址不能为空");
                    return;
                } else {
                    ProUrbAreaUtil proUrbAreaUtil = ProUrbAreaUtil.gainInstance();
                    if (choiceAddress) {
                        province_id = proUrbAreaUtil.getProvince_id();
                        province = proUrbAreaUtil.getProvince();

                        city_id = proUrbAreaUtil.getCity_id();
                        city = proUrbAreaUtil.getProvince();

                        area_id = proUrbAreaUtil.getArea_id();
                        area = proUrbAreaUtil.getArea();
                    }

                    try {
                        if (0 == type) {
                            addressPst.addAddress(receiver, phone, province, city, area, street, province_id, city_id, area_id, street_id, address, lng, lat);
                        } else {
                            addressPst.editAddress(address_id, receiver, phone, province, city, area, street, province_id, city_id, area_id, street_id, address, lng, lat);
                        }
                    }catch (Exception e){
                        L.e("AddNewAddressAty2 is throw exception:" + e.toString());
                        showErrorTip("传入参数异常，请重新核对");
                    }
                }
                break;
            case R.id.zore_layout:// 解析数据，弹出城市选择弹窗
                hideKeyBoard();
//                if (isLoaded) {
//                    ShowPickerView();
//                }
                ProUrbAreaUtil.gainInstance().showPickerView((TextView) findViewById(R.id.zore_tv), "", AddNewAddressAty2.this,null);
                break;
            case R.id.street_layout:// 解析数据，跳转至街道选择页面
                if (choiceAddress) {
                    area_id = ProUrbAreaUtil.gainInstance().getArea_id();
                }

                if (area_id.equals("")) {
                    showErrorTip("请选择省市区");
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择街道");
                bundle.putString("area_id", area_id);
                startActivityForResult(TextListAty.class, bundle, 100);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_add_new_address;
    }

    @Override
    protected void initialized() {
        addressPst = new AddressPst(this);
        type = getIntent().getIntExtra("type", 0);
        String data = application.getCityProvienceJson();
//        if (android.text.TextUtils.isEmpty(data)) {
//            addressPst.androidAddress();
//        } else {
//            initJsonData(data);
//        }
    }

    @Override
    protected void requestData() {
        if (1 == type) {
            address_id = getIntent().getStringExtra("address_id");
            addressPst.getOneAddress(address_id);
        }
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getApplication());
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        if (requestUrl.contains("getOneAddress")) {// 获取一条地址
            super.onComplete(requestUrl, jsonStr);
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            address_name_tv.setText(data.get("receiver"));
            address_phone_tv.setText(data.get("phone"));

            street_id = data.get("street_id");
            street = data.get("street");
            street_tv.setText(street);

            province_id = data.get("province_id");
            province = data.get("province");

            city_id = data.get("city_id");
            city = data.get("city");

            area_id = data.get("area_id");
            area = data.get("area");

            address_details_tv.setText(data.get("address"));
            zore_tv.setText(province + "," + city + "," + area);

            lng = data.get("lng");
            lat = data.get("lat");
            if (lng.isEmpty())
                lng = "0";
            if (lat.isEmpty())
                lat = "0";
            return;
        }
        if (requestUrl.contains("addAddress")) {// 添加地址
            super.onComplete(requestUrl, jsonStr);
            showRightTip("添加成功");
            finish();
            return;
        }
        if (requestUrl.contains("editAddress")) {// 删除地址
            super.onComplete(requestUrl, jsonStr);
            showRightTip("修改成功");
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 100:// 街道选择
                    street = data.getStringExtra("street");
                    street_tv.setText(street);
                    street_id = data.getStringExtra("street_id");

                    break;
            }

        }
    }

    private boolean choiceAddress = false;

    @Override
    public void getAddress() {
        choiceAddress = true;
        ProUrbAreaUtil proUrbAreaUtil = ProUrbAreaUtil.gainInstance();
        zore_tv.setText(proUrbAreaUtil.getProvince() + "," + proUrbAreaUtil.getCity() + "," + proUrbAreaUtil.getArea());
        street_tv.setText("");
    }
}
