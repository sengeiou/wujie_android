package com.txd.hzj.wjlp.http.index;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/14 0014
 * 时间：09:23
 * 描述：App首页，线上商城
 */

class IndexPage {

    private String url = Config.BASE_URL + "Index/";

    void index(String lng, String lat, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("lng", lng);
        params.addBodyParameter("lat", lat);
        apiTool2.postApi(url + "index", params, baseView);
    }

    /**
     * 头条列表
     *
     * @param p        分页
     * @param baseView 回调
     */
    void headLineList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "headLineList", params, baseView);
    }

    /**
     * 头条详情
     *
     * @param headlines_id id
     * @param baseView     回调
     */
    void headInfo(String headlines_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("headlines_id", headlines_id);
        apiTool2.postApi(url + "headInfo", params, baseView);
    }

    /**
     * 上传图片
     *
     * @param save_path 上传图片保存的路径
     * @param baseView  回调
     * @param logoFiles 上传的图片File
     */
    void uploadImage(String save_path, BaseView baseView, File... logoFiles) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("save_path", save_path);
        for (int i = 0; i < logoFiles.length; i++) {
            params.addBodyParameter("logo_" + i, logoFiles[i]);
        }
        apiTool2.postApi(url + "upload", params, baseView);
    }

}
