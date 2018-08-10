package com.txd.hzj.wjlp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/7/24 0024 14:58
 * 功能描述：
 * 联系方式：无
 */
public class ShopOffLineBean implements Serializable {

    /**
     * code : 1
     * message : 请求成功
     * data : {"s_id":"38","merchant_name":"达令商城","merchant_desc":"618爆款手机带回家，心动不如行动！！门店地址：红桥区兴县勉县鞍山西道与白堤路交口风湖里15号楼人！","logo":"http://test2.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg","score":"5.0","gallery":[{"path":"http://test2.wujiemall.com/Uploads/Manager/2018-07-24/5b56ca32616e8.jpg"},{"path":"http://test2.wujiemall.com/Uploads/Manager/2018-07-24/5b56ca328f165.jpg"}],"open_time":"09:00-20:00","merchant_phone":"15620732854","other_license":[{"license_pic":"http://test2.wujiemall.com/Uploads/Merchant/2018-07-18/5b4f04b16f6d0.jpg","license_name":"测试"}],"province_name":"天津市","city_name":"天津市","area_name":"西青区","street_name":"西营门街道","address":"精武镇学畔馨园","distance":"11.5","final_address":"天津市天津市西青区西营门街道精武镇学畔馨园","focus_num":"1","goods_num":"0","months_orders":"3","comment":{"list":[{"c_id":"2","nickname":"无界新人1508","start_time":"2018-07-23 15:50:49","star":"4","head_pic":"http://test2.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif","star_array":["1","1","1","1","0"]},{"c_id":"1","nickname":"无界新人1508","start_time":"2018-07-23 15:50:49","star":"1","head_pic":"http://test2.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif","star_array":["1","0","0","0","0"]}],"count":"2","star_cate":"2.5"},"is_collect":0}
     * nums : 0
     */

