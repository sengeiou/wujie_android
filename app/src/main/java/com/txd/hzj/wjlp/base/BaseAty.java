package com.txd.hzj.wjlp.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.systemBarUtil.ImmersionBar;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.txdHxListener.ChatListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;
import com.txd.hzj.wjlp.jpush.JpushSetTagAndAlias;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.mellOnLine.AllClassifyAty;
import com.txd.hzj.wjlp.mellOnLine.MessageAty;
import com.txd.hzj.wjlp.mellOnLine.ScanAty;
import com.txd.hzj.wjlp.mellOnLine.SearchAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsAttributeAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.ToShareAty;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：15:08
 * 描述：所有Activity的基类
 * ===============Txunda===============
 */

public abstract class BaseAty extends BaseActivity implements ChatListener {
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            if (ImmersionBar.isSupportStatusBarDarkFont())
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true).init();
            else
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
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
     * 评价
     *
     * @param v View
     */
//    public void toEvaluate(View v) {
//
//    }

    /**
     * 商品属性
     * goods_id - minfo_id
     *
     * @param v View
     */
    public void toAttrs(View v, int from, String type, String goods_id, String imageurl,
                        String price,
//                        String goods_attr,
//                        ArrayList<GoodsAttrs> list,
//            , ArrayList<GoodsAttrs.product> list_p,
                        String group_buy_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        bundle.putString("type", type);
        bundle.putString("goods_id", goods_id);
        bundle.putString("group_buy_id", group_buy_id);
        bundle.putString("imageurl", imageurl);
        bundle.putString("price", price);
//        bundle.putString("goods_attr", goods_attr);
//        bundle.putParcelableArrayList("list", list);
//        bundle.putParcelableArrayList("list_p", list_p);
        startActivity(GoodsAttributeAty.class, bundle);
    }

    public void toAttrs(View v, int from, String type, String goods_id, String imageurl,
                        String price,
//                        ArrayList<GoodsAttrs> list,
//            , ArrayList<GoodsAttrs.product> list_p,
                        String group_buy_id, String goods_attr,String goods_val,String is_attr) {
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
//        bundle.putParcelableArrayList("list", list);
//        bundle.putParcelableArrayList("list_p", list_p);
        startActivity(GoodsAttributeAty.class, bundle);
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
     */
    public void toShare(String title, String pic, String url, String context, String id, String Shapetype) {
        if (!Config.isLogin()) {
            toLogin();
            return;
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
                    L.e("=====退出登录=====", "成功");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toLogin();
                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {
                    L.e("=====退出登录=====", "退出中");
                }

                @Override
                public void onError(int code, String message) {
                    L.e("=====退出登录=====", "失败：" + code + "-----" + message);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DemoApplication.getInstance().removeLisetener();
    }

    public void call(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        startActivity(intent);
    }
}
