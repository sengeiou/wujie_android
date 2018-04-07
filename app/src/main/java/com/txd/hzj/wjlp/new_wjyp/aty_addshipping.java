package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
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
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.new_wjyp.http.AfterSale;
import com.txd.hzj.wjlp.tool.GetJsonDataUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class aty_addshipping extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    private String id;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.et_number)
    private EditText et_number;

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

    private String address_id = "";


    /**
     * 姓名
     */
    @ViewInject(R.id.address_name_et)
    private EditText address_name_et;
    /**
     * 电话
     */
    @ViewInject(R.id.address_phone_et)
    private EditText address_phone_et;

    /**
     * 详细地址
     */
    @ViewInject(R.id.address_details_et)
    private EditText address_details_et;
    private String lng = "";
    private String lat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("运单信息");
    }

    @OnClick({R.id.layout, R.id.submit, R.id.zore_layout, R.id.street_layout})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout:
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择快递");
                startActivityForResult(TextListAty.class, bundle, 888);
                break;
            case R.id.submit:

                if (TextUtils.isEmpty(id)) {
                    showToast("请选择快递！");
                    return;
                }
                if (TextUtils.isEmpty(et_number.getText().toString())) {
                    showToast("请输入快递单号！");
                    return;
                }
                // TODO =============================================================================
                String receiver = address_name_et.getText().toString().trim();
                String phone = address_phone_et.getText().toString().trim();
                String address = address_details_et.getText().toString().trim();
                if (receiver.equals("")) {
                    showToast("姓名不能为空");
                    return;
                }
                if (phone.equals("")) {
                    showToast("电话不能为空");
                    return;
                }
                if (zore_tv.getText().toString().trim().equals("")) {
                    showToast("区域不能为空");
                    return;
                }
                if (street_tv.getText().toString().trim().equals("")) {
                    showToast("街道不能为空");
                    return;
                }
                if (address.equals("")) {
                    showToast("详细地址不能为空");
                    return;
                }
//                    addressPst.addAddress(receiver, phone, province, city, area, street, province_id, city_id, area_id, street_id, address, lng, lat);
                // TODO =============================================================================

//                        addShipping(shipping_id,invoice,back_apply_id,receiver,receiver_phone,province,city,area,street,address,baseView)

                AfterSale.addShipping(id, et_number.getText().toString(), getIntent().getStringExtra("id"), receiver, phone, province_id, city_id, area_id, street_id, address, this);
                showProgressDialog();
                break;
            case R.id.zore_layout:// 解析数据，弹出城市选择弹窗
                hideKeyBoard();
                if (isLoaded) {
                    ShowPickerView();
                }
                break;
            case R.id.street_layout:// 解析数据，弹出城市选择弹窗
                if (area_id.equals("")) {
                    showErrorTip("请选择省市区");
                    break;
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "选择街道");
                bundle1.putString("area_id", area_id);
                startActivityForResult(TextListAty.class, bundle1, 100);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_addshipping;
    }

    @Override
    protected void initialized() {
        addressPst = new AddressPst(this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        addressPst.androidAddress();
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

        Map<String, String> map1 = JSONUtils.parseKeyAndValueToMap(jsonStr);

        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("getOneAddress")) {// 获取一条地址
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            address_name_et.setText(data.get("receiver"));
            address_phone_et.setText(data.get("phone"));
            zore_tv.setText(data.get("province") + data.get("city") + data.get("area"));
            street_tv.setText(data.get("street"));
            province_id = data.get("province_id");
            city_id = data.get("city_id");
            area_id = data.get("area_id");
            street_id = data.get("street_id");
            address_details_et.setText(data.get("address"));

            lng = data.get("lng");
            lat = data.get("lat");
            return;
        }
        if (requestUrl.contains("addShipping")) {
            showToast(map1.get("message"));
            if (map1.get("code").equals("1")) {
                finish();
            }
        }

        if (requestUrl.contains("androidAddress")) {
            L.e("wang", jsonStr);
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String data = jsonObject.getString("data");
                L.e("wang", data);
                initJsonData(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========

    private ArrayList<ProvinceForTxd> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityForTxd>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<DistrictsForTxd>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;


    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                .OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                // 省
                province = options1Items.get(options1).getPickerViewText();
                province_id = options1Items.get(options1).getProvince_id();
                // 市
                city = options2Items.get(options1).get(options2).getPickerViewText();
                city_id = options2Items.get(options1).get(options2).getCity_id();
                // 区
                area = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                area_id = options3Items.get(options1).get(options2).get(options3).getDistrict_id();
                // 设置省市区
                String tx = province + city + area;
                zore_tv.setText(tx);
                street_tv.setText("");
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private boolean isLoaded = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
//                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    removeDialog();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    removeDialog();
                    showErrorTip("解析失败");
                    break;

            }
        }
    };

    private void initJsonData(String JsonData) {//解析数据

        /*
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
//        String JsonData = new GetJsonDataUtil().getJson(this, "provinceFotTxd.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceForTxd> jsonBean = parseData(JsonData);//用Gson 转成实体

        /*
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<CityForTxd> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<DistrictsForTxd>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCities().size(); c++) {//遍历该省份的所有城市

                CityList.add(jsonBean.get(i).getCities().get(c));//添加城市

                ArrayList<DistrictsForTxd> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCities().get(c).getDistricts() == null
                        || jsonBean.get(i).getCities().get(c).getDistricts().size() == 0) {
                    City_AreaList.add(new DistrictsForTxd("", ""));
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCities().get(c).getDistricts().size(); d++) {//该城市对应地区所有数据
                        DistrictsForTxd AreaName = jsonBean.get(i).getCities().get(c).getDistricts().get(d);
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /*
             * 添加城市数据
             */
            options2Items.add(CityList);
            /*
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<ProvinceForTxd> parseData(String result) {//Gson 解析
        ArrayList<ProvinceForTxd> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceForTxd entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceForTxd.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            L.e("=====异常=====", e.getMessage());
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888) {
            id = data.getStringExtra("id");
            tv_name.setText(data.getStringExtra("express"));
        }
        if (requestCode == 100) {// 街道选择
            street = data.getStringExtra("street");
            street_tv.setText(street);
            street_id = data.getStringExtra("street_id");
        }
    }
}
