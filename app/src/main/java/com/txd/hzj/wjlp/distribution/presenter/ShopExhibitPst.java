package com.txd.hzj.wjlp.distribution.presenter;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;
import com.txd.hzj.wjlp.distribution.model.ExhibitModel;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/16 8:37
 * 功能描述：小店上货presenter
 * 联系方式：
 */
public class ShopExhibitPst extends BasePresenter {

    private ExhibitModel mModel;
    public ShopExhibitPst(BaseView baseView) {
        super(baseView);
        mModel=new ExhibitModel();
    }

    public void goodsList(String p, String cate_id, int type) {
        if (1 == type) {
            baseView.showDialog();
        }
        mModel.postExhibitData(p, cate_id, baseView);
    }
}
