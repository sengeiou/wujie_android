package com.txd.hzj.wjlp.bean.addres;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/25 0025
 * 时间：10:25
 * 描述：
 * ===============Txunda===============
 */

public class DistrictsForTxd implements IPickerViewData{
    /**
     * districtName : 东城区
     * district_id : 500
     */

    private String districtName;
    private String district_id;

    public DistrictsForTxd(String districtName, String district_id) {
        this.districtName = districtName;
        this.district_id = district_id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    @Override
    public String getPickerViewText() {
        return getDistrictName();
    }
}
