package com.txd.hzj.wjlp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.util.StringUtils;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnTouch;
import com.maning.updatelibrary.InstallUtils;
import com.txd.hzj.wjlp.baidu.LocationService;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.UpdataApp;
import com.txd.hzj.wjlp.http.updataApp.UpdataPst;
import com.txd.hzj.wjlp.huanxin.db.InviteMessgeDao;
import com.txd.hzj.wjlp.huanxin.db.UserDao;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;
import com.txd.hzj.wjlp.jpush.JpushSetTagAndAlias;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.mainFgt.CartFgt;
import com.txd.hzj.wjlp.mainFgt.MellOffLineFgt;
import com.txd.hzj.wjlp.mainFgt.MellonLineFgt;
import com.txd.hzj.wjlp.mainFgt.MineFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketZoonAty;
import com.txd.hzj.wjlp.minetoAty.order.GoodLuckOrderDetailsAty;
import com.txd.hzj.wjlp.minetoAty.setting.SetAty;
import com.txd.hzj.wjlp.popAty.WJHatchAty;
import com.txd.hzj.wjlp.popAty.WelfareServiceAty;
import com.txd.hzj.wjlp.tool.NotifyUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：下午 1:16
 * 描述：xfte优品主页
 * ===============Txunda===============
 */
public class MainAty extends BaseAty implements RadioGroup.OnCheckedChangeListener {
    private Bundle bundle;

    @ViewInject(R.id.app_main_rg)
    private RadioGroup app_main_rg;

    /**
     * 线上商城
     */
    @ViewInject(R.id.home_pager_rb)
    private RadioButton home_pager_rb;
    /**
     * 线下商城
     */
    @ViewInject(R.id.mell_offline_rb)
    private RadioButton mell_offline_rb;
    /**
     * 购物车
     */
    @ViewInject(R.id.cart_rb)
    private RadioButton cart_rb;
    /**
     * 我的
     */
    @ViewInject(R.id.mine_rb)
    private RadioButton mine_rb;

    /**
     * 更多
     */
    @ViewInject(R.id.mach_more_iv)
    private ImageView mach_more_iv;
    /**
     * 更多
     */
    @ViewInject(R.id.mach_more_tv)
    private TextView mach_more_tv;

    private int page_index = 0;
    private ArrayList<Fragment> fragments;
    private FragmentChangeManager fragmentChangeManager;

    private PopupWindow mCurPopupWindow;

    // 更新
    private UpdataPst updataPst;

    //========== 环信相关 ==========
    private InviteMessgeDao inviteMessgeDao;
    //========== apk更新 ==========
    private MaterialDialog dialogUpdate;
    private NotifyUtil notifyUtils;

    // TODO========== 百度地图定位服务 ==========
    // TODO========== 百度地图定位服务 ==========
    // TODO========== 百度地图定位服务 ==========
    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        L.e(ToolKit.sHA1(this, "MD5"));
//        L.e(ToolKit.sHA1(this, "SHA1"));


        app_main_rg.setOnCheckedChangeListener(this);
        fragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.main_content,
                fragments);
        //申请权限
        requestSomePermission();
        // 电源管理
