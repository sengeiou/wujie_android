package com.txd.hzj.wjlp.http.user;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.tools.RegexUtils;
import com.ants.theantsgo.util.L;

import java.io.File;
import java.util.List;

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

    // 编辑个人信息
    public void editInfo(String nickname, String sex, String email, String province_id, String city_id, String area_id,
                         String street_id, File head_pic) {
        baseView.showDialog();
        user.editInfo(nickname, sex, email, province_id, city_id, area_id, street_id, head_pic, baseView);
    }

    // 实名认证
    public void addAuth(String real_name, String id_card_num, File id_card_pic) {

        if (real_name.equals("")) {
            baseView.onErrorTip("请输入真实姓名");
            return;
        }
        if (id_card_num.equals("")) {
            baseView.onErrorTip("请输入身份证号");
            return;
        }

        if (id_card_pic == null || !id_card_pic.exists()) {
            baseView.onErrorTip("请上传身份证照片");
            return;
        }

        baseView.showDialog();
        user.addAuth(real_name, id_card_num, id_card_pic, baseView);
    }

    // 修改密码
    public void changePassword(String oldPassword, String newPassword, String rePassword) {

        if (oldPassword.equals("")) {
            baseView.onErrorTip("请输入旧密码");
            return;
        }
        if (newPassword.equals("")) {
            baseView.onErrorTip("请输入新密码");
            return;
        }
        if (rePassword.equals("")) {
            baseView.onErrorTip("请确认密码");
            return;
        }
        if (!newPassword.equals(rePassword)) {
            baseView.onErrorTip("请核对密码");
            return;
        }

        baseView.showDialog();
        user.changePassword(oldPassword, newPassword, rePassword, baseView);

    }

    // 设置
    public void setting() {
        baseView.showDialog();
        user.setting(baseView);
    }

    // 获取实名认证信息
    public void getAuth() {
        baseView.showDialog();
        user.getAuth(baseView);
    }

    // 评价列表
    public void myCommentList(int p) {
        baseView.showDialog();
        user.myCommentList(p, baseView);
    }

    // 足迹
    public void myfooter(int p, String type) {
        baseView.showDialog();
        user.myfooter(p, type, baseView);
    }

    // 我的购物券
    public void vouchersList(int p) {
        baseView.showDialog();
        user.vouchersList(p, baseView);
    }

    // 我的购物券
    public void vouchersLog(int p) {
        baseView.showDialog();
        user.vouchersLog(p, baseView);
    }

    // 修改支付密码
    public void rePayPwd(String newPayPwd, String rePayPwd, String oldPayPwd) {
        baseView.showDialog();
        user.rePayPwd(newPayPwd, rePayPwd, oldPayPwd, baseView);
    }

    // 换绑手机号
    public void changePhone(String newPhone, String verify) {
        if (verify.equals("")) {
            baseView.onErrorTip("请输入验证码");
            return;
        }
        baseView.showDialog();
        user.changePhone(newPhone, verify, baseView);
    }

    // 设置登录密码
    public void setPassword(String newPassword, String rePassword) {
        baseView.showDialog();
        user.setPassword(newPassword, rePassword, baseView);
    }

    // 设置支付密码
    public void setPayPwd(String newPayPwd, String rePayPwd) {
        baseView.showDialog();
        user.setPayPwd(newPayPwd, rePayPwd, baseView);
    }

    // 个人中心
    public void userCenter() {
        baseView.showDialog();
        user.userCenter(baseView);
    }

    // 卡券包--优惠券
    public void myTicket() {
        baseView.showDialog();
        user.myTicket(baseView);
    }

    // 获取经营范围
    public void getRange() {
        baseView.showDialog();
        user.getRange(baseView);
    }

    // 会员推荐商户
    public void merchantRefer(String name, String range_id, String link_man, String link_phone, String job,
                              String tmail_url, String jd_url, String other_url, String product_desc,
                              List<File> product_pic, File business_license, List<File> other_license) {
        baseView.showDialog();
        if (name.equals("")) {
            baseView.onErrorTip("请输入商店名");
            return;
        }
        if (range_id.equals("")) {
            baseView.onErrorTip("请选择经营范围");
            return;
        }
        if (link_man.equals("")) {
            baseView.onErrorTip("请输入联系人姓名");
            return;
        }
        if (!RegexUtils.checkPhone(link_phone)) {
            baseView.onErrorTip("请输入联系人手机号");
            return;
        }

        if (job.equals("")) {
            baseView.onErrorTip("请输入职位");
            return;
        }


        if (product_pic.isEmpty()) {
            baseView.onErrorTip("请上传商品图片");
            return;
        }

        if (business_license == null || !business_license.exists()) {
            baseView.onErrorTip("请上传营业执照");
            return;
        }

        L.e("=====产品=====", product_pic.toString());

        user.merchantRefer(name, range_id, link_man, link_phone, job, tmail_url, jd_url,
                other_url, product_desc, product_pic, business_license, other_license, baseView);
    }

    // 获取推荐商家列表
    public void referList() {
        baseView.showDialog();
        user.referList(baseView);
    }

    // 会员成长
    public void userDevelop() {
        baseView.showDialog();
        user.userDevelop(baseView);
    }

    // 成长明细
    public void userDevelopLog(int p) {
        baseView.showDialog();
        user.userDevelopLog(p, baseView);
    }

    // 增加明细
    public void addPoint(String reason, String get_point) {
        baseView.showDialog();
        user.addPoint(reason, get_point, baseView);
    }

    // 会员等级
    public void userRank() {
        baseView.showDialog();
        user.userRank(baseView);
    }

    // 工作成绩
    public void gradeRank(int p, String city_id, String type, String city_name, boolean show) {
        if (show)
            baseView.showDialog();
        user.gradeRank(p, city_id, type, city_name, baseView);
    }

    // 我的分享
    public void myShare(int p, boolean show) {
        if (show) {
            baseView.showDialog();
        }
        user.myShare(p, baseView);
    }

    // 我的推荐
    public void myRecommend(int p, boolean show) {
        if (show) {
            baseView.showDialog();
        }
        user.myRecommend(p, baseView);
    }

    // 忘记支付密码
    public void resetPayPwd(String phone, String verify, String newPayPwd, String rePayPwd) {
        baseView.showDialog();
        user.resetPayPwd(phone, verify, newPayPwd, rePayPwd, baseView);
    }

    // 积分说明
    public void integralLog() {
        baseView.showDialog();
        user.integralLog(baseView);
    }

    // 分享好友
    public void shareFriend() {
        baseView.showDialog();
        user.shareFriend(baseView);
    }
}
