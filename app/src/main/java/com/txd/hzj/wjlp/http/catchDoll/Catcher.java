package com.txd.hzj.wjlp.http.catchDoll;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：抓娃娃接口
 */
public class Catcher {

    private static String url = Config.OFFICIAL_WEB + "Api/Catcher/";

    /**
     * 获取房间列表
     *
     * @param p        分页页数
     * @param per      每页展示数量，为0时不传此参数，后台默认为10
     * @param baseView 回调
     */
    public static void getRoomList(int p, int per, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("p", String.valueOf(p));
        if (per != 0) {
            params.addBodyParameter("per", String.valueOf(per));
        }
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "getRoomList", params, baseView);
    }

    /**
     * 开始游戏
     *
     * @param id       房間id
     * @param baseView 回調
     */
    public static void startGame(String id, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "startGame", params, baseView);
    }

    /**
     * 进入房间
     *
     * @param id       房間id
     * @param baseView 回調
     */
    public static void enterRoom(String id, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "enterRoom", params, baseView);
    }

    /**
     * 游戏结束后回调
     *
     * @param user_id  用户id
     * @param cid      娃娃机id
     * @param mode     抓取状态码 0抓中 1未抓中
     * @param baseView 回调
     */
    public static void postCatcherData(int user_id, int cid, int mode, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", String.valueOf(user_id));
        params.addBodyParameter("cid", String.valueOf(cid));
        params.addBodyParameter("mode", String.valueOf(mode));
        apiTool2.postApi(url + "postCatcherData", params, baseView);
    }

    /**
     * 获取游戏记录（日志）
     *
     * @param p        当前分页
     * @param per      每页条数
     * @param baseView 回调
     */
    public static void getCatchersLogs(int p, int per, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("p", String.valueOf(p));
        if (per != 0) {
            params.addBodyParameter("per", String.valueOf(per));
        }
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "getCatchersLogs", params, baseView);
    }

    /**
     * 签到
     *
     * @param baseView 回调
     */
    public static void userSign(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userSign", new RequestParams(), baseView);
    }

    /**
     * 领取新人奖励
     *
     * @param baseView 回调
     */
    public static void newAward(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "newAward", new RequestParams(), baseView);
    }

    /**
     * 申诉问题列表
     *
     * @param baseView 回调
     */
    public static void appealApply(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "appealApply", new RequestParams(), baseView);
    }

    /**
     * 申诉
     *
     * @param baseView      回调
     */
    public static void appeal(String id, String type, String reason, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter("type", type);
        params.addBodyParameter("reason", reason);
        apiTool2.postApi(url + "appeal", params, baseView);
    }

    /**
     * 获取抓娃娃用户信息
     *
     * @param baseView 回调
     */
    public static void userCenter(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userCenter", new RequestParams(), baseView);
    }

    /**
     * 我的娃娃
     */
    public static void myAward(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        apiTool2.postApi(url + "myAward", params, baseView);
    }

    /**
     * 记录关注/取消关注
     *
     * @param cid    娃娃机id
     * @param status 1（关注） 0（取消关注）
     */
    public static void getCatcherAttention(int cid, int status, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("cid", String.valueOf(cid));
        params.addBodyParameter("status", String.valueOf(status));
        apiTool2.postApi(url + "getCatcherAttention", params, baseView);
    }

    /**
     * 获取关注列表
     *
     * @param status 1（关注） 0（取消关注）
     */
    public static void getCatcherAttentionList(int status, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("status", String.valueOf(status));
        apiTool2.postApi(url + "getCatcherAttentionList", params, baseView);
    }

    /**
     * 房间信息获取
     *
     * @param id       房间号
     * @param baseView 回调
     */
    public static void catcherDetails(String id, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        apiTool2.postApi(url + "catcherDetails", params, baseView);
    }

    /**
     * 退出房间
     *
     * @param id       房间号
     * @param baseView 回调
     */
    public static void outRoom(String id, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        apiTool2.postApi(url + "outRoom", params, baseView);
    }

    /**
     * 获取充值界面列表
     *
     * @param baseView 回调
     */
    public static void exchangeList(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "exchangeList", new RequestParams(), baseView);
    }

    /**
     * 获取银两明细
     *
     * @param type     明细类别 0 兑换记录 1 收入   2支出
     * @param baseView 回调
     */
    public static void userCoinLogInfo(int type, int page, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", String.valueOf(type));
        params.addBodyParameter("p", String.valueOf(page));
        apiTool2.postApi(url + "userCoinLogInfo", params, baseView);
    }

    /**
     * 我的娃娃列表
     *
     * @param type     获取类型 1、2、3、4 寄存，待邮，已发货，已兑换
     * @param baseView
     */
    public static void myList(int type, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", String.valueOf(type));
        apiTool2.postApi(url + "Mylist", params, baseView);
    }

    /**
     * 查询抓中记录页面（首页头部滚动字幕点击进来的列表）
     *
     * @param baseView
     */
    public static void userCatcher(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userCatcher", new RequestParams(), baseView);
    }

    /**
     * 生成充值订单
     *
     * @param money    实际支付金额
     * @param pay_type 支付方式
     * @param baseView 回调
     */
    public static void setOrder(String money, String pay_type, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("money", money);
        params.addBodyParameter("pay_type", pay_type);
        apiTool2.postApi(url + "setOrder", params, baseView);
    }


    /**
     *首页列表筛选
     * @param clumn
     * @param status
     * @param baseView
     */
    public static void catcherFilter(String clumn, String status,int p, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("clumn", clumn);
        params.addBodyParameter("status", status);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "catcherFilter", params, baseView);
    }


    /**
     * 抓娃娃成功次数兑换银两
     * @param cid 娃娃机ID
     * @param times 不传默认为1次
     * @param baseView
     */
    public static void exchangeCoin(String cid, String times, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("times", times);
        apiTool2.postApi(url + "exchangeCoin", params, baseView);
    }

    /**
     * 抓娃娃商品订单生成
     * @param cid
     * @param goods_id
     * @param baseView
     */
    public static void goodsOrder(String cid, String goods_id,String address_id,String product_id,String catcher_num, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("product_id", product_id);
        params.addBodyParameter("catcher_num", catcher_num);
        apiTool2.postApi(url + "goodsOrder", params, baseView);
    }


    /**
     * 改变房间状态
     * @param cid
     * @param baseView
     */
    public static void roomStatus(String cid, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("cid", cid);
        apiTool2.postApi(url + "roomStatus", params, baseView);
    }
}
