package com.txd.hzj.wjlp.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.systemBarUtil.ImmersionBar;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.txdHxListener.ChatListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.FirstListBean;
import com.txd.hzj.wjlp.bean.commodity.FirstValBean;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;
import com.txd.hzj.wjlp.jpush.JpushSetTagAndAlias;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.mellonLine.AllClassifyAty;
import com.txd.hzj.wjlp.mellonLine.MessageAty;
import com.txd.hzj.wjlp.mellonLine.ScanAty;
import com.txd.hzj.wjlp.mellonLine.SearchAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.AuctionGoodsDetailsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.GoodsAttributeAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.ToShareAty;
import com.txd.hzj.wjlp.minetoaty.balance.BankCardHzjAty;
import com.txd.hzj.wjlp.minetoaty.dialog.ApprenticeCodeAty;
import com.txd.hzj.wjlp.minetoaty.dialog.RegistrationCodeAty;
import com.txd.hzj.wjlp.minetoaty.mell.QRCodeForMellGoodsAty;
import com.txd.hzj.wjlp.minetoaty.order.GoodLuckOrderDetailsAty;
import com.txd.hzj.wjlp.popAty.GetRedPackageAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：15:08
 * 描述：所有Activity的基类
 */

public abstract class BaseAty extends BaseActivity implements ChatListener {
    private Bundle bundle;

    public static boolean isExit = false; // 判断Activity是否被销毁
    private CommonPopupWindow commonPopupWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 竖屏锁定 除去GoodsAttributeAty和RegistrationCodeAty两个Activity之外都锁定竖屏界面，为兼容Android8.0系统
        // 1GoodsAttributeAty 2RegistrationCodeAty 3AuctionGoodsDetailsAty 4GoodLuckOrderDetailsAty 5ToShareAty 6ApprenticeCodeAty
        if (!(this instanceof GoodsAttributeAty) && !(this instanceof ToShareAty) && !(this instanceof RegistrationCodeAty)
                && !(this instanceof GetRedPackageAty) && !(this instanceof QRCodeForMellGoodsAty) && !(this instanceof ApprenticeCodeAty)
                && !(this instanceof AuctionGoodsDetailsAty) && !(this instanceof BankCardHzjAty) && !(this instanceof GoodLuckOrderDetailsAty)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (L.isDebug) {
            rootText.setText(ToolKit.getClassName(this));
        } else {
            rootText.setVisibility(View.GONE);
        }

    }

    /**
     * 返回
     *
     * @param v 按钮
     */
    public void beBack(View v) {
        finish();
    }

    /**
     * 登录
     */
    public void toLogin() {
        bundle = new Bundle();
        bundle.putInt("type", 1);
        startActivity(LoginAty.class, bundle);
    }

    /**
     * 退出登录，并跳转到登录页
     */
    public void loginoutToLogin() {
        bundle = new Bundle();
        bundle.putInt("type", 0);
        Map<String, String> map = application.getUserInfo();
        map.clear();
        application.setUserInfo(map);
        DemoApplication.getInstance().removeLisetener();
        startActivity(LoginAty.class, bundle);
    }

