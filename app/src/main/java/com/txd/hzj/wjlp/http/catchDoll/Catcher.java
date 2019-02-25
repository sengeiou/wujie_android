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
     * @param user_id       用户id
     * @param cid           娃娃机id
     * @param goods_id      娃娃机奖品id
     * @param status        记录状态（0正常 1已使用 9删除）
     * @param create_time   创建时间
     * @param update_time   修改时间
     * @param appeal        申诉理由
     * @param appeal_reason 申诉原因
     * @param mode          是否抓中（0抓中 1未抓中）
     * @param baseView      回调
     */
    public static void appeal(int user_id, int cid, int goods_id, int status, int create_time, int update_time, int appeal, String appeal_reason, int mode, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", String.valueOf(user_id));
        params.addBodyParameter("cid", String.valueOf(cid));
        params.addBodyParameter("goods_id", String.valueOf(goods_id));
        params.addBodyParameter("status", String.valueOf(status));
        params.addBodyParameter("create_time", String.valueOf(create_time));
        params.addBodyParameter("update_time", String.valueOf(update_time));
        params.addBodyParameter("appeal", String.valueOf(appeal));
        params.addBodyParameter("appeal_reason", appeal_reason);
        params.addBodyParameter("mode", String.valueOf(mode));
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
     *
     * @param page 当前分页页数
     * @param type 查询分类
     * @param per  每页条数
     */
    public static void myAward(int page, int type, int per, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("p", String.valueOf(page));
        params.addBodyParameter("type", String.valueOf(type));
        params.addBodyParameter("per", String.valueOf(per));
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
     * 兑换抓娃娃商品（下订单）
     *
     * @param goods_id 1（关注） 0（取消关注）
     */
    public static void exchangeCatchersGoodsOrder(int cid, int goods_id, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("cid", String.valueOf(goods_id));
        params.addBodyParameter("goods_id", String.valueOf(goods_id));
        apiTool2.postApi(url + "exchangeCatchersGoodsOrder", params, baseView);
    }

    /**
     * 抓娃娃商品订单生成
     *
     * @param goods_id      商品id
     * @param address_id    地址id
     * @param catcher_num   需要兑换次数
     * @param leave_message 备注
     * @param goods         商品对象Json字符串
     * @param is_zhifu      是否已支付
     * @param baseView      回调
     */
    public static void setOrder(int goods_id, int address_id, int catcher_num, String leave_message, String goods, int is_zhifu, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("goods_id", String.valueOf(goods_id));
        params.addBodyParameter("address_id", String.valueOf(address_id));
        params.addBodyParameter("catcher_num", String.valueOf(catcher_num));
        params.addBodyParameter("goods", goods);
        params.addBodyParameter("leave_message", leave_message);
        params.addBodyParameter("is_zhifu", String.valueOf(is_zhifu));
        apiTool2.postApi(url + "setOrder", params, baseView);
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
     * @param type     明细类别 0获得 1消耗
     * @param baseView 回调
     */
    public static void userCoinLogInfo(int type, int page, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", String.valueOf(type));
        params.addBodyParameter("p", String.valueOf(page));
        apiTool2.postApi(url + "userCoinLogInfo", params, baseView);
    }

}
