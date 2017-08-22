package com.txd.hzj.wjlp.http.user;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

import java.io.File;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/22 0022
 * 时间：15:10
 * 描述：
 * ===============Txunda===============
 */

public class UserPst extends BasePresenter {
    private User user;

    public UserPst(BaseView baseView) {
        super(baseView);
        user = new User();
    }

    /**
     * 用户信息
     */
    public void userInfo() {
        baseView.showDialog();
        user.userInfo(baseView);
    }

    public void editInfo(String nickname, String sex, String email, String province_id, String city_id, String area_id,
                         String street_id, File head_pic) {
        baseView.showDialog();
        user.editInfo(nickname, sex, email, province_id, city_id, area_id, street_id, head_pic, baseView);
    }


    public void setting(){
        baseView.showDialog();
        user.setting(baseView);
    }

}
