package com.txd.hzj.wjlp.minetoAty.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 8:50
 * 描述：新增收货地址
 * ===============Txunda===============
 */
public class AddNewAddressAty extends BaseAty {
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
    }

    @Override
    @OnClick({R.id.titlt_right_tv, R.id.zore_layout, R.id.street_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:
                String receiver = address_name_tv.getText().toString();
                String phone = address_phone_tv.getText().toString();
                String address = address_details_tv.getText().toString();
                if (0 == type) {
                    addressPst.addAddress(receiver, phone, province, city, area, street, province_id, city_id, area_id,
                            street_id, address, lng, lat);
                } else {
                    addressPst.editAddress(address_id, receiver, phone, province, city, area, street, province_id,
                            city_id, area_id, street_id, address, lng, lat);
                }
                break;
            case R.id.zore_layout:// 解析数据，弹出城市选择弹窗
                startActivityForResult(AreaSelectAty.class, null, 101);
                break;
            case R.id.street_layout:// 解析数据，弹出城市选择弹窗
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
        type = getIntent().getIntExtra("type", 0);
        addressPst = new AddressPst(this);

    }

    @Override
    protected void requestData() {
        if (1 == type) {
            address_id = getIntent().getStringExtra("address_id");
            addressPst.getOneAddress(address_id);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("getOneAddress")) {// 获取一条地址
            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            Map<String, String> data = (Map<String, String>) map.get("data");

            address_name_tv.setText(data.get("receiver"));
            address_phone_tv.setText(data.get("phone"));
            zore_tv.setText(data.get("province" + data.get("city") + data.get("area")));
            street_tv.setText(data.get("street"));
            province_id = data.get("province_id");
            city_id = data.get("city_id");
            area_id = data.get("area_id");
            street_id = data.get("street_id");
            address_details_tv.setText(data.get("address"));

            lng = data.get("lng");
            lat = data.get("lat");
            return;
        }
        if (requestUrl.contains("addAddress")) {
            showRightTip("添加成功");
            finish();
            return;
        }
        if (requestUrl.contains("editAddress")) {
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
                case 100:// 街道
                    street = data.getStringExtra("street");
                    street_id = data.getStringExtra("street_id");
                    street_tv.setText(street);
                    break;
                case 101:// 省市区
                    province = data.getStringExtra("province");
                    city = data.getStringExtra("city");
                    area = data.getStringExtra("area");

                    province_id = data.getStringExtra("province_id");
                    city_id = data.getStringExtra("city_id");
                    area_id = data.getStringExtra("area_id");

                    // 省市区
                    String tx = province + city + area;
                    zore_tv.setText(tx);
                    break;
            }
        }
    }
}
