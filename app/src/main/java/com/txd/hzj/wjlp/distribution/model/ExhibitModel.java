package com.txd.hzj.wjlp.distribution.model;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/16 8:39
 * 功能描述：分销Model
 * 联系方式：
 */
public class ExhibitModel {
    //分销的base_url
    public static final String DISTRIBUTION_URL = Config.OFFICIAL_WEB + "Api/Distribution/";

    /**
     * 小店上货接口
     *
     * @param p       页数
     * @param cate_id 小店点标id
     * @param name    排序字段名 积分：’red_return_integral’ 代金券：’discount’， 价格：’shop_price’, 销量：’new_sell_num
     * @param flag    正序（asc） 倒序 (desc)
     */
    public void postExhibitData(String p, String cate_id, String name, String flag, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("p", p);
        params.addQueryStringParameter("cate_id", cate_id);
        params.addQueryStringParameter("name", name);
        params.addQueryStringParameter("flag", flag);
        apiTool2.getApi(DISTRIBUTION_URL + "goodsList", params, baseView);
    }


    /**
     * 小店营收接口
     *
     * @param id          小店id
     * @param type        必须传1才是统计信息
     * @param c_type      1：销售额 0：净收益
     * @param c_base_type 1: 日 2：月 0 ：年
     */
    public void getRevenueData(String id, String type, String c_type, String c_base_type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("c_type", c_type);
        params.addQueryStringParameter("c_base_type", c_base_type);
        apiTool2.getApi(DISTRIBUTION_URL + "shops", params, baseView);
    }


    /**
     * 小店信息获取，传id取单个，不传id取列表
     *
     * @param cate_id
     */
    public void getShopsData(String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", cate_id);
        apiTool2.getApi(DISTRIBUTION_URL + "shops", params, baseView);
    }

    /**
     * 小店信息更新接口
     *
     * @param id          小店id
     * @param shop_name   用户名
     * @param shop_pic    小店点标id
     * @param shop_desc   小店描述
     * @param user_id     小店店主
     * @param set_id      小店等级 小店等级升级
     * @param shop_status 小店状态（默认0 [0 正常 9 删除]）
     * @param pay_money   付款金额数（默认0）
     * @param pay_orders  付款订单数（默认0）
     * @param visit_nums  小店访问数（默认0）
     * @param update_time 更新时间
     */
    public void postShopsSetData(String id, String shop_name, File shop_pic, String shop_desc, String user_id, String update_time, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("id", id);
        params.addBodyParameter("shop_name", shop_name);
        params.addBodyParameter("shop_pic", shop_pic);
        params.addBodyParameter("shop_desc", shop_desc);
        params.addBodyParameter("user_id", user_id);
        //        params.addBodyParameter("set_id", set_id);
        //        params.addBodyParameter("shop_status", shop_status);
        //        params.addBodyParameter("pay_money", pay_money);
        //        params.addBodyParameter("pay_orders", pay_orders);
        //        params.addBodyParameter("visit_nums", visit_nums);
        params.addBodyParameter("update_time", update_time);

        apiTool2.postApis(DISTRIBUTION_URL + "shops", params, baseView);
    }


    /**
     * 顾客管理接口
     *
     * @param id   shop_id小店id
     * @param type 1：暂定为顾客信息查询
     */
    public void getShopPersonData(String id, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        apiTool2.getApi(DISTRIBUTION_URL + "orders", params, baseView);
    }


    /**
     * 本店订单列表
     *
     * @param id     shop_id小店id
     * @param type   2：暂定查询本店订单列表
     * @param status ‘0’: ‘待支付‘ ； ‘1’: ‘待发货’ ； ‘2’: ‘待收货’ ；’3’: ‘待评价’；’4’: ‘已完成）5已取消 9删除 不传默认显示全部
     */
    public void getShopOrderList(String id, String type, String status, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        if (!status.isEmpty()) {
            params.addQueryStringParameter("status", status);
        }
        apiTool2.getApi(DISTRIBUTION_URL + "orders", params, baseView);
    }

    /**
     * 查看自己店铺中申请黄券审核的订单列表
     * @param id shop_id小店id
     * @param type  	4：查看自己店铺中申请黄券审核的订单
     * @param p  分页页数
     */
    public void getShopYellowList(String id, String type, String p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("p", p);
        apiTool2.getApi(DISTRIBUTION_URL + "orders", params, baseView);
    }


    /**
     * 商品信息获取get
     *
     * @param id      分销商品id   商品信息获取，传id取单个，不传id取列表
     * @param p       分页页数
     * @param shop_id 小店id
     * @param type    3 已售完 2 399分销 1 普通商品下架 0 普通商品出售中
     */
    public void getfenxiaoGoods(String id, String p, String shop_id, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        if (!id.isEmpty()) {
            params.addQueryStringParameter("id", id);
        }
        params.addQueryStringParameter("p", p);
        params.addQueryStringParameter("shop_id", shop_id);
        params.addQueryStringParameter("type", type);
        apiTool2.getApi(DISTRIBUTION_URL + "goods", params, baseView);
    }


    /**
     * 商品上架/下架/删除接口
     *
     * @param ids               商品id 多个用”，”隔开
     * @param shop_goods_status 上架 传0  下架 传1 删除 传9
     */
    public void postManageGoods(List<String> ids, String shop_goods_status, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            builder.append(ids.get(i));
            if (i != ids.size() - 1) {
                builder.append(",");
            }
        }
        params.addBodyParameter("id", builder.toString());
        params.addBodyParameter("shop_goods_status", shop_goods_status);
        apiTool2.postApis(DISTRIBUTION_URL + "goods", params, baseView);
    }


    /**
     * 录入商品接口(小店上货的上架接口)
     *
     * @param shop_id           小店id
     * @param goods_id          商品id
     * @param product_id        (唯一标识)
     * @param shop_goods_status （默认0 [0 正常(上架中) 1 下架 9 删除]）
     * @param is_special        1 是专区商品（399区商品） 0 普通商品 （默认）
     */
    public void postShopExhibitGoods(String shop_id, String goods_id, String product_id, String shop_goods_status, String is_special, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("shop_id", shop_id);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("product_id", product_id);
        params.addBodyParameter("shop_goods_status", shop_goods_status);
        params.addBodyParameter("is_special", is_special);
        apiTool2.postApi(DISTRIBUTION_URL + "goods", params, baseView);
    }

    /**
     * 店主审核发送黄券订单的接口
     *
     * @param order_id      订单id
     * @param ticket_status 1:申请；2：批准；3：拒绝；4：使用；5：未使用
     * @param ticket_price  申请黄券数
     */
    public void postSetOrderTicket(String order_id, String ticket_status, String ticket_price, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("ticket_status", ticket_status);
        params.addBodyParameter("ticket_price", ticket_price);
        apiTool2.postApi(DISTRIBUTION_URL + "setOrderTicket", params, baseView);
    }


}
