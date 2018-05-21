package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/21 14:35
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class IntegralBuyInfoBean extends DataBaseBean implements Serializable{
    private IntegralBuyInfoDataBean data;

    public IntegralBuyInfoDataBean getData() {
        return data;
    }

    public void setData(IntegralBuyInfoDataBean data) {
        this.data = data;
    }
}
