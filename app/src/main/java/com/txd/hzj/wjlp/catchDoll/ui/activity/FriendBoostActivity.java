package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.ActivRuleAdapter;
import com.txd.hzj.wjlp.catchDoll.adapter.FriendHelperHeaderAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.GameRoomHeaderBean;
import com.txd.hzj.wjlp.catchDoll.bean.RuleNameBean;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.ActivRuleDialog;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.BoostSuccessDialog;
import com.txd.hzj.wjlp.catchDoll.util.Util;
import com.txd.hzj.wjlp.catchDoll.view.BarrageView;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：好友助力界面
 */
public class FriendBoostActivity extends BaseAty {

    @ViewInject(R.id.friendBootsOnePage_barrage_barrv)
    public BarrageView friendBootsOnePage_barrage_barrv;

    @ViewInject(R.id.friendBootsOnePage_progressIndication_tv) // 指示器
    public TextView friendBootsOnePage_progressIndication_tv;
    @ViewInject(R.id.friendBootsOnePage_progress_pb) // 进度条
    public ProgressBar friendBootsOnePage_progress_pb;
    @ViewInject(R.id.friendBootsOnePage_progressNumber_tv) // 进度百分比
    public TextView friendBootsOnePage_progressNumber_tv;

    @ViewInject(R.id.friendBootsOnePage_expired_cdv)
    public CountdownView friendBootsOnePage_expired_cdv;
    @ViewInject(R.id.friendBootsOnePage_helpFriendList_reView)
    public RecyclerView friendBootsOnePage_helpFriendList_reView;

    @ViewInject(R.id.friendBootsTwoPage_helpFriendList_reView)
    public RecyclerView friendBootsTwoPage_helpFriendList_reView;

