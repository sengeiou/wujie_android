package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.tools.RegexUtils;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.AfterSale;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.minetoaty.order.TextListAty;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;

import java.util.Map;

/**
 * 退货界面
 */
public class Addshipping_aty extends BaseAty implements ProUrbAreaUtil.GetData {
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
            case R.id.layout: {
                String et_numberStr = String.valueOf(et_number.getText());
                if (TextUtils.isEmpty(et_numberStr)) {
                    showErrorTip("请先填写快递单号");
                    return;
                }
                if (et_numberStr.trim().length() < 5) {
                    showErrorTip("快递单号需大于五位数");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择快递");
                bundle.putString("invoice", et_numberStr);
                startActivityForResult(TextListAty.class, bundle, 888);
            }
            break;
            case R.id.submit:
                if (TextUtils.isEmpty(et_number.getText().toString())) {
                    showToast("请输入快递单号！");
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    showToast("请选择快递！");
                    return;
                }
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
                if (!RegexUtils.checkMobile(phone)) {
                    showToast("电话号码不正确");
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
                ProUrbAreaUtil proUrbAreaUtil = ProUrbAreaUtil.gainInstance();
                if (choiceAddress) {
                    province_id = proUrbAreaUtil.getProvince_id();
                    city_id = proUrbAreaUtil.getCity_id();
                    area_id = proUrbAreaUtil.getArea_id();
                }
                AfterSale.addShipping(tv_name.getText().toString(), et_number.getText().toString(), getIntent().getStringExtra("id"), receiver, phone, province_id, city_id, area_id, street_id, address, this);
                showProgressDialog();
                break;
            case R.id.zore_layout:// 解析数据，弹出城市选择弹窗
                hideKeyBoard();
//                if (isLoaded) {
//                    ShowPickerView();
//                }
                ProUrbAreaUtil.gainInstance().showPickerView((TextView) findViewById(R.id.zore_tv), "","","", Addshipping_aty.this, null);
                break;
            case R.id.street_layout:
                // 选择街道
                if (choiceAddress) {
                    area_id = ProUrbAreaUtil.gainInstance().getArea_id();
                }
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
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getApplication());
//        addressPst = new AddressPst(this);
//        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
//        String data = application.getCityProvienceJson();
//        if (android.text.TextUtils.isEmpty(data)) {
//            addressPst.androidAddress();
//        }else{
//            initJsonData(data);
//        }

        ProUrbAreaUtil.gainInstance().setGetData(this);
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
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
        } else if (requestUrl.contains("addShipping")) {
            Map<String, String> map1 = JSONUtils.parseKeyAndValueToMap(jsonStr);
            showToast(map1.get("message"));
            if (map1.get("code").equals("1")) {
                setResult(500);
                finish();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == RESULT_OK) {
            if (data.hasExtra("id")) {
                id = data.getStringExtra("id");
                tv_name.setText(data.getStringExtra("express"));
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {// 街道选择
            street = data.getStringExtra("street");
            street_tv.setText(street);
            street_id = data.getStringExtra("street_id");
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
