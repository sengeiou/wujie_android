package com.txd.hzj.wjlp.bean.orderdetail;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 18:53
 * 功能描述： http://wjapi.wujiemall.com/index.php/Function/index/p_id/71/mo_id/1015/f_id/5595
 * 联系方式：常用邮箱或电话
 */
public class DetailsDataBean implements Serializable {
    private String leave_message;// 留言
    private String user_name;// 用户名
    private String phone; // 手机号
    private String address; // 地址
    private String merchant_name;// 店铺名
    private String merchant_id;// 店铺id
    private String order_price;//总金额
    private String order_sn;// 订单号
    private String logistics;// 物流状态
    private String logistics_time;//物流更新时间
    private String create_time;// 创建时间
    private String pay_time;  // 支付时间
    private String order_status;  //订单状态（0待支付 1待发货  2待收货3 待评价4 已完成 5已取消
    private String freight;// 运费
    private String express_company;// 快递公司名
    private String express_no;// 快递单号
    private String is_pay_password;// 是否设置支付密码  0否  1是
    private String o_return_integral;//返回积分数
    private List<DetailListBean> list;

    public String getLeave_message() {
        return leave_message;
    }

    public void setLeave_message(String leave_message) {
        this.leave_message = leave_message;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getLogistics_time() {
        return logistics_time;
    }

    public void setLogistics_time(String logistics_time) {
        this.logistics_time = logistics_time;
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

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getIs_pay_password() {
        return is_pay_password;
    }

    public void setIs_pay_password(String is_pay_password) {
        this.is_pay_password = is_pay_password;
    }

    public String getO_return_integral() {
        return o_return_integral;
    }

    public void setO_return_integral(String o_return_integral) {
        this.o_return_integral = o_return_integral;
    }

    public List<DetailListBean> getList() {
        return list;
    }

    public void setList(List<DetailListBean> list) {
        this.list = list;
    }
}
