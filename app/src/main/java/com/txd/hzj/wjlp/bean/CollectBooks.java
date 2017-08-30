package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/30 0030
 * 时间：10:41
 * 描述：
 * ===============Txunda===============
 */

public class CollectBooks {
    /**
     * code : 1
     * message : 获取成功
     * data : [{"collect_id":"收藏编号","aid":"文章id","title":"文章标题","logo":"文章封面图","collect_num":"收藏数","page_views":"浏览量"}]
     * nums : 1
     */

    private String code;
    private String message;
    private int nums;
    private List<AcademyList> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public List<AcademyList> getData() {
        return data;
    }

    public void setData(List<AcademyList> data) {
        this.data = data;
    }
}
