package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/12 16:07
 * 功能描述： http://wjapi.wujiemall.com/index.php/Function/index/p_id/71/mo_id/1035/f_id/6283
 * 根据快递单号获取物流公司名称
 * 联系方式：常用邮箱或电话
 */
public class CompanyBean implements Serializable {
    private String invoice;
    private String cname;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
