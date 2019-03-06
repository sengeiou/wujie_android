package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.ui.fragment.MoneyRecordingFragment;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两记录界面
 */
public class MoneyRecordingActivity extends BaseAty {

    @ViewInject(R.id.titleView_bg_rlayout) // 设置背景颜色
    public RelativeLayout titleView_bg_rlayout;
    @ViewInject(R.id.titleView_goback_imgv) // 返回按钮
    public ImageView titleView_goback_imgv;
    @ViewInject(R.id.titleView_title_tv) // 标题文字
    public TextView titleView_title_tv;

    @ViewInject(R.id.moneyRecording_get_tv)
    public TextView moneyRecording_get_tv;
    @ViewInject(R.id.moneyRecording_consumption_tv)
    public TextView moneyRecording_consumption_tv;
    @ViewInject(R.id.moneyRecording_get_view)
    public View moneyRecording_get_view;
    @ViewInject(R.id.moneyRecording_consumption_view)
    public View moneyRecording_consumption_view;

    private Fragment[] fragments; // Fragment列表数组
    private MoneyRecordingFragment getFragment; // 获得记录Fragment
    private MoneyRecordingFragment consumptionFragment; // 消费记录Fragment
    private Fragment currentFragment; // 当前显示的Fragment

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_money_recording;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("银两记录");

        fragments = new Fragment[2];
        getFragment = new MoneyRecordingFragment(1);
        consumptionFragment = new MoneyRecordingFragment(2);
        fragments[0] = getFragment;
        fragments[1] = consumptionFragment;
        switchFragment(getFragment).commit();
    }

    @Override
    protected void requestData() {
    }

    @OnClick({R.id.titleView_goback_imgv, R.id.moneyRecording_get_rlayout, R.id.moneyRecording_consumption_rlayout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
            case R.id.moneyRecording_get_rlayout:
                moneyRecording_get_tv.setTextColor(getResources().getColor(R.color.colorCatchDollPrimary));
                moneyRecording_get_view.setBackgroundColor(getResources().getColor(R.color.colorCatchDollPrimary));
                moneyRecording_consumption_tv.setTextColor(getResources().getColor(R.color.black));
                moneyRecording_consumption_view.setBackgroundColor(getResources().getColor(R.color.color_transparent));
                switchFragment(getFragment).commit();
                break;
            case R.id.moneyRecording_consumption_rlayout:
                moneyRecording_get_tv.setTextColor(getResources().getColor(R.color.black));
                moneyRecording_get_view.setBackgroundColor(getResources().getColor(R.color.color_transparent));
                moneyRecording_consumption_tv.setTextColor(getResources().getColor(R.color.colorCatchDollPrimary));
                moneyRecording_consumption_view.setBackgroundColor(getResources().getColor(R.color.colorCatchDollPrimary));
                switchFragment(consumptionFragment).commit();
                break;
        }
    }

    private FragmentTransaction switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.moneyRecording_content_flayout, fragment, fragment.getClass().getName());
        } else {
            transaction.hide(currentFragment).show(fragment);
        }
        currentFragment = fragment;
        return transaction;
    }

}
