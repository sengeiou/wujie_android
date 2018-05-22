package com.txd.hzj.wjlp.bean.commodity;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/21 17:18
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class LimitGoodsInfo extends GoodsInfoBean {

    private String exchange_num;
    private String limit_price;//限量购价格
    private String stage_status;
    private String start_time;
    private String end_time;
    private String pre_price;
    private String deposit;//定金
    private String success_max_num;
    private String integral_price;
    private String  desc;
    private String limit_store;
    private String end_delivery_date;

    public String getEnd_delivery_date() {
        return end_delivery_date;
    }

    public void setEnd_delivery_date(String end_delivery_date) {
        this.end_delivery_date = end_delivery_date;
    }

    public String getLimit_store() {
        return limit_store;
    }

    public void setLimit_store(String limit_store) {
        this.limit_store = limit_store;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIntegral_price() {
        return integral_price;
    }

    public void setIntegral_price(String integral_price) {
        this.integral_price = integral_price;
    }

    public String getLimit_price() {
        return limit_price;
    }

    public void setLimit_price(String limit_price) {
        this.limit_price = limit_price;
    }

    public String getSuccess_max_num() {
        return success_max_num;
    }

    public void setSuccess_max_num(String success_max_num) {
        this.success_max_num = success_max_num;
    }

    public String getExchange_num() {
        return exchange_num;
    }

    public void setExchange_num(String exchange_num) {
        this.exchange_num = exchange_num;
    }

    public String getStage_status() {
        return stage_status;
    }

    public void setStage_status(String stage_status) {
        this.stage_status = stage_status;
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

    public String getPre_price() {
        return pre_price;
    }

    public void setPre_price(String pre_price) {
        this.pre_price = pre_price;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