//        forPowerManager();
        // 当用户登录到另一个设备或删除时，确保活动不会出现在后台
        keepActivity();
        // 意外弹窗
        showExceptionDialogFromIntent(getIntent());
        inviteMessgeDao = new InviteMessgeDao(this);
        UserDao userDao = new UserDao(this);
        // 注册DemoHelper的广播接收器
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());
        locationService = DemoApplication.getInstance().locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK

        // 极光设置Tag或者别名
        if (Config.isLogin()) {
            JpushSetTagAndAlias.getInstance().setAlias(getApplicationContext());
            JpushSetTagAndAlias.getInstance().setTag(getApplicationContext());
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_main;

    }

    @Override
    protected void initialized() {
        page_index = getIntent().getIntExtra("index", 0);
        MellonLineFgt mellonLineFgt = new MellonLineFgt();
        MellOffLineFgt mellOffLineFgt = new MellOffLineFgt();
        CartFgt cartFgt = new CartFgt();
        MineFgt mineFgt = new MineFgt();
        fragments = new ArrayList<>();
        fragments.add(mellonLineFgt);
        fragments.add(mellOffLineFgt);
        fragments.add(cartFgt);
        fragments.add(mineFgt);
        updataPst = new UpdataPst(this);
    }

    @Override
    protected void requestData() {
        updataPst.toUpdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.isLogin()) {
            switch (page_index) {
                case 0:// 线上商城
                    home_pager_rb.setChecked(true);
                    break;
                case 1:// 线下商城
                    mell_offline_rb.setChecked(true);
                    break;
                case 2:// 购物车
                    cart_rb.setChecked(true);
                    break;
                case 3:// 我的
                    mine_rb.setChecked(true);
                    break;
            }
        } else {
            switch (page_index) {
                case 0:// 线上商城
                case 2:// 购物车
                case 3:// 我的
                    home_pager_rb.setChecked(true);
                    break;
                case 1:// 线下商城
                    mell_offline_rb.setChecked(true);
                    break;
            }
        }

        // 更新未读消息，新增联系人消息
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        // 后台运行时取消监听
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

//        EMClient.getInstance().chatManager().addMessageListener(messageListener);

//        DemoApplication.getInstance().setChatListener(this);

        // 前一次若没定位成功则再次定位，否则再次不定位。。。。。161标识定位成功，162标识对应so包导入出错
        String locType = StringUtils.nullStrToEmpty(DemoApplication.getInstance().getLocInfo().get("locType"));
        if (!locType.equals("161")) {
            locationService.start();
        }
    }

    @Override
    @OnClick({R.id.mach_more_lin_layout,R.id.main_content})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.main_content:
                if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
                    setMoreStatus();
                    mCurPopupWindow.dismiss();
                    mCurPopupWindow = null;
                }

                break;
            case R.id.mach_more_lin_layout:// 更多
                if (mCurPopupWindow == null) {
                    mCurPopupWindow = showPopupWindow(v);
                    mach_more_tv.setText("关闭");
                    mach_more_iv.setImageResource(R.drawable.icon_main_close_hzj);
                } else {
                    setMoreStatus();
                    mCurPopupWindow.dismiss();
                    mCurPopupWindow = null;
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        // 如果PopupWindow不为空，而且显示了，dismiss();
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            setMoreStatus();
            mCurPopupWindow.dismiss();
            mCurPopupWindow = null;
        }

        switch (i) {
            case R.id.home_pager_rb:// 线上商城
                page_index = 0;
                fragmentChangeManager.setFragments(0);
                break;
            case R.id.mell_offline_rb:// 线下商城
                page_index = 1;
                fragmentChangeManager.setFragments(1);
                break;
            case R.id.cart_rb:// 购物车
                if (!Config.isLogin()) {
                    toLogin();
                    break;
                }
                page_index = 2;
                fragmentChangeManager.setFragments(2);
                break;
            case R.id.mine_rb:// 我的
                if (!Config.isLogin()) {
                    toLogin();
                    break;
                }
                page_index = 3;
                fragmentChangeManager.setFragments(3);
                break;
        }
    }

    private long firstTime;

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
            setMoreStatus();
        } else {
            if (System.currentTimeMillis() - firstTime < 1500) {
                hasAnimiation = false;
                AppManager.getInstance().killAllActivity();
            } else {
                firstTime = System.currentTimeMillis();
                showToast("再按一次返回桌面");
            }
        }
    }

    /**
     * 更多
     */
    private void setMoreStatus() {
        mach_more_tv.setText("更多");
        mach_more_iv.setImageResource(R.drawable.icon_main_more);
    }

    /**
     * 更多弹窗
     *
     * @param anchorView 点击的按钮
     * @return PopupWindow
     */

    private PopupWindow showPopupWindow(View anchorView) {
        final View contentView = LayoutInflater.from(anchorView.getContext()).inflate(
                R.layout.main_popup_windeow_layout, null);
        LinearLayout lay = contentView.findViewById(R.id.lay);
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
                    setMoreStatus();
                    mCurPopupWindow.dismiss();
                    mCurPopupWindow = null;
                }
            }
        });

        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayWidth, false);

        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(false);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(false);

