package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/28 18:36
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class InvoiceBean implements Serializable {

    private String invoice_id;            //发票id
    private String t_id;         //发票类型id
    private String invoice_type;     // 发票种类名
    private String express_fee;             // 发票运费
    private String tax;                      //税金比例
    private String tax_pay;             //支付税金金额

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(String express_fee) {
        this.express_fee = express_fee;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTax_pay() {
        return tax_pay;
    }

    public void setTax_pay(String tax_pay) {
        this.tax_pay = tax_pay;
    }
}
