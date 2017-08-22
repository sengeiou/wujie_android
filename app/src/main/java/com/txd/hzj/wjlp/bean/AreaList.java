package com.txd.hzj.wjlp.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/22 0022
 * 时间：09:40
 * 描述：区
 * ===============Txunda===============
 */

public class AreaList implements IPickerViewData {
    /**
     * region_id : 区县id
     * region_name : 区县名称
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

    public AreaList(String region_id, String region_name) {
        this.region_id = region_id;
        this.region_name = region_name;
    }

    @Override
    public String toString() {
        return "AreaList{" +
                "region_id='" + region_id + '\'' +
                ", region_name='" + region_name + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return getRegion_name();
    }
}
