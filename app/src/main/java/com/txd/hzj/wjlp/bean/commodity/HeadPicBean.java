package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/15 9:33
 * 功能描述：//团员头像
 * 联系方式：常用邮箱或电话
 */
public class HeadPicBean implements Serializable {
    private String pic;
    private String type;
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
