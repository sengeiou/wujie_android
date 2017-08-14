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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.ants.theantsgo.util.L;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.maning.updatelibrary.InstallUtils;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.UpdataApp;
import com.txd.hzj.wjlp.http.updataApp.UpdataPst;
import com.txd.hzj.wjlp.huanxin.db.InviteMessgeDao;
import com.txd.hzj.wjlp.huanxin.db.UserDao;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.mainFgt.CartFgt;
import com.txd.hzj.wjlp.mainFgt.MellOffLineFgt;
import com.txd.hzj.wjlp.mainFgt.MellonLineFgt;
import com.txd.hzj.wjlp.mainFgt.MineFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketZoonAty;
import com.txd.hzj.wjlp.minetoAty.SetAty;
import com.txd.hzj.wjlp.popAty.WJHatchAty;
import com.txd.hzj.wjlp.popAty.WelfareServiceAty;
import com.txd.hzj.wjlp.tool.NotifyUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：下午 1:16
 * 描述：无界优品主页
 * ===============Txunda===============
 */
public class MainAty extends BaseAty implements RadioGroup.OnCheckedChangeListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_main_rg.setOnCheckedChangeListener(this);
        fragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.main_content,
                fragments);
        L.e("=====手机厂商=====", android.os.Build.BRAND);


        //申请权限
        requestSomePermission();

        // 电源管理
        forPowerManager();
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
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_main;
    }

    @Override
    protected void initialized() {
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
//        updataPst.toUpdata();
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

        EMClient.getInstance().chatManager().addMessageListener(messageListener);

    }

    @Override
    @OnClick({R.id.mach_more_lin_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
            return;
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
                    startActivity(LoginAty.class, null);
                    break;
                }
                page_index = 2;
                fragmentChangeManager.setFragments(2);
                break;
            case R.id.mine_rb:// 我的
                if (!Config.isLogin()) {
                    startActivity(LoginAty.class, null);
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
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
        /*
         * 无界商城
         */
        contentView.findViewById(R.id.main_wj_shop_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                Bundle mBundle = new Bundle();
//                mBundle.putString("title", "爱心商店");
//                startActivity(OnlineShopAty.class, mBundle);

                mBundle.putInt("type", 10);
                mBundle.putString("title", "无界商店");
                startActivity(TicketZoonAty.class, mBundle);

                popupWindow.dismiss();
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
            }
        });
        popupWindow.showAsDropDown(anchorView);
        return popupWindow;
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        UpdataApp updataApp = GsonUtil.GsonToBean(jsonStr, UpdataApp.class);
        showAppUpdateDialog(updataApp);
        L.e("=====更新=====", updataApp.toString());
    }
    // ============================== 环信 ==============================
    // Todo 环信=========================================================
    // Todo 环信=========================================================
    // Todo 环信=========================================================
    // Todo 环信=========================================================

    private boolean isExceptionDialogShow = false;

    private android.app.AlertDialog.Builder exceptionBuilder;

    // user logged into another device
    public boolean isConflict = false;

    private LocalBroadcastManager broadcastManager;

    private BroadcastReceiver broadcastReceiver;


    // user account was removed
    private boolean isCurrentAccountRemoved = false;

    /**
     * check if current user account was remove
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
        if (!MainAty.this.isFinishing()) {
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
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainAty.this, LoginAty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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
                        Toast.makeText(MainAty.this, ChatActivity.activityInstance.getToChatUsername() + st10,
                                Toast.LENGTH_LONG)
                                .show();
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
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
//                if (count > 0) {
//                    unreadAddressLable.setVisibility(View.VISIBLE);
//                } else {
//                    unreadAddressLable.setVisibility(View.INVISIBLE);
//                }
            }
        });
    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return int
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
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
    };

    /**
     * 刷新UI
     */
    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                // 刷新未读消息数量
                updateUnreadLabel();

            }
        });
    }

    /**
     * update unread message count
     * 更新未读消息数量
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();

//        if (count > 0) {// 显示消息数量TextView
//            unreadLabel.setText(String.valueOf(count));
//            unreadLabel.setVisibility(View.VISIBLE);
//        } else {
//            unreadLabel.setVisibility(View.INVISIBLE);
//        }
    }

    /**
     * get unread message count
     * 获取未读消息数量
     *
     * @return int
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
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
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
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
        int code = Integer.parseInt(updataApp.getData().getCode());
        if (code > BuildConfig.VERSION_CODE) {
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

        new InstallUtils(MainAty.this, appUpdateInfo.getData().getUri(), "无界优品 " + appUpdateInfo.getData().getMessage(),
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
        String ticker = "正在下载无界优品更新包...";
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(this, 0);
        notifyUtils.notify_progress(rightPendIntent, smallIcon, ticker, "无界优品 下载", "正在下载中...", false, false, false);
    }

    // ====================android M+动态授权====================
    private void requestSomePermission() {

        // 先判断是否有权限。
        // !AndPermission.hasPermission(MainAty.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
        if (!AndPermission.hasPermission(MainAty.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.READ_PHONE_STATE) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.CALL_PHONE) ||
                !AndPermission.hasPermission(MainAty.this, Manifest.permission.CAMERA)
                ) {
            // 申请权限。
            AndPermission.with(MainAty.this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA)
                    .send();
        }
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
}