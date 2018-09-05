package com.txd.hzj.wjlp.distribution.model;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/16 8:39
 * 功能描述：分销Model
 * 联系方式：
 */
public class ExhibitModel {
    //分销的base_url
    public static final String DISTRIBUTION_URL= Config.OFFICIAL_WEB+"Api/Distribution/";

    /**
     * 小店上货接口
     * @param p  页数
     * @param cate_id  小店点标id
     * @param name  排序字段名 积分：’red_return_integral’ 代金券：’discount’， 价格：’shop_price’, 销量：’new_sell_num
     * @param flag  正序（asc） 倒序 (desc)
     */
    public void postExhibitData(String p, String cate_id, String name,String flag,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", p);
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("name", name);
        params.addBodyParameter("flag", flag);
        apiTool2.postApi(DISTRIBUTION_URL+"goodsList", params, baseView);
    }


    /**
     * 小店营收接口
     * @param id  小店id
     * @param type 必须传1才是统计信息
     * @param c_type  1：销售额 0：净收益
     * @param c_base_type  1: 日 2：月 0 ：年
     */
    public void getRevenueData(String  id, String type,String  c_type, String c_base_type,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        params.addQueryStringParameter("c_type", c_type);
        params.addQueryStringParameter("c_base_type", c_base_type);
        apiTool2.getApi(DISTRIBUTION_URL+"shops", params, baseView);
    }


    /**
     * 小店信息获取，传id取单个，不传id取列表
     * @param cate_id
     */
    public void getShopsData(String cate_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", cate_id);
        apiTool2.getApi(DISTRIBUTION_URL+"shops", params, baseView);
    }

    /**
     * 小店信息更新接口
     * @param id  小店id
     * @param shop_name  用户名
     * @param shop_pic 	小店点标id
     * @param shop_desc 小店描述
     * @param user_id 	小店店主
     * @param set_id 小店等级 小店等级升级
     * @param shop_status 小店状态（默认0 [0 正常 9 删除]）
     * @param pay_money 付款金额数（默认0）
     * @param pay_orders 付款订单数（默认0）
     * @param visit_nums 小店访问数（默认0）
     * @param update_time 	更新时间
     */
    public void postShopsSetData(String id,String shop_name,String shop_pic,String shop_desc,String user_id,String set_id,String shop_status,String pay_money,String pay_orders,String visit_nums,String update_time, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("id", id);
        params.addBodyParameter("shop_name", shop_name);
        params.addBodyParameter("shop_pic", shop_pic);
        params.addBodyParameter("shop_desc", shop_desc);
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("set_id", set_id);
        params.addBodyParameter("shop_status", shop_status);
        params.addBodyParameter("pay_money", pay_money);
        params.addBodyParameter("pay_orders", pay_orders);
        params.addBodyParameter("visit_nums", visit_nums);
        params.addBodyParameter("update_time", update_time);

        apiTool2.postApis(DISTRIBUTION_URL+"shops", params, baseView);
    }



    /**
     *  顾客管理接口
     * @param id shop_id小店id
     * @param type 1：暂定为顾客信息查询
     */
    public void getShopPersonData(String  id, String type,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        apiTool2.getApi(DISTRIBUTION_URL+"orders", params, baseView);
    }


    /**
     *本店订单列表
     * @param id  	shop_id小店id
     * @param type  2：暂定查询本店订单列表
     * @param status  	‘0’: ‘待支付‘ ； ‘1’: ‘待发货’ ； ‘2’: ‘待收货’ ；’3’: ‘待评价’；’4’: ‘已完成）5已取消 9删除 不传默认显示全部
     */
    public void getShopOrderList(String  id, String type,String status,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        if (!status.isEmpty()){
            params.addQueryStringParameter("status", status);
        }
        apiTool2.getApi(DISTRIBUTION_URL+"orders", params, baseView);
    }


    /**
     *商品信息获取get
     * @param id 分销商品id   商品信息获取，传id取单个，不传id取列表
     * @param p  分页页数
     * @param shop_id  小店id
     * @param type 0 普通 1 399分销
     */
    public void getfenxiaogoods(String  id, String p,String shop_id,String type,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        if (!id.isEmpty()){
            params.addQueryStringParameter("id", id);
        }
        params.addQueryStringParameter("p", p);
        params.addQueryStringParameter("shop_id", shop_id);
        params.addQueryStringParameter("type", type);
        apiTool2.getApi(DISTRIBUTION_URL+"goods", params, baseView);
    }


}
