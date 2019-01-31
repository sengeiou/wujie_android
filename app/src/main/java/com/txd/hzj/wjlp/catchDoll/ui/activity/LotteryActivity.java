package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.LotteryPeopleAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.LotteryPeopleBean;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.LotteryResultDialog;
import com.txd.hzj.wjlp.catchDoll.util.Util;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;
import com.txd.hzj.wjlp.catchDoll.view.wheelSurf.WheelSurfView;
import com.txd.hzj.wjlp.catchDoll.view.wheelSurf.listener.RotateListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：转盘抽奖界面
 */
public class LotteryActivity extends BaseAty {

    @ViewInject(R.id.lottery_pan_wsView)
    public WheelSurfView lottery_pan_wsView;
    @ViewInject(R.id.lottery_people_noSrView)
    public NoScrollRecyclerView lottery_people_noSrView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lottery;
    }

    @Override
    protected void initialized() {
        initWheelSurfView();
        initGrandPrizePeople();
    }

    @Override
    protected void requestData() {
    }

    /**
     * 初始化转盘界面视图
     */
    private void initWheelSurfView() {
        // 颜色
        Integer[] colors = new Integer[]{Color.parseColor("#FFFFFF"), Color.parseColor("#FFFBDB")
                , Color.parseColor("#FFFFFF"), Color.parseColor("#FFFBDB")
                , Color.parseColor("#FFFFFF"), Color.parseColor("#FFFBDB")
                , Color.parseColor("#FFFFFF"), Color.parseColor("#FFFBDB")};
        // 文字
        final String[] des = new String[]{"1 0 银 两", "土 豪 金 i p a d", "5 0 银 两"
                , "精 美 平 衡 车", "8 0 银 两", "佳 能 单 反 相 机",
                "1 0 0 银 两", "i p h o n e x"};
        // 图标
        List<Bitmap> mListBitmap = new ArrayList<>();
        for (String desStr : des) { // 此处推荐使用的是foreach循环
            mListBitmap.add(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_money));
        }

        mListBitmap = WheelSurfView.rotateBitmaps(mListBitmap); // 旋转视图

        WheelSurfView.Builder builder = new WheelSurfView.Builder()
                .setmColors(colors)
                .setmTextColor(Color.parseColor("#A84108"))
                .setmDeses(des)
                .setmIcons(mListBitmap)
                .setmType(1)
                .setmTypeNum(des.length)
                .build();
        lottery_pan_wsView.setConfig(builder);
        lottery_pan_wsView.setRotateListener(new RotateListener() {
            @Override
            public void rotateEnd(int position, String des) {
                new LotteryResultDialog.Builder(LotteryActivity.this).setResultStr(des).create().show();
//                showToast("旋转结束 位置：" + position + "  描述：" + des);
            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {
            }

            @Override
            public void rotateBefore(ImageView goImg) {
                // 模拟位置
                int position = Util.getRandomNumber(des.length) + 1; // 下标从1开始
                lottery_pan_wsView.startRotate(position);
            }
        });
    }

    /**
     * 初始化抽到大奖的小伙伴
     */
    private void initGrandPrizePeople() {
        lottery_people_noSrView.setFocusable(false);
        List<LotteryPeopleBean> lotteryPeopleBeanList = new ArrayList<>();
        LotteryPeopleBean lotteryPeopleBean;
        for (int i = 0; i < 20; i++) {
            lotteryPeopleBean = new LotteryPeopleBean();
            lotteryPeopleBean.setHeaderUrl("http://img4.duitang.com/uploads/item/201407/26/20140726141237_32faQ.png");
            lotteryPeopleBean.setContent("那谁谁谁谁抓到了<font color=#FFF000>佳能单反相机</font>");
            lotteryPeopleBeanList.add(lotteryPeopleBean);
        }
        LotteryPeopleAdapter adapter = new LotteryPeopleAdapter(lotteryPeopleBeanList, this);
        lottery_people_noSrView.setLayoutManager(new LinearLayoutManager(this));
        lottery_people_noSrView.setNestedScrollingEnabled(false); // 设置阻尼效果
        lottery_people_noSrView.setAdapter(adapter);
    }
}
