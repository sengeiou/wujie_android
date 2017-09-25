package com.txd.hzj.wjlp.bean.addres;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/25 0025
 * 时间：10:23
 * 描述：
 * ===============Txunda===============
 */

public class ProvinceForTxd implements IPickerViewData{

    /**
     * cities : [{"city_id":"52","districts":[{"districtName":"东城区","district_id":"500"},{"districtName":"西城区",
     * "district_id":"501"},{"districtName":"海淀区","district_id":"502"},{"districtName":"朝阳区","district_id":"503"},
     * {"districtName":"崇文区","district_id":"504"},{"districtName":"宣武区","district_id":"505"},{"districtName":"丰台区",
     * "district_id":"506"},{"districtName":"石景山区","district_id":"507"},{"districtName":"房山区","district_id":"508"},
     * {"districtName":"门头沟区","district_id":"509"},{"districtName":"通州区","district_id":"510"},{"districtName":"顺义区",
     * "district_id":"511"},{"districtName":"昌平区","district_id":"512"},{"districtName":"怀柔区","district_id":"513"},
     * {"districtName":"平谷区","district_id":"514"},{"districtName":"大兴区","district_id":"515"},{"districtName":"密云县",
     * "district_id":"516"},{"districtName":"延庆县","district_id":"517"}],"cityName":"北京"}]
     * provinceName : 北京
     * province_id : 2
     */

    private String provinceName;
    private String province_id;
    private List<CityForTxd> cities;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public List<CityForTxd> getCities() {
        return cities;
    }

    public void setCities(List<CityForTxd> cities) {
        this.cities = cities;
    }

    @Override
    public String getPickerViewText() {
        return getProvinceName();
    }
}
