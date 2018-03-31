package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.View;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.share.ShareBeBackListener;
import com.ants.theantsgo.share.ShareForApp;
import com.ants.theantsgo.tools.CheckAppExist;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：上午 10:12
 * 描述：分享(5-4商品详情分享)
 * ===============Txunda===============
 */
public class ToShareAty extends BaseAty {

    private String link = Config.BASE_URL;
    private String title = "无界优品";
    private String pic = "";
    private String context = "无界优品";
    private String id = "";

    private String shareType = "";

    private UserPst userPst;

    private String shapetype = "";
    /**
     * 1 商品 2商家 3书院 4红包 5其他(个人中心)
     */
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @OnClick({R.id.share_to_wachar, R.id.share_to_qq, R.id.share_to_sine})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_to_wachar:// 微信
                L.e("微信");
                shareType = "1";
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mm")) {
                    showErrorTip("请安装微信");
                    break;
                }
                shareForApp(Wechat.NAME);
                break;
            case R.id.share_to_qq:// QQ
                shareType = "3";
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mobileqq")) {
                    showErrorTip("请安装QQ");
                    break;
                }
                shareForApp(QQ.NAME);
                break;
            case R.id.share_to_sine:// 新浪
                shareType = "2";
                shareForApp(SinaWeibo.NAME);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_to_share_hzj;
    }

    @Override
    protected void initialized() {
        title = getIntent().getStringExtra("title");
        pic = getIntent().getStringExtra("pic");
        link = getIntent().getStringExtra("url");
        context = getIntent().getStringExtra("context");
        type = getIntent().getStringExtra("Shapetype");
        id = getIntent().getStringExtra("id");
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {

    }

    /**
     * 分享
     *
     * @param name 分享平台
     */
    private void shareForApp(String name) {

        ShareForApp shareForApp = new ShareForApp(name, pic, title, context,
                link, new ShareBeBackListener() {
            @Override
            public void beBack(ShareForApp.PlatformForShare platformForShare, ShareForApp.StatusForShare
                    statusForShare, int code) {
                switch (statusForShare) {
                    case Success:
                        userPst.shareBack(shareType, context, id, type, link);
                        showRightTip("分享成功");
                        finish();
                        break;
                    case Error:
                        showErrorTip("分享失败");
                        break;
                    case Cancel:
                        showErrorTip("分享取消");
                        break;
                }
            }
        });
        shareForApp.toShareWithPicUrl();
    }
}
