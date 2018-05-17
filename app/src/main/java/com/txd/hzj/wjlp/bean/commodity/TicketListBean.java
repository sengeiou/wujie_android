package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:58
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class TicketListBean implements Serializable {
    /**
     * ticket_id : 优惠券ID
     * ticket_name : 优惠券名称
     * ticket_desc : 优惠券详情
     * ticket_type : 优惠券类型
     * value : 面额
     * condition : 满足条件
     * start_time : 开始时间
     * end_time : 结束时间
     */
    private String ticket_id;// "优惠券ID",
    private String ticket_name;// "优惠券名称",
    private String ticket_desc;// "优惠券详情",
    private String ticket_type;// "优惠券类型",//1 满减 2满折 3满赠
    private String value;//"面额", //满减=>金额 满折=>折扣 满赠=>商品id
    private String value_replace;//优惠券描述
    private String condition;//  "满足条件",
    private String start_time;//"开始时间",
    private String end_time;//"结束时间"
    private String get_receive;//

    public String getGet_receive() {
        return get_receive;
    }

    public void setGet_receive(String get_receive) {
        this.get_receive = get_receive;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_name() {
        return ticket_name;
    }

    public void setTicket_name(String ticket_name) {
        this.ticket_name = ticket_name;
    }

    public String getTicket_desc() {
        return ticket_desc;
    }

    public void setTicket_desc(String ticket_desc) {
        this.ticket_desc = ticket_desc;
    }

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getValue_replace() {
        return value_replace;
    }

    public void setValue_replace(String value_replace) {
        this.value_replace = value_replace;
    }
}
