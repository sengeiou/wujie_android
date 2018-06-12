package com.txd.hzj.wjlp.bean;

import com.txd.hzj.wjlp.bean.commodity.DataBaseBean;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/12 16:04
 * 功能描述：  http://wjapi.wujiemall.com/index.php/Function/index/p_id/71/mo_id/1035/f_id/6283
 * 根据快递单号获取物流公司名称
 * 联系方式：常用邮箱或电话
 */
public class CompayDataBean extends DataBaseBean {
    private List<CompanyBean> data;

    public List<CompanyBean> getData() {
        return data;
    }

    public void setData(List<CompanyBean> data) {
        this.data = data;
    }
}
