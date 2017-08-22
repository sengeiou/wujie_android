package com.txd.hzj.wjlp.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/22 0022
 * 时间：09:39
 * 描述：市
 * ===============Txunda===============
 */

public class CityList implements IPickerViewData {
    /**
     * region_id : 52
     * region_name : 北京市
     */

    private String region_id;
    private String region_name;

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public CityList(String region_id, String region_name) {
        this.region_id = region_id;
        this.region_name = region_name;
    }

    @Override
    public String toString() {
        return "CityList{" +
                "region_id='" + region_id + '\'' +
                ", region_name='" + region_name + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return getRegion_name();
    }
}