    private String code;
    private String message;
    private DataBean data;
    private String nums;

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

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public static class DataBean {
        /**
         * s_id : 38
         * user_id:0
         * merchant_name : 达令商城
         * merchant_desc : 618爆款手机带回家，心动不如行动！！门店地址：红桥区兴县勉县鞍山西道与白堤路交口风湖里15号楼人！
         * logo : http://test2.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg
         * score : 5.0
         * gallery : [{"path":"http://test2.wujiemall.com/Uploads/Manager/2018-07-24/5b56ca32616e8.jpg"},{"path":"http://test2.wujiemall.com/Uploads/Manager/2018-07-24/5b56ca328f165.jpg"}]
         * open_time : 09:00-20:00
         * merchant_phone : 15620732854
         * other_license : [{"license_pic":"http://test2.wujiemall.com/Uploads/Merchant/2018-07-18/5b4f04b16f6d0.jpg","license_name":"测试"}]
         * province_name : 天津市
         * city_name : 天津市
         * area_name : 西青区
         * street_name : 西营门街道
         * address : 精武镇学畔馨园
         * distance : 11.5
         * final_address : 天津市天津市西青区西营门街道精武镇学畔馨园
         * focus_num : 1
         * goods_num : 0
         * months_orders : 3
         * comment : {"list":[{"c_id":"2","nickname":"无界新人1508","start_time":"2018-07-23 15:50:49","star":"4","head_pic":"http://test2.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif","star_array":["1","1","1","1","0"]},{"c_id":"1","nickname":"无界新人1508","start_time":"2018-07-23 15:50:49","star":"1","head_pic":"http://test2.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif","star_array":["1","0","0","0","0"]}],"count":"2","star_cate":"2.5"}
         * is_collect : 0
         */

        private String s_id;
        private String user_id;
        private String merchant_name;
        private String merchant_desc;
        private String logo;
        private String score;
        private String open_time;
        private String merchant_phone;
        private String province_name;
        private String city_name;
        private String area_name;
        private String street_name;
        private String address;
        private String distance;
        private String final_address;
        private String focus_num;
        private String goods_num;
        private String months_orders;
        private CommentBean comment;
        private int is_collect;
        private List<GalleryBean> gallery;
        private List<OtherLicenseBean> other_license;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getMerchant_desc() {
            return merchant_desc;
        }

        public void setMerchant_desc(String merchant_desc) {
            this.merchant_desc = merchant_desc;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getOpen_time() {
            return open_time;
        }

        public void setOpen_time(String open_time) {
            this.open_time = open_time;
        }

        public String getMerchant_phone() {
            return merchant_phone;
        }

        public void setMerchant_phone(String merchant_phone) {
            this.merchant_phone = merchant_phone;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getStreet_name() {
            return street_name;
        }

        public void setStreet_name(String street_name) {
            this.street_name = street_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getFinal_address() {
            return final_address;
        }

        public void setFinal_address(String final_address) {
            this.final_address = final_address;
        }

        public String getFocus_num() {
            return focus_num;
        }

        public void setFocus_num(String focus_num) {
            this.focus_num = focus_num;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getMonths_orders() {
            return months_orders;
        }

        public void setMonths_orders(String months_orders) {
            this.months_orders = months_orders;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public List<GalleryBean> getGallery() {
            return gallery;
        }

        public void setGallery(List<GalleryBean> gallery) {
            this.gallery = gallery;
        }

        public List<OtherLicenseBean> getOther_license() {
            return other_license;
        }

        public void setOther_license(List<OtherLicenseBean> other_license) {
            this.other_license = other_license;
        }

        public static class CommentBean {
            /**
             * list : [{"c_id":"2","nickname":"无界新人1508","start_time":"2018-07-23 15:50:49","star":"4","head_pic":"http://test2.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif","star_array":["1","1","1","1","0"]},{"c_id":"1","nickname":"无界新人1508","start_time":"2018-07-23 15:50:49","star":"1","head_pic":"http://test2.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif","star_array":["1","0","0","0","0"]}]
             * count : 2
             * star_cate : 2.5
             */

            private String count;
            private String star_cate;
            private List<ListBean> list;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getStar_cate() {
                return star_cate;
            }

            public void setStar_cate(String star_cate) {
                this.star_cate = star_cate;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * c_id : 2
                 * nickname : 无界新人1508
                 * start_time : 2018-07-23 15:50:49
                 * star : 4
                 * head_pic : http://test2.wujiemall.com/Uploads/User/2018-07-16/5b4c4836e3008.gif
                 * star_array : ["1","1","1","1","0"]
                 */

                private String c_id;
                private String nickname;
                private String start_time;
                private String star;
                private String head_pic;
                private List<String> star_array;

                public String getC_id() {
                    return c_id;
                }

                public void setC_id(String c_id) {
                    this.c_id = c_id;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getStart_time() {
                    return start_time;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public String getStar() {
                    return star;
                }

                public void setStar(String star) {
                    this.star = star;
                }

                public String getHead_pic() {
                    return head_pic;
                }

                public void setHead_pic(String head_pic) {
                    this.head_pic = head_pic;
                }

                public List<String> getStar_array() {
                    return star_array;
                }

                public void setStar_array(List<String> star_array) {
                    this.star_array = star_array;
                }
            }
        }

        public static class GalleryBean {
            /**
             * path : http://test2.wujiemall.com/Uploads/Manager/2018-07-24/5b56ca32616e8.jpg
             */

            private String path;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }

        public static class OtherLicenseBean {
            /**
             * license_pic : http://test2.wujiemall.com/Uploads/Merchant/2018-07-18/5b4f04b16f6d0.jpg
             * license_name : 测试
             */

            private String license_pic;
            private String license_name;

            public String getLicense_pic() {
                return license_pic;
            }

            public void setLicense_pic(String license_pic) {
                this.license_pic = license_pic;
            }

            public String getLicense_name() {
                return license_name;
            }

            public void setLicense_name(String license_name) {
                this.license_name = license_name;
            }
        }
    }
}
