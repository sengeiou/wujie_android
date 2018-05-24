package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * wjj
 * 赠送代金券使用明细回传对象
 */

public class GifVoucherListDetailBean {

    /**
     * code : 1
     * message : 获取成功
     * data : [{"time":"2018-05","list":[{"b_v_id":"1","order_id":"785","order_sn":"152671682457124","user_id":"1508","create_time":"2018-05-19 15:00","voucher_price":"23.80","nickname":"无界新人1508","order_type":"0","img":"http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png","reason":"无界新人1508购买订单号152671682457124消费23.80元蓝色代金券。"},{"b_v_id":"2","order_id":"801","order_sn":"152671682457124","user_id":"1508","create_time":"2018-05-19 16:00","voucher_price":"47.60","nickname":"无界新人1508","order_type":"0","img":"http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png","reason":"无界新人1508购买订单号152671682457124消费47.60元蓝色代金券。"},{"b_v_id":"3","order_id":"807","order_sn":"152672226830267","user_id":"1508","create_time":"2018-05-19 17:31","voucher_price":"71.40","nickname":"无界新人1508","order_type":"0","img":"http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png","reason":"无界新人1508购买订单号152672226830267消费71.40元蓝色代金券。"}]}]
     * nums : 3
     */

    private String code; // 回传值
    private String message; // 回传消息
    private String nums; // 数量，保留字段
    private List<DataBean> data; // 回传数据List

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

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-05
         * list : [{"b_v_id":"1","order_id":"785","order_sn":"152671682457124","user_id":"1508","create_time":"2018-05-19 15:00","voucher_price":"23.80","nickname":"无界新人1508","order_type":"0","img":"http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png","reason":"无界新人1508购买订单号152671682457124消费23.80元蓝色代金券。"},{"b_v_id":"2","order_id":"801","order_sn":"152671682457124","user_id":"1508","create_time":"2018-05-19 16:00","voucher_price":"47.60","nickname":"无界新人1508","order_type":"0","img":"http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png","reason":"无界新人1508购买订单号152671682457124消费47.60元蓝色代金券。"},{"b_v_id":"3","order_id":"807","order_sn":"152672226830267","user_id":"1508","create_time":"2018-05-19 17:31","voucher_price":"71.40","nickname":"无界新人1508","order_type":"0","img":"http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png","reason":"无界新人1508购买订单号152672226830267消费71.40元蓝色代金券。"}]
         */

        private String time; // 时间
        private List<ListBean> list; // 时间下的详细数据

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * b_v_id : 1
             * order_id : 785
             * order_sn : 152671682457124
             * user_id : 1508
             * create_time : 2018-05-19 15:00
             * voucher_price : 23.80
             * nickname : 无界新人1508
             * order_type : 0
             * img : http://test.wujiemall.com/Uploads/LittleIcon/2018-04-04/5ac47c8120d9e.png
             * reason : 无界新人1508购买订单号152671682457124消费23.80元蓝色代金券。
             */

            private String b_v_id; // id
            private String order_id; // 订单id
            private String order_sn; // 订单编号
            private String user_id; // 用户id
            private String create_time; // 产生时间
            private String voucher_price; // 消费金额，全部是减
            private String nickname; // 昵称
            private String order_type; // 订单类型，暂时只有普通订单
            private String img; // 图标
            private String reason; // 描述

            public String getB_v_id() {
                return b_v_id;
            }

            public void setB_v_id(String b_v_id) {
                this.b_v_id = b_v_id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getVoucher_price() {
                return voucher_price;
            }

            public void setVoucher_price(String voucher_price) {
                this.voucher_price = voucher_price;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "b_v_id='" + b_v_id + '\'' +
                        ", order_id='" + order_id + '\'' +
                        ", order_sn='" + order_sn + '\'' +
                        ", user_id='" + user_id + '\'' +
                        ", create_time='" + create_time + '\'' +
                        ", voucher_price='" + voucher_price + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", order_type='" + order_type + '\'' +
                        ", img='" + img + '\'' +
                        ", reason='" + reason + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "time='" + time + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GifVoucherListDetailBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", nums='" + nums + '\'' +
                ", data=" + data +
                '}';
    }
}
