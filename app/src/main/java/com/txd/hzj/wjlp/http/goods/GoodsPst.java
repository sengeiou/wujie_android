package com.txd.hzj.wjlp.http.goods;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/14 0014
 * 时间：17:42
 * 描述：商品模块
 * ===============Txunda===============
 */

public class GoodsPst extends BasePresenter {

    private Goods goods;

    public GoodsPst(BaseView baseView) {
        super(baseView);
        goods = new Goods();
    }

    // 商品列表
    public void goodsList(int p, String cate_id, int type) {
        if (1 == type) {
            baseView.showDialog();
        }
        goods.goodsList(p, cate_id, baseView);
    }

    // 商品详情
    public void goodsInfo(String goods_id) {
        baseView.showDialog();
        goods.goodsInfo(goods_id, baseView);
    }

    // 三级分类商品列表
    public void threeList(String two_cate_id, String three_cate_id, int p, int type) {
        if (1 == type) {
            baseView.showDialog();
        }
        goods.threeList(two_cate_id, three_cate_id, p, baseView);
    }

}