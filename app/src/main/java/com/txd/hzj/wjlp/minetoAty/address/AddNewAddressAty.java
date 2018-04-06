package com.txd.hzj.wjlp.minetoAty.address;

import com.ants.theantsgo.util.L;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.address.AddressPst;

/**
 * 开发者： Txd_WangJJ
 * 创建时间： 2018/4/6 006 20:26:36.
 * 功能描述：
 * 联系方式： jingjie.office@qq.com
 */

public class AddNewAddressAty extends BaseAty {

    private AddressPst addressPst;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_add_new_address;
    }

    @Override
    protected void initialized() {
        addressPst = new AddressPst(this);
        addressPst.getRegion("");
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("wang", "ajksflahslakhflakhflkasfhlkas:::::::::" + jsonStr);
        if (requestUrl.contains("getRegion")){

        }
    }
}
