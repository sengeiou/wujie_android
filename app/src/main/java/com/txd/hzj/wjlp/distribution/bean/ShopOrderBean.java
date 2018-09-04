package com.txd.hzj.wjlp.distribution.bean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/9/3 10:48
 * 功能描述：订单管理bean类
 */
public class ShopOrderBean {

    /**
     * code : 200
     * message : 操作成功
     * data : [{"supply_name":"达令商城","merchant_id":"4","user_id":"15","pay_status":"1","order_goods_id":"1036","goods_num":"3","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"1197.00","pay_time":"1535617774","pay_type":"4","order_status":"1","freight":"0.00","id":"5","shop_id":"65","order_id":"843","is_open":"1","is_special":"1","is_has_shop":"0","other":null,"shop_name":"王文强的小店","nickname":"王文强","head_pic":"0","member_coding":"3","path":null,"set_name":"初级","profit_num":"100","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1036","market_price":"399.00","order_id":"843","goods_name":"分销商品南霸丸","merchant_name":"达令商城","shop_price":"399.00","goods_num":"3","goods_attr":"销售规格:大","goods_img":"25908","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535617603","pic":"http://test.wujiemall.com/Uploads/GoodsAttr/2018-08-29/5b85f51e65b91.jpg","return_integral":"0.00"}],"all_num":3,"is_pay_password":0},{"supply_name":"达令商城","merchant_id":"4","user_id":"15","pay_status":"1","order_goods_id":"1036","goods_num":"3","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"1197.00","pay_time":"1535617774","pay_type":"4","order_status":"1","freight":"0.00","id":"5","shop_id":"65","order_id":"843","is_open":"1","is_special":"1","is_has_shop":"0","other":null,"shop_name":"王文强的小店","nickname":"王文强","head_pic":"0","member_coding":"3","path":null,"set_name":"初级","profit_num":"50","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1036","market_price":"399.00","order_id":"843","goods_name":"分销商品南霸丸","merchant_name":"达令商城","shop_price":"399.00","goods_num":"3","goods_attr":"销售规格:大","goods_img":"25908","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535617603","pic":"http://test.wujiemall.com/Uploads/GoodsAttr/2018-08-29/5b85f51e65b91.jpg","return_integral":"0.00"}],"all_num":3,"is_pay_password":0},{"supply_name":"达令食品专营店","merchant_id":"7","user_id":"26","pay_status":"1","order_goods_id":"1039","goods_num":"1","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"588.00","pay_time":"1535701560","pay_type":"4","order_status":"1","freight":"8.00","id":"7","shop_id":"63","order_id":"846","is_open":"0","is_special":"1","is_has_shop":"1","other":null,"shop_name":"段文的小店","nickname":"段文","head_pic":"23436","member_coding":"3","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"初级","profit_num":"0","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1039","market_price":"600.00","order_id":"846","goods_name":"五香回锅肉","merchant_name":"达令食品专营店","shop_price":"580.00","goods_num":"1","goods_attr":":两代会狗肉","goods_img":"24353","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535696946","pic":"http://test3.wujiemall.com/Uploads/GoodsAttr/2018-08-10/5b6d411bab4a1.jpg","return_integral":"0.00"}],"all_num":1,"is_pay_password":0},{"supply_name":"达令食品专营店","merchant_id":"7","user_id":"26","pay_status":"1","order_goods_id":"1039","goods_num":"1","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"588.00","pay_time":"1535701560","pay_type":"4","order_status":"1","freight":"8.00","id":"7","shop_id":"63","order_id":"846","is_open":"0","is_special":"1","is_has_shop":"1","other":null,"shop_name":"段文的小店","nickname":"段文","head_pic":"23436","member_coding":"3","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"初级","profit_num":"0","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1039","market_price":"600.00","order_id":"846","goods_name":"五香回锅肉","merchant_name":"达令食品专营店","shop_price":"580.00","goods_num":"1","goods_attr":":两代会狗肉","goods_img":"24353","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535696946","pic":"http://test3.wujiemall.com/Uploads/GoodsAttr/2018-08-10/5b6d411bab4a1.jpg","return_integral":"0.00"}],"all_num":1,"is_pay_password":0},{"supply_name":"达令食品专营店","merchant_id":"7","user_id":"26","pay_status":"0","order_goods_id":"1040","goods_num":"1","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"588.00","pay_time":"0","pay_type":"0","order_status":"9","freight":"8.00","id":"8","shop_id":"63","order_id":"847","is_open":"0","is_special":"1","is_has_shop":"1","other":null,"shop_name":"段文的小店","nickname":"段文","head_pic":"23436","member_coding":"3","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"初级","profit_num":null,"shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1040","market_price":"600.00","order_id":"847","goods_name":"五香回锅肉","merchant_name":"达令食品专营店","shop_price":"580.00","goods_num":"1","goods_attr":":两代会狗肉","goods_img":"24353","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535697331","pic":"http://test3.wujiemall.com/Uploads/GoodsAttr/2018-08-10/5b6d411bab4a1.jpg","return_integral":"0.00"}],"all_num":1,"is_pay_password":0},{"supply_name":"达令食品专营店","merchant_id":"7","user_id":"26","pay_status":"1","order_goods_id":"1041","goods_num":"2","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"1160.00","pay_time":"1535698094","pay_type":"4","order_status":"1","freight":"0.00","id":"9","shop_id":"63","order_id":"848","is_open":"0","is_special":"1","is_has_shop":"1","other":null,"shop_name":"段文的小店","nickname":"段文","head_pic":"23436","member_coding":"3","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"初级","profit_num":"0","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1041","market_price":"600.00","order_id":"848","goods_name":"五香回锅肉","merchant_name":"达令食品专营店","shop_price":"580.00","goods_num":"2","goods_attr":":两代会狗肉","goods_img":"24353","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535697375","pic":"http://test3.wujiemall.com/Uploads/GoodsAttr/2018-08-10/5b6d411bab4a1.jpg","return_integral":"0.00"}],"all_num":2,"is_pay_password":0},{"supply_name":"达令食品专营店","merchant_id":"7","user_id":"26","pay_status":"1","order_goods_id":"1041","goods_num":"2","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"1160.00","pay_time":"1535698094","pay_type":"4","order_status":"1","freight":"0.00","id":"9","shop_id":"63","order_id":"848","is_open":"0","is_special":"1","is_has_shop":"1","other":null,"shop_name":"段文的小店","nickname":"段文","head_pic":"23436","member_coding":"3","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"初级","profit_num":"0","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1041","market_price":"600.00","order_id":"848","goods_name":"五香回锅肉","merchant_name":"达令食品专营店","shop_price":"580.00","goods_num":"2","goods_attr":":两代会狗肉","goods_img":"24353","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535697375","pic":"http://test3.wujiemall.com/Uploads/GoodsAttr/2018-08-10/5b6d411bab4a1.jpg","return_integral":"0.00"}],"all_num":2,"is_pay_password":0},{"supply_name":"达令商城","merchant_id":"4","user_id":"26","pay_status":"1","order_goods_id":"1043","goods_num":"1","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"399.00","pay_time":"1535702302","pay_type":"4","order_status":"1","freight":"0.00","id":"10","shop_id":"63","order_id":"850","is_open":"0","is_special":"1","is_has_shop":"1","other":null,"shop_name":"段文的小店","nickname":"段文","head_pic":"23436","member_coding":"3","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"初级","profit_num":"0","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1043","market_price":"399.00","order_id":"850","goods_name":"第6个商品","merchant_name":"达令商城","shop_price":"399.00","goods_num":"1","goods_attr":"销售规格:大","goods_img":"24342","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535702263","pic":"http://www.wujiemall.com/Uploads/GoodsAttr/2018-08-10/5b6d3f06ca32d.png","return_integral":"0.00"}],"all_num":1,"is_pay_password":0},{"supply_name":"达令商城","merchant_id":"4","user_id":"26","pay_status":"1","order_goods_id":"1043","goods_num":"1","use_integral":"0","ticket_color":"0","pay_tickets":"0.00","order_price":"399.00","pay_time":"1535702302","pay_type":"4","order_status":"1","freight":"0.00","id":"10","shop_id":"63","order_id":"850","is_open":"0","is_special":"1","is_has_shop":"1","other":null,"shop_name":"段文的小店","nickname":"段文","head_pic":"23436","member_coding":"3","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"初级","profit_num":"0","shop_type":"链店","is_special_html":"专区","shop_pic":0,"shop_pic_path":"http://test2.wujiemall.com/Uploads/User/default.png","order_goods":[{"order_goods_id":"1043","market_price":"399.00","order_id":"850","goods_name":"第6个商品","merchant_name":"达令商城","shop_price":"399.00","goods_num":"1","goods_attr":"销售规格:大","goods_img":"24342","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535702263","pic":"http://www.wujiemall.com/Uploads/GoodsAttr/2018-08-10/5b6d3f06ca32d.png","return_integral":"0.00"}],"all_num":1,"is_pay_password":0}]
     * nums : 9
     */

