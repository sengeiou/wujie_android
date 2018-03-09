package com.txd.hzj.wjlp.http.carbuy;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/18 0018
 * 时间：10:53
 * 描述：汽车详情pst
 * ===============Txunda===============
 */

public class CarBuyPst extends BasePresenter {
    private CarBuy carBuy;

    public CarBuyPst(BaseView baseView) {
        super(baseView);
        carBuy = new CarBuy();
    }

    // 汽车筛选页
    public void carSelect() {
        baseView.showDialog();
        carBuy.carSelect(baseView);
    }

    // 汽车列表
    public void carList(String min_price, String max_price, int p, String style_id, String brand_id, String lng, String lat) {
        baseView.showDialog();
        carBuy.carList(min_price, max_price, p, style_id, brand_id, baseView, lng, lat);
    }

    // 汽车详情
    public void carInfo(String car_id) {
        baseView.showDialog();
        carBuy.carInfo(car_id, baseView);
    }
}
