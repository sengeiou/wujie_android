package com.txd.hzj.wjlp.http.goods;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/14 0014
 * 时间：17:42
 * 描述：商品模块
 */

public class GoodsPst extends BasePresenter {

    private Goods goods;

    public GoodsPst(BaseView baseView) {
        super(baseView);
        goods = new Goods();
    }

    // 商品列表
    public void goodsList(int p, String cate_id,int type, String is_active) {
        if (1 == type) {
            baseView.showDialog();
        }
        goods.goodsList(p, cate_id,is_active, baseView);
    }

    // 商品详情
    public void goodsInfo(String goods_id,int p) {
        baseView.showDialog();
        goods.goodsInfo(goods_id,p, baseView);
    }

    // 三级分类商品列表
    public void threeList(String two_cate_id, String three_cate_id, int p, String sell, String tsort,String integral, String psort,String price,int type, String is_active) {
        if (1 == type) {
            baseView.showDialog();
        }
        goods.threeList(two_cate_id, three_cate_id, p,sell,tsort,integral,psort,price,is_active, baseView);
    }

    // 搜索
    public void search(String type, String name, int p, String sell, String tsort,String integral, String psort,String price,boolean show) {
        if (show) {
            baseView.showDialog();
        }
        goods.search(type, name, p,sell,tsort,integral,psort,price, baseView);
    }

}
