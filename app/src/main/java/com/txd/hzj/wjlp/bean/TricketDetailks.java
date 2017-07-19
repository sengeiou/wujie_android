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

    public TricketDetailks(String sticky, String name, String gender, String profession) {
        this.sticky = sticky;
        this.name = name;
        this.gender = gender;
        this.profession = profession;
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
}
