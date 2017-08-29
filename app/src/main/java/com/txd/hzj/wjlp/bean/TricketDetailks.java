package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：15:53
 * 描述：购物券使用详情
 * ===============Txunda===============
 */

public class TricketDetailks {

    /**
     * 吸顶标题
     */
    public String sticky;
    /**
     * 条目标题
     */
    public String name;
    /**
     * 交易记录时间
     */
    public String gender;
    /**
     * 最后的交易数量
     */
    public String profession;

    /**
     * 原因
     */
    public String reason;

    /**
     * 记录id
     */
    public String log_id;

    /**
     * 操作类型
     */
    public String act_type;

    public TricketDetailks(String sticky, String name, String gender, String profession, String reason, String
            log_id, String act_type) {
        this.sticky = sticky;
        this.name = name;
        this.gender = gender;
        this.profession = profession;
        this.reason = reason;
        this.log_id = log_id;
        this.act_type = act_type;
    }

    public String getSticky() {
        return sticky;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getAct_type() {
        return act_type;
    }

    public void setAct_type(String act_type) {
        this.act_type = act_type;
    }
}
