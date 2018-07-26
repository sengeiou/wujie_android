package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/1 0001
 * 时间：09:36
 * 描述：商品分类---分类页面
 *
 */

public class CateIndex {

    /**
     * code : 1
     * message : 获取成功
     * data : {"msg_tip":"1","top_cate":[{"cate_id":"分类id","short_name":"分类简称","name":"分类名称"}],
     * "two_cate":[{"cate_id":"分类id","short_name":"分类简称","name":"分类名称","hot_brand":"1,2,3,4","cate_img":"品牌图片",
     * "host_brand":[{"brand_id":"品牌id","brand_logo":"品牌logo","brand_name":"品牌名称"}],"three_cate":[{"cate_id":"分类id",
     * "short_name":"分类简称","name":"分类名称","hot_brand":"0","cate_img":"品牌图标"}]}]}
     * nums : 0
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
         * msg_tip : 1
         * top_cate : [{"cate_id":"分类id","short_name":"分类简称","name":"分类名称"}]
         * two_cate : [{"cate_id":"分类id","short_name":"分类简称","name":"分类名称","hot_brand":"1,2,3,4","cate_img":"品牌图片",
         * "host_brand":[{"brand_id":"品牌id","brand_logo":"品牌logo","brand_name":"品牌名称"}],
         * "three_cate":[{"cate_id":"分类id","short_name":"分类简称","name":"分类名称","hot_brand":"0","cate_img":"品牌图标"}]}]
         */

        private int msg_tip;
        private List<TopCateBean> top_cate;
        private List<TwoCateBean> two_cate;

        public int getMsg_tip() {
            return msg_tip;
        }

        public void setMsg_tip(int msg_tip) {
            this.msg_tip = msg_tip;
        }

        public List<TopCateBean> getTop_cate() {
            return top_cate;
        }

        public void setTop_cate(List<TopCateBean> top_cate) {
            this.top_cate = top_cate;
        }

        public List<TwoCateBean> getTwo_cate() {
            return two_cate;
        }

        public void setTwo_cate(List<TwoCateBean> two_cate) {
            this.two_cate = two_cate;
        }

        public static class TopCateBean {
            /**
             * cate_id : 分类id
             * short_name : 分类简称
             * name : 分类名称
             */

            private String cate_id;
            private String short_name;
            private String name;

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return "TopCateBean{" +
                        "cate_id='" + cate_id + '\'' +
                        ", short_name='" + short_name + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        public static class TwoCateBean {
            /**
             * cate_id : 分类id
             * short_name : 分类简称
             * name : 分类名称
             * hot_brand : 1,2,3,4
             * cate_img : 品牌图片
             * host_brand : [{"brand_id":"品牌id","brand_logo":"品牌logo","brand_name":"品牌名称"}]
             * three_cate : [{"cate_id":"分类id","short_name":"分类简称","name":"分类名称","hot_brand":"0","cate_img":"品牌图标"}]
             */

            private String cate_id;
            private String short_name;
            private String name;
            private String hot_brand;
            private String cate_img;
            private List<HostBrandBean> host_brand;
            private List<ThreeCateBean> three_cate;

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHot_brand() {
                return hot_brand;
            }

            public void setHot_brand(String hot_brand) {
                this.hot_brand = hot_brand;
            }

            public String getCate_img() {
                return cate_img;
            }

            public void setCate_img(String cate_img) {
                this.cate_img = cate_img;
            }

            public List<HostBrandBean> getHost_brand() {
                return host_brand;
            }

            public void setHost_brand(List<HostBrandBean> host_brand) {
                this.host_brand = host_brand;
            }

            public List<ThreeCateBean> getThree_cate() {
                return three_cate;
            }

            public void setThree_cate(List<ThreeCateBean> three_cate) {
                this.three_cate = three_cate;
            }

            public static class HostBrandBean {
                /**
                 * brand_id : 品牌id
                 * brand_logo : 品牌logo
                 * brand_name : 品牌名称
                 */

                private String brand_id;
                private String brand_logo;
                private String brand_name;

                public String getBrand_id() {
                    return brand_id;
                }

                public void setBrand_id(String brand_id) {
                    this.brand_id = brand_id;
                }

                public String getBrand_logo() {
                    return brand_logo;
                }

                public void setBrand_logo(String brand_logo) {
                    this.brand_logo = brand_logo;
                }

                public String getBrand_name() {
                    return brand_name;
                }

                public void setBrand_name(String brand_name) {
                    this.brand_name = brand_name;
                }

                @Override
                public String toString() {
                    return "HostBrandBean{" +
                            "brand_id='" + brand_id + '\'' +
                            ", brand_logo='" + brand_logo + '\'' +
                            ", brand_name='" + brand_name + '\'' +
                            '}';
                }
            }

            public static class ThreeCateBean {
                /**
                 * cate_id : 分类id
                 * short_name : 分类简称
                 * name : 分类名称
                 * hot_brand : 0
                 * cate_img : 品牌图标
                 */

                private String cate_id;
                private String short_name;
                private String name;
                private String hot_brand;
                private String cate_img;

                public String getCate_id() {
                    return cate_id;
                }

                public void setCate_id(String cate_id) {
                    this.cate_id = cate_id;
                }

                public String getShort_name() {
                    return short_name;
                }

                public void setShort_name(String short_name) {
                    this.short_name = short_name;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getHot_brand() {
                    return hot_brand;
                }

                public void setHot_brand(String hot_brand) {
                    this.hot_brand = hot_brand;
                }

                public String getCate_img() {
                    return cate_img;
                }

                public void setCate_img(String cate_img) {
                    this.cate_img = cate_img;
                }

                @Override
                public String toString() {
                    return "ThreeCateBean{" +
                            "cate_id='" + cate_id + '\'' +
                            ", short_name='" + short_name + '\'' +
                            ", name='" + name + '\'' +
                            ", hot_brand='" + hot_brand + '\'' +
                            ", cate_img='" + cate_img + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "TwoCateBean{" +
                        "cate_id='" + cate_id + '\'' +
                        ", short_name='" + short_name + '\'' +
                        ", name='" + name + '\'' +
                        ", hot_brand='" + hot_brand + '\'' +
                        ", cate_img='" + cate_img + '\'' +
                        ", host_brand=" + host_brand +
                        ", three_cate=" + three_cate +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "msg_tip=" + msg_tip +
                    ", top_cate=" + top_cate +
                    ", two_cate=" + two_cate +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CateIndex{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