    private int maxMoney; // 最大助力银两数量
    private int currentMoney; // 当前助力银两

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_friend_boost;
    }

    @Override
    protected void initialized() {
        friendBootsOnePage_expired_cdv.start(3670 * 1000L);
        initProgress();
        initBarrage();
        initHeaderList();
        initNameList();
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.friendBootsOnePage_activDetails_tv, R.id.friendBootsOnePage_helpFriend_flayout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.friendBootsOnePage_activDetails_tv:
                String contentStr = "1、首先注册成为无界优品会员，即可免费获赠贰拾银两。\n\n" +
                        "2、每日签到可获得贰银两；连续签到10天，之后每天签到均可额外获得贰银两，直至连续签到中断为止。\n\n" +
                        "3、每天分享“抓娃娃”活动给好友助力，每分享一次即可获得贰银两，在Wap端做分享每天一次封顶，在App端做分享每天五次封顶。\n\n" +
                        "4、每成功邀请一名好友助力，即可至少获得贰银两，具体奖励方式详见活动页说明。\n\n" +
                        "5、若好友注册参与抓娃娃活动，您还可额外获得贰拾银两，同时您的好友也将获得签到抓娃娃资格\n\n" +
                        "6、如银两不足，支持在线充值购买所需银两。\n\n" +
                        "7、每抓取一次娃娃消耗贰银两起，具体所需银两数见活动详情页说明。";
                new ActivRuleDialog.Builder(this).setContent(contentStr).create().show();
                break;
            case R.id.friendBootsOnePage_helpFriend_flayout:
                new BoostSuccessDialog.Builder(this)
                        .setMoney("80")
                        .setContent("银两用途：通过在无界优品商城注册账号、活动分享、好友助力、参与手气拼单等方式可获得商城赠送的“银两”，所获“银两”可用于参加“集碎片”、“抓娃娃”、“紫薇斗数”、“手气拼单”等各商城版块的活动，具体获取和使用方式详见各活动版块的相关说明。")
                        .setGoLotteryClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(LotteryActivity.class, null);
                            }
                        }).create().show();
                break;
        }
    }

    /**
     * 初始化助力进度
     */
    private void initProgress() {
        maxMoney = 200;
        currentMoney = 30;
        int percentage = (int) ((float) currentMoney / maxMoney * 100);// 计算进度百分比

        friendBootsOnePage_progress_pb.setProgress(percentage); // 设置进度百分比
        // 设置展示的百分比以及字体颜色
        String percentageStr = new StringBuffer().append(String.valueOf(percentage)).append("%").toString();
        SpannableString spanColor = new SpannableString(percentageStr);
        if (percentage > 46 && percentage <= 48) {
            spanColor.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 1, 0);
        } else if (percentage > 48 && percentage <= 51) {
            spanColor.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 2, 0);
        } else if (percentage > 51) {
            spanColor.setSpan(new ForegroundColorSpan(Color.WHITE), 0, percentageStr.length(), 0);
        }
        friendBootsOnePage_progressNumber_tv.setText(spanColor);

        // 设置指示器显示文字
        friendBootsOnePage_progressIndication_tv.setText(new StringBuffer().append("已助力").append(currentMoney).append("两"));
        // 设置指示器的进度位置
        friendBootsOnePage_progressIndication_tv.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(friendBootsOnePage_progressIndication_tv.getLayoutParams());
                int progressBarLeftDp = friendBootsOnePage_progress_pb.getLeft(); // 进度条左边距像素距离
                int progressBarProgressDp = (int) (friendBootsOnePage_progress_pb.getWidth() * (((float) currentMoney / maxMoney) < 1 ? (float) currentMoney / maxMoney : 1)); // 进度条进度像素长度
                int tvHalfDp = friendBootsOnePage_progressIndication_tv.getWidth() / 2; // 指示标半截像素长度
                params.leftMargin = progressBarLeftDp + progressBarProgressDp - tvHalfDp;
                if (params.leftMargin < 0) {
                    params.leftMargin = 0;
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(params);
                friendBootsOnePage_progressIndication_tv.setLayoutParams(layoutParams);
            }
        });

    }

    /**
     * 初始化弹幕
     */
    private void initBarrage() {
        friendBootsOnePage_barrage_barrv.setBackgroundResource(0);
        friendBootsOnePage_barrage_barrv.setRowNum(3);
        List<String> msgList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                msgList.add(new StringBuffer().append("第").append(i).append("条弹幕").toString());
            } else {
                msgList.add(new StringBuffer().append("第").append(i).append("<font color=#FFC000>条弹幕</font>").append("第").append(i).append("<font color=#FFC000>条弹幕</font>").toString());
            }
        }
        Util.showBarrage(FriendBoostActivity.this, friendBootsOnePage_barrage_barrv, msgList);
    }

    /**
     * 初始化头像列表
     */
    private void initHeaderList() {
        // 此处用的游戏房间那块的头像数据和复制出来的适配器
        List<GameRoomHeaderBean> gameRoomHeaderBeans = new ArrayList<>();
        GameRoomHeaderBean gameRoomHeaderBean;
        for (int i = 0; i < 10; i++) {
            gameRoomHeaderBean = new GameRoomHeaderBean();
            gameRoomHeaderBean.setHeaderUrl("http://img.25pp.com/uploadfile/youxi/images/2015/0324/20150324035941178.jpg");
            gameRoomHeaderBeans.add(gameRoomHeaderBean);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        friendBootsOnePage_helpFriendList_reView.setLayoutManager(layoutManager);
        FriendHelperHeaderAdapter headerAdapter = new FriendHelperHeaderAdapter(gameRoomHeaderBeans, this);
        friendBootsOnePage_helpFriendList_reView.setAdapter(headerAdapter);
    }

    /**
     * 初始化助力名单
     */
    private void initNameList() {
        List<RuleNameBean> ruleNameBeanList = new ArrayList<>();
        RuleNameBean ruleNameBean;
        for (int i = 0; i < 20; i++) {
            ruleNameBean = new RuleNameBean();
            ruleNameBean.setHeaderUrl("http://img.25pp.com/uploadfile/youxi/images/2015/0324/20150324035941178.jpg");
            ruleNameBean.setName("测试者测试者测试者测试者" + i);
            ruleNameBean.setNumber((i + 1) * 10);
            ruleNameBean.setTime(1546854138 * 1000L);
            ruleNameBeanList.add(ruleNameBean);
        }
        ActivRuleAdapter adapter = new ActivRuleAdapter(this, ruleNameBeanList);
        friendBootsTwoPage_helpFriendList_reView.setLayoutManager(new LinearLayoutManager(this));
        friendBootsTwoPage_helpFriendList_reView.setNestedScrollingEnabled(false); // 设置阻尼效果
        friendBootsTwoPage_helpFriendList_reView.setAdapter(adapter);
    }
}
