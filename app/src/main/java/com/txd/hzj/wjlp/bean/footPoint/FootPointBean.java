package com.txd.hzj.wjlp.bean.footPoint;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/22 0022
 * 时间：09:07
 * 描述：我的足迹商家
 * ===============Txunda===============
 */

public class FootPointBean {


    /**
     * code : 1
     * message : 获取成功
     * data : [{"merInfo":{"merchant_id":"店铺id","merchant_name":"店铺名称","merchant_desc":"店铺简介","score":"评分",
     * "logo":"店铺logo"},"goodsList":[{"goods_id":"商品ID","goods_img":"商品图片","shop_price":"商品价格"}]}]
     * nums : 足迹数量
     */

    private String code;
    private String message;
    private int nums;
    private List<FootMellsBan> data;

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

    public List<FootMellsBan> getData() {
        return data;
    }

    public void setData(List<FootMellsBan> data) {
        this.data = data;
    }
}
