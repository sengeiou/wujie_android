package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/21 14:57
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class IntegralBuyInfoDataBean extends DataBean implements Serializable {
    private List<AllGoodsBean>  guess_goods_list;

    public List<AllGoodsBean> getGuess_goods_list() {
        return guess_goods_list;
    }

    public void setGuess_goods_list(List<AllGoodsBean> guess_goods_list) {
        this.guess_goods_list = guess_goods_list;
    }
    private  LimitGoodsInfo goodsInfo;

    @Override
    public LimitGoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(LimitGoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
