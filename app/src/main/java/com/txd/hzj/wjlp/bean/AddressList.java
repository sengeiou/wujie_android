package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：15:01
 * 描述：收货地址列表
 * ===============Txunda===============
 */

public class AddressList {

    /**
     * code : 1
     * message : 请求成功
     * data : {"default_address":{"address_id":"地址id","receiver":"收货人","phone":"收货人电话","province":"省","city":"市",
     * "area":"区","street":"街道","address":"详细地址","lng":"经度","lat":"纬度"},"common_address":[{"address_id":"地址id",
     * "receiver":"收货人","phone":"收货人电话","province":"省","city":"市","area":"区","street":"街道","address":"详细地址",
     * "lng":"经度","lat":"纬度"}]}
     * nums : 个数
     */

    private String code;
    private String message;
    private Data data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public static class Data {
        /**
         * default_address : {"address_id":"地址id","receiver":"收货人","phone":"收货人电话","province":"省","city":"市",
         * "area":"区","street":"街道","address":"详细地址","lng":"经度","lat":"纬度"}
         * common_address : [{"address_id":"地址id","receiver":"收货人","phone":"收货人电话","province":"省","city":"市",
         * "area":"区","street":"街道","address":"详细地址","lng":"经度","lat":"纬度"}]
         */

        private DefaultAddress default_address;
        private List<CommonAddress> common_address;

        public DefaultAddress getDefault_address() {
            return default_address;
        }

        public void setDefault_address(DefaultAddress default_address) {
            this.default_address = default_address;
        }

        public List<CommonAddress> getCommon_address() {
            return common_address;
        }

        public void setCommon_address(List<CommonAddress> common_address) {
            this.common_address = common_address;
        }

        public static class DefaultAddress {
            /**
             * address_id : 地址id
             * receiver : 收货人
             * phone : 收货人电话
             * province : 省
             * city : 市
             * area : 区
             * street : 街道
             * address : 详细地址
             * lng : 经度
             * lat : 纬度
             */

            private String address_id;
            private String receiver;
            private String phone;
            private String province;
            private String city;
            private String area;
            private String street;
            private String address;
            private String lng;
            private String lat;

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            @Override
            public String toString() {
                return "DefaultAddress{" +
                        "address_id='" + address_id + '\'' +
                        ", receiver='" + receiver + '\'' +
                        ", phone='" + phone + '\'' +
                        ", province='" + province + '\'' +
                        ", city='" + city + '\'' +
                        ", area='" + area + '\'' +
                        ", street='" + street + '\'' +
                        ", address='" + address + '\'' +
                        ", lng='" + lng + '\'' +
                        ", lat='" + lat + '\'' +
                        '}';
            }
        }

        public static class CommonAddress {
            /**
             * address_id : 地址id
             * receiver : 收货人
             * phone : 收货人电话
             * province : 省
             * city : 市
             * area : 区
             * street : 街道
             * address : 详细地址
             * lng : 经度
             * lat : 纬度
             */

            private String address_id;
            private String receiver;
            private String phone;
            private String province;
            private String city;
            private String area;
            private String street;
            private String address;
            private String lng;
            private String lat;

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            @Override
            public String toString() {
                return "CommonAddress{" +
                        "address_id='" + address_id + '\'' +
                        ", receiver='" + receiver + '\'' +
                        ", phone='" + phone + '\'' +
                        ", province='" + province + '\'' +
                        ", city='" + city + '\'' +
                        ", area='" + area + '\'' +
                        ", street='" + street + '\'' +
                        ", address='" + address + '\'' +
                        ", lng='" + lng + '\'' +
                        ", lat='" + lat + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "AddressList{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
