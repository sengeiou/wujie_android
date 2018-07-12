package com.txd.hzj.wjlp.bean.groupbuy;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：11:30
 * 描述：属性图片
 *
 */

public class AttrImagesBean {
    /**
     * attr_id : 属性id
     * pic : 属性图片
     */

    private String attr_id;
    private String pic;

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "AttrImagesBean{" +
                "attr_id='" + attr_id + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
