package com.txd.hzj.wjlp.distribution.presenter;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;
import com.txd.hzj.wjlp.distribution.model.ExhibitModel;

import java.io.File;
import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/16 8:37
 * 功能描述：分销presenter
 * 联系方式：
 */
public class ShopExhibitPst extends BasePresenter {

    private ExhibitModel mModel;

    public ShopExhibitPst(BaseView baseView) {
        super(baseView);
        mModel = new ExhibitModel();
    }

    /**
     * 小店上货列表
     */
    public void goodsList(String p, String cate_id, String name, String flag, int type) {
        if (1 == type) {
            baseView.showDialog();
        }
        mModel.postExhibitData(p, cate_id, name, flag, baseView);
    }


    /**
     * 小店上货里面的上架按钮接口
     */
    public void shopExhibitGoods(String shop_id, String goods_id, String product_id, String shop_goods_status, String is_special) {
        baseView.showDialog();
        mModel.postShopExhibitGoods(shop_id, goods_id, product_id, shop_goods_status, is_special, baseView);
    }

    public void getRevenue(String id, String type, String c_type, String c_base_type) {
        baseView.showDialog();
        mModel.getRevenueData(id, type, c_type, c_base_type, baseView);
    }

    /**
     * 顾客管理
     */
    public void getShopPerson(String id, String type) {
        baseView.showDialog();
        mModel.getShopPersonData(id, type, baseView);
    }

    /**
     * 小店信息获取接口
     */
    public void shops(String cate_id) {

        baseView.showDialog();

        mModel.getShopsData(cate_id, baseView);
    }

    /**
     * 小店信息更新接口
     */
    public void shopsetData(String id, String shop_name, File shop_pic, String shop_desc, String user_id, String update_time) {
        baseView.showDialog();
        mModel.postShopsSetData(id, shop_name, shop_pic, shop_desc, user_id, update_time, baseView);
    }


    /**
     * 小店订单列表
     */
    public void shopOrderList(String id, String type, int p,String status) {
        baseView.showDialog();
        mModel.getShopOrderList(id, type, status,p, baseView);
    }

    /**
     *查看自己店铺中申请黄券审核的订单列表
     */
    public void shopYellowList(String id, String type, String p) {
        baseView.showDialog();
        mModel.getShopYellowList(id, type, p, baseView);
    }


    /**
     * 商品信息获取
     */
    public void getGoodsList(String id, String p, String shop_id, String type) {
        baseView.showDialog();
        mModel.getfenxiaoGoods(id, p, shop_id, type, baseView);
    }

    /**
     * 商品上架/下架/删除接口
     */
    public void goodsManage(List<String> ids, String shop_goods_status) {
        baseView.showDialog();
        mModel.postManageGoods(ids, shop_goods_status, baseView);
    }

    /**
     * 商品推荐
     */
    public void goodsRecommend(String id, String shop_goods_rec) {
        baseView.showDialog();
        mModel.postRecommend(id, shop_goods_rec, baseView);
    }

    /**
     * 店主审核发送黄券订单的接口
     */
    public void shopSetOrderTicket(String order_id, String ticket_status, String ticket_price) {
        baseView.showDialog();
        mModel.postSetOrderTicket(order_id, ticket_status, ticket_price, baseView);
    }

    /**
     *审核黄券明细接口
     */
    public void shopVouchersLog(int p) {
        baseView.showDialog();
        mModel.postVouchersLog(p, baseView);
    }
}
