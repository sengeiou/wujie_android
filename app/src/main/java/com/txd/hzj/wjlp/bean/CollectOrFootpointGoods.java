package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/25 0025
 * 时间：09:16
 * 描述：收藏，足迹商品
 * ===============Txunda===============
 */

public class CollectOrFootpointGoods {
    /**
     * code : 1
     * message : 获取成功
     * data : {"list":[{"goods_id":"商品ID","goods_name":"商品名称","goods_img":"商品图片","market_price":"市场价",
     * "shop_price":"销售价","integral":"积分","sell_num":"销量","ticket_buy_id":"","country_id":"0",
     * "country_logo":"国家logo","is_buy":"1","add_time":"0","ticket_buy_discount":"购物券折扣","isSelect":"是否被选中"}]}
     * nums : 商品数量
     */

    private String code;
    private String message;
    private int nums;

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

    private List<CFGoodsList> list;

    public List<CFGoodsList> getList() {
        return list;
    }

    public void setList(List<CFGoodsList> list) {
        this.list = list;
    }
}