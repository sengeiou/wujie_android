package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.share.ShareBeBackListener;
import com.ants.theantsgo.share.ShareForApp;
import com.ants.theantsgo.tools.CheckAppExist;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.GroupBuyOrder;
import com.txd.hzj.wjlp.http.user.UserPst;

import java.util.Map;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：上午 10:12
 * 描述：分享(5-4商品详情分享)
 */
public class ToShareAty extends BaseAty {

    private String link = Config.BASE_URL;
    private String title = "无界优品";
    private String pic = "";
    private String context = "无界优品";
    private String id = "";

    private String shareType = ""; // 分享类别：1微信 2微博 3qq 4微信朋友圈 5QQ空间
    private String shareUrl = "";
    private UserPst userPst;

    private String shapetype = "";
    /**
     * 1 商品 2商家 3书院 4红包 5其他(个人中心)
     */
    private String type;

    private boolean isSharing;  //是否调起了分享。如果调起分享，这个值为true。
    private boolean isResume;  //Activity是否处于前台。
    //    private ShareForApp.StatusForShare mStatusForShare;

    @ViewInject(R.id.share_to_sine_tv)
    private TextView share_to_sine_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @OnClick({R.id.share_to_wachar, R.id.share_to_qq, R.id.share_to_sine, R.id.share_to_WechatMoments, R.id.share_to_QZone})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_to_wachar: // 微信
                L.e("微信");
                shareType = "1";
//                isSharing = true;
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mm")) {
                    showErrorTip("请安装微信");
                    break;
                }
                shareForApp(Wechat.NAME);
                break;
            case R.id.share_to_WechatMoments: // 微信朋友圈
                L.e("微信朋友圈");
                shareType = "4";
//                isSharing = true;
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mm")) {
                    showErrorTip("请安装微信");
                    break;
                }
                shareForApp(WechatMoments.NAME);
                break;
            case R.id.share_to_qq:// QQ
                shareType = "3";
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mobileqq")) {
                    showErrorTip("请安装QQ");
                    break;
                }
                shareForApp(QQ.NAME);
                break;
            case R.id.share_to_QZone:// QQ空间
                shareType = "5";
                if (!CheckAppExist.getInstancei().isAppAvilible(this, "com.tencent.mobileqq")) {
                    showErrorTip("请安装QQ");
                    break;
                }
                shareForApp(QZone.NAME);
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
        if (!link.contains("://")) {
            GroupBuyOrder.shareurl(link, id, this);
        } else {
            shareUrl = link;
        }
    }


    @Override
    protected void requestData() {
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("mkShareUrl")) {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            JSONObject object = jsonObject.getJSONObject("data");
            shareUrl = object.getString("url");
        }

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);

    }

    /**
     * 分享
     *
     * @param name 分享平台
     */
    private void shareForApp(String name) {

        if(Config.isLogin() && shareUrl.contains("http://api")){
            shareUrl=shareUrl.replace("api","www");
        }
        if (Config.isLogin()&&!shareUrl.contains("invite_code")) {
            if (shareUrl.contains(".html")) {
                shareUrl = shareUrl.replace(".html", "");
            } else if (shareUrl.contains(".htm")) {
                shareUrl = shareUrl.replace(".htm", "");
            }
            String invite_code = PreferencesUtils.getString(AppManager.getInstance().getTopActivity(), "invite_code", "");
            shareUrl = shareUrl + "/invite_code/" + invite_code + ".html";
        }
        ShareForApp shareForApp = new ShareForApp(name, pic, title, context, shareUrl, new ShareBeBackListener() {
            @Override
            public void beBack(ShareForApp.PlatformForShare platformForShare, ShareForApp.StatusForShare statusForShare, int code) {
//                mStatusForShare = statusForShare;

                if (L.isDebug) {
                    showErrorTip("~~~~~~~从微信返回到应用~~~~~~~~~~``");
                }
                switch (statusForShare) {
                    case Success:
                        userPst.shareBack(shareType, context, id, type, shareUrl);
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

    @Override
    protected void onResume() {
        super.onResume();
//        isSharing = false;
        isResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if (isSharing) {
//            isSharing = false;
//            //这里要延时0.2秒在判断是否回调了onResume，因为onRestart在onResume之前执行。
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // 如果0.2秒后没有调用onResume，则认为是分享成功并且留着微信。
//                    if (!isResume) {
//                        if (userPst == null) { // 判断对象是否为空，防止空指针报错
//                            userPst = new UserPst(ToShareAty.this);
//                        }
//                        if (mStatusForShare != null) {
//                            switch (mStatusForShare) {
//                                case Error:
//                                    showErrorTip("分享失败");
//                                    break;
//                                case Cancel:
//                                    showErrorTip("分享取消");
//                                    break;
//                                case Success:
//                        if (L.isDebug) {
//                            showErrorTip("0.2s分享成功");
//                        }
//                        userPst.shareBack(shareType, context, id, type, link);
//                        showRightTip("分享成功");
//                        finish();
//                                    break;
//                            }
//                        }

//                    }
//                }
//            }, 200);
//        }
    }
}