package com.txd.hzj.wjlp.tool;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.maning.updatelibrary.InstallUtils;
import com.txd.hzj.wjlp.BuildConfig;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.UpdataApp;

import io.reactivex.annotations.NonNull;

/**
 * 创建者：wjj
 * 创建时间：2018-05-31 下午 15:48
 * 功能描述： App更新工具类
 */
public class AppUpdate {

    private MaterialDialog dialogUpdate;
    private NotifyUtil notifyUtils;

    public AppUpdate() {
    }

    private static AppUpdate appUpdate = null;

    public static AppUpdate getInstance() {
        if (appUpdate == null) {
            appUpdate = new AppUpdate();
        }
        return appUpdate;
    }

    /**
     * 检测APP是否有更新
     *
     * @param updataApp     服务器回传的更新对象
     * @param activity      调用的Activity
     * @param systemChecked 是否是系统检测的
     */
    public void showAppUpdateDialog(final UpdataApp updataApp, final Activity activity, boolean systemChecked) {

        if (updataApp == null || activity == null) {
            return;
        }

        // 获取服务器回传的Code值
        Long serverCode = Long.parseLong(updataApp.getData().getCode());

        // 如果服务器的Code值大于本地的Code值则执行更新
        if (serverCode > BuildConfig.VERSION_CODE) {
            showUpdate(updataApp, activity);
        } else if (serverCode == BuildConfig.VERSION_CODE) { // 没有新版本
            if (!systemChecked) { // 如果不是系统检测的（手动检查更新）要弹窗提示一下用户
                showNoUpdate(activity);
            }
        }
//        else if (serverCode < BuildConfig.VERSION_CODE && updataApp.getData().getUpdate().equals("0")) {
//            showBack(updataApp, activity);
//        } else if (serverCode < BuildConfig.VERSION_CODE && !updataApp.getData().getUpdate().equals("0")) {
//            if (!systemChecked) {
//                showNoUpdate(activity);
//            }
//        }
    }

    /**
     * 提示不需要更新，在手动检查更新时调用
     *
     * @param activity
     */
    private void showNoUpdate(Activity activity) {
        new MikyouCommonDialog(activity, "当前已是最新版本", "检查更新", "知道了", "", true)
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                        switch (btnType) {
                            case MikyouCommonDialog.OK: {
                                // 直接关闭该对话框
                            }
                            break;
                        }
                    }
                }).showDialog();
    }

    /**
     * 提示不需要更新，在手动检查更新时调用
     *
     * @param activity
     */
    private void showBack(final UpdataApp updataApp, final Activity activity) {
        // 如果服务器版本号小于当前版本号，并且开启强制更新了
        String messageStr = "诚邀您恢复到之前版本：v" + updataApp.getData().getName() + (updataApp.getData().getDesc().isEmpty() ? "" : "\n" + updataApp.getData().getDesc());
        String exitBtnStr = "退出";

        new MikyouCommonDialog(activity, messageStr, "APP更新", "马上退回", exitBtnStr, !updataApp.getData().getUpdate().equals("0"))
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {

                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                        switch (btnType) {
                            case MikyouCommonDialog.OK: { // 马上退回
                                showDownloadDialog(updataApp, activity);
                            }
                            break;
                            case MikyouCommonDialog.NO: { // 退出
                                System.exit(0);
                            }
                            break;
                        }
                    }
                }).showDialog();
    }

    /**
     * 提示更新，包括是否强制更新
     *
     * @param updataApp
     * @param activity
     */
    private void showUpdate(final UpdataApp updataApp, final Activity activity) {
        String messageStr = "检测到新版本：v" + updataApp.getData().getName() + (updataApp.getData().getDesc().isEmpty() ? "" : "\n" + updataApp.getData().getDesc());
        String exitBtnStr = updataApp.getData().getUpdate().equals("0") ? "退出" : "稍后更新";

        new MikyouCommonDialog(activity, messageStr, "APP更新", "立即更新", exitBtnStr, !updataApp.getData().getUpdate().equals("0"))
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {

                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                        switch (btnType) {
                            case MikyouCommonDialog.OK: { // 立即更新
                                showDownloadDialog(updataApp, activity);
                            }
                            break;
                            case MikyouCommonDialog.NO: { // 稍后更新或退出
                                if (updataApp.getData().getUpdate().equals("0")) {//强制更新
                                    System.exit(0);
                                }
                            }
                            break;
                        }
                    }
                }).showDialog();
    }

    /**
     * 显示下载进度弹窗
     *
     * @param appUpdateInfo 回传的更新数据对象
     * @param activity      调用的界面Activity
     */
    private void showDownloadDialog(UpdataApp appUpdateInfo, final Activity activity) {

        dialogUpdate = new MaterialDialog.Builder(activity)
                .title("正在下载最新版本")
                .content("请稍等")
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .progress(false, 100, false)
                .negativeText("后台下载")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startNotifyProgress(activity);
                    }
                })
                .show();

        new InstallUtils(activity, appUpdateInfo.getData().getUrl(), "无界优品 " + appUpdateInfo.getData().getMessage(), new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                if (dialogUpdate != null) {
                    dialogUpdate.setProgress(0);
                }
            }

            @Override
            public void onComplete(String path) {
                /**
                 * 安装APK工具类
                 * @param context       上下文
                 * @param filePath      文件路径
                 * @param authorities   ---------Manifest中配置provider的authorities字段---------
                 * @param callBack      安装界面成功调起的回调
                 */
                InstallUtils.installAPK(activity, path, activity.getPackageName() + ".fileProvider", new InstallUtils.InstallCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(activity, "正在安装程序", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Exception e) {
                        Toast.makeText(activity, "安装失败:" + e.toString(), Toast.LENGTH_SHORT).show();
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
     * 开启Notify通知栏
     *
     * @param activity 调用的界面
     */
    private void startNotifyProgress(Activity activity) {
        // 设置想要展示的数据内容
        // 点击Notify跳转到的界面
        Intent intent = new Intent(activity, MainAty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.mipmap.wjyp2;
        String ticker = "正在下载无界优品更新包...";
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(activity, 0);
        notifyUtils.notify_progress(rightPendIntent, smallIcon, ticker, "无界优品 下载", "正在下载中...", false, false, false);
    }

}
