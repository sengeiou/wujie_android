package com.txd.hzj.wjlp.http.index;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/14 0014
 * 时间：09:23
 * 描述：App首页，线上商城
 * ===============Txunda===============
 */

class IndexPage {

    private String url = Config.BASE_URL + "Index/";

    void index(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "index", params, baseView);
    }

}
