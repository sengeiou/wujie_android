package com.txd.hzj.wjlp.http.country;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/11 0011
 * 时间：17:44
 * 描述：进口馆
 * ===============Txunda===============
 */

public class CountryPst extends BasePresenter {

    private Country country;

    public CountryPst(BaseView baseView) {
        super(baseView);
        country = new Country();
    }

    // 首页
    public void countryIndex(int p) {
        baseView.showDialog();
        country.countryIndex(p, baseView);
    }

    // 选定国家商品列表
    public void countryGoods(int p, String country_id, String cate_id) {
        baseView.showDialog();
        country.countryGoods(p, country_id, cate_id, baseView);
    }

    // 商品详情
    public void goodsInfo(String goods_id) {
        baseView.showDialog();
        country.goodsInfo(goods_id, baseView);
    }

    // 三级分类商品
    public void threeList(String two_cate_id, String country_id, int p, String three_cate_id) {
        baseView.showDialog();
        country.threeList(two_cate_id, country_id, p, three_cate_id, baseView);
    }

}
