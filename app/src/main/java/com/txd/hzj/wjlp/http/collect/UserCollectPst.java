package com.txd.hzj.wjlp.http.collect;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/30 0030
 * 时间：09:41
 * 描述：会员收藏
 *
 */

public class UserCollectPst extends BasePresenter {

    private UserCollect collect;

    public UserCollectPst(BaseView baseView) {
        super(baseView);
        collect = new UserCollect();
    }

    // 收藏列表
    public void collectList(int p, String type) {
        baseView.showDialog();
        collect.collectList(p, type, baseView);
    }

    // 加入我们的收藏
    public void addCollect(String type, String id_val) {
        baseView.showDialog();
        collect.addCollect(type, id_val, baseView);
    }

    // 取消收藏
    public void delOneCollect(String type, String id_val) {
        baseView.showDialog();
        collect.delOneCollect(type, id_val, baseView);
    }

    // 删除收藏品
    public void delCollect(String collect_ids) {
        baseView.showDialog();
        collect.delCollect(collect_ids, baseView);
    }

}
