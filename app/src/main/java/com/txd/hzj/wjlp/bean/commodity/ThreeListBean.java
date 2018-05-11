package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**

 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/11
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class ThreeListBean implements Serializable {
    private String code;
    private String message;
    private String nums;
    private ThreeListDataBean data;

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

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public ThreeListDataBean getData() {
        return data;
    }

    public void setData(ThreeListDataBean data) {
        this.data = data;
    }
}
