package com.txd.hzj.wjlp.bean;

import com.txd.hzj.wjlp.minetoAty.mellInto.MellIntoListAty;

import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/25 0025
 * 时间：11:26
 * 描述：
 *
 */

public class CollectOrFootpointMell {

    /**
     * code : 1
     * message : 获取成功
     * data : [{"isSelect":"false","merInfo":{"merchant_id":"店铺id","merchant_name":"店铺名称","merchant_desc":"店铺简介",
     * "score":"评分","logo":"店铺logo"},"goodsList":[{"goods_id":"商品ID","goods_img":"商品图片","shop_price":"商品价格"}]}]
     * nums : 足迹数量
     */

    private String code;
    private String message;
    private int nums;
    private List<MellInfoList> data;

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

    public List<MellInfoList> getData() {
        return data;
    }

    public void setData(List<MellInfoList> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CollectOrFootpointMell{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", nums=" + nums +
                ", data=" + data +
                '}';
    }
}
