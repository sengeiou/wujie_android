package com.txd.hzj.wjlp.bean.groupbuy;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:28
 * 描述：优惠券
 * ===============Txunda===============
 */

public class TicketListBean {
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

    private String ticket_id;
    private String ticket_name;
    private String ticket_desc;
    private String ticket_type;
    private String value;
    private String condition;
    private String start_time;
    private String end_time;
    private String get_receive;

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

    @Override
    public String toString() {
        return "TicketListBean{" +
                "ticket_id='" + ticket_id + '\'' +
                ", ticket_name='" + ticket_name + '\'' +
                ", ticket_desc='" + ticket_desc + '\'' +
                ", ticket_type='" + ticket_type + '\'' +
                ", value='" + value + '\'' +
                ", condition='" + condition + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }
}
