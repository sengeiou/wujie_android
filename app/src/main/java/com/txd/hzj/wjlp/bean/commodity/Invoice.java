package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/28 18:48
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class Invoice implements Serializable {
    List<InvoiceBean> list;
    private String explain; ////纳税识别号显示内容

    public List<InvoiceBean> getList() {
        return list;
    }

    public void setList(List<InvoiceBean> list) {
        this.list = list;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
