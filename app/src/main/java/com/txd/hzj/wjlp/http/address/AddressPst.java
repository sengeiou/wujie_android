package com.txd.hzj.wjlp.http.address;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：14:31
 * 描述：
 * ===============Txunda===============
 * 地址相关所用网络接口
 */

public class AddressPst extends BasePresenter {
    private Address address;

    public AddressPst(BaseView baseView) {
        super(baseView);
        address = new Address();
    }

    /**
     * 收货地址列表
     *
     * @param p 分页
     */
    public void addressList(int p, boolean show) {
        if (show) {
            baseView.showDialog();
        }
        address.addressList(p, baseView);
    }

    /**
     * 设置默认收货地址
     *
     * @param address_id 地址id
     */
    public void setDefault(String address_id) {
        baseView.showDialog();
        address.setDefault(address_id, baseView);
    }

    /**
     * 获取一条收货地址
     *
     * @param address_id 地址id
     */
    public void getOneAddress(String address_id) {
        baseView.showDialog();
        address.getOneAddress(address_id, baseView);
    }

    /**
     * 添加一条收货地址
     *
     * @param receiver    收件人
     * @param phone       收货电话
     * @param province    省
     * @param city        市
     * @param area        区
     * @param street      街道
     * @param province_id 省id
     * @param city_id     市id
     * @param area_id     区id
     * @param street_id   街道ID
     * @param address     详细地址
     * @param lng         经度
     * @param lat         纬度
     */
    public void addAddress(String receiver, String phone, String province, String city, String area,
                           String street, String province_id, String city_id, String area_id, String street_id,
                           String address, String lng, String lat) {
        baseView.showDialog();
        this.address.addAddress(receiver, phone, province, city, area, street, province_id, city_id, area_id, street_id, address, lng, lat, baseView);
    }

    /**
     * 编辑收货地址
     *
     * @param address_id  地址id
     * @param receiver    收货人
     * @param phone       联系方式
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
     */
    public void editAddress(String address_id, String receiver, String phone, String province, String city, String area,
                            String street, String province_id, String city_id, String area_id, String street_id,
                            String address, String lng, String lat) {
        baseView.showDialog();
        this.address.editAddress(address_id, receiver, phone, province, city, area, street, province_id, city_id,
                area_id,
                street_id, address, lng, lat, baseView);
    }


    /**
     * 删除地址
     *
     * @param address_id 地址id
     */
    public void delAddress(String address_id) {
        baseView.showDialog();
        address.delAddress(address_id, baseView);
    }

    /**
     * 获取区域列表
     *
     * @param parent_id 上一级id,可以为空
     */
    public void getRegion(String parent_id) {
//        if (parent_id.equals("")) {
//            baseView.showDialog();
//        }
        address.getRegion(parent_id, baseView);
    }

    /**
     * android 获取地址列表数据
     */
    public void androidAddress(){
        address.androidAddress(baseView);
    }

    /**
     * 获取区域列表
     *
     * @param area_id 区县id
     */
    public void getStreet(String area_id) {
        baseView.showDialog();
        address.getStreet(area_id, baseView);
    }

}
