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
    private List<GuessGoodsListBean>  guess_goods_list;

    public List<GuessGoodsListBean> getGuess_goods_list() {
        return guess_goods_list;
    }

    public void setGuess_goods_list(List<GuessGoodsListBean> guess_goods_list) {
        this.guess_goods_list = guess_goods_list;
    }
}
