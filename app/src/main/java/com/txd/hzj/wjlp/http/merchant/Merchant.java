package com.txd.hzj.wjlp.http.merchant;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/11 0011
 * 时间：17:07
 * 描述：店铺首页
 *
 */

class Merchant {

    private String url = Config.BASE_URL + "Merchant/";

    /**
     * 店铺首页
     *
     * @param merchant_id 店铺id
     * @param p           分页
     * @param baseView    回调
     */
    void merIndex(String merchant_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "merIndex", params, baseView);
    }

    /**
     * 店铺详情
     *
     * @param merchant_id 店铺id
     * @param baseView    回调
     */
    void merInfo(String merchant_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        apiTool2.postApi(url + "merInfo", params, baseView);
    }

    /**
     * 商品页
     *
     * @param merchant_id 店铺id
     * @param is_hot      热销
     * @param new_buy     最新上架
     * @param p           分页
     * @param baseView    回调
     */
    void goodsList(String merchant_id, String is_hot, String new_buy, int p,String search_goods, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("is_hot", is_hot);
        params.addBodyParameter("new_buy", new_buy);
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("search_goods", search_goods);
        apiTool2.postApi(url + "goodsList", params, baseView);
    }

    /**
     * 获取评论列表
     *
     * @param merchant_id 商店id
     * @param p           分类
     * @param baseView    回调
     */
    void commentList(String merchant_id, String goods_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "commentList", params, baseView);
    }

    /**
     * 活动商品——拼团购
     *
     * @param merchant_id 商店id
     * @param p           分类
     * @param baseView    回调
     */
    void groupList(String merchant_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "groupList", params, baseView);
    }

    /**
     * 活动商品——无界预购
     *
     * @param merchant_id 商店id
     * @param p           分类
     * @param baseView    回调
     */
    void preList(String merchant_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "preList", params, baseView);
    }

    /**
     * 活动商品——一元购
     *
     * @param merchant_id 商店id
     * @param p           分类
     * @param baseView    回调
     */
    void oneBuyList(String merchant_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "oneBuyList", params, baseView);
    }

    /**
     * 活动商品——竞拍汇
     *
     * @param merchant_id 商店id
     * @param p           分类
     * @param baseView    回调
     */
    void auctionList(String merchant_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "auctionList", params, baseView);
    }

    /**
     * 活动商品——限量汇
     *
     * @param merchant_id 商店id
     * @param p           分类
     * @param baseView    回调
     */
    void limitList(String merchant_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "limitList", params, baseView);
    }

    /**
     * 商家资质
     *
     * @param merchant_id 商店id
     * @param baseView    回调
     */
    void license(String merchant_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        apiTool2.postApi(url + "license", params, baseView);
    }

    /**
     * 举报商家
     *
     * @param report_type_id 举报类型
     * @param report_content 举报理由
     * @param merchant_id    商家id
     * @param baseView       回调
     */
    void report(String report_type_id, String report_content, String merchant_id, List<File> list, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("report_type_id", report_type_id);
        params.addBodyParameter("report_content", report_content);
        params.addBodyParameter("merchant_id", merchant_id);
        for (int i = 0; i < list.size(); i++) {
            params.addBodyParameter("report_pic" + i, list.get(i));
        }
        apiTool2.postApi(url + "report", params, baseView);
    }

    /**
     * 线下店铺举报商家
     * @param report_type_id  	举报类型
     * @param report_content  举报理由
     * @param merchant_id  商家ID
     * @param list  举报凭证
     */
    void reportMerchant(String report_type_id, String report_content, String merchant_id, List<File> list, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("report_type_id", report_type_id);
        params.addBodyParameter("report_content", report_content);
        params.addBodyParameter("merchant_id", merchant_id);
        for (int i = 0; i < list.size(); i++) {
            params.addBodyParameter("report_pic" + i, list.get(i));
        }
        apiTool2.postApi(Config.BASE_URL + "OfflineStore/reportMerchant", params, baseView);
    }



    void reportType(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "reportType", params, baseView);
    }

}
