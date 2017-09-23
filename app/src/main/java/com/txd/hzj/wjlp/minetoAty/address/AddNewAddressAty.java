package com.txd.hzj.wjlp.minetoAty.address;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Area;
import com.txd.hzj.wjlp.bean.AreaList;
import com.txd.hzj.wjlp.bean.CityList;
import com.txd.hzj.wjlp.bean.JsonBean;
import com.txd.hzj.wjlp.bean.ProvinceList;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.tool.GetJsonDataUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
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
    private String pStr = "";
    private String cStr = "";
    private String aStr = "";
    private int times = 0;

    // TODO==========百度地图根据具体地址获取经纬度==========
    // TODO==========百度地图根据具体地址获取经纬度==========
    // TODO==========百度地图根据具体地址获取经纬度==========
    // TODO==========百度地图根据具体地址获取经纬度==========
    // TODO==========百度地图根据具体地址获取经纬度==========


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

                if (isLoaded) {
                    ShowPickerView();
                }

//                startActivityForResult(AreaSelectAty.class, null, 101);
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
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
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
        if (requestUrl.contains("getOneAddress")) {// 获取一条地址
            super.onComplete(requestUrl, jsonStr);
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
            return;
        }

        if (requestUrl.contains("getRegion")) {
            Area getArea = GsonUtil.GsonToBean(jsonStr, Area.class);
            if (1 == times) {// 省
                List<ProvinceList> pros = getArea.getData().getProvince_list();
                for (ProvinceList pl : pros) {
                    if (pl.getRegion_name().contains(pStr) || pStr.contains(pl.getRegion_name())) {
                        province_id = pl.getRegion_id();
                        province = pl.getRegion_name();
                        break;
                    }
                }
                times = 2;
                addressPst.getRegion(province_id);
                return;
            }
            if (2 == times) {// 市
                List<CityList> citys = getArea.getData().getCity_list();
                for (CityList cl : citys) {
                    if (cl.getRegion_name().contains(cStr) || cStr.contains(cl.getRegion_name())) {
                        city_id = cl.getRegion_id();
                        city = cl.getRegion_name();
                        break;
                    }
                }
                times = 3;
                addressPst.getRegion(city_id);
                return;
            }
            if (3 == times) {// 区(移除加载框)
                super.onComplete(requestUrl, jsonStr);
                List<AreaList> areas = getArea.getData().getArea_list();
                for (AreaList al : areas) {
                    if (al.getRegion_name().contains(aStr) || aStr.contains(al.getRegion_name())) {
                        area_id = al.getRegion_id();
                        area = al.getRegion_name();
                        break;
                    }
                }
                String tx = province + city + area;
                zore_tv.setText(tx);
            }
        }

    }

    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;


    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                .OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText() +
//                        options2Items.get(options1).get(options2) +
//                        options3Items.get(options1).get(options2).get(options3);
                pStr = options1Items.get(options1).getPickerViewText();
                cStr = options2Items.get(options1).get(options2);
                aStr = options3Items.get(options1).get(options2).get(options3);
                // 第一次获取省的信息
                times = 1;
                addressPst.getRegion("");

            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
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
                                initJsonData();
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

    private void initJsonData() {//解析数据

        /*
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /*
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

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


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
