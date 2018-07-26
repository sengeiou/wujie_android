package com.txd.hzj.wjlp.http.address;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：14:14
 * 描述：收货地址列表
 */

public class Address {

    private String url = Config.BASE_URL + "Address/";

    /**
     * 收货地址列表
     *
     * @param p        分页
     * @param baseView 回调
     */
    void addressList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("p", String.valueOf(p));
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "addressList", params, baseView);
    }

    /**
     * 设置为默认收货地址
     *
     * @param address_id 地址ID
     * @param baseView   回调
     */
    void setDefault(String address_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("address_id", address_id);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "setDefault", params, baseView);
    }

    /**
     * 获取一条收货地址
     *
     * @param address_id 地址ID
     * @param baseView   回调
     */
    void getOneAddress(String address_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("address_id", address_id);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "getOneAddress", params, baseView);
    }

    /**
     * 添加一条地址
     *
     * @param receiver    收货人
     * @param phone       收货手机号
     * @param province    省
     * @param city        市
     * @param area        区
     * @param street      街道
     * @param province_id 省id
     * @param city_id     市id
     * @param area_id     区id
     * @param street_id   街道id
     * @param address     详细地址
     * @param lng         经度
     * @param lat         纬度
     * @param baseView    回调
     */
    void addAddress(String receiver, String phone, String province, String city, String area, String street,
                    String province_id, String city_id, String area_id, String street_id, String address, String lng,
                    String lat, BaseView baseView) {

        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("receiver", receiver);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("province", province);
        params.addBodyParameter("city", city);
        params.addBodyParameter("area", area);
        params.addBodyParameter("street", street);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("street_id", street_id);
        params.addBodyParameter("address", address);
        params.addBodyParameter("lng", lng);
        params.addBodyParameter("lat", lat);
        apiTool2.postApi(url + "addAddress", params, baseView);
    }

    /**
     * 修改地址
     *
     * @param address_id  地址id
     * @param receiver    收货人
     * @param phone       收货手机号
     * @param province    省
     * @param city        市
     * @param area        区
     * @param street      街道
     * @param province_id 省id
     * @param city_id     市id
     * @param area_id     区id
     * @param street_id   街道id
     * @param address     详细地址
     * @param lng         经度
     * @param lat         纬度
     * @param baseView    回调
     */
    void editAddress(String address_id, String receiver, String phone, String province, String city, String area,
                     String street, String province_id, String city_id, String area_id, String street_id,
                     String address, String lng, String lat, BaseView baseView) {

        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("receiver", receiver);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("province", province);
        params.addBodyParameter("city", city);
        params.addBodyParameter("area", area);
        params.addBodyParameter("street", street);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("street_id", street_id);
        params.addBodyParameter("address", address);
        params.addBodyParameter("lng", lng);
        params.addBodyParameter("lat", lat);
        apiTool2.postApi(url + "editAddress", params, baseView);
    }


    /**
     * 删除地址
     *
     * @param address_id 地址id
     * @param baseView   回调
     */
    void delAddress(String address_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("address_id", address_id);
        apiTool2.postApi(url + "delAddress", params, baseView);
    }

    /**
     * 获取区域列表
     *
     * @param parent_id 上一级id
     * @param baseView  回调
     */
    void getRegion(String parent_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("region_id", parent_id);
        apiTool2.postApi(url + "getRegion", params, baseView);
    }

    /**
     * android 获取地址列表数据
     * @param baseView
     */
    void androidAddress(BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "androidAddress", new RequestParams(), baseView);
    }


    /**
     * 获取街道
     *
     * @param area_id  区县id
     * @param baseView 回调
     */
    void getStreet(String area_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("area_id", area_id);
        apiTool2.postApi(url + "getStreet", params, baseView);
    }
}
