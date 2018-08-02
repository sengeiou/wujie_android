package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.List;

/**
 * 线下店铺模块
 */
public class OfflineStore {
    public static String url = Config.BASE_URL + "OfflineStore/";

    public static void Index(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "Index",new RequestParams() , baseView);
    }

    /**
     *
     * @param lng  经度
     * @param lat  纬度
     * @param p   列表页面p递增 详情页面没有下拉刷新 只是p=1
     * @param merchant_id  店铺ID （s_id是店铺ID） 如果是店铺详情页面需要传 防止店铺详情页面的附近商家有当前点击的店铺
     */
    public  static void  offlineStoreList(String lng,String lat,int p,String  merchant_id,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("lng", lng);
        requestParams.addBodyParameter("lat", lat);
        requestParams.addBodyParameter("p", String.valueOf(p));
        requestParams.addBodyParameter("merchant_id",merchant_id);
        apiTool2.postApi(url + "offlineStoreList",requestParams , baseView);
    }


    /**
     *订单列表
     * @param pay_status  传空 全部 0未支付 1已支付 注意注意 ：如果pay_status=0 时 当 status=0 有 取消订单 和 立即支付按钮 当status= 5时只有删除订单按钮 如果pay_status = 1时 只有删除按钮
     * @param p  分页参数
     */
    public static void offLineOrderList(String pay_status,String p,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pay_status", pay_status);
        requestParams.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url+"orderList",requestParams , baseView);
    }


    /**
     *订单详情
     * @param order_id  订单ID 注意注意 ：从订单列表中点进详情时 需要带上 order_id merchant_id pay_status status 参数 判断 如果pay_status=1时 显示删除按钮 如果pay_status=0是 status=0显示按钮 取消按钮 立即支付 如果status = 5 只显示删除按钮
     */
    public static void offLineOrderInfo(String order_id,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url+"orderInfo",requestParams , baseView);
    }


    /**
     * 取消和删除订单
     * @param order_id  订单ID
     * @param order_status  5是取消订单 9是删除订单
     */
    public static void offLinedelOrder(String order_id,String order_status,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("order_status", order_status);
        apiTool2.postApi(url+"delOrder",requestParams , baseView);
    }


    /**
     *下单接口
     * @param merchant_id  店铺ID
     * @param pay_money  支付金额
     * @param order_id  第二次支付时传 在订单列表中或者订单详情中
     */
    public static void setOrder(String merchant_id,String pay_money,String order_id,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("merchant_id", merchant_id);
        requestParams.addBodyParameter("pay_money", pay_money);
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url+"setOrder",requestParams , baseView);
    }


    /**
     *搜索
     * @param name  商家名称
     * @param p     分页

     */
    public static  void  offlinesearch(String name,String p,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("name", name);
        requestParams.addBodyParameter("p", p);
        apiTool2.postApi(url+"search",requestParams , baseView);
    }


    /**
     *评论页面参数获取
     * @param order_id 	订单ID
     */
    public static  void commentPageParameters(String order_id,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url+"common",requestParams , baseView);
    }


    /**
     * 提交评论
     * @param order_id 订单ID
     * @param content 	评论内容
     * @param list 评论图片集合
     * @param environment 环境星级
     * @param serve 	服务星级
     */
    public static  void commentSubmit(String order_id, String content, List<File> list, String environment, String serve, BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("content", content);
        requestParams.addBodyParameter("environment", environment);
        requestParams.addBodyParameter("serve", serve);
        for (File file:list){
            requestParams.addBodyParameter("picture", file);
        }
        apiTool2.postApi(url+"comment",requestParams , baseView);
    }
}
