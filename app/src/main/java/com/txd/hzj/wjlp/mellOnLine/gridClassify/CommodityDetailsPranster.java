package com.txd.hzj.wjlp.mellOnLine.gridClassify;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 11:27
 * 功能描述： 商品详情pranster
 * 联系方式：常用邮箱或电话
 */
public class CommodityDetailsPranster implements CommodityDetailsInter.CommodityPranster {
    protected CommodityDetailsInter.CommodityView commodityView;

    @Override
    public void setView(CommodityDetailsInter.CommodityView view) {
        commodityView = view;
    }
}
