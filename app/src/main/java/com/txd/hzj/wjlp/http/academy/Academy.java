package com.txd.hzj.wjlp.http.academy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：09:23
<<<<<<< HEAD
 * 描述：无界书院
=======
 * 描述：xfte书院
>>>>>>> master
 * ===============Txunda===============
 */

class Academy {

    private String url = Config.BASE_URL + "Academy/";

    /**
<<<<<<< HEAD
     * 无界书院首页
=======
     * xfte书院首页
>>>>>>> master
     *
     * @param p          页码
     * @param ac_type_id 类型id
     * @param baseView   回调
     */
    void academyIndex(int p, String ac_type_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("ac_type_id", ac_type_id);
        apiTool2.postApi(url + "academyIndex", params, baseView);
    }

    /**
     * 文章详情
     *
     * @param academy_id 文章id
     * @param baseView   回调
     */
    void academyInfo(String academy_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("academy_id", academy_id);
        apiTool2.postApi(url + "academyInfo", params, baseView);
    }

}
