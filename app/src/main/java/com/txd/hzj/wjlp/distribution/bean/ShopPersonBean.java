package com.txd.hzj.wjlp.distribution.bean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/23 15:39
 * 功能描述：
 */
public class ShopPersonBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"consumer":[{"id":"5","order_sn":"152776522818014","merchant_name":"达令食品专营店","merchant_id":"7","user_id":"100","member_coding":"1","member_coding_html":"无界会员","order_type":"0","receiver":"刘泽仁","phone":"18602254712","address":"天津市天津市南开区学府街道白堤路186号","pay_type":"4","order_status":"3","settlement_status":"1","settlement_time":"","settlement_end_time":"1527765485","pay_status":"1","order_goods_id":"1","goods_num":"1","use_integral":"0","ticket_color":"3","pay_tickets":"4.98","order_price":"24.60","create_time":"1527765228","pay_time":"1527765237","deal_time":"2018-06-06 19:47:55","update_time":"1527765481","comment_status":"0","wx_sn":null,"return_integral":"0.00","import_tax":"0.00","freight":"8.00","express_fee":"0.00","tax_pay":"0.00","welfare":"0.00","leave_message":"","status":"0","shop_id":"1","order_id":"1","is_open":"0","is_special":"1","is_has_shop":"0","other":null,"nickname":"cold","head_pic":"0","path":null,"set_name":"","head_path":"http://test.wujiemall.com/Uploads/User/default.png"}],"shop":[{"id":"6","order_sn":"152776570576523","merchant_name":"达令食品专营店","merchant_id":"7","user_id":"26","member_coding":"3","member_coding_html":"优享会员","order_type":"0","receiver":"刘泽仁","phone":"18602254712","address":"天津市天津市南开区学府街道白堤路186号","pay_type":"4","order_status":"3","settlement_status":"1","settlement_time":"","settlement_end_time":"1527765902","pay_status":"1","order_goods_id":"2","goods_num":"4","use_integral":"0","ticket_color":"3","pay_tickets":"19.92","order_price":"74.40","create_time":"1527765705","pay_time":"1527765715","deal_time":"2018-05-31 19:21:55","update_time":"1527765877","comment_status":"0","wx_sn":null,"return_integral":"0.00","import_tax":"0.00","freight":"8.00","express_fee":"0.00","tax_pay":"0.00","welfare":"0.00","leave_message":"","status":"0","shop_id":"1","order_id":"2","is_open":"1","is_special":"1","is_has_shop":"1","other":null,"nickname":"段文","head_pic":"23436","path":"/Uploads/User/2018-06-01/5b10b070b764f.jpg","set_name":"中级","head_path":"http://test.wujiemall.com/Uploads/User/2018-06-01/5b10b070b764f.jpg"}]}
     * nums : 0
     */

    private int code;
    private String message;
    private DataBean data;
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
        private List<ConsumerBean> consumer;
        private List<ShopBean> shop;

        public List<ConsumerBean> getConsumer() {
            return consumer;
        }

        public void setConsumer(List<ConsumerBean> consumer) {
            this.consumer = consumer;
        }

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class ConsumerBean {
            /**
             * id : 5
             * order_sn : 152776522818014
             * merchant_name : 达令食品专营店
             * merchant_id : 7
             * user_id : 100
             * member_coding : 1
             * member_coding_html : 无界会员
             * order_type : 0
             * receiver : 刘泽仁
             * phone : 18602254712
             * address : 天津市天津市南开区学府街道白堤路186号
             * pay_type : 4
             * order_status : 3
             * settlement_status : 1
             * settlement_time :
             * settlement_end_time : 1527765485
             * pay_status : 1
             * order_goods_id : 1
             * goods_num : 1
             * use_integral : 0
             * ticket_color : 3
             * pay_tickets : 4.98
             * order_price : 24.60
             * create_time : 1527765228
             * pay_time : 1527765237
             * deal_time : 2018-06-06 19:47:55
             * update_time : 1527765481
             * comment_status : 0
             * wx_sn : null
             * return_integral : 0.00
             * import_tax : 0.00
             * freight : 8.00
             * express_fee : 0.00
             * tax_pay : 0.00
             * welfare : 0.00
             * leave_message :
             * status : 0
             * shop_id : 1
             * order_id : 1
             * is_open : 0
             * is_special : 1
             * is_has_shop : 0
             * other : null
             * nickname : cold
             * head_pic : 0
             * path : null
             * set_name :
             * "profit_num": "100",
             * head_path : http://test.wujiemall.com/Uploads/User/default.png
             */

            private String id;
            private String order_sn;
            private String merchant_name;
            private String merchant_id;
            private String user_id;
            private String member_coding;
            private String member_coding_html;
            private String order_type;
            private String receiver;
            private String phone;
            private String address;
            private String pay_type;
            private String order_status;
            private String settlement_status;
            private String settlement_time;
            private String settlement_end_time;
            private String pay_status;
            private String order_goods_id;
            private String goods_num;
            private String use_integral;
            private String ticket_color;
            private String pay_tickets;
            private String order_price;
            private String create_time;
            private String pay_time;
            private String deal_time;
            private String update_time;
            private String comment_status;
            private Object wx_sn;
            private String return_integral;
            private String import_tax;
            private String freight;
            private String express_fee;
            private String tax_pay;
            private String welfare;
            private String leave_message;
            private String status;
            private String shop_id;
            private String order_id;
            private String is_open;
            private String is_special;
            private String is_has_shop;
            private Object other;
            private String nickname;
            private String head_pic;
            private Object path;
            private String set_name;
            private String head_path;
            private String profit_num;

            public String getProfit_num() {
                return profit_num;
            }

            public void setProfit_num(String profit_num) {
                this.profit_num = profit_num;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getMerchant_name() {
                return merchant_name;
            }

            public void setMerchant_name(String merchant_name) {
                this.merchant_name = merchant_name;
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

            public String getMember_coding() {
                return member_coding;
            }

            public void setMember_coding(String member_coding) {
                this.member_coding = member_coding;
            }

            public String getMember_coding_html() {
                return member_coding_html;
            }

            public void setMember_coding_html(String member_coding_html) {
                this.member_coding_html = member_coding_html;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public String getSettlement_status() {
                return settlement_status;
            }

            public void setSettlement_status(String settlement_status) {
                this.settlement_status = settlement_status;
            }

            public String getSettlement_time() {
                return settlement_time;
            }

            public void setSettlement_time(String settlement_time) {
                this.settlement_time = settlement_time;
            }

            public String getSettlement_end_time() {
                return settlement_end_time;
            }

            public void setSettlement_end_time(String settlement_end_time) {
                this.settlement_end_time = settlement_end_time;
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getDeal_time() {
                return deal_time;
            }

            public void setDeal_time(String deal_time) {
                this.deal_time = deal_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getComment_status() {
                return comment_status;
            }

            public void setComment_status(String comment_status) {
                this.comment_status = comment_status;
            }

            public Object getWx_sn() {
                return wx_sn;
            }

            public void setWx_sn(Object wx_sn) {
                this.wx_sn = wx_sn;
            }

            public String getReturn_integral() {
                return return_integral;
            }

            public void setReturn_integral(String return_integral) {
                this.return_integral = return_integral;
            }

            public String getImport_tax() {
                return import_tax;
            }

            public void setImport_tax(String import_tax) {
                this.import_tax = import_tax;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getExpress_fee() {
                return express_fee;
            }

            public void setExpress_fee(String express_fee) {
                this.express_fee = express_fee;
            }

            public String getTax_pay() {
                return tax_pay;
            }

            public void setTax_pay(String tax_pay) {
                this.tax_pay = tax_pay;
            }

            public String getWelfare() {
                return welfare;
            }

            public void setWelfare(String welfare) {
                this.welfare = welfare;
            }

            public String getLeave_message() {
                return leave_message;
            }

            public void setLeave_message(String leave_message) {
                this.leave_message = leave_message;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getHead_path() {
                return head_path;
            }

            public void setHead_path(String head_path) {
                this.head_path = head_path;
            }
        }

        public static class ShopBean {
            /**
             * id : 6
             * order_sn : 152776570576523
             * merchant_name : 达令食品专营店
             * merchant_id : 7
             * user_id : 26
             * member_coding : 3
             * member_coding_html : 优享会员
             * order_type : 0
             * receiver : 刘泽仁
             * phone : 18602254712
             * address : 天津市天津市南开区学府街道白堤路186号
             * pay_type : 4
             * order_status : 3
             * settlement_status : 1
             * settlement_time :
             * settlement_end_time : 1527765902
             * pay_status : 1
             * order_goods_id : 2
             * goods_num : 4
             * use_integral : 0
             * ticket_color : 3
             * pay_tickets : 19.92
             * order_price : 74.40
             * create_time : 1527765705
             * pay_time : 1527765715
             * deal_time : 2018-05-31 19:21:55
             * update_time : 1527765877
             * comment_status : 0
             * wx_sn : null
             * return_integral : 0.00
             * import_tax : 0.00
             * freight : 8.00
             * express_fee : 0.00
             * tax_pay : 0.00
             * welfare : 0.00
             * leave_message :
             * status : 0
             * shop_id : 1
             * order_id : 2
             * is_open : 1
             * is_special : 1
             * is_has_shop : 1
             * other : null
             * nickname : 段文
             * head_pic : 23436
             * path : /Uploads/User/2018-06-01/5b10b070b764f.jpg
             * set_name : 中级
             * head_path : http://test.wujiemall.com/Uploads/User/2018-06-01/5b10b070b764f.jpg
             */

            private String id;
            private String order_sn;
            private String merchant_name;
            private String merchant_id;
            private String user_id;
            private String member_coding;
            private String member_coding_html;
            private String order_type;
            private String receiver;
            private String phone;
            private String address;
            private String pay_type;
            private String order_status;
            private String settlement_status;
            private String settlement_time;
            private String settlement_end_time;
            private String pay_status;
            private String order_goods_id;
            private String goods_num;
            private String use_integral;
            private String ticket_color;
            private String pay_tickets;
            private String order_price;
            private String create_time;
            private String pay_time;
            private String deal_time;
            private String update_time;
            private String comment_status;
            private Object wx_sn;
            private String return_integral;
            private String import_tax;
            private String freight;
            private String express_fee;
            private String tax_pay;
            private String welfare;
            private String leave_message;
            private String status;
            private String shop_id;
            private String order_id;
            private String is_open;
            private String is_special;
            private String is_has_shop;
            private Object other;
            private String nickname;
            private String head_pic;
            private String path;
            private String set_name;
            private String head_path;
            private String profit_num;

            public String getProfit_num() {
                return profit_num;
            }

            public void setProfit_num(String profit_num) {
                this.profit_num = profit_num;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getMerchant_name() {
                return merchant_name;
            }

            public void setMerchant_name(String merchant_name) {
                this.merchant_name = merchant_name;
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

            public String getMember_coding() {
                return member_coding;
            }

            public void setMember_coding(String member_coding) {
                this.member_coding = member_coding;
            }

            public String getMember_coding_html() {
                return member_coding_html;
            }

            public void setMember_coding_html(String member_coding_html) {
                this.member_coding_html = member_coding_html;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public String getSettlement_status() {
                return settlement_status;
            }

            public void setSettlement_status(String settlement_status) {
                this.settlement_status = settlement_status;
            }

            public String getSettlement_time() {
                return settlement_time;
            }

            public void setSettlement_time(String settlement_time) {
                this.settlement_time = settlement_time;
            }

            public String getSettlement_end_time() {
                return settlement_end_time;
            }

            public void setSettlement_end_time(String settlement_end_time) {
                this.settlement_end_time = settlement_end_time;
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getDeal_time() {
                return deal_time;
            }

            public void setDeal_time(String deal_time) {
                this.deal_time = deal_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getComment_status() {
                return comment_status;
            }

            public void setComment_status(String comment_status) {
                this.comment_status = comment_status;
            }

            public Object getWx_sn() {
                return wx_sn;
            }

            public void setWx_sn(Object wx_sn) {
                this.wx_sn = wx_sn;
            }

            public String getReturn_integral() {
                return return_integral;
            }

            public void setReturn_integral(String return_integral) {
                this.return_integral = return_integral;
            }

            public String getImport_tax() {
                return import_tax;
            }

            public void setImport_tax(String import_tax) {
                this.import_tax = import_tax;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getExpress_fee() {
                return express_fee;
            }

            public void setExpress_fee(String express_fee) {
                this.express_fee = express_fee;
            }

            public String getTax_pay() {
                return tax_pay;
            }

            public void setTax_pay(String tax_pay) {
                this.tax_pay = tax_pay;
            }

            public String getWelfare() {
                return welfare;
            }

            public void setWelfare(String welfare) {
                this.welfare = welfare;
            }

            public String getLeave_message() {
                return leave_message;
            }

            public void setLeave_message(String leave_message) {
                this.leave_message = leave_message;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getSet_name() {
                return set_name;
            }

            public void setSet_name(String set_name) {
                this.set_name = set_name;
            }

            public String getHead_path() {
                return head_path;
            }

            public void setHead_path(String head_path) {
                this.head_path = head_path;
            }
        }
    }
}
