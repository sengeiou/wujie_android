package com.txd.hzj.wjlp.bean;

import com.txd.hzj.wjlp.bean.commodity.DataBaseBean;
import com.txd.hzj.wjlp.minetoAty.order.OnlineChongDetailsAty;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/27 17:47
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class HjsInfoBean extends DataBaseBean {
    private List<HjsInfoDataBean> data;

    public List<HjsInfoDataBean> getData() {
        return data;
    }

    public void setData(List<HjsInfoDataBean> data) {
        this.data = data;
    }
}
