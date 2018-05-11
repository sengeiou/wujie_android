package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:54
 * 功能描述：属性值
 * 联系方式：常用邮箱或电话
 */
public class FirstListValBean implements Serializable{
    private String val;//属性值
    private String status;//属性状态  SELECTED = "1", UNSELECT = "2", CANNOT_SELECT = "3";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
