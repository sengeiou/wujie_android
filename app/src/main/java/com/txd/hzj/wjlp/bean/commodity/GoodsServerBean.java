package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:06
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GoodsServerBean implements Serializable {
    /**
     * id : 1
     * server_name : 正品保证
     * desc : 该商品无界优品已经进行商家资质审核
     * icon : http://wjyp.txunda.com/Uploads/GoodsServer/2017-10-28/59f3f9c83eba7.png
     */
    private String id;
    private String server_name;
    private String desc;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