    private int code;
    private String message;
    private String nums;
    private List<DataBean> data;

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
         * supply_name : 达令商城
         * merchant_id : 4
         * user_id : 15
         * pay_status : 1
         * order_goods_id : 1036
         * goods_num : 3
         * use_integral : 0
         * ticket_color : 0
         * pay_tickets : 0.00
         * order_price : 1197.00
         * pay_time : 1535617774
         * pay_type : 4
         * order_status : 1
         * freight : 0.00
         * id : 5
         * shop_id : 65
         * order_id : 843
         * is_open : 1
         * is_special : 1
         * is_has_shop : 0
         * other : null
         * shop_name : 王文强的小店
         * nickname : 王文强
         * head_pic : 0
         * member_coding : 3
         * path : null
         * set_name : 初级
         * profit_num : 100
         * buyer:无界新人
         * shop_type : 链店
         * is_special_html : 专区
         * shop_pic : 0
         * order_time:2018-08-29
         * shop_pic_path : http://test2.wujiemall.com/Uploads/User/default.png
         * order_goods : [{"order_goods_id":"1036","market_price":"399.00","order_id":"843","goods_name":"分销商品南霸丸","merchant_name":"达令商城","shop_price":"399.00","goods_num":"3","goods_attr":"销售规格:大","goods_img":"25908","red_return_integral":"0.00","yellow_return_integral":"0.00","create_time":"1535617603","pic":"http://test.wujiemall.com/Uploads/GoodsAttr/2018-08-29/5b85f51e65b91.jpg","return_integral":"0.00"}]
         * all_num : 3
         * is_pay_password : 0
         */

