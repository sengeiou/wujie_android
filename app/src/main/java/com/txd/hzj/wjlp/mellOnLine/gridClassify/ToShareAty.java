package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.ants.theantsgo.share.ShareBeBackListener;
import com.ants.theantsgo.share.ShareForApp;
import com.ants.theantsgo.tools.CheckAppExist;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

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

    private String link = "www.baidu.com";
    private Bitmap bitmap;
    private String uid = "";
    private String from = "1";
    private String title = "欢迎下载无界优品";


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
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mm")) {
                    showErrorTip("请安装微信");
                    break;
                }
                shareForApp(Wechat.NAME);
                break;
            case R.id.share_to_qq:// QQ
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mobileqq")) {
                    showErrorTip("请安装QQ");
                    break;
                }
                shareForApp(QQ.NAME);
                break;
            case R.id.share_to_sine:// 新浪
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
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
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
        if (from.equals("3")) {
            title = getIntent().getStringExtra("title");
        }
        ShareForApp shareForApp = new ShareForApp(name, bitmap, "来自无界优品的分享", title,
                link, new ShareBeBackListener() {
            @Override
            public void beBack(ShareForApp.PlatformForShare platformForShare, ShareForApp.StatusForShare
                    statusForShare, int code) {
                switch (statusForShare) {
                    case Success:
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
        shareForApp.toShare();
    }
}
