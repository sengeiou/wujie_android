package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：16:41
 * 描述：区域选择
 * ===============Txunda===============
 */

public class Area {

    /**
     * code : 1
     * message : 获取成功
     * data : {"province_list":[{"region_id":"省id","region_name":"省名称"}],"city_list":[{"region_id":"52",
     * "region_name":"北京市"}],"area_list":[{"region_id":"区县id","region_name":"区县名称"}]}
     * nums : 0
     */

    private String code;
    private String message;
    private DataBean data;
    private int nums;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public static class DataBean {
        private List<ProvinceList> province_list;
        private List<CityList> city_list;
        private List<AreaList> area_list;

        public List<ProvinceList> getProvince_list() {
            return province_list;
        }

        public void setProvince_list(List<ProvinceList> province_list) {
            this.province_list = province_list;
        }

        public List<CityList> getCity_list() {
            return city_list;
        }

        public void setCity_list(List<CityList> city_list) {
            this.city_list = city_list;
        }

        public List<AreaList> getArea_list() {
            return area_list;
        }

        public void setArea_list(List<AreaList> area_list) {
            this.area_list = area_list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "province_list=" + province_list +
                    ", city_list=" + city_list +
                    ", area_list=" + area_list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Area{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
