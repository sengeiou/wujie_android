package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 16:24
 * 功能描述：//优惠组合
 * 联系方式：常用邮箱或电话
 */
public class CheapGroupBean implements Serializable {
    /**
     * 最多可使用多少代金券
     */
    private String ticket_buy_discount;//最多可使用多少代金券
    private String group_price;//优惠组合价格
    private String integral;//赠送积分
    private String goods_price;//商品总价、
    private List<GoodsBean> goods; //商品列表

    public String getTicket_buy_discount() {
        return ticket_buy_discount;
    }

    public void setTicket_buy_discount(String ticket_buy_discount) {
        this.ticket_buy_discount = ticket_buy_discount;
    }

    public String getGroup_price() {
        return group_price;
    }

    public void setGroup_price(String group_price) {
        this.group_price = group_price;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }
}
