package com.txd.hzj.wjlp.bean.commodity;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 15:28
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GoodsMsgBean extends DataBaseBean{
    private List<Event_msgBean> data;

    public List<Event_msgBean> getData() {
        return data;
    }

    public void setData(List<Event_msgBean> data) {
        this.data = data;
    }
}
