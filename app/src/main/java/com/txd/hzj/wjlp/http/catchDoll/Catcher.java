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
     * 申诉问题列表
     *
     * @param baseView 回调
     */
    public static void appealApply(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "appealApply", new RequestParams(), baseView);
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


}
