package com.txd.hzj.wjlp.catchDoll.view.nineLotteryView;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.SignInPrizeBean;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class LuckyMonkeyPanelView extends FrameLayout {

    public static final int START_MARQUEE = 1001;
    public static final int START_GAME = 1002;

    private ImageView marqueeStateOne; // 外灯框1
    private ImageView marqueeStateTwo; // 外灯框2

    private PanelItemView[] itemViewArr = new PanelItemView[8]; // 选框数组
    private int currentIndex = 0; // 当前选中
    private int currentTotal = 0; //
    private int stayIndex = 0; // 最终指向的View下标

    private boolean isMarqueeRunning = false; // 外边框灯闪动
    private boolean isGameRunning = false; // 是否正在游戏中（焦点是否在旋转中）
    private boolean isTryToStop = false; // 是否尝试（点击）停止

    private static final int DEFAULT_SPEED = 200; // 默认速度
    private static final int MIN_SPEED = 50; // 最快速度
    private int currentSpeed = DEFAULT_SPEED; // 当前速度，为默认速度
    private CountDownTimer countDownTimer; // 开始后的倒计时停止
    private PanelItemView view1;
    private PanelItemView view2;
    private PanelItemView view3;
    private PanelItemView view4;
    private PanelItemView view6;
    private PanelItemView view7;
    private PanelItemView view8;
    private PanelItemView view9;
    private Context context;

    public LuckyMonkeyPanelView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate(context, R.layout.view_lucky_mokey_panel, this);
        setupView(); // 设置View
    }

    private void setupView() {
        marqueeStateOne = findViewById(R.id.marquee_state_one); // 背景灯1
        marqueeStateTwo = findViewById(R.id.marquee_state_two); // 背景灯2

        view1 = findViewById(R.id.item1);// 上左
        view2 = findViewById(R.id.item2);// 上中
        view3 = findViewById(R.id.item3);// 上右
        view4 = findViewById(R.id.item4);// 中右
        view6 = findViewById(R.id.item6);// 下右
        view7 = findViewById(R.id.item7);// 下中
        view8 = findViewById(R.id.item8);// 下左
        view9 = findViewById(R.id.item9);// 中左

        itemViewArr[0] = view1;
        itemViewArr[1] = view2;
        itemViewArr[2] = view3;
        itemViewArr[3] = view6;
        itemViewArr[4] = view9;
        itemViewArr[5] = view8;
        itemViewArr[6] = view7;
        itemViewArr[7] = view4;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startMarquee(); // 控件附加到窗口的时候开始闪灯
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee(); // 分离出窗口的时候停止闪灯
        super.onDetachedFromWindow();
    }

    /**
     * 开始闪灯
     */
    private void startMarquee() {
        isMarqueeRunning = true;
        mHandler.sendEmptyMessageDelayed(START_MARQUEE, 250);
    }

    /**
     * 停止闪灯，停止一切
     */
    private void stopMarquee() {
        isMarqueeRunning = false;
        isGameRunning = false;
        isTryToStop = false;
    }

    private MarqueeRunningHandler mHandler = new MarqueeRunningHandler(this);

    /**
     * 选框运行处理线程
     */
    private static class MarqueeRunningHandler extends Handler {
        private WeakReference<LuckyMonkeyPanelView> panelView;

        public MarqueeRunningHandler(LuckyMonkeyPanelView luckyMonkeyPanelView) {
            panelView = new WeakReference<>(luckyMonkeyPanelView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LuckyMonkeyPanelView panelView;
            switch (msg.what) {
                case START_MARQUEE: // 开始闪动边框灯
                    panelView = this.panelView.get();
                    if (panelView != null) {
                        if (panelView.marqueeStateOne != null && panelView.marqueeStateTwo != null) {
                            if (VISIBLE == panelView.marqueeStateOne.getVisibility()) {
                                panelView.marqueeStateOne.setVisibility(GONE);
                                panelView.marqueeStateTwo.setVisibility(VISIBLE);
                            } else {
                                panelView.marqueeStateOne.setVisibility(VISIBLE);
                                panelView.marqueeStateTwo.setVisibility(GONE);
                            }
                        }
                        if (panelView.isMarqueeRunning) {
                            panelView.mHandler.sendEmptyMessageDelayed(START_MARQUEE, 250);
                        }
                    }
                    break;
                case START_GAME: // 开始游戏
                    panelView = this.panelView.get();
                    if (panelView != null) {
                        int preIndex = panelView.currentIndex; // 获取焦点索引
                        panelView.itemViewArr[preIndex].setFocus(false);
                        panelView.currentIndex++; // 向后移动一位
                        if (panelView.currentIndex >= panelView.itemViewArr.length) { // 如果当前焦点下标大于集合的长度，则将索引还原到0位置
                            panelView.currentIndex = 0;
                        }
                        panelView.itemViewArr[preIndex].setFocus(false); // 设置当前指向的选项焦点设为false
                        panelView.itemViewArr[panelView.currentIndex].setFocus(true); // 设置下一个项的焦点为true

                        // 如果尝试停止了，并且，当前速度等于默认速度，并且，最终指定的下标等于当前焦点的下标
                        if (panelView.isTryToStop && panelView.currentSpeed == DEFAULT_SPEED && panelView.stayIndex == panelView.currentIndex) {
                            panelView.isGameRunning = false; // 未进行游戏（设置焦点不再旋转）
                            if (panelView.panelStateListener != null) { // 如果监听不为空
                                panelView.panelStateListener.onPanelStateStop(panelView.stayIndex); // 调用监听通知已停止
                            }
                        }
                        if (panelView.isGameRunning) { // 如果正在游戏中，则继续发送消息
                            panelView.mHandler.sendEmptyMessageDelayed(START_GAME, panelView.getInterruptTime());
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 获取延迟时间
     *
     * @return 需要延迟的时长
     */
    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) { // 如果点击了停止
            currentSpeed += 10; // 当前延迟每次延长加10，相当于速度减缓
            if (currentSpeed > DEFAULT_SPEED) { // 如果当前速度发育默认速度，则将其设置为默认速度（慢速）
                currentSpeed = DEFAULT_SPEED;
            }
        } else { // 否则的话是点击了开始则相反
            if (currentTotal / itemViewArr.length > 0) {
                currentSpeed -= 10;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
        }
        return currentSpeed;
    }

    /**
     * 获取是否正在运行游戏
     *
     * @return 当前运行状态
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }

    /**
     * 开始游戏（开始旋转）
     *
     * @param position 指定停止位置
     */
    public void startGame(int position) {
        stayIndex = position;
        if (panelStateListener != null) {
            panelStateListener.onPanelStateStart();
        }
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        mHandler.sendEmptyMessageDelayed(START_GAME, getInterruptTime());
        countDownTimer = new CountDownTimer(5 * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                stopGame();
            }
        };
        countDownTimer.start();
    }

    /**
     * 停止游戏
     */
    public void stopGame() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTryToStop = true;
    }

    /**
     * 设置奖品
     */
    public void setItemPrize(List<SignInPrizeBean> list) {
        if (list == null || list.size() < 8) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PanelItemView panelItemView = itemViewArr[i];
            SignInPrizeBean signInPrizeBean = list.get(i);

            ImageView bgImgv = panelItemView.getViewPanel_bg_imgv(); // 背景图片
            LinearLayout prizeLlayout = panelItemView.getViewPanel_prize_llayout(); // 奖品内容和描述父布局
            ImageView prizeImgv = panelItemView.getViewPanel_prize_imgv(); // 奖品图片
            TextView contentTv = panelItemView.getViewPanel_content_tv(); // 内容描述
            ImageView failImgv = panelItemView.getViewPanel_fail_imgv(); // 未中奖图片

            // 首先设置一下item的背景图片（如果有回传背景图片）
            if (signInPrizeBean.getBgImg() != null && !signInPrizeBean.getBgImg().isEmpty()) {
                GlideUtils.loadUrlImg(context, signInPrizeBean.getBgImg(), bgImgv);
            } else {
                bgImgv.setImageResource(R.mipmap.square_marquee_square);
            }
            // 然后开始设置显示数据
            switch (signInPrizeBean.getType()) {
                case 1: // 奖励
                    prizeLlayout.setVisibility(View.VISIBLE);
                    failImgv.setVisibility(View.GONE);
                    GlideUtils.loadUrlImg(context, signInPrizeBean.getPrizeImg(), prizeImgv);
                    contentTv.setText(signInPrizeBean.getContent());
                    break;
                case 2: // 再来一次
                    prizeLlayout.setVisibility(View.VISIBLE);
                    failImgv.setVisibility(View.GONE);
                    prizeImgv.setVisibility(View.GONE);
                    contentTv.setText(signInPrizeBean.getContent());
                    break;
                case 3: // 未中奖
                    prizeLlayout.setVisibility(View.GONE);
                    failImgv.setVisibility(View.VISIBLE);
                    GlideUtils.loadUrlImg(context, signInPrizeBean.getPrizeImg(), failImgv);
                    break;
            }
        }
    }

    // ======================================= 监听接口 =======================================

    private PanelStateListener panelStateListener;

    public void setPanelStateListener(PanelStateListener panelStateListener) {
        this.panelStateListener = panelStateListener;
    }

    public interface PanelStateListener {
        /**
         * 开始旋转
         */
        void onPanelStateStart();

        /**
         * 停止旋转
         */
        void onPanelStateStop(int position);
    }

}
