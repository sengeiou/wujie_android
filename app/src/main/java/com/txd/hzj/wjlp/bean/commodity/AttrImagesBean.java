package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:04
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class AttrImagesBean implements Serializable {
    /**
     * goods_attr_id : 649
     * pic :
     */

    private String goods_attr_id;
    private String pic;

    public String getGoods_attr_id() {
        return goods_attr_id;
    }

    public void setGoods_attr_id(String goods_attr_id) {
        this.goods_attr_id = goods_attr_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("AttrImagesBean{");
        sb.append("\ngoods_attr_id=");
        sb.append(goods_attr_id);
        sb.append("\npic=");
        sb.append(pic);
        sb.append("}");
        return sb.toString();
    }

}
