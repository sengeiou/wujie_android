package com.txd.hzj.wjlp.bean.orderdetail;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 18:50
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class DetailsBean implements Serializable {
    private String code;
    private String message;
    private DetailsDataBean data;
    private String nums;

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

    public DetailsDataBean getData() {
        return data;
    }

    public void setData(DetailsDataBean data) {
        this.data = data;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }
}
