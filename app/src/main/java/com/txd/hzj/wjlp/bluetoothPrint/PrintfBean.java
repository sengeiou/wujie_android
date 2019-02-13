package com.txd.hzj.wjlp.bluetoothPrint;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：打印小票信息对象
 */
public class PrintfBean {

    /**
     * ginfo : [{"attr_name":"大份","goods_id":"1","goods_name":"大份麻辣鸡块(大份)","goods_num":"1","shop_price":"17.00","specs":null}]
     * merchant_id : 12
     * num : 3
     * order_sn : 154474896564829
     * q1 : [{"desc":"#3 无界优品"},{"desc":"B类店铺（wap）-带商品"}]
     * q2 : [{"desc":"期望送达时间：12-17 00:00"},{"desc":"下单时间：12-14 08:56"},{"desc":"订单号：154474896564829"}]
     * q3 : [{"desc":"配送费：9.00"},{"desc":"餐盒费：1.5"}]
     * receiver : 无****
     * receiver_address : 天津市天津市南开区测试
     * receiver_phone : 183****7224
     * total_price : 已付：27.50
     * url : http://test2.wujiemall.com/Wap/OsManager/os_to_detail/sta_mid/12/order_sn/154474896564829.html
     */

    private int receipt; // 打印几份【新增字段 20190126】
    private List<Q1Bean> q1; // 第一块区域列表数据-店铺信息
    private List<Q2Bean> q2; // 第二块区域列表数据-单号及时间等
    private List<GinfoBean> ginfo; // 菜品列表
    private List<Q3Bean> q3; // 第一块区域列表数据-其他费用以及优惠等
    private String total_price; // 总价格
    private String receiver_address; // 配送地址
    private String receiver; // 联系人
    private String receiver_phone; // 配送人电话
    private int num; // 单子编号
    private String merchant_id; // 店铺id
    private String order_sn; // 订单号
    private String url; // 点击跳转的Url
    private String qr_url; // 生成二维码字段

    public int getReceipt() {
        return receipt;
    }

    public void setReceipt(int receipt) {
        this.receipt = receipt;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<GinfoBean> getGinfo() {
        return ginfo;
    }

    public void setGinfo(List<GinfoBean> ginfo) {
        this.ginfo = ginfo;
    }

    public List<Q1Bean> getQ1() {
        return q1;
    }

    public void setQ1(List<Q1Bean> q1) {
        this.q1 = q1;
    }

    public List<Q2Bean> getQ2() {
        return q2;
    }

    public void setQ2(List<Q2Bean> q2) {
        this.q2 = q2;
    }

    public List<Q3Bean> getQ3() {
        return q3;
    }

    public void setQ3(List<Q3Bean> q3) {
        this.q3 = q3;
    }

    public String getQr_url() {
        return qr_url;
    }

    public void setQr_url(String qr_url) {
        this.qr_url = qr_url;
    }

    public static class GinfoBean {
        /**
         * attr_name : 大份
         * goods_id : 1
         * goods_name : 大份麻辣鸡块(大份)
         * goods_num : 1
         * shop_price : 17.00
         * specs : null
         */

        private String attr_name;
        private String goods_id;
        private String goods_name;
        private String goods_num;
        private String shop_price;
        private Object specs;

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public Object getSpecs() {
            return specs;
        }

        public void setSpecs(Object specs) {
            this.specs = specs;
        }
    }

    public static class Q1Bean {
        /**
         * desc : #3 无界优品
         */

        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class Q2Bean {
        /**
         * desc : 期望送达时间：12-17 00:00
         */

        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class Q3Bean {
        /**
         * desc : 配送费：9.00
         */

        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