//


        /*
         * xfte商城
         */
        contentView.findViewById(R.id.main_wj_shop_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                Bundle mBundle = new Bundle();
//                mBundle.putString("title", "爱心商店");
//                startActivity(OnlineShopAty.class, mBundle);

                mBundle.putInt("type", 10);
                mBundle.putString("title", "xfte商店");
                startActivity(TicketZoonAty.class, mBundle);

                popupWindow.dismiss();
                mCurPopupWindow = null;
            }
        });
        /*
         * 福利社
         */
        contentView.findViewById(R.id.welfare_service_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                startActivity(WelfareServiceAty.class, null);
                popupWindow.dismiss();
                mCurPopupWindow = null;
            }
        });
        /*
         * 上市孵化
         */
        contentView.findViewById(R.id.main_wj_hatch_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                startActivity(WJHatchAty.class, null);
                popupWindow.dismiss();
                mCurPopupWindow = null;
            }
        });
        /*
         * 股东招募
         */
        contentView.findViewById(R.id.recruit_stockholder_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                popupWindow.dismiss();
                mCurPopupWindow = null;
            }
        });
        popupWindow.showAsDropDown(anchorView);
        return popupWindow;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
            mCurPopupWindow= null;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        UpdataApp updataApp = GsonUtil.GsonToBean(jsonStr, UpdataApp.class);
        showAppUpdateDialog(updataApp);
    }
    // ============================== 环信 ==============================
    // Todo 环信=========================================================
    // Todo 环信=========================================================
    // Todo 环信=========================================================
    // Todo 环信=========================================================

    private boolean isExceptionDialogShow = false;

    private android.app.AlertDialog.Builder exceptionBuilder;

    /**
     * 账号异地登录
     */
    public boolean isConflict = false;

    private LocalBroadcastManager broadcastManager;

    private BroadcastReceiver broadcastReceiver;


    /**
     * 账号被移除
     */
    private boolean isCurrentAccountRemoved = false;

    /**
     * check if current user account was remove
     * 检查当前账号是否被移除
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    /**
     * 电源管理
     */
    private void forPowerManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// android 6.0电源管理
            String packageName = getPackageName();// 获取包名
            // 获取电源管理服务
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    // 防止有些设备无法对电量管理服务进行处理(添加try...catch)
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                    L.e("=====", "电量管理");
                }
            }
        }
    }

    /**
     * 当用户登录到另一个设备或删除时，确保活动不会出现在后台
     */
    private void keepActivity() {
        // 当用户登录到另一个设备或删除时，确保活动不会出现在后台
        if (getIntent() != null &&
                (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
            DemoHelper.getInstance().logout(false, null);
            startActivity(new Intent(this, LoginAty.class));
        } else if (getIntent() != null && getIntent().getBooleanExtra("isConflict", false)) {
            startActivity(new Intent(this, LoginAty.class));
        }
    }

    /**
     * 用户意外会话弹窗
     *
     * @param intent 意图
     */
    private void showExceptionDialogFromIntent(Intent intent) {
        EMLog.e("=====mainAty=====", "showExceptionDialogFromIntent");
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
            this.finish();
            startActivity(new Intent(this, LoginAty.class));
        }
    }

    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     * 当用户遇到一些异常时显示对话框:例如在另一个设备上登录，用户删除或禁止用户
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        Config.setLoginState(false);
        PreferencesUtils.putString(MainAty.this, "token", "");
        Map<String, String> map = application.getUserInfo();
        map.clear();
        application.setUserInfo(map);
        exceptionBuilder = null;
        isExceptionDialogShow = false;
        if (!MainAty.this.isFinishing()) {// 主页没有被销毁
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainAty.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(MainAty.this, LoginAty.class);
                        intent.putExtra("type", 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e("=====MainAty=====", "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }

    private int getExceptionMessageId(String exceptionType) {

        int reid = R.string.Network_error;
        switch (exceptionType) {
            case Constant.ACCOUNT_CONFLICT:
                reid = R.string.connect_conflict;
                break;
            case Constant.ACCOUNT_REMOVED:
                reid = R.string.em_user_remove;
                break;
            case Constant.ACCOUNT_FORBIDDEN:
                reid = R.string.user_forbidden;
                break;
        }
        return reid;
    }

    /**
     * 环信联系人监听
     */
    private class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
//                        Toast.makeText(MainAty.this, ChatActivity.activityInstance.getToChatUsername() + st10,
//                                Toast.LENGTH_LONG)
                        //  .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
            updateUnreadAddressLable();
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onFriendRequestAccepted(String username) {
        }

        @Override
        public void onFriendRequestDeclined(String username) {
        }
    }

    private class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {

        }

        @Override
        public void onGroupEvent(int event, String target, final List<String> username) {
            switch (event) {
                case EMMultiDeviceListener.GROUP_LEAVE:
                    ChatActivity.activityInstance.finish();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * update the total unread count
     * 获取全部新朋友数量(通讯录)
     */
    public void updateUnreadAddressLable() {
        int count = getUnreadAddressCountTotal();
    }

    /**
     * get unread event notification count, including application, accepted, etc
     * 获取未读的事件通知数，包括应用程序、消息等
     *
     * @return int
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }

    /**
     * update unread message count
     * 更新未读消息数量
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
    }


    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
//                String action = intent.getAction();
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }
    // ============================== 环信 ==============================

    @Override
    protected void onStop() {
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

    // 注销广播
    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
        }
        unregisterBroadcastReceiver();

    }

    //==================== apk更新 =====================
    // Todo============================================
    // Todo============================================
    // Todo============================================
    // Todo============================================
    // Todo============================================
    private void showAppUpdateDialog(final UpdataApp updataApp) {
        String  code = updataApp.getData().getName();

        Log.d("banben=========",String.valueOf(code));
        if (!code.equals(BuildConfig.VERSION_NAME)) {
            new MikyouCommonDialog(this, "检测到新版本v" + updataApp.getData().getName(), "提示", "立即更新", "稍后更新")
                    .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                        @Override
                        public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int
                                which) {
                            switch (btnType) {
                                case MikyouCommonDialog.NO:// 取消
                                    break;
                                case MikyouCommonDialog.OK:// 更新
                                    showDownloadDialog(updataApp);
                                    break;
                            }
                        }
                    }).showDialog();
        }
    }

    private void showDownloadDialog(UpdataApp appUpdateInfo) {
        dialogUpdate = new MaterialDialog.Builder(MainAty.this)
                .title("正在下载最新版本")
                .content("请稍等")
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .progress(false, 100, false)
                .negativeText("后台下载")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startNotifyProgress();
                    }
                })
                .show();

        new InstallUtils(MainAty.this, appUpdateInfo.getData().getUri(), "xfte优品 " + appUpdateInfo.getData().getMessage(),
                new
                        InstallUtils.DownloadCallBack() {
                            @Override
                            public void onStart() {
                                if (dialogUpdate != null) {
                                    dialogUpdate.setProgress(0);
                                }
                            }

                            @Override
                            public void onComplete(String path) {
                                /*
                                 * 安装APK工具类
                                 * @param context       上下文
                                 * @param filePath      文件路径
                                 * @param authorities   ---------Manifest中配置provider的authorities字段---------
                                 * @param callBack      安装界面成功调起的回调
                                 */
                                InstallUtils.installAPK(MainAty.this, path, getPackageName() + ".fileProvider", new
                                        InstallUtils
                                                .InstallCallBack
                                                () {
                                            @Override
                                            public void onSuccess() {
                                                Toast.makeText(MainAty.this, "正在安装程序", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFail(Exception e) {
                                                Toast.makeText(MainAty.this, "安装失败:" + e.toString(), Toast
                                                        .LENGTH_SHORT).show();
                                            }
                                        });
                                if (dialogUpdate != null && dialogUpdate.isShowing()) {
                                    dialogUpdate.dismiss();
                                }
                                if (notifyUtils != null) {
                                    notifyUtils.setNotifyProgressComplete();
                                    notifyUtils.clear();
                                }
                            }

                            @Override
                            public void onLoading(long total, long current) {
                                int currentProgress = (int) (current * 100 / total);
                                if (dialogUpdate != null) {
                                    dialogUpdate.setProgress(currentProgress);
                                }
                                if (notifyUtils != null) {
                                    notifyUtils.setNotifyProgress(100, currentProgress, false);
                                }
                            }

                            @Override
                            public void onFail(Exception e) {
                                if (dialogUpdate != null && dialogUpdate.isShowing()) {
                                    dialogUpdate.dismiss();
                                }
                                if (notifyUtils != null) {
                                    notifyUtils.clear();
                                }
                            }
                        }).downloadAPK();

    }

    /**
     * 开启通知栏
     */
    private void startNotifyProgress() {
        //设置想要展示的数据内容
        Intent intent = new Intent(this, SetAty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.mipmap.ic_launcher;
        String ticker = "正在下载xfte优品更新包...";
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(this, 0);
        notifyUtils.notify_progress(rightPendIntent, smallIcon, ticker, "xfte优品 下载", "正在下载中...", false, false, false);
    }

    // ====================android M+动态授权====================
    private void requestSomePermission() {

        // 先判断是否有权限。
        if (!AndPermission.hasPermission(MainAty.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.READ_PHONE_STATE) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.CALL_PHONE) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.CAMERA) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                ) {
            // 申请权限。
            AndPermission.with(MainAty.this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                    .send();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(MainAty.this, deniedPermissions)) {
                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(MainAty.this, 300)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };

    // TODO==========定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
    // TODO==========定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
    // TODO==========定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
    // TODO==========定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
    // TODO==========定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
    // TODO==========定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
    // TODO==========定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                Map<String, String> locMap = new HashMap<>();
                StringBuilder sb = new StringBuilder(256);
                sb.append("time : ");
                /*
                 * 时间也可以使用systemClock.elapsedRealtime()方法
                 * 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息

                locMap.put("time", location.getTime());// 时间
                locMap.put("locType", String.valueOf(location.getLocType()));// 类型
                locMap.put("description", location.getLocTypeDescription());// 定位说明
                locMap.put("lat", String.valueOf(location.getLatitude()));// 纬度
                locMap.put("lon", String.valueOf(location.getLongitude()));// 经度
                locMap.put("radius", String.valueOf(location.getRadius()));// 半径
                locMap.put("countryCode", location.getCountryCode());// 国家码
                locMap.put("Country", location.getCountry());// 国家
                locMap.put("cityCode", location.getCityCode());// 城市码
                locMap.put("province", location.getProvince());// 省份
                locMap.put("city", location.getCity());// 城市
                locMap.put("district", location.getDistrict());// 区县
                locMap.put("street", location.getStreet());// 街道
                locMap.put("address", location.getAddrStr());// 地址
                locMap.put("userIndoorState", String.valueOf(location.getUserIndoorState()));// 返回用户室内外判断结果

                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = location.getPoiList().get(i);
                        sb.append(poi.getName()).append(";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                DemoApplication.getInstance().setLocInfo(locMap);
                locationService.stop();
                L.e("======定位结果=====", sb.toString());
                L.e("======定位信息=====", DemoApplication.getInstance().getLocInfo().toString());
                locMap.put("city", location.getCity());// 城市
                locMap.put("district", location.getDistrict());//
                locMap.put("street", location.getStreet());// 街道


            }
        }

    };

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        super.onMessageReceived(messages);

        for (EMMessage message : messages) {
            DemoHelper.getInstance().getNotifier().onNewMsg(message);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 刷新未读消息数量
                updateUnreadLabel();
                L.e("=====主页=====", "回调");
                if (0 == page_index) {
                    ((MellonLineFgt) fragments.get(0)).showOrHindNum(getUnreadMsgCountTotal());
                    return;
                }
                if (1 == page_index) {
//                    ((MellOffLineFgt) fragments.get(1)).showOrHindNum(getUnreadMsgCountTotal());
                    return;
                }
                if (3 == page_index) {
                    ((MineFgt) fragments.get(3)).showOrHindMsg(getUnreadMsgCountTotal());
                }
            }
        });
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                // 刷新未读消息数量
                updateUnreadLabel();

            }
        });
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
    }

    @Override
    public void onMessageDelivered(List<EMMessage> message) {
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
    }


}