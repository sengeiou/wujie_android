package com.txd.hzj.wjlp.http.street;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/9 0009
 * 时间：17:05
 * 描述：
 *
 */

public class ThemePst extends BasePresenter {
    private Theme theme;

    public ThemePst(BaseView baseView) {
        super(baseView);
        theme = new Theme();
    }

    // 主题列表
    public void themeList(int p) {
        baseView.showDialog();
        theme.themeList(p, baseView);
    }

    // 主题商品页
    public void themeGoods(String theme_id, int p) {
        baseView.showDialog();
        theme.themeGoods(theme_id, p, baseView);
    }

    // 商品详情
    public void goodsInfo(String goods_id) {
        baseView.showDialog();
        theme.goodsInfo(goods_id, baseView);
    }
}
