package com.txd.hzj.wjlp.http.article;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/18 0018
 * 时间：10:09
 * 描述：
 * ===============Txunda===============
 */

public class ArticlePst extends BasePresenter {
    private Article article;

    public ArticlePst(BaseView baseView) {
        super(baseView);
        article = new Article();
    }
    public void getArticle(String type){
        baseView.showDialog();
        article.getArticle(type,baseView);
    }
}
