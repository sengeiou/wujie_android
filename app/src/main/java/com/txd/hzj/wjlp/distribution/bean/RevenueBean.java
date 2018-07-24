package com.txd.hzj.wjlp.distribution.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/24 9:47
 * 功能描述：
 */
public class RevenueBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"normal":{"count":164.4,"data":{"2012":"0","2013":"0","2014":"0","2015":"0","2016":"0","2017":"0","2018":"164.40"}},"dis":{"count":99,"data":{"2012":"0","2013":"0","2014":"0","2015":"0","2016":"0","2017":"0","2018":"99.00"}},"ordinate":164.4}
     * nums : 0
     */

    private int code;
    private String message;
    private DataBeanXX data;
    private String nums;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBeanXX getData() {
        return data;
    }

    public void setData(DataBeanXX data) {
        this.data = data;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public static class DataBeanXX {
        /**
         * normal : {"count":164.4,"data":{"2012":"0","2013":"0","2014":"0","2015":"0","2016":"0","2017":"0","2018":"164.40"}}
         * dis : {"count":99,"data":{"2012":"0","2013":"0","2014":"0","2015":"0","2016":"0","2017":"0","2018":"99.00"}}
         * ordinate : 164.4
         */

        private NormalBean normal;
        private DisBean dis;
        private double ordinate;

        public NormalBean getNormal() {
            return normal;
        }

        public void setNormal(NormalBean normal) {
            this.normal = normal;
        }

        public DisBean getDis() {
            return dis;
        }

        public void setDis(DisBean dis) {
            this.dis = dis;
        }

        public double getOrdinate() {
            return ordinate;
        }

        public void setOrdinate(double ordinate) {
            this.ordinate = ordinate;
        }

        public static class NormalBean {
            /**
             * count : 164.4
             * data : {"2012":"0","2013":"0","2014":"0","2015":"0","2016":"0","2017":"0","2018":"164.40"}
             */

            private double count;
            private DataBean data;

            public double getCount() {
                return count;
            }

            public void setCount(double count) {
                this.count = count;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * 2012 : 0
                 * 2013 : 0
                 * 2014 : 0
                 * 2015 : 0
                 * 2016 : 0
                 * 2017 : 0
                 * 2018 : 164.40
                 */

                @SerializedName("2012")
                private String _$2012;
                @SerializedName("2013")
                private String _$2013;
                @SerializedName("2014")
                private String _$2014;
                @SerializedName("2015")
                private String _$2015;
                @SerializedName("2016")
                private String _$2016;
                @SerializedName("2017")
                private String _$2017;
                @SerializedName("2018")
                private String _$2018;

                public String get_$2012() {
                    return _$2012;
                }

                public void set_$2012(String _$2012) {
                    this._$2012 = _$2012;
                }

                public String get_$2013() {
                    return _$2013;
                }

                public void set_$2013(String _$2013) {
                    this._$2013 = _$2013;
                }

                public String get_$2014() {
                    return _$2014;
                }

                public void set_$2014(String _$2014) {
                    this._$2014 = _$2014;
                }

                public String get_$2015() {
                    return _$2015;
                }

                public void set_$2015(String _$2015) {
                    this._$2015 = _$2015;
                }

                public String get_$2016() {
                    return _$2016;
                }

                public void set_$2016(String _$2016) {
                    this._$2016 = _$2016;
                }

                public String get_$2017() {
                    return _$2017;
                }

                public void set_$2017(String _$2017) {
                    this._$2017 = _$2017;
                }

                public String get_$2018() {
                    return _$2018;
                }

                public void set_$2018(String _$2018) {
                    this._$2018 = _$2018;
                }
            }
        }

        public static class DisBean {
            /**
             * count : 99
             * data : {"2012":"0","2013":"0","2014":"0","2015":"0","2016":"0","2017":"0","2018":"99.00"}
             */

            private int count;
            private DataBeanX data;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public DataBeanX getData() {
                return data;
            }

            public void setData(DataBeanX data) {
                this.data = data;
            }

            public static class DataBeanX {
                /**
                 * 2012 : 0
                 * 2013 : 0
                 * 2014 : 0
                 * 2015 : 0
                 * 2016 : 0
                 * 2017 : 0
                 * 2018 : 99.00
                 */

                @SerializedName("2012")
                private String _$2012;
                @SerializedName("2013")
                private String _$2013;
                @SerializedName("2014")
                private String _$2014;
                @SerializedName("2015")
                private String _$2015;
                @SerializedName("2016")
                private String _$2016;
                @SerializedName("2017")
                private String _$2017;
                @SerializedName("2018")
                private String _$2018;

                public String get_$2012() {
                    return _$2012;
                }

                public void set_$2012(String _$2012) {
                    this._$2012 = _$2012;
                }

                public String get_$2013() {
                    return _$2013;
                }

                public void set_$2013(String _$2013) {
                    this._$2013 = _$2013;
                }

                public String get_$2014() {
                    return _$2014;
                }

                public void set_$2014(String _$2014) {
                    this._$2014 = _$2014;
                }

                public String get_$2015() {
                    return _$2015;
                }

                public void set_$2015(String _$2015) {
                    this._$2015 = _$2015;
                }

                public String get_$2016() {
                    return _$2016;
                }

                public void set_$2016(String _$2016) {
                    this._$2016 = _$2016;
                }

                public String get_$2017() {
                    return _$2017;
                }

                public void set_$2017(String _$2017) {
                    this._$2017 = _$2017;
                }

                public String get_$2018() {
                    return _$2018;
                }

                public void set_$2018(String _$2018) {
                    this._$2018 = _$2018;
                }
            }
        }
    }
}