        private String supply_name;
        private String merchant_id;
        private String user_id;
        private String pay_status;
        private String order_goods_id;
        private String goods_num;
        private String use_integral;
        private String ticket_color;
        private String pay_tickets;
        private String order_price;
        private String pay_time;
        private String pay_type;
        private String order_status;
        private String freight;
        private String id;
        private String shop_id;
        private String order_id;
        private String is_open;
        private String is_special;
        private String is_has_shop;
        private Object other;
        private String shop_name;
        private String nickname;
        private String head_pic;
        private String member_coding;
        private Object path;
        private String set_name;
        private String profit_num;
        private String buyer;
        private String shop_type;
        private String is_special_html;
        private int shop_pic;
        private String order_time;

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        private String shop_pic_path;
        private int all_num;
        private int is_pay_password;
        private List<OrderGoodsBean> order_goods;

        public String getSupply_name() {
            return supply_name;
        }

        public void setSupply_name(String supply_name) {
            this.supply_name = supply_name;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getOrder_goods_id() {
            return order_goods_id;
        }

        public void setOrder_goods_id(String order_goods_id) {
            this.order_goods_id = order_goods_id;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getUse_integral() {
            return use_integral;
        }

        public void setUse_integral(String use_integral) {
            this.use_integral = use_integral;
        }

        public String getTicket_color() {
            return ticket_color;
        }

        public void setTicket_color(String ticket_color) {
            this.ticket_color = ticket_color;
        }

        public String getPay_tickets() {
            return pay_tickets;
        }

        public void setPay_tickets(String pay_tickets) {
            this.pay_tickets = pay_tickets;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getIs_open() {
            return is_open;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public String getIs_special() {
            return is_special;
        }

        public void setIs_special(String is_special) {
            this.is_special = is_special;
        }

        public String getIs_has_shop() {
            return is_has_shop;
        }

        public void setIs_has_shop(String is_has_shop) {
            this.is_has_shop = is_has_shop;
        }

        public Object getOther() {
            return other;
        }

        public void setOther(Object other) {
            this.other = other;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getMember_coding() {
            return member_coding;
        }

        public void setMember_coding(String member_coding) {
            this.member_coding = member_coding;
        }

        public Object getPath() {
            return path;
        }

        public void setPath(Object path) {
            this.path = path;
        }

        public String getSet_name() {
            return set_name;
        }

        public void setSet_name(String set_name) {
            this.set_name = set_name;
        }

        public String getProfit_num() {
            return profit_num;
        }

        public void setProfit_num(String profit_num) {
            this.profit_num = profit_num;
        }

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public String getIs_special_html() {
            return is_special_html;
        }

        public void setIs_special_html(String is_special_html) {
            this.is_special_html = is_special_html;
        }

        public int getShop_pic() {
            return shop_pic;
        }

        public void setShop_pic(int shop_pic) {
            this.shop_pic = shop_pic;
        }

        public String getShop_pic_path() {
            return shop_pic_path;
        }

        public void setShop_pic_path(String shop_pic_path) {
            this.shop_pic_path = shop_pic_path;
        }

        public int getAll_num() {
            return all_num;
        }

        public void setAll_num(int all_num) {
            this.all_num = all_num;
        }

        public int getIs_pay_password() {
            return is_pay_password;
        }

        public void setIs_pay_password(int is_pay_password) {
            this.is_pay_password = is_pay_password;
        }

        public List<OrderGoodsBean> getOrder_goods() {
            return order_goods;
        }

        public void setOrder_goods(List<OrderGoodsBean> order_goods) {
            this.order_goods = order_goods;
        }

        public static class OrderGoodsBean {
            /**
             * order_goods_id : 1036
             * market_price : 399.00
             * order_id : 843
             * goods_name : 分销商品南霸丸
             * merchant_name : 达令商城
             * shop_price : 399.00
             * goods_num : 3
             * goods_attr : 销售规格:大
             * goods_img : 25908
             * red_return_integral : 0.00
             * yellow_return_integral : 0.00
             * create_time : 1535617603
             * pic : http://test.wujiemall.com/Uploads/GoodsAttr/2018-08-29/5b85f51e65b91.jpg
             * return_integral : 0.00
             */

            private String order_goods_id;
            private String market_price;
            private String order_id;
            private String goods_name;
            private String merchant_name;
            private String shop_price;
            private String goods_num;
            private String goods_attr;
            private String goods_img;
            private String red_return_integral;
            private String yellow_return_integral;
            private String create_time;
            private String pic;
            private String return_integral;

            public String getOrder_goods_id() {
                return order_goods_id;
            }

            public void setOrder_goods_id(String order_goods_id) {
                this.order_goods_id = order_goods_id;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getMerchant_name() {
                return merchant_name;
            }

            public void setMerchant_name(String merchant_name) {
                this.merchant_name = merchant_name;
            }

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getRed_return_integral() {
                return red_return_integral;
            }

            public void setRed_return_integral(String red_return_integral) {
                this.red_return_integral = red_return_integral;
            }

            public String getYellow_return_integral() {
                return yellow_return_integral;
            }

            public void setYellow_return_integral(String yellow_return_integral) {
                this.yellow_return_integral = yellow_return_integral;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getReturn_integral() {
                return return_integral;
            }

            public void setReturn_integral(String return_integral) {
                this.return_integral = return_integral;
            }
        }
    }
}
