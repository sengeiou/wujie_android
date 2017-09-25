package com.txd.hzj.wjlp.bean.addres;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/25 0025
 * 时间：10:24
 * 描述：
 * ===============Txunda===============
 */

public class CityForTxd implements IPickerViewData{

    private String city_id;
    private String cityName;
    private List<DistrictsForTxd> districts;

    public CityForTxd(String city_id, String cityName, List<DistrictsForTxd> districts) {
        this.city_id = city_id;
        this.cityName = cityName;
        this.districts = districts;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<DistrictsForTxd> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictsForTxd> districts) {
        this.districts = districts;
    }

    @Override
    public String getPickerViewText() {
        return getCityName();
    }

}
