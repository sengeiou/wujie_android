package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/28 18:35
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class InvoiceDataBean extends DataBaseBean implements Serializable{
    private Invoice data;

    public Invoice getData() {
        return data;
    }

    public void setData(Invoice data) {
        this.data = data;
    }
}
