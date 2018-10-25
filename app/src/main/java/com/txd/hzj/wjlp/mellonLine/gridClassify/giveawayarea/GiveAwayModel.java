package com.txd.hzj.wjlp.mellonLine.gridClassify.giveawayarea;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/24 9:18
 * 功能描述：
 */
public class GiveAwayModel {


    /**
     * 赠品专区首页接口
     * @param p 分页参数
     */
    public static void postGiftGoodsIndex(String p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", p);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoods/giftGoodsIndex", params, baseView);
    }

    /**
     *赠品专区详情页接口
     * @param gift_goods_id
     */
    public static void postGiftGoodsInfo(String gift_goods_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("gift_goods_id", gift_goods_id);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoods/giftGoodsInfo", params, baseView);
    }

    /**
     *赠品专区结算页接口
     * @param gift_goods_id  赠品专区商品id
     * @param num  个数
     * @param address_id  地址id
     */
    public static void postGiftGoodsOrderShoppingCart(String gift_goods_id,String num,String address_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("giftGoods_id", gift_goods_id);
        params.addBodyParameter("num", num);
        if (!address_id.isEmpty()){
            params.addBodyParameter("address_id", address_id);
        }
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/shoppingCart", params, baseView);
    }


    /**
     *生成订单接口
     * @param address_id 地址id
     * @param goods_num 商品数量
     * @param order_id 订单id
     * @param goods json格式 普通商品下单（单商品）参数：[{“属性id”:”91”,”商品id”:”43”,”商品数量”:”3”,”配送方式id”:”11”,”配送方式类型:”1”,”快递公司ID”:”1”,”邮费”:”1”,”快递类型ID”:”1”,”描述”:” “,”相同模板的商品ID”:”1,2,3”}] [{“product_id”:”91”,”goods_id”:”43”,”num”:”3”,”tem_id”: “4”,”type_status”: 1, “shipping_id”: “35”,”pay”: “10”,”type”: “1”,”desc”: “满6.000KG满7.00元包邮”,”same_tem_id”: “308,98”}] 购物车商品下单（多商品）参数：[{“购物车id”:”91”,”配送方式id”:”11”,”配送方式类型:”1”,”快递公司ID”:”1”,”邮费”:”1”,”快递类型ID”:”1”,”描述”:” “,”相同模板的商品ID”:”1,2,3”}] [{“cate_ids”:”91”,”tem_id”: “4”,”type_status”: 1, “shipping_id”: “35”,”pay”: “10”,”type”: “1”,”desc”: “满6.000KG满7.00元包邮”,”same_tem_id”: “308,98”}]
     * @param leave_message 留言
     * @param invoice json , 请按顺序传入！！！[{“发票类型id”:”1”,”发票抬头（1->个人，2->公司）”:”1”,”发票抬头名”:”name”,”发票明细”:”detail”,”发票id”:”1”,”识别号”:1111,”是否开发票（1->是，0->否）”:1},{“t_id”:”1”,”rise”:”1”,”rise_name”:”name”,”invoice_detail”:”detail”,”invoice_id”:”3”,,”recognition”:1111,”is_invoice”:1}]
     * @param order_type  订单类型（ 0:普通 1限量购 2无界商店 3进口馆 4搭配购 13 2980专区  15赠品专区）
     */
    public static void postGiftGoodsOrderSetOrder(String address_id,String goods_num,String order_id,String goods,String leave_message,String invoice, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("goods_num", goods_num);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("goods", goods);
        params.addBodyParameter("leave_message", leave_message);
        params.addBodyParameter("invoice", invoice);
        params.addBodyParameter("order_type", "15");

        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/setOrder", params, baseView);
    }


    /**
     *赠品券支付接口
     * @param order_id  订单id
     * @param goods_num  商品数
     */
    public static void postGiftGoodsPay(String order_id,String goods_num, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("order_type", "15");
        params.addBodyParameter("goods_num", goods_num);

        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsPay/giftGoodsPay", params, baseView);
    }


    /**
     *订单列表接口
     * @param order_status  订单状态（0待付款1待发货2待收货3待评价4已完成5取消 9全部)
     * @param p  页数
     */
    public static void postGiftOrderList(String order_status,int p, BaseView baseView){
        switch (order_status) {
            case "0":
                order_status = "9";
                break;
            case "1":
                order_status = "0";
                break;
            case "2":
                order_status = "1";
                break;
            case "3":
                order_status = "2";
                break;
            case "4":
                order_status = "3";
                break;
        }
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_status", order_status);
        params.addBodyParameter("p", String.valueOf(p));

        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/OrderList", params, baseView);
    }


    /**
     * 订单详情接口
     * @param order_id  订单id
     */
    public static void postGiftGoodsOrderDetails(String order_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/details", params, baseView);
    }

    /**
     * 取消订单
     * @param order_id  订单id
     */
    public static void postGiftGoodsOrderCancelOrder(String order_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/CancelOrder", params, baseView);
    }

    /**
     * 删除订单接口
     * @param order_id  订单id
     */
    public static void postGiftGoodsOrderDeleteOrder(String order_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/DeleteOrder", params, baseView);
    }


    /**
     *确认收货接口
     * @param order_id
     * @param status  状态（2->放弃，1->确认）
     */
    public static void postGiftGoodsOrderReceiving(String order_id, String status,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("status", status);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/Receiving", params, baseView);
    }


    /**
     * 提醒发货接口
     * @param order_id  订单id
     */
    public static void postGiftGoodsOrderRemind(String order_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/remind", params, baseView);
    }

    /**
     * 延长收货接口
     * @param order_goods_id  订单商品表id
     */
    public static void postGiftGoodsOrderDelayReceiving(String order_goods_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_goods_id", order_goods_id);
        apiTool2.postApis(Config.OFFICIAL_WEB + "Api/GiftGoodsOrder/delayReceiving", params, baseView);
    }




}
