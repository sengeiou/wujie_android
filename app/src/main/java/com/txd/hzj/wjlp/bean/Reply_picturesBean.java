package com.txd.hzj.wjlp.bean;

import java.io.Serializable;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/28 15:16
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class Reply_picturesBean implements Serializable {
    private String path;//"http://www.wjyp.com/Uploads/2018-06-28/5b345b38a4c0f.jpg
    private String name;//"9341950_123944651000_2.jpg",
    private String id;//"23662",
    private String status;//"1"

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
