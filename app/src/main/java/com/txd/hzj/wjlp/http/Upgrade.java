package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/8 0008
 * 时间：09:44
 * 描述：APP更新模块
 */
public class Upgrade {

    private String url = Config.BASE_URL + "Upgrade/";

    /**
     * 检查更新
     *
     * @param baseView
     */
    public void upgrade(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool = new ApiTool2();
        apiTool.postApi(url + "upgrade", params, baseView);
    }

}
