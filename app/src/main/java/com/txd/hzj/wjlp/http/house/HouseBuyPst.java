package com.txd.hzj.wjlp.http.house;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/19 0019
 * 时间：10:24
 * 描述：
 * ===============Txunda===============
 */

public class HouseBuyPst extends BasePresenter {

    private HouseBuy houseBuy;

    public HouseBuyPst(BaseView baseView) {
        super(baseView);
        houseBuy = new HouseBuy();
    }

    // 房产列表
    public void houseList(int p, String lng, String lat, String integral_sort, String distance_sort, String price_sort,
                          String sort) {
        baseView.showDialog();
        houseBuy.houseList(p, lng, lat, integral_sort, distance_sort, price_sort, sort, baseView);
    }

    // 户型详情
    public void houseInfo(String house_id) {
        baseView.showDialog();
        houseBuy.houseInfo(house_id, baseView);
    }

    // 户型列表页
    public void houseStyleList(String house_id, int p) {
        baseView.showDialog();
        houseBuy.houseStyleList(house_id, p, baseView);
    }

    // 户型详情
    public void styleInfo(String style_id) {
        baseView.showDialog();
        houseBuy.styleInfo(style_id, baseView);
    }

}
