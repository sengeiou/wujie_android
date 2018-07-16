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
        mModel = new ExhibitModel();
    }

    public void goodsList(String p, String cate_id, int type) {
        if (1 == type) {
            baseView.showDialog();
        }
        mModel.postExhibitData(p, cate_id, baseView);
    }

    /**
     * 小店信息获取接口
     */
    public void shops(String cate_id) {

        baseView.showDialog();

        mModel.postShopsData(cate_id, baseView);
    }

    /**
     * 小店信息更新接口
     */
    public void shopsetData(String id, String shop_name, String shop_pic, String shop_desc, String user_id, String set_id, String shop_status, String pay_money, String pay_orders, String visit_nums, String update_time) {
        baseView.showDialog();
        mModel.postShopsSetData(id, shop_name, shop_pic, shop_desc, user_id, set_id, shop_status, pay_money, pay_orders, visit_nums, update_time, baseView);
    }
}
