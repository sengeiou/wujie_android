package com.txd.hzj.wjlp.http.offlineStoreInfo;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * 创建者：Qyl
 * 创建时间：2018/7/24 0024 9:21
 * 功能描述：
 * 联系方式：无
 */
public class StoreInfoPst extends BasePresenter {
    private StoreInfo storeInfo;

    public StoreInfoPst(BaseView baseView) {
        super(baseView);
        storeInfo = new StoreInfo();
    }

    public void offlineStoreInfo(String id){
        storeInfo.offlineStoreInfo(id,baseView);

    }
}
