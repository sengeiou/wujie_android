package com.txd.hzj.wjlp.http.index;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/14 0014
 * 时间：09:46
 * 描述：
 * ===============Txunda===============
 */

public class IndexPst extends BasePresenter {
    private IndexPage indexPage;

    public IndexPst(BaseView baseView) {
        super(baseView);
        indexPage = new IndexPage();
    }

    // 首页
    public void index(String lng ,String lat) {
        baseView.showDialog();
        indexPage.index(lng,lat,baseView);
    }

    // 头条列表
    public void headLineList(int p, boolean show) {
        if (show)
            baseView.showDialog();
        indexPage.headLineList(p, baseView);
    }

    // 头条信息
    public void headInfo(String headlines_id) {
        baseView.showDialog();
        indexPage.headInfo(headlines_id, baseView);
    }

}
