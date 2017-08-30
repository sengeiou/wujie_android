package com.txd.hzj.wjlp.http.collect;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/30 0030
 * 时间：09:26
 * 描述：会员收藏
 * ===============Txunda===============
 */

public class UserCollect {
    // 会员收藏链接
    private String url = Config.BASE_URL + "UserCollect/";

    /**
     * 用户收藏
     *
     * @param p        分页
     * @param type     分类
     * @param baseView 回调
     */
    void collectList(int p, String type, BaseView baseView) {

        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "collectList", params, baseView);

    }

    /**
     * 加入收藏
     *
     * @param type
     * @param id_val
     * @param baseView
     */
    void addCollect(String type, String id_val, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("id_val", id_val);
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "addCollect", params, baseView);
    }

    void delCollect(String collect_ids, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("collect_ids", collect_ids);
        apiTool2.postApi(url + "delCollect", params, baseView);
    }

}
