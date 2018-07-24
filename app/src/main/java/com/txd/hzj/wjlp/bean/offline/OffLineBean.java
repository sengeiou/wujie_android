package com.txd.hzj.wjlp.bean.offline;

import com.txd.hzj.wjlp.bean.commodity.DataBaseBean;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 14:42
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class OffLineBean extends DataBaseBean {
    private List<OffLineDataBean> data;

    public List<OffLineDataBean> getData() {
        return data;
    }

    public void setData(List<OffLineDataBean> data) {
        this.data = data;
    }
}
