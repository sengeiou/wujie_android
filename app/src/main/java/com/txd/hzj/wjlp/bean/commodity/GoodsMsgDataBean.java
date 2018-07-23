package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 16:08
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GoodsMsgDataBean implements Serializable {
    private List<Event_msgBean> event_msg;

    public List<Event_msgBean> getEvent_msg() {
        return event_msg;
    }

    public void setEvent_msg(List<Event_msgBean> event_msg) {
        this.event_msg = event_msg;
    }
}
