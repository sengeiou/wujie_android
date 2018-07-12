package com.txd.hzj.wjlp.http.companyDevelop;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/20 0020
 * 时间：17:02
 * 描述：上市孵化(企业孵化)
 */

public class CompanyDevelop {

    private String url = Config.BASE_URL + "CompanyDevelop/";

    /**
     * 企业列表
     *
     * @param p        分页
     * @param baseView 回调
     */
    void companyList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "companyList", params, baseView);
    }

    /**
     * 企业简介
     *
     * @param company_id 企业id
     * @param baseView   回调
     */
    void companyInfo(String company_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("company_id", company_id);
        apiTool2.postApi(url + "companyInfo", params, baseView);
    }

}