    /**
     * 修改StatusBar颜色
     *
     * @param vid 标题栏
     */
    public void showStatusBar(int vid) {
        String name = android.os.Build.BRAND;

        if (name.equals("Huawei")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
            } else {
                ImmersionBar.with(this).titleBar(vid).init();
            }
        } else {
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true).init();
            } else {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
            }
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyBoard() {
        // 先隐藏键盘
        ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * @param v
     * @param from
     * @param type         "0"   主界面购物车, "1" 票券 "2" 拼单单独购买 "3" 拼单参团 "4" 参团 "5" 限量购  "6" 限量购 无界预购 "10" 限量购 积分商店 "11" 搭配购
     * @param goods_id
     * @param imageurl
     * @param price
     * @param group_buy_id
     * @param goods_attr
     * @param goods_val
     * @param is_attr
     */
    public void toAttrs(View v, int from, String type, String goods_id, String imageurl,
                        String price,
                        String group_buy_id, List<FirstListBean> goods_attr, List<FirstValBean> goods_val, String is_attr) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        bundle.putString("type", type);
        bundle.putString("goods_id", goods_id);
        bundle.putString("group_buy_id", group_buy_id);
        bundle.putString("imageurl", imageurl);
        bundle.putString("price", price);
        bundle.putSerializable("goods_attr_Serializable", (Serializable) goods_attr);
        bundle.putSerializable("goods_val_Serializable", (Serializable) goods_val);
        bundle.putString("is_attr", is_attr);
        startActivityForResult(GoodsAttributeAty.class, bundle, 1000);
    }

    /**
     * 体验拼单
     *
     * @param v
     * @param from
     * @param type         "0"   主界面购物车, "1" 票券 "2" 拼单单独购买 "3" 拼单参团 "4" 参团 "5" 限量购  "6" 限量购 无界预购 "10" 限量购 积分商店 "11" 搭配购
     * @param goods_id
     * @param imageurl
     * @param price
     * @param group_buy_id
     * @param goods_attr
     * @param goods_val
     * @param is_attr
     * @param groupType
     */
    public void toExAttars(View v, int from, String type, String goods_id, String imageurl,
                           String price,
                           String group_buy_id, List<FirstListBean> goods_attr, List<FirstValBean> goods_val, String is_attr, String groupType) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        bundle.putString("type", type);
        bundle.putString("goods_id", goods_id);
        bundle.putString("group_buy_id", group_buy_id);
        bundle.putString("imageurl", imageurl);
        bundle.putString("price", price);
        bundle.putSerializable("goods_attr_Serializable", (Serializable) goods_attr);
        bundle.putSerializable("goods_val_Serializable", (Serializable) goods_val);
        bundle.putString("is_attr", is_attr);
        bundle.putString("group_type", groupType);
        startActivityForResult(GoodsAttributeAty.class, bundle, 1000);
    }

    /**
     * @param v
     * @param from
     * @param type         "0"   主界面购物车, "1" 票券 "2" 拼单单独购买 "3" 拼单参团 "4" 参团 "5" 限量购  "6" 限量购 无界预购 "10" 限量购 积分商店 "11" 搭配购
     * @param goods_id
     * @param imageurl
     * @param price
     * @param group_buy_id
     * @param goods_attr
     * @param goods_val
     * @param is_attr
     */
    public void toAttrs(View v, int from, String type, String goods_id, String imageurl,
                        String price,
                        String group_buy_id, String goods_attr, String goods_val, String is_attr) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        bundle.putString("type", type);
        bundle.putString("goods_id", goods_id);
        bundle.putString("group_buy_id", group_buy_id);
        bundle.putString("imageurl", imageurl);
        bundle.putString("price", price);
        bundle.putString("goods_attr", goods_attr);
        bundle.putString("goods_val", goods_val);
        bundle.putString("is_attr", is_attr);
        startActivityForResult(GoodsAttributeAty.class, bundle, 1000);
    }

    /**
     * 消息
     *
     * @param v View
     */
    public void toMessage(View v) {
        if (!Config.isLogin()) {
            toLogin();
            return;
        }
        startActivity(MessageAty.class, null);
    }

    /**
     * 分类
     *
     * @param v View
     */
    public void toClassify(View v) {
        startActivity(AllClassifyAty.class, null);
    }

    public void toClassify(View v, String cate_id) {
        Bundle bundle = new Bundle();
        bundle.putString("cate_id", cate_id);
        startActivity(AllClassifyAty.class, bundle);
    }

    /**
     * 搜索
     *
     * @param v View
     */
    public void toSearch(View v) {
        startActivity(SearchAty.class, null);
    }

    /**
     * 分享
     *
     * @param title
     * @param pic
     * @param url
     * @param context
     * @param id
     * @param Shapetype
     */
    public void toShare(String title, String pic, String url, String context, String id, String Shapetype) {
        if (!Config.isLogin()) {
            //            toLogin();
            //            return;
            Toast.makeText(BaseAty.this, "您未登陆，这会导致无法获得分享收益!", Toast.LENGTH_LONG).show();
        }
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("pic", pic);
        bundle.putString("url", url);
        bundle.putString("context", context);
        bundle.putString("id", id);
        bundle.putString("Shapetype", Shapetype);
        startActivity(ToShareAty.class, bundle);
    }

    /**
     * 扫一扫
     */
    public void toScan(View v) {
        startActivity(ScanAty.class, null);
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        if (error.get("code").equals("-1")) {// 登录失效
            // 删除极光推送之前设置好的Tag或Alias
            JpushSetTagAndAlias.getInstance().delAlias(getApplicationContext());
            JpushSetTagAndAlias.getInstance().delTag(getApplicationContext());
            // 清除掉本地的token
            PreferencesUtils.putString(this, "token", "");
            // 友盟统计signout统计
            MobclickAgent.onProfileSignOff();
            Config.setLoginState(false);
            // 环信退出登录
            DemoHelper.getInstance().logout(true, new EMCallBack() {

                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toLogin();
                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {
                }

                @Override
                public void onError(int code, String message) {
                }
            });
        }
    }

    /**
     * get unread message count
     * 获取未读消息数量(环信获取消息数量)
     *
     * @return int
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    /**
     * 回到主页
     *
     * @param index 第几页，从0开始
     */
    public void backMain(int index) {
        if (index == 2 && !Config.isLogin()) {
            toLogin();
            return;
        }
        bundle = new Bundle();
        bundle.putInt("index", index);
        startActivity(MainAty.class, bundle);
    }

    /**
     * 聊天界面
     *
     * @param easemob_account 环信账号
     * @param head_pic        头像
     * @param nickname        昵称
     */
    public void toChat(String easemob_account, String head_pic, String nickname) {
        if (!Config.isLogin()) {
            toLogin();
            return;
        }
        if (TextUtils.isEmpty(easemob_account)) {
            showErrorTip("对方不在线");
            return;
        }
        String my_easemob_account = application.getUserInfo().get("easemob_account");
        if (easemob_account.equals(my_easemob_account)) {
            showErrorTip("自己不能和自己聊天");
            return;
        }
        bundle = new Bundle();
        bundle.putString("userId", easemob_account);// 对方环信账号
        bundle.putString("userHead", head_pic);// 对方头像
        bundle.putString("userName", nickname);// 对方昵称
        bundle.putString("myName", application.getUserInfo().get("nickname"));// 我的昵称
        bundle.putString("myHead", application.getUserInfo().get("head_pic"));// 我的头像
        startActivity(ChatActivity.class, bundle);
    }


    @Override
    public void onMessageReceived(List<EMMessage> var1) {
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> var1) {
    }

    @Override
    public void onMessageRead(List<EMMessage> var1) {
    }

    @Override
    public void onMessageDelivered(List<EMMessage> var1) {
    }

    @Override
    public void onMessageChanged(EMMessage var1, Object var2) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        DemoApplication.getInstance().setChatListener(this);
        Glide.with(this).resumeRequests();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
        Glide.with(this).pauseRequests();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //        DemoApplication.getInstance().removeLisetener(); 将该方法移动到退出账户
    }

    public void call(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        startActivity(intent);
    }

    /**
     * 重置字体大小，防止APP内字体随系统字体改变
     * 该段代码主要是适配API 8.0 8.0以下直接在Application中设置即可
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    @Override
    public void onException(Exception exception) {
        //        showProgressContent();
        // TODO 此处尽量不要统统的在Exception中关闭，否则有点页面正常关闭了也会造成闪退回桌面的感觉，
        // TODO Android系统在启动的时候也会打印出错误日志，所以有点无关紧要的错也不一定影响正常运行
        if (this instanceof TicketGoodsDetialsAty || this instanceof GoodLuckOrderDetailsAty || this instanceof LimitGoodsAty) {
            showTip(R.mipmap.icon_error_tip, "数据请求有误,暂不能预览!");
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    finish();
                }
            };
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 3 * 1000);
        }
        super.onException(exception);
    }
}
