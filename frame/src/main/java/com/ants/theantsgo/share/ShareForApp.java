package com.ants.theantsgo.share;

import android.graphics.Bitmap;

import com.ants.theantsgo.util.L;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 4:49
 * 描述：分享
 */
public class ShareForApp implements PlatformActionListener {

    /**
     * 分享平台
     */
    public enum PlatformForShare {
        QQ, Qzon, Sine, Wechat, WechatMoments
    }

    /**
     * 分享之后的状态
     */
    public enum StatusForShare {
        Success, Error, Cancel
    }

    /**
     * 分享平台的名称
     */
    private String platFormName;
    /**
     * 分享的图片
     */
    private Bitmap bitmap;
    /**
     * 图片链接
     */
    private String picUrl;

    /**
     * 标题
     */
    private String title;
    /**
     * 分享文本
     */
    private String text;
    /**
     * 标题链接
     */
    private String titleUrl;

    private ShareBeBackListener shareBeBackListener;

    /**
     * 够造函数
     *
     * @param platFormName 平台名称
     * @param bitmap       分享的图片
     * @param title        标题
     * @param text         文本
     * @param titleUrl     标题连接
     */
    public ShareForApp(String platFormName, Bitmap bitmap, String title, String text, String titleUrl,
                       ShareBeBackListener shareBeBackListener) {
        this.platFormName = platFormName;
        this.bitmap = bitmap;
        this.title = title;
        this.text = text;
        this.titleUrl = titleUrl;
        this.shareBeBackListener = shareBeBackListener;
    }

    public ShareForApp(String platFormName, String picUrl, String title, String text, String titleUrl,
                       ShareBeBackListener shareBeBackListener) {
        this.platFormName = platFormName;
        this.picUrl = picUrl;
        this.title = title;
        this.text = text;
        this.titleUrl = titleUrl;
        this.shareBeBackListener = shareBeBackListener;
    }

    public void toShare() {
        Platform.ShareParams sp = new Platform.ShareParams();

        if (!platFormName.equals(SinaWeibo.NAME)) {
            sp.setShareType(Platform.SHARE_WEBPAGE); // 非常重要：一定要设置分享属性
        }

        sp.setTitle(title);
        sp.setText(text);// 分享文本

        if (platFormName.equals(WechatMoments.NAME) || platFormName.equals(Wechat.NAME) ||
                platFormName.equals(SinaWeibo.NAME)) {
            sp.setUrl(titleUrl);
        } else if (platFormName.equals(QQ.NAME) || platFormName.equals(QZone.NAME)) {
            sp.setTitleUrl(titleUrl);
        }
        sp.setImageData(bitmap);
        // 3、非常重要：获取平台对象
        Platform wechathy = ShareSDK.getPlatform(platFormName);
        wechathy.setPlatformActionListener(this); // 设置分享事件回调
        // 执行分享
        wechathy.share(sp);
        L.e("分享");
    }

    public void toShareWithPicUrl() {
        Platform.ShareParams sp = new Platform.ShareParams();

        if (!platFormName.equals(SinaWeibo.NAME)) {
            sp.setShareType(Platform.SHARE_WEBPAGE); // 非常重要：一定要设置分享属性
        }

        sp.setTitle(title);
        if (platFormName.equals(SinaWeibo.NAME)){
            sp.setText(text+titleUrl);
        }else {
            sp.setText(text);// 分享文本
        }

        if (platFormName.equals(WechatMoments.NAME) || platFormName.equals(Wechat.NAME)) {
            sp.setUrl(titleUrl);
        } else if (platFormName.equals(QQ.NAME) || platFormName.equals(QZone.NAME)) {
            sp.setTitleUrl(titleUrl);
        }
        sp.setImageUrl(picUrl);
        // 3、非常重要：获取平台对象
        Platform wechathy = ShareSDK.getPlatform(platFormName);
        wechathy.setPlatformActionListener(this); // 设置分享事件回调
        // 执行分享
        wechathy.share(sp);
        L.e("分享");
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        L.e("成功");
        if (platform.getName().equals(SinaWeibo.NAME)) {// 新浪微博
            shareBeBackListener.beBack(PlatformForShare.Sine, StatusForShare.Success, i);
        } else if (platform.getName().equals(Wechat.NAME)) {// 微信好友
            shareBeBackListener.beBack(PlatformForShare.Wechat, StatusForShare.Success, i);
        } else if (platform.getName().equals(WechatMoments.NAME)) {// 朋友圈
            shareBeBackListener.beBack(PlatformForShare.WechatMoments, StatusForShare.Success, i);
        } else if (platform.getName().equals(QQ.NAME)) {// QQ
            shareBeBackListener.beBack(PlatformForShare.QQ, StatusForShare.Success, i);
        } else if (platform.getName().equals(QZone.NAME)) {// QQ空间
            shareBeBackListener.beBack(PlatformForShare.Qzon, StatusForShare.Success, i);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        L.e("失败");
        if (platform.getName().equals(SinaWeibo.NAME)) {// 新浪微博
            shareBeBackListener.beBack(PlatformForShare.Sine, StatusForShare.Error, i);
        } else if (platform.getName().equals(Wechat.NAME)) {// 微信好友
            shareBeBackListener.beBack(PlatformForShare.Wechat, StatusForShare.Error, i);
        } else if (platform.getName().equals(WechatMoments.NAME)) {// 朋友圈
            shareBeBackListener.beBack(PlatformForShare.WechatMoments, StatusForShare.Error, i);
        } else if (platform.getName().equals(QQ.NAME)) {// QQ
            shareBeBackListener.beBack(PlatformForShare.QQ, StatusForShare.Error, i);
        } else if (platform.getName().equals(QZone.NAME)) {// QQ空间
            shareBeBackListener.beBack(PlatformForShare.Qzon, StatusForShare.Error, i);
        }
    }

    @Override
    public void onCancel(Platform platform, int i) {
        L.e("取消");
        if (platform.getName().equals(SinaWeibo.NAME)) {// 新浪微博
            shareBeBackListener.beBack(PlatformForShare.Sine, StatusForShare.Cancel, i);
        } else if (platform.getName().equals(Wechat.NAME)) {// 微信好友
            shareBeBackListener.beBack(PlatformForShare.Wechat, StatusForShare.Cancel, i);
        } else if (platform.getName().equals(WechatMoments.NAME)) {// 朋友圈
            shareBeBackListener.beBack(PlatformForShare.WechatMoments, StatusForShare.Cancel, i);
        } else if (platform.getName().equals(QQ.NAME)) {// QQ
            shareBeBackListener.beBack(PlatformForShare.QQ, StatusForShare.Cancel, i);
        } else if (platform.getName().equals(QZone.NAME)) {// QQ空间
            shareBeBackListener.beBack(PlatformForShare.Qzon, StatusForShare.Cancel, i);
        }
    }
}
