package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * by Txunda_LH on 2017/11/3.
 */

public class GoodLuckBean implements Serializable {
    private String code;//"1"请求数据成功
    private String message;//请求消息返回
    private DataBean data;//请求主体内容
    private String nums;//请求数组数量

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

}
