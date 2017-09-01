package com.txd.hzj.wjlp.http.category;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/1 0001
 * 时间：09:31
 * 描述：商品分类Pst
 * ===============Txunda===============
 */

public class GoodsCategoryPst extends BasePresenter {
    private GoodsCategory goodsCategory;

    public GoodsCategoryPst(BaseView baseView) {
        super(baseView);
        goodsCategory = new GoodsCategory();
    }

    /**
     * 商品分类
     *
     * @param cate_id 上一级分类
     */
    public void cateIndex(String cate_id) {
        baseView.showDialog();
        goodsCategory.cateIndex(cate_id, baseView);
    }
}
