package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.RecargeAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.RechargeBean;
import com.txd.hzj.wjlp.catchDoll.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两界面
 */
public class MoneyActivity extends BaseAty implements RecargeAdapter.OnSelectChange {

    @ViewInject(R.id.titleView_bg_rlayout) // 设置背景颜色
    public RelativeLayout titleView_bg_rlayout;
    @ViewInject(R.id.titleView_goback_imgv) // 返回按钮
    public ImageView titleView_goback_imgv;
    @ViewInject(R.id.titleView_title_tv) // 标题文字
    public TextView titleView_title_tv;

    @ViewInject(R.id.money_recording_tv) // 游戏币记录按钮
    public TextView money_recording_tv;
    @ViewInject(R.id.money_number_tv) // 银两总数显示
    public TextView money_number_tv;
    @ViewInject(R.id.money_data_reView) // 选项支付列表
    public RecyclerView money_data_reView;
    @ViewInject(R.id.money_alipay_llayout) // 支付宝支付选项
    public LinearLayout money_alipay_llayout;
    @ViewInject(R.id.money_alipay_cbox) // 支付宝支付复选框
    public CheckBox money_alipay_cbox;
    @ViewInject(R.id.money_weChat_llayout) // 微信支付选项
    public LinearLayout money_weChat_llayout;
    @ViewInject(R.id.money_weChat_cbox) // 微信支付复选框
    public CheckBox money_weChat_cbox;
    @ViewInject(R.id.money_balance_llayout) // 余额支付选项
    public LinearLayout money_balance_llayout;
    @ViewInject(R.id.money_balance_cbox) // 余额支付复选框
    public CheckBox money_balance_cbox;
    @ViewInject(R.id.money_integral_llayout) // 积分支付选项
    public LinearLayout money_integral_llayout;
    @ViewInject(R.id.money_integral_cbox) // 积分支付复选框
    public CheckBox money_integral_cbox;
    @ViewInject(R.id.money_submit_tv) // 立即充值按钮
    public TextView money_submit_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_money;
    }

    @Override
    protected void initialized() {
        titleView_bg_rlayout.setBackgroundColor(getResources().getColor(R.color.white));
        titleView_goback_imgv.setImageResource(R.mipmap.icon_be_back_b);
        titleView_title_tv.setTextColor(getResources().getColor(R.color.black));
        titleView_title_tv.setText("银两");
    }

    @Override
    protected void requestData() {
        money_number_tv.setText("2500");
        setRechargeListShow();
    }

    /**
     * 设置充值游戏币数量
     */
    private void setRechargeListShow() {
        List<RechargeBean> rechargeBeanList = new ArrayList<>();
        rechargeBeanList.add(new RechargeBean(100, true, 100, 10.00));
        rechargeBeanList.add(new RechargeBean(300, true, 300, 30.00));
        rechargeBeanList.add(new RechargeBean(500, true, 500, 50.00));
        rechargeBeanList.add(new RechargeBean(1000, false, 1000, 100.00));
        rechargeBeanList.add(new RechargeBean(5000, true, 5000, 500.00));
        RecargeAdapter recargeAdapter = new RecargeAdapter(this, rechargeBeanList);
        recargeAdapter.setOnSelectChange(this);
        money_data_reView.setLayoutManager(new LinearLayoutManager(this));
        money_data_reView.setNestedScrollingEnabled(false); // 设置阻尼效果
        money_data_reView.setAdapter(recargeAdapter);
    }

    @OnClick({R.id.titleView_goback_imgv, R.id.money_recording_tv, R.id.money_submit_tv, R.id.money_alipay_llayout, R.id.money_weChat_llayout, R.id.money_balance_llayout, R.id.money_integral_llayout, R.id.money_alipay_cbox, R.id.money_weChat_cbox, R.id.money_balance_cbox, R.id.money_integral_cbox})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
            case R.id.money_recording_tv:
                startActivity(MoneyRecordingActivity.class, null);
                break;
            case R.id.money_submit_tv:
                break;
            case R.id.money_alipay_llayout:
            case R.id.money_alipay_cbox:
                setCheckboxChecked(1);
                break;
            case R.id.money_weChat_llayout:
            case R.id.money_weChat_cbox:
                setCheckboxChecked(2);
                break;
            case R.id.money_balance_llayout:
            case R.id.money_balance_cbox:
                setCheckboxChecked(3);
                break;
            case R.id.money_integral_llayout:
            case R.id.money_integral_cbox:
                setCheckboxChecked(4);
                break;
        }
    }

    /**
     * 设置相应选项选择状态
     *
     * @param index 选择索引 1:支付宝 2:微信 3:余额 4:积分
     */
    private void setCheckboxChecked(int index) {
        money_alipay_cbox.setChecked(false);
        money_weChat_cbox.setChecked(false);
        money_balance_cbox.setChecked(false);
        money_integral_cbox.setChecked(false);
        switch (index) {
            case 1:
                money_alipay_cbox.setChecked(true);
                break;
            case 2:
                money_weChat_cbox.setChecked(true);
                break;
            case 3:
                money_balance_cbox.setChecked(true);
                break;
            case 4:
                money_integral_cbox.setChecked(true);
                break;
        }
    }

    @Override
    public void change(RechargeBean rechargeBean) {
        // 充值列表选择项选择改变
        L.e(rechargeBean.toString());
    }
}
